package com.lt.cloud.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
*
* @author ****
* @date 2019年1月11日
*/
public interface PageService<T,R> {
	/**
	 * 生成分页的pageSize和pageIndex
	 * @param receiver
	 * @return
	 */
	public  Pageable getPageRequest(R receiver);
	/**
	 * 生成分页查询参数
	 * @param receiver
	 * @return
	 */
	public Specification<T> getSpecification(R receiver);
}
