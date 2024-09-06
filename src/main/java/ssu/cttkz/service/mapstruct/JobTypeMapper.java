package ssu.cttkz.service.mapstruct;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssu.cttkz.model.JobType;
import ssu.cttkz.repository.JobTypeRepository;

//@Component
public class JobTypeMapper {

//    @Autowired
//    private JobTypeRepository jobTypeRepository;
//
//    @Named("idToJobType")
//    public JobType idToJobType(String id) {
//        if (id == null) {
//            return null;
//        }
//        Long idLong = Long.valueOf(id); // Преобразование из String в Long
//        return jobTypeRepository.findById(idLong).orElse(null);
//    }
}