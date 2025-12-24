$ErrorActionPreference = "Stop"

function Require-Command ($command) {
    if (-not (Get-Command $command -ErrorAction SilentlyContinue)) {
        Write-Error "$command is required but not found."
        exit 1
    }
}
Require-Command mvn

$pidsToKill = @()

function Start-Mvn ($serviceName, $directory, $port) {
    $portActive = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
    if ($portActive) {
        Write-Host "SKIP: $serviceName is ALREADY RUNNING on port $port." -ForegroundColor Yellow
        return
    }

    Write-Host "STARTING: $serviceName on port $port..." -ForegroundColor Cyan
    $workDir = Join-Path "$PSScriptRoot\.." "$directory"
    
    $proc = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory $workDir -PassThru -NoNewWindow
    $global:pidsToKill += $proc.Id
    Write-Host "STARTED: $serviceName (PID $($proc.Id))." -ForegroundColor Green
}

try {
    Write-Host "=== PROCESO 3: SOLICITUD DE CITA ===" -ForegroundColor Magenta

    # 1. Eureka
    Start-Mvn "Eureka Server" "EurekaServerN" 8761
    Start-Sleep -Seconds 20

    # 2. Infra / Dependencies
    # Usually needs Medico, Horario, Paciente for logic
    Start-Mvn "Ms-Medico" "ms-medico" 8091
    Start-Mvn "Ms-Paciente" "ms-paciente" 8092
    Start-Mvn "Ms-HorarioMedico" "ms-horariomedico" 8085
    Start-Sleep -Seconds 10
    
    # 3. Process Specific (Note subfolder 'procesoTres' from previous structure)
    Start-Mvn "Ms-DisponibilidadHorarios" "ms-disponibilidadhorarios" 8185
    Start-Mvn "Ms-Cita" "procesoTres\ms-cita" 8089
    Start-Sleep -Seconds 10

    # 4. Orchestrator
    Start-Mvn "Ms-SolicitudCita" "procesoTres\ms-solicitudcita" 8189

    Write-Host "--------------------------------------------------"
    Write-Host "PROCESO 3 READY"
    Write-Host "--------------------------------------------------"
    Write-Host "Press Ctrl+C to stop services started by THIS script."

    while ($true) {
        Start-Sleep -Seconds 1
    }

} finally {
    Write-Host "Stopping services..."
    foreach ($pidKill in $pidsToKill) {
        Stop-Process -Id $pidKill -Force -ErrorAction SilentlyContinue 
        Start-Process -FilePath "taskkill" -ArgumentList "/PID $pidKill /T /F" -NoNewWindow -Wait
    }
}
