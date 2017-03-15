package com.eager.core.util;

import java.util.HashMap;
import java.util.Map;

import com.eager.core.constract.GlobalValue;
import com.eager.core.domain.Session;
import com.eager.core.domain.Tysysusr;

public class ThreadVariable {
	private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

	public static void clearThreadVariable() {
		threadLocal.remove();
	}

	public static void setSession(Object session) {
		Map map = (Map) threadLocal.get();
		if (map == null) {
			map = new HashMap();
		}
		map.put(GlobalValue.SESSION_ID, session);
		threadLocal.set(map);
	}

	public static Session getSession() {
		Map map = (Map) threadLocal.get();
		if (map != null) {
			return (Session) map.get(GlobalValue.SESSION_ID);
		}
		return null;
	}

	public static void setTysysusr(Tysysusr tysysusr) {
		Map map = (Map) threadLocal.get();
		if (map == null) {
			map = new HashMap();
		}
		map.put(GlobalValue.USER_ID, tysysusr);
		threadLocal.set(map);
	}

	public static Tysysusr getTysysusr() {
		Map map = (Map) threadLocal.get();
		if (map != null) {
			return (Tysysusr) map.get(GlobalValue.USER_ID);
		}
		return null;
	}

	public static void setDataSource(String DataSource) {
		Map map = (Map) threadLocal.get();
		if (map == null) {
			map = new HashMap();
		}
		map.put(GlobalValue.DATA_KEY, DataSource);
		threadLocal.set(map);
	}

	public static String getDataSource() {
		Map map = (Map) threadLocal.get();
		if (map != null) {
			return (String) map.get(GlobalValue.DATA_KEY);
		}
		return null;
	}
}