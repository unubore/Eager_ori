package com.eager.core.interceptor;



import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class PermissionInterceptor implements Interceptor {
	private static final long serialVersionUID = -5178310397732210602L;
   
	public void destroy() {}
	
	public void init() {}
	
	public String intercept(ActionInvocation invocation) throws Exception {
		/*Object obj = ServletActionContext.getRequest().getSession().getAttribute("tysysusr");
		Object objMenu = ServletActionContext.getRequest().getSession().getAttribute("menuCheck");
	 	String result = "";
	 	//Tysysusr tysysusr=null;
	 	if(invocation.getProxy().getMethod().contains("query")||invocation.getProxy().getMethod().contains("egister")||invocation.getProxy().getMethod().contains("YC425")||invocation.getProxy().getMethod().contains("YC426")||invocation.getProxy().getMethod().contains("YC4211")||invocation.getProxy().getMethod().contains("showXQ")){
	 		result = invocation.invoke();
	 		return result;
	 		
	 	}
	 	if(obj instanceof Tysysusr){
		tysysusr= (Tysysusr) obj;
	 	}else{
	 		ServletActionContext.getRequest().setAttribute("login", "login");
			result = "login";	
			return result;
	 	}
	 	if(("M".equals(tysysusr.getEmpid())||"N".equals(tysysusr.getEmpid())||"Y".equals(tysysusr.getEmpid()))||(invocation.getProxy().getConfig().getClassName().toLowerCase().contains("lookup"))||invocation.getProxy().getConfig().getMethodName().toLowerCase().contains("register")||invocation.getProxy().getActionName().contains("report")){
	 		if(obj instanceof Tysysusr){
	 			result = invocation.invoke();
	 		 	}else{
	 		 		ServletActionContext.getRequest().setAttribute("login", "login");
	 				result = "login";	
	 				return result;
	 		 	}
	 	}else{
		 	if(obj!=null&&objMenu!=null&&objMenu instanceof String){
				String menu= (String) objMenu;
				String method=invocation.getProxy().getMethod();
				String Action=invocation.getProxy().getConfig().getClassName();
				if(method.contains("YC")){
					if(menu.contains(method.substring(method.indexOf("YC"), 6))||menu.contains(Action)){
						result = invocation.invoke();
					}else{
							ServletActionContext.getRequest().setAttribute("login", "login");
							result = "login";	
					}
				}else if(menu.contains(Action)){
					result = invocation.invoke();
				}else{
					ServletActionContext.getRequest().setAttribute("login", "login");
					result = "login";	
				}
		 	}else{
		 		ServletActionContext.getRequest().setAttribute("login", "login");
		 		result = "login";
		 	}
	 	}
	 	if(result==invocation.invoke()){
	 		
	 		ServletActionContext.getResponse().setHeader("Cache-Control","no-cache");

	 		ServletActionContext.getResponse().setHeader("Cache-Control","no-store");

	 		ServletActionContext.getResponse().setDateHeader("Expires", 0);

	 		ServletActionContext.getResponse().setHeader("Pragma","no-cache");
	 	}*/
		return  "login";
	 }
}