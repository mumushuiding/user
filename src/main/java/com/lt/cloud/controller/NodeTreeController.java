package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.cloud.pojos.NodeTree;
import com.lt.cloud.service.NodeTreeService;
/**
 * 组织架构和人员
 * @author lt
 *
 */
@RestController
@RequestMapping("/user/nodetree")
public class NodeTreeController {
	@Autowired
	private NodeTreeService nodeTreeService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String josn) {
		return this.nodeTreeService.findAll(josn);
	}
	@RequestMapping("/findAllAsTree")
	public String findAllAsTree(@RequestBody String josn) {
		return this.nodeTreeService.findAllAsTree(josn);
	}
	@RequestMapping("/saveNew")
	public boolean save(@RequestBody String json) {
		return this.nodeTreeService.saveNew(json);
	}
	@RequestMapping("/saveReturn")
	public NodeTree saveReturn(@RequestBody String json) {
		return this.nodeTreeService.saveReturn(json);
	}
	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
		return this.nodeTreeService.deleteById(id);
	}
	@RequestMapping("/update")
	public boolean update(@RequestBody String json) {
		return this.nodeTreeService.update(json);
	}
	@RequestMapping("/exists")
	public boolean exists(@RequestBody String json) {
		return this.nodeTreeService.exists(json);
	}
	@RequestMapping("/hasChildren/{id}")
	public boolean hasChildren(@PathVariable Long id) {
		return this.nodeTreeService.hasChildren(id);
	}
}
