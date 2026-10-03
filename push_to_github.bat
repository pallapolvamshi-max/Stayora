@echo off
cd /d "%~dp0"
echo ========================================================
echo   Pushing Stayora to https://github.com/pallapolvamshi-max/stayora
echo ========================================================
echo.
"C:\Program Files\Git\cmd\git.exe" branch -M main
"C:\Program Files\Git\cmd\git.exe" push -u origin main

if %errorlevel% neq 0 (
    echo.
    echo [!] Remote branch has existing content or conflict.
    echo [!] Overwriting remote with clean latest Stayora project...
    "C:\Program Files\Git\cmd\git.exe" push -u origin main --force
)

echo.
echo ========================================================
echo   Push Complete!
echo   View your repository at:
echo   https://github.com/pallapolvamshi-max/stayora
echo ========================================================
pause
