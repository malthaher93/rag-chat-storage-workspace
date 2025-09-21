package com.northbay.configuration;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class ObjectMapperConfig {

	private static final String JSON_DATE_TIME_INTERNAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/***
	 * instance of objectMapper for internal usage
	 * 
	 * @return
	 */
	@Bean
	@Primary
	ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_TIME_INTERNAL_PATTERN));
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
		mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
		mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES);
		return mapper;
	}

}
