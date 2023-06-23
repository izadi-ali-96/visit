package com.project.visit.resource.mapper;

import com.project.visit.model.Visit;
import com.project.visit.resource.model.DoctorVisitInfo;
import com.project.visit.resource.model.GenerateTimeModel;
import com.project.visit.resource.model.UserVisitInfo;
import com.project.visit.resource.model.VisitModel;
import com.project.visit.resource.response.DoctorVisitInfoResponse;
import com.project.visit.resource.response.GenerateTimeResponse;
import com.project.visit.resource.response.UserVisitInfoResponse;
import com.project.visit.service.model.TimeModel;
import com.project.visit.service.model.VisitInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitResourceMapper {

    VisitModel toVisitModel(Visit visit);

    List<VisitModel> toVisitModel(List<Visit> visit);

    default DoctorVisitInfoResponse toDoctorVisitInfoResponse(List<VisitInfoModel> models) {
        return new DoctorVisitInfoResponse(models.stream().map(this::toDoctorInfo).toList());
    }

    DoctorVisitInfo toDoctorInfo(VisitInfoModel model);

    default UserVisitInfoResponse toUserVisitInfoResponse(List<VisitInfoModel> models) {
        return new UserVisitInfoResponse(models.stream().map(this::toUserInfo).toList());
    }

    @Mapping(target = "path", source = "path")
    @Mapping(target = "doctor", source = "doctorName")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    UserVisitInfo toUserInfo(VisitInfoModel model);

    default GenerateTimeResponse toGenerateTimeResponse(List<TimeModel> models) {
        return new GenerateTimeResponse(models.stream().map(this::toGenerateTimeModel).toList());
    }

    GenerateTimeModel toGenerateTimeModel(TimeModel model);
}

