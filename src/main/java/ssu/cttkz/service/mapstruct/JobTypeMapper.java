package ssu.cttkz.service.mapstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssu.cttkz.model.JobType;
import ssu.cttkz.repository.JobTypeRepository;

@Component
public class JobTypeMapper {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    public JobType idToJobType(Long id) {
        if (id == null) {
            return null;
        }
        return jobTypeRepository.findById(id).orElse(null);
    }
}