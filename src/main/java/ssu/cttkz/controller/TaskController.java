package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.Task;
import ssu.cttkz.service.TaskService;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<?> getAll() {
        List<TaskDto> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }
}
