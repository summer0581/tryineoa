/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.dao;

import java.util.List;

import com.tryine.oa.common.persistence.CrudDao;
import com.tryine.oa.common.persistence.annotation.MyBatisDao;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;

/**
 * 内部消息DAO接口
 * @author Summer
 * @version 2015-04-28
 */
@MyBatisDao
public interface OaMessageRecordDao extends CrudDao<OaMessageRecord> {
	
	/**
	 * 插入消息记录
	 * @param oaMessageRecordList
	 * @return
	 */
	public int insertAll(List<OaMessageRecord> oaMessageRecordList);
	
	/**
	 * 根据消息ID删除消息记录
	 * @param oaMessageId 消息ID
	 * @return
	 */
	public int deleteByOaMessageId(String oaMessageId);
}