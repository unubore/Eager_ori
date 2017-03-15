package com.eager.core.jedis;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jedisService")
public class JedisServiceImpl implements JedisService {
	// private static final String NAMESPACE_PREFIX = "namespace_cache_";

	@Autowired
	private JedisClusterClient JedisManager;

	@Override
	public Object get(String key) {
		try {
			return JedisManager.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			// 此处日志记录过于频繁，缓存没命中不影响程序正常运�?
			// logger.error(
			// "Exception occur when getCached value with key=" + key, e);
			return null;
		}
	}

	@Override
	public void set(String key, Object value) {
		if (null == key || null == value) {
			return;
		}
		JedisManager.set(key, value);
	}

	@Override
	public void set(String key, int seconds, final Object value) {
		if (null == key || null == value) {
			return;
		}
		JedisManager.set(key, value, seconds);
	}

	@Override
	public void remove(String key) {
		try {
			JedisManager.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("Exception occur when removeCached value with key="
			// + key, e);
		}
	}

	@Override
	public void setSet(String key, Object value) {
		if (null == key || null == value) {
			return;
		}
		JedisManager.sadd(key, value);

	}

	@Override
	public Set<Object> getSet(String key) {
		if (null == key) {
			return null;
		}
		return JedisManager.sget(key);
	}

	@Override
	public void removeSet(String key, Object value) {
		if (null == key || null == value) {
			return;
		}
		JedisManager.sremove(key, value);
	}

}
