Write-Host "=== LIMPIANDO DATA DEL PROCESO 6 (RECETA) ===" -ForegroundColor Cyan
Write-Host "Cerrando procesos Java para liberar archivos..." -ForegroundColor Yellow

try {
    taskkill /F /IM java.exe 2>$null
    taskkill /F /IM javaw.exe 2>$null
} catch {}

Start-Sleep -Seconds 2

function Clean-Data($path) {
    if (Test-Path $path) {
        Write-Host "Eliminando data en: $path" -ForegroundColor Red
        Remove-Item -Path $path -Recurse -Force
    } else {
        Write-Host "No se encontró data en: $path (Ya estaba limpio)" -ForegroundColor Gray
    }
}

# Rutas especificas del Proceso 6
Clean-Data ".\ms-medicamento\data"
Clean-Data ".\ms-receta\data"
Clean-Data ".\ms-detallereceta\data"

# Tambien limpiamos ms-atencionmedica si se desea "Full Reset" para que los enlaces no queden rotos
# Descomentar si se desea: 
# Clean-Data ".\ms-atencionmedica\data"

Write-Host "=== LIMPIEZA COMPLETADA ===" -ForegroundColor Green
Write-Host "Ahora puedes ejecutar run-proceso6.bat para iniciar desde CERO." -ForegroundColor Green
Pause
