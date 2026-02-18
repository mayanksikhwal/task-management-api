package com.mayank.taskmanagement.service;

import com.mayank.taskmanagement.dto.TaskRequest;
import com.mayank.taskmanagement.dto.TaskResponse;
import com.mayank.taskmanagement.entity.Task;
import com.mayank.taskmanagement.entity.User;
import com.mayank.taskmanagement.enums.TaskStatus;
import com.mayank.taskmanagement.repository.TaskRepository;
import com.mayank.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mayank.taskmanagement.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    //create task
    public TaskResponse createTask(TaskRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //create new task
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO);
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setUser(user);

        //save task
        Task savedTask = taskRepository.save(task);
        //convert to response
        return convertToResponse(savedTask);
    }

    //get all tasks for user
    public List<TaskResponse> getAllTasks(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    //get tasks by status
    public List<TaskResponse> getTasksByStatus(Long userId, TaskStatus status) {
        List<Task> tasks = taskRepository.findByUserIdAndStatus(userId, status);
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    //get single task by id
    public TaskResponse getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or you don't have access"));

        return convertToResponse(task);
    }

    //update task
    public TaskResponse updateTask(Long taskId, TaskRequest request, Long userId) {
        //Find task (ensures user owns this task)
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or you don't have access"));

        //update fields
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        //save updated task
        Task updatedTask = taskRepository.save(task);

        return convertToResponse(updatedTask);
    }

    //update task status only
    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or you don't have access"));

        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);

        return convertToResponse(updatedTask);
    }

    //delete task
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or you don't have access"));

        taskRepository.delete(task);
    }

    //search tasks by title
    public List<TaskResponse> searchTasks(Long userId, String keyword) {
        List<Task> tasks = taskRepository.findByUserIdAndTitleContainingIgnoreCase(userId, keyword);
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    //get task stats
    public TaskStatistics getTaskStatistics(Long userId) {
        long totalTasks = taskRepository.countByUserId(userId);
        long todoTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.TODO);
        long inProgressTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.IN_PROGRESS);
        long completedTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.COMPLETED);

        return new TaskStatistics(totalTasks, todoTasks, inProgressTasks, completedTasks);
    }
        //helper method: converting Task entity to TaskResponse DTO
        private TaskResponse convertToResponse(Task task) {
            return new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getUser().getId(),
                    task.getUser().getUsername(),
                    task.getCreatedAt(),
                    task.getUpdatedAt()
            );
        }

        // Inner class for task stats
        public static class TaskStatistics {
            public long total;
            public long todo;
            public long inProgress;
            public long completed;

            public TaskStatistics(long total, long todo, long inProgress, long completed) {
                this.total = total;
                this.todo = todo;
                this.inProgress = inProgress;
                this.completed = completed;
            }
        }
    }