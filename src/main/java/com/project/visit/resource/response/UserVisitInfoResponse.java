package com.project.visit.resource.response;

import com.project.visit.resource.model.UserVisitInfo;

import java.util.List;

public record UserVisitInfoResponse(List<UserVisitInfo> infos) {
}
