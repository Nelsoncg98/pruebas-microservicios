# Script para iniciar Proceso 5: Atención Médica
# Inicia Eureka y los servicios necesarios (Core y Composite)

Write-Host "Iniciando Eureka Server..." -ForegroundColor Green
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\EurekaServerN" -NoNewWindow
Start-Sleep -Seconds 10

Write-Host "Iniciando ms-atencionmedica (Core)..." -ForegroundColor Cyan
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-atencionmedica" -NoNewWindow

Write-Host "Iniciando ms-gestionatencionmedica (Composite)..." -ForegroundColor Cyan
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-gestionatencionmedica" -NoNewWindow

Write-Host "Iniciando ms-nuevaatencion (Composite - Inicializador)..." -ForegroundColor Cyan
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-nuevaatencion" -NoNewWindow

# Iniciar servicios mock/dependencias si es necesario para que el composite no falle al buscar
Write-Host "Iniciando Dependencias (Cita, Paciente, Medico, Historia)..." -ForegroundColor Yellow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\procesoTres\ms-cita" -NoNewWindow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-paciente" -NoNewWindow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-medico" -NoNewWindow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory ".\ms-historiamedica" -NoNewWindow

Write-Host "Todos los servicios iniciados. Espere a que se registren en Eureka." -ForegroundColor Green
Write-Host "Pruebe el endpoint: POST http://localhost:8190/gestionatencion/registrar"
