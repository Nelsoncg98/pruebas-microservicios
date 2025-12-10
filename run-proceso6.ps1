Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Require-Command($name){
  if (-not (Get-Command $name -ErrorAction SilentlyContinue)){
    Write-Error "Command '$name' not found. Please install it and try again."
  }
}

function Start-Mvn($name, $module){
  $workDir = Join-Path $PSScriptRoot "$module"
  Write-Host "[run] $name (mvn spring-boot:run) -> $workDir" -ForegroundColor Yellow
  # Remove -WindowStyle Minimized if you want to see them immediately
  $p = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory $workDir -PassThru -WindowStyle Minimized
  return $p
}

Require-Command mvn

$pids = @()
$global:pidsList = $pids

try {
    # 1. Eureka
    $pE = Start-Mvn "Eureka Server" "EurekaServerN"; $global:pidsList += $pE.Id
    Write-Host "Waiting 20s for Eureka..." -ForegroundColor Cyan
    Start-Sleep -Seconds 20

    # 2. Dependencies (Common)
    if (Test-Path "ms-medico") {
        $pMed = Start-Mvn "Ms-Medico" "ms-medico"; $global:pidsList += $pMed.Id
    }
    if (Test-Path "ms-atencionmedica") {
        $pAt = Start-Mvn "Ms-AtencionMedica" "ms-atencionmedica"; $global:pidsList += $pAt.Id
    }
    Start-Sleep -Seconds 10

    # 3. Process 6 Core
    $pCat = Start-Mvn "Ms-Medicamento (Catalogo)" "ms-medicamento"; $global:pidsList += $pCat.Id
    $pRec = Start-Mvn "Ms-Receta (Core)" "ms-receta"; $global:pidsList += $pRec.Id
    $pDet = Start-Mvn "Ms-DetalleReceta (Detalle)" "ms-detallereceta"; $global:pidsList += $pDet.Id
    Start-Sleep -Seconds 10

    # 4. Action & Orchestration
    $pAct = Start-Mvn "Ms-AgregarMedicamento (Accion)" "ms-agregarmedicamento"; $global:pidsList += $pAct.Id
    $pGes = Start-Mvn "Ms-GestionReceta (Facade)" "ms-gestionreceta"; $global:pidsList += $pGes.Id

    Write-Host "[ok] Proceso 6 launched. Processes are running in background windows." -ForegroundColor Green
    Write-Host "Press Ctrl+C to stop ALL services." -ForegroundColor Green

    while ($true){
        Start-Sleep -Seconds 3
    }

} finally {
    Write-Host "[stop] Cleaning up processes..." -ForegroundColor Red
    foreach($pidTarget in $global:pidsList){
        try { Stop-Process -Id $pidTarget -ErrorAction SilentlyContinue } catch {}
    }
}
