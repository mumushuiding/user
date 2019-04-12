package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lt.cloud.service.RoleService;
import com.lt.cloud.utils.ResponseCodeUtils;
@RestController
@RequestMapping("/user/role")
public class RoleController{
	@Autowired
	private RoleService roleService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.roleService.findAll(receiver);
	}
	@RequestMapping("/findRolesAsTree")
	public String findRolesAsTree(@RequestBody String json) {
		return this.roleService.findRolesAsTree(json);
	}
	@RequestMapping("/save")
	public String save(@RequestBody String entity) {
		
		return ResponseCodeUtils.response(this.roleService.save(entity));
	}

	@RequestMapping("/deleteById/{id}")
	public String deleteById(@PathVariable Long id) {
		
		return ResponseCodeUtils.response(this.roleService.deleteById(id));
	}
	@RequestMapping("/exists")
	public boolean exists(@RequestBody String json) {
		return this.roleService.exists(json);
	}
	@RequestMapping("/existsRole")
	public boolean existsRole(@RequestBody String json) {
		return this.roleService.existsRole(json);
	}
}
