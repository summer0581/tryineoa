/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.tryine.oa.common.persistence.TreeEntity;

/**
 * 规章制度目录维护Entity
 * @author Summer
 * @version 2015-04-27
 */
public class OaZhiduMl extends TreeEntity<OaZhiduMl> {
	
	private static final long serialVersionUID = 1L;
	private OaZhiduMl parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	
	public OaZhiduMl() {
		super();
	}

	public OaZhiduMl(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public OaZhiduMl getParent() {
		return parent;
	}

	public void setParent(OaZhiduMl parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}