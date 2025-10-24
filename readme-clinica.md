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

## Microservicios del sistema (Sprint Semana 1)
A continuación, los servicios a crear tomando como plantilla ApiArticuloN y ajustando nombres, entidad, puertos y endpoints. Todos exponen CRUD RESTful: GET (listar/buscar), POST (crear), PUT (actualizar), DELETE (borrar). Donde aplique, se añaden endpoints adicionales.

1. ms-personaladministrativo (8081)
   - Entidad: PersonalAdministrativo { numero, nombre, apellido, telefono, cargo, fecha, estado }
   - BD: personaladministrativo_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /personaladministrativo
   - Dependencias externas: ninguna

2. ms-programacionmedica (8082)
   - Entidad: ProgramacionMedica { numero, fecha, personal, medico }
   - BD: programacionmedica_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /programacionmedica
   - Consume: GET /personaladministrativo/{id} (ms-personaladministrativo), GET /medico/{id} (a futuro ms-medico)

3. ms-carritohorariomedico (8083)
   - Entidad: CarritoHorarioMedico { numero, fecha, programacion, total_horarios }
   - BD: carritohorariomedico_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /carritohorariomedico
   - Consume: GET /programacionmedica/{id} (ms-programacionmedica)

4. ms-lineahorariomedico (8084)
   - Entidad: LineaHorarioMedico { numero, carrito, horario, especialidad }
   - BD: lineahorariomedico_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /lineahorariomedico
   - Consume: GET /carritohorariomedico/{id} (ms-carritohorariomedico), GET /horariomedico/{id} (ms-horariomedico)

5. ms-horariomedico (8085)
   - Entidad: HorarioMedico { numero, fecha, horaInicio, horaFin, medico, consultorio }
   - BD: horariomedico_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /horariomedico; GET /horariomedico/disponibles
   - Consume: GET /medico/{id} (a futuro ms-medico)

6. ms-enfermera (8086)
   - Entidad: Enfermera { numero, nombre, apellido, documento, cod_coleg, turno, area, estado }
   - BD: enfermera_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /enfermera
   - Dependencias externas: ninguna

7. ms-paciente (8087)
   - Entidad: Paciente { numero, nombre, apellido, nroDoc, telefono, sexo, fechaNaci, direccion, nro_afiliado, nom_seguro }
   - BD: paciente_db (H2)
   - Endpoints: GET, POST, PUT, DELETE /paciente
   - Dependencias externas: ninguna

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
