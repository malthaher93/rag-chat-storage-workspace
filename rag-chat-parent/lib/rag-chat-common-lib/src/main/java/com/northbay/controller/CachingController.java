package com.northbay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.northbay.service.CachingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cache")
@RequiredArgsConstructor
public class CachingController {

	private final CachingService cachingService;

	@Operation(hidden = true)
	@GetMapping("clear")
	public String evictAll() {
		return cachingService.evictAll();
	}

}
