package com.project.visit.config;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
	}
}
