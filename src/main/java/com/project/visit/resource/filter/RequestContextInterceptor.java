package com.project.visit.resource.filter;

import java.util.Date;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class RequestContextInterceptor implements AsyncHandlerInterceptor {

	private static final String REQUEST_CONTEXT_NAME = "current-context";

	public static RequestContext getCurrentContext() {
		return (RequestContext) RequestContextHolder.currentRequestAttributes().getAttribute(REQUEST_CONTEXT_NAME,
				RequestAttributes.SCOPE_REQUEST);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		var requestContextBuilder = RequestContext.builder().requestDate(new Date());

		String userId = request.getHeader(Constants.REQUEST_HEADER_USER_ID);
		if (StringUtils.isNotBlank(userId)) {
			requestContextBuilder.userId(userId);
		}

		RequestContextHolder.currentRequestAttributes().setAttribute(REQUEST_CONTEXT_NAME,
				requestContextBuilder.build(), RequestAttributes.SCOPE_REQUEST);

		return true;
	}
}
