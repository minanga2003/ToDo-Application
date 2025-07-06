package com.CoverageX.Take.Home.Assessment.controller;
import com.CoverageX.Take.Home.Assessment.dto.TaskDTO;
import com.CoverageX.Take.Home.Assessment.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Validated @RequestBody TaskDTO dto) {
        TaskDTO created = service.createTask(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getLatestTasks(@RequestParam(defaultValue = "5") int limit) {
        List<TaskDTO> tasks = service.getLatestTasks(limit);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> markComplete(@PathVariable Long id) {
        service.markComplete(id);
        return ResponseEntity.noContent().build();
    }
}
