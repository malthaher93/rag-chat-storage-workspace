package com.northbay.util;

public class ConfigHelper {

	public static String getProtocol(Boolean sslEnabled) {
		return Boolean.TRUE.equals(sslEnabled) ? "https://" : "http://";
	}

}
