package com.lt.cloud.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.lt.cloud.data.jpa.RoleTreeRepository;
import com.lt.cloud.pojos.PowerCompo;
import com.lt.cloud.pojos.PowerNode;
import com.lt.cloud.pojos.RoleTree;
import com.lt.cloud.pojos.RoleTreeReceiver;
import com.lt.cloud.service.PageService;
import com.lt.cloud.service.RoleTreeService;
import com.lt.cloud.utils.JsonUtils;

@Component
public class RoleTreeServiceImpl implements RoleTreeService,PageService<RoleTree, RoleTreeReceiver>{
	private static final String ALL_NODE="所有节点";
	private static final String TYPE_POWER="权限";
	private static final String TYPE_STATUS="状态";
	private static final String TYPE_POST="职级";
	@Autowired
	private RoleTreeRepository roleTreeRepository;
	@Override
	public String findAll(String json) {
		RoleTreeReceiver receiver = JsonUtils.getGson().fromJson(json, RoleTreeReceiver.class);
		return JsonUtils.getGson().toJson(this.findAll(receiver));
	}
	/**
	 * 查询所有角色
	 * @param receiver
	 * @return
	 */
	private List<RoleTree> findAll(RoleTreeReceiver receiver) {
		if (StringUtils.isEmpty(receiver.getCompany())) {
			throw new RuntimeException("公司为空");
		}
		List<RoleTree> result=this.roleTreeRepository.findAll(getSpecification(receiver));
		if (result.size() == 0) { // 创建初始role节点
			this.createInitialRole(receiver);
			result=this.roleTreeRepository.findAll(getSpecification(receiver));
		}
		return result;
	}
	
	/**
	 * 创建初始节点
	 * @param receiver
	 */
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	private void createInitialRole(RoleTreeReceiver receiver) {
		RoleTree roleTree=new RoleTree();
		roleTree.setCompany(receiver.getCompany());
		if (receiver.getCompany().equals("0")) { // 公司等于0的是管理员，一般是数据库直接设置
			roleTree.setDepth(0);
			roleTree.setParentid(-1l);
			roleTree.setTitle(ALL_NODE);
			roleTree.setType("节点");
		} else {
			roleTree.setDepth(1);
			roleTree.setParentid(this.findInitialNode().getId());
			roleTree.setTitle(receiver.getCompany());
			roleTree.setType("节点");
		}
		try {
			roleTree=this.saveNew(roleTree);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		// 给初始节点添加一个权限、状态、职级
		List<RoleTree> list=new ArrayList<>();
		RoleTree power=new RoleTree();// 给初始节点添加一个权限节点
		power.setDepth(roleTree.getDepth()+1);
		power.setParentid(roleTree.getId());
		power.setTitle(TYPE_POWER);
		power.setType(TYPE_POWER);
		power.setCompany(roleTree.getCompany());
		list.add(power);
		
		
		RoleTree status=new RoleTree();// 给初始节点添加一个状态节点
		status.setDepth(roleTree.getDepth()+1);
		status.setParentid(roleTree.getId());
		status.setTitle(TYPE_STATUS);
		status.setType(TYPE_STATUS);
		status.setCompany(roleTree.getCompany());
		list.add(status);
		
		RoleTree post=new RoleTree();// 给初始节点添加一个职级节点
		post.setDepth(roleTree.getDepth()+1);
		post.setParentid(roleTree.getId());
		post.setTitle(TYPE_POST);
		post.setType(TYPE_POST);
		post.setCompany(roleTree.getCompany());
		list.add(post);
		try {
			this.roleTreeRepository.saveAll(list);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 查询初始节点，即：company=0,title=所有节点
	 * @param title
	 * @param company
	 * @return
	 */
	private RoleTree findInitialNode() {
		RoleTree result = this.findByTitleAndCompany(ALL_NODE, "0");
		if (result==null) {
			RoleTree roleTree=new RoleTree();
			roleTree.setCompany("0");
			roleTree.setDepth(0);
			roleTree.setParentid(-1l);
			roleTree.setTitle(ALL_NODE);
			roleTree.setType("节点");
			result=this.saveNew(roleTree);
		}
		return result;
	}
	private RoleTree findByTitleAndCompany(String title,String company) {
		RoleTree result = this.roleTreeRepository.findByTitleAndCompany(title,company);
		return result;
		
	}
	@Override
	public String save(String entity) {
		RoleTree receiver = JsonUtils.getGson().fromJson(entity, RoleTree.class);
		if (this.existsByTitleAndCompany(receiver.getTitle(),receiver.getCompany())) {
			throw new RuntimeException("已经存在同名节点");
		};
		return JsonUtils.getGson().toJson(this.saveNew(receiver));
	}

	private RoleTree saveNew(RoleTree receiver) {
		try {
			return this.roleTreeRepository.save(receiver);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败");
		}
	}

	private boolean existsByTitleAndCompany(String title, String company) {
		return this.roleTreeRepository.existsByTitleAndCompany(title,company);
		
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			if (this.hasChildren(id)) { // 存在子节点无法删除
				throw new RuntimeException("存在子节点无法删除！！");
			}
			this.roleTreeRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("删除失败");
		}
	}
	/**
	 * 判断是否存在子节点
	 * @param id
	 * @return
	 */
	@Override
	public boolean hasChildren(Long id) {
		
		return this.roleTreeRepository.existsByParentid(id);
	}
	@Override
	public boolean exists(String json) {
		RoleTree receiver = JsonUtils.getGson().fromJson(json, RoleTree.class);
		try {
			return this.existsByTitleAndCompany(receiver.getTitle(),receiver.getCompany());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Pageable getPageRequest(RoleTreeReceiver receiver) {
		return PageRequest.of(receiver.getPageIndex()==null?0:(receiver.getPageIndex()-1), 
				receiver.getPageSize()==null?10:receiver.getPageSize());
	}

	@Override
	public Specification<RoleTree> getSpecification(RoleTreeReceiver receiver) {
		Specification<RoleTree> specification=new Specification<RoleTree>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<RoleTree> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
				if (!StringUtils.isEmpty(receiver.getCompany()) && !receiver.getCompany().equals("0")) { // 当 company 不为空时 当title,这0时全部可见
					predicates.add(criteriaBuilder.equal(root.get("company"), receiver.getCompany()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
		return specification;
	}

	@Override
	public String findAllAstree(String json) {
		RoleTreeReceiver receiver = JsonUtils.getGson().fromJson(json, RoleTreeReceiver.class);
		List<RoleTree> list = this.findAll(receiver);
		String resutl=this.transformToTree(list);
		return resutl;
	}
	/**
	 * @param list
	 * @return
	 */
	private String transformToTree(List<RoleTree> list) {
		if (list==null||list.size()==0) {
			return null;
		}
		List<PowerNode> result=new ArrayList<>();
		// 获取节点深度最小值
		Integer depth = list.stream().min(Comparator.comparing(RoleTree::getDepth)).get().getDepth();
//		System.out.println("***************************depth="+depth);
		// 按父节点分组
		Map<Long, List<RoleTree>> groupMap=list.stream().collect(Collectors.groupingBy(RoleTree::getParentid));
		
		// 将父节点下的所有节点存入父节点
		List<PowerCompo> compos=new ArrayList<>();
		groupMap.entrySet().stream().forEach(group ->{
			PowerCompo parent=new PowerCompo(group.getKey());
			group.getValue().stream().forEach(node -> {
				PowerCompo child=new PowerCompo(node.getId(), node.getTitle(),node.getDepth(),node.getParentid(),null);
				child.setType(node.getType());
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

}
