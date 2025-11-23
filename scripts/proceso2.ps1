Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Require-Command($name){
  if (-not (Get-Command $name -ErrorAction SilentlyContinue)){
    Write-Error "Command '$name' not found. Please install it and try again."
  }
}

function Start-Mvn($name, $module){
  $workDir = Join-Path $PSScriptRoot "..\$module"
  Write-Host "[run] $name (mvn spring-boot:run) -> $workDir" -ForegroundColor Yellow
  $p = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory $workDir -PassThru -WindowStyle Minimized
  return $p
}

Set-Location -Path (Join-Path $PSScriptRoot '..')
Require-Command mvn

$pids = @()
$pE = Start-Mvn "Eureka" "EurekaServerN"; $pids += $pE.Id
Start-Sleep -Seconds 7

# Servicios Core del Proceso 2
$pPac = Start-Mvn "ms-paciente" "ms-paciente"; $pids += $pPac.Id
Start-Sleep -Seconds 2
$pEnf = Start-Mvn "ms-enfermera" "ms-enfermera"; $pids += $pEnf.Id
Start-Sleep -Seconds 2
$pHM = Start-Mvn "ms-historiamedica" "ms-historiamedica"; $pids += $pHM.Id
Start-Sleep -Seconds 2
$pEC = Start-Mvn "ms-expedienteclinico" "ms-expedienteclinico"; $pids += $pEC.Id

$null = Register-EngineEvent PowerShell.Exiting -Action {
  try {
    if ($global:pids){
      Write-Host "[stop] Stopping services..." -ForegroundColor Cyan
      foreach($pid in $global:pids){
        try { Stop-Process -Id $pid -ErrorAction SilentlyContinue } catch {}
      }
    }
  } catch {}
}

Write-Host "[ok] Proceso 2 services started. Press Ctrl+C or close the window to stop." -ForegroundColor Green

try {
  while ($true){
    Start-Sleep -Seconds 2
  }
} finally {
  foreach($pid in $pids){
    try { Stop-Process -Id $pid -ErrorAction SilentlyContinue } catch {}
  }
}
