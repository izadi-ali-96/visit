package com.project.visit.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.project.visit.resource.filter.RequestContextInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestContextInterceptor());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.stream().filter(converter -> converter instanceof MappingJackson2HttpMessageConverter).findFirst()
				.ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter)
						.setDefaultCharset(StandardCharsets.UTF_8));
	}

}