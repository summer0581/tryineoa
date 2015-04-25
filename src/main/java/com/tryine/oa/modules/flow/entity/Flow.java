/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.flow.entity;

import com.tryine.oa.common.persistence.BaseEntity;

/**
 * 工作流Entity
 * @author ThinkGem
 * @version 2015-04-09
 */
public class Flow extends BaseEntity<Flow> {
	
	private static final long serialVersionUID = 1L;
	 
	private String processInstanceId;
	private String userId;
	private String processDefinitionKey;
	private String status;
	

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void preInsert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate() {
		// TODO Auto-generated method stub
		
	}

}


