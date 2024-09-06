package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.service.TaskService;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @PostMapping("/task")
    public ResponseEntity<?> save(@RequestBody TaskDto task) {
        System.out.println(task);
        return ResponseEntity.ok(taskService.save(task));
    }

    @PatchMapping("/task")
    public ResponseEntity<?> update(@RequestBody TaskDto task) {
        System.out.println(task);
        return ResponseEntity.ok(taskService.update(task));
    }
}
