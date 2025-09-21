package com.northbay.common.controller;

import static com.northbay.constants.GlobalConstants.AUTH_USER_HEADER_ATTRIBUTE_NAME;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/sessions")
@RequiredArgsConstructor
@SecurityRequirement(name = AUTH_USER_HEADER_ATTRIBUTE_NAME)
public abstract class AbstractChatController {

	
}
