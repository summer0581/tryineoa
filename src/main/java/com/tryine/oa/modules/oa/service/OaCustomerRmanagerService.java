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
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.oa.entity.OaCustomerRmanager;
import com.tryine.oa.modules.oa.dao.OaCustomerRmanagerDao;
import com.tryine.oa.modules.oa.entity.OaCustomerRelation;
import com.tryine.oa.modules.oa.dao.OaCustomerRelationDao;

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
	
	public OaCustomerRmanager get(String id) {
		OaCustomerRmanager oaCustomerRmanager = super.get(id);
		oaCustomerRmanager.setOaCustomerRelationList(oaCustomerRelationDao.findList(new OaCustomerRelation(oaCustomerRmanager)));
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
		// 更新发送接受人记录
		oaCustomerRelationDao.deleteByOaCustomerManagerId(oaCustomerRmanager.getId());
		if (oaCustomerRmanager.getOaCustomerRelationList().size() > 0){
			oaCustomerRelationDao.insertAll(oaCustomerRmanager.getOaCustomerRelationList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OaCustomerRmanager oaCustomerRmanager) {
		super.delete(oaCustomerRmanager);
		oaCustomerRelationDao.delete(new OaCustomerRelation(oaCustomerRmanager));
	}
	
}