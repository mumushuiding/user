package com.lt.cloud.pojos;

import java.util.ArrayList;
import java.util.List;

public class Pojo4Tree {
	public Long value;
	public String label;
	public List<Pojo4Tree> children=new ArrayList<>();
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void insert(Pojo4Tree pojo) {
		this.children.add(pojo);
	}
	
}
