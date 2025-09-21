package com.northbay.configuration;

import static com.northbay.constants.GlobalConstants.AUTH_USER_HEADER_ATTRIBUTE_NAME;
import static com.northbay.constants.GlobalConstants.SECURITY_SCHEMA_DESCRIPTION;
import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	OpenAPI openAPI(@Value("${spring.application.name}") String appName,
			@Value("${spring.application.version}") String appVersion,
			@Value("${spring.application.description}") String appDescription) {
		return new OpenAPI().info(getAppInfo(appName, appVersion, appDescription))
				.components(securityHeaderComponent());
	}
	
	private Info getAppInfo(String appName, String appVersion, String appDescription) {
		return new Info().title(appName).version(appVersion).description(appDescription);
	}

	private Components securityHeaderComponent() {
		return new Components().addSecuritySchemes(AUTH_USER_HEADER_ATTRIBUTE_NAME,
				new SecurityScheme().type(APIKEY).in(HEADER)
						.scheme(AUTH_USER_HEADER_ATTRIBUTE_NAME)
						.name(AUTH_USER_HEADER_ATTRIBUTE_NAME)
						.description(SECURITY_SCHEMA_DESCRIPTION));
	}
}
