package ssu.cttkz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.service.TaskHistoryService;

@RestController
public class TaskHistoryController {
    private final TaskHistoryService taskHistoryService;

    public TaskHistoryController(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    @GetMapping("task-history/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        return ResponseEntity.ok(taskHistoryService.findAllByTaskId(id));
    }
}
