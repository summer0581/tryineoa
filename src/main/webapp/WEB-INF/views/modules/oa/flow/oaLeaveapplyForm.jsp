<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.row_border div{
			border:1px solid black;
		}
		.btn-normal {
		  display: inline-block;
		  margin: 8px 8px;
		 }
	</style>
	<script type="text/javascript"
	src="${ctxStatic}/flow/js/flowcommand.js"></script>
	<script type="text/javascript"
	src="${ctxStatic}/flow/js/flowautoassemble.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					//$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			var action = $("#action").val();
			var nodeId = $("#nodeId").val();
			var pdk = $("#processDefinitionKey").val();
			if("demoDoNext" == action){
				if(("UserTask_4" != nodeId && "tryine_bossdirectmanager_leave" == pdk) ||
						("UserTask_1" != nodeId && "tryine_leaveapply" == pdk ) ){
					$("input,textarea,select").attr("readonly","readonly");
					$(".Wdate").removeAttr("onclick");
					$("#officeButton").remove();
					$("#officeName").unbind("click");
					if("tryine_bossdirectmanager_leave" == pdk ){
						if("UserTask_1" == nodeId){
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_2" == nodeId){
							$("#chairManIdea").removeAttr("readonly");
						}
					}else if("tryine_leaveapply" == pdk){
						if("UserTask_2" == nodeId){
							$("#directLeaderIdea").removeAttr("readonly");
						}else if("UserTask_3" == nodeId){
							$("#generalManagerIdea").removeAttr("readonly");
						}else if("UserTask_4" == nodeId){
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_5" == nodeId){
							$("#chairManIdea").removeAttr("readonly");
						}
					}
				}else{
					$("#directLeaderIdea,#generalManagerIdea,#humanResourceIdea,#chairManIdea").attr("readonly","readonly");
				}

			}else if("demoCompleteTask" == action){
				$("#directLeaderIdea,#generalManagerIdea,#humanResourceIdea,#chairManIdea").attr("readonly","readonly");
			}
		});
	</script>
</head>
<body>

	<form:form id="inputForm" modelAttribute="oaLeaveapply" action="${ctx}/oa/oaLeaveapply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="container">
			<div class="row">
				<div class="text-center span12">
				<span class="help-inline"><font color="red">*</font> </span>
				<form:input path="type" htmlEscape="false" maxlength="20" class="input-mini required"/>假申请单</div>
			</div>
			<div class="">
				<div class="text-right span12">填表时间：<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium "
					value="<fmt:formatDate value="${oaLeaveapply.createDate}" pattern="yyyy-MM-dd"/>"/></div>
			</div>
		</div>
		<table class="table table-bordered">
			<tr class="">
				<td class="">姓名</td>
				<td class="" width="15%"><form:input path="name" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
				<td class="">部门</td>
				<td class="" ><sys:treeselect id="office" name="office.id" value="${oaLeaveapply.office.id}" labelName="office.name" labelValue="${oaLeaveapply.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small required" allowClear="true" notAllowSelectParent="true"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<td class="">岗位</td>
				<td class="" width="15%"><form:input path="post" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
				<td class="">联系电话</td>
				<td class=""><form:input path="telephone" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr class="">
				<td class="">转正否</td>
				<td class="" colspan=1><form:select path="isPositive">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select></td>
				<td class="">请假时间</td>
				<td class="" colspan=5><input name="starttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaLeaveapply.starttime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'});"/> 至 <input name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaLeaveapply.endtime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'});"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr class="">
				<td class="">岗位代理</td>
				<td class="" colspan=1><form:input path="postAgent" htmlEscape="false" maxlength="64" class="input-small "/>
				</td>
				<td class="">请假原因</td>
				<td class="" colspan=5><form:textarea path="reason" htmlEscape="false" maxlength="4000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr class="" style="">
				<td class="">直接</br>上级</br>意见</td>
				<td class=""><form:textarea path="directLeaderIdea" htmlEscape="false" maxlength="4000" class="input-small "/></td>
				<td class="">总经理</br>意见</td>
				<td class=""><form:textarea path="generalManagerIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/></td>
				<td class="">人资部</br>意见</td>
				<td class="" ><form:textarea path="humanResourceIdea" htmlEscape="false" maxlength="4000" class="input-small "/></td>
				<td class="">董事长</br>意见</td>
				<td class=""><form:textarea path="chairManIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/></td>
			</tr>
		</table>
		<div class="toolbar">
				<input type="hidden" id="action" name="action" value="${result.action }" />
				<input type="hidden" id="viewonly" name="viewonly" value="${result.viewonly }" />
				<input type="hidden" id="nodeId" name="nodeId" value="${result.nodeId }" />
				<input type="hidden" id="processDefinitionKey" name="processDefinitionKey" value="${result.processDefinitionKey }" />
				<c:forEach items="${result.commandList}" var="row"
					varStatus="status">
					<div class="btn-normal" id="btn_${status.index+1}"
						commandId="${row.id}" commandName="${row.name}"
						commandType="${row.type}" isAdmin="${row.isAdmin}"
						isVerification="${row.isVerification}"
						isSaveData="${row.isSaveData}"
						isSimulationRun="${row.isSimulationRun}" nodeId="${row.nodeId}"
						nodeName="${row.nodeName}">
						<a href="#" class="btn btn-primary">${row.name}</a>
					</div>
				</c:forEach>
			</div>
		
	</form:form>
</body>
</html>