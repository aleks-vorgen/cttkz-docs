package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.cttkz.model.JobType;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {
}
