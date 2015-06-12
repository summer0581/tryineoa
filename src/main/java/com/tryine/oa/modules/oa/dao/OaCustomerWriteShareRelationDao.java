/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import java.util.List;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaCustomerWriteShareRelation;

/**
 * 可写共享客户与用户关系表DAO接口
 * @author pengyue
 * @version 2015-06-11
 */
@MyBatisDao
public interface OaCustomerWriteShareRelationDao extends CrudDao<OaCustomerWriteShareRelation> {
	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaCustomerWriteShareRelation> oaCustomerWriteShareRelationList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaCustomerManagerId(String oaCustomerWriteId);
}