package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.cttkz.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByApplicationNumberOriginal(String applicationNumberOriginal);
    List<Task> findByTitleContainingIgnoreCase(String title);
}
