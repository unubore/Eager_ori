package com.eager.core.session;

import java.util.List;

import com.eager.core.domain.Session;
import com.eager.core.domain.Tysysusr;

public interface SessionDao {
	Session getSessionBySessionId(String sessionId);

	void deleteSessionBySessionId(String sessionId, Tysysusr tysysusr);

	Session addSession(Session session);

	List<Session> findSessionByUserName(String username);

	Session updateSessionHasLogined(String sessionId, boolean logined);

	int deleteSessionsWhenTimeOut();
}
