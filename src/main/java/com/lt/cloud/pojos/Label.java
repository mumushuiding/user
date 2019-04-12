package com.lt.cloud.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
/** 
* @ClassName: Label 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lt
* @date 2019年1月11日 下午4:53:01 
*  
*/
@Entity
public class Label implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:sss")
	private Date createdate;
	private Long creatorid;
	private String creatorname;
	/**
	 *  @Description:权限标签类型id
	 */
	private Long typeid;
	/**
	 *  @Description:权限标签名称name
	 */
	private String typename;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}

	
	
}
