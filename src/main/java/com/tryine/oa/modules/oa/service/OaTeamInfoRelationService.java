/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.oa.entity.OaTeamInfoRelation;
import com.tryine.oa.modules.oa.dao.OaTeamInfoRelationDao;

/**
 * 团队信息所属关系管理Service
 * @author pengyue
 * @version 2015-06-04
 */
@Service
@Transactional(readOnly = true)
public class OaTeamInfoRelationService extends CrudService<OaTeamInfoRelationDao, OaTeamInfoRelation> {

	
	public OaTeamInfoRelation get(String id) {
		OaTeamInfoRelation oaTeamInfoRelation = super.get(id);
		return oaTeamInfoRelation;
	}
	
	public List<OaTeamInfoRelation> findList(OaTeamInfoRelation oaTeamInfoRelation) {
		return super.findList(oaTeamInfoRelation);
	}
	
	public Page<OaTeamInfoRelation> findPage(Page<OaTeamInfoRelation> page, OaTeamInfoRelation oaTeamInfoRelation) {
		return super.findPage(page, oaTeamInfoRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(OaTeamInfoRelation oaTeamInfoRelation) {
		super.save(oaTeamInfoRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaTeamInfoRelation oaTeamInfoRelation) {
		super.delete(oaTeamInfoRelation);
	}
	
}