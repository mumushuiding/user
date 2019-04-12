package com.lt.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lt.cloud.data.jpa.NodeTreeRepository;
import com.lt.cloud.pojos.NodeTree;
import com.lt.cloud.service.NodeTreeService;
import com.lt.cloud.utils.JsonUtils;


@SpringBootTest
@RunWith(SpringRunner.class)
public class NodeTreeTest {
	@Autowired
	private NodeTreeService nodeTreeService;
	@Autowired
	private NodeTreeRepository nodeTreeRepository;
	@Test
	public void findAllAsTree() {
//		this.nodeTreeService.findAllAsTree();
	}
	@Test
	public void exits() {

	}
}
