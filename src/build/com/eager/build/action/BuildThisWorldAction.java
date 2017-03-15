package com.eager.build.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;
import com.eager.build.service.BuildPageService;
import com.eager.core.action.BaseAction;
import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusr;
import com.eager.core.exception.ServiceException;
import com.eager.core.service.TysysperService;
import com.eager.core.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("biuldBuildThisWorldController")
@Namespace("/biuld/buildThisWorldAction")
@Scope("prototype")
@Transactional
public class BuildThisWorldAction extends BaseAction<Columns> {
	private String tablename;
	private Tables tables;
	@Autowired
	private BuildPageService buildBuildPageService;
	
	@Action(value = "getAllpermissions", results = {
			@Result(type = "json", name = "success", params = { "root", "jsonData", "ignoreHierarchy", "false" }) })
	public String getAllpermissions(){
		Tysysusr tysysusr = new Tysysusr();
		tysysusr.setIsAdmin(1);
		try {
			jsonData = JSONUtil.serialize(buildBuildPageService.findAllpermissionsToList());
		} catch (JSONException e) {
			return ERROR;
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		
		return SUCCESS;
	}
	@Action(value = "getAllTables", results = {
			@Result(type = "json", name = "success", params = { "root", "stringList", "ignoreHierarchy", "false" }) })
	public String getAllTables(){
		try {
			List<Tables> tblist=buildBuildPageService.findAllTables();
			stringList=new ArrayList<String>();
			if(tblist.size()>0){
				for (Tables tables : tblist) {
					stringList.add(tables.getTableName());
				}
			}
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		
		return SUCCESS;
	}
	@Action(value = "getAllColumnsByTablename", results = {
			@Result(type = "json", name = "success", params = { "root", "gridData", "ignoreHierarchy", "false" }) })
	public String getAllColumnsByTablename(){
		try {
			gridData=new PagingBean<Columns>(this.baseDomain,buildBuildPageService.findAllColumns(tablename) );
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		
		return SUCCESS;
	}
	@Action(value = "getAllDlsts", results = {
			@Result(type = "json", name = "success", params = { "root", "jsonData", "ignoreHierarchy", "false" }) })
	public String getAllDlsts(){
		try {
			jsonData = JSONUtil.serialize(buildBuildPageService.findAllDlsts());
		} catch (JSONException e) {
			return ERROR;
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "getSimpleColumnsByTablename", results = {
			@Result(type = "json", name = "success", params = { "root", "stringList", "ignoreHierarchy", "false" }) })
	public String getSimpleColumnsByTablename(){
		if (!StringUtil.isStringAvaliable(tablename)) {
			return ERROR;
		}
		try {
			stringList=buildBuildPageService.findSimpleTableColumns(tablename);
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "buildSimpleTable", results = {
			@Result(type = "json", name = "success", params = { "root", "stringList", "ignoreHierarchy", "false" }) })
	public String buildSimpleTable(){
		
		try {
			JSONArray array = JSONArray.fromObject(jsonData);
			Object[] objArray=array.toArray();
			List<Columns> columnList= new ArrayList<Columns>();
			if(objArray.length>0){
				for (Object object : objArray) {
					columnList.add((Columns) JSONObject.toBean((JSONObject) object,Columns.class));
				}
			}
			buildBuildPageService.buildSimpleTable(tables,columnList);
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public Tables getTables() {
		return tables;
	}
	public void setTables(Tables tables) {
		this.tables = tables;
	}
}
