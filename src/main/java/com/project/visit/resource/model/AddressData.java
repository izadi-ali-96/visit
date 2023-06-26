package com.project.visit.resource.model;

import java.util.List;

public record AddressData(Long id, String title, String path, String city, String province, List<String> phones,
                          List<String> days) {
}
