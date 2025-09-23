package com.northbay.service;

import java.util.function.Consumer;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
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
		return cacheName -> evictByCacheName(cacheName);
	}

	public void evictByCacheName(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}
	
	
	public void evictByCacheNameAndKeys(String cacheName, Object ...keys) {
		cacheManager.getCache(cacheName).evict(new SimpleKey(keys));
	}
	
	public void evictByCacheNameAndKey(String cacheName, Object key) {
		cacheManager.getCache(cacheName).evict(new SimpleKey(key));
	}
}
