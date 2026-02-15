package com.mayank.taskmanagement.repository;

import com.mayank.taskmanagement.entity.Task;
import com.mayank.taskmanagement.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId,TaskStatus status);
    Optional<Task> findByIdAndUserId(Long id, Long userId);
    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);
    List<Task> findByUserIdOrderByDueDateAsc(Long userId);

    long countByUserId(Long userId);
    long countByUserIdAndStatus(Long userId, TaskStatus status);
}
