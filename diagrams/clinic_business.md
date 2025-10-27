## Diagrama de negocio — Microservicios de la clínica

Este archivo resume, en formato Markdown, la vista de negocio de los microservicios del proyecto: puerto expuesto, rutas públicas principales y las relaciones de negocio (quién consume a quién). No contiene atributos, entidades ni nombres de archivo, tal como pediste.

---

### Resumen rápido
- Descubrimiento: Eureka en el puerto `8761` (UI en `/`).
- Servicios principales y responsabilidades (alto nivel): horarios, médicos, programación, carrito de horarios y servicios de apoyo (personal, paciente, enfermera).
- Relaciones detectadas en el código: Programación compone vistas desde el Carrito; Carrito obtiene horarios de HorarioMedico; HorarioMedico consulta datos de Médico.

---

### Servicios y endpoints principales

- Eureka (Service Discovery)
  - puerto: 8761
  - endpoints públicos: `/` (UI), `/actuator/*` (si activado)

- ms-horariomedico
  - puerto: 8085
  - rutas públicas (principales):
    - GET  /horariomedico/listar
    - POST /horariomedico/guardar
    - GET  /horariomedico/buscar/{id}
    - DELETE /horariomedico/eliminar/{id}
    - PUT  /horariomedico/actualizar/{id}
    - DELETE /horariomedico/limpiar
    - GET  /horariomedico/disponibles  (filtros opcionales)
    - GET  /horariomedico/medicosdisponibles

- ms-medico
  - puerto: 8091
  - rutas públicas (principales):
    - GET  /medico/listar
    - POST /medico/guardar
    - GET  /medico/buscar/{id}
    - DELETE /medico/eliminar/{id}
    - DELETE /medico/limpiar
    - POST /medico/reactivar/{id}

- ms-programacionmedica
  - puerto: 8087
  - rutas públicas (principales):
    - POST /programacionmedica/nueva/{idAdministrativo}
    - GET  /programacionmedica/buscar/{id}
    - GET  /programacionmedica/listar
    - POST /programacionmedica/eliminar/{id}  (soft-delete / inactivar)
    - POST /programacionmedica/reactivar/{id}
    - POST /programacionmedica/limpiar

- ms-carritohorariomedico
  - puerto: 8094
  - rutas públicas (principales):
    - POST   /carritohorario/agregar/{horarioId}
    - DELETE /carritohorario/quitar/{id}
    - GET    /carritohorario/listar
    - GET    /carritohorario/total
    - DELETE /carritohorario/nuevo  (limpiar)

- ms-personaladministrativo
  - puerto: 8081
  - rutas públicas (principales):
    - GET    /personaladministrativo
    - GET    /personaladministrativo/{id}
    - POST   /personaladministrativo
    - PUT    /personaladministrativo/{id}
    - DELETE /personaladministrativo/{id}

- ms-paciente
  - puerto: 8092
  - rutas públicas (principales):
    - GET    /paciente/listar
    - GET    /paciente/buscar/{id}
    - POST   /paciente/guardar
    - DELETE /paciente/eliminar/{id}
    - DELETE /paciente/limpiar

- ms-enfermera
  - puerto: 8093
  - rutas públicas (principales):
    - GET    /enfermera/listar
    - GET    /enfermera/buscar/{id}
    - POST   /enfermera/guardar
    - DELETE /enfermera/eliminar/{id}
    - DELETE /enfermera/limpiar

- ApiArticuloN (ejemplo)
  - puerto: 8081
  - rutas públicas: GET /articulo/listar, POST /articulo/guardar, GET /articulo/buscar/{id}, DELETE /articulo/eliminar/{id}, DELETE /articulo/limpiar

- ApiBoletaN (ejemplo)
  - puerto: 8082
  - rutas públicas: GET /boleta

---

### Relaciones de negocio (alto nivel)

- Frontend → ms-horariomedico: consulta horarios y disponibilidad (ej. `/horariomedico/disponibles`).
- Frontend → ms-programacionmedica: operaciones de programación médicas (crear, listar, buscar, inactivar, reactivar).
- Frontend → ms-carritohorariomedico: agregar/quitar/listar/totalizar horarios.

- Programación Médica → Carrito: el servicio de programación compone vistas pidiendo al carrito `/carritohorario/listar` (llamada HTTP detectada en el código).
- Carrito → Horario: cuando se agrega o consulta una línea, el carrito obtiene detalle del horario desde `ms-horariomedico` (`/horariomedico/buscar/{id}`).
- Horario → Médico: `ms-horariomedico` consulta información del médico desde `ms-medico` (`/medico/buscar/{id}`) para enriquecer horarios.
- Boleta (ejemplo) → Carrito: flujo de boleta/venta que consume datos del carrito para calcular totales.

---

### Fuentes y comandos que usé para extraer la información

Los siguientes comandos te ayudan a reproducir o actualizar la extracción de información desde el código fuente.

- Buscar puertos (application.properties):
```bash
rg "server.port" -n || grep -R --line-number "server.port" .
```

- Listar controladores y mappings:
```bash
rg "@(RestController|RequestMapping|GetMapping|PostMapping|PutMapping|DeleteMapping)" -n src || grep -R --line-number "@RestController\|@RequestMapping\|@GetMapping" src
```

- Encontrar llamadas inter-servicio (RestTemplate / WebClient / Feign):
```bash
rg "RestTemplate|getForObject|WebClient|@FeignClient" -n src || true
```

- Obtener endpoints runtime (si Actuator está habilitado):
```bash
curl -sS http://localhost:8085/actuator/mappings | jq .
```

---

### PlantUML (opcional)
También generé un `diagrams/clinic_business.puml` con la misma visión de negocio (archivo PlantUML). Si deseas una imagen PNG/SVG lista para documentación, puedo generarla y añadirla al repositorio.

---

Si quieres, ahora puedo:
- (A) Generar la imagen PNG/SVG a partir del PlantUML y subirla a `diagrams/`.  
- (B) Añadir una versión Mermaid para pegar en Markdown.  

Dime cuál prefieres y lo hago (no modificaré nada más sin tu confirmación).
