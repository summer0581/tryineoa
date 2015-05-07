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

import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaCustomerRelation;
import com.tryine.oa.modules.oa.entity.OaCustomerRmanager;
import com.tryine.oa.modules.oa.service.OaCustomerRmanagerService;
import com.tryine.oa.modules.sys.utils.UserUtils;

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
		if(StringUtils.isBlank(oaCustomerRmanager.getId())){
			List<OaCustomerRelation> oaCustomerRelationList = new ArrayList<OaCustomerRelation>();
			OaCustomerRelation oaCustomerRelation  = new OaCustomerRelation();
			oaCustomerRelation.preInsert();
			oaCustomerRelation.setUser(UserUtils.getUser());
			oaCustomerRelation.setOaCustomer(oaCustomerRmanager);
			oaCustomerRelationList.add(oaCustomerRelation);
			oaCustomerRmanager.setOaCustomerRelationList(oaCustomerRelationList);
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
	
	@RequiresPermissions("oa:oaCustomerRmanager:edit")
	@RequestMapping(value = "delete")
	public String delete(OaCustomerRmanager oaCustomerRmanager, RedirectAttributes redirectAttributes) {
		oaCustomerRmanagerService.delete(oaCustomerRmanager);
		addMessage(redirectAttributes, "删除客户信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerRmanager/?repage";
	}

}