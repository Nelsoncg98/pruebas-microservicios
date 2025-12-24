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
    Write-Host "=== PROCESO 2: HISTORIA MEDICA ===" -ForegroundColor Magenta

    # 1. Eureka
    Start-Mvn "Eureka Server" "EurekaServerN" 8761
    Start-Sleep -Seconds 20

    # 2. Core Services
    Start-Mvn "Ms-Paciente" "ms-paciente" 8092
    Start-Mvn "Ms-Enfermera" "ms-enfermera" 8093
    Start-Sleep -Seconds 10
    
    # 3. Process Specific
    Start-Mvn "Ms-HistoriaMedica" "ms-historiamedica" 8088
    # Note: ExpedienteClinico (8193) is listed in Readme as Process 5 mostly, but sometimes checked here.
    # Readme process 2 only lists Enferm, Paciente, Historia. 
    # I will stick to Readme strictly unless user overrides.

    Write-Host "--------------------------------------------------"
    Write-Host "PROCESO 2 READY"
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
