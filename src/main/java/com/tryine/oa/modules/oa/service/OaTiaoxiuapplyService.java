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
import com.tryine.oa.modules.oa.entity.OaJiabanapply;
import com.tryine.oa.modules.oa.entity.OaTiaoxiuapply;
import com.tryine.oa.modules.oa.dao.OaJiabanapplyDao;
import com.tryine.oa.modules.oa.dao.OaTiaoxiuapplyDao;

/**
 * 调休申请Service
 * @author Summer
 * @version 2015-05-11
 */
@Service
@Transactional(readOnly = true)
public class OaTiaoxiuapplyService extends CrudService<OaTiaoxiuapplyDao, OaTiaoxiuapply> {
	@Autowired
	private OaTiaoxiuapplyDao oaTiaoxiuapplyDao;

	public OaTiaoxiuapply get(String id) {
		return super.get(id);
	}
	
	public List<OaTiaoxiuapply> findList(OaTiaoxiuapply oaTiaoxiuapply) {
		return super.findList(oaTiaoxiuapply);
	}
	
	public Page<OaTiaoxiuapply> findPage(Page<OaTiaoxiuapply> page, OaTiaoxiuapply oaTiaoxiuapply) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		oaTiaoxiuapply.getSqlMap().put("dsf", dataScopeFilter(oaTiaoxiuapply.getCurrentUser(), "o", "u"));
		return super.findPage(page, oaTiaoxiuapply);
	}
	public double countTiaoxiuHours(OaTiaoxiuapply oaTiaoxiuapply){
		return oaTiaoxiuapplyDao.countTiaoxiuHours(oaTiaoxiuapply);
	}
	
	@Transactional(readOnly = false)
	public void save(OaTiaoxiuapply oaTiaoxiuapply) {
		super.save(oaTiaoxiuapply);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaTiaoxiuapply oaTiaoxiuapply) {
		super.delete(oaTiaoxiuapply);
	}
	
}