package com.project.visit.resource.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCommentRequest {

    @NotBlank
    private String description;

    @NotBlank
    private String doctorId;

}
