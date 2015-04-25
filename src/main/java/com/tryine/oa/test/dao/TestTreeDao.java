/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.test.dao;

import com.tryine.oa.common.persistence.TreeDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author Summer
 * @version 2015-03-30
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}