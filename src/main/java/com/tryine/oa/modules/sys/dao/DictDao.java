/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.sys.dao;

import java.util.List;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
