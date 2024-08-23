package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.JobType;
import ssu.cttkz.model.Task;
import ssu.cttkz.repository.JobTypeRepository;
import ssu.cttkz.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JobTypeRepository jobTypeRepository;

    public List<Task> getAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Long save(TaskDto data) {
        Task task = requestToTask(data);
        return taskRepository.save(task).getId();
    }

    private Task requestToTask(TaskDto request) {
        Task task = new Task();
        JobType jobType = jobTypeRepository.findById(Long.valueOf(request.getJobType())).orElse(null);

        task.setInvNumber(request.getInvNumber());
        task.setSerialNumber(request.getSerialNumber());
        task.setTitle(request.getTitle());
        task.setFullNameMVO(request.getFullNameMVO());
        task.setDepartment(request.getDepartment());
        task.setApplicationNumberOriginal(request.getApplicationNumberOriginal());
        task.setJobType(jobType);
        task.setExecutor(request.getExecutor());
        task.setComment(request.getComment());
        task.setCreateUser(request.getCreateUser());

        return task;
    }
}
