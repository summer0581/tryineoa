/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaLeaveapply;
import com.tryine.oa.modules.oa.dao.OaLeaveapplyDao;

/**
 * 请假申请流程Service
 * @author Summer
 * @version 2015-04-13
 */
@Service
@Transactional(readOnly = true)
public class OaLeaveapplyService extends CrudService<OaLeaveapplyDao, OaLeaveapply> {

	public OaLeaveapply get(String id) {
		return super.get(id);
	}
	
	public List<OaLeaveapply> findList(OaLeaveapply oaLeaveapply) {
		return super.findList(oaLeaveapply);
	}
	
	public Page<OaLeaveapply> findPage(Page<OaLeaveapply> page, OaLeaveapply oaLeaveapply) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		oaLeaveapply.getSqlMap().put("dsf", dataScopeFilter(oaLeaveapply.getCurrentUser(), "o", "u"));
		return super.findPage(page, oaLeaveapply);
	}
	
	@Transactional(readOnly = false)
	public void save(OaLeaveapply oaLeaveapply) {
		super.save(oaLeaveapply);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaLeaveapply oaLeaveapply) {
		super.delete(oaLeaveapply);
	}
	
}