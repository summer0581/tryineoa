<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出差申请管理</title>
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
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
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
			if(("UserTask_4" != nodeId && "tryine_bossdirectmanager_travel" == pdk) ||
					("UserTask_1" != nodeId && "tryine_travelapply" == pdk ) ){
				if("demoDoNext" == action ){
					$("input,textarea").attr("readonly","readonly");
					$(".Wdate").removeAttr("onclick");
					$("#officeButton").remove();
					$("#officeName").unbind("click");
						if("tryine_bossdirectmanager_travel" == pdk && "UserTask_4" != nodeId){
							if("UserTask_1" == nodeId){
								$("#directleaderIdea").removeAttr("readonly");
							}else if("UserTask_2" == nodeId){
								$("#generalManagerIdea").removeAttr("readonly");
							}
						}else if("tryine_travelapply" == pdk && "UserTask_1" != nodeId){
							if("UserTask_2" == nodeId){
								$("#directleaderIdea").removeAttr("readonly");
							}else if("UserTask_3" == nodeId){
								$("#generalManagerIdea").removeAttr("readonly");
							}
						}
					}
			}else{
				$("#directleaderIdea,#generalManagerIdea").attr("readonly","readonly");
			}
			if("demoCompleteTask" == action){
				$("#directleaderIdea,#generalManagerIdea").attr("readonly","readonly");
			}
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="oaTravelapply" action="${ctx}/oa/oaTravelapply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="container">
			<div class="row">
				<div class="text-center span12"><h1>外出申请单</h1></div>
			</div>
			<div class="row">
				<div class="text-right span12">填表时间：<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium "
					value="<fmt:formatDate value="${oaTravelapply.createDate}" pattern="yyyy-MM-dd"/>"/></div>
			</div>
		</div>		
		<table class="table table-bordered">
			<tr class="">
				<td class="" width="10%">姓名</td>
				<td class=""><form:input path="name" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="" width="10%">部门</td>
				<td class="" ><sys:treeselect id="office" name="office.id" value="${oaTravelapply.office.id}" labelName="office.name" labelValue="${oaTravelapply.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small required" allowClear="true" notAllowSelectParent="true"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td class="">岗位</td>
				<td class=""><form:input path="post" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
				<td class="">联系电话</td>
				<td class=""><form:input path="telephone" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td>外出时间</td>
				<td ><input name="outTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTravelapply.outTime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
				<td>预计返还时间</td>
				<td ><input name="plantobacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTravelapply.plantobacktime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td >随行人员</td>
				<td ><form:input path="joinPeople" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
				<td>外出地点</td>
				<td colspan=""><form:input path="outPlace" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td>客户姓名</td>
				<td ><form:input path="customerInfo" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
				<td>客户电话</td>
				<td><form:input path="customerTelephone" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td  >外出事由</td>
				<td colspan="3"><form:textarea path="outReason" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr>
				<td colspan="4" ><h1 class="text-center">审核批复</h1></td>
			</tr>
			<c:if test="${'tryine_travelapply' eq result.processDefinitionKey}">
				<tr>
					<td>直接</br>上级</td>
					<td ><form:textarea path="directleaderIdea" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge "/></td>
					<td>分公司</br>总经理</td>
					<td ><form:textarea path="generalManagerIdea" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge "/></td>
				</tr>
			</c:if>
			<c:if test="${'tryine_bossdirectmanager_travel' eq result.processDefinitionKey}">
			<tr>
				<td>人资总监</br>意见</td>
				<td ><form:textarea path="directleaderIdea" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge "/></td>
				<td>董事长</br>意见</td>
				<td ><form:textarea path="generalManagerIdea" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge "/></td>
			</tr>
			</c:if>
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