package com.project.visit.resource.response;

import com.project.visit.resource.model.CommentModel;

import java.util.List;

public record CommentResponse(List<CommentModel> comments) {
}
