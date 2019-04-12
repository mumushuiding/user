package com.lt.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lt.cloud.AvailableSetting;
import com.lt.cloud.interceptor.RequiredPermission;
import com.lt.cloud.pojos.Login;
import com.lt.cloud.pojos.User;
import com.lt.cloud.service.LoginService;
import com.lt.cloud.service.UserService;
import com.lt.cloud.utils.JsonUtils;

/** 
* @ClassName: UserController 
* @Description: 用户
* @author lt
* @date 2019年1月11日 下午5:18:40 
*  
*/
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	public Login login(@RequestBody String login) {
		Login login2=JsonUtils.getGson().fromJson(login, Login.class);
		Login result=loginService.findByNameAndPassword(login2.getName(), login2.getPassword());
		return result;
	}
	@RequestMapping("/loginAndReturn")
	public String loginAndReturn(@RequestBody String login) {
		
		return this.loginService.login(login);
	}
	@RequestMapping("/updatePassword")
	public Long updatePassword(@RequestBody String login) {
		try {
			this.loginService.save(JsonUtils.getGson().fromJson(login, Login.class));
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
		
	}
	@RequestMapping("/findAll")
	public String findAll(@RequestBody String receiver) {
		return this.userService.findAll(receiver);
	}
	@RequestMapping("/find4AutoComplete")
	public String find4AutoComplete(@RequestBody String receiver) {
		
		return this.userService.findAll(receiver);
	}
	@RequestMapping("/findById/{id}")
	public User findById(@PathVariable Long id) {
		return this.userService.findById(id);
	}
	@RequestMapping("/getUserInfo/{id}")
	public String getUserInfo(@PathVariable Long id) {
		return this.userService.getUserInfo(id);
	}
	@RequestMapping("/getUserInfoWithToken")
	public String getUserInfo(@RequestParam String token) {
		return this.userService.getUserInfoWithToken(token);
	}
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	@RequestMapping("/save")
	public boolean save(@RequestBody String json) {
		return this.userService.save(json);
	}
	@RequestMapping("/existsByPhone/{phone}")
	public boolean existsByPhone(@PathVariable String phone) {
		return this.userService.existsByPhone(phone);
	}
}
