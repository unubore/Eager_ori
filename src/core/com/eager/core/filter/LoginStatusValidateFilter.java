package com.eager.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.eager.core.constract.GlobalValue;
import com.eager.core.service.TysysperService;
import com.eager.core.session.SessionManager;
import com.eager.core.util.ThreadVariable;

//import com.eager.core.dao.SessionManager;

public class LoginStatusValidateFilter implements Filter {

	private FilterConfig filterConfig;
	private TysysperService coreTysysperService;
	private SessionManager sessionManager;
	private static Logger logger = LoggerFactory.getLogger(LoginStatusValidateFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		Long startTime = System.currentTimeMillis();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String url = request.getRequestURI();

		chain.doFilter(servletRequest, servletResponse);

		if (url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".css") || url.endsWith(".js")
				|| url.endsWith(".png") || url.endsWith(".bmp") || url.endsWith(".ico") || url.endsWith("1.txt")
				|| url.endsWith(".3gpp") || url.endsWith(".3gp") || url.endsWith(".apk") || url.endsWith(".mp4")
				|| url.endsWith(".exe")) {
			chain.doFilter(servletRequest, servletResponse);
			ThreadVariable.clearThreadVariable();
			return;
		}

		if (isNotLoginValidate(url, request)) {
			chain.doFilter(servletRequest, servletResponse);
			ThreadVariable.clearThreadVariable();
			return;
		}

		if (!getSessionManager().isLogin(request, response)) {
			String errorMessage = "您的帐号已失效或者已经在别的地方登录!";
			response.setContentType("text/html");
			response.getWriter()
					.print("<script>document.location.href='/login.jsp?errorMessage=" + errorMessage + "'</script>");
			ThreadVariable.clearThreadVariable();
			return;
		} else {
			chain.doFilter(servletRequest, servletResponse);
			ThreadVariable.clearThreadVariable();
			long processTime = System.currentTimeMillis() - startTime;
			if (processTime > 3000) {
				if (logger.isDebugEnabled()) {
					logger.info("花了 {} 时间访问 {}", new Object[] { processTime, url });
				}

			}
			return;
		}

	}

	@Override
	public void destroy() {
		filterConfig = null;
		// sessionManager = null;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	private SessionManager getSessionManager() {
		if (this.sessionManager == null) {
			WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(filterConfig.getServletContext());
			AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
			this.sessionManager = (SessionManager) autowireCapableBeanFactory.getBean("sessionManager");
		}
		return sessionManager;
	}

	private TysysperService getTysysperService() {
		if (this.coreTysysperService == null) {
			WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(filterConfig.getServletContext());
			AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
			this.coreTysysperService = (TysysperService) autowireCapableBeanFactory.getBean("coreTysysperService");
		}
		return coreTysysperService;
	}

	private boolean isNotLoginValidate(String url, HttpServletRequest request) {
		for (String path : GlobalValue.IS_NOT_LOGIN_VALIDATE_PATH) {
			if (url.equals(request.getContextPath() + path)) {
				return true;
			}
		}
		if (url.endsWith("/login.jsp")) {
			return true;
		}
		return false;
	}
}
