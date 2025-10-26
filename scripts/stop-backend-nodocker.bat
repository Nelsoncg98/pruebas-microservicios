@echo off
setlocal ENABLEDELAYEDEXPANSION

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0stop-backend-nodocker.ps1"

endlocal
