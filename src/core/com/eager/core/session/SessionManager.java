package com.eager.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eager.core.constract.LoginType;

public interface SessionManager {
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response);
	public LoginType login(HttpServletRequest request, HttpServletResponse response);
	public void logout(HttpServletRequest request, HttpServletResponse response);
}
