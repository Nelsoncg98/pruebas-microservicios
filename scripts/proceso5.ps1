$ErrorActionPreference = "Stop"

function Require-Command ($command) {
    if (-not (Get-Command $command -ErrorAction SilentlyContinue)) {
        Write-Error "$command is required but not found."
        exit 1
    }
}

Require-Command mvn
Require-Command java

# Store PIDs for cleanup (only for processes we actually start)
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
    
    # Start process
    $proc = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory $workDir -PassThru -NoNewWindow
    $global:pidsToKill += $proc.Id
    Write-Host "STARTED: $serviceName (PID $($proc.Id))." -ForegroundColor Green
}

try {
    Write-Host "=== PROCESO 5: ATENCION MEDICA ===" -ForegroundColor Magenta
    
    # 1. Infrastructure
    Start-Mvn "EurekaServerN" "EurekaServerN" 8761
    Start-Sleep -Seconds 10

    # 2. Transversal / Dependencies
    Write-Host "Checking Dependencies..." -ForegroundColor White
    Start-Mvn "ms-medico" "ms-medico" 8091
    Start-Mvn "ms-paciente" "ms-paciente" 8092
    Start-Mvn "ms-cita" "procesoTres\ms-cita" 8089
    Start-Mvn "ms-historiamedica" "ms-historiamedica" 8088
    Start-Sleep -Seconds 10
    
    # 3. Core Domain
    Write-Host "Starting Core Services..." -ForegroundColor White
    Start-Mvn "ms-atencionmedica" "ms-atencionmedica" 8097
    Start-Sleep -Seconds 10

    # 4. Orchestrators
    Write-Host "Starting Orchestrators..." -ForegroundColor White
    Start-Mvn "ms-gestionatencionmedica" "ms-gestionatencionmedica" 8197
    Start-Mvn "ms-nuevaatencion" "ms-nuevaatencion" 8297
    
    Write-Host "--------------------------------------------------"
    Write-Host "PROCESO 5 READY"
    Write-Host "Use: POST http://localhost:8197/gestionatencion/registrar"
    Write-Host "--------------------------------------------------"
    Write-Host "Press Ctrl+C to stop services started by THIS script."

    while ($true) {
        Start-Sleep -Seconds 1
    }

} finally {
    Write-Host "Stopping services launched by this session..." -ForegroundColor Yellow
    foreach ($pidKill in $pidsToKill) {
        Stop-Process -Id $pidKill -Force -ErrorAction SilentlyContinue 
        Start-Process -FilePath "taskkill" -ArgumentList "/PID $pidKill /T /F" -NoNewWindow -Wait
    }
}
