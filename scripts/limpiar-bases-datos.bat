@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script de limpieza de bases de datos
echo.
echo ========================================
echo   Limpieza de Bases de Datos H2
echo ========================================
echo.
echo Este script eliminara TODAS las bases de datos.
echo Los IDs comenzaran desde 1 nuevamente.
echo.

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0limpiar-bases-datos.ps1"

endlocal
