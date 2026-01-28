@echo off
echo =========================================
echo Task Manager WebFlux - Quick Start
echo =========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Maven is not installed. Please install Maven first.
    echo    Visit: https://maven.apache.org/install.html
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)

echo √ Maven found
echo √ Java found
echo.

REM Build the project
echo Building the project...
call mvn clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo √ Build successful!
    echo.
    echo Starting the application...
    echo.
    echo Application will be available at: http://localhost:8080
    echo API Base URL: http://localhost:8080/api/tasks
    echo.
    echo Press Ctrl+C to stop the application
    echo.
    
    REM Run the application
    call mvn spring-boot:run
) else (
    echo.
    echo X Build failed. Please check the errors above.
    pause
    exit /b 1
)
