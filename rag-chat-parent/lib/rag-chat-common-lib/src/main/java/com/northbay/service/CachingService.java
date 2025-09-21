package com.northbay.service;

import java.util.function.Consumer;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachingService {

	private final CacheManager cacheManager;

	/***
	 * evict all
	 * 
	 * @return
	 */
	public String evictAll() {
		cacheManager.getCacheNames().stream().forEach(evict());
		return "DONE";
	}

	/***
	 * evict by name
	 * 
	 * @return
	 */
	private Consumer<? super String> evict() {
		return cacheName -> cacheManager.getCache(cacheName).clear();
	}
}
