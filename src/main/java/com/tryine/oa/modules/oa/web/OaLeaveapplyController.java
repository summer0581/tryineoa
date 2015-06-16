/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
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

import com.founder.fix.fixflow.core.runtime.ProcessInstance;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.service.impl.FlowCenterServiceImpl;
import com.founder.fix.fixflow.shell.FlowUtilServiceImpl;
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.DateUtils;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.flow.service.FlowService;
import com.tryine.oa.modules.oa.entity.OaLeaveapply;
import com.tryine.oa.modules.oa.service.OaLeaveapplyService;
import com.tryine.oa.modules.oa.service.OaMessageService;
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
	@Autowired
	private OaMessageService oaMessageService;
	
	FlowUtilServiceImpl flowUtil = new FlowUtilServiceImpl();
	
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
		if(StringUtils.isBlank(oaLeaveapply.getPost())){
			oaLeaveapply.setPost(UserUtils.getUser().getZhiwei());
		}
		if(StringUtils.isBlank(oaLeaveapply.getTelephone())){
			oaLeaveapply.setTelephone(UserUtils.getUser().getMobile());
		}
		if(null == oaLeaveapply.getCreateDate()){
			oaLeaveapply.setCreateDate(new Date());
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
		Date startTime = DateUtils.parseDate(params.get("starttime"));
		Date endTime = DateUtils.parseDate(params.get("endtime"));	
		Double leaveDate = DateUtils.getDistanceOfTwoDate(startTime, endTime);
		Map<String, Double> taskParams = new HashMap<String, Double>();
		taskParams.put("leaveDate", leaveDate);
		params.put("taskParams", taskParams);//将计算出的请假天数传入流程
		oaLeaveapplyService.save(oaLeaveapply);
		String action = (String)params.get("action");
		String nodeId = (String)params.get("nodeId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		
		String _taskComment = "";
		if("tryine_leaveapply".equals(processDefinitionKey)){
			if("UserTask_2".equals(nodeId)){//部门领导审核
				_taskComment = oaLeaveapply.getDirectLeaderIdea();
			}else if("UserTask_3".equals(nodeId)){//分公司总经理
				_taskComment = oaLeaveapply.getBranchLeaderIdea();
			}else if("UserTask_4".equals(nodeId)){//人资总监
				_taskComment = oaLeaveapply.getHumanResourceIdea();
			}else if("UserTask_5".equals(nodeId)){//集团总经理
				_taskComment = oaLeaveapply.getGeneralManagerIdea();
			}else if("UserTask_7".equals(nodeId)){//董事长
				_taskComment = oaLeaveapply.getChairManIdea();
			} 
		}else if("tryine_bossdirectmanager_leave".equals(processDefinitionKey)){
			if("UserTask_1".equals(nodeId)){//人资总监
				_taskComment = oaLeaveapply.getHumanResourceIdea();
			}else if("UserTask_5".equals(nodeId)){//董事长
				_taskComment = oaLeaveapply.getChairManIdea();
			}else if("UserTask_2".equals(nodeId)||"UserTask_7".equals(nodeId)){//集团总经理
				_taskComment = oaLeaveapply.getGeneralManagerIdea();
			}
		}


		params.put("_taskComment", _taskComment);//将每个环节的意见放置进入流程意见字段
		params.put("businessKey", oaLeaveapply.getId());
		params.put("userId", UserUtils.getUser().getId());
		try {
			ProcessInstance processInstance = null;
			if("demoCompleteTask".equals(action)){
				processInstance = flowCenterService.completeTask(params);
			}else if("demoDoNext".equals(action)){
				processInstance = flowCenterService.completeTask(params);
			}
			/*String processInstanceId = "";
			if(null != processInstance){
				processInstanceId = processInstance.getId();
			}else{
				processInstanceId = (String)params.get("processInstanceId");
			}
			List<UserTo> userList = flowUtil.getNextTaskAssignee(processInstanceId);
			
			if(null != userList){
				OaMessage oaMessage =  new OaMessage();
				oaMessage.setContent("您有新的【"+processInstance.getSubject()+"】待办流程需要审批！");
				oaMessage.setUrl("/flow/work");
				List<OaMessageRecord> oaMessageRecordList = new ArrayList<OaMessageRecord>();
				for(UserTo userto : userList){
					OaMessageRecord entity = new OaMessageRecord();
					entity.setId(IdGen.uuid());
					entity.setOaMessage(oaMessage);
					entity.setUser(new User(userto.getUserId()));
					entity.setReadFlag("0");
					oaMessageRecordList.add(entity);
				}
				oaMessage.setOaMessageRecordList(oaMessageRecordList);
				oaMessageService.save(oaMessage);
			}*/

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addMessage(redirectAttributes, "保存请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/flow/work";
	}
	
	@RequiresPermissions("oa:oaLeaveapply:edit")
	@RequestMapping(value = "delete")
	public String delete(OaLeaveapply oaLeaveapply, RedirectAttributes redirectAttributes) {
		oaLeaveapplyService.delete(oaLeaveapply);
		addMessage(redirectAttributes, "删除请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaLeaveapply";
	}

}