package com.eager.core.domain;

public class TysysperCount extends BaseDomain{
	private Long userId;
	private Long permissionId;
	private Long clickTimes;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Long getClickTimes() {
		return clickTimes;
	}
	public void setClickTimes(Long clickTimes) {
		this.clickTimes = clickTimes;
	}
	
}
