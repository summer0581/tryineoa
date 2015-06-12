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
import com.tryine.oa.modules.oa.entity.OaJiabanapply;
import com.tryine.oa.modules.oa.entity.OaTiaoxiuapply;
import com.tryine.oa.modules.oa.service.OaJiabanapplyService;
import com.tryine.oa.modules.oa.service.OaMessageService;
import com.tryine.oa.modules.oa.service.OaTiaoxiuapplyService;
import com.tryine.oa.modules.sys.entity.User;
import com.tryine.oa.modules.sys.service.SystemService;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 调休申请Controller
 * @author Summer
 * @version 2015-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaTiaoxiuapply")
public class OaTiaoxiuapplyController extends BaseController {

	@Autowired
	private OaTiaoxiuapplyService oaTiaoxiuapplyService;
	@Autowired
	private OaJiabanapplyService oaJiabanapplyService;
	@Autowired
	private FlowCenterServiceImpl flowCenterService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private OaMessageService oaMessageService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public OaTiaoxiuapply get(@RequestParam(required=false) String id) {
		OaTiaoxiuapply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTiaoxiuapplyService.get(id);
		}
		if (entity == null){
			entity = new OaTiaoxiuapply();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaTiaoxiuapply:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaTiaoxiuapply oaTiaoxiuapply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTiaoxiuapply> page = oaTiaoxiuapplyService.findPage(new Page<OaTiaoxiuapply>(request, response), oaTiaoxiuapply); 
		model.addAttribute("page", page);
		return "modules/oa/oaTiaoxiuapplyList";
	}

	@RequiresPermissions("oa:oaTiaoxiuapply:view")
	@RequestMapping(value = {"tiaoxiuDayList"})
	public String tiaoxiudaylist(OaTiaoxiuapply oaTiaoxiuapply, HttpServletRequest request, HttpServletResponse response, Model model) {
		User createBy = null;
		if(null == oaTiaoxiuapply.getCreateBy() || StringUtils.isBlank(oaTiaoxiuapply.getCreateBy().getId())){
			createBy = UserUtils.getUser();
		}else{
			createBy = UserUtils.get(oaTiaoxiuapply.getCreateBy().getId());
		}
		oaTiaoxiuapply.setCreateBy(createBy);
		model.addAttribute("canTiaoxiuHours", createBy.getTiaoxiuTimes());
		
		Page<OaTiaoxiuapply> page = oaTiaoxiuapplyService.findPage(new Page<OaTiaoxiuapply>(request, response), oaTiaoxiuapply); 
		OaJiabanapply oaJiabanapply = new OaJiabanapply();
		oaJiabanapply.setCreateBy(createBy);

		double jiabanHours = oaJiabanapplyService.countJiabanHours(oaJiabanapply);
		double tiaoxiuHours = oaTiaoxiuapplyService.countTiaoxiuHours(oaTiaoxiuapply);
		model.addAttribute("countJiabanHours", jiabanHours);
		
		model.addAttribute("countTiaoxiuHours", tiaoxiuHours);
		
		model.addAttribute("canTiaoxiuHours", createBy.getTiaoxiuTimes());
		double outTiaoxiuHours = createBy.getTiaoxiuTimes() - (jiabanHours - tiaoxiuHours);
		model.addAttribute("outTiaoxiuHours", outTiaoxiuHours > 0 ? outTiaoxiuHours : 0);
		model.addAttribute("page", page);
		return "modules/oa/oaTiaoxiuDayList";
	}
	@RequiresPermissions("oa:oaTiaoxiuapply:view")
	@RequestMapping(value = "form")
	public String form(OaTiaoxiuapply oaTiaoxiuapply,@RequestParam Map<String,Object> params, Model model) {
		String processInstanceId = (String)params.get("processInstanceId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		String taskId = (String)params.get("taskId");
		if(StringUtils.isNotBlank(processInstanceId)){
			params.put("action", "demoDoNext");
		}else{
			params.put("action", "demoCompleteTask");
		}
		if(StringUtils.isBlank(oaTiaoxiuapply.getName())){
			oaTiaoxiuapply.setName(UserUtils.getUser().getName());
		}

		if(StringUtils.isBlank(oaTiaoxiuapply.getPost())){
			oaTiaoxiuapply.setPost(UserUtils.getUser().getZhiwei());
		}
		if(null == oaTiaoxiuapply.getCreateDate()){
			oaTiaoxiuapply.setCreateDate(new Date());
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
		model.addAttribute("oaTiaoxiuapply", oaTiaoxiuapply);
		User createBy = null;
		if(null == oaTiaoxiuapply.getCreateBy()){
			createBy = UserUtils.getUser();
		}else{
			createBy = UserUtils.get(oaTiaoxiuapply.getCreateBy().getId());
		}
		model.addAttribute("canTiaoxiuHours", createBy.getTiaoxiuTimes());
		return "modules/oa/flow/oaTiaoxiuapplyForm";
	}

	@RequiresPermissions("oa:oaTiaoxiuapply:edit")
	@RequestMapping(value = "save")
	public String save(OaTiaoxiuapply oaTiaoxiuapply,@RequestParam Map<String,Object> params, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTiaoxiuapply)){
			return form(oaTiaoxiuapply, params, model);
		}
		Date startTime = DateUtils.parseDate(params.get("restStarttime"));
		Date endTime = DateUtils.parseDate(params.get("restEndtime"));	
		Double leaveDate = DateUtils.getDistanceOfTwoDate(startTime, endTime);
		Map<String, Double> taskParams = new HashMap<String, Double>();
		taskParams.put("leaveDate", leaveDate);
		params.put("taskParams", taskParams);//将计算出的请假天数传入流程
		oaTiaoxiuapplyService.save(oaTiaoxiuapply);
		String action = (String)params.get("action");
		String nodeId = (String)params.get("nodeId");
		String processDefinitionKey = (String)params.get("processDefinitionKey");
		String _taskComment = "";
		if("tryine_leaveapply".equals(processDefinitionKey)){
			if("UserTask_2".equals(nodeId)){//部门领导审核
				_taskComment = oaTiaoxiuapply.getDirectLeaderIdea();
			}else if("UserTask_3".equals(nodeId)){//分公司总经理
				_taskComment = oaTiaoxiuapply.getGeneralManagerIdea();
			}else if("UserTask_4".equals(nodeId)){//人资总监
				_taskComment = oaTiaoxiuapply.getHumanResourceIdea();
			}else if("UserTask_5".equals(nodeId)){//董事长
				_taskComment = oaTiaoxiuapply.getChairManIdea();
			}
		}else if("tryine_bossdirectmanager_leave".equals(processDefinitionKey)){
			if("UserTask_1".equals(nodeId)){//人资总监
				_taskComment = oaTiaoxiuapply.getHumanResourceIdea();
			}else if("UserTask_2".equals(nodeId)){//董事长
				_taskComment = oaTiaoxiuapply.getChairManIdea();
			}
		}


		params.put("_taskComment", _taskComment);//将每个环节的意见放置进入流程意见字段
		params.put("businessKey", oaTiaoxiuapply.getId());
		params.put("userId", UserUtils.getUser().getId());
		try {
			ProcessInstance processInstance = null;
			if("demoCompleteTask".equals(action)){
				processInstance = flowCenterService.completeTask(params);
			}else if("demoDoNext".equals(action)){
				flowCenterService.completeTask(params);
				processInstance = flowService.getProcessInstanceByInstanceId(params);
				if(null != processInstance.getEndTime()){//当流程完全结束时
					User user = UserUtils.get(oaTiaoxiuapply.getCreateBy().getId());
					user.setTiaoxiuTimes(user.getTiaoxiuTimes()-Double.parseDouble(oaTiaoxiuapply.getRestHours()));
					systemService.updateUserInfo(user);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		addMessage(redirectAttributes, "保存调休申请成功");
		return "redirect:"+Global.getAdminPath()+"/flow/work";
	}
	
	@RequiresPermissions("oa:oaTiaoxiuapply:edit")
	@RequestMapping(value = "delete")
	public String delete(OaTiaoxiuapply oaTiaoxiuapply, RedirectAttributes redirectAttributes) {
		oaTiaoxiuapplyService.delete(oaTiaoxiuapply);
		addMessage(redirectAttributes, "删除调休申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTiaoxiuapply";
	}

}