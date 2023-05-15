package com.project.visit.resource.filter;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestContext {

	private String userId;

	private Date requestDate;
}
