/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tryine.oa.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tryine.oa.common.service.TreeService;
import com.tryine.oa.modules.sys.dao.OfficeDao;
import com.tryine.oa.modules.sys.entity.Office;
import com.tryine.oa.modules.sys.entity.User;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		//此部分代码修复框架原有bug
		User user = UserUtils.getUser();
		if(office==null||office.getParentIds()==null||"".equals(office.getParentIds())){//第一次进入做判断
			if("1".equals(user.getId())){//如果是超级管理员不做处理
				
			}else{//加入本公司权限判断
				office = new Office();
				office.setId(user.getCompany().getId());
				office.setParentIds(user.getCompany().getParentIds()+user.getCompany().getId()+"%");
			}
		}else{
		office.setParentIds(office.getParentIds()+office.getId()+"%");
		}
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
}
