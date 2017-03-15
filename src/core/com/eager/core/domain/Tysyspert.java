package com.eager.core.domain;

public class Tysyspert extends BaseDomain {
	private Tysysusrg userg;
	private Tysysper perm;

	public Tysysusrg getUserg() {
		return userg;
	}

	public void setUser(Tysysusrg userg) {
		this.userg = userg;
	}

	public Tysysper getPerm() {
		return perm;
	}

	public void setPerm(Tysysper perm) {
		this.perm = perm;
	}

}
