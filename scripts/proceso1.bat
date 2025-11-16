@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script PowerShell del Proceso 1
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0proceso1.ps1"

endlocal
