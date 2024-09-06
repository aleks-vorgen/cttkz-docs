package ssu.cttkz.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ssu.cttkz.dto.TaskDto;
import ssu.cttkz.model.Task;

//@Mapper(componentModel = "spring", uses = {JobTypeMapper.class})
public interface TaskMapper {
//    @Mapping(source = "jobType", target = "jobType", qualifiedByName = "idToJobType")
//    void updateTaskFromDto(TaskDto dto, @MappingTarget Task entity);
}
