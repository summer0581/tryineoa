/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.flow.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.founder.fix.fixflow.core.impl.util.StringUtil;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.service.JobService;
import com.founder.fix.fixflow.service.ProcessDefinitionService;
import com.founder.fix.fixflow.service.ProcessInstanceService;
import com.founder.fix.fixflow.service.TaskInstanceService;
import com.founder.fix.fixflow.service.UserGroupService;
import com.founder.fix.fixflow.util.Pagination;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.utils.UploadUtils;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 流程管理相关Controller
 * @author summer
 * @version 2015-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/manager")
public class FlowManagerController extends BaseController {

	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TaskInstanceService taskInstanceService;
	@Autowired
	private ProcessDefinitionService processDefinitionService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private JobService jobService;

	/**
	 * 流程实例管理任务列表
	 */
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "list", "" })
	public String processManageList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page<TaskInstance> page = new Page<TaskInstance>(request, response);
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		try {
			pageResult = processInstanceService.getProcessInstances(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<TaskInstance>)pageResult.get("dataList"));
		model.addAttribute("page", page);
		model.addAttribute("params", params);

		return "modules/flow/manager/processInstanceList";
	}
	
	/**
	 * 流程定义管理任务列表
	 */
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "processDefinitionList"})
	public String processDefinitionList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page page = new Page(request, response);
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		params.put("userId", UserUtils.getUser().getId());
		try {
			pageResult = processDefinitionService.getProcessDefitionList(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<Map<String, Object>>)pageResult.get("dataList"));
		model.addAttribute("page", page);
		model.addAttribute("params", params);

		return "modules/flow/manager/processDefinitionList";
	}

	
	//暂停流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "suspendProcessInstance"})
	public String suspendProcessInstance(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			processInstanceService.suspendProcessInstance(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程暂停失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager?repage";
	}
	//恢复流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "continueProcessInstance"})
	public String continueProcessInstance(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			processInstanceService.continueProcessInstance(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程恢复失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager?repage";
	}
	
	//作废流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "terminatProcessInstance"})
	public String terminatProcessInstance(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			processInstanceService.terminatProcessInstance(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程作废失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager?repage";
	}
	
	//作废流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "deleteProcessInstance"})
	public String deleteProcessInstance(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			processInstanceService.deleteProcessInstance(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程删除失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager?repage";
	}
	//获取进程token流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "processTokenList"})
	public String processTokenList(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		Map<String, Object> pageResult = new HashMap<String,Object>();

		try {
			pageResult = processInstanceService.getProcessTokens(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "获取进程token失败！失败信息："+e.getMessage());
		}

		model.addAttribute("result", pageResult);
		return "modules/flow/manager/processTokenList";
	}
	
	//获取流程参数流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "toProcessVariable"})
	public String toProcessVariable(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		Map<String, Object> pageResult = new HashMap<String,Object>();
		try {
			pageResult = processInstanceService.getProcessVariables(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "获取流程参数失败！失败信息："+e.getMessage());
		}
		model.addAttribute("result", pageResult);		
		return "modules/flow/manager/processVariableList";
	}
	
	//将流程归档
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "setHis"})
	public String setHis(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			processInstanceService.setHistory(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程归档失败！失败信息："+e.getMessage());
		}		
		return "redirect:" + adminPath + "/flow/manager?repage";
	}
	
	
	/**
	 * 流程实例管理任务列表
	 */
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "taskInstanceList"})
	public String taskInstanceList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page<TaskInstance> page = new Page<TaskInstance>(request, response);
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		try {
			pageResult = taskInstanceService.getTaskList(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<TaskInstance>)pageResult.get("dataList"));
		model.addAttribute("page", page);
		model.addAttribute("params", params);

		return "modules/flow/manager/taskInstanceList";
	}
	
	//暂停ssffffffffffffffffffffffff
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "doTaskSuspend"})
	public String doTaskSuspend(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			taskInstanceService.suspendTask(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "任务暂停失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager/taskInstanceList?repage";
	}
	
	//流程定义xxx详细页面22222222zzwwwwwwwwwwxxxwwwwwwwwwwwwwwwwwwwwwwwwwww
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "processDefinitionDetail"})
	public String processDefinitionDetail(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "查询流程定义失败！失败信息："+e.getMessage());
		}
		return "modules/flow/manager/processDefinitionDetail";
	}
	
	//恢复
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "doTaskResume"})
	public String doTaskResume(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			taskInstanceService.resumeTask(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "任务恢复失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/flow/manager/taskInstanceList?repage";
	}
	
	//转发流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "doTaskTransfer"})
	public String doTaskTransfer(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			taskInstanceService.transferTask(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程转发失败！失败信息："+e.getMessage());
		}		
		return "redirect:" + adminPath + "/flow/manager/taskInstanceList?repage";
	}
	
	//退回节点
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "doTaskRollBackNode"})
	public String doTaskRollBackNode(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			taskInstanceService.rollBackNode(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程退回节点失败！失败信息："+e.getMessage());
		}		
		return "redirect:" + adminPath + "/flow/manager/taskInstanceList?repage";
	}
	
	//退回步骤
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "doTaskRollBackTask"})
	public String doTaskRollBackTask(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			taskInstanceService.rollBackStep(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage(redirectAttributes, "流程步骤退回失败！失败信息："+e.getMessage());
		}		
		return "redirect:" + adminPath + "/flow/manager/taskInstanceList?repage";
	}
	
	//发布流程
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "deployment"})
	public String deployment(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		model.addAllAttributes(params);
		return "modules/flow/manager/deployment";
	}
	
	//发布流程
	
	@RequestMapping(value = {"deploy"})
	public @ResponseBody Map<String, Object> deploy(@RequestParam Map<String,Object> params,@RequestParam CommonsMultipartFile ProcessFile,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			UploadUtils  uploadUtils = new UploadUtils();
			params.put("userId", UserUtils.getUser().getId());
			params.put("ProcessFile", ProcessFile.getFileItem());
			processDefinitionService.deployByZip(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.remove("ProcessFile");
	    params.put("success", "true"); 
		return params;
	}
	
	@RequestMapping(value = {"deleteDeploy"})
	public @ResponseBody Map<String, Object> deleteDeploy(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			params.put("userId", UserUtils.getUser().getId());
			processDefinitionService.deleteDeploy(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    params.put("success", "true"); 
		return params;
	}
	
	@RequestMapping(value = {"download"})
	public void download(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		String processDefinitionId = StringUtil.getString(params.get("processDefinitionId"));
		response.reset();
		try {
			request.setCharacterEncoding("gbk");
			response.setContentType("applcation/octet-stream");
			response.setHeader("Content-disposition","attachment; filename="+processDefinitionId+".zip");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0,private, max-age=0");
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			
			ZipOutputStream outZip = new ZipOutputStream(response.getOutputStream());
			List<Map<String,Object>> fileList = processDefinitionService.getResources(params);
			for (Map<String, Object> file : fileList) {
				ZipEntry entry = new ZipEntry(file.get("FILENAME").toString());
				entry.setSize(((byte[])file.get("BYTES")).length);
				outZip.putNextEntry(entry);
				outZip.write((byte[])file.get("BYTES"));
				outZip.closeEntry();
			}
			outZip.close();
			outZip.flush();
			outZip.close(); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = {"testajax"})
	public @ResponseBody Map<String, Object> testajax(@RequestParam Map<String,Object> params,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
	    params.put("success", "true"); 
		return params;
	}
	
	@RequestMapping(value = {"uploadify"})
	public @ResponseBody Map<String, Object> uploadify(@RequestParam Map<String,Object> params,@RequestParam CommonsMultipartFile file_upload_1,HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			UploadUtils  uploadUtils = new UploadUtils();
			params.put("userId", UserUtils.getUser().getId());
			params.put("ProcessFile", file_upload_1.getFileItem());
			//processDefinitionService.deployByZip(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.remove("ProcessFile");
	    params.put("success", "true"); 
		return params;
	}

}
