/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import java.util.List;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaCustomerRelation;
import com.tryine.oa.modules.oa.entity.OaNotifyRecord;

/**
 * 客户信息管理DAO接口
 * @author Summer
 * @version 2015-05-04
 */
@MyBatisDao
public interface OaCustomerRelationDao extends CrudDao<OaCustomerRelation> {
	
	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaCustomerRelation> oaCustomerRelationList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaCustomerManagerId(String oaCustomerId);
}