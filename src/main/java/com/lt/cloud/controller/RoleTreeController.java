package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.cloud.service.RoleTreeService;
/**
 * 角色信息表
 * @author lt
 *
 */
@RestController
@RequestMapping("/user/roletree")
public class RoleTreeController{
	@Autowired
	private RoleTreeService  roleTreeService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.roleTreeService.findAll(receiver);
	}
	@RequestMapping("/findAllAstree")
	public String findAllAstree(@RequestBody String receiver) {
		return this.roleTreeService.findAllAstree(receiver);
	}
	@RequestMapping("/save")
	public String save(@RequestBody String entity) {
		
		return this.roleTreeService.save(entity);
	}

	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
		return this.roleTreeService.deleteById(id);
	}

	@RequestMapping("/exists")
	public boolean exists(@RequestBody String receiver) {
		
		return this.roleTreeService.exists(receiver);
	}
	@RequestMapping("/hasChildren/{id}")
	public boolean hasChildren(@PathVariable Long id) {
		return this.roleTreeService.hasChildren(id);
	}
	
}
