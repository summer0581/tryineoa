/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.service.CrudService;
import com.tryine.oa.modules.oa.entity.OaTravelapply;
import com.tryine.oa.modules.oa.dao.OaTravelapplyDao;

/**
 * 出差申请Service
 * @author Summer
 * @version 2015-04-16
 */
@Service
@Transactional(readOnly = true)
public class OaTravelapplyService extends CrudService<OaTravelapplyDao, OaTravelapply> {

	public OaTravelapply get(String id) {
		return super.get(id);
	}
	
	public List<OaTravelapply> findList(OaTravelapply oaTravelapply) {
		return super.findList(oaTravelapply);
	}
	
	public Page<OaTravelapply> findPage(Page<OaTravelapply> page, OaTravelapply oaTravelapply) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		oaTravelapply.getSqlMap().put("dsf", dataScopeFilter(oaTravelapply.getCurrentUser(), "o", "u"));
		return super.findPage(page, oaTravelapply);
	}
	
	@Transactional(readOnly = false)
	public void save(OaTravelapply oaTravelapply) {
		super.save(oaTravelapply);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaTravelapply oaTravelapply) {
		super.delete(oaTravelapply);
	}
	
}