package com.lt.cloud.pojos;

public class NodeTreeReceiver {
	private String title;
	private Long parentid; // 父节点id
	private String type; // 节点类型
	private Long typeid;
	private Integer depth; // 节点深度
	/**
	 * 节点深度大于等于depthGe
	 */
	private Integer depthGe;
	/**
	 * compay 查询： 当 type=公司 时 title=company 
	 * @return
	 */
	private String company;
	
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Integer getDepthGe() {
		return depthGe;
	}
	public void setDepthGe(Integer depthGe) {
		this.depthGe = depthGe;
	}
	
}
