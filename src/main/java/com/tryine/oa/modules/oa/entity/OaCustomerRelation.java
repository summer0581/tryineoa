/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import com.tryine.oa.modules.sys.entity.User;

import com.tryine.oa.common.persistence.DataEntity;

/**
 * 客户信息管理Entity
 * @author Summer
 * @version 2015-05-04
 */
public class OaCustomerRelation extends DataEntity<OaCustomerRelation> {
	
	private static final long serialVersionUID = 1L;
	private OaCustomerRmanager oaCustomer;		// 客户ID 父类
	private User ownerUser;		// 拥有者ID
	
	
	public OaCustomerRelation() {
		super();
	}

	public OaCustomerRelation(String id){
		super(id);
	}

	public OaCustomerRelation(OaCustomerRmanager oaCustomer){
		this.oaCustomer = oaCustomer;
	}

	@Length(min=0, max=64, message="客户ID长度必须介于 0 和 64 之间")
	public OaCustomerRmanager getOaCustomer() {
		return oaCustomer;
	}

	public void setOaCustomer(OaCustomerRmanager oaCustomer) {
		this.oaCustomer = oaCustomer;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
/*
	public User getReadUser() {
		return readUser;
	}

	public void setReadUser(User readUser) {
		this.readUser = readUser;
	}

	public User getWriteUser() {
		return writeUser;
	}

	public void setWriteUser(User writeUser) {
		this.writeUser = writeUser;
	}
	*/
}