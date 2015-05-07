/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import java.util.List;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaFeedbackReceived;

/**
 * 意见反馈DAO接口
 * @author Summer
 * @version 2015-05-05
 */
@MyBatisDao
public interface OaFeedbackReceivedDao extends CrudDao<OaFeedbackReceived> {
	
	/**
	 * 插入反馈记录
	 * @param oaFeedbackReceivedList
	 * @return
	 */
	public int insertAll(List<OaFeedbackReceived> oaFeedbackReceivedList);
	
	/**
	 * 根据反馈ID删除反馈记录
	 * @param oaFeedbackId 反馈ID
	 * @return
	 */
	public int deleteByOaFeedbackId(String oaFeedbackId);
}