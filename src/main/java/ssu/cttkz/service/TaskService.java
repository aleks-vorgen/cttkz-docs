package ssu.cttkz.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.service.mapstruct.TaskMapper;
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
    @Autowired
    private TaskMapper taskMapper;

    public List<Task> getAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public Task save(TaskDto data) {
        Task task = requestToTask(data);
        Long id = taskRepository.saveAndFlush(task).getId();
        System.out.println(taskRepository.findById(id).orElse(null));
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(TaskDto data) {
        Task task = taskRepository.findById(data.getId())
                .orElseThrow(() -> new EntityNotFoundException("Задача не знайдена: " + data.getId()));

        System.out.println("DTO\n" + data);
        System.out.println("Found task\n" + task);
        taskMapper.updateTaskFromDto(data, task);

        System.out.println("Edited task\n" + task);

        Long id = taskRepository.saveAndFlush(task).getId();

        Task savedTask = taskRepository.findById(id).orElse(null);

        System.out.println("Saved task\n" + savedTask);

        return savedTask;
    }

    private Task editTask(TaskDto data, Task task) {
        JobType jobType = jobTypeRepository.findById(data.getJobType()).orElseThrow(EntityNotFoundException::new);
        //TODO доделать редактирование

        return null;
    }



    private Task requestToTask(TaskDto request) {
        Task task = new Task();
        JobType jobType = jobTypeRepository.findById(request.getJobType()).orElse(null);
        String regNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        Status status = statusRepository.findById(1L).orElse(null);
        if (request.getId() != null) task.setId(request.getId());
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
