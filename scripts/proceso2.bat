@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script PowerShell del Proceso 2
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0proceso2.ps1"

endlocal
