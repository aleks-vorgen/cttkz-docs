package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.model.JobType;
import ssu.cttkz.service.JobTypeService;

import java.util.List;

@RestController
public class JobTypeController {
    @Autowired
    private JobTypeService jobTypeService;

    @GetMapping("/jobtypes")
    public ResponseEntity<?> getAll(@RequestParam(required = false) boolean with_tasks) {
        List<JobType> jobTypes = with_tasks ? jobTypeService.getAllWithTasks() : jobTypeService.getAll();
        return ResponseEntity.ok(jobTypes);
    }
}
