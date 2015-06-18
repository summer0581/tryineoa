/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.tryine.oa.modules.sys.entity.User;
import com.tryine.oa.common.persistence.DataEntity;

/**
 * 可写共享客户与用户关系表Entity
 * @author pengyue
 * @version 2015-06-11
 */
public class OaCustomerWriteShareRelation extends DataEntity<OaCustomerWriteShareRelation> {
	
	private static final long serialVersionUID = 1L;
	private OaCustomerRmanager oaCustomerWrite;		// 可写客户ID
	private User writeUser;		// 可写人员ID
	
	public OaCustomerWriteShareRelation() {
		super();
	}

	public OaCustomerWriteShareRelation(String id){
		super(id);
	}
	
	public OaCustomerWriteShareRelation(OaCustomerRmanager oaCustomerWrite){
		this.oaCustomerWrite = oaCustomerWrite;
	}

	@Length(min=0, max=64, message="可写客户ID长度必须介于 0 和 64 之间")
	public OaCustomerRmanager getOaCustomerWrite() {
		return oaCustomerWrite;
	}

	public void setOaCustomerWrite(OaCustomerRmanager oaCustomerWrite) {
		this.oaCustomerWrite = oaCustomerWrite;
	}
	
	public User getWriteUser() {
		return writeUser;
	}

	public void setWriteUser(User writeUser) {
		this.writeUser = writeUser;
	}
	
}