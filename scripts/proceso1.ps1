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

$pPA = Start-Mvn "ms-personaladministrativo" "ms-personaladministrativo"; $pids += $pPA.Id
Start-Sleep -Seconds 2
$pM = Start-Mvn "ms-medico" "ms-medico"; $pids += $pM.Id
Start-Sleep -Seconds 2
$pH = Start-Mvn "ms-horariomedico" "ms-horariomedico"; $pids += $pH.Id
Start-Sleep -Seconds 2
$pCH = Start-Mvn "ms-carritohorariomedico" "ms-carritohorariomedico"; $pids += $pCH.Id
Start-Sleep -Seconds 2
$pPM = Start-Mvn "ms-programacionmedica" "ms-programacionmedica"; $pids += $pPM.Id
Start-Sleep -Seconds 2
$pPC = Start-Mvn "ms-programacioncompuesta" "ms-programacioncompuesta"; $pids += $pPC.Id

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

Write-Host "[ok] Proceso 1 services started. Press Ctrl+C or close the window to stop." -ForegroundColor Green

try {
  while ($true){
    Start-Sleep -Seconds 2
  }
} finally {
  foreach($pid in $pids){
    try { Stop-Process -Id $pid -ErrorAction SilentlyContinue } catch {}
  }
}
