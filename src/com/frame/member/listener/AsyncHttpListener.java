package com.frame.member.listener;

import com.frame.member.bean.ContractTerm;
import com.frame.member.bean.Plat;
import com.frame.member.bean.Regtime;

public abstract class AsyncHttpListener {
	private ContractTerm contractTerm;

	private Plat plat;
	private Regtime regtime;

	public Regtime getRegtime() {
		return regtime;
	}

	public void setRegtime(Regtime regtime) {
		this.regtime = regtime;
	}

	public abstract void onSucess(Object obj);

	public abstract void onFailur(Throwable e);

	public abstract void onJSONException(Throwable e);

	public ContractTerm getContractTerm() {
		return contractTerm;
	}

	public void setContractTerm(ContractTerm contractTerm) {
		this.contractTerm = contractTerm;
	}
	
	public Plat getPlat() {
		return plat;
	}

	public void setPlat(Plat plat) {
		this.plat = plat;
	}
}
