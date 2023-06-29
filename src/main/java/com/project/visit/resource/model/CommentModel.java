package com.project.visit.resource.model;

public record CommentModel(Long id, String user, String comment, String date, boolean removable) {
}
