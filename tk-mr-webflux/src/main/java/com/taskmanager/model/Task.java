package com.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    public enum TaskStatus {
        TODO, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    public enum TaskPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
}
