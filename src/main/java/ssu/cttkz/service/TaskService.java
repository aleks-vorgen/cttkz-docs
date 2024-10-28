package ssu.cttkz.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.JobType;
import ssu.cttkz.model.Status;
import ssu.cttkz.model.Task;
import ssu.cttkz.model.TaskHistory;
import ssu.cttkz.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;
    private final JobTypeService jobTypeService;
    private final StatusService statusService;
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskHistoryService taskHistoryService, JobTypeService jobTypeService, StatusService statusService, UserService userService, DepartmentService departmentService) {
        this.taskRepository = taskRepository;
        this.taskHistoryService = taskHistoryService;
        this.jobTypeService = jobTypeService;
        this.statusService = statusService;
        this.userService = userService;
        this.departmentService = departmentService;
    }

    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            fetchWithHistory(task);
        }
        return tasks;
    }

    public Task save(TaskDto data) {
        Long id = taskRepository.saveAndFlush(requestToTask(data)).getId();
        return taskRepository.findById(id).orElse(null);
    }

    public Task update(TaskDto data) {
        Task task = taskRepository.saveAndFlush(requestToTask(data));
        fetchWithHistory(task);
        return task;
    }

    public Task accept(TaskDto data) {
        Status status = statusService.getById(2L);

        Task task = requestToTask(data);
        task.setStatus(status);

        taskRepository.save(task);
        fetchWithHistory(task);
        return task;
    }

    private void fetchWithHistory(Task task) {
        Set<TaskHistory> history = taskHistoryService.findAllByTaskId(task.getId());
        history.iterator().forEachRemaining(e -> e.setTask(null));
        task.setHistory(history);
    }

    private Task requestToTask(TaskDto data) {
        Task task;
        JobType jobType = jobTypeService.getById(data.getJobType());
        Status status;
        String regNumber;

        if (data.getId() == null) { //if creating
            task = new Task();
            status = statusService.getById(1L);
            regNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
            task.setRegNumber(regNumber);
            task.setCreatedUser(userService.findByUsername(data.getCreateUser()));
            task.setCreatedAt(LocalDateTime.now());
        } else { //if updating
            task = taskRepository.findById(data.getId()).orElseThrow(EntityNotFoundException::new);
            status = statusService.getById(1L);

            task.setId(data.getId());
            task.setUpdateReason(data.getUpdateReason());
            task.setUpdatedUser(userService.findByUsername(data.getUpdateUser()));
            task.setUpdatedAt(LocalDateTime.now());

            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTask(task);
            taskHistory.setUpdatedAt(LocalDateTime.now());
            taskHistory.setUpdateUser(data.getUpdateUser());
            taskHistory.setUpdateReason(data.getUpdateReason());

            taskHistoryService.save(taskHistory);
        }

        task.setInvNumber(data.getInvNumber());
        task.setSerialNumber(data.getSerialNumber());
        task.setTitle(data.getTitle());
        task.setFullNameMVO(data.getFullNameMVO());
        task.setDepartment(departmentService.findByTitle(data.getDepartment()));
        task.setApplicationNumberOriginal(data.getApplicationNumberOriginal());
        task.setJobType(jobType);
        task.setStatus(status);
        task.setExecutor(userService.findByUsername(data.getExecutor()));
        task.setComment(data.getComment());

        return task;
    }
}
