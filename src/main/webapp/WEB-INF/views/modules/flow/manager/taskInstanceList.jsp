<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流程干预</title>
<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/flow/css/flow.css">

<script type="text/javascript"
	src="${ctxStatic}/flow/js/common.js"></script>
<script type="text/javascript">
function doSuspend(){
	if(checkButton("doSuspend")){
 			return false;
 		}
	doProcess("doTaskSuspend");
}

function doResume(){
	if(checkButton("doResume")){
 			return false;
 		}
	doProcess("doTaskResume");
}

function doTransfer(){
	if(checkButton("doTransfer")){
			return false;
		}
	$("#transferUserId").val("");
	$("#transferUserButton").click();
	
	window.transferUserTreeselectCallBack = function(v, h, f){
		var transferUserId = $("#transferUserId").val();
		if("" != transferUserId){
			doProcess("doTaskTransfer");
		}
	}
}

function doRollBackNode(nodeId,taskId){
	if("" != nodeId){
		$("#rollBackNodeId").val(nodeId);
		doProcess("doTaskRollBackNode",taskId);
	}
}

function doRollBackTask(nodeId,taskId){
	if("" != nodeId){
		$("#rollBackTaskId").val(nodeId);
		doProcess("doTaskRollBackTask",taskId);
	}
}

function doProcess(action,taskId){
		if(taskId==null){
	 		var checkboxs = $("input:checked[name=checked]");
	 		if(checkboxs.length!=1){
	 			alert("请选中一个流程实例！");	
	 			return;
	 		}else{
	 			taskId = $(checkboxs[0]).val();
	 		}
 		}
 		
 		if(confirm("确认提交?")){
			var managerpath = "${ctx}/flow/manager";
			$("#searchForm").attr("action",managerpath+"/"+action);
			$("#taskId").val(taskId);
			$("#searchForm").submit();
		}
}
$(function(){

  $("a[name=flowGraph]").click(function(){
    var pdk = $(this).attr("pdk");
    var pii = $(this).attr("pii");
    var obj = {};
    windowOpen ("${ctx}/flow/work/getTaskDetailInfo?processDefinitionKey="+pdk+"&processInstanceId="+pii, "",
			screen.width-300,screen.height-100);
  });
    $("a[name=doTask]").click(function(){
    	var tii = $(this).attr("tii");
        var pdk = $(this).attr("pdk");
        var pii = $(this).attr("pii");
        var bizKey = $(this).attr("bk");
        
        var obj = {};
        var formUrl = $(this).attr("formUri");//"FlowCenter?action=startOneTask";
        var url = "${ctx}/"+formUrl;
        if(formUrl.indexOf("?")!=-1){
       	 url+="&";
       	
        }else{
       	 url+="?";
        }
        url+="viewonly=true&taskId="+tii+"&processInstanceId="+pii+"&id="+bizKey+"&processDefinitionKey="+pdk,obj;
        windowOpen(url,"",screen.width-300,screen.height-50);
  });
	Fix.Util.ClickTr(null,false,true,0);
	
	$("#doRollBackNode").click(function(){
		if(checkButton("doRollBackNode")){
			return false;
		}
		var checkboxs = $("input:checked[name=checked]");
		var taskid = "";
		if(checkboxs.length!=1){
			alert("请选中一个流程实例！");	
			return;
		}else{
			taskid = $(checkboxs[0]).val();
		}
		top.$.jBox.open("iframe:${ctx}/flow/work/selectNodeList?taskId="+taskid+"&userId="+$("#userId").val(), "退回节点",$(top.document).width()-520,$(top.document).height()-180,{
			buttons:{"确定":"ok","关闭":true},
			submit:function(v,h,f){
				if (v=="ok"){
					var body = h.find("iframe")[0].contentWindow.document.body;
					var nodeId = $(body).find("input[name='checked']:checked").attr("nodeId");
					doRollBackNode(nodeId, taskid);
				}
			},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
	
	$("#doRollBackTask").click(function(){
		if(checkButton("doRollBackTask")){
			return false;
		}
		var checkboxs = $("input:checked[name=checked]");
		var taskid = "";
		if(checkboxs.length!=1){
			alert("请选中一个流程实例！");	
			return;
		}else{
			taskid = $(checkboxs[0]).val();
		}
		top.$.jBox.open("iframe:${ctx}/flow/work/selectStepList?taskId="+taskid+"&userId="+$("#userId").val(), "退回步骤",$(top.document).width()-220,$(top.document).height()-180,{
			buttons:{"确定":"ok","关闭":true},
			submit:function(v,h,f){
				if (v=="ok"){
					var body = h.find("iframe")[0].contentWindow.document.body;
					var nodeId = $(body).find("input[name='checked']:checked").attr("nodeId");
					doRollBackTask(nodeId, taskid);
				}
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
});
function clearInfo(){
	$("#searchForm").find("input").not(".btn").val("");
	$("#searchForm").submit();
 	}
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>
</head>

<body>

<form id="searchForm" method="post" action="${ctx}/flow/manager/taskInstanceList" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
    <!-- 隐藏参数部分 -->
    	<input type="hidden" id="action" name="action" value="taskInstanceList"/>
		<sys:treeselect id="transferUser" name="transferUserId" value="" labelName="transferUserName" labelValue="${params.transferUserName}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" hideBtn="true" cssClass="input-medium " cssStyle="display:none"/>
    	<input type="hidden" id="rollBackNodeId" name="rollBackNodeId"/> 
    	<input type="hidden" id="rollBackTaskId" name="rollBackTaskId"/> 
    	<input type="hidden" id="taskId" name="taskId">
    	<input type="hidden" id="userId" name="userId" value="${fns:getUser().id}">
    	<ul class="ul-form">
		<li><label>任务主题：</label><input type="text" name="title" value="${params.title}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>流程名称：</label><input type="text" name="processDefinitionName" value="${params.processDefinitionName}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>单据号：</label><input type="text" name="bizKey" value="${params.bizKey}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li class="clearfix"></li>
		<li><label>发起人：</label><sys:treeselect id="initor" name="initor" value="${params.initor}" labelName="initorName" labelValue="${params.initorName}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-medium"/>
		</li>
		<li><label>到达时间：</label><input type="text" id="arrivalTimeS" name="arrivalTimeS" class="input-mini fix-input"  value="${params.arrivalTimeS}" onClick="WdatePicker()"/>
            - <input type="text" id="arrivalTimeE" name="arrivalTimeE" class="input-mini fix-input"  value="${params.arrivalTimeE}" onClick="WdatePicker()"/>
        </li>
		<li class="btns">
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<input class="btn btn-primary" type="button" onclick="clearInfo();" value="重置"/>
		</li>
		</ul>
		</form>
		<sys:message content="${message}"/>
        <div id="toolbar" style="padding-right:2px;text-align: right;margin-bottom: 4px;">
        	
        	<div id="doSuspend" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doSuspend();">暂停</a></div>
            <div id="doResume" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doResume();">恢复</a></div>
            <div id="doTransfer" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doTransfer();">转发</a></div>
            <div id="doRollBackNode" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" >退回-节点</a></div>
            <div id="doRollBackTask" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" >退回-步骤</a></div>
        </div>
      	<table width="100%" class="table table-striped table-bordered table-condensed fix-table">
		<thead>
			<th width="20">&nbsp;</th>
			<th width="30">序号</th>
			<th>流程名称</th>
			<th>单据号</th>
			<th>任务主题</th>
			<th>发起人</th>
			<th >发起时间</th>
			<th>当前步骤</th>
			<th width="160">到达时间</th>
			<th width="60">流程状态</th>
			<th width="60">运行状态</th>
		</thead>
		<c:forEach items="${page.list}" var="dataList"
			varStatus="index">
			<tr isSuspended = ${dataList.isSuspended}>
			<td class="num"><input type="radio" name="checked" value="${dataList.taskInstanceId}"></td>
				<td style="text-align:center;">${(index.index+1)+page.pageSize*(page.pageNo-1)}</td>
				<td>${dataList.processDefinitionName}</td>
				<td>${dataList.bizKey}</td>
				<td><a name="doTask" href="#"
					formUri="${dataList.formUri}" tii="${dataList.taskInstanceId}"
					pii="${dataList.processInstanceId}" bk="${dataList.bizKey}"
					pdk="${dataList.processDefinitionKey}">${dataList.description}</a>
				</td>
				<td>${dataList.userName}</td>
				<td class="time"><fmt:formatDate value="${dataList.PI_START_TIME}"
						type="both" /></td>
				<td>${dataList.nowProc}</td>
				<td class="time"><fmt:formatDate value="${dataList.createTime}"
						type="both" />
				</td>
				<td><a name="flowGraph" href="#"
					pii="${dataList.processInstanceId}"
					pdk="${dataList.processDefinitionKey}">查看</a></td>
				<td>
					<c:if test="${dataList.isSuspended == true}" var="runStatue">暂停</c:if>
					<c:if test="${dataList.isSuspended == false}" var="runStatue">运行中</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
<div class="pagination">${page}</div>

 
</body>
</html>
