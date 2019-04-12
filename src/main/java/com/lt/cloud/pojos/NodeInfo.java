package com.lt.cloud.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="nodeinfo")
/**
 * 存储用户： 公司，权限深度
 * @author lt
 *
 */
public class NodeInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userid;
	private String username;
	/**
	 * depth 用户权限深度,用户只能操作深度大于自身的节点
	 */
	private Integer depth;
	/**
	 * company 用户所处公司节点
	 */
	private String company;
	/**
	 * department 用户所处部门完整节点
	 */
	private String department;
	private String departmentid;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	
}
