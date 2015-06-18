/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

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
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.oa.entity.OaCustomerReadShareRelation;
import com.tryine.oa.modules.oa.service.OaCustomerReadShareRelationService;

/**
 * 只读共享客户与用户关系表Controller
 * @author pengyue
 * @version 2015-06-11
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaCustomerReadShareRelation")
public class OaCustomerReadShareRelationController extends BaseController {

	@Autowired
	private OaCustomerReadShareRelationService oaCustomerReadShareRelationService;
	
	@ModelAttribute
	public OaCustomerReadShareRelation get(@RequestParam(required=false) String id) {
		OaCustomerReadShareRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaCustomerReadShareRelationService.get(id);
		}
		if (entity == null){
			entity = new OaCustomerReadShareRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaCustomerReadShareRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaCustomerReadShareRelation oaCustomerReadShareRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaCustomerReadShareRelation> page = oaCustomerReadShareRelationService.findPage(new Page<OaCustomerReadShareRelation>(request, response), oaCustomerReadShareRelation); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerReadShareRelationList";
	}

	@RequiresPermissions("oa:oaCustomerReadShareRelation:view")
	@RequestMapping(value = "form")
	public String form(OaCustomerReadShareRelation oaCustomerReadShareRelation, Model model) {
		model.addAttribute("oaCustomerReadShareRelation", oaCustomerReadShareRelation);
		return "modules/oa/oaCustomerReadShareRelationForm";
	}

	@RequiresPermissions("oa:oaCustomerReadShareRelation:edit")
	@RequestMapping(value = "save")
	public String save(OaCustomerReadShareRelation oaCustomerReadShareRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaCustomerReadShareRelation)){
			return form(oaCustomerReadShareRelation, model);
		}
		oaCustomerReadShareRelationService.save(oaCustomerReadShareRelation);
		addMessage(redirectAttributes, "保存只读共享客户与用户关系表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerReadShareRelation/?repage";
	}
	
	@RequiresPermissions("oa:oaCustomerReadShareRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(OaCustomerReadShareRelation oaCustomerReadShareRelation, RedirectAttributes redirectAttributes) {
		oaCustomerReadShareRelationService.delete(oaCustomerReadShareRelation);
		addMessage(redirectAttributes, "删除只读共享客户与用户关系表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerReadShareRelation/?repage";
	}

}