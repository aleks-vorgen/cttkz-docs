package ssu.cttkz.service;

import org.springframework.stereotype.Service;
import ssu.cttkz.model.TaskHistory;
import ssu.cttkz.repository.TaskHistoryRepository;

import java.util.Set;

@Service
public class TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryService(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    public Set<TaskHistory> findAllByTaskId(Long id) {
        return taskHistoryRepository.findAllByTaskId(id);
    }

    public TaskHistory save(TaskHistory taskHistory) {
        return taskHistoryRepository.save(taskHistory);
    }
}
