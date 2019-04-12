package com.lt.cloud.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Groups implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long systemid;
	private String systemname;
	private Long groupid;
	private String groupname;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	private Long creatorid;
	private String creatorname;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
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
	public Long getSystemid() {
		return systemid;
	}
	public void setSystemid(Long systemid) {
		this.systemid = systemid;
	}
	public String getSystemname() {
		return systemname;
	}
	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}
	
	
	
}
