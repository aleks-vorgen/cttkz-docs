package ssu.cttkz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.model.JobType;
import ssu.cttkz.repository.JobTypeRepository;

import java.util.List;

@Service
public class JobTypeService {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    public List<JobType> getAll() {
        return jobTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<JobType> getAllWithTasks() {
        return jobTypeRepository.findAllWithTasks();
    }
}
