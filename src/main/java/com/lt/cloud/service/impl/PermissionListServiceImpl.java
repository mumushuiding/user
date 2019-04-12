package com.lt.cloud.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import com.lt.cloud.data.jpa.PermissionListRepository;
import com.lt.cloud.pojos.PermissionList;
import com.lt.cloud.pojos.PermissionListReceiver;
import com.lt.cloud.pojos.ResponseCode;
import com.lt.cloud.pojos.Userinfo;
import com.lt.cloud.service.PageService;
import com.lt.cloud.service.PermissionListService;
import com.lt.cloud.service.RedisService;
import com.lt.cloud.utils.JsonUtils;



@Component
public class PermissionListServiceImpl implements PermissionListService,PageService<PermissionList, PermissionListReceiver>{
	@Autowired
	private RedisService redisService;
	@Autowired
	private PermissionListRepository permissionListRepository;

	@Override
	public String findAll(String json) {
		PermissionListReceiver receiver=JsonUtils.getGson().fromJson(json, PermissionListReceiver.class);
		return JsonUtils.formatPageForPagination(this.permissionListRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}
	@Override
	public Boolean save(String entity) {
		try {
			PermissionList permissionList=JsonUtils.getGson().fromJson(entity, PermissionList.class);
			if (this.exists(permissionList.getCompany(),permissionList.getUrl())) {
				throw new RuntimeException("已经存在!!");
			}
			this.permissionListRepository.save(permissionList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败");
		}
	}
	/**
	 * 判断是否已经存在,只看company 和 url
	 */
	@Override
	public boolean exists(String json) {
		PermissionList receiver=JsonUtils.getGson().fromJson(json, PermissionList.class);
		
		return this.exists(receiver.getCompany(),receiver.getUrl());
	}
	private boolean exists(String company, String url) {
		Assert.hasLength(company, "company 字段不能为空,它代表公司");
		Assert.hasLength(url, "url 字段不能为空,它代表api地址");
		return this.permissionListRepository.existsByCompanyAndUrl(company, url);
	}
	@Override
	public boolean deleteById(Long id) {
		try {
			this.permissionListRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除失败");
		}
	}
	@Override
	public Boolean update(String entity) {
		PermissionList permissionList=JsonUtils.getGson().fromJson(entity, PermissionList.class);
		Assert.notNull(permissionList.getId(), "id不能为空");
		try {
			this.update(permissionList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新失败");
		}
	}
	private void update(PermissionList permissionList) {
		this.permissionListRepository.save(permissionList);
		
	}
	@Override
	public Pageable getPageRequest(PermissionListReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}
	@Override
	public Specification<PermissionList> getSpecification(PermissionListReceiver receiver) {
		Specification<PermissionList> specification=new Specification<PermissionList>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<PermissionList> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return specification;
	}

	@Override
	public String hasPermission(String token, String url) {
		ResponseCode data=new ResponseCode();
		boolean flag=false;
		data.setFlag(flag);
		try {
			// 根据 token 获取用户信息
			if (StringUtils.isEmpty(token)) {
				data.setMessage("token不能为空!!");
				return JsonUtils.getGson().toJson(data);
			}
			String info=this.redisService.getStr(token);
			if (info==null) {
				data.setMessage("用户信息为空,token已经可能过期,请重新登陆!!");
				return JsonUtils.getGson().toJson(data);
			}
//			System.out.println(info);
			Userinfo userinfo=JsonUtils.getGson().fromJson(info, Userinfo.class);
			// url去掉ip和port
			url=this.removeIpAndPort(url);
			// 用户可访问的受限制路径
			List<String> permission=userinfo.getPermission();
			// 判断是否有当前路径的访问权限
			flag=this.hasPermission(url, permission);
			// 判断用户是否包含该角色
			data.setFlag(flag);
			if (!flag) {
				data.setMessage("您没有权限执行该操作或url没有指定访问权限");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return JsonUtils.getGson().toJson(data);
	}
	private boolean hasPermission(String url, List<String> permission) {
		for (String str : permission) {
			if (str.length()<=url.length() && url.substring(0, str.length()).equals(str)) {
				return true;
			}
		}
		return false;
	}
	private String removeIpAndPort(String url) {
		if (url.indexOf("://")>-1) {
			url=url.replace("://", ""); // 一般url开头为 http:// 或者 https://
			return url.substring(url.indexOf("/"));
		}
		return url;
		
	}
	@Override
	public List<PermissionList> findByCompanyAndRoleIn(String company, List<String> role) {
		
		return this.permissionListRepository.findByCompanyAndRoleIn(company, role);
	}



}
