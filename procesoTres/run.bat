@echo off
REM run.bat — inicia EurekaServerN, ms-paciente, ms-horariomedico y ms-cita en ventanas separadas

REM Obtener carpeta del script y la carpeta raíz del workspace (una arriba)
set SCRIPT_DIR=%~dp0
for %%I in ("%SCRIPT_DIR%..") do set ROOT_DIR=%%~fI

echo Starting services from %ROOT_DIR%
REM Este batch delega en el PowerShell script existente `run.ps1`.
REM Abre una nueva ventana de PowerShell y ejecuta el script con ExecutionPolicy Bypass.

if not exist "%SCRIPT_DIR%run.ps1" (
	echo ERROR: no se encontró %SCRIPT_DIR%run.ps1
	pause
	exit /b 1
)

echo Launching PowerShell script: %SCRIPT_DIR%run.ps1
start "Run-Proceso3-PS1" powershell -NoExit -ExecutionPolicy Bypass -File "%SCRIPT_DIR%run.ps1"

echo PowerShell window started to run run.ps1. Close that window when finished.
pause
