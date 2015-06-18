/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.tryine.oa.modules.sys.entity.User;
import com.tryine.oa.common.persistence.DataEntity;

/**
 * 只读共享客户与用户关系表Entity
 * @author pengyue
 * @version 2015-06-11
 */
public class OaCustomerReadShareRelation extends DataEntity<OaCustomerReadShareRelation> {
	
	private static final long serialVersionUID = 1L;
	private OaCustomerRmanager oaCustomerRead;		// 客户只读共享ID
	private User readUser;		// 只读人员ID
	
	public OaCustomerReadShareRelation() {
		super();
	}

	public OaCustomerReadShareRelation(String id){
		super(id);
	}
	
	public OaCustomerReadShareRelation(OaCustomerRmanager oaCustomerRead){
		this.oaCustomerRead = oaCustomerRead;
	}
	
	@Length(min=0, max=64, message="客户只读共享ID长度必须介于 0 和 64 之间")
	public OaCustomerRmanager getOaCustomerRead() {
		return oaCustomerRead;
	}

	public void setOaCustomerRead(OaCustomerRmanager oaCustomerRead) {
		this.oaCustomerRead = oaCustomerRead;
	}
	
	public User getReadUser() {
		return readUser;
	}

	public void setReadUser(User readUser) {
		this.readUser = readUser;
	}
	
}