# Quick Start Guide

## 🚀 Get Running in 5 Minutes

### Step 1: Setup MySQL Database (2 minutes)

```bash
# Login to MySQL
mysql -u root -p

# Create database and table
CREATE DATABASE taskmanager;
USE taskmanager;

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

# Insert sample data (optional)
INSERT INTO tasks (title, description, status, created_at) VALUES
    ('Setup Development Environment', 'Install Java, Maven, MySQL', 'COMPLETED', NOW()),
    ('Learn Spring Core', 'Study dependency injection', 'IN_PROGRESS', NOW());

# Exit MySQL
exit
```

### Step 2: Configure Database Connection (30 seconds)

Edit `src/main/resources/db.properties`:

```properties
db.host=localhost
db.port=3306
db.name=taskmanager
db.username=root
db.password=YOUR_PASSWORD  # <-- CHANGE THIS
```

### Step 3: Build and Run (1 minute)

```bash
# Navigate to project directory
cd task-manager-spring

# Build the project
mvn clean compile

# Run the application
mvn exec:java
```

### Step 4: Use the Application

When the menu appears, try these commands:

1. Press `2` - List all tasks (see the sample data)
2. Press `1` - Create a new task
3. Press `8` - Start a task (change status to IN_PROGRESS)
4. Press `9` - Complete a task (change status to COMPLETED)
5. Press `0` - Exit

## ✅ Verify Spring DI is Working

When the app starts, you should see:

```
Starting Task Manager with Spring...

ConnectionManager initialized by Spring
TaskRepository initialized by Spring
TaskService initialized by Spring
ConsoleUI initialized by Spring
```

These messages prove Spring is creating and wiring all dependencies automatically! 🎉

## 🐛 Troubleshooting

**Problem**: Database connection error

```bash
# Make sure MySQL is running
sudo systemctl status mysql

# Test connection manually
mysql -u root -p -e "SHOW DATABASES;"
```

**Problem**: Maven not found

```bash
# Install Maven (Ubuntu/Debian)
sudo apt install maven

# Install Maven (macOS)
brew install maven
```

**Problem**: Java version error

```bash
# Check Java version (needs 17+)
java -version

# Install Java 17 (Ubuntu/Debian)
sudo apt install openjdk-17-jdk
```

## 📖 Next Steps

Once it's running:

1. Read the full `README.md` for detailed explanations
2. Explore the code in `src/main/java/com/example/taskmanager/`
3. Try the exercises in the README
4. Experiment with adding new features

Happy coding! 🚀
