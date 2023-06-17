package com.project.visit.service;

import com.project.visit.model.Visit;
import com.project.visit.service.model.VisitModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitServiceMapper {

    @Mapping(target = "time", source = "time")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "path", source = "address.path")
    @Mapping(target = "doctorName", expression = "java(visit.getDoctor().getFullName())")
    @Mapping(target = "userName", expression = "java(visit.getUser() != null ? visit.getUser().getFullName() : null)")
    VisitModel toVisitModel(Visit visit);

    List<VisitModel> toVisitModel(List<Visit> visits);
}
