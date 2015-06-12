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
import com.tryine.oa.modules.oa.entity.OaCustomerWriteShareRelation;
import com.tryine.oa.modules.oa.service.OaCustomerWriteShareRelationService;

/**
 * 可写共享客户与用户关系表Controller
 * @author pengyue
 * @version 2015-06-11
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaCustomerWriteShareRelation")
public class OaCustomerWriteShareRelationController extends BaseController {

	@Autowired
	private OaCustomerWriteShareRelationService oaCustomerWriteShareRelationService;
	
	@ModelAttribute
	public OaCustomerWriteShareRelation get(@RequestParam(required=false) String id) {
		OaCustomerWriteShareRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaCustomerWriteShareRelationService.get(id);
		}
		if (entity == null){
			entity = new OaCustomerWriteShareRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaCustomerWriteShareRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaCustomerWriteShareRelation oaCustomerWriteShareRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaCustomerWriteShareRelation> page = oaCustomerWriteShareRelationService.findPage(new Page<OaCustomerWriteShareRelation>(request, response), oaCustomerWriteShareRelation); 
		model.addAttribute("page", page);
		return "modules/oa/oaCustomerWriteShareRelationList";
	}

	@RequiresPermissions("oa:oaCustomerWriteShareRelation:view")
	@RequestMapping(value = "form")
	public String form(OaCustomerWriteShareRelation oaCustomerWriteShareRelation, Model model) {
		model.addAttribute("oaCustomerWriteShareRelation", oaCustomerWriteShareRelation);
		return "modules/oa/oaCustomerWriteShareRelationForm";
	}

	@RequiresPermissions("oa:oaCustomerWriteShareRelation:edit")
	@RequestMapping(value = "save")
	public String save(OaCustomerWriteShareRelation oaCustomerWriteShareRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaCustomerWriteShareRelation)){
			return form(oaCustomerWriteShareRelation, model);
		}
		oaCustomerWriteShareRelationService.save(oaCustomerWriteShareRelation);
		addMessage(redirectAttributes, "保存可写共享客户与用户关系表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerWriteShareRelation/?repage";
	}
	
	@RequiresPermissions("oa:oaCustomerWriteShareRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(OaCustomerWriteShareRelation oaCustomerWriteShareRelation, RedirectAttributes redirectAttributes) {
		oaCustomerWriteShareRelationService.delete(oaCustomerWriteShareRelation);
		addMessage(redirectAttributes, "删除可写共享客户与用户关系表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaCustomerWriteShareRelation/?repage";
	}

}