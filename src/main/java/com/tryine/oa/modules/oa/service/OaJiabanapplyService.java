/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaJiabanapply;
import com.tryine.oa.modules.oa.dao.OaJiabanapplyDao;

/**
 * 加班申请Service
 * @author Summer
 * @version 2015-05-11
 */
@Service
@Transactional(readOnly = true)
public class OaJiabanapplyService extends CrudService<OaJiabanapplyDao, OaJiabanapply> {

	public OaJiabanapply get(String id) {
		return super.get(id);
	}
	
	public List<OaJiabanapply> findList(OaJiabanapply oaJiabanapply) {
		return super.findList(oaJiabanapply);
	}
	
	public Page<OaJiabanapply> findPage(Page<OaJiabanapply> page, OaJiabanapply oaJiabanapply) {
		return super.findPage(page, oaJiabanapply);
	}
	
	@Transactional(readOnly = false)
	public void save(OaJiabanapply oaJiabanapply) {
		super.save(oaJiabanapply);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaJiabanapply oaJiabanapply) {
		super.delete(oaJiabanapply);
	}
	
}