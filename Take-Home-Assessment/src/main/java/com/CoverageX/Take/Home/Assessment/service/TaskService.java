package com.CoverageX.Take.Home.Assessment.service;

import com.CoverageX.Take.Home.Assessment.dto.TaskDTO;
import com.CoverageX.Take.Home.Assessment.entity.Task;
import com.CoverageX.Take.Home.Assessment.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskDTO createTask(TaskDTO dto) {
        Task task = new Task(dto.getTitle(), dto.getDescription());
        Task saved = repository.save(task);
        return new TaskDTO(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }

    public List<TaskDTO> getLatestTasks(int limit) {
        return repository.findByCompletedFalseOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .stream()
                .map(t -> new TaskDTO(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted()))
                .collect(Collectors.toList());
    }

    public void markComplete(Long id) {
        repository.findById(id).ifPresent(task -> {
            task.setCompleted(true);
            repository.save(task);
        });
    }
}

