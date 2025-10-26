@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Simple wrapper to launch the PowerShell runner
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0run-backend-nodocker.ps1"

endlocal
