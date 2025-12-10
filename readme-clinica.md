# Clínica — Sistema de Microservicios (Actualizado)

Este documento refleja el estado actual del sistema de microservicios para la clínica, alineado con los requisitos de negocio.

## Tecnologías
- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Cloud 2025.0.0 (Eureka)**
- **H2 Database** (Persistencia en archivo local `./data/`)

## Arquitectura de Microservicios Existentes

El sistema se compone de los siguientes microservicios, todos registrados en el servidor Eureka (Puerto 8761).

### Servicios de Dominio (Core)

| Microservicio | Puerto | Descripción |
| :--- | :--- | :--- |
| `ms-personaladministrativo` | 8081 | Gestión de personal administrativo. |
| `ms-medico` | 8091 | Gestión de médicos y especialidades. |
| `ms-paciente` | 8092 | Gestión de pacientes. |
| `ms-enfermera` | 8093 | Gestión de enfermeras. |
| `ms-horariomedico` | 8085 | CRUD de horarios disponibles. |
| `ms-programacionmedica` | 8087 | Gestión de la programación de turnos. |
| `ms-historiamedica` | 8088 | Gestión de historias clínicas. |
| `ms-cita` | 8089 | Gestión de citas médicas. |
| `ms-pagos` | 8199 | Gestión de pagos y caja. |
| `ms-expedienteclinico` | 8193 | Servicio de consulta consolidada del expediente del paciente. |

### Servicios de Orquestación y Apoyo

| Microservicio | Puerto | Descripción |
| :--- | :--- | :--- |
| `ms-carritohorariomedico` | 8094 | Carrito temporal para selección de horarios. |
| `ms-programacioncompuesta` | 8187 | Orquestador para creación de programaciones complejas. |
| `ms-solicitudcita` | 8189 | Orquestador para el flujo de solicitud de citas. |
| `ms-disponibilidadhorarios` | 8185 | Consulta agregada de disponibilidad. |
| `EurekaServerN` | 8761 | Servidor de descubrimiento de servicios. |

---

## Procesos del cliente (Requisitos de Negocio)

Se incluyen a continuación los procesos funcionales que el cliente definió y que sirven como requisitos de negocio para el sistema. **Estos procesos son inmutables.**

### 1. Proceso de programación de horarios de médicos
- El personal administrativo consulta la disponibilidad al médico por especialidad y elabora la programación de médicos por día y hora, considerando la especialidad, fecha, día, hora y consultorio.
- **Estado:** Implementado.
- **Servicios:** 
  - `ms-programacioncompuesta` (Puerto 8187): Orquestador.
  - `ms-horariomedico` (Puerto 8085): Entidad Horario.
  - `ms-programacionmedica` (Puerto 8087): Entidad Programación.
  - `ms-personaladministrativo` (Puerto 8081): Entidad Personal.
  - `ms-medico` (Puerto 8091): Entidad Médico.

### 2. Proceso de historia médica
- Para ser atendido el paciente acude al centro médico, la enfermera registra sus datos personales y toma los datos médicos básicos como peso, talla, edad siempre y cuando sea la primera vez que asista, finalmente la enfermera elabora la historia médica del paciente.
- **Estado:** Implementado.
- **Servicios:**
  - `ms-enfermera` (Puerto 8093): Entidad Enfermera.
  - `ms-paciente` (Puerto 8092): Entidad Paciente.
  - `ms-historiamedica` (Puerto 8088): Entidad Historia Médica.

### 3. Proceso de solicitud de cita médica
- Para ser atendido el paciente solicita vía telefónica una cita médica, el encargado solicita la especialidad y le informa los médicos disponibles la fecha, hora y costo de atención, una vez confirmado el médico, el encargado elabora la cita médica.
- **Estado:** Implementado.
- **Servicios:**
  - `ms-solicitudcita` (Puerto 8189): Orquestador.
  - `ms-disponibilidadhorarios` (Puerto 8185): Consulta Agregada.
  - `ms-cita` (Puerto 8089): Entidad Cita.

### 4. Proceso de pago de cita
- El paciente acude al centro médico y se acerca a caja; el cajero solicita sus datos personales y de la cita, finalmente el cajero elabora una boleta de venta, el paciente entrega el dinero y se retira con la boleta cancelada.
- **Estado:** Implementado (`ms-pagos` Puerto 8199).

### 5. Proceso de atención médica
- El paciente acude a la cita, el médico verifica el pago de la cita y elabora la atención médica: examina al paciente, registra los datos de la atención médica como diagnóstico y tratamiento; a esa ficha se le añaden la receta de medicamentos y/o análisis clínico si se requiere; finalmente registra la atención y añade toda esta información a la historia médica.
- **Estado:** Implementado.
- **Servicios:**
    - `ms-gestionatencionmedica` (Puerto 8197): Orquestador principal.
    - `ms-atencionmedica` (Puerto 8097): Entidad Atención Médica.
    - `ms-nuevaatencion` (Puerto 8297): Orquestador alternativo/experimental.
    - `ms-expedienteclinico` (Puerto 8193): Consulta consolidada.

### 6. Proceso de receta médica
- El médico selecciona los medicamentos de un listado de medicamentos y elabora la receta médica.
- **Estado:** Implementado.
- **Servicios:**
  - `ms-gestionreceta` (Puerto 8196): Orquestador principal.
  - `ms-agregarmedicamento` (Puerto 8195): Orquestador de apoyo.
  - `ms-receta` (Puerto 8095): Entidad Receta.
  - `ms-medicamento` (Puerto 8082): Catálogo de Medicamentos.
  - `ms-detallereceta` (Puerto 8096): Entidad Detalle.

### 7. Proceso de análisis clínico
- El médico selecciona los tipos de análisis de una lista de tipos de análisis y elabora la solicitud de análisis clínico.
- **Estado:** Implementado.
- **Servicios:**
  - `ms-gestionanalisis` (Puerto 8198): Orquestador principal.
  - `ms-agregartipo` (Puerto 8190): Orquestador de apoyo.
  - `ms-analisis` (Puerto 8099): Entidad Análisis.
  - `ms-tipoanalisis` (Puerto 8090): Catálogo de Tipos.
  - `ms-detalleanalisis` (Puerto 8098): Entidad Detalle.

---

## Respuestas Enriquecidas de Servicios Compuestos

Los servicios orquestadores devuelven respuestas enriquecidas con datos completos de entidades relacionadas para mejorar la experiencia del cliente y reducir llamadas adicionales.

### ms-programacioncompuesta

**Endpoint:** `GET /programacioncompuesta/buscar/{id}`

**Respuesta Enriquecida:**
```json
{
  "id": 1,
  "administrativoId": 5,
  "fechaProgramacion": "21/11/2025",
  "activo": true,
  "horarioMedicoIds": [1, 2, 3],
  "horarios": [
    {
      "numero": 1,
      "medicoId": 10,
      "fecha": "2025-11-22",
      "horaInicio": "08:00",
      "horaFin": "09:00",
      "consultorio": "101",
      "disponible": true
    }
  ],
  "administrativo": {
    "numero": 5,
    "nombre": "Ana",
    "apellido": "García",
    "dni": "87654321",
    "cargo": "Coordinador",
    "email": "ana@clinica.com"
  },
  "medico": {
    "numero": 10,
    "nombre": "Carlos",
    "apellido": "Rodríguez",
    "especialidad": "Cardiología",
    "dni": "12345678",
    "email": "carlos@clinica.com",
    "telefono": "987654321"
  },
  "totalHorarios": 3
}
```

**Campos Enriquecidos:**
- `administrativo`: Objeto completo del personal administrativo responsable
- `medico`: Objeto completo del médico (una programación = un médico)
- `totalHorarios`: Contador de horarios en la programación

### ms-solicitudcita

**Endpoint:** `GET /solicitudcita/citasPorPaciente?idPaciente=1`

**Respuesta Enriquecida:**
```json
{
  "numero": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "fechaNacimiento": "1990-05-15",
  "telefono": "999888777",
  "email": "juan@email.com",
  "direccion": "Av. Principal 123",
  "estado": true,
  "citas": [
    {
      "numero": 10,
      "pacienteId": 1,
      "horarioId": 5,
      "idDoctor": "10",
      "motivo": "Control",
      "tipoCita": "CONSULTA",
      "estado": "RESERVADA",
      "costo": 100.0
    }
  ],
  "totalCitas": 2,
  "citasPendientes": 1
}
```

**Campos Enriquecidos:**
- `totalCitas`: Total de citas del paciente
- `citasPendientes`: Citas en estado RESERVADA

### ms-carritohorariomedico - Validación de Médico Único

**Regla de Negocio:** El carrito solo puede contener horarios de **UN SOLO médico** a la vez.

**Comportamiento:**
- Carrito vacío → Acepta cualquier médico
- Carrito con médico A → Solo acepta horarios del médico A
- Intento de agregar médico B → Error 409 Conflict

**Ejemplo de Error:**
```json
{
  "message": "El carrito ya contiene horarios del médico ID=10. No se pueden agregar horarios de otro médico (ID=15). Vacíe el carrito primero."
}
```

---

## Ejecución Local

1. **Iniciar Eureka:**
   ```powershell
   cd EurekaServerN
   mvn spring-boot:run
   ```

2. **Iniciar Servicios:**
   Se recomienda iniciar primero los servicios Core y luego los de Proceso.
