package com.lt.cloud;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crm.springboot.utils.RegexUtils;
import com.lt.cloud.pojos.PermissionList;
import com.lt.cloud.service.PermissionListService;
import com.lt.cloud.utils.JsonUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionListTest {
	@Autowired
	private PermissionListService permissionListService;
	@Test
	public void exitst() {
		PermissionList receiver=new PermissionList();
		receiver.setCompany("xx");
		this.permissionListService.exists(JsonUtils.getGson().toJson(receiver));
	}
	@Test 
	public void hasPermission() {
		String result = this.permissionListService.hasPermission("1cb4323b1ebc4a41bbb16370adc182513qA7sAN4j94=", "https://www.baidu.com/user/c/b/user");
		System.out.println(result);
	}
	@Test
	public void test() {
		String Lresult = RegexUtils.getFirstGroup("/order/deleteById/34413", "deleteBy");
		System.out.println(JsonUtils.getGson().toJson(Lresult));
	}
}
