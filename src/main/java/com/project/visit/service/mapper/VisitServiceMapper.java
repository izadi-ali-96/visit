package com.project.visit.service.mapper;

import com.project.visit.model.Visit;
import com.project.visit.service.model.VisitInfoModel;
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
    VisitInfoModel toVisitModel(Visit visit);

    List<VisitInfoModel> toVisitModel(List<Visit> visits);

    default List<VisitInfoModel> toVisitLightModel(List<Visit> visits) {
        return visits.stream().map(v -> new VisitInfoModel(v.getId(), v.getTime(), v.getUser() == null)).toList();
    }
}
