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
import com.tryine.oa.modules.oa.entity.OaTeamInfo;
import com.tryine.oa.modules.oa.dao.OaTeamInfoDao;
import com.tryine.oa.modules.oa.entity.OaTeamInfoRelation;
import com.tryine.oa.modules.oa.dao.OaTeamInfoRelationDao;

/**
 * 团队信息管理Service
 * 
 * @author pengyue
 * @version 2015-06-04
 */
@Service
@Transactional(readOnly = true)
public class OaTeamInfoService extends CrudService<OaTeamInfoDao, OaTeamInfo> {

	@Autowired
	private OaTeamInfoRelationDao oaTeamInfoRelationDao;

	@Autowired
	private OaTeamInfoDao oaTeamInfoDao;

	public OaTeamInfo get(String id) {
		OaTeamInfo oaTeamInfo = super.get(id);
		oaTeamInfo.setOaTeamInfoRelationList(oaTeamInfoRelationDao
				.findList(new OaTeamInfoRelation(oaTeamInfo)));
		return oaTeamInfo;
	}

	public List<OaTeamInfo> findList(OaTeamInfo oaTeamInfo) {
		return super.findList(oaTeamInfo);
	}

	public Page<OaTeamInfo> findPage(Page<OaTeamInfo> page,
			OaTeamInfo oaTeamInfo) {
		return super.findPage(page, oaTeamInfo);
	}

	@Transactional(readOnly = false)
	public void save(OaTeamInfo oaTeamInfo) {
		super.save(oaTeamInfo);
		/*
		 * 主子表保存逻辑： 1、判断原主表ID是否存在?更新：保存 2、如果是更新：子表根据团队ID先删除再保存 3、如果是保存：子表保存
		 */
		if (StringUtils.isEmpty(oaTeamInfo.getId())) {
			oaTeamInfo.preInsert();
			oaTeamInfoDao.insert(oaTeamInfo);
		}else{
			oaTeamInfo.preUpdate();
			oaTeamInfoDao.update(oaTeamInfo);
		}	
		
		if (!StringUtils.isEmpty(oaTeamInfo.getId())) {
			OaTeamInfoRelation oatir = new OaTeamInfoRelation();
			oatir.setOaTeam(oaTeamInfo);
			oaTeamInfoRelationDao.delete(oatir);
		}
		for (OaTeamInfoRelation oaTeamInfoRelation : oaTeamInfo
				.getOaTeamInfoRelationList()) {
			oaTeamInfoRelation.preInsert();
			oaTeamInfoRelationDao.insert(oaTeamInfoRelation);
		}
	}

	@Transactional(readOnly = false)
	public void delete(OaTeamInfo oaTeamInfo) {
		super.delete(oaTeamInfo);
		oaTeamInfoRelationDao.delete(new OaTeamInfoRelation(oaTeamInfo));
	}

}