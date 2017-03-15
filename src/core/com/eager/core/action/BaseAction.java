package com.eager.core.action;

import java.util.List;

import com.eager.core.domain.PagingBean;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction<T> extends ActionSupport {
	// 操作信息
	protected String errorMessage;
	// 操作方式
	protected String opt;
	protected String ids;
	protected PagingBean<T> gridData;
	protected T baseDomain;
	protected String jsonData;
	protected List<String> stringList;
	protected List<Object> objectList;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public PagingBean<T> getGridData() {
		return gridData;
	}

	public void setGridData(PagingBean<T> gridData) {
		this.gridData = gridData;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public T getBaseDomain() {
		return baseDomain;
	}

	public void setBaseDomain(T baseDomain) {
		this.baseDomain = baseDomain;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public List<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

}
