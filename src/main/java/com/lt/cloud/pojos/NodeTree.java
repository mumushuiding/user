package com.lt.cloud.pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 组织架构和人员
 * @author lt
 *
 */
@Entity
@Table(name="nodetree")
public class NodeTree {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Long parentid; // 父节点id
	private String type; // 节点类型
	private Long typeid;
	private Integer depth; // 节点深度
	private String company; // 公司节点
	/**
	 * 若type=部门，charger为部门负责人
	 */
	private String charger; // 负责人
	
	public String getCharger() {
		return charger;
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
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	
}
