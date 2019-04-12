package com.lt.cloud.pojos;
/**
*
* @author ****
* @date 2019年1月11日
*/
public class UserReceiver {
	private String labelname;
	private String labeltype;
	private Long userid;
	private String username;
	private Long systemid;
	private String systemname; //系统名
	private Long groupid;
	private String groupname; //权限组名
	private Long powerid;
	private String powername; //权限名
	private Integer pageIndex;
	private Integer pageSize;
	private String fields;
	private String company;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Long getSystemid() {
		return systemid;
	}
	public void setSystemid(Long systemid) {
		this.systemid = systemid;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public Long getPowerid() {
		return powerid;
	}
	public void setPowerid(Long powerid) {
		this.powerid = powerid;
	}
	public String getSystemname() {
		return systemname;
	}
	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getPowername() {
		return powername;
	}
	public void setPowername(String powername) {
		this.powername = powername;
	}
	public String getLabeltype() {
		return labeltype;
	}
	public void setLabeltype(String labeltype) {
		this.labeltype = labeltype;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
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
	public String getLabelname() {
		return labelname;
	}
	public void setLabelname(String labelname) {
		this.labelname = labelname;
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
	
	
}
