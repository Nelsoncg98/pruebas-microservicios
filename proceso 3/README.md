# Proceso 3: Programación de cita

Este documento describe el flujo backend del Proceso 3 y el microservicio `ms-cita` creado para orquestar la reserva del horario y registrar la cita.

## Flujo (alineado al requerimiento)

1. El cliente filtra médicos y visualiza horarios disponibles desde `ms-horariomedico`.
2. Ingresa el DNI del paciente (o utiliza un paciente ya registrado).
3. Selecciona un horario disponible.
4. El backend:
   - Verifica el paciente vía `GET /paciente?dni=...` en `ms-paciente`.
   - Reserva el horario vía `PATCH /horariomedico/reservar/{id}` en `ms-horariomedico`.
   - Registra la cita en `ms-cita`.
5. Para cancelar, el backend libera el horario (`PATCH /horariomedico/liberar/{id}`) y marca la cita como cancelada.

## Endpoints utilizados (servicios existentes)

- `ms-paciente`
  - `GET /paciente?dni=...` → 200 con Paciente o 404 si no existe.
- `ms-horariomedico`
  - `GET /horariomedico/disponibles` → lista de horarios filtrables.
  - `PATCH /horariomedico/reservar/{id}` → 200 si reserva, 409 si ya está reservado, 404 si no existe.
  - `PATCH /horariomedico/liberar/{id}` → 200 si libera, 409 si ya estaba libre, 404 si no existe.

## Microservicio nuevo: ms-cita

Nombre de aplicación: `ms-cita` (Eureka)

Entidad `Cita` (ampliada):
- `numero` (Long)
- `pacienteId` (Long)
- `horarioId` (Long, opcional si cita sin horario)
- `idDoctor` (String, identificador del médico)
- `motivo` (String, motivo breve)
- `fecha` (LocalDateTime, fecha/hora efectiva; se deriva del horario si no se envía y existe `horarioId`)
- `tipoCita` (String: CONSULTA | CONTROL | EMERGENCIA | TELECONSULTA, abierto)
- `estado` (String: RESERVADA | CANCELADA | FINALIZADA)

API:
- `POST /cita/crear`
  - Con horario: `{ "dniPaciente": "..." | "pacienteId": 7, "horarioId": 15, "idDoctor": "45", "motivo": "Dolor de cabeza", "tipoCita": "CONSULTA" }` (fecha derivada)
  - Sin horario (emergencia): `{ "dniPaciente": "...", "idDoctor": "45", "motivo": "Trauma", "fecha": "2025-11-14T10:35:00", "tipoCita": "EMERGENCIA" }`
  - Reglas: requiere paciente (id o DNI), idDoctor, motivo; horarioId opcional si se provee fecha explícita.
  - Respuestas: 201 (cita), 400 (faltan datos / formato fecha), 404 (paciente/horario), 409 (horario ya reservado/no disponible).
- `DELETE /cita/cancelar/{id}`
  - Libera el horario asociado y marca la cita como CANCELADA.
  - Respuestas: 200 (cita), 404 (cita no encontrada).
- `GET /cita/buscar/{id}` y `GET /cita/listar` (consulta básica).

## Diagramas

Revisa `./diagrams/proceso3_overview.puml` para la secuencia de integración.

## Cómo ejecutar (local)

Requisitos: Java 21, Maven, y `EurekaServerN` corriendo en `http://localhost:8761`.

Opciones:
- Ejecutar cada servicio desde su IDE.
- Usar el script `run.ps1` para abrir procesos (PowerShell en Windows).

Puertos por defecto:
- `EurekaServerN` → 8761
- `ms-paciente` → 8092
- `ms-horariomedico` → 8085 (o `PORT`)
- `ms-cita` → 8096

