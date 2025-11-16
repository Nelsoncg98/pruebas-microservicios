@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script PowerShell del Proceso 3
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0proceso3.ps1"

endlocal
