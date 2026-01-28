# API Usage Examples

## Using cURL

### 1. Create a New Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete Project Documentation",
    "description": "Write comprehensive documentation for the project",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2026-02-15T17:00:00"
  }'
```

**Response:**
```json
{
  "id": 6,
  "title": "Complete Project Documentation",
  "description": "Write comprehensive documentation for the project",
  "status": "TODO",
  "priority": "HIGH",
  "createdAt": "2026-01-27T10:30:00",
  "updatedAt": "2026-01-27T10:30:00",
  "dueDate": "2026-02-15T17:00:00"
}
```

### 2. Get All Tasks

```bash
curl http://localhost:8080/api/tasks
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Complete Spring WebFlux Tutorial",
    "description": "Learn and implement Spring WebFlux with reactive programming",
    "status": "IN_PROGRESS",
    "priority": "HIGH",
    "createdAt": "2026-01-27T09:00:00",
    "updatedAt": "2026-01-27T09:00:00",
    "dueDate": "2026-02-15T23:59:59"
  },
  {
    "id": 2,
    "title": "Setup CI/CD Pipeline",
    "description": "Configure Jenkins for automated deployment",
    "status": "TODO",
    "priority": "MEDIUM",
    "createdAt": "2026-01-27T09:00:00",
    "updatedAt": "2026-01-27T09:00:00",
    "dueDate": "2026-02-10T17:00:00"
  }
]
```

### 3. Get Task by ID

```bash
curl http://localhost:8080/api/tasks/1
```

**Response:**
```json
{
  "id": 1,
  "title": "Complete Spring WebFlux Tutorial",
  "description": "Learn and implement Spring WebFlux with reactive programming",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "createdAt": "2026-01-27T09:00:00",
  "updatedAt": "2026-01-27T09:00:00",
  "dueDate": "2026-02-15T23:59:59"
}
```

### 4. Update Task

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete Spring WebFlux Tutorial",
    "description": "Learn and implement Spring WebFlux with reactive programming - 80% complete",
    "status": "IN_PROGRESS",
    "priority": "HIGH",
    "dueDate": "2026-02-15T23:59:59"
  }'
```

**Response:**
```json
{
  "id": 1,
  "title": "Complete Spring WebFlux Tutorial",
  "description": "Learn and implement Spring WebFlux with reactive programming - 80% complete",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "createdAt": "2026-01-27T09:00:00",
  "updatedAt": "2026-01-27T10:45:00",
  "dueDate": "2026-02-15T23:59:59"
}
```

### 5. Delete Task

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

**Response:** 204 No Content

### 6. Get Tasks by Status

```bash
# Get all TODO tasks
curl http://localhost:8080/api/tasks/status/TODO

# Get all IN_PROGRESS tasks
curl http://localhost:8080/api/tasks/status/IN_PROGRESS

# Get all COMPLETED tasks
curl http://localhost:8080/api/tasks/status/COMPLETED
```

### 7. Get Tasks by Priority

```bash
# Get all HIGH priority tasks
curl http://localhost:8080/api/tasks/priority/HIGH

# Get all MEDIUM priority tasks
curl http://localhost:8080/api/tasks/priority/MEDIUM

# Get all LOW priority tasks
curl http://localhost:8080/api/tasks/priority/LOW
```

### 8. Search Tasks

```bash
# Search for tasks containing "spring"
curl http://localhost:8080/api/tasks/search?keyword=spring

# Search for tasks containing "review"
curl "http://localhost:8080/api/tasks/search?keyword=review"
```

### 9. Get Tasks Sorted by Due Date

```bash
curl http://localhost:8080/api/tasks/sorted/due-date
```

### 10. Get Overdue Tasks

```bash
curl http://localhost:8080/api/tasks/overdue
```

---

## Using JavaScript/Fetch API

### 1. Create Task

```javascript
const createTask = async () => {
  const response = await fetch('http://localhost:8080/api/tasks', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      title: 'New Task',
      description: 'Task description',
      status: 'TODO',
      priority: 'HIGH',
      dueDate: '2026-02-15T17:00:00'
    })
  });
  
  const task = await response.json();
  console.log(task);
};
```

### 2. Get All Tasks

```javascript
const getAllTasks = async () => {
  const response = await fetch('http://localhost:8080/api/tasks');
  const tasks = await response.json();
  console.log(tasks);
};
```

### 3. Get Task by ID

```javascript
const getTaskById = async (id) => {
  const response = await fetch(`http://localhost:8080/api/tasks/${id}`);
  const task = await response.json();
  console.log(task);
};
```

### 4. Update Task

```javascript
const updateTask = async (id) => {
  const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      title: 'Updated Task',
      description: 'Updated description',
      status: 'IN_PROGRESS',
      priority: 'HIGH',
      dueDate: '2026-02-15T17:00:00'
    })
  });
  
  const task = await response.json();
  console.log(task);
};
```

### 5. Delete Task

```javascript
const deleteTask = async (id) => {
  const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
    method: 'DELETE'
  });
  
  console.log('Task deleted:', response.status === 204);
};
```

### 6. Search Tasks

```javascript
const searchTasks = async (keyword) => {
  const response = await fetch(
    `http://localhost:8080/api/tasks/search?keyword=${encodeURIComponent(keyword)}`
  );
  const tasks = await response.json();
  console.log(tasks);
};
```

---

## Using Python/Requests

### 1. Create Task

```python
import requests
import json

url = "http://localhost:8080/api/tasks"
payload = {
    "title": "New Python Task",
    "description": "Task created from Python",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2026-02-15T17:00:00"
}

response = requests.post(url, json=payload)
print(response.json())
```

### 2. Get All Tasks

```python
import requests

url = "http://localhost:8080/api/tasks"
response = requests.get(url)
tasks = response.json()

for task in tasks:
    print(f"ID: {task['id']}, Title: {task['title']}, Status: {task['status']}")
```

### 3. Update Task

```python
import requests

task_id = 1
url = f"http://localhost:8080/api/tasks/{task_id}"
payload = {
    "title": "Updated Task",
    "description": "Updated from Python",
    "status": "IN_PROGRESS",
    "priority": "HIGH",
    "dueDate": "2026-02-15T17:00:00"
}

response = requests.put(url, json=payload)
print(response.json())
```

### 4. Delete Task

```python
import requests

task_id = 1
url = f"http://localhost:8080/api/tasks/{task_id}"
response = requests.delete(url)
print(f"Status Code: {response.status_code}")
```

---

## Error Responses

### 404 Not Found

```json
{
  "error": "NOT_FOUND",
  "message": "Task not found with id: 999",
  "timestamp": "2026-01-27T10:30:00",
  "path": null
}
```

### 400 Bad Request (Validation Error)

```json
{
  "error": "VALIDATION_ERROR",
  "message": "title: Title is required, status: Status is required",
  "timestamp": "2026-01-27T10:30:00",
  "path": null
}
```

### 500 Internal Server Error

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred",
  "timestamp": "2026-01-27T10:30:00",
  "path": null
}
```

---

## Postman Collection

You can import this JSON into Postman:

```json
{
  "info": {
    "name": "Task Manager WebFlux API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Create Task",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"title\": \"New Task\",\n  \"description\": \"Task description\",\n  \"status\": \"TODO\",\n  \"priority\": \"HIGH\",\n  \"dueDate\": \"2026-02-15T17:00:00\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/tasks",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "tasks"]
        }
      }
    },
    {
      "name": "Get All Tasks",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/tasks",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "tasks"]
        }
      }
    }
  ]
}
```
