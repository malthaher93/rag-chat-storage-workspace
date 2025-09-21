package com.northbay.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("application")
public class VersionController {

	@Value("${spring.application.version}")
	private String applicationVersion;

	@Operation(hidden = true)
	@GetMapping("version")
	public String getVersion() {
		return applicationVersion;
	}

}
