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
import com.tryine.oa.modules.oa.dao.OaCustomerReadShareRelationDao;
import com.tryine.oa.modules.oa.dao.OaCustomerRelationDao;
import com.tryine.oa.modules.oa.dao.OaCustomerRmanagerDao;
import com.tryine.oa.modules.oa.dao.OaCustomerWriteShareRelationDao;
import com.tryine.oa.modules.oa.entity.OaCustomerReadShareRelation;
import com.tryine.oa.modules.oa.entity.OaCustomerRelation;
import com.tryine.oa.modules.oa.entity.OaCustomerRmanager;
import com.tryine.oa.modules.oa.entity.OaCustomerWriteShareRelation;

/**
 * 客户信息管理Service
 * @author Summer
 * @version 2015-05-04
 */
@Service
@Transactional(readOnly = true)
public class OaCustomerRmanagerService extends CrudService<OaCustomerRmanagerDao, OaCustomerRmanager> {

	@Autowired
	private OaCustomerRelationDao oaCustomerRelationDao;
	@Autowired
	private OaCustomerReadShareRelationDao oaCustomerReadShareRelationDao;
	@Autowired
	private OaCustomerWriteShareRelationDao oaCustomerWriteShareRelationDao;
	
	
	public OaCustomerRmanager get(String id) {
		OaCustomerRmanager oaCustomerRmanager = super.get(id);
		oaCustomerRmanager.setOaCustomerRelationList(oaCustomerRelationDao.findList(new OaCustomerRelation(oaCustomerRmanager)));
		oaCustomerRmanager.setOaCustomerReadShareRelationList(oaCustomerReadShareRelationDao.findList(new OaCustomerReadShareRelation(oaCustomerRmanager)));
		oaCustomerRmanager.setOaCustomerWriteShareRelationList(oaCustomerWriteShareRelationDao.findList(new OaCustomerWriteShareRelation(oaCustomerRmanager)));
		return oaCustomerRmanager;
	}
	
	public List<OaCustomerRmanager> findList(OaCustomerRmanager oaCustomerRmanager) {
		return super.findList(oaCustomerRmanager);
	}
	
	public Page<OaCustomerRmanager> findPage(Page<OaCustomerRmanager> page, OaCustomerRmanager oaCustomerRmanager) {
		return super.findPage(page, oaCustomerRmanager);
	}
	
	@Transactional(readOnly = false)
	public void save(OaCustomerRmanager oaCustomerRmanager) {
		super.save(oaCustomerRmanager);
		// 更新子表记录
		oaCustomerRelationDao.deleteByOaCustomerManagerId(oaCustomerRmanager.getId());
		oaCustomerReadShareRelationDao.deleteByOaCustomerManagerId(oaCustomerRmanager.getId());
		oaCustomerWriteShareRelationDao.deleteByOaCustomerManagerId(oaCustomerRmanager.getId());
		
		if (oaCustomerRmanager.getOaCustomerRelationList().size() > 0){
			oaCustomerRelationDao.insertAll(oaCustomerRmanager.getOaCustomerRelationList());
		}
		if (oaCustomerRmanager.getOaCustomerReadShareRelationList().size() > 0){
			oaCustomerReadShareRelationDao.insertAll(oaCustomerRmanager.getOaCustomerReadShareRelationList());
		}
		if (oaCustomerRmanager.getOaCustomerWriteShareRelationList().size() > 0){
			oaCustomerWriteShareRelationDao.insertAll(oaCustomerRmanager.getOaCustomerWriteShareRelationList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OaCustomerRmanager oaCustomerRmanager) {
		super.delete(oaCustomerRmanager);
		oaCustomerRelationDao.delete(new OaCustomerRelation(oaCustomerRmanager));
		oaCustomerReadShareRelationDao.delete(new OaCustomerReadShareRelation(oaCustomerRmanager));
		oaCustomerWriteShareRelationDao.delete(new OaCustomerWriteShareRelation(oaCustomerRmanager));
	}
	
}