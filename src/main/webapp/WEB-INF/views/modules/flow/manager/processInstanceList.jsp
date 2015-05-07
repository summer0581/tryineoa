<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程实例管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/flow/css/flow.css">
		<script type="text/javascript"
	src="${ctxStatic}/flow/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
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
			Fix.Util.ClickTr(null,false,true,0,function($table){
				  var flag = true;
				  $("tbody tr.selected",$table).each(function(){
				    var state = $("td:eq(10)",$(this)).html();
				    //alert(state);
				    if(state.trim()=="运行中" ||state.trim()=="暂停"){
				      flag = false;
				    }
				  });
				  if(!flag){
				    $("div#setHis").removeClass("btn-normal").addClass("btn-disable");
				  }
				});

		});
		
	  	function updateVariables(){
	  		if(checkButton("updateVariables")){
	  			return false;
	  		}
	 		var checkboxs = $("input[name=checked]");
	 		var id = "";
	 		
	 		for(var i=0;i<checkboxs.length;i++) 
			{ 
				if(checkboxs[i].checked==true){
					id = $(checkboxs[i]).val();
					break;
				}
			}
			if(id!=""){
				var obj = {};
				windowOpen("${ctx}/flow/manager/toProcessVariable?processInstanceId="+id, "流程变量管理",
		    			screen.width-300,screen.height-100);
		    }
	  	}
	  	
	  	function updateToken(){
	  		if(checkButton("updateToken")){
	  			return false;
	  		}
	 		var checkboxs = $("input[name=checked]");
	 		var id = "";
	 		
	 		for(var i=0;i<checkboxs.length;i++) 
			{ 
				if(checkboxs[i].checked==true){
					id = $(checkboxs[i]).val();
					break;
				}
			}
			if(id!=""){
				var obj = {};
				windowOpen("${ctx}/flow/manager/processTokenList?processInstanceId="+id, "流程令牌管理",
		    			screen.width-300,screen.height-100);
		    }
	  	}
	  	function doSuspend(){
	  		if(checkButton("doSuspend")){
	  			return false;
	  		}
	  		doProcess("suspendProcessInstance");
	  	}
	  	function doContinue(){
	  		if(checkButton("doContinue")){
	  			return false;
	  		}
	  		doProcess("continueProcessInstance");
	  	}
	  	function doTerminat(){
	  		if(checkButton("doTerminat")){
	  			return false;
	  		}
	  		doProcess("terminatProcessInstance");
	  	}
	  	function doDelete(){
	  		if(checkButton("doDelete")){
	  			return false;
	  		}
	  		doProcess("deleteProcessInstance");
	  	}
	  	function setHis(){
	  		if(checkButton("setHis")){
	  			return false;
	  		}
	  		doProcess("setHis");
	  	}
	  	function doProcess(action){
	 		var checkboxs = $("input:checked[name=checked]");
	 		var id = "";
	 		if(checkboxs.length!=1){
	 			alert("请选中一个流程实例！");	
	 			return;
	 		}else{
		   		for(var i=0;i<checkboxs.length;i++) 
				{ 
					if(i!=0){
						id += ',';
					}
					id += $(checkboxs[i]).val();
				}
	 		}
	 		if(confirm("确认提交?")){
	 			var managerpath = $("#searchForm").attr("action");
				$("#searchForm").attr("action",managerpath+"/"+action);
				$("#operProcessInstanceId").val(id);
				$("#searchForm").submit();
	 		}
	  	}
		function page(n,s){
        	location = '${ctx}/flow/work/?pageNo='+n+'&pageSize='+s;
        }
		
		function clearInfo(){
			$("#searchForm").find("input").not(".btn").val("");
			$("#searchForm").submit();
	 	}
	</script>

</head>
<body>
	<form id="searchForm"  action="${ctx}/flow/manager" method="post" class="breadcrumb form-search">
		<input type="hidden" id="operProcessInstanceId" name="operProcessInstanceId">
    	<input type="hidden" id="action" name="action" value="processManageList"/>
    	<ul class="ul-form">
		<li><label>流程名称：</label><input type="text" name="processName" value="${params.processName}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>实例编号：</label><input type="text" name="processInstanceId" value="${params.processInstanceId}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>流程主题：</label><input type="text" name="subject" value="${params.subject}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li class="clearfix"></li>
		<li><label>单据号：</label><input type="text" name="bizKey" value="${params.bizKey}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>发起人：</label><sys:treeselect id="initor" name="initor" value="${params.initor}" labelName="initorName" labelValue="${params.initorName}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-medium"/>
		</li>
		<li><label>流程状态：</label>  
			<select name="status" class="input-xlarge" style="width:100px;">
				<option value="">请选择</option>
				<c:forEach items="${fns:getDictList('flow_instanceStatus')}" var="type">
					<option value="${type.value}" <c:if test="${params.status eq type.value}">selected</c:if>>${ type.label}</option>
				</c:forEach>
			</select>              
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
    	<div id="doContinue" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doContinue();">恢复</a></div>
       	<div id="doTerminat" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doTerminat();">作废</a></div>
       	<div id="doDelete" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="doDelete();">删除</a></div>
    	<div id="updateVariables" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="updateVariables();">变量管理</a></div>
       	<div id="updateToken" class="btn-normal" data-scope=single style="display:inline-block;margin-left:5px;"><a href="#" onclick="updateToken();">令牌管理</a></div>
      	<div id="setHis" class="btn-normal" data-scope=multi style="display:inline-block;margin-left:5px;"><a href="#" onclick="setHis();">归档</a></div>
    </div>
	<table class="table table-striped table-bordered table-condensed fix-table">
		<thead>
			<tr>
				<th></th>
				<th>序号</th>
				<th>实例编号</th>
				<th>流程名称</th>
				<th>流程主题</th>
				<th>单据号</th>
				<th>发起时间</th>
				<th>修改时间</th>
				<th>发起人</th>
				<th>当前步骤</th>
				<th>流程状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="index">
				<tr>
					<td><input type="radio" name="checked" value="${model.processInstanceId}"></td>
					<td>${(index.index+1)+page.pageSize*(page.pageNo-1)}</td>
					<td>${model.processInstanceId}</td>
					<td>${model.processDefinitionName}</td>
					<td><a name="doTask" href="#"
					formUri="${model.formUri}" tii="${model.taskInstanceId}"
					pii="${model.processInstanceId}" bk="${model.bizKey}"
					pdk="${model.processDefinitionKey}">${model.subject}</a></td>
					<td>${model.bizKey}</td>
					<td><fmt:formatDate value="${model.startTime}" type="both"/></td>
					<td><fmt:formatDate value="${model.updateTime}" type="both"/></td>
					<td>${model.startAuthorName}</td>
					<td>${model.nowNodeInfo}</td>
					<td>
						${fns:getDictLabel(model.instanceStatus,'flow_instanceStatus','')}
					</td>
					<td><a name="flowGraph" href="#" pii="${model.processInstanceId}" pdk="${model.processDefinitionKey}">查看</a></td>				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>
