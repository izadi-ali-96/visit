package com.project.visit.resource.mapper;

import java.util.List;

import com.project.visit.model.Visit;
import com.project.visit.resource.model.VisitModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitResourceMapper {

	VisitModel toVisitModel(Visit visit);

	List<VisitModel> toVisitModel(List<Visit> visit);
}
