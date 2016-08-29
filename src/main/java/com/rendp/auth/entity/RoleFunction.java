package com.rendp.auth.entity;

import com.rendp.auth.common.BaseEntity;

public class RoleFunction extends BaseEntity {
	
	private Long roleId;
	private Long functionId;
	private Integer status;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
