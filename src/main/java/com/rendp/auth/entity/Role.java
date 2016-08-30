package com.rendp.auth.entity;

import com.rendp.auth.common.BaseEntity;

public class Role extends BaseEntity {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
