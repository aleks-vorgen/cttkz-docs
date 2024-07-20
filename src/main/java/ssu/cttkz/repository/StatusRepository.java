package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.cttkz.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
