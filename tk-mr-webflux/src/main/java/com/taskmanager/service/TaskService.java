package com.taskmanager.service;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    
    Mono<TaskDTO> createTask(TaskDTO taskDTO);
    
    Mono<TaskDTO> getTaskById(Long id);
    
    Flux<TaskDTO> getAllTasks();
    
    Mono<TaskDTO> updateTask(Long id, TaskDTO taskDTO);
    
    Mono<Void> deleteTask(Long id);
    
    Flux<TaskDTO> getTasksByStatus(Task.TaskStatus status);
    
    Flux<TaskDTO> getTasksByPriority(Task.TaskPriority priority);
    
    Flux<TaskDTO> searchTasks(String keyword);
    
    Flux<TaskDTO> getTasksSortedByDueDate();
    
    Flux<TaskDTO> getOverdueTasks();
}
