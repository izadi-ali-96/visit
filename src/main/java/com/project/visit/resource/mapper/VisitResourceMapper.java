package com.project.visit.resource.mapper;

import com.project.visit.model.Visit;
import com.project.visit.resource.model.DoctorVisitInfo;
import com.project.visit.resource.model.UserVisitInfo;
import com.project.visit.resource.model.VisitModel;
import com.project.visit.resource.response.DoctorVisitInfoResponse;
import com.project.visit.resource.response.UserVisitInfoResponse;
import com.project.visit.service.model.VisitInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitResourceMapper {

    VisitModel toVisitModel(Visit visit);

    List<VisitModel> toVisitModel(List<Visit> visit);

    List<DoctorVisitInfoResponse> toDoctorVisitInfoResponse(List<VisitInfoModel> models);

    DoctorVisitInfo toDoctorInfo(VisitInfoModel model);

    List<UserVisitInfoResponse> toUserVisitInfoResponse(List<VisitInfoModel> models);

    @Mapping(target = "path", source = "path")
    @Mapping(target = "doctor", source = "doctorName")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    UserVisitInfo toUserInfo(VisitInfoModel model);
}

