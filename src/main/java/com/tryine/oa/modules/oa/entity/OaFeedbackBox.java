/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.tryine.oa.common.persistence.DataEntity;

/**
 * 意见箱维护Entity
 * @author Summer
 * @version 2015-05-05
 */
public class OaFeedbackBox extends DataEntity<OaFeedbackBox> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 意见箱名称
	private String userids;		// 管理人员
	private String usernames;   //管理人员名称
	
	public OaFeedbackBox() {
		super();
	}

	public OaFeedbackBox(String id){
		super(id);
	}

	@Length(min=1, max=64, message="意见箱名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=2000, message="管理人员长度必须介于 0 和 2000 之间")
	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}
	@Length(min=0, max=2000, message="管理人员名称长度必须介于 0 和 2000 之间")
	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
	
	
	
}