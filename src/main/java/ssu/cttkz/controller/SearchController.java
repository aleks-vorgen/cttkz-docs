package ssu.cttkz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.service.TaskService;

@RestController
public class SearchController {
    private final TaskService taskService;

    public SearchController(TaskService taskService) {
        this.taskService = taskService;
    }

    //TODO search
    @PostMapping("tasks/search")
    public ResponseEntity<?> searchTask(@RequestBody String title) {
        System.out.println(title);
        return ResponseEntity.ok(taskService.search(title));
    }
}
