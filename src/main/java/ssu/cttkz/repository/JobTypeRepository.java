package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssu.cttkz.model.JobType;

import java.util.List;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    @Query("SELECT jt FROM JobType jt JOIN Task t ON t.jobType.id = jt.id")
    List<JobType> findAllWithTasks();
}
