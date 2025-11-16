@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Wrapper para lanzar el script PowerShell de Eureka
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0eureka.ps1"

endlocal
