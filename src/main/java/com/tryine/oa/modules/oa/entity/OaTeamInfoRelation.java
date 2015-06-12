/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tryine.oa.modules.sys.entity.User;

import com.tryine.oa.common.persistence.DataEntity;

/**
 * 团队信息管理Entity
 * @author pengyue
 * @version 2015-06-04
 */
public class OaTeamInfoRelation extends DataEntity<OaTeamInfoRelation> {
	
	private static final long serialVersionUID = 1L;
	private OaTeamInfo oaTeam;		// 团队ID 父类
	private User user;		// 成员ID
	
	public OaTeamInfoRelation() {
		super();
	}

	public OaTeamInfoRelation(String id){
		super(id);
	}

	public OaTeamInfoRelation(OaTeamInfo oaTeam){
		this.oaTeam = oaTeam;
	}

	@JsonBackReference
	public OaTeamInfo getOaTeam() {
		return oaTeam;
	}

	public void setOaTeam(OaTeamInfo oaTeam) {
		this.oaTeam = oaTeam;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}