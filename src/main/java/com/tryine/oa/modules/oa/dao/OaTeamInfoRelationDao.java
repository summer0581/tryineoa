/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaTeamInfoRelation;

/**
 * 团队信息管理DAO接口
 * @author pengyue
 * @version 2015-06-04
 */
@MyBatisDao
public interface OaTeamInfoRelationDao extends CrudDao<OaTeamInfoRelation> {
	
}