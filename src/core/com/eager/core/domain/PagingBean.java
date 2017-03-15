package com.eager.core.domain;

import java.util.List;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.eager.core.exception.DAOException;

public class PagingBean<T> extends BaseDomain {

	public PagingBean(BaseDomain bd) {
		this.setPageSize(bd.getPageSize());
		this.setIndexPage(bd.getIndexPage());
		this.setSortName(bd.getSortName());
		this.setSortType(bd.getSortType());
	}

	public PagingBean(BaseDomain bd, List<T> list) {
		this.setPageSize(bd.getPageSize());
		this.setIndexPage(bd.getIndexPage());
		this.setSortName(bd.getSortName());
		this.setSortType(bd.getSortType());
		this.setAllRows(bd.getAllRows());
		this.setdata(list);
	}

	private String dataJson;

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public void setdata(List<T> list) {
		try {
			this.setDataJson(JSONUtil.serialize(list));
		} catch (JSONException e) {
			throw new DAOException("JSON数据转化异常");
		}
	}

}