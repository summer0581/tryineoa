/**
 *  Copyright 1996-2013 Founder International Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author shao
 */
package com.founder.fix.fixflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.founder.fix.fixflow.core.IdentityService;
import com.founder.fix.fixflow.core.ManagementService;
import com.founder.fix.fixflow.core.ProcessEngine;
import com.founder.fix.fixflow.core.impl.bpmn.behavior.ProcessDefinitionBehavior;
import com.founder.fix.fixflow.core.impl.identity.GroupDefinition;
import com.founder.fix.fixflow.core.impl.identity.GroupTo;
import com.founder.fix.fixflow.core.impl.identity.UserTo;
import com.founder.fix.fixflow.core.impl.util.DateUtil;
import com.founder.fix.fixflow.core.impl.util.StringUtil;
import com.founder.fix.fixflow.core.runtime.ProcessInstance;
import com.founder.fix.fixflow.core.task.IdentityLink;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.core.task.TaskQuery;
import com.founder.fix.fixflow.service.TaskInstanceService;
import com.founder.fix.fixflow.shell.CommonServiceImpl;
import com.founder.fix.fixflow.util.Pagination;

/**
 * @ClassName: TaskInstanceServiceImpl
 * @Description: TODO
 * @author shao
 *
 */
@Scope("prototype")
@Service
public class TaskInstanceServiceImpl  extends CommonServiceImpl implements TaskInstanceService {

	/*
	 * <p>Title: getTaskList</p>
	 * <p>Description: </p>
	 * @param params
	 * @return
	 * @throws Exception
	 * @see com.founder.fix.fixflow.service.TaskInstanceService#getTaskList(java.util.Map)
	 */
	public Map<String, Object> getTaskList(Map<String, Object> filter)
			throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		ProcessEngine engine = getProcessEngine(filter
				.get("userId"));
		try {
			TaskQuery tq = engine.getTaskService().createTaskQuery();
			String descritpion = StringUtil.getString(filter.get("title"));
			if(StringUtil.isNotEmpty(descritpion))
				tq.taskDescriptionLike(descritpion);
			
			String initor	   = StringUtil.getString(filter.get("initor"));
			if(StringUtil.isNotEmpty(initor))
				tq.initiatorLike(initor);
			
			String bizKey	   = StringUtil.getString(filter.get("bizKey"));
			if(StringUtil.isNotEmpty(bizKey))
				tq.businessKeyLike(bizKey);
			
			String processDefinitionName	   = StringUtil.getString(filter.get("processDefinitionName"));
			if(StringUtil.isNotEmpty(processDefinitionName))
				tq.processDefinitionNameLike(processDefinitionName);
			
			Date dates = null;
			Date datee = null;
			String dss = StringUtil.getString(filter.get("arrivalTimeS"));
			String dse = StringUtil.getString(filter.get("arrivalTimeE"));
			if(StringUtil.isNotEmpty(dss)){
				dates = DateUtil.stringToDate(dss,"yyyy-MM-dd");
			}
			if(StringUtil.isNotEmpty(dse)){
				String endTime = "235959999";
				dse += endTime;
				datee = DateUtil.stringToDate(dse,"yyyy-MM-ddHHmmssSSS");
			}
			if(dates!=null)
				tq.taskCreatedBefore(dates);
				
			
			if(datee!=null)
				tq.taskCreatedAfter(datee);
			
			String pageI = StringUtil.getString(filter.get("pageIndex"));
			String rowI = StringUtil.getString(filter.get("pageSize"));
			
			int pageIndex=1;
			int rowNum   =15;
			if(StringUtil.isNotEmpty(pageI)){
				pageIndex = Integer.valueOf(pageI);
			}
			if(StringUtil.isNotEmpty(rowI)){
				rowNum = Integer.valueOf(rowI);
			}
			
			if(filter.get("ended")==null)
				tq.taskNotEnd();
			tq.orderByTaskCreateTime().desc();
			List<TaskInstance> lts = tq.listPagination(pageIndex, rowNum);
			Long count = tq.count();
			List<Map<String,Object>> instanceMaps = new ArrayList<Map<String,Object>>();
			
			Pagination page = new Pagination(pageIndex,rowNum);
			page.setTotal(count.intValue());
			IdentityService identsvz = engine.getIdentityService();
			
			for(TaskInstance tmp:lts){ 
				Map<String,Object> instances = tmp.getPersistentState();
				String userId = StringUtil.getString(instances.get("PI_INITIATOR"));
				if(StringUtil.isEmpty(userId)){
					instances.put("userName", "(未知用户)");
				}else{
					UserTo user = identsvz.getUserTo(userId);
					if(user!=null){
						instances.put("userName", user.getPropertyValue("name"));
					}else{
						instances.put("userName", userId+"(未知用户)");
					}
				}
				
				
				String nowproc = getShareTaskNowNodeInfo(tmp,engine);
				instances.put("nowProc", nowproc);
				instanceMaps.add(instances);
			}
			result.put("dataList", instanceMaps);
			result.put("pageInfo", page);
			
		} finally {
			closeProcessEngine();
		}
		return result;
	}
	
	public void suspendTask(Map<String,Object> filter) throws Exception{
		ProcessEngine engine = getTransactionProcessEngine(filter
				.get("userId"));
		ManagementService mservice = engine.getManagementService();
		String taskId = StringUtil.getString(filter.get("taskId"));
		try{
			mservice.suspendTask(taskId);
		}finally{
			closeProcessEngine();
		}
	}
	
	public void resumeTask(Map<String,Object> filter) throws Exception{
		ProcessEngine engine = getTransactionProcessEngine(filter
				.get("userId"));
		ManagementService mservice = engine.getManagementService();
		String taskId = StringUtil.getString(filter.get("taskId"));
		try{
			mservice.resumeTask(taskId);
		}finally{
			closeProcessEngine();
		}
	}
	
	public void transferTask(Map<String,Object> filter) throws Exception{
		ProcessEngine engine = getTransactionProcessEngine(filter
				.get("userId"));
		String transferUserId = StringUtil.getString(filter.get("transferUserId"));
		String taskId = StringUtil.getString(filter.get("taskId"));
		
		ManagementService mservice = engine.getManagementService();
		try{
			mservice.transfer(taskId, transferUserId,"管理员干预", null);
		}finally{
			closeProcessEngine();
		}
	}
	
	public void rollBackNode(Map<String,Object> filter) throws Exception{
		ProcessEngine engine = getTransactionProcessEngine(filter
				.get("userId"));
		String rollBackNodeId = StringUtil.getString(filter.get("rollBackNodeId"));
		String taskId = StringUtil.getString(filter.get("taskId"));
		
		ManagementService mservice = engine.getManagementService();
		try{
			mservice.rollBack(taskId, rollBackNodeId,"管理员干预", null);
		}finally{
			closeProcessEngine();
		}
	}
	
	public void rollBackStep(Map<String,Object> filter) throws Exception{
		ProcessEngine engine = getTransactionProcessEngine(filter
				.get("userId"));
		String rollBackTaskId = StringUtil.getString(filter.get("rollBackTaskId"));
		String taskId = StringUtil.getString(filter.get("taskId"));
		
		ManagementService mservice = engine.getManagementService();
		try{
			mservice.rollBackByTaskId(taskId, rollBackTaskId,"管理员干预", null);
		}finally{
			closeProcessEngine();
		}
	}

	/**
	 * 获得实例的当前处理信息
	 * 
	 * @param taskInstanceQueryTo
	 * @return 例如 "人工任务(共享角色:功能角色)(共享部门:平台产品部,财务部)"
	 */
	public static String getShareTaskNowNodeInfo(
			TaskInstance taskInstanceQueryTo,ProcessEngine engine) {

		if (taskInstanceQueryTo.getEndTime() == null) {
			try {
				return processState(taskInstanceQueryTo,engine);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			String processInstanceId = taskInstanceQueryTo
					.getProcessInstanceId();
			return getShareTaskNowNodeInfo(processInstanceId,engine);
		}
		return null;

	}
	
	public static String getShareTaskNowNodeInfo(String processInstanceId,ProcessEngine engine) {
		try {
			String taskInfo = "";
			ProcessInstance processInstanceQueryTo = engine.getRuntimeService().getProcessInstance(processInstanceId);
			if (processInstanceQueryTo.getEndTime() != null) {
				return "完成";
			}

			List<TaskInstance> taskInstanceQueryTos = new ArrayList<TaskInstance>();
			TaskQuery tq = engine.getTaskService().createTaskQuery();
			tq.processInstanceId(processInstanceId);
			tq.taskNotEnd();
			taskInstanceQueryTos = tq.list();
			for (TaskInstance taskInstanceQueryTo2 : taskInstanceQueryTos) {
				if (taskInfo.equals("") && taskInstanceQueryTos.size() == 1) {
					taskInfo = taskInfo + processState(taskInstanceQueryTo2,engine);
				} else {
					taskInfo = taskInfo + "<div>"
							+ processState(taskInstanceQueryTo2,engine) + "</div>";
				}

			}
			return taskInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String processState(TaskInstance taskInstanceQueryTo,ProcessEngine engine)
			throws Exception {
		String taskInfo = "";
		String assignee = taskInstanceQueryTo.getAssignee();
		ProcessDefinitionBehavior processDefinition = engine.getModelService().getProcessDefinition(taskInstanceQueryTo
				.getProcessDefinitionId());
		String nodeName = processDefinition.getFlowElement(
				taskInstanceQueryTo.getNodeId()).getName();
		taskInfo = taskInfo + nodeName;
		IdentityService identityService = engine.getIdentityService();
		if (assignee == null) {
			List<UserTo> userTos = new ArrayList<UserTo>();
			Map<String, List<GroupTo>> groupTosMap = new HashMap<String, List<GroupTo>>();
			List<IdentityLink> identityLinkQueryToList = taskInstanceQueryTo.getIdentityLinkQueryToList();
			for (IdentityLink identityLinkQueryTo : identityLinkQueryToList) {
				String userId = identityLinkQueryTo.getUserId();
				if (userId == null) {
					String groupTypeId = identityLinkQueryTo.getGroupType();
					String groupId = identityLinkQueryTo.getGroupId();
					GroupTo groupTo = identityService.getGroup(groupId,
							groupTypeId);
					if (groupTo == null) {
						continue;
					}
					if (groupTosMap.get(groupTypeId) != null) {
						groupTosMap.get(groupTypeId).add(groupTo);
					} else {
						List<GroupTo> groupTos = new ArrayList<GroupTo>();
						groupTos.add(groupTo);
						groupTosMap.put(groupTypeId, groupTos);
					}
				} else {
					UserTo userTo = null;
					if (userId.equals("fixflow_allusers")) {
							userTo = new UserTo("fixflow_allusers", "所有人", null);
					} else {
						userTo = getUserTo(userId,engine);
					}
					if (userTo != null) {
						userTos.add(userTo);
					}
				}
			}
			if (userTos.size() > 0) {
				String groupTypeName = "";
				groupTypeName = "用户";
				taskInfo += "(共享 " + groupTypeName + " : ";
				for (int i = 0; i < userTos.size(); i++) {
					UserTo userTo = userTos.get(i);
					if (i == userTos.size() - 1) {
						taskInfo += userTo.getPropertyValue("name");
					} else {
						taskInfo += userTo.getPropertyValue("name") + ",";
					}
				}
				taskInfo = taskInfo + ")";
			}
			for (String groupToKey : groupTosMap.keySet()) {
				List<GroupTo> groupTos = groupTosMap.get(groupToKey);
				GroupDefinition groupDefinition = identityService.getGroupDefinition(groupToKey);
				String groupTypeName = "";
				groupTypeName = groupDefinition.getName();
				taskInfo += "(共享 " + groupTypeName + " : ";
				taskInfo += listToStr(groupTos, ",", groupDefinition) + ")";
			}
		} else {
			String username = getUsername(assignee,engine);
			username = "<span title='" + username + "(" + assignee + ")'>" + username + "</span>";
				taskInfo = taskInfo + " (处理者 ： " + username + ") ";
		}
		return taskInfo;
	}
	
	protected static UserTo getUserTo(String userId,ProcessEngine engine) {
		try {
			UserTo user = engine.getIdentityService().getUserTo(userId);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String listToStr(List<GroupTo> groupTos, String joinChar,
			GroupDefinition groupDefinition) {
		if (groupTos == null || groupTos.size() == 0 || joinChar == null) {
			return "";
		}
		String listStr = "";
		for (GroupTo groupTo : groupTos) {

			List<UserTo> userTos = groupDefinition
					.findUserChildMembersIncludeByGroupId(groupTo.getGroupId());
			String nameList = "";
			int x = 0;
			int y = 5;
			if (userTos.size() > y) {
				userTos = userTos.subList(0, y);
				x = 1;
			}
			for (int i = 0; i < userTos.size(); i++) {
				UserTo userTo = userTos.get(i);
				if (i == userTos.size() - 1) {
					nameList = nameList + userTo.getUserName() + "("
							+ userTo.getUserId() + ")";
				} else {
					nameList = nameList + userTo.getUserName() + "(" + userTo.getUserId() + "),  ";
				}
			}
			if (x == 1) {
				nameList = nameList + " .......";
			}
			listStr = listStr + "<span title='" + nameList + "'>" + groupTo.getGroupName() + "</span>" + joinChar;
		}
		listStr = listStr.substring(0, listStr.length() - joinChar.length());
		return listStr;
	}
	
	protected static String getUsername(String userId,ProcessEngine engine) {
		String assigneeName = "";
		if (StringUtil.isEmpty(userId)) {
			return "";
		}
		try {
			UserTo user = getUserTo(userId,engine);
			assigneeName = user.getUserName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assigneeName;
	}
}
