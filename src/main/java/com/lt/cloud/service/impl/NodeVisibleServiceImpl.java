package com.lt.cloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lt.cloud.data.jpa.NodeVisibleRepository;
import com.lt.cloud.pojos.NodeVisible;
import com.lt.cloud.pojos.NodeVisibleReceiver;
import com.lt.cloud.service.NodeVisibleService;
import com.lt.cloud.service.PageService;
import com.lt.cloud.utils.JsonUtils;
@Component
public class NodeVisibleServiceImpl  implements NodeVisibleService,PageService<NodeVisible, NodeVisibleReceiver> {
	@Autowired
	private NodeVisibleRepository nodeVisibleRepository;
	@Override
	public String findAll(String json) {
		NodeVisibleReceiver receiver = JsonUtils.getGson().fromJson(json, NodeVisibleReceiver.class);
		return JsonUtils.formatPageForPagination(this.nodeVisibleRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}

	@Override
	public Pageable getPageRequest(NodeVisibleReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}

	@Override
	public Specification<NodeVisible> getSpecification(NodeVisibleReceiver receiver) {
		Specification<NodeVisible> specification=new Specification<NodeVisible>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<NodeVisible> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (!StringUtils.isEmpty(receiver.getUsername())) {
					predicates.add(criteriaBuilder.equal(root.get("username"), receiver.getUsername()));
				}
				List<Order> orders =new ArrayList<>();
				orders.add(criteriaBuilder.desc(root.get("createdate")));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(orders).getRestriction();
			}
		};
		return specification;
	}

	@Override
	public String save(String entity) {
		try {
			NodeVisible receiver = JsonUtils.getGson().fromJson(entity, NodeVisible.class);
			
			if (this.exists(receiver)) { // 已经存在的节点
				throw new RuntimeException("节点已经存在，请刷新页面");
			}
			receiver.setCreatedate(new Date());
			return JsonUtils.getGson().toJson(this.nodeVisibleRepository.save(receiver));
		} catch (Exception e) {
			throw new RuntimeException("保存【"+entity+"】失败!!");
		}
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			this.nodeVisibleRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除节点【"+id+"】失败!!");
		}
	}

	@Override
	public boolean exists(String json) {
		NodeVisible receiver = JsonUtils.getGson().fromJson(json, NodeVisible.class);
		return this.nodeVisibleRepository.existsByUseridAndNode(receiver.getUserid(),receiver.getNode());
	}

	@Override
	public boolean exists(NodeVisible receiver) {
		
		return this.nodeVisibleRepository.existsByUseridAndNode(receiver.getUserid(),receiver.getNode());
	}

	@Override
	public String findByUserid(Long id) {
		
		return JsonUtils.getGson().toJson(this.nodeVisibleRepository.findByUserid(id));
	}



}
