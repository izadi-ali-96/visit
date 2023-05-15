package com.project.visit.resource.model;

import java.util.List;

public record DoctorData(Long id, String name, String family, String medicalCode, String picLink, List<String> tags,
						 List<AddressData> addressData) {
}
