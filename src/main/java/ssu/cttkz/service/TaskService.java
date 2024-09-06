package ssu.cttkz.service;

import jakarta.persistence.EntityNotFoundException;
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
    private final TaskRepository taskRepository;
    private final JobTypeRepository jobTypeRepository;
    private final StatusRepository statusRepository;

    public TaskService(TaskRepository taskRepository, JobTypeRepository jobTypeRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.statusRepository = statusRepository;
    }

    public List<Task> getAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public Task save(TaskDto data) {
        Task task = requestToTask(data);
        Long id = taskRepository.saveAndFlush(task).getId();
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(TaskDto data) {
        Long id = taskRepository.saveAndFlush(requestToTask(data)).getId();
        return taskRepository.findById(id).orElse(null);
    }

    private Task requestToTask(TaskDto data) {
        Task task;
        JobType jobType = jobTypeRepository.findById(data.getJobType()).orElseThrow(EntityNotFoundException::new);
        Status status;
        String regNumber;

        if (data.getId() == null) {
            task = new Task();
            status = statusRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
            regNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
            task.setRegNumber(regNumber);
            task.setCreateUser(data.getCreateUser());
            task.setCreatedAt(LocalDateTime.now());
        }
        else {
            task = taskRepository.findById(data.getId()).orElseThrow(EntityNotFoundException::new);
            status = statusRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
            task.setId(data.getId());
            task.setUpdateReason(data.getUpdateReason());
            task.setUpdateUser(data.getUpdateUser());
            task.setUpdatedAt(LocalDateTime.now());
            task.setDeleteReason(data.getDeleteReason());
            task.setDeleteUser(data.getDeleteUser());
        }

        task.setInvNumber(data.getInvNumber());
        task.setSerialNumber(data.getSerialNumber());
        task.setTitle(data.getTitle());
        task.setFullNameMVO(data.getFullNameMVO());
        task.setDepartment(data.getDepartment());
        task.setApplicationNumberOriginal(data.getApplicationNumberOriginal());
        task.setJobType(jobType);
        task.setStatus(status);
        task.setExecutor(data.getExecutor());
        task.setComment(data.getComment());

        System.out.println(data);
        System.out.println(task);

        return task;
    }
}
