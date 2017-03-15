package com.eager.core.session;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.eager.core.action.BaseAction;
import com.eager.core.constract.GlobalValue;
import com.eager.core.constract.LoginType;
import com.eager.core.domain.Session;

@Transactional
@Scope("request")
@Controller("sessionManageController")
public class SessionManageController extends BaseAction<Session> {
	private static final Logger logger = LoggerFactory.getLogger(SessionManageController.class);
	@Autowired
	private SessionManager sessionManager;

	@Action(value = "login", results = { @Result(name = "loginSuccess", type = "json", params = { "root", "true" }),
			@Result(name = "error", type = "json", params = { "root", "errorMessage" }) })
	public String login() {
		LoginType loginResult = null;
		try {
			loginResult = sessionManager.login(ServletActionContext.getRequest(), ServletActionContext.getResponse());
			errorMessage = (String) ServletActionContext.getRequest().getAttribute(GlobalValue.LOGIN_FAILURE_MSG);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return loginResult.name();
	}

	@Action(value = "logout", results = {
			@Result(name = "success", type = "redirect", location = "${#parameters.indexPath[0]}/login.jsp") })
	public String logout() {
		sessionManager.logout(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		return SUCCESS;
	}

}
