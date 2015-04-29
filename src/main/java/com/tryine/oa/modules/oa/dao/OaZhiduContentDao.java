/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaZhiduContent;

/**
 * 规章制度内容维护DAO接口
 * @author Summer
 * @version 2015-04-27
 */
@MyBatisDao
public interface OaZhiduContentDao extends CrudDao<OaZhiduContent> {
	
}