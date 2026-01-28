#!/bin/bash

echo "========================================="
echo "Task Manager WebFlux - Quick Start"
echo "========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven is not installed. Please install Maven first."
    echo "   Visit: https://maven.apache.org/install.html"
    exit 1
fi

# Check Java version
if ! command -v java &> /dev/null
then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

echo "✅ Maven found: $(mvn -version | head -n 1)"
echo "✅ Java found: $(java -version 2>&1 | head -n 1)"
echo ""

# Build the project
echo "📦 Building the project..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting the application..."
    echo ""
    echo "📍 Application will be available at: http://localhost:8080"
    echo "📍 API Base URL: http://localhost:8080/api/tasks"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo ""
    
    # Run the application
    mvn spring-boot:run
else
    echo ""
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
