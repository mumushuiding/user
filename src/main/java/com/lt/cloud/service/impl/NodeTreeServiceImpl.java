package com.lt.cloud.service.impl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.lt.cloud.data.jpa.NodeTreeRepository;
import com.lt.cloud.pojos.NodeInfo;
import com.lt.cloud.pojos.NodeTree;
import com.lt.cloud.pojos.NodeTreeReceiver;
import com.lt.cloud.pojos.PowerCompo;
import com.lt.cloud.pojos.PowerNode;
import com.lt.cloud.service.NodeTreeService;
import com.lt.cloud.service.PageService;
import com.lt.cloud.utils.JsonUtils;



@Component
public class NodeTreeServiceImpl implements NodeTreeService,PageService<NodeTree, NodeTreeReceiver>{
	private static final String TYPE_MEMBERS="人员"; 
	private static final String TYPE_COMPANY="公司"; 
	@Autowired
	private NodeTreeRepository nodeTreeRepository;
	@Override
	public List<NodeTree> findAllAsList(){
		return this.nodeTreeRepository.findAll();
	}
	@Override
	public String findAllAsTree(String json) {
		NodeTreeReceiver receiver = JsonUtils.getGson().fromJson(json, NodeTreeReceiver.class);
		List<NodeTree> list = this.findAllAsList(receiver);
		
		String resutl=this.transformToTree(list);
		return resutl;
	}
	/**
	 * @param list
	 * @return
	 */
	private String transformToTree(List<NodeTree> list) {
		if (list==null||list.size()==0) {
			return null;
		}
		List<PowerNode> result=new ArrayList<>();
		// 获取节点深度最小值
		Integer depth = list.stream().min(Comparator.comparing(NodeTree::getDepth)).get().getDepth();
//		System.out.println("***************************depth="+depth);
		// 按父节点分组
		Map<Long, List<NodeTree>> groupMap=list.stream().collect(Collectors.groupingBy(NodeTree::getParentid));
		
		// 将父节点下的所有节点存入父节点
		List<PowerCompo> compos=new ArrayList<>();
		groupMap.entrySet().stream().forEach(group ->{
			PowerCompo parent=new PowerCompo(group.getKey());
			group.getValue().stream().forEach(node -> {
				PowerCompo child=new PowerCompo(node.getId(), node.getTitle(),node.getDepth(),node.getParentid(),node.getCompany(),node.getCharger());
				if (parent.getDepth()==null) {
					parent.setDepth(child.getDepth());
				}
				parent.add(child);
			});
			compos.add(parent);
		});
		// 按深度倒序排列
		compos.stream().sorted(Comparator.comparing(PowerCompo::getDepth).reversed()).filter(node -> {
//					System.out.println("-------------------目标---------------------");
			//上一层查询
			Stream<PowerCompo> top = compos.stream().filter(node1 -> {// 找到上一层的节点
				return node.getDepth()-1 >=depth && node.getDepth()-1 == node1.getDepth();
			});
			top.forEach(topNode -> {
				topNode.getChildren().stream().forEach(child -> {// 
					if (child.getId() == node.getId()) { //在上一层的节点里找到对应的节点,并将节点存入上一层的节点中
						child.getChildren().addAll(node.getChildren());
					}
				});
			});
//					System.out.println("--------------------------------");
			return node.getDepth()==depth; //最终返回深度最浅的元素
		}).forEach(row -> {
			result.addAll(0,row.getChildren());
		});

		return JsonUtils.getGson().toJson(result);
	}
	@Override
	public boolean saveNew(String json) {
		NodeTree entity = JsonUtils.getGson().fromJson(json, NodeTree.class);
		this.saveNew(entity);
		return true;
	}
	@Override
	public NodeTree saveNew(NodeTree nodetree) {
		try {
			if (this.exists(nodetree)) {
				throw new RuntimeException("同名节点已经存在添加失败");
			}
			switch (nodetree.getType()) {
			case TYPE_COMPANY:
				nodetree.setCompany(nodetree.getTitle());
				break;
			case TYPE_MEMBERS:
				if (this.findByTitle(nodetree.getTitle()).size()>0) { // 如果节点是 人员 
					throw new RuntimeException("在组织架构中已经存在用户【"+nodetree.getTitle()+"】"); //保证用户在组织架构中的唯一性
				}
			default:
				break;
			}
			return this.nodeTreeRepository.save(nodetree);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败！！");
		}
	}
	private boolean exists(NodeTree nodetree) {
		
		return this.nodeTreeRepository.existsByParentidAndTitleAndCompany(nodetree.getParentid(), nodetree.getTitle(), nodetree.getCompany());
	}
	@Override
	public NodeTree saveReturn(String json) {
		System.out.println(json);
		NodeTree entity = JsonUtils.getGson().fromJson(json, NodeTree.class);
		return this.nodeTreeRepository.save(entity);
	}
	@Override
	public boolean deleteById(Long id) {
		try {
			if (this.nodeTreeRepository.existsByParentid(id)) {// 查询是否存在子节点
				throw new RuntimeException("存在子节点无法删除，请先删除子节点！！");
			}
			this.nodeTreeRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除失败！！");
		}
	}
	@Override
	public boolean update(String json) {
		NodeTree entity = JsonUtils.getGson().fromJson(json, NodeTree.class);
		try {
			this.nodeTreeRepository.save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新失败！！");
		}
	}
	@Override
	public boolean exists(String json) {
		NodeTree entity = JsonUtils.getGson().fromJson(json, NodeTree.class);
		return this.nodeTreeRepository.existsByParentidAndTitleAndCompany(entity.getParentid(),entity.getTitle(),entity.getCompany());
	}
	@Override
	public List<NodeTree> findAllAsList(NodeTreeReceiver receiver) {
		
		return this.nodeTreeRepository.findAll(this.getSpecification(receiver));
	}
	@Override
	public List<NodeTree> findAllAsList(String josn) {
		NodeTreeReceiver receiver = JsonUtils.getGson().fromJson(josn, NodeTreeReceiver.class);
		return this.nodeTreeRepository.findAll(this.getSpecification(receiver));
	}
	public Specification<NodeTree> getSpecification(NodeTreeReceiver receiver) {
		Specification<NodeTree> specification=new Specification<NodeTree>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<NodeTree> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates=new ArrayList<>();
				if (!StringUtils.isEmpty(receiver.getTitle())) {
					predicates.add(criteriaBuilder.equal(root.get("title"), receiver.getTitle()));
				}
				if (!StringUtils.isEmpty(receiver.getType())) {
					String[] types = receiver.getType().split(",");
					if (types.length == 1) {
						predicates.add(criteriaBuilder.equal(root.get("type"), receiver.getType()));
					} else {
						In<String> in=criteriaBuilder.in(root.get("type"));
						for (String string : types) {
							in.value(string);
						}
						predicates.add(in);
					}
					
				}
				if (receiver.getParentid()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("parentid"), receiver.getParentid()));
				}
				if (receiver.getTypeid()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("typeid"), receiver.getTypeid()));
				}
				if (receiver.getDepth()!=null) {
					predicates.add(criteriaBuilder.equal(root.get("depth"), receiver.getDepth()));
				}
				if (receiver.getDepthGe()!=null) {
					predicates.add(criteriaBuilder.ge(root.get("depth"), receiver.getDepthGe()));
				}
				if (!StringUtils.isEmpty(receiver.getCompany()) && !receiver.getCompany().equals("0")) { // 当 company 不为空时 当title,这0时全部可见
					predicates.add(criteriaBuilder.equal(root.get("company"), receiver.getCompany()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return specification;
	}
	@Override
	public List<NodeTree> findNodeByUserid(Long id,Integer depth) {
		
		return this.nodeTreeRepository.findNodeByUseridAndDepth(id,depth);
	}
	@Override
	public String findNodeByUseridAsTree(Long id) {
		List<NodeTree> list = this.nodeTreeRepository.findNodeByUseridAndDepth(id,0);
		return this.transformToTree(list);
	}
	@Override
	public Pageable getPageRequest(NodeTreeReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}
	@Override
	public String findAll(String josn) {
		NodeTreeReceiver receiver = JsonUtils.getGson().fromJson(josn, NodeTreeReceiver.class);
		return this.findAll(receiver);
	}
	@Override
	public String findAll(NodeTreeReceiver receiver) {
		Page<NodeTree> result = this.nodeTreeRepository.findAll(getSpecification(receiver),getPageRequest(receiver));
		return JsonUtils.formatPageForPagination(result);
	}
	@Override
	public void saveNewWithNodeinfo(NodeInfo receiver) {
		NodeTree nodeTree=new NodeTree();
		nodeTree.setTitle(receiver.getUserid()+"-"+receiver.getUsername());
		String[] ids=receiver.getDepartmentid().split(",");
		nodeTree.setParentid(Long.valueOf(ids[ids.length-1]));
		nodeTree.setType(TYPE_MEMBERS);
		nodeTree.setDepth(ids.length+1);
		nodeTree.setCompany(receiver.getCompany());
		try {
			this.saveNew(nodeTree);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加失败");
		}
		
	}
	@Override
	public void updateWithNodeinfo(NodeInfo receiver) {
	    NodeTree old = this.findNodetreeWithUser(receiver.getUserid(),receiver.getUsername());
		if (old==null) {
			this.saveNewWithNodeinfo(receiver);
		} else {
			String[] ids=receiver.getDepartmentid().split(",");
			old.setParentid(Long.valueOf(ids[ids.length-1]));
			old.setDepth(ids.length+1);
			old.setCompany(receiver.getCompany());
			this.update(old);
		}
	}
	private void update(NodeTree old) {
		try {
			this.nodeTreeRepository.save(old);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新失败");
		}
		
	}
	private NodeTree findNodetreeWithUser(Long userid, String username) {
		List<NodeTree> list = this.findByTitle(userid+"-"+username);
		
		return list.size()>0 ? list.get(0) : null;
	}
	private List<NodeTree> findByTitle(String title) {
		return this.nodeTreeRepository.findByTitle(title);
	}
	@Override
	public boolean hasChildren(Long id) {
		
		return this.nodeTreeRepository.existsByParentid(id);
	}

}
