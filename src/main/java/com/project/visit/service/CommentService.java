package com.project.visit.service;

import com.project.visit.model.Comment;
import com.project.visit.service.model.CommentServiceModel;

import java.util.List;

public interface CommentService {

    List<CommentServiceModel> getComments(String doctorId);

    void addComment(String description, String userId, String doctorId);
}
