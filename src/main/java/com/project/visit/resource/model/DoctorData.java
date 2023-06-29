package com.project.visit.resource.model;

import java.util.List;

public record DoctorData(String doctorId, String name, String family, String pictureUrl, String medicalCode,
                         String description, List<ExpertiseData> tags,
                         List<AddressData> addressData) {
}
