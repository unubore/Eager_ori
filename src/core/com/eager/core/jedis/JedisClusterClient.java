package com.eager.core.jedis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eager.core.util.SerializingUtil;

import redis.clients.jedis.JedisCluster;

@Repository("JedisManager")
public class JedisClusterClient {
	@Autowired
	private JedisCluster jedisCluster;

	public void delete(String key) {
		try {
			jedisCluster.del(SerializingUtil.serialize(key));
		} catch (Exception e) {

		}
	}

	public Object get(String key) {
		Object obj = new Object();
		try {
			obj = SerializingUtil.deserialize(jedisCluster.get(SerializingUtil.serialize(key)));
		} catch (Exception e) {
		}
		return obj;
	}

	public void set(String key, Object obj) {
		try {
			jedisCluster.set(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		}
	}

	public void set(String key, Object obj, int second) {
		try {
			jedisCluster.setex(SerializingUtil.serialize(key), second, SerializingUtil.serialize(obj));
		} catch (Exception e) {
		}
	}

	public void sadd(String key, Object obj) {
		try {
			jedisCluster.sadd(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		}
	}

	public void sremove(String key, Object obj) {
		try {
			jedisCluster.srem(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		}
	}

	public Set<Object> sget(String key) {
		Set<Object> obj = new HashSet<Object>();
		try {
			obj = SerializingUtil.deserialize(jedisCluster.smembers(SerializingUtil.serialize(key)));
		} catch (Exception e) {
		}
		return obj;
	}
}
