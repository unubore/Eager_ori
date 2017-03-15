package com.eager.core.jedis;

import java.util.Set;

public interface JedisService {
	/*
	 * int DEFAULT_EXPIRED_SECOND = 7200;
	 */
	Object get(String key);

	void set(String key, Object value);

	void set(String key, int expried, Object value);

	void remove(String key);

	void setSet(String key, Object value);

	void removeSet(String key, Object value);

	Set<Object> getSet(String key);

	/*
	 * Object get(String namespace, String key);
	 * 
	 * void set(String namespace, String key, Object value);
	 * 
	 * void set(String namespace, String key, int expried, Object value);
	 * 
	 * void remove(String namespace, String key);
	 * 
	 * void invalidateNamespaceCache(String namespace);
	 */
}
