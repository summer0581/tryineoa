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
import com.tryine.oa.modules.oa.entity.OaTeamInfo;
import com.tryine.oa.modules.oa.service.OaTeamInfoService;

/**
 * 团队信息管理Controller
 * @author pengyue
 * @version 2015-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaTeamInfo")
public class OaTeamInfoController extends BaseController {

	@Autowired
	private OaTeamInfoService oaTeamInfoService;
	
	@ModelAttribute
	public OaTeamInfo get(@RequestParam(required=false) String id) {
		OaTeamInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTeamInfoService.get(id);
		}
		if (entity == null){
			entity = new OaTeamInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaTeamInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaTeamInfo oaTeamInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTeamInfo> page = oaTeamInfoService.findPage(new Page<OaTeamInfo>(request, response), oaTeamInfo); 
		model.addAttribute("page", page);
		return "modules/oa/oaTeamInfoList";
	}

	@RequiresPermissions("oa:oaTeamInfo:view")
	@RequestMapping(value = "form")
	public String form(OaTeamInfo oaTeamInfo, Model model) {
		model.addAttribute("oaTeamInfo", oaTeamInfo);	
		return "modules/oa/oaTeamInfoForm";
	}

	@RequiresPermissions("oa:oaTeamInfo:edit")
	@RequestMapping(value = "save")
	public String save(OaTeamInfo oaTeamInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTeamInfo)){
			return form(oaTeamInfo, model);
		}
		oaTeamInfoService.save(oaTeamInfo);
		addMessage(redirectAttributes, "保存团队信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTeamInfo/?repage";
	}
	
	@RequiresPermissions("oa:oaTeamInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(OaTeamInfo oaTeamInfo, RedirectAttributes redirectAttributes) {
		oaTeamInfoService.delete(oaTeamInfo);
		addMessage(redirectAttributes, "删除团队信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTeamInfo/?repage";
	}

}