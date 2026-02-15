package com.mayank.taskmanagement.dto;

import com.mayank.taskmanagement.enums.TaskPriority;
import com.mayank.taskmanagement.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
}
