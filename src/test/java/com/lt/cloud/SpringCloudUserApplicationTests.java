package com.lt.cloud;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lt.cloud.pojos.Login;
import com.lt.cloud.pojos.User;
import com.lt.cloud.pojos.UserReceiver;

import com.lt.cloud.service.LabelService;
import com.lt.cloud.service.LoginService;
import com.lt.cloud.service.RoleService;
import com.lt.cloud.service.UserService;
import com.lt.cloud.utils.JsonUtils;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCloudUserApplicationTests {
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private RoleService roleService;


	@Test
	public void login() {
		System.out.println("***************************************登陆测试****************************");
		Login result = this.loginService.findByNameAndPassword("13328200866", "1234");
		System.out.println(JsonUtils.getGson().toJson(result));
		System.out.println("**********************************************************************");
	}
	@Test
	public void find4AutoComplete() {
		System.out.println("***************************************分页查询全部测试****************************");
		UserReceiver receiver=new UserReceiver();
		receiver.setUsername("林");
		String result = this.userService.findAll(JsonUtils.getGson().toJson(receiver));
		System.out.println(result);
		System.out.println("*******************************************************************");
	}
	@Test
	public void findById() {
		System.out.println("***************************************根据ID查询****************************");
		User result = userService.findById(11003L);
		System.out.println(JsonUtils.getGson().toJson(result));
		System.out.println("*******************************************************************");
	}
	@Test
	public void findAllLabel() {
		System.out.println("***************************************查询所有标签****************************");
		UserReceiver userReceiver=new UserReceiver();
		userReceiver.setLabelname("一线");
		userReceiver.setLabeltype("系统");
		System.out.println(this.labelService.findAll(JsonUtils.getGson().toJson(userReceiver)));
		System.out.println("*******************************************************************");
	}
	@Test
	public void findAllRole() {
		System.out.println("***************************************查询所有角色****************************");
		UserReceiver userReceiver=new UserReceiver();
//		userReceiver.setLabelname("一线");
//		userReceiver.setLabeltype("系统");
		System.out.println(this.roleService.findAll(JsonUtils.getGson().toJson(userReceiver)));
		System.out.println("*******************************************************************");
	}


//	@Test
//	public void updatePassword() {
//		System.out.println("***************************************更新密码****************************");
//		Login result = this.loginService.findByNameAndPassword("13328200866", "1234");
//		result.setPassword("123");
//		loginService.save(result);
//		System.out.println(JsonUtils.getGson().toJson(result));
//		System.out.println("*******************************************************************");
//	}
	
}

