package com.eager.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.eager.core.domain.Tysysper;
import com.eager.core.domain.TysysperCount;
import com.eager.core.service.TysysperCountService;
import com.eager.core.service.TysysperService;
import com.eager.core.util.ThreadVariable;

public class EagerModuelFilter implements Filter {

	private FilterConfig filterConfig;
	private TysysperService coreTysysperService;
	private TysysperCountService coreTysysperCountService;

	@Override
	public void destroy() {
		filterConfig = null;
		coreTysysperService = null;
		coreTysysperCountService = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String url = httpServletRequest.getServletPath();
		if(httpServletRequest.getQueryString().contains("&")){
			String [] temp = httpServletRequest.getQueryString().split("&");
			String parameter = new String();
			if(temp.length == 2){
				parameter = temp[0];
			} else if(temp.length > 2){
				parameter = temp[0];
				for (int i = 1; i < temp.length-1; i++) {
					parameter += temp[i];
				}
			}
			if(!(parameter==null||("").equals(parameter))){
				url = url+"?"+parameter;
			}
		}
		String newUrl = url.replaceFirst("/EagerModuel", "");
		if (!"".equals(newUrl)) {
			moduelClickCount(url);
//			httpServletResponse.sendRedirect(newUrl);
			request.getRequestDispatcher(newUrl).forward(request,response);
			return;
		}

		filterChain.doFilter(request, response);

	}

	private void moduelClickCount(String url) {
		Tysysper tysysper = coreTysysperService
				.findTysysperByUrl(url);
		if (tysysper != null) {
			TysysperCount tysysperCount = coreTysysperCountService
					.findTysysperCountByTysysperIdAndTysysusrId(tysysper
							.getId(), ThreadVariable.getSession().getTysysusrId());
			if (tysysperCount != null) {
				Long clickTimes = tysysperCount.getClickTimes() + 1;
				tysysperCount.setClickTimes(clickTimes);
				coreTysysperCountService.updateTysysperCount(tysysperCount);
			} else {
				tysysperCount = new TysysperCount();
				tysysperCount.setPermissionId(tysysper.getId());
				tysysperCount.setUserId(ThreadVariable.getSession()
						.getTysysusrId());
				tysysperCount.setClickTimes(1L);
				coreTysysperCountService.addTysysperCount(tysysperCount);
			}
		} else {
			return;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		getCoreTysysperService();
		getTysysperCountService();
	}

	public TysysperService getCoreTysysperService() {
		if (this.coreTysysperService == null) {
			WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(filterConfig.getServletContext());
			AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext
					.getAutowireCapableBeanFactory();
			this.coreTysysperService = (TysysperService) autowireCapableBeanFactory
					.getBean("coreTysysperService");
		}
		return coreTysysperService;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public TysysperCountService getTysysperCountService() {
		if (this.coreTysysperCountService == null) {
			WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(filterConfig.getServletContext());
			AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext
					.getAutowireCapableBeanFactory();
			this.coreTysysperCountService = (TysysperCountService) autowireCapableBeanFactory
					.getBean("coreTysysperCountService");
		}
		return coreTysysperCountService;
	}

}