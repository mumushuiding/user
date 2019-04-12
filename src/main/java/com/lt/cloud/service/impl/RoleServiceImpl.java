package com.lt.cloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.StringUtils;

import com.lt.cloud.data.jpa.RoleRepository;
import com.lt.cloud.pojos.Role;
import com.lt.cloud.pojos.RoleReceiver;
import com.lt.cloud.service.NodeTreeService;
import com.lt.cloud.service.PageService;
import com.lt.cloud.service.RoleService;
import com.lt.cloud.utils.JsonUtils;

/** 
* @ClassName: RoleServiceImpl 
* @Description: 用户角色
* @author lt
* @date 2019年1月11日 下午5:03:41 
*  
*/
@Component
public class RoleServiceImpl implements RoleService,PageService<Role,RoleReceiver>{
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private NodeTreeService nodeTreeService;
	@Override
	public String findAll(String json) {
		RoleReceiver receiver = JsonUtils.getGson().fromJson(json, RoleReceiver.class);
		return JsonUtils.formatPageForPagination(this.roleRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}

	@Override
	public Boolean save(String entity) {
		try {
			Role role=JsonUtils.getGson().fromJson(entity, Role.class);
			if (this.existsRole(role.getUserid(), role.getNodevalue())) { // 判断角色是否已经存在
				throw new RuntimeException("角色已经存在");
			}
			role.setCreatedate(new Date());
			this.roleRepository.save(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			this.roleRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return true;
	}
	@Override
	public Pageable getPageRequest(RoleReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}

	@Override
	public Specification<Role> getSpecification(RoleReceiver receiver) {
		Specification<Role> result=new Specification<Role>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (receiver.getUserid()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("userid"), receiver.getUserid()));
				}
				if(receiver.getNodevalue()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("nodevalue"), receiver.getNodevalue()));
				}
				if (!StringUtils.isEmpty(receiver.getType())) {
					predicates.add(criteriaBuilder.equal(root.get("type"), receiver.getType()));
				}
				if (receiver.getTypeid()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("typeid"), receiver.getTypeid()));
				}
				if (receiver.getDepth()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("depth"), receiver.getDepth()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return result;
	}


	@Override
	public boolean existsGroup(String json) {
		List<Role> result = this.roleRepository.findAll(getSpecification(JsonUtils.getGson().fromJson(json, RoleReceiver.class)));
		if (result!=null&&result.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean exists(String json) {
		RoleReceiver receiver=JsonUtils.getGson().fromJson(json, RoleReceiver.class);
		List<Role> result = this.roleRepository.findAll(getSpecification(receiver));
		return result.size()>0?true:false;
	}

	@Override
	public String findRoleByUseridAsTree(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findRolesAsTree(String json) {
		
		return this.nodeTreeService.findAllAsTree(json);
	}
	public boolean existsRole(Long userid, Long nodevalue) {
		return this.roleRepository.existsByUseridAndNodevalue(userid,nodevalue);
	}
	@Override
	public boolean existsRole(String json) {
		RoleReceiver receiver=JsonUtils.getGson().fromJson(json, RoleReceiver.class);
		return this.existsRole(receiver.getUserid(), receiver.getNodevalue());
	}

	@Override
	public List<Role> findAllByUserid(Long id) {
		
		return this.roleRepository.findAllByUserid(id);
	}
/**
 *  查询用户的角色，并根据角色类型进行分类
 */
	@Override
	public Map<String, List<Role>> findAllWithUseridClassifyByType(Long id) {
		List<Role> roles=this.findAllByUserid(id);
		Map<String, List<Role>> group = roles.stream().map(r -> {
			Role role=new Role();
			role.setNodelabel(r.getNodelabel());
			role.setDepth(r.getDepth());
			role.setType(r.getType());
			return role;
		}).collect(Collectors.groupingBy(Role::getType));
		return group;
	}








}
