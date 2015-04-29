/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.DateUtils;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;
import com.tryine.oa.modules.oa.service.OaMessageRecordService;

/**
 * 内部消息记录Controller
 * @author Summer
 * @version 2015-04-28
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaMessageRecord")
public class OaMessageRecordController extends BaseController {

	@Autowired
	private OaMessageRecordService oaMessageRecordService;
	
	@ModelAttribute
	public OaMessageRecord get(@RequestParam(required=false) String id,@RequestParam(required=false) String type) {
		OaMessageRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaMessageRecordService.get(id);
		}
		if (entity == null){
			entity = new OaMessageRecord();
		}
		entity.setType(type);
		return entity;
	}
	
	@RequiresPermissions("oa:oaMessageRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaMessageRecord oaMessageRecord, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaMessageRecord> page = oaMessageRecordService.findPage(new Page<OaMessageRecord>(request, response), oaMessageRecord); 
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "modules/oa/oaMessageRecordList";
	}

	@RequiresPermissions("oa:oaMessageRecord:view")
	@RequestMapping(value = "form")
	public String form(OaMessageRecord oaMessageRecord, Model model) {
		if(OaMessageRecord.TYPE.noread.toString().equals(oaMessageRecord.getType())){
			oaMessageRecord.setReadFlag("1");
			oaMessageRecord.setReadDate(new Date());
			oaMessageRecordService.save(oaMessageRecord);
		}
		if(StringUtils.isBlank(oaMessageRecord.getOaMessage().getUrl())){
			oaMessageRecord.getOaMessage().setUrl("oa/oaMessageRecord/list?type=noread");
		}
		model.addAttribute("oaMessageRecord", oaMessageRecord);
		return "modules/oa/oaMessageRecordForm";
	}
	
	@RequiresPermissions("oa:oaMessageRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(OaMessageRecord oaMessageRecord, @RequestParam Map<String,Object> params, RedirectAttributes redirectAttributes) {
		oaMessageRecordService.delete(oaMessageRecord);
		addMessage(redirectAttributes, "删除内部消息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaMessageRecord/?type="+params.get("type")+"&repage";
	}

}