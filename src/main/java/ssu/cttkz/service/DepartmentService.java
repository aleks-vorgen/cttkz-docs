package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssu.cttkz.model.Department;
import ssu.cttkz.repository.DepartmentRepository;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department findByTitle(String title) {
        return departmentRepository.findByTitle(title);
    }

    public Department save(Department department) {
        department.setCode(getDepartmentCode(department.getTitle()));
        return departmentRepository.save(department);
    }

    private String getDepartmentCode(String title) {
        return String.copyValueOf(title.toCharArray(), 0, 5);
    }
}
