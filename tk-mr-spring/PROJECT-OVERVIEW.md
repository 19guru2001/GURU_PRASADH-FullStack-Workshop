# Task Manager - Spring DI Project Overview

## 🎯 Project Summary

A fully functional **console-based Task Manager** demonstrating Spring Framework's core Dependency Injection capabilities. This project shows how Spring eliminates manual dependency wiring and manages object lifecycles automatically.

## 📦 What's Included

### Complete Source Code
- ✅ All Java classes with Spring annotations
- ✅ Maven configuration (pom.xml)
- ✅ Database properties file
- ✅ SQL schema script

### Documentation
- ✅ Comprehensive README.md
- ✅ Quick Start Guide (QUICKSTART.md)
- ✅ Inline code comments explaining Spring concepts

### Project Structure
```
task-manager-spring/
├── src/main/java/com/example/taskmanager/
│   ├── Main.java                    # Entry point with Spring context
│   ├── config/
│   │   └── AppConfig.java           # @Configuration for component scanning
│   ├── model/
│   │   ├── Task.java                # Domain entity
│   │   └── TaskStatus.java          # Enum for task states
│   ├── repository/
│   │   ├── ConnectionManager.java   # @Component for DB connections
│   │   └── TaskRepository.java      # @Repository for data access
│   ├── service/
│   │   └── TaskService.java         # @Service for business logic
│   └── ui/
│       └── ConsoleUI.java           # @Component for user interface
├── src/main/resources/
│   └── db.properties                # Database configuration
├── pom.xml                          # Maven dependencies
├── database-schema.sql              # MySQL setup script
├── README.md                        # Full documentation
└── QUICKSTART.md                    # 5-minute setup guide
```

## 🔑 Spring Concepts Demonstrated

### 1. Dependency Injection
Every component receives its dependencies automatically via constructor injection:

```java
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    
    @Autowired  // Spring injects TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
```

### 2. Component Scanning
Spring automatically discovers and registers beans:

```java
@Configuration
@ComponentScan("com.example.taskmanager")
public class AppConfig {
    // Spring finds all @Component, @Service, @Repository classes
}
```

### 3. Property Injection
External configuration loaded from properties file:

```java
@Component
public class ConnectionManager {
    public ConnectionManager(
        @Value("${db.host}") String host,
        @Value("${db.port}") String port,
        // ... Spring injects from db.properties
    ) { }
}
```

### 4. Stereotype Annotations
Clear separation of concerns:
- `@Repository` - Data access layer
- `@Service` - Business logic layer
- `@Component` - Generic components

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Quick Setup (5 minutes)

1. **Setup Database**
   ```bash
   mysql -u root -p < database-schema.sql
   ```

2. **Configure Connection**
   Edit `src/main/resources/db.properties` with your MySQL password

3. **Run Application**
   ```bash
   mvn clean compile
   mvn exec:java
   ```

## 💡 Key Features

### Task Management Operations
- ✅ Create new tasks
- ✅ List all tasks
- ✅ View task by ID
- ✅ Update task status (PENDING → IN_PROGRESS → COMPLETED)
- ✅ Update task details
- ✅ Delete tasks
- ✅ Filter tasks by status

### Spring Features
- ✅ Automatic dependency wiring
- ✅ Singleton bean management
- ✅ Constructor injection
- ✅ Property-based configuration
- ✅ Component lifecycle management

## 📊 Dependency Graph

Spring automatically resolves this dependency chain:

```
Main
  └── ApplicationContext
        └── ConsoleUI (@Component)
              └── TaskService (@Service)
                    └── TaskRepository (@Repository)
                          └── ConnectionManager (@Component)
```

**No manual wiring needed!** Spring figures out the correct creation order.

## 🎓 Learning Value

This project teaches:

1. **IoC (Inversion of Control)**: Framework controls object creation
2. **DI (Dependency Injection)**: Dependencies injected, not created manually
3. **Spring Annotations**: How to mark and wire components
4. **Configuration Management**: External properties for flexibility
5. **Layered Architecture**: Proper separation of concerns

## 🔄 Comparison: Before vs After Spring

### Before (Manual Wiring)
```java
ConnectionManager cm = new ConnectionManager();
TaskRepository repo = new TaskRepository(cm);
TaskService service = new TaskService(repo);
ConsoleUI ui = new ConsoleUI(service);
ui.run();
```
**Problems**: Manual order, tight coupling, hard to test

### After (Spring DI)
```java
ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
ConsoleUI ui = ctx.getBean(ConsoleUI.class);
ui.run();
```
**Benefits**: Automatic wiring, loose coupling, easy testing

## 🛠️ Technologies Used

- **Spring Context 6.1.3** - Core DI container
- **MySQL 8.0** - Database
- **Maven** - Build tool
- **Java 17** - Programming language

## 📚 Next Steps for Learning

After mastering this project:

1. **Add Unit Tests** - Use Mockito with Spring
2. **Try Different Scopes** - Prototype, Request, Session
3. **Add AOP** - Cross-cutting concerns like logging
4. **Move to Spring Boot** - Eliminate more boilerplate
5. **Add REST API** - Spring MVC for web layer

## 🎯 Perfect For

- ✅ Learning Spring Core fundamentals
- ✅ Understanding dependency injection
- ✅ Preparing for Spring Boot
- ✅ Interview preparation
- ✅ Teaching Spring concepts
- ✅ Building on for your own projects

## 📖 Additional Resources

The project includes:
- **Detailed README**: Full explanations with diagrams
- **Quick Start Guide**: Get running in 5 minutes
- **Inline Comments**: Every annotation explained
- **SQL Schema**: Ready-to-run database setup

---

**Ready to dive into Spring Framework? Extract the zip and start learning! 🚀**
