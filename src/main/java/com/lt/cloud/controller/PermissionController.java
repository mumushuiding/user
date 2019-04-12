package com.lt.cloud.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lt.cloud.service.PermissionListService;




@RestController
@RequestMapping("/user/permission")
public class PermissionController{
	@Autowired
	private PermissionListService permissionService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.permissionService.findAll(receiver);
	}
	/**
	 * 查询PermissionList
	 * @param receiver
	 * @return
	 */
	@RequestMapping("/hasPermission")
	public String hasPermission(@RequestParam String token,@RequestParam String url) {
		return this.permissionService.hasPermission(token,url);
	}
	@RequestMapping("/save")
	public Boolean save(@RequestBody String entity) {
		
		return this.permissionService.save(entity);
	}
	@RequestMapping("/update")
	public Boolean update(@RequestBody String entity) {
		
		return this.permissionService.update(entity);
	}
	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
		
		return this.permissionService.deleteById(id);
	}
	@RequestMapping("/exists")
	public boolean exists(@RequestBody String receiver) {
		
		return this.permissionService.exists(receiver);
	}
}
