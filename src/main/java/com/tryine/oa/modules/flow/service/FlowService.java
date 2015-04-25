/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.flow.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fix.fixflow.core.ProcessEngine;
import com.founder.fix.fixflow.core.runtime.ProcessInstance;
import com.founder.fix.fixflow.core.runtime.ProcessInstanceQuery;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.core.task.TaskQuery;
import com.founder.fix.fixflow.shell.CommonServiceImpl;

/**
 * 流程模型相关Controller
 * @author Summer
 * @version 2015-4-14
 */
@Service
@Transactional(readOnly = true)
public class FlowService extends CommonServiceImpl {
	
	/**
	 * 根据流程实例id获取任务实例
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public ProcessInstance getProcessInstanceByInstanceId(Map<String,Object> filter) throws SQLException {
		ProcessEngine engine = getProcessEngine(filter
				.get("userId"));
		ProcessInstanceQuery pio = engine.getRuntimeService().createProcessInstanceQuery();
		ProcessInstance pi = null;
		try{
			pi = pio.processInstanceId((String)filter
					.get("processInstanceId")).singleResult();
		}finally {
			closeProcessEngine();
		}
		return pi;
	}
	
	public TaskInstance getTaskInstanceByInstanceId(Map<String,Object> filter) throws SQLException {
		ProcessEngine engine = getProcessEngine(filter
				.get("userId"));
		TaskQuery tq = engine.getTaskService().createTaskQuery();
		TaskInstance ti = null;
		try{
			ti = tq.processInstanceId((String)filter
					.get("processInstanceId")).list().get(0);
		}finally {
			closeProcessEngine();
		}
		return ti;
	}
	public TaskInstance getTaskInstanceByTaskId(Map<String,Object> filter) throws SQLException {
		ProcessEngine engine = getProcessEngine(filter
				.get("userId"));
		TaskQuery tq = engine.getTaskService().createTaskQuery();
		TaskInstance ti = null;
		try{
			ti = tq.taskId((String)filter
					.get("taskId")).list().get(0);
		}finally {
			closeProcessEngine();
		}
		return ti;
	}
	
}
