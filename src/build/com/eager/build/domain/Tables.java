package com.eager.build.domain;

import java.util.List;

import com.eager.core.domain.BaseDomain;
import com.eager.core.domain.Tysysper;

public class Tables extends BaseDomain{
	//表明
private String tableName;
//权限
private Tysysper tysysper;
//页面标题
private String pageTitle;
//字段设置
private List<Columns> ColumnsList;
//操作数据 筛选
private String selectSqlFilter;
//删除验证
private String deleteValidateFilter;
//是否新增
private boolean doAdd;
//是否修改
private boolean doUpdate;
//是否删除
private boolean doDelete;
public String getTableName() {
	return tableName;
}
public void setTableName(String tableName) {
	this.tableName = tableName;
}
public Tysysper getTysysper() {
	return tysysper;
}
public void setTysysper(Tysysper tysysper) {
	this.tysysper = tysysper;
}
public String getPageTitle() {
	return pageTitle;
}
public void setPageTitle(String pageTitle) {
	this.pageTitle = pageTitle;
}
public List<Columns> getColumnsList() {
	return ColumnsList;
}
public void setColumnsList(List<Columns> columnsList) {
	ColumnsList = columnsList;
}
public String getSelectSqlFilter() {
	return selectSqlFilter;
}
public void setSelectSqlFilter(String selectSqlFilter) {
	this.selectSqlFilter = selectSqlFilter;
}
public String getDeleteValidateFilter() {
	return deleteValidateFilter;
}
public void setDeleteValidateFilter(String deleteValidateFilter) {
	this.deleteValidateFilter = deleteValidateFilter;
}
public boolean isDoAdd() {
	return doAdd;
}
public void setDoAdd(boolean doAdd) {
	this.doAdd = doAdd;
}
public boolean isDoUpdate() {
	return doUpdate;
}
public void setDoUpdate(boolean doUpdate) {
	this.doUpdate = doUpdate;
}
public boolean isDoDelete() {
	return doDelete;
}
public void setDoDelete(boolean doDelete) {
	this.doDelete = doDelete;
}

}
