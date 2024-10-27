package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.cttkz.model.TaskHistory;

import java.util.Set;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    Set<TaskHistory> findAllByTaskId(Long id);
}
