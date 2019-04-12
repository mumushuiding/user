package com.lt.cloud.pojos;

public class PowerCompo extends PowerNode{
	public PowerCompo(Long id) {
		super(id);
	}
	public PowerCompo(Long id, String title, Integer depth, Long parentid, String company) {
		super(id, title,depth,parentid,company);
	}
	public PowerCompo(Long id, String title, Integer depth, Long parentid, String company, String charger) {
		super(id, title,depth,parentid,company,charger);
	}
	@Override
	public void add(PowerNode node) {
		this.children.add(node);
		
	}

	@Override
	public void remove(PowerNode node) {
		for (PowerNode powerNode : children) {
			if (powerNode instanceof PowerCompo) { // 如果子节点是组合节点，那么执行子节点的remove方法
				((PowerCompo) powerNode).remove(node);
			}
		}
		this.children.remove(node);
		
	}

	
}
