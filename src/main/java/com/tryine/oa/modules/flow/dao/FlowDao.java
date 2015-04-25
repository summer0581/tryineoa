/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.flow.dao;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.flow.entity.Flow;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface FlowDao extends CrudDao<Flow> {

	
}
