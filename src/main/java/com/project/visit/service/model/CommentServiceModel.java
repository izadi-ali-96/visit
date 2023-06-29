package com.project.visit.service.model;

public record CommentServiceModel(Long commentId, String fullName, String comment, String date, boolean removable) {
}
