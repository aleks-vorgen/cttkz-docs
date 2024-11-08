package ssu.cttkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.cttkz.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByTitle(String title);
}
