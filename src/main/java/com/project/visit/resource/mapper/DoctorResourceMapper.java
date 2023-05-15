package com.project.visit.resource.mapper;

import java.util.List;
import java.util.Set;

import com.project.visit.model.Address;
import com.project.visit.model.Doctor;
import com.project.visit.model.Expertise;
import com.project.visit.resource.model.AddressData;
import com.project.visit.resource.model.DoctorData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DoctorResourceMapper {

	@Mapping(target = "tags", source = "expertise", qualifiedByName = "toTagName")
	DoctorData toLightDoctorInfo(Doctor doctor);

	@Mapping(target = "tags", source = "expertise", qualifiedByName = "toTagName")
	@Mapping(target = "addressData", source = "addresses")
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
}
