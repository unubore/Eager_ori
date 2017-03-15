package com.eager.core.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.eager.core.constract.GlobalValue;
import com.eager.core.domain.Tysysper;
import com.eager.core.domain.Tysysusr;
import com.eager.core.domain.vo.TysysusrVo;
import com.eager.core.exception.ServiceException;
import com.eager.core.service.TysysperService;
import com.eager.core.service.TysysusrService;
import com.eager.core.util.StringUtil;

@Controller("coreTysysusrController")
@Namespace("/core/tysysusrAction")
@Scope("prototype")
@Transactional
public class TysysusrAction extends BaseAction<Tysysusr> {
	private static Logger logger = LoggerFactory.getLogger(TysysusrAction.class);
	private String menuJson;
	private Long menuId;
	private Long tysysusrId;
	private Tysysusr tysysusr;
	private Tysysper tysysper;
	private TysysusrVo tysysusrVo;
	@Autowired
	private TysysusrService coreTysysusrService;
	@Autowired
	private TysysperService coreTysysperService;

	@Action(value = "dispatch", results = {
			@Result(name = "success", location = "/coreFeatures/tysysusr/dealTysysusrDlg.jsp") })
	public String dispatch() {
		if (!GlobalValue.OPTIONTYPE_ADD.equals(this.getOpt())) {
			if (tysysusrId != null) {
				tysysusr = coreTysysusrService.getTysysusrById(tysysusrId);
			} else {
				this.setOpt(GlobalValue.OPTIONTYPE_ADD);
			}
		}
		return SUCCESS;
	}

	@Action(value = "findTysysperByTysysusr", results = {
			@Result(type = "json", name = "success", params = { "root", "menuJson", "ignoreHierarchy", "false" }),
			@Result(name = "error", type = "json", params = { "root", "errorMessage" }) })
	public String findTysysperByTysysusr() {
		tysysusr = new Tysysusr();
		tysysusr.setIsAdmin(1);
		try {
			menuJson = JSONUtil.serialize(coreTysysperService.findTysyspersByTysysusr(tysysusr));
		} catch (JSONException e) {
			return ERROR;
		} catch (ServiceException e) {
			return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "getTysysperById", results = {
			@Result(type = "json", name = "success", params = { "root", "tysysper", "ignoreHierarchy", "false" }),
			@Result(name = "error", type = "json", params = { "root", "errorMessage" }) })
	public String getTysysperById() {
		if (menuId == null) {
			this.errorMessage = GlobalValue.ERROR_MESSAGE;
			return ERROR;
		}
		try {
			tysysper = coreTysysperService.getTysysperByid(menuId);
		} catch (ServiceException e) {
			this.errorMessage = e.getMessage();
			return ERROR;
		} catch (Exception e) {
			this.errorMessage = GlobalValue.ERROR_MESSAGE;
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "saveTheTysysusr", results = {
			@Result(type = "json", name = "success", params = { "root", "tysysusr", "ignoreHierarchy", "false" }),
			@Result(name = "error", type = "json", params = { "root", "errorMessage" }) })
	public String saveTheTysysusr() {
		try {
			if (!GlobalValue.OPTIONTYPE_ADD.equals(this.getOpt())) {
				tysysusr = coreTysysusrService.updateTysysusr(tysysusr);
			} else {
				tysysusr = coreTysysusrService.addTysysusr(tysysusr);
			}
		} catch (ServiceException e) {
			this.errorMessage = e.getMessage();
			return ERROR;
		} catch (Exception e) {
			this.errorMessage = GlobalValue.ERROR_MESSAGE;
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "goTemp", results = { @Result(name = "success", location = "/tmp.ftl") })
	public String goTemp() throws Exception {
		return SUCCESS;
	}

	@Action(value = "findAllTysysusrs", results = {
			@Result(type = "json", name = "success", params = { "root", "gridData", "ignoreHierarchy", "false" }) })
	public String findAllTysysusrs() {
		gridData = coreTysysusrService.findAllTysysusrs(tysysusrVo);
		return SUCCESS;
	}

	@Action(value = "deleteTysysusrsByIds", results = {
			@Result(type = "json", name = "success", params = { "root", "true", "ignoreHierarchy", "false" }),
			@Result(name = "error", type = "json", params = { "root", "errorMessage" }) })
	public String deleteTysysusrsByIds() {
		try {
			if (StringUtil.isStringAvaliable(ids)) {
				String[] idss = ids.split(",");
				for (String string : idss) {
					if (StringUtil.isStringAvaliable(string)) {
						coreTysysusrService.deleteTysysusrById(Long.parseLong(string));
					}
				}
			}
		} catch (ServiceException e) {
			this.errorMessage = e.getMessage();
			return ERROR;
		} catch (Exception e) {
			this.errorMessage = GlobalValue.ERROR_MESSAGE;
			return ERROR;
		}
		return SUCCESS;
	}

	public String getMenuJson() {
		return menuJson;
	}

	public void setMenuJson(String menuJson) {
		this.menuJson = menuJson;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Tysysusr getTysysusr() {
		return tysysusr;
	}

	public void setTysysusr(Tysysusr tysysusr) {
		this.tysysusr = tysysusr;
	}

	public Tysysper getTysysper() {
		return tysysper;
	}

	public void setTysysper(Tysysper tysysper) {
		this.tysysper = tysysper;
	}

	public TysysusrVo getTysysusrVo() {
		return tysysusrVo;
	}

	public void setTysysusrVo(TysysusrVo tysysusrVo) {
		this.tysysusrVo = tysysusrVo;
	}

	public Long getTysysusrId() {
		return tysysusrId;
	}

	public void setTysysusrId(Long tysysusrId) {
		this.tysysusrId = tysysusrId;
	}

}
