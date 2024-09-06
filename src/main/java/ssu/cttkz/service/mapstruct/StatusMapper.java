package ssu.cttkz.service.mapstruct;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssu.cttkz.model.Status;
import ssu.cttkz.repository.StatusRepository;

@Component
public class StatusMapper {

    @Autowired
    private StatusRepository statusRepository;

    @Named("idToStatus")
    public Status idToStatus(String id) {
        if (id == null) {
            return null;
        }
        Long idLong = Long.valueOf(id); // Преобразование из String в Long
        return statusRepository.findById(idLong).orElse(null);
    }
}