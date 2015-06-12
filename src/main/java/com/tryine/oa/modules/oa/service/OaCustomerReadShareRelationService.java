/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaCustomerReadShareRelation;
import com.tryine.oa.modules.oa.dao.OaCustomerReadShareRelationDao;

/**
 * 只读共享客户与用户关系表Service
 * @author pengyue
 * @version 2015-06-11
 */
@Service
@Transactional(readOnly = true)
public class OaCustomerReadShareRelationService extends CrudService<OaCustomerReadShareRelationDao, OaCustomerReadShareRelation> {

	public OaCustomerReadShareRelation get(String id) {
		return super.get(id);
	}
	
	public List<OaCustomerReadShareRelation> findList(OaCustomerReadShareRelation oaCustomerReadShareRelation) {
		return super.findList(oaCustomerReadShareRelation);
	}
	
	public Page<OaCustomerReadShareRelation> findPage(Page<OaCustomerReadShareRelation> page, OaCustomerReadShareRelation oaCustomerReadShareRelation) {
		return super.findPage(page, oaCustomerReadShareRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(OaCustomerReadShareRelation oaCustomerReadShareRelation) {
		super.save(oaCustomerReadShareRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaCustomerReadShareRelation oaCustomerReadShareRelation) {
		super.delete(oaCustomerReadShareRelation);
	}
	
}