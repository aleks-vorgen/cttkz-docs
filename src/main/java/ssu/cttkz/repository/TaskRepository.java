package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.cttkz.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByApplicationNumberOriginal(String applicationNumberOriginal);
}
