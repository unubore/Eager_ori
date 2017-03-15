package com.eager.core.session;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eager.core.constract.CacheNamePrefix;
import com.eager.core.constract.GlobalValue;
import com.eager.core.constract.GridProperties;
import com.eager.core.constract.LoginType;
import com.eager.core.domain.Session;
import com.eager.core.domain.Tysysusr;
import com.eager.core.jedis.JedisManager;
import com.eager.core.service.TysysusrService;
import com.eager.core.util.CookieUtil;
import com.eager.core.util.ThreadVariable;

@Component("sessionManager")
public class SessionManagerImpl implements SessionManager {
	private final static Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);
	@Autowired
	private SessionDao coreSessionDao;
	@Autowired
	private TysysusrService coreTysysusrService;
	@Autowired
	private JedisManager jedisManager;

	@Override
	@Transactional
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = request.getParameter(GlobalValue.SESSION_ID);
		if (sessionId == null || "".equals(sessionId.trim())) {
			sessionId = CookieUtil.getSesssionIdFromCookies(request);
		}
		Session session = coreSessionDao.getSessionBySessionId(sessionId);
		if (session == null || null == session.getSessionId()) {
			return false;
		}

		if (!session.isLogin()) {
			return false;
		}

		/* 注释该代码 在dao的位置删除在线用户 */
		if (isTimeOut(session)) {
			/*
			 * if (request.getRequestURI().endsWith(".dwr")){ return false; }
			 */
			coreSessionDao.deleteSessionBySessionId(sessionId, ThreadVariable.getTysysusr());
			return false;
		}

		ThreadVariable.setSession(session);
		Tysysusr tysysusr = coreTysysusrService.getTysysusrById(session.getTysysusrId());
		ThreadVariable.setTysysusr(tysysusr);
		ThreadVariable.setDataSource(tysysusr.getDataSource());
		CookieUtil.putSessionIdInCookies(request, response, GlobalValue.SESSION_ID, session.getSessionId());
		return true;
	}

	private boolean isTimeOut(Session session) {
		if ((Calendar.getInstance().getTime().getTime()
				- session.getAccessTime().getTime()) > GridProperties.SESSION_TIME_OUT) {
			logger.info("用户：{}Session失效，上一次访问时间{}", session.getUserName(), session.getAccessTime());
			return true;
		}
		return false;
	}

	@Override
	public LoginType login(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String validateCode = request.getParameter("validateCode");

		String sso = (String) request.getAttribute("sso");

		jedisManager.delete(userName + "_" + password);

		if (sso != null && !"".equals(sso)) {
			if ("admin".equals(userName)) {
				return LoginType.loginFailure;
			}
			password = "password";
		}

		return proccessLogin(request, response, userName, password, validateCode);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = getSessionIdFromCookie(request);
		coreSessionDao.deleteSessionBySessionId(sessionId, ThreadVariable.getTysysusr());
		/*
		 * systemLogService.log("用户登出", ModelType.LOGGIN, OperatorType.LOGINUP);
		 * systemLoginLogService.log("用户登出", 0);
		 */
		ThreadVariable.setSession(null);
		clearValueInCookies(response, GlobalValue.SESSION_ID, sessionId);
		jedisManager.delete(CacheNamePrefix.TYSYSPER_BY_TYSYSUSRID + ThreadVariable.getTysysusr().getId());
	}

	private LoginType proccessLogin(HttpServletRequest request, HttpServletResponse response, String userName,
			String password, String validateCode) {
		clearSessionIdFromCookie(request, response);
		if (!validateParameters(userName, password)) {
			return LoginType.loginFailure;
		}

		setParametersToRequest(request, userName, password, validateCode);

		Tysysusr tysysusr = getTysysusrByUsername(request, userName);

		if (null == tysysusr || !compareUserWithParameters(tysysusr, request, password, validateCode)) {
			return LoginType.loginFailure;
		}

		proccessLoginSuccess(request, response, tysysusr);

		return LoginType.loginSuccess;
	}

	private void clearSessionIdFromCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		// String sessionId = null;
		if (cookies == null)
			return;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(GlobalValue.SESSION_ID)) {
				String sid = cookie.getValue();
				if (sid != null && !"".equals(sid.trim()))
					clearValueInCookies(response, GlobalValue.SESSION_ID, sid);
				// break;
			}
		}

	}

	private void clearValueInCookies(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private boolean validateParameters(String userName, String password) {
		if (userName == null || password == null) {
			return false;
		}
		return true;
	}

	private String getSessionIdFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(GlobalValue.SESSION_ID)) {
				return cookie.getValue();
			}
		}
		return "";
	}

	private void setParametersToRequest(HttpServletRequest request, String userName, String password,
			String validateCode) {
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		request.setAttribute("validateCode", validateCode);
	}

	private Tysysusr getTysysusrByUsername(HttpServletRequest request, String userName) {
		Tysysusr tysysusr = coreTysysusrService.getTysysusrByUsername(userName);
		if (tysysusr == null) {
			/*
			 * systemLogService.log("用户登录失败，不存在用户名:" + userName,
			 * ModelType.LOGGIN, OperatorType.LOGIN, SystemLog.WARN,
			 * getIpAddr(request)); systemLoginLogService.log("用户登录失败，不存在用户名:" +
			 * userName, 0);
			 */
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG, "{userName:'用户名或密码错误'}");
			return null;
		}
		return tysysusr;
	}

	private void proccessLoginSuccess(HttpServletRequest request, HttpServletResponse response, Tysysusr tysysusr) {
		fireLoginedUser(tysysusr.getUsername());
		generateLoginedSession(request, response, tysysusr);
		updateLoginUser(request, tysysusr);
		/*
		 * systemLogService.log("用户登录成功!", ModelType.LOGGIN, OperatorType.LOGIN,
		 * loginUserType); systemLoginLogService.log("用户登录成功!", loginUserType);
		 */
		if (tysysusr.getFailureTimes() > 0) {
			tysysusr.setFailureTimes(0);
			coreTysysusrService.updateFailureTimesById(tysysusr.getId(), tysysusr.getFailureTimes());
		}
	}

	private void fireLoginedUser(String userName) {
		List<Session> sessions = coreSessionDao.findSessionByUserName(userName);
		for (Session session : sessions) {
			coreSessionDao.updateSessionHasLogined(session.getSessionId(), false);
		}

	}

	private boolean compareUserWithParameters(Tysysusr tysysusr, HttpServletRequest request, String password,
			String validateCode) {
		/*
		 * String tqmobile = request.getParameter("tqmobile"); if
		 * ("true".equals(tqmobile) && !user.isMobileusable()) {
		 * request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG,
		 * "{userName:'用户登录失败，该用户不能通过手机登入'}"); return false; } if
		 * (!"true".equals(tqmobile) && !user.isPcusable()) {
		 * systemLogService.log("用户登录失败，该用户不能通过PC登入:" + user.getUserName(),
		 * ModelType.LOGGIN, OperatorType.LOGIN, SystemLog.WARN,
		 * getIpAddr(request)); systemLoginLogService.log("用户登录失败，该用户不能通过PC登入:"
		 * + user.getUserName(), 0);
		 * request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG,
		 * "{userName:'用户登录失败，该用户不能通过PC登入'}"); return false; }
		 */
		if (tysysusr.getIsLocked() == 1) {
			/*
			 * systemLogService.log("用户登录失败，该用户已被锁定:" + user.getUserName(),
			 * ModelType.LOGGIN, OperatorType.LOGIN, SystemLog.WARN,
			 * getIpAddr(request)); systemLoginLogService.log("用户登录失败，该用户已被锁定:"
			 * + user.getUserName(), 0);
			 */
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG, "{userName:'用户登录失败，该用户已被锁定'}");
			return false;
		}
		Calendar validPeriod = Calendar.getInstance();
		validPeriod.setTime(tysysusr.getValidPeriod());
		if (validPeriod.compareTo(Calendar.getInstance()) == -1) {
			/*
			 * systemLogService.log("用户登录失败，该用户已被禁用:" + user.getUserName(),
			 * ModelType.LOGGIN, OperatorType.LOGIN, SystemLog.WARN,
			 * getIpAddr(request)); systemLoginLogService.log("用户登录失败，该用户已被禁用:"
			 * + user.getUserName(), 0);
			 */
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG, "{userName:'用户登录失败，该用户已过期'}");
			return false;
		}

		if (!validatePassword(tysysusr, password)) {
			/*
			 * systemLogService.log("用户登录失败，密码错误！ 用户名为:" + user.getUserName(),
			 * ModelType.LOGGIN, OperatorType.LOGIN, SystemLog.WARN,
			 * getIpAddr(request));
			 * systemLoginLogService.log("用户登录失败，密码错误！ 用户名为:" +
			 * user.getUserName(), 0);
			 */
			request.setAttribute("tysysusr", tysysusr);
			proccessFailureTimeslimit(request, tysysusr);
			return false;
		}

		return true;
	}

	private boolean validatePassword(Tysysusr tysysusr, String password) {
		if (password == null) {
			return false;
		}
		if (!password.equals(tysysusr.getPassword())) {
			return false;
		}
		return true;
	}

	private void proccessFailureTimeslimit(HttpServletRequest request, Tysysusr tysysusr) {
		if (tysysusr.getIsAdmin() == 1) {
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG, "{userName:'用户名或密码错误'}");
			return;
		}
		tysysusr.setFailureTimes(tysysusr.getFailureTimes() + 1);
		coreTysysusrService.updateFailureTimesById(tysysusr.getId(), tysysusr.getFailureTimes());

		if (tysysusr.getFailureTimes() >= 5) {
			tysysusr.setIsLocked(1);
			coreTysysusrService.lockTysysusrByid(tysysusr.getId());
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG,
					"{userName:'用户登录失败，密码错误！您登录失败次数超过5次已被锁定，请与管理员联系',failureTimes:'" + tysysusr.getFailureTimes()
							+ "'}");
		} else {
			request.setAttribute(GlobalValue.LOGIN_FAILURE_MSG,
					"{userName:'用户登录失败，密码错误！您已有" + tysysusr.getFailureTimes() + "次登录失败，超过5次之后将被锁定',failureTimes:'"
							+ tysysusr.getFailureTimes() + "'}");
		}
	}

	private void generateLoginedSession(HttpServletRequest request, HttpServletResponse response, Tysysusr tysysusr) {
		String sessionId = CookieUtil.getSesssionIdFromCookies(request);
		if (sessionId != null) {
			coreSessionDao.deleteSessionBySessionId(sessionId, tysysusr);
		}

		Session session = addSession(tysysusr.getId(), tysysusr.getUsername(), tysysusr.getName(), getIpAddr(request),
				request.getRequestURI(), true, getIpAddr(request));
		ThreadVariable.setSession(session);
		CookieUtil.putSessionIdInCookies(request, response, GlobalValue.SESSION_ID, session.getSessionId());
	}

	private Session addSession(Long userId, String userName, String userRealName, String loginIp, String lastUrl,
			boolean isLogin, String accessIp) {
		Session session = new Session();
		session.setLoginIp(loginIp);
		session.setUserName(userName);
		session.setUserRealName(userRealName);
		session.setAccessTime(Calendar.getInstance().getTime());
		String randomUUId = UUID.randomUUID().toString();
		session.setSessionId(randomUUId);
		session.setLogin(isLogin);
		session.setLastUrl(lastUrl);
		session.setAccessIp(accessIp);
		session.setTysysusrId(userId);
		if (isLogin) {
			session.setLoginDate(Calendar.getInstance().getTime());
		}
		return addSession(session);
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public Session addSession(Session session) {
		if (session.getAccessTime().getTime() + GridProperties.SESSION_TIME_OUT < System.currentTimeMillis()) {
			return null;
		}
		return coreSessionDao.addSession(session);
	}

	private void updateLoginUser(HttpServletRequest request, Tysysusr tysysusr) {
		tysysusr.setPreviousLoginIp(tysysusr.getLastLoginIp());
		tysysusr.setPreviousLoginTime(tysysusr.getLastLoginTime());
		tysysusr.setLastLoginIp(getIpAddr(request));
		tysysusr.setLastLoginTime(Calendar.getInstance().getTime());
		coreTysysusrService.updateTysysusrForLogin(tysysusr);
	}
}
