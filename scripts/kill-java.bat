@echo off
echo ====================================================
echo  CERRANDO PROCESOS JAVA HUERFANOS (ZOMBIES)
echo ====================================================
echo.
echo Esto detendra TODOS los procesos 'java.exe' y 'javaw.exe' en tu maquina.
echo Asegurate de guardar tu trabajo en otros programas Java (como IDEs si corren sobre Java, aunque VS Code suele estar bien).
echo.
pause
taskkill /F /IM java.exe
taskkill /F /IM javaw.exe
echo.
echo ====================================================
echo  LIMPIEZA COMPLETADA.
echo  Ahora puedes ejecutar run-proceso6.bat de nuevo.
echo ====================================================
pause
