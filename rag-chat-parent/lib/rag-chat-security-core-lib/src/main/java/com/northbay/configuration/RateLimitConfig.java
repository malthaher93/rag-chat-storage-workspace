package com.northbay.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

@Configuration
public class RateLimitConfig {

	@Bean
	RateLimiter rateLimiter(RateLimiterConfig rateLimiterConfig) {
		return RateLimiter.of("globalLimiter", rateLimiterConfig);
	}

	@Bean
	RateLimiterConfig rateLimiterConfig(@Value("${ratelimit.configs.limit-for-period}") Integer limitCount, 
			@Value("${ratelimit.configs.timeout-duration}")  Long timeOut, 
			@Value("${ratelimit.configs.limit-refresh-period}") Long refreshPeriod) {

		return RateLimiterConfig.custom()
				.limitForPeriod(limitCount)               
				.limitRefreshPeriod(Duration.ofSeconds(refreshPeriod))
				.timeoutDuration(Duration.ofMillis(timeOut))
				.build();
	}
}
