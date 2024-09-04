package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.cttkz.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByApplicationNumberOriginal(String applicationNumberOriginal);
}
