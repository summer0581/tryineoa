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
import com.tryine.oa.modules.oa.entity.OaFeedbackBox;
import com.tryine.oa.modules.oa.service.OaFeedbackBoxService;

/**
 * 类型维护Controller
 * @author Summer
 * @version 2015-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaFeedbackBox")
public class OaFeedbackBoxController extends BaseController {

	@Autowired
	private OaFeedbackBoxService oaFeedbackBoxService;
	
	@ModelAttribute
	public OaFeedbackBox get(@RequestParam(required=false) String id) {
		OaFeedbackBox entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFeedbackBoxService.get(id);
		}
		if (entity == null){
			entity = new OaFeedbackBox();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaFeedbackBox:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaFeedbackBox oaFeedbackBox, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaFeedbackBox> page = oaFeedbackBoxService.findPage(new Page<OaFeedbackBox>(request, response), oaFeedbackBox); 
		model.addAttribute("page", page);
		return "modules/oa/oaFeedbackBoxList";
	}

	@RequiresPermissions("oa:oaFeedbackBox:view")
	@RequestMapping(value = "form")
	public String form(OaFeedbackBox oaFeedbackBox, Model model) {
		model.addAttribute("oaFeedbackBox", oaFeedbackBox);
		return "modules/oa/oaFeedbackBoxForm";
	}

	@RequiresPermissions("oa:oaFeedbackBox:edit")
	@RequestMapping(value = "save")
	public String save(OaFeedbackBox oaFeedbackBox, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFeedbackBox)){
			return form(oaFeedbackBox, model);
		}
		oaFeedbackBoxService.save(oaFeedbackBox);
		addMessage(redirectAttributes, "保存类型成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedbackBox/?repage";
	}
	
	@RequiresPermissions("oa:oaFeedbackBox:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFeedbackBox oaFeedbackBox, RedirectAttributes redirectAttributes) {
		oaFeedbackBoxService.delete(oaFeedbackBox);
		addMessage(redirectAttributes, "删除类型成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedbackBox/?repage";
	}

}