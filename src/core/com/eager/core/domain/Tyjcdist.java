package com.eager.core.domain;

public class Tyjcdist extends BaseDomain {
	private String name;
	private Tyjcdlst tyjcdlst;
	private String showtext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tyjcdlst getTyjcdlst() {
		return tyjcdlst;
	}

	public void setTyjcdlst(Tyjcdlst tyjcdlst) {
		this.tyjcdlst = tyjcdlst;
	}

	public String getShowtext() {
		return showtext;
	}

	public void setShowtext(String showtext) {
		this.showtext = showtext;
	}

}
