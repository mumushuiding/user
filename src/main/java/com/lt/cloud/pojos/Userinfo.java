package com.lt.cloud.pojos;

import java.util.List;


public class Userinfo {
	NodeInfo nodeInfo;
	User user;
	List<String> roles;
	List<String> permission;
	
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getPermission() {
		return permission;
	}
	public void setPermission(List<String> permission) {
		this.permission = permission;
	}
	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


	
	
}
