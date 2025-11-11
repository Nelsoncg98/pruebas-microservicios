# Clínica — Sistema de Microservicios (plantilla basada en ApiArticuloN)

Este documento define cómo construiremos el sistema de la clínica con microservicios siguiendo el mismo estilo tecnológico de `ApiArticuloN` (Spring Boot + Eureka), pero estandarizando para desarrollo local con persistencia en H2/Hibernate. `ApiArticuloN` sirve como ejemplo; los demás microservicios se crearán con la misma plantilla.

## Tecnología base
- Java 21
- Spring Boot 3.5.x (parent actual en ApiArticuloN: 3.5.6)
- Spring Cloud 2025.0.x (Eureka)
- Spring Web (REST)
- Spring Data JPA + Hibernate
- H2 Database (dev/test). Opcional: migración a PostgreSQL por entorno (prod)
- Testing: JUnit 5 + spring-boot-starter-test (objetivo >50% cobertura por servicio)

Dependencias base por servicio (pom.xml):
- org.springframework.boot:spring-boot-starter-web
- org.springframework.boot:spring-boot-starter-data-jpa
- com.h2database:h2 (scope runtime)
- org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
- test: org.springframework.boot:spring-boot-starter-test

Variables importantes detectadas en ApiArticuloN/Eureka:
- Java 21
- spring-cloud 2025.0.0
- Eureka Server en 8761

## Arquitectura
- Eureka Server (descubrimiento) — puerto 8761 (como el proyecto `EurekaServerN`).
- Microservicios independientes registrados en Eureka. Comunicación HTTP REST directa por nombre lógico (load-balancing simple del lado cliente a futuro si agregamos Spring Cloud LoadBalancer).
- Base de datos: H2 por microservicio en dev/test (modo file o in-memory). Para producción, se puede parametrizar a PostgreSQL con perfiles.

### Orden de arranque
1. EurekaServerN (8761)
2. Resto de servicios (ver puertos).

## Microservicios del sistema — Estado actual y faltantes
En lugar de listar sólo lo planificado, a continuación se presenta el estado real del repo ("YA ESTÁN") y los microservicios que faltan por implementar ("FALTA"). También se alinea esta lista con los requerimientos del cliente (procesos del caso de estudio).

### Servicios presentes en el workspace (YA ESTÁN)
- `EurekaServerN` (8761) — servidor de descubrimiento
- `ms-personaladministrativo` (8081)
- `ms-programacionmedica` (8082)
- `ms-carritohorariomedico` (8083)
- `ms-horariomedico` (8085)
- `ms-enfermera` (8086)
- `ms-paciente` (8087)
- `ms-medico` (8091)
- Otros módulos de ejemplo / soporte que están en el repo: `ApiArticuloN`, `ApiBoletaN`, `ArqEmpNBoletaCarrito`, `Carrito/`, `postman_collection.json` (usar solo como referencia)

> Nota: según su instrucción los servicios `ms-linea*` (por ejemplo `ms-lineahorariomedico`, `ms-lineareceta`, `ms-lineaanalisis`) no se contabilizan como servicios separados: su funcionalidad se asumirá dentro del servicio padre correspondiente.

### Servicios que faltan por implementar (FALTA)
Estos servicios figuran en la tabla referencial y son necesarios para cubrir todos los procesos del cliente:
- `ms-historia` — Historia clínica (8088)
- `ms-cita` — Gestión de citas (8089)
- `ms-cajero` — Caja / pagos (8090)
- `ms-boleta` — Emisión de boletas (8092)
- `ms-receta` — Recetas médicas (8093)
- `ms-carritoreceta` — Carrito para recetas (8094)
- `ms-medicamento` — Catálogo de medicamentos (8096)
- `ms-atencionmedica` — Registro de atenciones (8097)
- `ms-analisis` — Solicitudes de laboratorio (8098)
- `ms-carritoanalisis` — Carrito para análisis (8099)
- `ms-tipoanalisis` — Catálogo de tipos de análisis (8101)

### Alineación con los procesos del cliente (caso de estudio)
Los 7 procesos descritos por el cliente (programación, historia, solicitud de cita, pago, atención, receta, análisis) se mapean a los microservicios así:

- Proceso 1 — Programación de horarios de médicos:
   - Servicios implicados: `ms-personaladministrativo` (actor), `ms-programacionmedica`, `ms-horariomedico`, `ms-medico`.

- Proceso 2 — Historia médica (registro inicial por enfermera):
   - Servicios implicados: `ms-enfermera`, `ms-paciente`, `ms-historia` (faltante).

- Proceso 3 — Solicitud de cita médica (teléfono):
   - Servicios implicados: `ms-cita` (faltante), `ms-programacionmedica`, `ms-horariomedico`, `ms-medico`.

- Proceso 4 — Pago de cita:
   - Servicios implicados: `ms-cajero` (faltante), `ms-boleta` (faltante), `ms-cita`.

- Proceso 5 — Atención médica:
   - Servicios implicados: `ms-atencionmedica` (faltante), `ms-cita`, `ms-boleta`, `ms-receta` (faltante), `ms-analisis` (faltante), `ms-historia`.

- Proceso 6 — Receta médica:
   - Servicios implicados: `ms-receta` (faltante), `ms-medicamento` (faltante).

- Proceso 7 — Análisis clínico:
   - Servicios implicados: `ms-analisis` (faltante), `ms-tipoanalisis` (faltante), `ms-carritoanalisis` (faltante).

### Qué se cambió respecto al README anterior
- Se eliminaron las referencias a `ms-linea*` como servicios independientes (su lógica irá dentro del servicio padre).
- Se añadió una sección clara con el estado actual del repo (qué YA ESTÁ) y con el listado de lo que FALTA.
- Se alineó la lista con los procesos del cliente, indicando para cada proceso qué microservicios implementan la funcionalidad y cuáles están pendientes.

Si quieres, puedo ahora:
- Generar automáticamente `estimates/missing_microservices.csv` con este listado (nombre, puerto sugerido, entidad, estado FALTA), o
- Crear un `requirements/` README que incluya este mapeo más detallado (endpoints mínimos por servicio), o
- Nada: dejar el README actualizado y volvemos a lo siguiente que indiques.

## Procesos del cliente (caso de estudio: Centro Médico)
Se incluyen a continuación los procesos funcionales que el cliente definió y que sirven como requisitos de negocio para el sistema.

1. Proceso de programación de horarios de médicos:
   - El personal administrativo consulta la disponibilidad al médico por especialidad y elabora la programación de médicos por día y hora, considerando la especialidad, fecha, día, hora y consultorio.

2. Proceso de historia médica:
   - Para ser atendido el paciente acude al centro médico, la enfermera registra sus datos personales y toma los datos médicos básicos como peso, talla, edad siempre y cuando sea la primera vez que asista, finalmente la enfermera elabora la historia médica del paciente.

3. Proceso de solicitud de cita médica:
   - Para ser atendido el paciente solicita vía telefónica una cita médica, el encargado solicita la especialidad y le informa los médicos disponibles la fecha, hora y costo de atención, una vez confirmado el médico, el encargado elabora la cita médica.

4. Proceso de pago de cita:
   - El paciente acude al centro médico y se acerca a caja; el cajero solicita sus datos personales y de la cita, finalmente el cajero elabora una boleta de venta, el paciente entrega el dinero y se retira con la boleta cancelada.

5. Proceso de atención médica:
   - El paciente acude a la cita, el médico verifica el pago de la cita y elabora la atención médica: examina al paciente, registra los datos de la atención médica como diagnóstico y tratamiento; a esa ficha se le añaden la receta de medicamentos y/o análisis clínico si se requiere; finalmente registra la atención y añade toda esta información a la historia médica.

6. Proceso de receta médica:
   - El médico selecciona los medicamentos de un listado de medicamentos y elabora la receta médica.

7. Proceso de análisis clínico:
   - El médico selecciona los tipos de análisis de una lista de tipos de análisis y elabora la solicitud de análisis clínico.

Estas descripciones deben usarse como requisitos de negocio al diseñar los endpoints y el comportamiento de los microservicios listados más arriba.

## Convenciones por servicio
- Paquete base: `clinica.<nombreServicio>` (o similar) para diferenciar del ejemplo `Tienda.ApiArticuloN`.
- application.properties mínimo:

```properties
spring.application.name=ms-<servicio>
server.port=<puerto>

# Eureka
eureka.client.service-url.default-zone=http://localhost:8761/eureka

# H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:file:./data/<servicio>;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

- Entidades JPA con `@Entity` y `@Id` (usar `@GeneratedValue` cuando aplique). Campos según tabla.
- Repositorios Spring Data: `interface XxxRepositorio extends JpaRepository<Xxx, Long>`
- Servicio de dominio: `XxxServicio` con métodos CRUD.
- Controlador REST: `@RestController` bajo `@RequestMapping("/<recurso>")` con `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`.
- Para llamadas entre servicios (consume): usar `RestClient`/`WebClient` de Spring o `RestTemplate` simple, resolviendo por nombre lógico cuando registremos todos en Eureka.

## Estructura recomendada del proyecto
```
clinica/
  EurekaServerN/                     # servidor de descubrimiento (8761)
  ms-personaladministrativo/
  ms-programacionmedica/
  ms-carritohorariomedico/
  ms-lineahorariomedico/
  ms-horariomedico/
  ms-enfermera/
  ms-paciente/
  postman_collection.json            # colección de pruebas (se irá ampliando)
  readme-clinica.md                  # este documento
```

## Cómo ejecutar en local (Windows PowerShell)
Requisitos:
- JDK 21 instalado y en PATH (`java -version`)
- Maven instalado (`mvn -v`) — o agregar Maven Wrapper en cada módulo más adelante

Arranque rápido (una consola por servicio):
```powershell
# 1) Eureka
cd .\EurekaServerN
mvn spring-boot:run

# 2) Servicios (ejemplos)
cd ..\ms-personaladministrativo
mvn spring-boot:run

cd ..\ms-programacionmedica
mvn spring-boot:run
# ... y así sucesivamente
```

Verificaciones:
- Eureka UI: http://localhost:8761
- H2 console por servicio: http://localhost:<puerto>/h2 (JDBC URL como en properties)

## Postman
La colección `postman_collection.json` actual incluye ejemplos de `ApiArticuloN` y `ApiBoletaN`. Para el sistema de clínica:
- Se agregarán carpetas por cada `ms-*` con CRUDs correspondientes.
- Se añadirán variables de entorno para `{{baseUrl}}` o por-servicio (`{{url_personaladministrativo}}`, etc.).

## Testing
- JUnit 5 + Spring Boot Test.
- Criterio mínimo: >50% cobertura por servicio (clases de Controlador y Servicio con pruebas de happy path + errores comunes).

## Docker (opcional)
- Si usamos H2 en desarrollo, Docker no es necesario. Se puede añadir más adelante para escenarios con PostgreSQL u orquestación local.
- A futuro: `Dockerfile` por servicio (jdk21-jre base) y `docker-compose.yml` con Eureka y servicios. Para H2, se puede montar volumen para el archivo `./data` de cada servicio; para entornos integrados se recomienda migrar a PostgreSQL.

## Roadmap de implementación
1. Crear plantilla de microservicio clonando estructura de ApiArticuloN y ajustando POM a incluir `spring-boot-starter-data-jpa` y `h2`.
2. Generar entidades, repositorios, servicios y controladores según cada dominio.
3. Configurar `application.properties` con puerto, nombre, H2 y Eureka.
4. Probar CRUDs con Postman y H2 console.
5. Añadir pruebas unitarias y de integración.
6. Añadir Dockerfiles y compose (opcional).

## Notas
- `ApiArticuloN` se mantiene como ejemplo inicial. Los nuevos servicios seguirán la misma convención y se irán agregando al repositorio.
- La tecnología puede ajustarse “sobre la marcha” de acuerdo a lo que necesitemos (por ejemplo, mover H2 a PostgreSQL, agregar OpenAPI/Swagger, etc.).

---

## H2: creación de esquema y datos de ejemplo (opcional)
Cada servicio puede inicializar su BD con `schema.sql` y `data.sql` en `src/main/resources/`:

```sql
-- schema.sql
CREATE TABLE IF NOT EXISTS articulo (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   cod VARCHAR(50),
   nom VARCHAR(255),
   pre DOUBLE
);

-- data.sql
INSERT INTO articulo (cod, nom, pre) VALUES ('A001','Producto demo',10.5);
```

Spring Boot ejecuta `schema.sql` y `data.sql` automáticamente al arrancar (si existen) con H2.

## Consumo entre microservicios (Eureka + RestTemplate)
Ejemplo real tomado de `ApiBoletaN` para consumir `ApiArticuloN` resolviendo por nombre de servicio (requiere `@Bean @LoadBalanced RestTemplate`):

```java
@RestController
@RequestMapping("/boleta")
public class ApiBoletaNControl {
   @Autowired private RestTemplate resTem; // Bean definido con @LoadBalanced

   @GetMapping
   public List<?> listarArticulos() {
      // Importante: usar el path completo del controlador de Artículo
      String url = "http://ApiArticuloN/articulo/listar";
      return resTem.getForObject(url, List.class);
   }
}

@SpringBootApplication
public class ApiBoletaNApplication {
   @Bean
   @LoadBalanced
   public RestTemplate restTemplate(){
      return new RestTemplate();
   }
}
```

Notas:
- El host `ApiArticuloN` coincide con `spring.application.name` del servicio Artículo y se resuelve vía Eureka.
- Asegúrate de que el path `/articulo/listar` corresponda al `@RequestMapping` del controlador de `ApiArticuloN`.
- Alternativas modernas: `RestClient` (Spring 6) o `WebClient`.
