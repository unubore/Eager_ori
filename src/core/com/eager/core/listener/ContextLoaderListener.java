package com.eager.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
	private static ApplicationContext applicationContext;	

    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);

        ServletContext context = event.getServletContext();
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}	

}
