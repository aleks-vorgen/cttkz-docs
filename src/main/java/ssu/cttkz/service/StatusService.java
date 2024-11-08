package ssu.cttkz.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssu.cttkz.model.Status;
import ssu.cttkz.repository.StatusRepository;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    public Status getById(Long id) {
        return statusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
