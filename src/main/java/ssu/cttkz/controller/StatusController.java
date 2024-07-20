package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.model.Status;
import ssu.cttkz.service.StatusService;

import java.util.List;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("/statuses")
    public ResponseEntity<?> getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }
}
