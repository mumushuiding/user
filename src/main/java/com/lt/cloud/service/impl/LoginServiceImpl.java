package com.lt.cloud.service.impl;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lt.cloud.data.jpa.LoginRepository;
import com.lt.cloud.pojos.Login;
import com.lt.cloud.service.LoginService;
import com.lt.cloud.service.UserService;
import com.lt.cloud.utils.CodeUtils;
import com.lt.cloud.utils.JsonUtils;
@Component
public class LoginServiceImpl implements LoginService{
	@Autowired
	private  LoginRepository loginRepository;
	@Autowired
	private UserService userService;
	@Override
	public Login findByNameAndPassword(String name, String password) {
		return this.loginRepository.findByNameAndPassword(name, CodeUtils.encryptBasedDes(password));
	}
	@Override
	public Login save(Login login) {
		login.setPassword(CodeUtils.encryptBasedDes(login.getPassword()));
		return this.loginRepository.save(login);
	}
	@Override
	public String login(String login) {
		Login login2=JsonUtils.getGson().fromJson(login, Login.class);
		Login result=this.findByNameAndPassword(login2.getName(), login2.getPassword());
		if(result==null) {
			return null;
		}
		String token=UUID.randomUUID().toString().replace("-", "")+CodeUtils.encryptBasedDes(result.getId().toString());
		// 查询用户信息 并保存到 redis
		this.userService.cacheUserinfo(token, this.userService.getUserInfo(result.getId()));
		return token;
	}

}
