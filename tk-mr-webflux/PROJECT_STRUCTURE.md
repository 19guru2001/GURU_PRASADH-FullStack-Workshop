# Task Manager WebFlux - Project Structure

## Complete Directory Tree

```
task-manager-webflux/
│
├── pom.xml                          # Maven configuration with dependencies
├── README.md                         # Project documentation
├── API_EXAMPLES.md                   # API usage examples
├── PROJECT_STRUCTURE.md              # This file
├── .gitignore                        # Git ignore file
├── start.sh                          # Quick start script (Unix/Linux/Mac)
├── start.bat                         # Quick start script (Windows)
│
└── src/
    └── main/
        ├── java/com/taskmanager/
        │   │
        │   ├── TaskManagerWebFluxApplication.java    # Main Spring Boot application
        │   │
        │   ├── config/
        │   │   └── DatabaseConfig.java               # R2DBC database configuration
        │   │
        │   ├── controller/
        │   │   └── TaskController.java               # REST API endpoints
        │   │
        │   ├── dto/
        │   │   ├── TaskDTO.java                      # Data Transfer Object for Task
        │   │   └── ErrorResponse.java                # Error response structure
        │   │
        │   ├── exception/
        │   │   ├── TaskNotFoundException.java        # Custom exception
        │   │   └── GlobalExceptionHandler.java       # Global error handler
        │   │
        │   ├── mapper/
        │   │   └── TaskMapper.java                   # Entity ↔ DTO mapper
        │   │
        │   ├── model/
        │   │   └── Task.java                         # Task entity/domain model
        │   │
        │   ├── repository/
        │   │   └── TaskRepository.java               # R2DBC repository interface
        │   │
        │   └── service/
        │       ├── TaskService.java                  # Service interface
        │       └── impl/
        │           └── TaskServiceImpl.java          # Service implementation
        │
        └── resources/
            ├── application.properties                # Application configuration
            ├── schema.sql                            # Database schema
            └── data.sql                              # Sample data
```

---

## Layer-by-Layer Explanation

### 1. **Configuration Layer** (`config/`)

**DatabaseConfig.java**
- Configures R2DBC (Reactive Database Connectivity)
- Initializes database with schema and sample data
- Sets up connection factory

```java
@Configuration
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    // Database initialization logic
}
```

---

### 2. **Controller Layer** (`controller/`)

**TaskController.java**
- Handles HTTP requests
- Defines REST API endpoints
- Returns `Mono<T>` for single results
- Returns `Flux<T>` for multiple results

**Key Endpoints:**
```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    POST   /api/tasks              → Create task
    GET    /api/tasks              → Get all tasks
    GET    /api/tasks/{id}         → Get task by ID
    PUT    /api/tasks/{id}         → Update task
    DELETE /api/tasks/{id}         → Delete task
    GET    /api/tasks/status/{status}
    GET    /api/tasks/priority/{priority}
    GET    /api/tasks/search?keyword=xxx
}
```

---

### 3. **Service Layer** (`service/`)

**TaskService.java** (Interface)
- Defines business logic contracts
- Declares reactive methods returning Mono/Flux

**TaskServiceImpl.java** (Implementation)
- Implements business logic
- Interacts with repository
- Handles data transformation
- Includes logging

**Key Operations:**
```java
@Service
public class TaskServiceImpl implements TaskService {
    Mono<TaskDTO>  createTask(TaskDTO taskDTO)
    Mono<TaskDTO>  getTaskById(Long id)
    Flux<TaskDTO>  getAllTasks()
    Mono<TaskDTO>  updateTask(Long id, TaskDTO taskDTO)
    Mono<Void>     deleteTask(Long id)
    // ... filter and search methods
}
```

---

### 4. **Repository Layer** (`repository/`)

**TaskRepository.java**
- Extends `ReactiveCrudRepository`
- Provides reactive CRUD operations
- Custom query methods using R2DBC

```java
@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    Flux<Task> findByStatus(Task.TaskStatus status);
    Flux<Task> findByPriority(Task.TaskPriority priority);
    
    @Query("SELECT * FROM tasks WHERE ...")
    Flux<Task> searchByKeyword(String keyword);
}
```

---

### 5. **Model/Entity Layer** (`model/`)

**Task.java**
- Represents database table
- Domain model with enums for Status and Priority
- Uses Spring Data annotations

```java
@Data
@Table("tasks")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    
    enum TaskStatus { TODO, IN_PROGRESS, COMPLETED, CANCELLED }
    enum TaskPriority { LOW, MEDIUM, HIGH, URGENT }
}
```

---

### 6. **DTO Layer** (`dto/`)

**TaskDTO.java**
- Data Transfer Object for API requests/responses
- Includes validation annotations
- Separates API contract from database model

**ErrorResponse.java**
- Standardized error response format
- Used by exception handler

---

### 7. **Exception Handling** (`exception/`)

**TaskNotFoundException.java**
- Custom exception for missing tasks

**GlobalExceptionHandler.java**
- Catches all exceptions globally
- Returns standardized error responses
- Handles validation errors

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    → Returns 404 with error details
    
    @ExceptionHandler(WebExchangeBindException.class)
    → Returns 400 for validation errors
}
```

---

### 8. **Mapper Layer** (`mapper/`)

**TaskMapper.java**
- Converts between Task (entity) and TaskDTO
- Separates database model from API model

```java
@Component
public class TaskMapper {
    Task toEntity(TaskDTO dto)
    TaskDTO toDTO(Task task)
}
```

---

## Request Flow

```
1. HTTP Request → TaskController
         ↓
2. Controller validates and calls → TaskService
         ↓
3. Service applies business logic → TaskRepository
         ↓
4. Repository queries database (R2DBC/H2)
         ↓
5. Task entity returned → Service
         ↓
6. Service maps to TaskDTO → Controller
         ↓
7. Controller returns HTTP Response
```

---

## Reactive Flow Example

### Creating a Task:

```java
// Controller receives request
@PostMapping
public Mono<ResponseEntity<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO) {
    return taskService.createTask(taskDTO)           // Returns Mono<TaskDTO>
        .map(created -> ResponseEntity              // Transform Mono
            .created(URI.create("/api/tasks/" + created.getId()))
            .body(created));
}

// Service processes
public Mono<TaskDTO> createTask(TaskDTO taskDTO) {
    Task task = taskMapper.toEntity(taskDTO);
    task.setCreatedAt(LocalDateTime.now());
    
    return taskRepository.save(task)                // Returns Mono<Task>
        .map(taskMapper::toDTO);                    // Convert to Mono<TaskDTO>
}

// Repository saves to database
public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    // save() method returns Mono<Task>
}
```

---

## Key Reactive Concepts

### Mono vs Flux

| Type | Cardinality | Use Case | Example |
|------|-------------|----------|---------|
| `Mono<T>` | 0 or 1 element | Single result | Get task by ID |
| `Flux<T>` | 0 to N elements | Multiple results | Get all tasks |

### Non-Blocking I/O

```java
// Traditional blocking (Spring MVC)
public User getUser(Long id) {
    return repository.findById(id).orElseThrow();  // Blocks thread
}

// Reactive non-blocking (Spring WebFlux)
public Mono<User> getUser(Long id) {
    return repository.findById(id)                  // Returns immediately
        .switchIfEmpty(Mono.error(...));           // No thread blocking
}
```

---

## Database Schema

**tasks** table:
```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP
);
```

---

## Technologies Used

| Technology | Purpose | Layer |
|------------|---------|-------|
| **Spring WebFlux** | Reactive web framework | Controller |
| **Spring Data R2DBC** | Reactive database access | Repository |
| **H2 Database** | In-memory database | Data |
| **Lombok** | Reduce boilerplate | All |
| **Jakarta Validation** | Input validation | DTO |
| **Maven** | Build & dependency management | Build |

---

## Configuration Files

### application.properties
```properties
server.port=8080
spring.r2dbc.url=r2dbc:h2:mem:///taskdb
spring.r2dbc.username=sa
spring.r2dbc.password=
```

### pom.xml
Key dependencies:
- `spring-boot-starter-webflux`
- `spring-boot-starter-data-r2dbc`
- `r2dbc-h2`
- `lombok`
- `spring-boot-starter-validation`

---

## Running the Application

### Option 1: Using start scripts
```bash
# Unix/Linux/Mac
./start.sh

# Windows
start.bat
```

### Option 2: Using Maven
```bash
mvn spring-boot:run
```

### Option 3: Using JAR
```bash
mvn clean package
java -jar target/task-manager-webflux-1.0.0.jar
```

---

## Testing the API

```bash
# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","status":"TODO","priority":"HIGH"}'

# Get all tasks
curl http://localhost:8080/api/tasks

# Get by status
curl http://localhost:8080/api/tasks/status/TODO
```

---

## Summary

This project demonstrates:
- ✅ Reactive programming with Spring WebFlux
- ✅ Non-blocking database access with R2DBC
- ✅ Clean layered architecture
- ✅ Proper separation of concerns
- ✅ Global exception handling
- ✅ Input validation
- ✅ RESTful API design
- ✅ Reactive types (Mono & Flux)
