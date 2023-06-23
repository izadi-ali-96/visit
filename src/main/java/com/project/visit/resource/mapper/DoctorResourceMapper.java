package com.project.visit.resource.mapper;

import com.project.visit.model.Address;
import com.project.visit.model.Doctor;
import com.project.visit.model.Expertise;
import com.project.visit.resource.model.AddressData;
import com.project.visit.resource.model.DoctorData;
import com.project.visit.resource.request.AddressRequestModel;
import com.project.visit.resource.request.UpdateUserInfoRequestModel;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DoctorResourceMapper {

    @Mapping(target = "tags", source = "expertise", qualifiedByName = "toTagName")
    @Mapping(target = "pictureUrl", source = "pictureUrl")
    @Mapping(target = "description", source = "description")
    DoctorData toLightDoctorInfo(Doctor doctor);

    @Mapping(target = "tags", source = "expertise", qualifiedByName = "toTagName")
    @Mapping(target = "addressData", source = "addresses")
    @Mapping(target = "pictureUrl", source = "pictureUrl")
    @Mapping(target = "description", source = "description")
    DoctorData toDoctorData(Doctor doctor);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "city", source = "city.name")
    @Mapping(target = "province", source = "city.province.name")
    @Mapping(target = "days", source = "days")
    @Mapping(target = "phones", source = "phones")
    AddressData toAddressData(Address address);

    @Named(value = "toTagName")
    default List<String> toTagName(Set<Expertise> expertise) {
        return expertise.stream().map(Expertise::getName).toList();
    }

    @Mapping(target = "userId", source = "userId")
    UserInfoModel toUserInfoModel(UpdateUserInfoRequestModel model, String userId);

    @Mapping(target = "userId", source = "userId")
    AddressModel toAddressModel(AddressRequestModel model, String userId);
}
