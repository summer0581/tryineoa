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
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaZhiduContent;
import com.tryine.oa.modules.oa.entity.OaZhiduMl;
import com.tryine.oa.modules.oa.service.OaZhiduContentService;
import com.tryine.oa.modules.oa.service.OaZhiduMlService;

/**
 * 规章制度内容维护Controller
 * @author Summer
 * @version 2015-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaZhiduContent")
public class OaZhiduContentController extends BaseController {

	@Autowired
	private OaZhiduContentService oaZhiduContentService;
	@Autowired
	private OaZhiduMlService oaZhiduMlService;
	
	@ModelAttribute
	public OaZhiduContent get(@RequestParam(required=false) String id) {
		OaZhiduContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaZhiduContentService.get(id);
		}
		if (entity == null){
			entity = new OaZhiduContent();
		}
		return entity;
	}
	
	@RequestMapping(value = {"oaZhiduContentIndex"})
	public String index(OaZhiduContent oaZhiduContent, Model model) {
		return "modules/oa/oaZhiduContentIndex";
	}
	
	@RequiresPermissions("oa:oaZhiduContent:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaZhiduContent oaZhiduContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaZhiduContent> page = oaZhiduContentService.findPage(new Page<OaZhiduContent>(request, response), oaZhiduContent); 
		model.addAttribute("page", page);
		model.addAttribute("oaZhiduContent", oaZhiduContent);
		return "modules/oa/oaZhiduContentList";
	}

	@RequiresPermissions("oa:oaZhiduContent:view")
	@RequestMapping(value = "form")
	public String form(OaZhiduContent oaZhiduContent, Model model) {
		model.addAttribute("oaZhiduContent", oaZhiduContent);
		model.addAttribute("oaZhiduMlList", oaZhiduMlService.findList(new OaZhiduMl()));
		return "modules/oa/oaZhiduContentForm";
	}

	@RequiresPermissions("oa:oaZhiduContent:edit")
	@RequestMapping(value = "save")
	public String save(OaZhiduContent oaZhiduContent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaZhiduContent)){
			return form(oaZhiduContent, model);
		}
		oaZhiduContentService.save(oaZhiduContent);
		addMessage(redirectAttributes, "保存规章制度内容维护成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaZhiduContent/?oaZhiduMl.id="+oaZhiduContent.getOaZhiduMl().getId()+"&repage";
	}
	
	@RequiresPermissions("oa:oaZhiduContent:edit")
	@RequestMapping(value = "delete")
	public String delete(OaZhiduContent oaZhiduContent, RedirectAttributes redirectAttributes) {
		oaZhiduContentService.delete(oaZhiduContent);
		addMessage(redirectAttributes, "删除规章制度内容维护成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaZhiduContent/?repage";
	}

}