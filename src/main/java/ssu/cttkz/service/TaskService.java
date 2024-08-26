package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.JobType;
import ssu.cttkz.model.Status;
import ssu.cttkz.model.Task;
import ssu.cttkz.repository.JobTypeRepository;
import ssu.cttkz.repository.StatusRepository;
import ssu.cttkz.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private StatusRepository statusRepository;

    public List<Task> getAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Long save(TaskDto data) {
        Task task = requestToTask(data);
        return taskRepository.saveAndFlush(task).getId();
    }

    private Task requestToTask(TaskDto request) {
        Task task = new Task();
        JobType jobType = jobTypeRepository.findById(Long.valueOf(request.getJobType())).orElse(null);
        String regNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        Status status = statusRepository.findById(1L).orElse(null);

        task.setInvNumber(request.getInvNumber());
        task.setSerialNumber(request.getSerialNumber());
        task.setTitle(request.getTitle());
        task.setFullNameMVO(request.getFullNameMVO());
        task.setDepartment(request.getDepartment());
        task.setApplicationNumberOriginal(request.getApplicationNumberOriginal());
        task.setJobType(jobType);
        task.setRegNumber(regNumber);
        task.setStatus(status);
        task.setExecutor(request.getExecutor());
        task.setComment(request.getComment());
        task.setCreateUser(request.getCreateUser());

        return task;
    }
}
