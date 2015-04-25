/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.sql.SQLException;
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

import com.founder.fix.fixflow.core.impl.runtime.ProcessInstanceEntity;
import com.founder.fix.fixflow.core.runtime.ProcessInstance;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.service.impl.FlowCenterServiceImpl;
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.flow.service.FlowService;
import com.tryine.oa.modules.oa.entity.OaLeaveapply;
import com.tryine.oa.modules.oa.service.OaLeaveapplyService;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 请假申请流程Controller
 * @author Summer
 * @version 2015-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaLeaveapply")
public class OaLeaveapplyController extends BaseController {

	@Autowired
	private OaLeaveapplyService oaLeaveapplyService;
	@Autowired
	private FlowCenterServiceImpl flowCenterService;
	@Autowired
	private FlowService flowService;
	
	@ModelAttribute
	public OaLeaveapply get(@RequestParam(required=false) String id) {
		OaLeaveapply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaLeaveapplyService.get(id);
		}
		if (entity == null){
			entity = new OaLeaveapply();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaLeaveapply:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaLeaveapply oaLeaveapply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaLeaveapply> page = oaLeaveapplyService.findPage(new Page<OaLeaveapply>(request, response), oaLeaveapply); 
		model.addAttribute("page", page);
		return "modules/oa/oaLeaveapplyList";
	}

	@RequiresPermissions("oa:oaLeaveapply:view")
	@RequestMapping(value = "form")
	public String form(OaLeaveapply oaLeaveapply,@RequestParam Map<String,Object> params, Model model) {
		String processInstanceId = (String)params.get("processInstanceId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		String taskId = (String)params.get("taskId");
		if(StringUtils.isNotBlank(processInstanceId)){
			params.put("action", "demoDoNext");
		}else{
			params.put("action", "demoCompleteTask");
		}
		if(StringUtils.isBlank(oaLeaveapply.getName())){
			oaLeaveapply.setName(UserUtils.getUser().getName());
		}
		if(null == oaLeaveapply.getOffice()){
			oaLeaveapply.setOffice(UserUtils.getUser().getOffice());
		}
		ProcessInstance processIns = null;
		TaskInstance taskIns = null;
		try {
			params.put("userId", UserUtils.getUser().getId());
			if(StringUtils.isNotBlank(processInstanceId)){
				processIns = flowService.getProcessInstanceByInstanceId(params);
				model.addAttribute(processIns);
			}
			if(StringUtils.isNotBlank(taskId)){
				taskIns = flowService.getTaskInstanceByTaskId(params);
				params.put("nodeId", taskIns.getNodeId());//当前环节编号
			}
			if(StringUtils.isNoneBlank(processDefinitionKey)){
				Map<String, Object> list = flowCenterService.GetFlowRefInfo(
						params);
				params.putAll(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("result", params);
		model.addAttribute("oaLeaveapply", oaLeaveapply);
		return "modules/oa/flow/oaLeaveapplyForm";
	}

	@RequiresPermissions("oa:oaLeaveapply:edit")
	@RequestMapping(value = "save")
	public String save(OaLeaveapply oaLeaveapply,@RequestParam Map<String,Object> params, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaLeaveapply)){
			return form(oaLeaveapply,params, model);
		}
		oaLeaveapplyService.save(oaLeaveapply);
		String action = (String)params.get("action");
		String nodeId = (String)params.get("nodeId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		
		String _taskComment = "";
		if("tryine_leaveapply".equals(processDefinitionKey)){
			if("UserTask_2".equals(nodeId)){//部门领导审核
				_taskComment = oaLeaveapply.getDirectLeaderIdea();
			}else if("UserTask_3".equals(nodeId)){//分公司总经理
				_taskComment = oaLeaveapply.getGeneralManagerIdea();
			}else if("UserTask_4".equals(nodeId)){//人资总监
				_taskComment = oaLeaveapply.getHumanResourceIdea();
			}else if("UserTask_5".equals(nodeId)){//董事长
				_taskComment = oaLeaveapply.getChairManIdea();
			}
		}else if("tryine_bossdirectmanager_leave".equals(processDefinitionKey)){
			if("UserTask_1".equals(nodeId)){//人资总监
				_taskComment = oaLeaveapply.getHumanResourceIdea();
			}else if("UserTask_2".equals(nodeId)){//董事长
				_taskComment = oaLeaveapply.getChairManIdea();
			}
		}


		params.put("_taskComment", _taskComment);//将每个环节的意见放置进入流程意见字段
		params.put("businessKey", oaLeaveapply.getId());
		params.put("userId", UserUtils.getUser().getId());
		try {
  			
			if("demoCompleteTask".equals(action)){
				flowCenterService.completeTask(params);
			}else if("demoDoNext".equals(action)){
				flowCenterService.completeTask(params);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addMessage(redirectAttributes, "保存请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/flow/work/?repage";
	}
	
	@RequiresPermissions("oa:oaLeaveapply:edit")
	@RequestMapping(value = "delete")
	public String delete(OaLeaveapply oaLeaveapply, RedirectAttributes redirectAttributes) {
		oaLeaveapplyService.delete(oaLeaveapply);
		addMessage(redirectAttributes, "删除请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaLeaveapply/?repage";
	}

}