package com.project.visit.resource.mapper;

import com.project.visit.model.Comment;
import com.project.visit.resource.model.CommentModel;
import com.project.visit.resource.response.CommentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentResourceMapper {

    CommentModel toCommentModel(Comment comment);


    default CommentResponse toCommentResponse(List<Comment> comments) {
        return new CommentResponse(comments.stream().map(this::toCommentModel).toList());
    }
}
