/**
 * Copyright 1996-2013 Founder International Co.,Ltd.
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
 * @author kenshin
 */
package com.founder.fix.fixflow.core.impl.cmd;

import com.founder.fix.fixflow.core.impl.interceptor.Command;
import com.founder.fix.fixflow.core.impl.interceptor.CommandContext;
import com.founder.fix.fixflow.core.runtime.ProcessInstance;

public class GetProcessInstanceSimpleCmd implements Command<ProcessInstance> {

	protected String processInstanceId;
	
	public GetProcessInstanceSimpleCmd(String processInstanceId){
		this.processInstanceId=processInstanceId;
	}
	
	
	public ProcessInstance execute(CommandContext commandContext) {
		

		ProcessInstance processInstance=commandContext.getProcessInstanceManager().findProcessInstanceSimpleById(processInstanceId);
		
		
		return processInstance;
	}

}
