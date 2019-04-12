package com.lt.cloud.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="role")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	private Long creatorid;
	private String creatorname;
	private Long userid;//用户id
	/**
	 * 树形结构末端节点的id
	 */
	private Long nodevalue;
	private String nodelabel;
	/**
	 * typeid
	 */
	private Long typeid;
	/**
	 * 类型
	 */
	public String type;
	/**
	 * 深度
	 * @return
	 */
	private Integer depth;
	/**
	 * 用户所在公司
	 * @return
	 */
	private String company;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getNodelabel() {
		return nodelabel;
	}
	public void setNodelabel(String nodelabel) {
		this.nodelabel = nodelabel;
	}
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Long getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}
	public String getCreatorname() {
		return creatorname;
	}
	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getNodevalue() {
		return nodevalue;
	}
	public void setNodevalue(Long nodevalue) {
		this.nodevalue = nodevalue;
	}

	
	
}
