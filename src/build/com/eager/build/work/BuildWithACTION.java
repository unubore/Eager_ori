package com.eager.build.work;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.eager.build.construc.BuildPorperty;
import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;

public class BuildWithACTION {
	
	public static void excute(Tables tables,List<Columns> columnList){
		String tablenamemulti=tables.getTableName().substring(0,1).toUpperCase()+tables.getTableName().substring(1).toLowerCase();	
		String url = BuildPorperty.BUILD_BASE_URL+"com\\eager\\plugin\\"+tables.getTysysper().getName()+"\\action\\"+tablenamemulti+"Action.java";
		
		try {
			File file = new File(url);  	
			if(file.exists()){
				file.delete();
			}
			new File(file.getParent()).mkdirs();  
		    file.createNewFile();  
			 
			BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), BuildPorperty.BUILD_CODE_TYPE));
			bw.write(getContentByBuild(tables,columnList,tablenamemulti).toString());		
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static StringBuffer getContentByBuild(Tables tables,List<Columns> columnList,String tablenamemulti){
		StringBuffer sb = new  StringBuffer("");
		sb.append("");sb.append("\r\n");
		sb.append("package com.eager.plugin."+tables.getTysysper().getName()+".action;");sb.append("\r\n");
		sb.append("");sb.append("\r\n");
		sb.append("import org.apache.struts2.convention.annotation.Action;");sb.append("\r\n");
		sb.append("import org.apache.struts2.convention.annotation.Namespace;");sb.append("\r\n");
		sb.append("import org.apache.struts2.convention.annotation.Result;");sb.append("\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;");sb.append("\r\n");
		sb.append("import org.springframework.context.annotation.Scope;");sb.append("\r\n");
		sb.append("import org.springframework.stereotype.Controller;");sb.append("\r\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;");sb.append("\r\n");
		sb.append("");sb.append("\r\n");
		sb.append("import com.eager.plugin."+tables.getTysysper().getName()+".domain."+tablenamemulti+";");sb.append("\r\n");
		sb.append("import com.eager.plugin."+tables.getTysysper().getName()+".domain.vo."+tablenamemulti+"Vo;");sb.append("\r\n");
		sb.append("import com.eager.core.exception.ServiceException;");sb.append("\r\n");
		sb.append("import com.eager.plugin."+tables.getTysysper().getName()+".service."+tablenamemulti+"Service;");sb.append("\r\n");
		sb.append("import com.eager.core.util.Globalvalue;");sb.append("\r\n");
		sb.append("import com.eager.core.util.StringUtil;");sb.append("\r\n");
		sb.append("");sb.append("\r\n");
		sb.append("@Controller(\""+tables.getTysysper().getName()+tablenamemulti+"Controller\")");sb.append("\r\n");
		sb.append("@Namespace(\"/"+tables.getTysysper().getName()+"/"+tablenamemulti.toLowerCase()+"Action\")");sb.append("\r\n");
		sb.append("@Scope(\"prototype\")");sb.append("\r\n");
		sb.append("@Transactional");sb.append("\r\n");
		sb.append("public class "+tablenamemulti+"Action extends BaseAction<"+tablenamemulti+"> {");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	private Long "+tablenamemulti.toLowerCase()+"Id;");sb.append("\r\n");
		sb.append("	private "+tablenamemulti+" "+tablenamemulti.toLowerCase()+";");sb.append("\r\n");
		sb.append("	private "+tablenamemulti+"Vo "+tablenamemulti.toLowerCase()+"Vo;");sb.append("\r\n");
		sb.append("	@Autowired");sb.append("\r\n");
		sb.append("	private "+tablenamemulti+"Service "+tables.getTysysper().getName()+tablenamemulti+"Service;");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	@Action(value = \"dispatch\", results = {");sb.append("\r\n");
		sb.append("			@Result(name = \"success\", location = \"/plugin/"+tables.getTysysper().getName()+"/"+tablenamemulti.toLowerCase()+"/deal"+tablenamemulti+"Dlg.jsp\") })");sb.append("\r\n");
		sb.append("	public String dispatch() {");sb.append("\r\n");
		sb.append("		if (!Globalvalue.OPTIONTYPE_ADD.equals(this.getOpt())) {");sb.append("\r\n");
		sb.append("			if ("+tablenamemulti.toLowerCase()+"Id != null) {");sb.append("\r\n");
		sb.append("				"+tablenamemulti.toLowerCase()+" = "+tables.getTysysper().getName()+tablenamemulti+"Service.get"+tablenamemulti+"ById("+tablenamemulti.toLowerCase()+"Id);");sb.append("\r\n");
		sb.append("			} else {");sb.append("\r\n");
		sb.append("				this.setOpt(Globalvalue.OPTIONTYPE_ADD);");sb.append("\r\n");
		sb.append("			}");sb.append("\r\n");
		sb.append("		}");sb.append("\r\n");
		sb.append("		return SUCCESS;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	@Action(value = \"saveThe"+tablenamemulti+"\", results = {");sb.append("\r\n");
		sb.append("			@Result(type = \"json\", name = \"success\", params = { \"root\", \""+tablenamemulti.toLowerCase()+"\", \"ignoreHierarchy\", \"false\" }),");sb.append("\r\n");
		sb.append("			@Result(name = \"error\", type = \"json\", params = { \"root\", \"errorMessage\" }) })");sb.append("\r\n");
		sb.append("	public String saveThe"+tablenamemulti+"() {");sb.append("\r\n");
		sb.append("		try {");sb.append("\r\n");
		sb.append("			if (!Globalvalue.OPTIONTYPE_ADD.equals(this.getOpt())) {");sb.append("\r\n");
		sb.append("				"+tablenamemulti.toLowerCase()+" = "+tables.getTysysper().getName()+tablenamemulti+"Service.update"+tablenamemulti+"("+tablenamemulti.toLowerCase()+");");sb.append("\r\n");
		sb.append("			} else {");sb.append("\r\n");
		sb.append("				"+tablenamemulti.toLowerCase()+" = "+tables.getTysysper().getName()+tablenamemulti+"Service.add"+tablenamemulti+"("+tablenamemulti.toLowerCase()+");");sb.append("\r\n");
		sb.append("			}");sb.append("\r\n");
		sb.append("		} catch (ServiceException e) {");sb.append("\r\n");
		sb.append("			this.errorMessage = e.getMessage();");sb.append("\r\n");
		sb.append("			return ERROR;");sb.append("\r\n");
		sb.append("		} catch (Exception e) {");sb.append("\r\n");
		sb.append("			this.errorMessage = Globalvalue.ERROR_MESSAGE;");sb.append("\r\n");
		sb.append("			return ERROR;");sb.append("\r\n");
		sb.append("		}");sb.append("\r\n");
		sb.append("		return SUCCESS;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	@Action(value = \"findAll"+tablenamemulti+"s\", results = {");sb.append("\r\n");
		sb.append("			@Result(type = \"json\", name = \"success\", params = { \"root\", \"gridData\", \"ignoreHierarchy\", \"false\" }) })");sb.append("\r\n");
		sb.append("	public String findAll"+tablenamemulti+"s() {");sb.append("\r\n");
		sb.append("		gridData = "+tables.getTysysper().getName()+tablenamemulti+"Service.findAll"+tablenamemulti+"s("+tablenamemulti.toLowerCase()+"Vo);");sb.append("\r\n");
		sb.append("		return SUCCESS;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	@Action(value = \"delete"+tablenamemulti+"sByIds\", results = {");sb.append("\r\n");
		sb.append("			@Result(type = \"json\", name = \"success\", params = { \"root\", \"true\", \"ignoreHierarchy\", \"false\" }),");sb.append("\r\n");
		sb.append("			@Result(name = \"error\", type = \"json\", params = { \"root\", \"errorMessage\" }) })");sb.append("\r\n");
		sb.append("	public String delete"+tablenamemulti+"sByIds() {");sb.append("\r\n");
		sb.append("		try {");sb.append("\r\n");
		sb.append("			if (StringUtil.isStringAvaliable(ids)) {");sb.append("\r\n");
		sb.append("				String[] idss = ids.split(\",\");");sb.append("\r\n");
		sb.append("				for (String string : idss) {");sb.append("\r\n");
		sb.append("					if (StringUtil.isStringAvaliable(string)) {");sb.append("\r\n");
		sb.append("						"+tables.getTysysper().getName()+tablenamemulti+"Service.delete"+tablenamemulti+"ById(Long.parseLong(string));");sb.append("\r\n");
		sb.append("					}");sb.append("\r\n");
		sb.append("				}");sb.append("\r\n");
		sb.append("			}");sb.append("\r\n");
		sb.append("		} catch (ServiceException e) {");sb.append("\r\n");
		sb.append("			this.errorMessage = e.getMessage();");sb.append("\r\n");
		sb.append("			return ERROR;");sb.append("\r\n");
		sb.append("		} catch (Exception e) {");sb.append("\r\n");
		sb.append("			this.errorMessage = Globalvalue.ERROR_MESSAGE;");sb.append("\r\n");
		sb.append("			return ERROR;");sb.append("\r\n");
		sb.append("		}");sb.append("\r\n");
		sb.append("		return SUCCESS;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public "+tablenamemulti+" get"+tablenamemulti+"() {");sb.append("\r\n");
		sb.append("		return "+tablenamemulti.toLowerCase()+";");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public void set"+tablenamemulti+"("+tablenamemulti+" "+tablenamemulti.toLowerCase()+") {");sb.append("\r\n");
		sb.append("		this."+tablenamemulti.toLowerCase()+" = "+tablenamemulti.toLowerCase()+";");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public "+tablenamemulti+"Vo get"+tablenamemulti+"Vo() {");sb.append("\r\n");
		sb.append("		return "+tablenamemulti.toLowerCase()+"Vo;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public void set"+tablenamemulti+"Vo("+tablenamemulti+"Vo "+tablenamemulti.toLowerCase()+"Vo) {");sb.append("\r\n");
		sb.append("		this."+tablenamemulti.toLowerCase()+"Vo = "+tablenamemulti.toLowerCase()+"Vo;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public Long get"+tablenamemulti+"Id() {");sb.append("\r\n");
		sb.append("		return "+tablenamemulti.toLowerCase()+"Id;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("	public void set"+tablenamemulti+"Id(Long "+tablenamemulti.toLowerCase()+"Id) {");sb.append("\r\n");
		sb.append("		this."+tablenamemulti.toLowerCase()+"Id = "+tablenamemulti.toLowerCase()+"Id;");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	");sb.append("\r\n");
		sb.append("}");sb.append("\r\n");
		return sb;
	}
}
