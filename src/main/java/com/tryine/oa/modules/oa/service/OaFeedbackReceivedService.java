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
import com.tryine.oa.modules.oa.dao.OaFeedbackDao;
import com.tryine.oa.modules.oa.dao.OaFeedbackReceivedDao;
import com.tryine.oa.modules.oa.entity.OaFeedback;
import com.tryine.oa.modules.oa.entity.OaFeedbackReceived;

/**
 * 意见反馈接收人Service
 * @author Summer
 * @version 2015-05-05
 */
@Service
@Transactional(readOnly = true)
public class OaFeedbackReceivedService extends CrudService<OaFeedbackReceivedDao, OaFeedbackReceived> {

	@Autowired
	private OaFeedbackReceivedDao oaFeedbackReceivedDao;
	
	public OaFeedbackReceived get(String id) {
		OaFeedbackReceived oaFeedbackReceived = super.get(id);
		return oaFeedbackReceived;
	}
	
	public List<OaFeedbackReceived> findList(OaFeedbackReceived oaFeedbackReceived) {
		return super.findList(oaFeedbackReceived);
	}
	
	public Page<OaFeedbackReceived> findPage(Page<OaFeedbackReceived> page, OaFeedbackReceived oaFeedbackReceived) {
		return super.findPage(page, oaFeedbackReceived);
	}
	
	@Transactional(readOnly = false)
	public void save(OaFeedbackReceived oaFeedbackReceived) {
		super.save(oaFeedbackReceived);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaFeedbackReceived oaFeedbackReceived) {
		super.delete(oaFeedbackReceived);
	}
	
}