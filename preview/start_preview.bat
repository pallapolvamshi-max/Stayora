@echo off
title Stayora Landing Page & Mobile App Preview
cls
echo ========================================================
echo   STAYORA - STUDENT ACCOMMODATION & ROOMMATE FINDER
echo   Interactive Landing Page (React + Tailwind + GSAP)
echo ========================================================
echo.
echo [1] Launching Stayora Landing Page at http://localhost:8085 ...
start http://localhost:8085
echo [2] Starting local preview server on Port 8085 ...
powershell -ExecutionPolicy Bypass -File "%~dp0start_server.ps1" -Port 8085
pause
