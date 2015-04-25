/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.test.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.test.entity.TestData;

/**
 * 单表生成DAO接口
 * @author Summer
 * @version 2015-03-30
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {
	
}