/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.service.TreeService;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.oa.entity.OaZhiduMl;
import com.tryine.oa.modules.oa.dao.OaZhiduMlDao;

/**
 * 规章制度目录维护Service
 * @author Summer
 * @version 2015-04-27
 */
@Service
@Transactional(readOnly = true)
public class OaZhiduMlService extends TreeService<OaZhiduMlDao, OaZhiduMl> {

	public OaZhiduMl get(String id) {
		return super.get(id);
	}
	
	public List<OaZhiduMl> findList(OaZhiduMl oaZhiduMl) {
		if (StringUtils.isNotBlank(oaZhiduMl.getParentIds())){
			oaZhiduMl.setParentIds(","+oaZhiduMl.getParentIds()+",");
		}
		return super.findList(oaZhiduMl);
	}
	
	@Transactional(readOnly = false)
	public void save(OaZhiduMl oaZhiduMl) {
		super.save(oaZhiduMl);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaZhiduMl oaZhiduMl) {
		super.delete(oaZhiduMl);
	}
	
}