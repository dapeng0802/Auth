package com.rendp.auth.entity;

import com.rendp.auth.common.BaseEntity;

public class Role extends BaseEntity {
	
	private String name;
	private String functionIds;
	public String getFunctionIds() {
		return functionIds;
	}
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
