/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaZhiduContent;
import com.tryine.oa.modules.oa.dao.OaZhiduContentDao;

/**
 * 规章制度内容维护Service
 * @author Summer
 * @version 2015-04-27
 */
@Service
@Transactional(readOnly = true)
public class OaZhiduContentService extends CrudService<OaZhiduContentDao, OaZhiduContent> {

	public OaZhiduContent get(String id) {
		return super.get(id);
	}
	
	public List<OaZhiduContent> findList(OaZhiduContent oaZhiduContent) {
		return super.findList(oaZhiduContent);
	}
	
	public Page<OaZhiduContent> findPage(Page<OaZhiduContent> page, OaZhiduContent oaZhiduContent) {
		return super.findPage(page, oaZhiduContent);
	}
	
	@Transactional(readOnly = false)
	public void save(OaZhiduContent oaZhiduContent) {
		super.save(oaZhiduContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaZhiduContent oaZhiduContent) {
		super.delete(oaZhiduContent);
	}
	
}