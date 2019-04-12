package com.lt.cloud.pojos;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NodeVisibleReceiver {
	private Long id;
	/**
	 * 用户id
	 */
	private Long userid;
	private String username;
	/**
	 * 用户可视节点,如: 福州日报社,部门,权限组
	 */
	private String nodes;
	/**
	 * depth 用户权限深度,用户只能操作深度大于自身的节点
	 */
	private Integer depth;
	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	private String creatorname;
	private Integer pageSize;
	private Integer pageIndex;
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
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
