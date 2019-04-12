package com.lt.cloud.pojos;


public class RoleReceiver{
	private Long creatorid;
	private String creatorname;
	private Long userid;//用户id
	/**
	 * 最后一个节点的id
	 */
	private Long nodevalue;
	private String nodeLabel;
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
	private Integer pageIndex;
	private Integer pageSize;
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
	public String getNodeLabel() {
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	

}
