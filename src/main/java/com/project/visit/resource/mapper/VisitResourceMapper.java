package com.project.visit.resource.mapper;

import com.project.visit.model.Visit;
import com.project.visit.resource.model.*;
import com.project.visit.resource.response.DoctorVisitInfoResponse;
import com.project.visit.resource.response.GenerateTimeResponse;
import com.project.visit.resource.response.UserVisitInfoResponse;
import com.project.visit.service.model.TimeAndVisitInfoModel;
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

//    default VisitLightResponse toVisitLightResponse(List<VisitInfoModel> models) {
//        return new VisitLightResponse(models.stream().map(v -> new VisitLightModel(v.id(), v.time(), null, v.active())).toList());
//    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "userId", source = "userName")
    DoctorVisitInfo toDoctorInfo(VisitInfoModel model);

    default UserVisitInfoResponse toUserVisitInfoResponse(List<VisitInfoModel> models) {
        return new UserVisitInfoResponse(models.stream().map(this::toUserInfo).toList());
    }

    @Mapping(target = "path", source = "path")
    @Mapping(target = "doctor", source = "doctorName")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    UserVisitInfo toUserInfo(VisitInfoModel model);

    default GenerateTimeResponse toGenerateTimeResponse(TimeAndVisitInfoModel model) {
        return new GenerateTimeResponse(model.visitInfoModels().stream().map(this::toVisit).toList(), model.timeModel().getDay(), model.timeModel().getPersianTime());
    }

    GenerateTimeModel toGenerateTimeModel(TimeModel model);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "hourAndMin")
    @Mapping(target = "active", source = "active")
    VisitLightModel toVisit(VisitInfoModel model);

}

