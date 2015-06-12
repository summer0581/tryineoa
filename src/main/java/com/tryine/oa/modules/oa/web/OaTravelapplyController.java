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
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.DateUtils;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.flow.service.FlowService;
import com.tryine.oa.modules.oa.entity.OaTravelapply;
import com.tryine.oa.modules.oa.service.OaTravelapplyService;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 出差申请Controller
 * @author Summer
 * @version 2015-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaTravelapply")
public class OaTravelapplyController extends BaseController {

	@Autowired
	private OaTravelapplyService oaTravelapplyService;
	@Autowired
	private FlowCenterServiceImpl flowCenterService;
	@Autowired
	private FlowService flowService;
	
	@ModelAttribute
	public OaTravelapply get(@RequestParam(required=false) String id) {
		OaTravelapply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTravelapplyService.get(id);
		}
		if (entity == null){
			entity = new OaTravelapply();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaTravelapply:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaTravelapply oaTravelapply,@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTravelapply> page = oaTravelapplyService.findPage(new Page<OaTravelapply>(request, response), oaTravelapply); 
		model.addAttribute("page", page);
		return "modules/oa/oaTravelapplyList";
	}

	@RequiresPermissions("oa:oaTravelapply:view")
	@RequestMapping(value = "form")
	public String form(OaTravelapply oaTravelapply,@RequestParam Map<String,Object> params, Model model) {
		String processInstanceId = (String)params.get("processInstanceId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		String taskId = (String)params.get("taskId");
		if(StringUtils.isNotBlank(processInstanceId)){
			params.put("action", "demoDoNext");
		}else{
			params.put("action", "demoCompleteTask");
		}
		if(StringUtils.isBlank(oaTravelapply.getName())){
			oaTravelapply.setName(UserUtils.getUser().getName());
		}
		if(null == oaTravelapply.getOffice()){
			oaTravelapply.setOffice(UserUtils.getUser().getOffice());
		}
		if(StringUtils.isBlank(oaTravelapply.getPost())){
			oaTravelapply.setPost(UserUtils.getUser().getZhiwei());
		}
		if(StringUtils.isBlank(oaTravelapply.getTelephone())){
			oaTravelapply.setTelephone(UserUtils.getUser().getMobile());
		}
		if(null == oaTravelapply.getCreateDate()){
			oaTravelapply.setCreateDate(new Date());
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
		model.addAttribute("oaTravelapply", oaTravelapply);
		return "modules/oa/flow/oaTravelapplyForm";
	}

	@RequiresPermissions("oa:oaTravelapply:edit")
	@RequestMapping(value = "save")
	public String save(OaTravelapply oaTravelapply,@RequestParam Map<String,Object> params, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTravelapply)){
			return form(oaTravelapply, params, model);
		}
		Date startTime = DateUtils.parseDate(params.get("outTime"));
		Date endTime = DateUtils.parseDate(params.get("plantobacktime"));	
		Double leaveDate = DateUtils.getDistanceOfTwoDate(startTime, endTime);
		Map<String, Double> taskParams = new HashMap<String, Double>();
		taskParams.put("leaveDate", leaveDate);
		params.put("taskParams", taskParams);//将计算出的请假天数传入流程
		oaTravelapplyService.save(oaTravelapply);
		String action = (String)params.get("action");
		String nodeId = (String)params.get("nodeId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		
		String _taskComment = "";
		if("tryine_travelapply".equals(processDefinitionKey)){
			if("UserTask_2".equals(nodeId)){//部门领导审核
				_taskComment = oaTravelapply.getDirectleaderIdea();
			}else if("UserTask_3".equals(nodeId)){//分公司总经理
				_taskComment = oaTravelapply.getGeneralManagerIdea();
			}
		}else if("tryine_bossdirectmanager_travel".equals(processDefinitionKey)){
			if("UserTask_1".equals(nodeId)){//人力资源审核
				_taskComment = oaTravelapply.getDirectleaderIdea();
			}else if("UserTask_2".equals(nodeId)){//董事长审核
				_taskComment = oaTravelapply.getGeneralManagerIdea();
			}
		}


		params.put("_taskComment", _taskComment);//将每个环节的意见放置进入流程意见字段
		params.put("businessKey", oaTravelapply.getId());
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
		addMessage(redirectAttributes, "保存出差申请成功");
		return "redirect:"+Global.getAdminPath()+"/flow/work";
	}
	
	@RequiresPermissions("oa:oaTravelapply:edit")
	@RequestMapping(value = "delete")
	public String delete(OaTravelapply oaTravelapply, RedirectAttributes redirectAttributes) {
		oaTravelapplyService.delete(oaTravelapply);
		addMessage(redirectAttributes, "删除出差申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTravelapply";
	}

}