/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import com.tryine.oa.common.persistence.TreeDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaZhiduMl;

/**
 * 规章制度目录维护DAO接口
 * @author Summer
 * @version 2015-04-27
 */
@MyBatisDao
public interface OaZhiduMlDao extends TreeDao<OaZhiduMl> {
	
}