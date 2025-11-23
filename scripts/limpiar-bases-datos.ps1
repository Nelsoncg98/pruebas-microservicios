# Script para Limpiar Todas las Bases de Datos H2

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Limpieza de Bases de Datos H2" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$rootPath = Split-Path -Parent $PSScriptRoot
$dbFiles = Get-ChildItem -Path $rootPath -Recurse -Filter "*.mv.db" -ErrorAction SilentlyContinue

if ($dbFiles.Count -eq 0) {
    Write-Host "[INFO] No se encontraron archivos de base de datos." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Presiona cualquier tecla para salir..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit
}

Write-Host "[ENCONTRADOS] $($dbFiles.Count) archivos de base de datos:" -ForegroundColor Yellow
foreach ($file in $dbFiles) {
    $relativePath = $file.FullName.Replace($rootPath, ".")
    Write-Host "  - $relativePath" -ForegroundColor Gray
}
Write-Host ""

Write-Host "[ADVERTENCIA] Esta acción eliminará TODOS los datos de TODOS los microservicios." -ForegroundColor Red
Write-Host "[ADVERTENCIA] Los IDs comenzarán desde 1 nuevamente." -ForegroundColor Red
Write-Host ""
$confirmation = Read-Host "¿Estás seguro? Escribe 'SI' para continuar"

if ($confirmation -ne "SI") {
    Write-Host ""
    Write-Host "[CANCELADO] Operación cancelada por el usuario." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Presiona cualquier tecla para salir..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit
}

Write-Host ""
Write-Host "[ELIMINANDO] Archivos de base de datos..." -ForegroundColor Cyan

$deletedCount = 0
$errorCount = 0

foreach ($file in $dbFiles) {
    try {
        $relativePath = $file.FullName.Replace($rootPath, ".")
        Remove-Item -Path $file.FullName -Force -ErrorAction Stop
        Write-Host "  [OK] $relativePath" -ForegroundColor Green
        $deletedCount++
    } catch {
        Write-Host "  [ERROR] $relativePath - $($_.Exception.Message)" -ForegroundColor Red
        $errorCount++
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Resumen" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Archivos eliminados: $deletedCount" -ForegroundColor Green
if ($errorCount -gt 0) {
    Write-Host "Errores: $errorCount" -ForegroundColor Red
}
Write-Host ""
Write-Host "[COMPLETADO] Bases de datos limpiadas exitosamente." -ForegroundColor Green
Write-Host "[NOTA] Los IDs comenzarán desde 1 cuando inicies los servicios." -ForegroundColor Yellow
Write-Host ""
Write-Host "Presiona cualquier tecla para salir..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
