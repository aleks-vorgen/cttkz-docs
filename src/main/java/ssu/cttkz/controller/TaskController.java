package ssu.cttkz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.service.TaskService;

@RestController
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @PostMapping("/task")
    public ResponseEntity<?> save(@RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PatchMapping("/task")
    public ResponseEntity<?> update(@RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.update(task));
    }

    @PostMapping("/task/accept")
    public ResponseEntity<?> accept(@RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.accept(task));
    }
}
