package com.taskmanager.controller;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    
    private final TaskService taskService;
    
    /**
     * Create a new task
     * POST /api/tasks
     */
    @PostMapping
    public Mono<ResponseEntity<TaskDTO>> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        log.info("REST request to create task: {}", taskDTO.getTitle());
        
        return taskService.createTask(taskDTO)
                .map(created -> ResponseEntity
                        .created(URI.create("/api/tasks/" + created.getId()))
                        .body(created));
    }
    
    /**
     * Get task by ID
     * GET /api/tasks/{id}
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskDTO>> getTaskById(@PathVariable Long id) {
        log.info("REST request to get task: {}", id);
        
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok);
    }
    
    /**
     * Get all tasks
     * GET /api/tasks
     */
    @GetMapping
    public Flux<TaskDTO> getAllTasks() {
        log.info("REST request to get all tasks");
        return taskService.getAllTasks();
    }
    
    /**
     * Update task
     * PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskDTO>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) {
        log.info("REST request to update task: {}", id);
        
        return taskService.updateTask(id, taskDTO)
                .map(ResponseEntity::ok);
    }
    
    /**
     * Delete task
     * DELETE /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTask(@PathVariable Long id) {
        log.info("REST request to delete task: {}", id);
        return taskService.deleteTask(id);
    }
    
    /**
     * Get tasks by status
     * GET /api/tasks/status/{status}
     */
    @GetMapping("/status/{status}")
    public Flux<TaskDTO> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        log.info("REST request to get tasks by status: {}", status);
        return taskService.getTasksByStatus(status);
    }
    
    /**
     * Get tasks by priority
     * GET /api/tasks/priority/{priority}
     */
    @GetMapping("/priority/{priority}")
    public Flux<TaskDTO> getTasksByPriority(@PathVariable Task.TaskPriority priority) {
        log.info("REST request to get tasks by priority: {}", priority);
        return taskService.getTasksByPriority(priority);
    }
    
    /**
     * Search tasks by keyword
     * GET /api/tasks/search?keyword=xxx
     */
    @GetMapping("/search")
    public Flux<TaskDTO> searchTasks(@RequestParam String keyword) {
        log.info("REST request to search tasks with keyword: {}", keyword);
        return taskService.searchTasks(keyword);
    }
    
    /**
     * Get tasks sorted by due date
     * GET /api/tasks/sorted/due-date
     */
    @GetMapping("/sorted/due-date")
    public Flux<TaskDTO> getTasksSortedByDueDate() {
        log.info("REST request to get tasks sorted by due date");
        return taskService.getTasksSortedByDueDate();
    }
    
    /**
     * Get overdue tasks
     * GET /api/tasks/overdue
     */
    @GetMapping("/overdue")
    public Flux<TaskDTO> getOverdueTasks() {
        log.info("REST request to get overdue tasks");
        return taskService.getOverdueTasks();
    }
}
