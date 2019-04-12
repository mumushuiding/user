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
import com.lt.cloud.data.jpa.LabeltypeRepository;
import com.lt.cloud.pojos.Label;
import com.lt.cloud.pojos.Labeltype;
import com.lt.cloud.pojos.UserReceiver;
import com.lt.cloud.service.LabeltypeService;
import com.lt.cloud.utils.JsonUtils;
/**
*
* @author ****
* @date 2019年1月11日
*/
@Component
public class LabeltypeServiceImpl implements LabeltypeService{
	@Autowired
	private LabeltypeRepository labeltypeRepository;


	/* (non-Javadoc)
	 * @see com.lt.cloud.service.BaseService#save(java.lang.String)
	 */
	@Override
	public Boolean save(String entity) {
		try {
			Labeltype labeltype=JsonUtils.getGson().fromJson(entity, Labeltype.class);
			labeltype.setCreatedate(new Date());
			if (this.existsByName(labeltype.getName())) {
				throw new RuntimeException("已经存在标签类型【"+labeltype.getName()+"】");
			}
			this.labeltypeRepository.save(labeltype);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	@Override
	public boolean existsByName(String name) {
		
		return this.labeltypeRepository.existsByName(name);
	}

	/* (non-Javadoc)
	 * @see com.lt.cloud.service.BaseService#deleteById(java.lang.Long)
	 */
	@Override
	public boolean deleteById(Long id) {
		try {
			this.labeltypeRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除失败！！");
		}
	}

	/* (non-Javadoc)
	 * @see com.lt.cloud.service.PageService#getPageRequest(com.lt.cloud.pojos.UserReceiver)
	 */
	public Pageable getPageRequest(UserReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}

	/*
	* Title: findAll
	*Description: 
	* @param receiver
	* @return 
	* @see com.lt.cloud.service.BaseService#findAll(java.lang.String) 
	*/
	public String findAll(String json) {
		UserReceiver receiver = JsonUtils.getGson().fromJson(json, UserReceiver.class);
		return JsonUtils.formatPageForPagination(this.labeltypeRepository.findAll(getSpecification(receiver),getPageRequest(receiver)));
	}

	/*
	* Title: getSpecification
	*Description: 
	* @param receiver
	* @return 
	* @see com.lt.cloud.service.PageService#getSpecification(com.lt.cloud.pojos.UserReceiver) 
	*/
	public Specification<Labeltype> getSpecification(UserReceiver receiver) {
		Specification<Labeltype> result=new Specification<Labeltype>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Labeltype> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (!StringUtils.isEmpty(receiver.getLabeltype())) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%"+receiver.getLabeltype()+"%"));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return result;
	}

	@Override
	public boolean exists(String json) {
		UserReceiver receiver=JsonUtils.getGson().fromJson(json, UserReceiver.class);
		List<Label> result = this.labeltypeRepository.findAll(getSpecification(receiver));
		return result.size()>0?true:false;
	}
	@Override
	public Labeltype findByName(String name) {
		
		return this.labeltypeRepository.findByName(name);
	}
	

}
