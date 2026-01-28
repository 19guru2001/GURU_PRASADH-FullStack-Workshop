package com.taskmanager.repository;

import com.taskmanager.model.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    
    Flux<Task> findByStatus(Task.TaskStatus status);
    
    Flux<Task> findByPriority(Task.TaskPriority priority);
    
    Flux<Task> findByStatusAndPriority(Task.TaskStatus status, Task.TaskPriority priority);
    
    @Query("SELECT * FROM tasks WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Flux<Task> searchByKeyword(String keyword);
    
    @Query("SELECT * FROM tasks ORDER BY due_date ASC")
    Flux<Task> findAllOrderByDueDateAsc();
    
    @Query("SELECT * FROM tasks WHERE due_date < :date AND status != 'COMPLETED'")
    Flux<Task> findOverdueTasks(String date);
}
