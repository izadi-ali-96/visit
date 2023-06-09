package com.project.visit.resource.model;

import java.util.List;

public record DoctorData(String name, String family, String medicalCode, List<String> tags,
                         List<AddressData> addressData) {
}
