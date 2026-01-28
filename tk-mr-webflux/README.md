# Task Manager - Spring WebFlux

A reactive task management REST API built with Spring WebFlux, R2DBC, and H2 database.

## Features

- ✅ Fully reactive, non-blocking REST API
- ✅ CRUD operations for tasks
- ✅ Filter tasks by status and priority
- ✅ Search tasks by keyword
- ✅ Get overdue tasks
- ✅ Sort tasks by due date
- ✅ Global exception handling
- ✅ Input validation
- ✅ In-memory H2 database with sample data

## Technology Stack

- **Spring Boot 3.2.0**
- **Spring WebFlux** - Reactive web framework
- **Spring Data R2DBC** - Reactive database access
- **H2 Database** - In-memory database
- **Lombok** - Reduce boilerplate code
- **Maven** - Build tool

## Project Structure

```
task-manager-webflux/
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── config/
│   │   │   │   └── DatabaseConfig.java
│   │   │   ├── controller/
│   │   │   │   └── TaskController.java
│   │   │   ├── dto/
│   │   │   │   ├── TaskDTO.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── TaskNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── mapper/
│   │   │   │   └── TaskMapper.java
│   │   │   ├── model/
│   │   │   │   └── Task.java
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.java
│   │   │   ├── service/
│   │   │   │   ├── TaskService.java
│   │   │   │   └── impl/
│   │   │   │       └── TaskServiceImpl.java
│   │   │   └── TaskManagerWebFluxApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/ (optional)
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation & Running

1. **Clone or extract the project**

2. **Navigate to project directory**
   ```bash
   cd task-manager-webflux
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or run the JAR file:
   ```bash
   java -jar target/task-manager-webflux-1.0.0.jar
   ```

5. **Application will start on**: `http://localhost:8080`

## API Endpoints

### Task Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |

### Filter & Search Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks/status/{status}` | Get tasks by status |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority |
| GET | `/api/tasks/search?keyword={keyword}` | Search tasks |
| GET | `/api/tasks/sorted/due-date` | Get tasks sorted by due date |
| GET | `/api/tasks/overdue` | Get overdue tasks |

### Status Values
- `TODO`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`

### Priority Values
- `LOW`
- `MEDIUM`
- `HIGH`
- `URGENT`

## Example API Requests

### 1. Create a Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Write documentation",
    "description": "Create comprehensive API documentation",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2026-02-01T23:59:59"
  }'
```

### 2. Get All Tasks

```bash
curl http://localhost:8080/api/tasks
```

### 3. Get Task by ID

```bash
curl http://localhost:8080/api/tasks/1
```

### 4. Update Task

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Write documentation",
    "description": "Create comprehensive API documentation - Updated",
    "status": "IN_PROGRESS",
    "priority": "HIGH",
    "dueDate": "2026-02-01T23:59:59"
  }'
```

### 5. Delete Task

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

### 6. Get Tasks by Status

```bash
curl http://localhost:8080/api/tasks/status/TODO
```

### 7. Get Tasks by Priority

```bash
curl http://localhost:8080/api/tasks/priority/HIGH
```

### 8. Search Tasks

```bash
curl http://localhost:8080/api/tasks/search?keyword=spring
```

### 9. Get Overdue Tasks

```bash
curl http://localhost:8080/api/tasks/overdue
```

## Sample Data

The application comes with 5 pre-loaded sample tasks:

1. Complete Spring WebFlux Tutorial (IN_PROGRESS, HIGH)
2. Setup CI/CD Pipeline (TODO, MEDIUM)
3. Code Review (TODO, HIGH)
4. Database Optimization (IN_PROGRESS, MEDIUM)
5. Write Unit Tests (COMPLETED, LOW)

## Testing with Postman

Import the following into Postman:

1. **Base URL**: `http://localhost:8080/api/tasks`
2. Create requests for each endpoint listed above
3. Use the sample JSON payloads provided

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server port
server.port=8080

# Database configuration
spring.r2dbc.url=r2dbc:h2:mem:///taskdb
spring.r2dbc.username=sa
spring.r2dbc.password=

# Logging levels
logging.level.com.taskmanager=DEBUG
```

## Key Reactive Concepts Used

### Mono vs Flux
- **Mono<T>**: Returns 0 or 1 element (single task)
- **Flux<T>**: Returns 0 to N elements (multiple tasks)

### Example from Controller:
```java
// Single task - returns Mono
@GetMapping("/{id}")
public Mono<ResponseEntity<TaskDTO>> getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id).map(ResponseEntity::ok);
}

// Multiple tasks - returns Flux
@GetMapping
public Flux<TaskDTO> getAllTasks() {
    return taskService.getAllTasks();
}
```

## Error Handling

The application includes comprehensive error handling:

- **404 Not Found**: When task doesn't exist
- **400 Bad Request**: Validation errors
- **500 Internal Server Error**: Unexpected errors

Example error response:
```json
{
  "error": "NOT_FOUND",
  "message": "Task not found with id: 999",
  "timestamp": "2026-01-27T10:30:00",
  "path": null
}
```

## Advantages of WebFlux

1. **High Concurrency**: Handles many concurrent requests with fewer threads
2. **Non-blocking I/O**: Doesn't block threads while waiting for database/network
3. **Backpressure Support**: Handles data streams efficiently
4. **Scalability**: Better resource utilization

## Next Steps / Enhancements

- [ ] Add authentication & authorization (Spring Security)
- [ ] Implement pagination
- [ ] Add unit and integration tests
- [ ] Use PostgreSQL with R2DBC instead of H2
- [ ] Add WebSocket support for real-time updates
- [ ] Implement task categories/tags
- [ ] Add task comments and attachments
- [ ] Create a frontend (React/Angular)

## Troubleshooting

### Port 8080 already in use
Change the port in `application.properties`:
```properties
server.port=8081
```

### Database initialization issues
Check that `schema.sql` and `data.sql` are in `src/main/resources/`

## License

This project is created for educational purposes.

## Author

Task Manager WebFlux - Reactive Spring Boot Application
