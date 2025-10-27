## Microservicios – Clínica (Grupo 1)
ds
Este repositorio contiene un conjunto de microservicios para un escenario académico de clínica (Grupo 1). Está orientado a la práctica y las pruebas locales, con componentes independientes que se comunican mediante REST y registro en Eureka.

Incluye:
- Descubrimiento de servicios con Eureka
- Microservicios de dominio clínico (médico, horario, programación, paciente, enfermera, personal administrativo)
- Un carrito mínimo de horarios (`ms-carritohorariomedico`) para uso académico
- Colección de Postman para probar localmente los endpoints de la clínica

Tecnologías clave
- Java 21
- Spring Boot 3.5.6
- Spring Cloud 2025.0.0 (Eureka Client)
- H2 para persistencia en servicios que lo requieren
---

## Estructura y propósito de módulos

- `EurekaServerN` (8761): Registro de servicios (Service Discovery) vía Eureka.
- `ms-medico` (8091): CRUD de médicos.
- `ms-horariomedico` (8085): CRUD de horarios médicos. Endpoints claros (listar/guardar/buscar/eliminar/limpiar/disponibles).
- `ms-programacionmedica` (8087): CRUD de Programación Médica (mínimo, académico). Soft delete (activo/inactivo). Sin acoplamientos complejos.
- `ms-personaladministrativo` (8081): CRUD de personal administrativo.
- `ms-paciente` (8092): CRUD de pacientes.
- `ms-enfermera` (8093): CRUD de enfermeras.
- `ms-carritohorariomedico` (8094): Carrito académico para “agregar/quitar/listar/total/nuevo” usando horarios del ms-horariomedico, al estilo del Carrito/Boleta del profesor.

Ejemplos del profesor (referencias y pruebas):
- `ApiArticuloN`, `ApiBoletaN`: ejemplos de tienda.
- `Carrito`, `ArqEmpNBoletaCarrito`: ejemplo canónico de Carrito/Boleta (composición con RestTemplate, sin complejidad extra).

---

## Cómo ejecutar local (sin Docker)

Requisitos:
- Java 21 en PATH (java -version)
- Maven en PATH (mvn -version)

Inicio rápido (Windows):
1) Ejecuta `scripts\run-backend-nodocker.bat`
2) Espera a que arranquen: Eureka → médicos → horario → programación → administrativo → paciente → enfermera → carrito de horarios
3) Para detener todo: cierra la ventana o usa `scripts\stop-backend-nodocker.bat`

Puertos por defecto:
- Eureka: 8761
- ms-medico: 8091
- ms-horariomedico: 8085
- ms-programacionmedica: 8087
- ms-personaladministrativo: 8081
- ms-paciente: 8092
- ms-enfermera: 8093
- ms-carritohorariomedico: 8094

Variables de entorno relevantes:
- `EUREKA_URL` (default: http://localhost:8761/eureka)
- `PORT` (algunos servicios soportan PORT dinámico)

---

## Postman – Colección de pruebas

Archivo: `postman_collection.json`

Incluye carpetas para cada microservicio. Destacados:
- ms-programacionmedica
	- POST `/programacionmedica/nueva/{idAdministrativo}`
	- GET `/programacionmedica/buscar/{id}`
	- GET `/programacionmedica/listar`
	- POST `/programacionmedica/eliminar/{id}` (soft delete)
	- POST `/programacionmedica/reactivar/{id}`
	- POST `/programacionmedica/limpiar`
- ms-carritohorariomedico
	- POST `/carritohorario/agregar/{horarioId}`
	- GET `/carritohorario/listar`
	- GET `/carritohorario/total`
	- DELETE `/carritohorario/quitar/{id}`
	- DELETE `/carritohorario/nuevo`

Consejo: crea primero un horario en `ms-horariomedico` y usa su id en “agregar” del carrito.

---

## Estado actual del proyecto (resumen)

- Orientado a lo académico y a la simplicidad:
	- Programación Médica sin acoplamientos complejos (sin DTOs ni clientes específicos).
	- Carrito de horarios minimalista, inspirado en Carrito/Boleta del profesor.
- Controladores con respuestas claras (200/201/204/400/404/409 donde aplica).
- Config listas para discovery con Eureka y ejecución local sin Docker.

Pendientes sugeridos (opcionales):
- Ajustar validaciones avanzadas (solapamientos en horarios, timeouts).
- Añadir pruebas automatizadas por servicio.
- Empaquetado para despliegue cloud (Railway/otros) por servicio.

---

## Problemas comunes y tips

- “No arranca porque falta Maven/Java”: instala Java 21 y Maven, reinicia la terminal.
- “Puerto en uso”: ejecuta `scripts\stop-backend-nodocker.bat` y reintenta.
- “Eureka no registra servicios”: espera unos segundos tras levantar Eureka, o verifica `EUREKA_URL`.

---

## Créditos

Proyecto académico – Grupo 1 (Microservicios). Basado en prácticas y ejemplos del profesor (Carrito/Boleta). Muchas partes fueron simplificadas para priorizar comprensión y pruebas rápidas del front.

