package com.lt.cloud.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.lt.cloud.data.jpa.UserRepository;
import com.lt.cloud.pojos.Login;
import com.lt.cloud.pojos.NodeInfo;
import com.lt.cloud.pojos.NodeTree;
import com.lt.cloud.pojos.PermissionList;
import com.lt.cloud.pojos.RegistryReceiver;
import com.lt.cloud.pojos.Role;
import com.lt.cloud.pojos.User;
import com.lt.cloud.pojos.UserReceiver;
import com.lt.cloud.pojos.Userinfo;
import com.lt.cloud.service.BaseService;
import com.lt.cloud.service.LoginService;
import com.lt.cloud.service.NodeInfoService;
import com.lt.cloud.service.NodeTreeService;
import com.lt.cloud.service.PermissionListService;
import com.lt.cloud.service.RedisService;
import com.lt.cloud.service.RoleService;
import com.lt.cloud.service.UserService;
import com.lt.cloud.utils.JsonUtils;

/** 
* @ClassName: UserServiceImpl 
* @Description: 用户模块 
* @author lt
* @date 2019年1月11日 下午5:08:02 
*  
*/
@Component
public class UserServiceImpl implements UserService,BaseService{
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NodeInfoService nodeDepthService;
	@Autowired
	private NodeTreeService nodeTreeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionListService permissionListService;
	@Autowired
	private RedisService redisService;
	public Pageable getPageRequest(UserReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
						receiver.getPageSize()==null?10:receiver.getPageSize());
	}
	public Specification<User> getSpecification(UserReceiver receiver) {
		Specification<User> result=new Specification<User>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (!StringUtils.isEmpty(receiver.getUsername())) {
					predicates.add(criteriaBuilder.like(root.get("username"), "%"+receiver.getUsername()+"%"));
				}
				if (!StringUtils.isEmpty(receiver.getCompany()) && !receiver.getCompany().equals("0")) {
					predicates.add(criteriaBuilder.equal(root.get("company"), receiver.getCompany()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return result;
	}

	@Override
	public String findAll(String json) {
		UserReceiver receiver = JsonUtils.getGson().fromJson(json, UserReceiver.class);
		return JsonUtils.formatPageForPagination(this.userRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}

	@Override
	public User findById(Long id) {
		Optional<User> result = userRepository.findById(id);
		return result.isPresent()?result.get():null;
	}

	@Override
	@Transactional
	public Boolean save(String entity) {
		RegistryReceiver receiver=JsonUtils.getGson().fromJson(entity, RegistryReceiver.class);
		// 查看节点类型
		if (receiver.getTypeid()==null) {
			throw new RuntimeException("类型【"+receiver.getTypename()+"】的id为空，无法注册");
		}
		// 根据电话号码查询用户是否已经注册过了！！
		if (StringUtils.isEmpty(receiver.getPhone())) {
			throw new RuntimeException("手机号码为空");
		}
		if (this.existsByPhone(receiver.getPhone())) {
			throw new RuntimeException("手机号【"+receiver.getPhone()+"】已经注册过了");
		}
		try {
			// 用户信息表 user
			User user=this.setUserByRegistry(receiver);
			user=this.userRepository.save(user); // 保存用户基本信息
			if (user.getId()==null) {
				throw new RuntimeException("用户ID【"+user.getId()+"】为空");
			}
			// 用户登陆表login
			Login login=this.setLoginByUser(user);
			login.setPassword(receiver.getPassword());
			this.loginService.save(login); //保存登陆表
			
			// 节点信息表 nodeinfo
			NodeInfo nodeInfo=this.setNodeinfoByRegistry(receiver);
			nodeInfo.setUserid(user.getId());
			this.nodeDepthService.save(nodeInfo);
			
			// 节点 nodetree
			NodeTree nodeTree=this.setNodetreeByRegistry(receiver);
			nodeTree.setTitle(user.getId()+"-"+user.getUsername());
			this.nodeTreeService.saveNew(nodeTree);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("用户【"+entity+"】注册失败");
		}
	}

	private NodeTree setNodetreeByRegistry(RegistryReceiver receiver) {
		NodeTree nodeTree=new NodeTree();
		nodeTree.setCompany(receiver.getCompany());
		String[] department = receiver.getDepartmentid().split(",");
		nodeTree.setDepth(department.length+1);
		nodeTree.setParentid(Long.valueOf(department[department.length-1]));
		nodeTree.setType(receiver.getTypename());
		nodeTree.setTypeid(receiver.getTypeid());
		return nodeTree;
	}
	private NodeInfo setNodeinfoByRegistry(RegistryReceiver receiver) {
		NodeInfo nodeInfo=new NodeInfo();
		nodeInfo.setUsername(receiver.getUsername());
		nodeInfo.setCompany(receiver.getCompany());
		nodeInfo.setDepartment(receiver.getDepartment());
		nodeInfo.setDepartmentid(receiver.getDepartmentid());
		return nodeInfo;
	}
	private Login setLoginByUser(User user) {
		Login login=new Login();
		login.setId(user.getId());
		login.setName(user.getPhone());
		return login;
	}
	private User setUserByRegistry(RegistryReceiver receiver) {
		User user=new User();
		user.setEmail(receiver.getEmail());
		user.setCreatedate(new Date());
		user.setPhone(receiver.getPhone());
		user.setUsername(receiver.getUsername());
		user.setCompany(receiver.getCompany());
		return user;
	}
	@Override
	public boolean deleteById(Long id) {
		try {
			this.userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除用户失败！！");
		}
	}

	@Override
	public boolean exists(String json) {
		UserReceiver receiver=JsonUtils.getGson().fromJson(json, UserReceiver.class);
		List<User> result = this.userRepository.findAll(getSpecification(receiver));
		return result.size()>0?true:false;
	}
	@Override
	public String getUserInfo(Long id) {
		Userinfo userinfo=new Userinfo();
		// 查询用户基本信息
		User user=this.findById(id);
		//查询用户节点信息
		NodeInfo nodeinfo=this.nodeDepthService.findNodeinfoByUserid(id);
		//查询用户角色信息
		List<String> roles = this.roleService.findAllByUserid(id).stream().map(r -> r.getNodelabel()).collect(Collectors.toList());
		// 查询用户有权访问哪些受限访问的路径 
		List<String> permissionLists=this.permissionListService.findByCompanyAndRoleIn(nodeinfo.getCompany(), roles).stream().map(r-> r.getUrl()).collect(Collectors.toList());
		userinfo.setUser(user);
		userinfo.setNodeInfo(nodeinfo);
		userinfo.setRoles(roles);
		userinfo.setPermission(permissionLists);
		String result=JsonUtils.getGson().toJson(userinfo);
		return result;
	}
	@Override
	public String getUserInfoWithToken(String token) {
		
		return this.redisService.getStr(token);
	}
	@Override
	public boolean existsByPhone(String phone) {
		
		return this.userRepository.existsByPhone(phone);
	}
	@Override
	public void cacheUserinfo(String token,String userinfo) {
		this.redisService.setStr(token, userinfo,1,TimeUnit.DAYS);
		
	}
	

}
