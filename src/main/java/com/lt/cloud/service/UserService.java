package com.lt.cloud.service;
import com.lt.cloud.pojos.User;

/** 
* @ClassName: UserService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lt
* @date 2019年1月11日 下午5:11:43 
*  
*/
public interface UserService {
	public String findAll(String receiver);
	public User findById(Long id);
	public Boolean save(String entity);
	public String getUserInfo(Long id);
	public boolean existsByPhone(String phone);
	public void cacheUserinfo(String token,String userinfo);
	public String getUserInfoWithToken(String token);

}
