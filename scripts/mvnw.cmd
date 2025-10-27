@echo off
REM Simple launcher to call the PowerShell mvnw wrapper and forward arguments
set SCRIPT_DIR=%~dp0
powershell -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%mvnw.ps1" %*
exit /b %ERRORLEVEL%
