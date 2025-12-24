$ErrorActionPreference = "Stop"

function Require-Command ($command) {
    if (-not (Get-Command $command -ErrorAction SilentlyContinue)) {
        Write-Error "$command is required but not found."
        exit 1
    }
}

Require-Command mvn
Require-Command java

$pidsToKill = @()

function Start-Mvn ($serviceName, $directory, $port) {
    # Check if port is already in use
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
    Write-Host "=== PROCESO 4: PAGO DE CITA ===" -ForegroundColor Magenta

    # 1. Infra
    Start-Mvn "EurekaServerN" "EurekaServerN" 8761
    Start-Sleep -Seconds 20

    # 2. Dependencies (Reference)
    # Note: If you want to force start them, uncomment. 
    # Usually Proceso 4 assumes Cita exists.
    Start-Mvn "ms-cita" "procesoTres\ms-cita" 8089
    Start-Mvn "ms-paciente" "ms-paciente" 8092
    
    # 3. Core
    Write-Host "Starting Core Services..." -ForegroundColor White
    Start-Mvn "ms-boleta" "ms-boleta" 8083
    Start-Mvn "ms-cajero" "ms-cajero" 8084
    Start-Sleep -Seconds 10

    # 4. Orchestrator
    Write-Host "Starting Orchestrator..." -ForegroundColor White
    Start-Mvn "ms-gestionboleta" "ms-gestionboleta" 8199
    
    Write-Host "--------------------------------------------------"
    Write-Host "PROCESO 4 READY"
    Write-Host "--------------------------------------------------"
    Write-Host "Press Ctrl+C to stop services started by THIS script."

    while ($true) {
        Start-Sleep -Seconds 1
    }

} finally {
    Write-Host "Stopping services launched by this session..."
    foreach ($pidKill in $pidsToKill) {
        Stop-Process -Id $pidKill -Force -ErrorAction SilentlyContinue 
        Start-Process -FilePath "taskkill" -ArgumentList "/PID $pidKill /T /F" -NoNewWindow -Wait
    }
}
