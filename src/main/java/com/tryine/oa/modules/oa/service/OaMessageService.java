/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fix.fixflow.core.impl.identity.UserTo;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.modules.oa.dao.OaMessageDao;
import com.tryine.oa.modules.oa.dao.OaMessageRecordDao;
import com.tryine.oa.modules.oa.entity.OaMessage;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 内部消息Service
 * @author Summer
 * @version 2015-04-28
 */
@Service
@Transactional(readOnly = true)
public class OaMessageService extends CrudService<OaMessageDao, OaMessage> {

	@Autowired
	private OaMessageRecordDao oaMessageRecordDao;
	
	public OaMessage get(String id) {
		OaMessage oaMessage = super.get(id);
		oaMessage.setOaMessageRecordList(oaMessageRecordDao.findList(new OaMessageRecord(oaMessage)));
		return oaMessage;
	}
	
	public List<OaMessage> findList(OaMessage oaMessage) {
		return super.findList(oaMessage);
	}
	
	public Page<OaMessage> findPage(Page<OaMessage> page, OaMessage oaMessage) {
		return super.findPage(page, oaMessage);
	}
	
	/**
	 * 获取消息数目
	 * @param oaMessage
	 * @return
	 */
	public Long findCount(OaMessage oaMessage) {
		return dao.findCount(oaMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(OaMessage oaMessage) {
		super.save(oaMessage);
		// 更新发送接受人记录
		oaMessageRecordDao.deleteByOaMessageId(oaMessage.getId());
		if (oaMessage.getOaMessageRecordList().size() > 0){
			oaMessageRecordDao.insertAll(oaMessage.getOaMessageRecordList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OaMessage oaMessage) {
		super.delete(oaMessage);
		oaMessageRecordDao.delete(new OaMessageRecord(oaMessage));
	}
	
	
}