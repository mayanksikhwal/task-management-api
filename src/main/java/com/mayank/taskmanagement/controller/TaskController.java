package com.mayank.taskmanagement.controller;

import com.mayank.taskmanagement.dto.TaskRequest;
import com.mayank.taskmanagement.dto.TaskResponse;
import com.mayank.taskmanagement.enums.TaskStatus;
import com.mayank.taskmanagement.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Task management endpoints (requires JWT authentication)")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // HELPER METHOD: to extract userId from JWT (stored in request by filter)
    private Long getUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return userId;
    }

    // create task
    @Operation(summary = "Create task", description = "Create a new task for authenticated user")
    @PostMapping
    public ResponseEntity<?> createTask(
            @Valid @RequestBody TaskRequest request,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            TaskResponse response = taskService.createTask(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get all tasks for users
    @Operation(summary = "Get all tasks", description = "Retrieve all tasks for authenticated user")
    @GetMapping
    public ResponseEntity<?> getAllTasks(HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            List<TaskResponse> tasks = taskService.getAllTasks(userId);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get tasks by status
    @Operation(summary = "Get tasks by status", description = "Retrieve all tasks with their status")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTasksByStatus(
            @PathVariable TaskStatus status,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            List<TaskResponse> tasks = taskService.getTasksByStatus(userId, status);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get single task by id
    @Operation(summary = "Get single task by authenticated user id", description = "Retrieve single tasks for authenticated user with authenticated user id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            TaskResponse task = taskService.getTaskById(id, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // update task
    @Operation(summary = "Update the task", description = "Update task for authenticated user")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            TaskResponse task = taskService.updateTask(id, request, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // update task status only
    @Operation(summary = "Update status of the task", description = "Update only status of the task for authenticated user")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            TaskResponse task = taskService.updateTaskStatus(id, status, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // delete task
    @Operation(summary = "Delete a task", description = "Delete a task for authenticated user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            taskService.deleteTask(id, userId);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // search tasks
    @Operation(summary = "Search all tasks", description = "Search all tasks for authenticated user")
    @GetMapping("/search")
    public ResponseEntity<?> searchTasks(
            @RequestParam String keyword,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            List<TaskResponse> tasks = taskService.searchTasks(userId, keyword);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get task stats
    @Operation(summary = "Get statistics of all tasks", description = "Retrieve statistics of all tasks for authenticated user")
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(HttpServletRequest httpRequest) {
        try {
            Long userId = getUserId(httpRequest);
            TaskService.TaskStatistics stats = taskService.getTaskStatistics(userId);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}