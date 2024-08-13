package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.Task;
import ssu.cttkz.repository.TaskRepository;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            taskDtos.add(new TaskDto(
                    task.getId(),
                    task.getInvNumber(),
                    task.getSerialNumber(),
                    task.getTitle(),
                    task.getFullNameMVO(),
                    task.getDepartment(),
                    task.getApplicationNumberOriginal(),
                    task.getJobType().getTitle(),
                    format(task.getRegNumber(), true),
                    task.getExecutor(),
                    task.getComment(),
                    task.getStatus().getTitle(),
                    format(task.getCreatedAt(), false),
                    task.getCreateUser(),
                    format(task.getUpdatedAt(), false),
                    task.getUpdateReason(),
                    task.getUpdateUser()
            ));
            System.out.println(task);
        }
        return taskDtos;
    }

    private String format(Timestamp datetime, boolean withSec) {
        return withSec ?
                datetime.toLocalDateTime().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")):
                datetime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
