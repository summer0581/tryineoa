/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaCustomerReadShareRelation;
import com.tryine.oa.modules.oa.entity.OaCustomerRelation;
import com.tryine.oa.modules.oa.entity.OaCustomerRmanager;
import com.tryine.oa.modules.oa.entity.OaCustomerWriteShareRelation;
import com.tryine.oa.modules.oa.service.OaCustomerRmanagerService;

/**
 * 客户信息管理Controller
 * @author Summer
 * @version 2015-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaCustomerRmanager")
public class OaCustomerRmanagerController extends BaseController {

	@Autowired
	private OaCustomerRmanagerService oaCustomerRmanagerService;
	
	
	@ModelAttribute
	public OaCustomerRmanager get(@RequestParam(required=false) String id) {
		OaCustomerRmanager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaCustomerRmanagerService.get(id);
		}
		if (entity == null){
			entity = new OaCustomerRmanager();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaCustomerRmanager:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaCustomerRmanager oaCustomerRmanager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaCustomerRmanager> page = oaCustomerRmanagerService.findPage(new Page<OaCustomerRmanager>(request, response), oaCustomerRmanager); 
		model.addAttribute("page", page);
		
		return "modules/oa/oaCustomerRmanagerList";
	}
	/**
	 * 共享客户列表
	 * @param oaCustomerRmanager
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "shareList")
	public String shareList(OaCustomerRmanager oaCustomerRmanager, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaCustomerRmanager.setGongxiang("share");
		Page<OaCustomerRmanager> page = oaCustomerRmanagerService.findPage(new Page<OaCustomerRmanager>(request, response), oaCustomerRmanager); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerManagerShareList";
	}
	/**
	 * 公海客户列表
	 * @param oaCustomerRmanager
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "openSeaList")
	public String openSeaList(OaCustomerRmanager oaCustomerRmanager, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaCustomerRmanager.setIsOpenSea("1");
		Page<OaCustomerRmanager> page = oaCustomerRmanagerService.findPage(new Page<OaCustomerRmanager>(request, response), oaCustomerRmanager); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerManagerOpenSeaList";
	}
	
	@RequiresPermissions("oa:oaCustomerRmanager:view")
	@RequestMapping(value = {"qiyelist"})
	public String qiyelist(OaCustomerRmanager oaCustomerRmanager, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaCustomerRmanager.setType("qiye");
		Page<OaCustomerRmanager> page = oaCustomerRmanagerService.findPage(new Page<OaCustomerRmanager>(request, response), oaCustomerRmanager); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerRmanagerQiyeList";
	}
	
	@RequiresPermissions("oa:oaCustomerRmanager:view")
	@RequestMapping(value = {"gerenlist"})
	public String gerenlist(OaCustomerRmanager oaCustomerRmanager, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaCustomerRmanager.setType("geren");
		Page<OaCustomerRmanager> page = oaCustomerRmanagerService.findPage(new Page<OaCustomerRmanager>(request, response), oaCustomerRmanager); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerRmanagerGerenList";
	}

	@RequiresPermissions("oa:oaCustomerRmanager:view")
	@RequestMapping(value = "form")
	public String form(OaCustomerRmanager oaCustomerRmanager, Model model) {
		model.addAttribute("oaCustomerRmanager", oaCustomerRmanager);
		return "modules/oa/oaCustomerRmanagerForm";
	}
	
	@RequiresPermissions("oa:oaCustomerRmanager:view")
	@RequestMapping(value = "qiyeform")
	public String qiyeform(OaCustomerRmanager oaCustomerRmanager, Model model) {
		model.addAttribute("oaCustomerRmanager", oaCustomerRmanager);
		return "modules/oa/oaCustomerRmanagerQiyeForm";
	}

	@RequiresPermissions("oa:oaCustomerRmanager:edit")
	@RequestMapping(value = "save")
	public String save(OaCustomerRmanager oaCustomerRmanager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaCustomerRmanager)){
			return form(oaCustomerRmanager, model);
		}
		if(StringUtils.isBlank(oaCustomerRmanager.getId())){//新增需要添加三张子表对象再保存
			List<OaCustomerRelation> oaCustomerRelationList = new ArrayList<OaCustomerRelation>();
			OaCustomerRelation oaCustomerRelation  = new OaCustomerRelation();
			oaCustomerRelation.preInsert();
			oaCustomerRelation.setOaCustomer(oaCustomerRmanager);
			oaCustomerRelationList.add(oaCustomerRelation);
			oaCustomerRmanager.setOaCustomerRelationList(oaCustomerRelationList);
			
			List<OaCustomerReadShareRelation> oaCustomerReadShareRelationList = Lists.newArrayList();
			OaCustomerReadShareRelation oaCustomerReadShareRelation  = new OaCustomerReadShareRelation();
			oaCustomerReadShareRelation.preInsert();
			oaCustomerReadShareRelation.setOaCustomerRead(oaCustomerRmanager);
			oaCustomerReadShareRelationList.add(oaCustomerReadShareRelation);
			oaCustomerRmanager.setOaCustomerReadShareRelationList(oaCustomerReadShareRelationList);
			
			List<OaCustomerWriteShareRelation> oaCustomerWriteShareRelationList = Lists.newArrayList();
			OaCustomerWriteShareRelation oaCustomerWriteShareRelation  = new OaCustomerWriteShareRelation();
			oaCustomerWriteShareRelation.preInsert();
			oaCustomerWriteShareRelation.setOaCustomerWrite(oaCustomerRmanager);
			oaCustomerWriteShareRelationList.add(oaCustomerWriteShareRelation);
			oaCustomerRmanager.setOaCustomerWriteShareRelationList(oaCustomerWriteShareRelationList);
		}
		oaCustomerRmanagerService.save(oaCustomerRmanager);
		addMessage(redirectAttributes, "保存客户信息管理成功");
		String type = oaCustomerRmanager.getType();
		if("qiye".equals(type)){
			return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerRmanager/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerRmanager/?repage";
		}
		
	}
	/**
	 * 提取公海客户
	 * @param oaCustomerRmanager
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:oaCustomerRmanager:edit")
	@RequestMapping(value = "extractOpenSea")
	public String extractOpenSea(OaCustomerRmanager oaCustomerRmanager, Model model, RedirectAttributes redirectAttributes) {
		oaCustomerRmanager.setIsOpenSea("0");
		if (!beanValidator(model, oaCustomerRmanager)){
			return form(oaCustomerRmanager, model);
		}
		if(StringUtils.isBlank(oaCustomerRmanager.getId())){
			List<OaCustomerRelation> oaCustomerRelationList = new ArrayList<OaCustomerRelation>();
			OaCustomerRelation oaCustomerRelation  = new OaCustomerRelation();
			oaCustomerRelation.preInsert();
			oaCustomerRelation.setOaCustomer(oaCustomerRmanager);
			oaCustomerRelationList.add(oaCustomerRelation);
			oaCustomerRmanager.setOaCustomerRelationList(oaCustomerRelationList);
		}
		oaCustomerRmanagerService.save(oaCustomerRmanager);
		addMessage(redirectAttributes, "收取客户信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerRmanager/openSeaList";
	}
	
	
	@RequiresPermissions("oa:oaCustomerRmanager:edit")
	@RequestMapping(value = "delete")
	public String delete(OaCustomerRmanager oaCustomerRmanager, RedirectAttributes redirectAttributes) {
		oaCustomerRmanagerService.delete(oaCustomerRmanager);
		addMessage(redirectAttributes, "删除客户信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerRmanager/?repage";
	}

}