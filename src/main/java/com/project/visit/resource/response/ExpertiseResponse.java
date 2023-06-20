package com.project.visit.resource.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ExpertiseResponse {

    private List<ExpertiseInfo> expertise;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ExpertiseInfo {

        private Long id;

        private String name;

    }
}
