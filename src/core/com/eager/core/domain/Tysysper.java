package com.eager.core.domain;

import com.eager.core.util.StringUtil;

public class Tysysper extends BaseDomain {
	private String name;
	private String url;
	private Tysysper parent;
	private String showtext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Tysysper getParent() {
		return parent;
	}

	public void setParent(Tysysper parent) {
		this.parent = parent;
	}

	public String getShowtext() {
		return showtext;
	}

	public void setShowtext(String showtext) {
		this.showtext = showtext;
	}
	public boolean canSave(){
		if(StringUtil.isStringAvaliable(name)&&StringUtil.isStringAvaliable(showtext)){
			return true;
		}else{
			return false;
		}
	}
}
