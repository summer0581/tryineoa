/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.util.ArrayList;
import java.util.Date;
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
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaFeedbackBox;
import com.tryine.oa.modules.oa.entity.OaFeedbackReceived;
import com.tryine.oa.modules.oa.entity.OaMessage;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;
import com.tryine.oa.modules.oa.service.OaFeedbackBoxService;
import com.tryine.oa.modules.oa.service.OaFeedbackReceivedService;
import com.tryine.oa.modules.oa.service.OaMessageService;

/**
 * 意见反馈Controller
 * @author Summer
 * @version 2015-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaFeedbackReceived")
public class OaFeedbackReceivedController extends BaseController {

	@Autowired
	private OaFeedbackReceivedService oaFeedbackReceivedService;
	@Autowired
	private OaFeedbackBoxService oaFeedbackBoxService;
	@Autowired
	private OaMessageService oaMessageService;
	
	@ModelAttribute
	public OaFeedbackReceived get(@RequestParam(required=false) String id) {
		OaFeedbackReceived entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFeedbackReceivedService.get(id);
		}
		if (entity == null){
			entity = new OaFeedbackReceived();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaFeedbackReceived:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaFeedbackReceived oaFeedbackReceived, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaFeedbackReceived.setIsRead("0");
		oaFeedbackReceived.setIsSelf(true);
		Page<OaFeedbackReceived> page = oaFeedbackReceivedService.findPage(new Page<OaFeedbackReceived>(request, response), oaFeedbackReceived); 
		model.addAttribute("page", page);
		return "modules/oa/oaFeedbackReceivedList";
	}
	
	@RequiresPermissions("oa:oaFeedbackReceived:view")
	@RequestMapping(value = {"readedList"})
	public String readedList(OaFeedbackReceived oaFeedbackReceived, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaFeedbackReceived.setIsRead("1");
		oaFeedbackReceived.setIsSelf(true);
		Page<OaFeedbackReceived> page = oaFeedbackReceivedService.findPage(new Page<OaFeedbackReceived>(request, response), oaFeedbackReceived); 
		model.addAttribute("page", page);
		return "modules/oa/oaFeedbackReceivedReadedList";
	}

	@RequiresPermissions("oa:oaFeedbackReceived:view")
	@RequestMapping(value = "form")
	public String form(OaFeedbackReceived oaFeedbackReceived, Model model) {
		if(!"1".equals(oaFeedbackReceived.getIsRead())){
			oaFeedbackReceived.setIsRead("1");
			oaFeedbackReceivedService.save(oaFeedbackReceived);
		}
		List<OaFeedbackBox> boxlist = oaFeedbackBoxService.findList(new OaFeedbackBox());
		model.addAttribute("boxlist", boxlist);
		model.addAttribute("oaFeedbackReceived", oaFeedbackReceived);
		return "modules/oa/oaFeedbackReceivedForm";
	}

	@RequiresPermissions("oa:oaFeedbackReceived:edit")
	@RequestMapping(value = "save")
	public String save(OaFeedbackReceived oaFeedbackReceived, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFeedbackReceived)){
			return form(oaFeedbackReceived, model);
		}
		oaFeedbackReceived.setBackTime(new Date());
		oaFeedbackReceivedService.save(oaFeedbackReceived);
		
		/*消息发送start*/
		if(oaFeedbackReceived.getIsSendMessage()){
			OaMessage oaMessage =  new OaMessage();
			oaMessage.setContent("您有新的意见反馈回复【 "+oaFeedbackReceived.getFeedback().getName()+"】需要查看！");
			oaMessage.setUrl("/oa/oaFeedback");
			List<OaMessageRecord> oaMessageRecordList = new ArrayList<OaMessageRecord>();
			OaMessageRecord entity = new OaMessageRecord();
			entity.setId(IdGen.uuid());
			entity.setOaMessage(oaMessage);
			entity.setUser(oaFeedbackReceived.getFeedback().getCreateBy());
			entity.setReadFlag("0");
			if("1".equals(oaFeedbackReceived.getFeedback().getIsHidename())){
				entity.setTempUsername("匿名");
			}
			oaMessageRecordList.add(entity);
			oaMessage.setOaMessageRecordList(oaMessageRecordList);
			oaMessageService.save(oaMessage);
		}
		/*消息发送end*/
		
		addMessage(redirectAttributes, "保存意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedbackReceived/?repage";
	}
	
	@RequiresPermissions("oa:oaFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFeedbackReceived oaFeedbackReceived, RedirectAttributes redirectAttributes) {
		oaFeedbackReceivedService.delete(oaFeedbackReceived);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedbackReceived/?repage";
	}

}