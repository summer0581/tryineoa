/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.dao.OaMessageRecordDao;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;

/**
 * 内部消息记录Service
 * @author Summer
 * @version 2015-04-28
 */
@Service
@Transactional(readOnly = true)
public class OaMessageRecordService extends CrudService<OaMessageRecordDao, OaMessageRecord> {

	@Autowired
	private OaMessageRecordDao oaMessageRecordDao;
	
	public OaMessageRecord get(String id) {
		OaMessageRecord oaMessageRecord = super.get(id);
		return oaMessageRecord;
	}
	
	public List<OaMessageRecord> findList(OaMessageRecord oaMessageRecord) {
		return super.findList(oaMessageRecord);
	}
	
	public Page<OaMessageRecord> findPage(Page<OaMessageRecord> page, OaMessageRecord oaMessageRecord) {
		return super.findPage(page, oaMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaMessageRecord oaMessageRecord) {
		super.delete(oaMessageRecord);
	}
	
}