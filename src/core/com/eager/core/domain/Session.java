package com.eager.core.domain;

import java.util.Date;

public class Session extends BaseDomain {
	private String sessionId;
	private Long tysysusrId;
	private String userName;
	private String userRealName;
	// private Organization organization;
	private String accessIp;
	private Date accessTime;
	private boolean login = false;
	private Date loginDate;
	private String lastUrl;
	private String loginIp;
	private String validateCode;

	// private String orgInternalCode;
	// private Integer userType;
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getTysysusrId() {
		return tysysusrId;
	}

	public void setTysysusrId(Long tysysusrId) {
		this.tysysusrId = tysysusrId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLastUrl() {
		return lastUrl;
	}

	public void setLastUrl(String lastUrl) {
		this.lastUrl = lastUrl;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

}
