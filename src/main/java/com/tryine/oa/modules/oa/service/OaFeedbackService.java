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
 * 意见反馈Service
 * @author Summer
 * @version 2015-05-05
 */
@Service
@Transactional(readOnly = true)
public class OaFeedbackService extends CrudService<OaFeedbackDao, OaFeedback> {

	@Autowired
	private OaFeedbackReceivedDao oaFeedbackReceivedDao;
	
	public OaFeedback get(String id) {
		OaFeedback oaFeedback = super.get(id);
		oaFeedback.setOaFeedbackReceivedList(oaFeedbackReceivedDao.findList(new OaFeedbackReceived(oaFeedback)));
		return oaFeedback;
	}
	
	public List<OaFeedback> findList(OaFeedback oaFeedback) {
		return super.findList(oaFeedback);
	}
	
	public Page<OaFeedback> findPage(Page<OaFeedback> page, OaFeedback oaFeedback) {
		return super.findPage(page, oaFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(OaFeedback oaFeedback) {
		super.save(oaFeedback);
		// 更新发送接受人记录
		oaFeedbackReceivedDao.deleteByOaFeedbackId(oaFeedback.getId());
		if (oaFeedback.getOaFeedbackReceivedList().size() > 0){
			oaFeedbackReceivedDao.insertAll(oaFeedback.getOaFeedbackReceivedList());
		}	
	}
	
	@Transactional(readOnly = false)
	public void delete(OaFeedback oaFeedback) {
		super.delete(oaFeedback);
		oaFeedbackReceivedDao.delete(new OaFeedbackReceived(oaFeedback));
	}
	
}