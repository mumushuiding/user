package com.lt.cloud.pojos;

import java.util.ArrayList;
import java.util.List;

public abstract class PowerNode {
	private Long id;
	private String title; // 256 中文最多32位
	private Integer depth;
	private Long parentid;
	private String company;
	private String type;
	/**
	 * 若type=部门，charger为部门负责人
	 */
	private String charger; // 负责人
	
	public String getCharger() {
		return charger;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	List<PowerNode> children=new ArrayList<>();
	public PowerNode(Long id) {
		this.id=id;
	}
	public PowerNode(Long id, String title, Integer depth, Long parentid, String company) {
		this.id=id;
		this.title=title;
		this.depth=depth;
		this.parentid=parentid;
		this.company=company;
	}
	public PowerNode(Long id, String title, Integer depth, Long parentid, String company, String charger) {
		this.id=id;
		this.title=title;
		this.depth=depth;
		this.parentid=parentid;
		this.company=company;
		this.charger=charger;
	}
	// 添加子节点
	public void add(PowerNode node) {
		
	}
	// 移除子节点
	public void remove(PowerNode node) {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<PowerNode> getChildren() {
		return children;
	}
	public void setChildren(List<PowerNode> children) {
		this.children = children;
	}
	
}
