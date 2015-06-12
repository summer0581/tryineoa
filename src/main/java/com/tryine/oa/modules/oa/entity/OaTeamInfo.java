/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.tryine.oa.common.persistence.DataEntity;
import com.tryine.oa.common.utils.Collections3;
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 团队信息管理Entity
 * @author pengyue
 * @version 2015-06-04
 */
public class OaTeamInfo extends DataEntity<OaTeamInfo> {
	
	private static final long serialVersionUID = 1L;
	private String teamName;		// 团队名称
	private User headUser;		// 负责人
	private List<OaTeamInfoRelation> oaTeamInfoRelationList = Lists.newArrayList();		// 子表列表
	private String oaTeamInfoRelationNames;//团队所有成员名字  from回显用
	private String oaTeamInfoRelationNamesList;//团队所有成员名字  list回显用
	
	public OaTeamInfo() {
		super();
	}

	public OaTeamInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="团队名称长度必须介于 0 和 64 之间")
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public User getHeadUser() {
		return headUser;
	}	

	public void setHeadUser(User headUser) {	
		this.headUser = headUser;
	}
	
	public List<OaTeamInfoRelation> getOaTeamInfoRelationList() {
		return oaTeamInfoRelationList;
	}

	public void setOaTeamInfoRelationList(List<OaTeamInfoRelation> oaTeamInfoRelationList) {
		this.oaTeamInfoRelationList = oaTeamInfoRelationList;
	}

	public String getOaTeamInfoRelationIds() {
		return Collections3.extractToString(oaTeamInfoRelationList, "user.id", ",") ;
	}
	
	/**
	 * 设置团队成员ID
	 * @return
	 */
	public void setOaTeamInfoRelationIds(String ids) {
		this.oaTeamInfoRelationList = Lists.newArrayList();
		for (String id : StringUtils.split(ids, ",")){
			OaTeamInfoRelation entity = new OaTeamInfoRelation();
			entity.setId(IdGen.uuid());
			entity.setOaTeam(this);
			entity.setUser(new User(id));
			this.oaTeamInfoRelationList.add(entity);
		}
	}
	/**
	 * 获取团队成员名字 用于 list显示
	 * @return
	 */
	public String getOaTeamInfoRelationNamesList() {
		return oaTeamInfoRelationNamesList ;
	}
	
	/**
	 * 获取团队成员名字 用于 form显示
	 * @return
	 */
	public String getOaTeamInfoRelationNames() {
		return Collections3.extractToString(oaTeamInfoRelationList, "user.name", ",") ;
	}
	public void setOaTeamInfoRelationNames(String oaTeamInfoRelationNames) {
		this.oaTeamInfoRelationNames = oaTeamInfoRelationNames;
	}

}