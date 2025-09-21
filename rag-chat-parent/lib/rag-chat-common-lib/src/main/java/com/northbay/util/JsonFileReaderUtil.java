package com.northbay.util;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonFileReaderUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JsonFileReaderUtil.class);
	private final ObjectMapper objectMapper;

	public Object loadTestData(String fileName, Class<?> clazz) {
		try {
			return objectMapper.readValue(new URL("file:" + fileName), clazz);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	public Object loadTestData(String fileName, TypeReference<?> ref) {
		try {
			return objectMapper.readValue(new URL("file:" + fileName), ref);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}
}
