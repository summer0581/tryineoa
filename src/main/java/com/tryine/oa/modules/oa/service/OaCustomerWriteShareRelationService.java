/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaCustomerWriteShareRelation;
import com.tryine.oa.modules.oa.dao.OaCustomerWriteShareRelationDao;

/**
 * 可写共享客户与用户关系表Service
 * @author pengyue
 * @version 2015-06-11
 */
@Service
@Transactional(readOnly = true)
public class OaCustomerWriteShareRelationService extends CrudService<OaCustomerWriteShareRelationDao, OaCustomerWriteShareRelation> {

	public OaCustomerWriteShareRelation get(String id) {
		return super.get(id);
	}
	
	public List<OaCustomerWriteShareRelation> findList(OaCustomerWriteShareRelation oaCustomerWriteShareRelation) {
		return super.findList(oaCustomerWriteShareRelation);
	}
	
	public Page<OaCustomerWriteShareRelation> findPage(Page<OaCustomerWriteShareRelation> page, OaCustomerWriteShareRelation oaCustomerWriteShareRelation) {
		return super.findPage(page, oaCustomerWriteShareRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(OaCustomerWriteShareRelation oaCustomerWriteShareRelation) {
		super.save(oaCustomerWriteShareRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaCustomerWriteShareRelation oaCustomerWriteShareRelation) {
		super.delete(oaCustomerWriteShareRelation);
	}
	
}