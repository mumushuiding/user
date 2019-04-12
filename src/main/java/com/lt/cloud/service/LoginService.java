package com.lt.cloud.service;

import com.lt.cloud.pojos.Login;

public interface LoginService {
	Login findByNameAndPassword(String name,String password);
	Login save(Login login);
	String login(String login);
}
