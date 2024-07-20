package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.Task;
import ssu.cttkz.repository.JobTypeRepository;
import ssu.cttkz.repository.StatusRepository;
import ssu.cttkz.repository.TaskRepository;

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
                    task.getRegNumber(),
                    task.getExecutor(),
                    task.getComment(),
                    task.getStatus().getTitle(),
                    task.getCreatedAt(),
                    task.getCreateUser(),
                    task.getUpdatedAt(),
                    task.getUpdateReason(),
                    task.getUpdateUser()
            ));
        }
        return taskDtos;
    }
}
