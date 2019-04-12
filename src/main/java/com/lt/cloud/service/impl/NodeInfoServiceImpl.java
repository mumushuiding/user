package com.lt.cloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.util.StringUtils;

import com.lt.cloud.data.jpa.NodeInfoRepository;
import com.lt.cloud.pojos.NodeInfo;
import com.lt.cloud.pojos.NodeInfoReceiver;
import com.lt.cloud.service.NodeInfoService;
import com.lt.cloud.service.NodeTreeService;
import com.lt.cloud.service.PageService;
import com.lt.cloud.utils.JsonUtils;

@Component
public class NodeInfoServiceImpl implements NodeInfoService,PageService<NodeInfo, NodeInfoReceiver>{
	@Autowired
	private NodeInfoRepository nodeDepthRepository;
	@Autowired
	private NodeTreeService nodeTreeService;
	@Override
	public Pageable getPageRequest(NodeInfoReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}

	@Override
	public Specification<NodeInfo> getSpecification(NodeInfoReceiver receiver) {
		Specification<NodeInfo> result=new Specification<NodeInfo>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<NodeInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (receiver.getUserid()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("userid"), receiver.getUserid()));
				}
				if (!StringUtils.isEmpty(receiver.getCompany())) {
					predicates.add(criteriaBuilder.equal(root.get("company"), receiver.getCompany()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return result;
	}

	@Override
	public String findAll(String json) {
		NodeInfoReceiver receiver = JsonUtils.getGson().fromJson(json, NodeInfoReceiver.class);
		return JsonUtils.formatPageForPagination(this.nodeDepthRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}

	@Override
	public Boolean save(String entity) {
//		System.out.println(entity);
		NodeInfo receiver = JsonUtils.getGson().fromJson(entity, NodeInfo.class);
		try {
			if (this.existsByUserid(receiver.getUserid())) { // 查看用户的权限深度是否已经存在
				throw new RuntimeException("已经存在！！");
			}
			receiver.setCreatedate(new Date());
			// 保存用户节点信息
			receiver=this.save(receiver);
			// 保存用户组织架构
			this.nodeTreeService.saveNewWithNodeinfo(receiver);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败！！");
		}
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			this.nodeDepthRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除深度【"+id+"】失败");
		}
	}

	@Override
	public boolean exists(String json) {
		NodeInfo receiver = JsonUtils.getGson().fromJson(json, NodeInfo.class);
		return this.existsByUserid(receiver.getUserid());
	}

	@Override
	public boolean existsByUserid(Long id) {
		
		return this.nodeDepthRepository.existsByUserid(id);
	}
	@Override
	public NodeInfo findNodeinfoByUserid(Long id) {
		
		return this.nodeDepthRepository.findByUserid(id);
	}
	@Override
	public String findByUserid(Long id) {
		
		return JsonUtils.getGson().toJson(this.nodeDepthRepository.findByUserid(id));
	}

	@Override
	public NodeInfo save(NodeInfo nodeInfo) {
		try {
			nodeInfo.setCreatedate(new Date());
			//用户的权限深度等于他在组织架构中的深度
			nodeInfo.setDepth(nodeInfo.getDepartmentid().split(",").length+1);
			return this.nodeDepthRepository.save(nodeInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败");
		}
	}

	@Override
	public boolean update(String entity) {
		NodeInfo receiver = JsonUtils.getGson().fromJson(entity, NodeInfo.class);
		try {
			receiver.setCreatedate(new Date());
			//用户的权限深度等于他在组织架构中的深度
			receiver.setDepth(receiver.getDepartmentid().split(",").length+1);
			this.nodeDepthRepository.save(receiver);
			
			// 保存用户组织架构
			this.nodeTreeService.updateWithNodeinfo(receiver);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新失败");
		}
	}

}
