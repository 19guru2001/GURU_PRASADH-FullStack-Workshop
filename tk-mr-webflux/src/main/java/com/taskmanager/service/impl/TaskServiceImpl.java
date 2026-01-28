package com.taskmanager.service.impl;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.mapper.TaskMapper;
import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    
    @Override
    public Mono<TaskDTO> createTask(TaskDTO taskDTO) {
        log.info("Creating new task: {}", taskDTO.getTitle());
        
        Task task = taskMapper.toEntity(taskDTO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        return taskRepository.save(task)
                .doOnSuccess(savedTask -> log.info("Task created with id: {}", savedTask.getId()))
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Mono<TaskDTO> getTaskById(Long id) {
        log.info("Fetching task with id: {}", id);
        
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Flux<TaskDTO> getAllTasks() {
        log.info("Fetching all tasks");
        
        return taskRepository.findAll()
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Mono<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        log.info("Updating task with id: {}", id);
        
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .flatMap(existingTask -> {
                    existingTask.setTitle(taskDTO.getTitle());
                    existingTask.setDescription(taskDTO.getDescription());
                    existingTask.setStatus(taskDTO.getStatus());
                    existingTask.setPriority(taskDTO.getPriority());
                    existingTask.setUpdatedAt(LocalDateTime.now());
                    existingTask.setDueDate(taskDTO.getDueDate());
                    
                    return taskRepository.save(existingTask);
                })
                .doOnSuccess(updatedTask -> log.info("Task updated: {}", updatedTask.getId()))
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Mono<Void> deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);
        
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .flatMap(task -> taskRepository.deleteById(id))
                .doOnSuccess(v -> log.info("Task deleted: {}", id));
    }
    
    @Override
    public Flux<TaskDTO> getTasksByStatus(Task.TaskStatus status) {
        log.info("Fetching tasks with status: {}", status);
        
        return taskRepository.findByStatus(status)
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Flux<TaskDTO> getTasksByPriority(Task.TaskPriority priority) {
        log.info("Fetching tasks with priority: {}", priority);
        
        return taskRepository.findByPriority(priority)
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Flux<TaskDTO> searchTasks(String keyword) {
        log.info("Searching tasks with keyword: {}", keyword);
        
        return taskRepository.searchByKeyword(keyword)
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Flux<TaskDTO> getTasksSortedByDueDate() {
        log.info("Fetching tasks sorted by due date");
        
        return taskRepository.findAllOrderByDueDateAsc()
                .map(taskMapper::toDTO);
    }
    
    @Override
    public Flux<TaskDTO> getOverdueTasks() {
        log.info("Fetching overdue tasks");
        String currentDate = LocalDateTime.now().toString();
        
        return taskRepository.findOverdueTasks(currentDate)
                .map(taskMapper::toDTO);
    }
}
