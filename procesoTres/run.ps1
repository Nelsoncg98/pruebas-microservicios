# Requires: PowerShell, Java 21, Maven on PATH
# This script starts EurekaServerN, ms-paciente, ms-horariomedico and ms-cita

$workspace = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Split-Path -Parent $workspace

function Start-ServiceMvn {
    param(
        [string]$path
    )
    # Use Set-Location -LiteralPath to safely handle paths with spaces and special chars
    # Build a single -Command string and enclose the path in single quotes so the launched PowerShell
    # receives the full path as one token even if it contains spaces.
    $arg = "& { Set-Location -LiteralPath '$path'; mvn -q spring-boot:run }"
    Start-Process powershell -ArgumentList "-NoExit","-Command",$arg | Out-Null
}

Write-Host "Starting EurekaServerN..."
Start-ServiceMvn -path "$root\EurekaServerN"
Start-Sleep -Seconds 5

Write-Host "Starting ms-paciente..."
Start-ServiceMvn -path "$root\ms-paciente"
Start-Sleep -Seconds 2

Write-Host "Starting ms-horariomedico..."
Start-ServiceMvn -path "$root\ms-horariomedico"
Start-Sleep -Seconds 2

Write-Host "Starting ms-cita..."
Start-ServiceMvn -path "$workspace\ms-cita"

Write-Host "All services started in new PowerShell windows."
