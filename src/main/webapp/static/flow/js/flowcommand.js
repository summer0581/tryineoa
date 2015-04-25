function regFlowCommand(formId,processInstanceId,processDefinitionKey,taskId){
		var ctaskParams = '<input type="hidden" id="taskParams" name="taskParams"/>';
		var ccommandId = '<input type="hidden" id="commandId" name="commandId"/>';
		var ccommandType = '<input type="hidden" id="commandType" name="commandType"/>';
		var cprocessInstanceId = '<input type="hidden" id="processInstanceId" name="processInstanceId" value="'+processInstanceId+'"/>';
		var cprocessDefinitionKey = '<input type="hidden" id="processDefinitionKey" name="processDefinitionKey" value="'+processDefinitionKey+'"/>';
		var ctaskId = '<input type="hidden" id="taskId" name="taskId" value="'+taskId+'"/>';
		
		$("#"+formId).append(ctaskParams);
		$("#"+formId).append(ccommandId);
		$("#"+formId).append(ccommandType);
		$("#"+formId).append(cprocessInstanceId);
		$("#"+formId).append(cprocessDefinitionKey);
		$("#"+formId).append(ctaskId);

		$("div[commandType],button[commandType]").click(function() {
			var id = $(this).attr("commandId");
			var type = $(this).attr("commandType");
			$("#commandId").val(id);
			$("#commandType").val(type);
			var params={};

			if(type=="processStatus"){
				var pii = processInstanceId;
				var pdk = processDefinitionKey;
				var obj = {};

				windowOpen (ctx+"/flow/work/getTaskDetailInfo?processInstanceId="
						+ pii+"&processDefinitionKey="
						+ pdk+"", "",
		    			screen.width-300,screen.height-100);
				return false;
			}else if(type=="transfer"){
				var obj = {
						  type:"user"
						};
				var fn = function(params,d){
					params['transferUserId'] = d[0].USERID;
				};
				var d = FixSelect(obj,fn,params);
				params={
						//被转发的UserId，这里设定了就是管理员
						transferUserId:d[0].USERID
				};
			}else if(type=="pending"){//转办
				var obj = {type:"user"};
				var fn = function(params,d){
					if(d&&d.length>0){
						params['recoverNodeId'] = d[0].nodeId;
				  	}else{
				  		return;
				  	}
				};
			  	var d = FixSelect(obj,fn,params);
			  	if(d&&d.length>0){
					params={
							//转办的任务编号
						pendingUserId:d[0].USERID
					};
			  	}else{
			  		return;
			  	}
			}else if(type=="recover"){
				var obj = {
						  type:"node",
						taskId:taskId
						};
				var fn = function(params,d){
					params['recoverNodeId'] = d[0].nodeId;
				};
				var d = FixSelect(obj,fn,params);
				params={
						//追回的任务编号
					recoverNodeId:d[0].nodeId
				};
			}else if(type=="reminders"){
				params={
						//提醒某一个用户
					usersInfo:"",
						//提醒内容
					content:"",
						//提醒标题
					title:""
						
				};
			}else if(type=="rollBack"){
				var obj = {
						  type:"node",
						taskId:taskId
						};
				var fn = function(params,d){
					params['rollBackNodeId'] = d[0].nodeId;
				};
				var d = FixSelect(obj,fn,params);
				params={
						//退回到某个节点
					rollBackNodeId:d[0].nodeId
						
				};
			}else if(type=="rollBackTaskByTaskId"){
				var obj = {
						type:"step",
						taskId:taskId
						};
				var fn = function(params,d){
					params['rollBackTaskId'] = d[0].taskId;
				};
				var d = FixSelect(obj,fn,params);
				params={
						//退回到某个节点
					rollBackTaskId:d[0].taskId
						
				};
			}
			if(params!={}){
				if(confirm("确认提交?")){
					var ss = JSON.stringify(params);
					$("#taskParams").val(ss);
					$("#"+formId).submit();
				}else 
					return false;
			}
		});
	}

function requestUrlParam(paras)
{ 
    var url = location.href; 
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {};
    for (var i=0; j=paraString[i]; i++){ 
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined"){ 
    return ""; 
    }else{ 
    return returnValue; 
    } 
}