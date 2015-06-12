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
import com.tryine.oa.modules.oa.entity.OaTeamInfoRelation;
import com.tryine.oa.modules.oa.service.OaTeamInfoRelationService;

/**
 * 团队信息所属关系管理Controller
 * @author pengyue
 * @version 2015-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaTeamInfoRelation")
public class OaTeamInfoRelationController extends BaseController {

	@Autowired
	private OaTeamInfoRelationService oaTeamInfoRelationService;
	
	@ModelAttribute
	public OaTeamInfoRelation get(@RequestParam(required=false) String id) {
		OaTeamInfoRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTeamInfoRelationService.get(id);
		}
		if (entity == null){
			entity = new OaTeamInfoRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaTeamInfoRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaTeamInfoRelation oaTeamInfoRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTeamInfoRelation> page = oaTeamInfoRelationService.findPage(new Page<OaTeamInfoRelation>(request, response), oaTeamInfoRelation); 
		model.addAttribute("page", page);
		return "modules/oa/oaTeamInfoRelationList";
	}

	@RequiresPermissions("oa:oaTeamInfoRelation:view")
	@RequestMapping(value = "form")
	public String form(OaTeamInfoRelation oaTeamInfoRelation, Model model) {
		model.addAttribute("oaTeamInfoRelation", oaTeamInfoRelation);
		return "modules/oa/oaTeamInfoRelationForm";
	}

	@RequiresPermissions("oa:oaTeamInfoRelation:edit")
	@RequestMapping(value = "save")
	public String save(OaTeamInfoRelation oaTeamInfoRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTeamInfoRelation)){
			return form(oaTeamInfoRelation, model);
		}
		oaTeamInfoRelationService.save(oaTeamInfoRelation);
		addMessage(redirectAttributes, "保存团队信息所属关系成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTeamInfoRelation/?repage";
	}
	
	@RequiresPermissions("oa:oaTeamInfoRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(OaTeamInfoRelation oaTeamInfoRelation, RedirectAttributes redirectAttributes) {
		oaTeamInfoRelationService.delete(oaTeamInfoRelation);
		addMessage(redirectAttributes, "删除团队信息所属关系成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTeamInfoRelation/?repage";
	}

}