/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaMessage;
import com.tryine.oa.modules.oa.service.OaMessageService;

/**
 * 内部消息Controller
 * @author Summer
 * @version 2015-04-28
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaMessage")
public class OaMessageController extends BaseController {

	@Autowired
	private OaMessageService oaMessageService;
	
	@ModelAttribute
	public OaMessage get(@RequestParam(required=false) String id) {
		OaMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaMessageService.get(id);
		}
		if (entity == null){
			entity = new OaMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaMessage oaMessage, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaMessage> page = oaMessageService.findPage(new Page<OaMessage>(request, response), oaMessage); 
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "modules/oa/oaMessageList";
	}

	@RequiresPermissions("oa:oaMessage:view")
	@RequestMapping(value = "form")
	public String form(OaMessage oaMessage, Model model) {
		model.addAttribute("oaMessage", oaMessage);
		return "modules/oa/oaMessageForm";
	}
	
	@RequiresPermissions("oa:oaMessage:view")
	@RequestMapping(value = "noListForm")
	public String noListform(OaMessage oaMessage, Model model) {
		model.addAttribute("oaMessage", oaMessage);
		return "modules/oa/oaMessageNoListForm";
	}

	@RequiresPermissions("oa:oaMessage:edit")
	@RequestMapping(value = "save")
	public String save(OaMessage oaMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaMessage)){
			return form(oaMessage, model);
		}
		oaMessageService.save(oaMessage);
		addMessage(redirectAttributes, "保存内部消息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaMessageRecord/?type=sended&repage";
	}
	
	@RequiresPermissions("oa:oaMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(OaMessage oaMessage, RedirectAttributes redirectAttributes) {
		oaMessageService.delete(oaMessage);
		addMessage(redirectAttributes, "删除内部消息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaMessage/?repage";
	}
	
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaMessage oaMessage, Model model) {
		oaMessage.setIsSelf(true);
		oaMessage.setReadFlag("0");
		return String.valueOf(oaMessageService.findCount(oaMessage));
	}

}