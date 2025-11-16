@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script PowerShell del Proceso 4
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0proceso4.ps1"

endlocal
