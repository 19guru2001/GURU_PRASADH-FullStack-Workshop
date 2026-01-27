# Task Manager - Spring Core (Stage 3)

A console-based task management application demonstrating **Spring Framework's Dependency Injection** and **Inversion of Control (IoC)**.

## 📋 What This Project Demonstrates

This project showcases Spring Core's fundamental concepts:

- **Dependency Injection (DI)** using `@Autowired`
- **Component Scanning** with `@ComponentScan`
- **Stereotype Annotations** (`@Component`, `@Service`, `@Repository`)
- **Java-based Configuration** with `@Configuration`
- **Property Injection** using `@Value` and `@PropertySource`
- **ApplicationContext** for bean management

## 🏗️ Project Structure

```
task-manager-spring/
├── src/
│   ├── main/
│   │   ├── java/com/example/taskmanager/
│   │   │   ├── Main.java                      # Application entry point
│   │   │   ├── config/
│   │   │   │   └── AppConfig.java             # Spring configuration
│   │   │   ├── model/
│   │   │   │   ├── Task.java                  # Domain model
│   │   │   │   └── TaskStatus.java            # Status enum
│   │   │   ├── repository/
│   │   │   │   ├── ConnectionManager.java     # DB connection (@Component)
│   │   │   │   └── TaskRepository.java        # Data access (@Repository)
│   │   │   ├── service/
│   │   │   │   └── TaskService.java           # Business logic (@Service)
│   │   │   └── ui/
│   │   │       └── ConsoleUI.java             # User interface (@Component)
│   │   └── resources/
│   │       └── db.properties                  # Database configuration
├── pom.xml                                     # Maven dependencies
└── database-schema.sql                         # Database setup script
```

## 🔑 Key Spring Concepts

### Before Spring (Manual Wiring)

```java
// You create everything manually in the correct order
ConnectionManager connectionManager = new ConnectionManager();
TaskRepository taskRepository = new TaskRepository(connectionManager);
TaskService taskService = new TaskService(taskRepository);
ConsoleUI ui = new ConsoleUI(taskService);
ui.run();
```

### After Spring (Automatic DI)

```java
// Spring creates and wires everything automatically
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
ConsoleUI ui = context.getBean(ConsoleUI.class);
ui.run();
```

### Spring Annotations Used

| Annotation | Purpose | Used In |
|------------|---------|---------|
| `@Configuration` | Marks class as Spring configuration | `AppConfig` |
| `@ComponentScan` | Tells Spring where to find components | `AppConfig` |
| `@PropertySource` | Loads external properties | `AppConfig` |
| `@Component` | Generic Spring-managed bean | `ConnectionManager`, `ConsoleUI` |
| `@Service` | Business logic layer component | `TaskService` |
| `@Repository` | Data access layer component | `TaskRepository` |
| `@Autowired` | Injects dependencies automatically | All component constructors |
| `@Value` | Injects property values | `ConnectionManager` |

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **MySQL 8.0+**

### Setup Instructions

#### 1. Clone/Download the Project

```bash
# Navigate to the project directory
cd task-manager-spring
```

#### 2. Setup Database

```bash
# Login to MySQL
mysql -u root -p

# Run the schema script
source database-schema.sql

# Or copy-paste the SQL commands from database-schema.sql
```

#### 3. Configure Database Connection

Edit `src/main/resources/db.properties`:

```properties
db.host=localhost
db.port=3306
db.name=taskmanager
db.username=root
db.password=YOUR_PASSWORD_HERE
```

#### 4. Build the Project

```bash
mvn clean compile
```

#### 5. Run the Application

```bash
mvn exec:java
```

## 📖 Usage

When you run the application, you'll see:

```
Starting Task Manager with Spring...

ConnectionManager initialized by Spring
TaskRepository initialized by Spring
TaskService initialized by Spring
ConsoleUI initialized by Spring

=== Task Manager ===
Spring DI is managing all dependencies!

=== Menu ===
1. Create Task
2. List All Tasks
3. View Task by ID
4. Update Task Status
5. Update Task Details
6. Delete Task
7. List Tasks by Status
8. Start Task
9. Complete Task
0. Exit
```

### Sample Workflow

1. **Create a new task** (Option 1)
   - Title: "Learn Spring Boot"
   - Description: "Study Spring Boot fundamentals"

2. **List all tasks** (Option 2)
   - View all tasks with their status

3. **Start a task** (Option 8)
   - Changes status to IN_PROGRESS

4. **Complete a task** (Option 9)
   - Changes status to COMPLETED

## 🔍 How Spring DI Works in This Project

### Spring Container Lifecycle

```
1. Main creates ApplicationContext
         ↓
2. Spring reads @Configuration (AppConfig)
         ↓
3. @ComponentScan finds all @Component classes:
   - ConnectionManager
   - TaskRepository
   - TaskService
   - ConsoleUI
         ↓
4. Spring analyzes dependencies
         ↓
5. Spring creates beans in correct order
         ↓
6. Spring injects dependencies via @Autowired
         ↓
7. Application ready! 🎉
```

### Dependency Graph

```
ConsoleUI
    └── TaskService
            └── TaskRepository
                    └── ConnectionManager
```

Spring automatically figures out this creation order!

## 🎯 Benefits of Spring DI

| Problem (Manual Wiring) | Solution (Spring DI) |
|------------------------|----------------------|
| Manual creation order | Spring figures it out automatically |
| Changes ripple through Main | Just add `@Component` |
| Hard to test | Inject mock beans easily |
| Tight coupling | Loose coupling via interfaces |
| Managing object lifecycle | Spring handles it |

## 🧪 Exercises to Try

1. **Add a New Service**
   - Create `TaskStatisticsService` that depends on `TaskRepository`
   - Add `@Service` annotation
   - Notice: No changes needed in `Main.java`!

2. **Understand Scanning**
   - Remove `@Service` from `TaskService`
   - Run the app - What error do you get?

3. **Constructor vs Field Injection**
   - Try `@Autowired` on a field instead of constructor
   - Compare the approaches

4. **Bean Scopes**
   - Add `@Scope("prototype")` to a component
   - Observe the behavior change

## 📦 Dependencies

From `pom.xml`:

```xml
<dependencies>
    <!-- Spring Context - Core DI Container -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.3</version>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

## 🐛 Common Issues

### Issue: "No bean found" error

**Cause**: Component not scanned or missing annotation

**Solution**: Ensure class is in `com.example.taskmanager` package and has `@Component`, `@Service`, or `@Repository`

### Issue: Database connection failure

**Cause**: Incorrect credentials or MySQL not running

**Solution**: 
- Check `db.properties` settings
- Verify MySQL is running: `systemctl status mysql`
- Test connection: `mysql -u root -p`

### Issue: Maven build fails

**Cause**: Wrong Java version or Maven not installed

**Solution**:
- Verify Java 17+: `java -version`
- Verify Maven: `mvn -version`

## 📚 Learning Resources

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Core Basics](https://spring.io/guides/gs/spring-boot/)
- [Dependency Injection Patterns](https://martinfowler.com/articles/injection.html)

## 🔄 Next Steps (Future Stages)

- **Stage 4**: Spring JDBC Template (eliminate JDBC boilerplate)
- **Stage 5**: Spring Data JPA (automatic repository implementation)
- **Stage 6**: Spring Boot (auto-configuration and embedded server)
- **Stage 7**: RESTful API (web layer with Spring MVC)

## 📝 License

This is an educational project for learning Spring Framework.

## 👨‍💻 Author

Created as a demonstration of Spring Core's Dependency Injection capabilities.

---

**Happy Learning! 🚀**
