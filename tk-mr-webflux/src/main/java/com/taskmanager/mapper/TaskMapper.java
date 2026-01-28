package com.taskmanager.mapper;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {
    
    public Task toEntity(TaskDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setCreatedAt(dto.getCreatedAt());
        task.setUpdatedAt(LocalDateTime.now());
        task.setDueDate(dto.getDueDate());
        
        return task;
    }
    
    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDueDate(task.getDueDate());
        
        return dto;
    }
}
