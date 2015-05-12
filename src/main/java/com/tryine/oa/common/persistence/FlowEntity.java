/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.common.persistence;


/**
 * 流程Entity类
 * @author ThinkGem
 * @version 2015-05-11
 */
public abstract class FlowEntity<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;

	private String processInstanceId;
	private String userId;
	private String processDefinitionKey;
	private String status;

	
	public FlowEntity() {
		super();
	}
	
	public FlowEntity(String id) {
		super(id);
	}

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
	
	
	
}
