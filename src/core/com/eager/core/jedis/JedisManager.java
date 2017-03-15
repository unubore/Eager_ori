package com.eager.core.jedis;

import java.util.HashSet;
import java.util.Set;

import com.eager.core.util.SerializingUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/*暂时没有任何用处
@Repository("jedisManager")*/
public class JedisManager {
	private JedisPool jedisPool;
	private Jedis jedis;

	private Jedis getConnection() {
		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		return jedis;
	}

	private void closeConnection() {
		if (jedis != null) {
			jedis.close();
		}
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void delete(String key) {
		try {
			jedis = getConnection();
			jedis.del(SerializingUtil.serialize(key));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	public Object get(String key) {
		Object obj = new Object();
		try {
			jedis = getConnection();
			obj = SerializingUtil.deserialize(jedis.get(SerializingUtil.serialize(key)));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
		return obj;
	}

	public void set(String key, Object obj) {
		try {
			jedis = getConnection();
			jedis.set(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	public void set(String key, Object obj, int second) {
		try {
			jedis = getConnection();
			jedis.setex(SerializingUtil.serialize(key), second, SerializingUtil.serialize(obj));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	public void sadd(String key, Object obj) {
		try {
			jedis = getConnection();
			jedis.sadd(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	public void sremove(String key, Object obj) {
		try {
			jedis = getConnection();
			jedis.srem(SerializingUtil.serialize(key), SerializingUtil.serialize(obj));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	public Set<Object> sget(String key) {
		Set<Object> obj = new HashSet<Object>();
		try {
			jedis = getConnection();
			obj = SerializingUtil.deserialize(jedis.smembers(SerializingUtil.serialize(key)));
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
		return obj;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
