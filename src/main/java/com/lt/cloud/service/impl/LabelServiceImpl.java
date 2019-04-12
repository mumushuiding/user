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

import com.lt.cloud.data.jpa.LabelRepository;
import com.lt.cloud.pojos.Label;
import com.lt.cloud.pojos.UserReceiver;
import com.lt.cloud.service.LabelService;
import com.lt.cloud.service.PageService;
import com.lt.cloud.utils.JsonUtils;
/**
*
* @author ****
* @date 2019年1月11日
*/
@Component
public class LabelServiceImpl implements LabelService{
	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Boolean save(String entity) {
		try {
			Label label=JsonUtils.getGson().fromJson(entity, Label.class);
			if (label.getId()==null) {
				label.setCreatedate(new Date());
			}
			this.labelRepository.save(label);
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			this.labelRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}
	public Pageable getPageRequest(UserReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}
	public Specification<Label> getSpecification(UserReceiver receiver) {
		Specification<Label> result=new Specification<Label>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (!StringUtils.isEmpty(receiver.getLabelname())) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%"+receiver.getLabelname()+"%"));
				}
				if (!StringUtils.isEmpty(receiver.getLabeltype())) {
					predicates.add(criteriaBuilder.like(root.get("typename"), "%"+receiver.getLabeltype()+"%"));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return result;
	}

	@Override
	public String findAll(String json) {
		UserReceiver receiver=JsonUtils.getGson().fromJson(json, UserReceiver.class);
		
		return JsonUtils.formatPageForPagination(this.labelRepository.findAll(getSpecification(receiver), getPageRequest(receiver)));
	}

	@Override
	public String findByTypename(String typename) {
		List<Label> result = this.labelRepository.findByTypename(typename);
		return JsonUtils.getGson().toJson(result);
	}

	@Override
	public boolean exists(String json) {
		UserReceiver receiver=JsonUtils.getGson().fromJson(json, UserReceiver.class);
		List<Label> result = this.labelRepository.findAll(getSpecification(receiver));
		return result.size()>0?true:false;
	}


}
