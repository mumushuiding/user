package com.lt.cloud.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.cloud.service.NodeInfoService;


/**
 * 用户在组织中的位置，涉及：公司、部门、权限深度
 * @author lt
 *
 */
@RestController
@RequestMapping("/user/nodeinfo")
public class NodeInfoController {
	@Autowired
	private NodeInfoService  nodeDepthService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.nodeDepthService.findAll(receiver);
	}

	@RequestMapping("/save")
	public Boolean save(@RequestBody String entity) {

		return this.nodeDepthService.save(entity);
	}
	@RequestMapping("/update")
	public boolean Update(@RequestBody String entity) {
		return this.nodeDepthService.update(entity);
	}
	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
	
		return this.nodeDepthService.deleteById(id);
	}

	@RequestMapping("/exists")
	public boolean exists(@RequestBody String receiver) {

		return this.nodeDepthService.exists(receiver);
	}
	@RequestMapping("/existsByUserid/{id}")
	public boolean existsByUserid(@PathVariable Long id) {

		return this.nodeDepthService.existsByUserid(id);
	}
	@RequestMapping("/findByUserid/{id}")
	public String findByUserid(@PathVariable Long id) {

		return this.nodeDepthService.findByUserid(id);
	}

}
