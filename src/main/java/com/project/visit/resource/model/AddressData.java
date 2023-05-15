package com.project.visit.resource.model;

import java.util.List;

public record AddressData(String id, String title, String path, String city, String province, List<String> phones,
						  List<String> days) {
}
