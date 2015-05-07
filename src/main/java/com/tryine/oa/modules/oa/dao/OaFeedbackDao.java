/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaFeedback;

/**
 * 意见反馈DAO接口
 * @author Summer
 * @version 2015-05-05
 */
@MyBatisDao
public interface OaFeedbackDao extends CrudDao<OaFeedback> {
	
}