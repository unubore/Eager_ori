package com.eager.core.domain;

import java.util.Date;

import com.eager.core.util.StringUtil;

public class Tysysusr extends BaseDomain {
	private String username;
	private String password;
	private String name;
	private Long phonenumber;
	private String email;
	private int isAdmin;
	private int isLocked;
	private Tysysusrg group;
	private String remark;
	private int failureTimes;
	private Date validPeriod;
	private String previousLoginIp;
	private Date previousLoginTime;
	private String lastLoginIp;
	private Date lastLoginTime;
	private String dataSource;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Tysysusrg getGroup() {
		return group;
	}

	public void setGroup(Tysysusrg group) {
		this.group = group;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getFailureTimes() {
		return failureTimes;
	}

	public void setFailureTimes(int failureTimes) {
		this.failureTimes = failureTimes;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public Date getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(Date validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getPreviousLoginIp() {
		return previousLoginIp;
	}

	public void setPreviousLoginIp(String previousLoginIp) {
		this.previousLoginIp = previousLoginIp;
	}

	public Date getPreviousLoginTime() {
		return previousLoginTime;
	}

	public void setPreviousLoginTime(Date previousLoginTime) {
		this.previousLoginTime = previousLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public boolean canLogin() {
		if (StringUtil.isStringAvaliable(this.username) && StringUtil.isStringAvaliable(this.password)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canSave() {
		if (StringUtil.isStringAvaliable(username)) {
			return true;
		} else {
			return false;
		}

	}
}
