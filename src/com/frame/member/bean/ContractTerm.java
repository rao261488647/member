package com.frame.member.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ContractTerm implements  Serializable{
	private String contractTermKey;
	private String contractTermVal;
	
	public String getContractTermKey() {
		return contractTermKey;
	}
	public void setContractTermKey(String contractTermKey) {
		this.contractTermKey = contractTermKey;
	}
	public String getContractTermVal() {
		return contractTermVal;
	}
	public void setContractTermVal(String contractTermVal) {
		this.contractTermVal = contractTermVal;
	}
	
	@Override
	public String toString() {
		return contractTermKey;
	}
}