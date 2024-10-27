package ssu.cttkz.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.JobType;
import ssu.cttkz.model.Status;
import ssu.cttkz.model.Task;
import ssu.cttkz.model.TaskHistory;
import ssu.cttkz.repository.JobTypeRepository;
import ssu.cttkz.repository.StatusRepository;
import ssu.cttkz.repository.TaskHistoryRepository;
import ssu.cttkz.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final JobTypeRepository jobTypeRepository;
    private final StatusRepository statusRepository;

    public TaskService(TaskRepository taskRepository, TaskHistoryRepository taskHistoryRepository, JobTypeRepository jobTypeRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.statusRepository = statusRepository;
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
        Status status = statusRepository.findById(2L).orElseThrow(EntityNotFoundException::new);

        Task task = requestToTask(data);
        task.setStatus(status);

        taskRepository.save(task);
        fetchWithHistory(task);
        return task;
    }

    private void fetchWithHistory(Task task) {
        Set<TaskHistory> history = taskHistoryRepository.findAllByTaskId(task.getId());
        history.iterator().forEachRemaining(e -> e.setTask(null));
        task.setHistory(history);
    }

    private Task requestToTask(TaskDto data) {
        Task task;
        JobType jobType = jobTypeRepository.findById(data.getJobType()).orElseThrow(EntityNotFoundException::new);
        Status status;
        String regNumber;

        if (data.getId() == null) { //if creating
            task = new Task();
            status = statusRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
            regNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
            task.setRegNumber(regNumber);
            task.setCreateUser(data.getCreateUser());
            task.setCreatedAt(LocalDateTime.now());
        } else { //if updating
            task = taskRepository.findById(data.getId()).orElseThrow(EntityNotFoundException::new);
            status = statusRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

            task.setId(data.getId());
            task.setUpdateReason(data.getUpdateReason());
            task.setUpdateUser(data.getUpdateUser());
            task.setUpdatedAt(LocalDateTime.now());

            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTask(task);
            taskHistory.setUpdatedAt(LocalDateTime.now());
            taskHistory.setUpdateUser(data.getUpdateUser());
            taskHistory.setUpdateReason(data.getUpdateReason());

            taskHistoryRepository.saveAndFlush(taskHistory);
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

        return task;
    }
}
