/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaCustomerRmanager;

/**
 * 客户信息管理DAO接口
 * @author Summer
 * @version 2015-05-04
 */
@MyBatisDao
public interface OaCustomerRmanagerDao extends CrudDao<OaCustomerRmanager> {
	
}