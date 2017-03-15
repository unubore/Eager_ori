package com.eager.core.freemarker;

import javax.servlet.ServletException;

import freemarker.ext.servlet.FreemarkerServlet;

/**
 * @ClassName: EagerFreemarkerServlet
 * @Description: 自定义freemarker页面请求
 * @author 
 * @date 2013-12-19 上午10:36:39
 * 
 */
public class EagerFreemarkerServlet extends FreemarkerServlet {
	public void init() throws ServletException {
		super.init();
		this.getConfiguration().setTemplateExceptionHandler(
				new EagerTemplateExceptionHandler());
	}
}
