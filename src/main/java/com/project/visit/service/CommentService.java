package com.project.visit.service;

import com.project.visit.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments(String doctorId);

    void addComment(String description, String userId, String doctorId);
}
