package com.project.visit.resource.mapper;

import com.project.visit.resource.model.CommentModel;
import com.project.visit.resource.response.CommentResponse;
import com.project.visit.service.model.CommentServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentResourceMapper {

    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "user", source = "fullName")
    @Mapping(target = "date", source = "date")
    CommentModel toCommentModel(CommentServiceModel comment);


    default CommentResponse toCommentResponse(List<CommentServiceModel> comments) {
        return new CommentResponse(comments.stream().map(this::toCommentModel).toList());
    }
}
