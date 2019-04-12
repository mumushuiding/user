package com.lt.cloud.service;

import java.util.List;

import com.lt.cloud.pojos.NodeInfo;
import com.lt.cloud.pojos.NodeTree;
import com.lt.cloud.pojos.NodeTreeReceiver;

public interface NodeTreeService {
	List<NodeTree> findAllAsList();
	List<NodeTree> findAllAsList(String josn);
	List<NodeTree> findAllAsList(NodeTreeReceiver receiver);
	String findAllAsTree(String josn);

	boolean saveNew(String json);

	boolean deleteById(Long id);

	boolean update(String json);

	boolean exists(String json);
	/**
	 * 多表查询 role和 nodetree
	 */
	List<NodeTree> findNodeByUserid(Long userid,Integer depth);
	String findNodeByUseridAsTree(Long userid);
	/**
	 * 保存并返回最新值
	 * @param json
	 * @return
	 */
	NodeTree saveReturn(String json);

	String findAll(String josn);
	String findAll(NodeTreeReceiver receiver);
	NodeTree saveNew(NodeTree nodetree);
	void saveNewWithNodeinfo(NodeInfo receiver);
	void updateWithNodeinfo(NodeInfo receiver);
	boolean hasChildren(Long id);
}
