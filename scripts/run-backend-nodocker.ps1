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

# Move to repo root
Set-Location -Path (Join-Path $PSScriptRoot '..')

Require-Command mvn

# Start processes (minimized windows)
$pids = @()
$pE = Start-Mvn "Eureka" "EurekaServerN"; $pids += $pE.Id
Start-Sleep -Seconds 7
$pM = Start-Mvn "ms-medico" "ms-medico"; $pids += $pM.Id
Start-Sleep -Seconds 2
$pH = Start-Mvn "ms-horariomedico" "ms-horariomedico"; $pids += $pH.Id
Start-Sleep -Seconds 2
$pP = Start-Mvn "ms-programacionmedica" "ms-programacionmedica"; $pids += $pP.Id
Start-Sleep -Seconds 2
$pA = Start-Mvn "ms-personaladministrativo" "ms-personaladministrativo"; $pids += $pA.Id
Start-Sleep -Seconds 2
$pC = Start-Mvn "ms-paciente" "ms-paciente"; $pids += $pC.Id
Start-Sleep -Seconds 2
$pN = Start-Mvn "ms-enfermera" "ms-enfermera"; $pids += $pN.Id
Start-Sleep -Seconds 2
$pCH = Start-Mvn "ms-carritohorariomedico" "ms-carritohorariomedico"; $pids += $pCH.Id

# Register cleanup on PowerShell exit (Ctrl+C or window close)
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

Write-Host "[ok] Services started. Press Ctrl+C or close the window to stop." -ForegroundColor Green

# Keep script alive while children are running
try {
  while ($true){
    Start-Sleep -Seconds 2
    foreach($pid in @($pids)){
      if (-not (Get-Process -Id $pid -ErrorAction SilentlyContinue)){
        # If any process exited unexpectedly, inform
        Write-Host "[warn] Process $pid exited." -ForegroundColor DarkYellow
      }
    }
  }
} finally {
  # Redundant cleanup (in case Exiting event didn't run)
  foreach($pid in $pids){
    try { Stop-Process -Id $pid -ErrorAction SilentlyContinue } catch {}
  }
}
