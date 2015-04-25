/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.flow.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.founder.fix.fixflow.core.impl.util.StringUtil;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.service.impl.FlowCenterServiceImpl;
import com.founder.fix.fixflow.util.Pagination;
import com.tryine.oa.common.persistence.Page;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.modules.sys.utils.UserUtils;

/**
 * 流程任务相关Controller
 * @author summer
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/work")
public class FlowCenterController extends BaseController {

	@Autowired
	private FlowCenterServiceImpl flowCenterService;

	/**
	 * 流程代办任务列表
	 */
	@RequiresPermissions("flow:todowork:list")
	@RequestMapping(value = { "list", "" })
	public String todoWorkList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page<TaskInstance> page = new Page<TaskInstance>(request, response);
		params.put("userId", UserUtils.getUser().getId());
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		try {
			pageResult = flowCenterService
					.queryMyTaskNotEnd(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<TaskInstance>)pageResult.get("dataList"));
		model.addAttribute("page", page);

		return "modules/flow/todoWorkList";
	}
	
	/**
	 * 流程已办任务列表
	 */
	@RequiresPermissions("flow:todowork:list")
	@RequestMapping(value = { "allMyStartWorkList"})
	public String allMyStartWorkList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page<TaskInstance> page = new Page<TaskInstance>(request, response);
		params.put("userId", UserUtils.getUser().getId());
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		params.put("processType", "initor");
		try {
			pageResult = flowCenterService
					.queryTaskInitiator(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<TaskInstance>)pageResult.get("dataList"));
		model.addAttribute("page", page);

		return "modules/flow/allMyStartWorkList";
	}
	
	/**
	 * 流程已办任务列表
	 */
	@RequiresPermissions("flow:todowork:list")
	@RequestMapping(value = { "allMyJoinWorkList"})
	public String allMyJoinWorkList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		Page<TaskInstance> page = new Page<TaskInstance>(request, response);
		params.put("userId", UserUtils.getUser().getId());
		params.put("pageIndex", page.getPageNo());
		params.put("pageSize", page.getPageSize());
		try {
			pageResult = flowCenterService
					.queryTaskParticipants(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination ff_page = ((Pagination)pageResult.get("pageInfo"));
		page.setCount(ff_page.getTotal());
		page.setPageNo(ff_page.getPageIndex());
		page.setList((List<TaskInstance>)pageResult.get("dataList"));
		model.addAttribute("page", page);

		return "modules/flow/allMyJoinWorkList";
	}
	
	@RequiresPermissions("flow:todowork:list")
	@RequestMapping(value = { "getTaskDetailInfo"})
	public String getTaskDetailInfo(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result = flowCenterService.getTaskDetailInfo(params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.putAll(result);
		model.addAttribute("result", params);
		return "modules/flow/flowGraphic";
	}
	
	@RequestMapping(value = { "getFlowGraph"})
	@ResponseBody
	public void getFlowGraph(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model){
		InputStream is;
		try {
			is = flowCenterService.getFlowGraph(params);
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream;charset=UTF-8");
			byte[] buff = new byte[2048];
			int size = 0;
			while (is != null && (size = is.read(buff)) != -1) {
				out.write(buff, 0, size);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequiresPermissions("flow:todowork:list")
	@RequestMapping(value = { "getMyProcess"})
	public String getMyProcess(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model){
		String userId = UserUtils.getUser().getId();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			result = flowCenterService
					.queryStartProcess(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,List<Map<String, String>>> newResult = new HashMap<String,List<Map<String, String>>>();
		for(Map<String,String> tmp:result){
			String category = tmp.get("category");
			if(StringUtil.isEmpty(category))
				category = "默认分类";
			
			List<Map<String, String>> tlist = newResult.get(category);
			if(tlist==null){
				tlist= new ArrayList<Map<String, String>>();
			}
			tlist.add(tmp);
			newResult.put(category, tlist);
		}
		model.addAttribute("result", newResult);
		//获取最近使用流程在sqlserver下有已知语法bug，所以捕捉掉不影响功能使用
		model.addAttribute("userId", userId); // 返回userId add Rex
		try{
			List<Map<String,String>> lastestProcess = flowCenterService.queryLastestProcess(userId);
			request.setAttribute("lastest", lastestProcess);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "modules/flow/startTask";
	}
	
	/**
	 * 流程跳转节点列表
	 */
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "selectNodeList"})
	public String selectNodeList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> pageResult = new HashMap<String,Object>();
		try {
			pageResult = flowCenterService.getRollbackNode(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("result", pageResult);
		model.addAttribute("params", params);

		return "modules/flow/selectNodeList";
	}
	
	/**
	 * 流程跳转步骤列表
	 */
	@RequiresPermissions("flow:manager:list")
	@RequestMapping(value = { "selectStepList"})
	public String selectStepList(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, Object> pageResult = new HashMap<String,Object>();
		try {
			pageResult = flowCenterService.getRollbackTask(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("result", pageResult);
		model.addAttribute("params", params);

		return "modules/flow/selectStepList";
	}


}
