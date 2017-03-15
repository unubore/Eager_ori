package com.eager.build.domain;

import com.eager.core.domain.BaseDomain;

public class Columns extends BaseDomain {
	// 英文行名
	private String columnName;
	// 中文注释
	private String comment;
	// 数据类型
	private String dataType;
	// 数据长度
	private int dataLength;
	// 数据精度
	private int dataScale;
	// 是否必填
	private boolean notNull;
	// 是否显示
	private boolean doShow;
	// 限制占宽
	private String showWidth;
	// 显示序列
	private int showQueue;
	// 是否编辑
	private boolean doEdit;
	// 编辑队列
	private int editQueue;
	// 编辑队列
	private String editWidth;
	// 是否查询条件
	private boolean doQuery;
	// 查询队列(日期类型占2个查询位)
	private int queryQueue;
	// 是否排序
	private boolean doSort;
	// 是否关联表
	private String relateTable;
	// 关联表显示用字段
	private String relateTableShowColumns;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public int getDataScale() {
		return dataScale;
	}
	public void setDataScale(int dataScale) {
		this.dataScale = dataScale;
	}
	public boolean isDoShow() {
		return doShow;
	}
	public void setDoShow(boolean doShow) {
		this.doShow = doShow;
	}
	public String getShowWidth() {
		return showWidth;
	}
	public void setShowWidth(String showWidth) {
		this.showWidth = showWidth;
	}
	public int getShowQueue() {
		return showQueue;
	}
	public void setShowQueue(int showQueue) {
		this.showQueue = showQueue;
	}
	public boolean isDoEdit() {
		return doEdit;
	}
	public void setDoEdit(boolean doEdit) {
		this.doEdit = doEdit;
	}
	public int getEditQueue() {
		return editQueue;
	}
	public void setEditQueue(int editQueue) {
		this.editQueue = editQueue;
	}
	public String getEditWidth() {
		return editWidth;
	}
	public void setEditWidth(String editWidth) {
		this.editWidth = editWidth;
	}
	public boolean isDoQuery() {
		return doQuery;
	}
	public void setDoQuery(boolean doQuery) {
		this.doQuery = doQuery;
	}
	public int getQueryQueue() {
		return queryQueue;
	}
	public void setQueryQueue(int queryQueue) {
		this.queryQueue = queryQueue;
	}
	public boolean isDoSort() {
		return doSort;
	}
	public void setDoSort(boolean doSort) {
		this.doSort = doSort;
	}
	public String getRelateTable() {
		return relateTable;
	}
	public void setRelateTable(String relateTable) {
		this.relateTable = relateTable;
	}
	public String getRelateTableShowColumns() {
		return relateTableShowColumns;
	}
	public void setRelateTableShowColumns(String relateTableShowColumns) {
		this.relateTableShowColumns = relateTableShowColumns;
	}
	public boolean isNotNull() {
		return notNull;
	}
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}


}
