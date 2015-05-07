/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaFeedbackBox;
import com.tryine.oa.modules.oa.dao.OaFeedbackBoxDao;

/**
 * 意见箱维护Service
 * @author Summer
 * @version 2015-05-05
 */
@Service
@Transactional(readOnly = true)
public class OaFeedbackBoxService extends CrudService<OaFeedbackBoxDao, OaFeedbackBox> {

	public OaFeedbackBox get(String id) {
		return super.get(id);
	}
	
	public List<OaFeedbackBox> findList(OaFeedbackBox oaFeedbackBox) {
		return super.findList(oaFeedbackBox);
	}
	
	public Page<OaFeedbackBox> findPage(Page<OaFeedbackBox> page, OaFeedbackBox oaFeedbackBox) {
		return super.findPage(page, oaFeedbackBox);
	}
	
	@Transactional(readOnly = false)
	public void save(OaFeedbackBox oaFeedbackBox) {
		super.save(oaFeedbackBox);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaFeedbackBox oaFeedbackBox) {
		super.delete(oaFeedbackBox);
	}
	
}