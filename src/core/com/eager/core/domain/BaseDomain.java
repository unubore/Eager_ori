package com.eager.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.eager.core.constract.GlobalValue;
import com.eager.core.util.StringUtil;
import com.eager.core.util.ThreadVariable;

abstract public class BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String createUser;
	protected Date createDate;
	protected String updateUser;
	protected Date updateDate;

	protected String databaseType;

	protected int indexPage;// 页码，默认是第一页
	protected int pageSize;// 每页显示的记录数，默认是20
	protected int allRows;// 总记录数
	protected int allPage;// 总页数
	protected String sortName;
	protected String sortType;
	protected boolean doPage;
	protected boolean doSort;

	public String getCreateUser() {
		if (!StringUtil.isStringAvaliable(createUser)) {
			if (null != ThreadVariable.getTysysusr()) {
				return ThreadVariable.getTysysusr().getUsername();
			}
		}
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		if (!StringUtil.isStringAvaliable(updateUser)) {
			updateUser = "admin";
		}
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int hashCode() {
		if (id != null) {
			final int prime = 31;
			int result = 1;
			result = prime * (prime * result + getClass().hashCode()) + id.hashCode();
			return result;
		}
		return super.hashCode();
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaseDomain)) {
			return false;
		}

		if ((getClass().isAssignableFrom(obj.getClass())) || (obj.getClass().isAssignableFrom(getClass()))) {

		} else {
			return false;
		}

		BaseDomain other = (BaseDomain) obj;
		if (other.getId() == null || getId() == null) {
			return false;
		} else {
			if (other.getId().equals(getId())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public int getPageSize() {
		if (this.pageSize == 0) {
			this.pageSize = 20;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isDoPage() {
		return doPage;
	}

	public void setDoPage(boolean doPage) {
		this.doPage = doPage;
	}

	public int getAllRows() {
		return allRows;
	}

	public void setAllRows(int allRows) {
		this.allRows = allRows;
		int allPage = allRows % pageSize == 0 ? allRows / pageSize : allRows / pageSize + 1;
		this.setAllPage(allPage);
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public boolean isDoSort() {
		return doSort;
	}

	public void setDoSort(boolean doSort) {
		this.doSort = doSort;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getDatabaseType() {
		return GlobalValue.DATABASE_TYPE;
	}

}
