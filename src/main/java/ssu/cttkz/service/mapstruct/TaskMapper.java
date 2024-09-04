package ssu.cttkz.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.JobType;
import ssu.cttkz.model.Status;
import ssu.cttkz.model.Task;

@Mapper(componentModel = "spring", uses = {JobTypeMapper.class, StatusMapper.class})
public interface TaskMapper {
    @Mapping(source = "id", target = "jobType", qualifiedByName = "idToJobType")
    @Mapping(source = "id", target = "status", qualifiedByName = "idToStatus")
    void updateTaskFromDto(TaskDto dto, @MappingTarget Task entity);

    @Named("idToJobType")
    default JobType idToJobType(Long id) {
        if (id == null) {
            return null;
        }
        JobType jobType = new JobType();
        jobType.setId(id);
        return jobType;
    }

    @Named("idToStatus")
    default Status idToStatus(Long id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }
}
