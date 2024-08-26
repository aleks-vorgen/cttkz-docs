package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.Task;
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
        Long id = taskService.save(task);
        Task response = taskService.findById(id);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
