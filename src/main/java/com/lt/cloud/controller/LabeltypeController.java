package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lt.cloud.service.LabeltypeService;
import com.lt.cloud.utils.JsonUtils;
@RestController
@RequestMapping("/user/labeltype")
public class LabeltypeController{
	@Autowired
	private LabeltypeService labeltypeService;
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.labeltypeService.findAll(receiver);
	}

	@RequestMapping("/save")
	public Boolean save(@RequestBody String entity) {
		
		return this.labeltypeService.save(entity);
	}

	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
		
		return this.labeltypeService.deleteById(id);
	}
	@RequestMapping("/exists")
	public boolean exists(@RequestBody String json) {
		return this.labeltypeService.exists(json);
	}
	@RequestMapping("/findByName/{name}")
	public String findByName(@PathVariable String name) {
		return JsonUtils.getGson().toJson(this.labeltypeService.findByName(name));
	}
}
