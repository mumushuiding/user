package com.lt.cloud.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="nodevisible")
public class NodeVisible {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userid;
	private String username;
	/**
	 * 用户可视节点,userid-node: 一对多关系
	 */
	private Long nodeid; // 外键 同表label字段id相关联
	private String node;	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	private String creatorname;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getNodeid() {
		return nodeid;
	}
	public void setNodeid(Long nodeid) {
		this.nodeid = nodeid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getCreatorname() {
		return creatorname;
	}
	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	
	
}
