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

import com.google.common.collect.Lists;
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.oa.entity.OaFeedback;
import com.tryine.oa.modules.oa.entity.OaFeedbackBox;
import com.tryine.oa.modules.oa.entity.OaFeedbackReceived;
import com.tryine.oa.modules.oa.entity.OaMessage;
import com.tryine.oa.modules.oa.entity.OaMessageRecord;
import com.tryine.oa.modules.oa.service.OaFeedbackBoxService;
import com.tryine.oa.modules.oa.service.OaFeedbackService;
import com.tryine.oa.modules.oa.service.OaMessageService;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 意见反馈Controller
 * @author Summer
 * @version 2015-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaFeedback")
public class OaFeedbackController extends BaseController {

	@Autowired
	private OaFeedbackService oaFeedbackService;
	@Autowired
	private OaFeedbackBoxService oaFeedbackBoxService;
	@Autowired
	private OaMessageService oaMessageService;
	
	@ModelAttribute
	public OaFeedback get(@RequestParam(required=false) String id) {
		OaFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFeedbackService.get(id);
		}
		if (entity == null){
			entity = new OaFeedback();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaFeedback:view")
	@RequestMapping(value = {"list",""})
	public String list(OaFeedback oaFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaFeedback> page = oaFeedbackService.findPage(new Page<OaFeedback>(request, response), oaFeedback); 
		model.addAttribute("page", page);
		return "modules/oa/oaFeedbackList";
	}

	@RequiresPermissions("oa:oaFeedback:view")
	@RequestMapping(value = "form")
	public String form(OaFeedback oaFeedback, Model model) {
		List<OaFeedbackBox> boxlist = oaFeedbackBoxService.findList(new OaFeedbackBox());
		model.addAttribute("oaFeedback", oaFeedback);
		model.addAttribute("boxlist", boxlist);
		return "modules/oa/oaFeedbackForm";
	}
	
	@RequiresPermissions("oa:oaFeedback:view")
	@RequestMapping(value = "viewform")
	public String viewform(OaFeedback oaFeedback, Model model) {
		OaFeedbackBox box = oaFeedbackBoxService.get(oaFeedback.getBox());
		List<OaFeedbackReceived> oaFeedbackReceivedList = oaFeedback.getOaFeedbackReceivedList();
		StringBuffer isReadUsernames = new StringBuffer();
		for(int i = 0 ; i < oaFeedbackReceivedList.size(); i++){
			OaFeedbackReceived tempOaFeedbackReceived = oaFeedbackReceivedList.get(i);
			if("1".equals(tempOaFeedbackReceived.getIsRead())){
				isReadUsernames.append(tempOaFeedbackReceived.getUser().getName()).append(",");
			}
		}
		model.addAttribute("oaFeedback", oaFeedback);
		model.addAttribute("box", box);
		if(isReadUsernames.length() > 0){
			model.addAttribute("isReadUsernames", isReadUsernames.substring(0, isReadUsernames.length()-1));
		}
		return "modules/oa/oaFeedbackViewForm";
	}

	@RequiresPermissions("oa:oaFeedback:edit")
	@RequestMapping(value = "save")
	public String save(OaFeedback oaFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFeedback)){
			return form(oaFeedback, model);
		}
		//List<OaFeedbackReceived> oaFeedbackReceiveds = oaFeedback.getOaFeedbackReceivedList();
		/*OaFeedbackBox oaFeedbackBox = oaFeedbackBoxService.get(oaFeedback.getBox());
		String userids = oaFeedbackBox.getUserids();
		if(StringUtils.isNotBlank(userids)){
			String[] useridarry = userids.split(",");
			List<OaFeedbackReceived> receivedList = Lists.newArrayList();
			for(int i = 0 ; i < useridarry.length; i ++){
				OaFeedbackReceived ofr = new OaFeedbackReceived();
				ofr.setId(IdGen.uuid());
				ofr.setIsRead("0");
				ofr.setUser(new User(useridarry[i]));
				ofr.setFeedback(oaFeedback);
				receivedList.add(ofr);
			}*/
		if(null!=oaFeedback){
			List<OaFeedbackReceived> oaFeedbackReceiveds = oaFeedback.getOaFeedbackReceivedList();
			List<OaFeedbackReceived> os = new ArrayList<OaFeedbackReceived>();
			for(int i=0;i<oaFeedbackReceiveds.size();i++){
				OaFeedbackReceived ofr = new OaFeedbackReceived();
				ofr.setId(IdGen.uuid());
				ofr.setIsRead("0");
				ofr.setUser(oaFeedbackReceiveds.get(i).getUser());
				ofr.setFeedback(oaFeedbackReceiveds.get(i).getFeedback());
				os.add(ofr);
			}
			/*
			
			for(OaFeedbackReceived ofr : oaFeedbackReceiveds){
				ofr.setId(IdGen.uuid());
				ofr.setIsRead("0");
				oaFeedbackReceiveds.add(ofr);
			}*/
			oaFeedback.setOaFeedbackReceivedList(os);
		}
		//}
		
		/*消息发送start*/
			OaMessage oaMessage =  new OaMessage();
			oaMessage.setContent("您有新的信件【 "+oaFeedback.getName()+"】需要查看！");
			oaMessage.setUrl("/oa/oaFeedbackReceived");
			if("1".equals(oaFeedback.getIsHidename())){
				oaMessage.setTempUsername("匿名");
			}
			List<OaMessageRecord> oaMessageRecordList = new ArrayList<OaMessageRecord>();
			List<OaFeedbackReceived> tmpReceivedList = oaFeedback.getOaFeedbackReceivedList();
			for(OaFeedbackReceived ofr : tmpReceivedList){
				OaMessageRecord entity = new OaMessageRecord();
				entity.setId(IdGen.uuid());
				entity.setOaMessage(oaMessage);
				entity.setUser(ofr.getUser());
				entity.setReadFlag("0");
				oaMessageRecordList.add(entity);
			}
			oaMessage.setOaMessageRecordList(oaMessageRecordList);
			oaMessageService.save(oaMessage);
		/*消息发送end*/

		
		oaFeedbackService.save(oaFeedback);
		addMessage(redirectAttributes, "保存信件成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedback/?repage";
	}
	
	@RequiresPermissions("oa:oaFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFeedback oaFeedback, RedirectAttributes redirectAttributes) {
		oaFeedbackService.delete(oaFeedback);
		addMessage(redirectAttributes, "删除信件成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFeedback/?repage";
	}

}