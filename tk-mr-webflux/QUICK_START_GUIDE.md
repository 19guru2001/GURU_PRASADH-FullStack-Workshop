# Quick Start Guide - Task Manager WebFlux

## ⚡ Get Started in 3 Steps

### Step 1: Prerequisites
- **Java 17+** installed ([Download here](https://adoptium.net/))
- **Maven 3.6+** installed ([Download here](https://maven.apache.org/download.cgi))

### Step 2: Run the Application

**Option A: Using the start script (Recommended)**
```bash
# On Unix/Linux/Mac
./start.sh

# On Windows
start.bat
```

**Option B: Using Maven directly**
```bash
mvn spring-boot:run
```

### Step 3: Test the API
Open your browser or use curl:
```bash
# View all tasks
curl http://localhost:8080/api/tasks

# Or open in browser
http://localhost:8080/api/tasks
```

---

## 🎯 First API Calls

### 1. Get All Tasks (Pre-loaded with 5 sample tasks)
```bash
curl http://localhost:8080/api/tasks
```

### 2. Create Your First Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Task",
    "description": "Learning Spring WebFlux",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2026-02-01T17:00:00"
  }'
```

### 3. Get Task by ID
```bash
curl http://localhost:8080/api/tasks/1
```

### 4. Filter by Status
```bash
curl http://localhost:8080/api/tasks/status/TODO
```

### 5. Search Tasks
```bash
curl http://localhost:8080/api/tasks/search?keyword=spring
```

---

## 📋 Valid Values

### Status
- `TODO`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`

### Priority
- `LOW`
- `MEDIUM`
- `HIGH`
- `URGENT`

---

## 📚 Full Documentation

- **README.md** - Complete project documentation
- **API_EXAMPLES.md** - Detailed API examples with curl, JavaScript, Python
- **PROJECT_STRUCTURE.md** - Architecture and code walkthrough

---

## 🐛 Troubleshooting

### Port 8080 already in use?
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Maven not found?
Download and install Maven from: https://maven.apache.org/download.cgi

### Java not found?
Download and install Java 17+ from: https://adoptium.net/

---

## 🚀 What's Included?

✅ Complete Task Manager REST API  
✅ 10+ API endpoints  
✅ 5 pre-loaded sample tasks  
✅ Spring WebFlux (Reactive)  
✅ R2DBC + H2 Database  
✅ Error handling & validation  
✅ Comprehensive documentation  
✅ Quick start scripts  

---

## 📖 Learn More

**Key Files to Explore:**
1. `TaskController.java` - REST API endpoints
2. `TaskService.java` - Business logic
3. `TaskRepository.java` - Database queries
4. `Task.java` - Data model
5. `schema.sql` - Database structure

**Concepts Demonstrated:**
- Reactive programming with Mono/Flux
- Non-blocking I/O
- Layered architecture
- REST API design
- Exception handling

---

## 💡 Next Steps

1. ✅ Run the application
2. ✅ Test the API endpoints
3. ✅ Explore the code structure
4. ✅ Modify and extend
5. ✅ Build your own features!

---

**Need Help?** Check the full README.md or API_EXAMPLES.md files!

**Happy Coding! 🎉**
