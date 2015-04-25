/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.sys.dao;

import com.tryine.oa.common.persistence.TreeDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
