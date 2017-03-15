package com.eager.core.session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eager.core.constract.CacheNamePrefix;
import com.eager.core.constract.GridProperties;
import com.eager.core.dao.BaseDao;
import com.eager.core.domain.Session;
import com.eager.core.domain.Tysysusr;
import com.eager.core.jedis.JedisService;
import com.eager.core.util.CacheKeyGeneratorUtil;

@Repository("coreSessionDao")
public class SessionDaoImpl extends BaseDao implements SessionDao {
	@Autowired
	private JedisService jedisService;

	@Override
	public Session getSessionBySessionId(String sessionId) {
		Session session = (Session) jedisService
				.get(CacheKeyGeneratorUtil.generateCacheKeyFromString(Session.class, sessionId));
		if (session == null) {
			session = loadSessionFromDatabaseById(sessionId);
			if (session == null)
				return null;
			String cacheKey = CacheKeyGeneratorUtil.generateCacheKeyFromString(Session.class, session.getSessionId());
			cacheSession(session, cacheKey);
		}
		return session;
	}

	@Override
	public void deleteSessionBySessionId(String sessionId, Tysysusr tysysusr) {
		jedisService.remove(CacheKeyGeneratorUtil.generateCacheKeyFromString(Session.class, sessionId));
		getSqlSession().delete("core.session.deleteSessionBySessionId", sessionId);
		removeSession(tysysusr);

	}

	@Override
	public List<Session> findSessionByUserName(String userName) {
		return getSqlSession().selectList("core.session.findSessionByUserName", userName);
	}

	@Override
	public Session updateSessionHasLogined(String sessionId, boolean logined) {
		Session session = getSessionBySessionId(sessionId);
		if (session == null) {
			return null;
		}
		if (session.isLogin() == logined) {
			return session;
		} else {
			session.setLogin(logined);
		}
		jedisService.remove(CacheKeyGeneratorUtil.generateCacheKeyFromString(Session.class, sessionId));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", session.getId());
		map.put("login", logined);
		getSqlSession().update("core.session.updateSessionHasLogined", map);
		return session;
	}

	@Override
	public Session addSession(Session session) {
		getSqlSession().insert("core.session.addSession", session);
		session = loadSessionFromDatabaseById(session.getSessionId());

		String cacheKey = CacheKeyGeneratorUtil.generateCacheKeyFromString(Session.class, session.getSessionId());
		cacheSession(session, cacheKey);

		pushSession(session);

		return session;
	}

	private Session loadSessionFromDatabaseById(String sessionId) {
		return (Session) getSqlSession().selectOne("core.session.getSessionBySessionId", sessionId);
	}

	private void cacheSession(Session session, String cacheKey) {
		jedisService.set(cacheKey, (int) GridProperties.SESSION_TIME_OUT / 1000, session);
	}

	private void pushSession(Session session) {
		jedisService.setSet(CacheNamePrefix.ONLINEUSER_PREFIX, session.getTysysusrId());
	}

	private void removeSession(Tysysusr tysysusr) {
		jedisService.removeSet(CacheNamePrefix.ONLINEUSER_PREFIX, tysysusr.getId());
	}
	/*
	 * public Set<Long> findSessionsOnLine(boolean fromCache) { Set<Long>
	 * sessionMap = null; if (fromCache) { Set<Object> sessionMapTemp =
	 * jedisService.getSet(CacheNamePrefix.ONLINEUSER_PREFIX); if (null !=
	 * sessionMapTemp && sessionMapTemp.size() > 0) { sessionMap = new
	 * HashSet<Long>(); for (Object object : sessionMapTemp) {
	 * sessionMap.add((Long) object); } } }
	 * 
	 * if (fromCache && null != sessionMap && sessionMap.size() > 0) return
	 * sessionMap;
	 * 
	 * List<Session> sessions =
	 * getSqlSession().selectList("core.session.findSessionsOnLine");
	 * 
	 * if (null != sessions) { sessionMap = new HashSet<Long>(); for (Session
	 * session : sessions) { sessionMap.add(session.getTysysusrId());
	 * jedisService.setSet(CacheNamePrefix.ONLINEUSER_PREFIX,
	 * session.getTysysusrId()); } return sessionMap; } else { return null; }
	 * 
	 * }
	 */

	@Override
	public int deleteSessionsWhenTimeOut() {
		long time = Calendar.getInstance().getTime().getTime() - GridProperties.SESSION_TIME_OUT;
		int deleteCount = (int) getSqlSession().delete("core.session.deleteSessionsWhenTimeOut",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
		jedisService.remove(CacheNamePrefix.ONLINEUSER_PREFIX);
		return deleteCount;
	}
}
