package com.frame.member.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Regtime implements  Serializable{
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}
}