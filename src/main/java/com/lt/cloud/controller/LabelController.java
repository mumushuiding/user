package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lt.cloud.service.LabelService;
@RestController
@RequestMapping("/user/label")
public class LabelController{
	@Autowired
	private LabelService labelService;

	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		
		return this.labelService.findAll(receiver);
	}

	@RequestMapping("/save")
	public Boolean save(@RequestBody String entity) {
		
		return this.labelService.save(entity);
	}

	@RequestMapping("/deleteById/{id}")
	public boolean deleteById(@PathVariable Long id) {
		
		return this.labelService.deleteById(id);
	}

	@RequestMapping("/findByTypename/{typename}")
	public String findByTypename(@PathVariable String typename) {
		
		return this.labelService.findByTypename(typename);
	}
	@RequestMapping("/exists")
	public boolean exists(@RequestBody String json) {
		return this.labelService.exists(json);
	}


}
