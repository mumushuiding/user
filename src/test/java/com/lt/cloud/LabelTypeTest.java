package com.lt.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lt.cloud.service.LabeltypeService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class LabelTypeTest {
	@Autowired
	private LabeltypeService labeltypeService;
	@Test
	public void existsByName() {
		System.out.println(this.labeltypeService.existsByName("部门1"));
	}

	
}
