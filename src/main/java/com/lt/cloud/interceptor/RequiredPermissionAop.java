package com.lt.cloud.interceptor;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lt.cloud.pojos.ResponseCode;
import com.lt.cloud.service.PermissionListService;
import com.lt.cloud.utils.JsonUtils;
/**
 * 根据用户的token，查询用户的权限
 * @author lt
 *
 */
@Aspect
@Component
public class RequiredPermissionAop {
	@Autowired
	private PermissionListService permissionService;
	@Around("@annotation(rp)")
	public Object around(ProceedingJoinPoint pjp,RequiredPermission rp) throws Throwable {
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		// 获取token
		String token = this.getParamToken(request);
		// 获取要访问的路径
		String url = this.getUrl(request);
		String res = this.permissionService.hasPermission(token, url);
		ResponseCode responseCode=JsonUtils.getGson().fromJson(res, ResponseCode.class);
		if (responseCode.isFlag()) {
			return pjp.proceed();
		} else {
			return res;
		}
	}


	private String getUrl(HttpServletRequest request) {

		return request.getServletPath();
	}
	private String getParamToken(HttpServletRequest request) {
//		System.out.println(JsonUtils.getGson().toJson(request.getHeader("Authorization")));
		String result=request.getHeader("Authorization");
		return result;
	}
}
