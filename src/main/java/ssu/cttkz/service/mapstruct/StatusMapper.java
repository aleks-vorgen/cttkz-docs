package ssu.cttkz.service.mapstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssu.cttkz.model.Status;
import ssu.cttkz.repository.StatusRepository;

@Component
public class StatusMapper {

    @Autowired
    private StatusRepository statusRepository;

    public Status idToStatus(Long id) {
        if (id == null) {
            return null;
        }
        return statusRepository.findById(id).orElse(null);
    }
}