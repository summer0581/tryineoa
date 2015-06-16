<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出差申请管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/common.js?version=${jsversion}" type="text/javascript"></script>
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
						if("UserTask_1" == nodeId){//人力资源审核
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_2" == nodeId){//董事长审核
							$("#chairManIdea").removeAttr("readonly");
						}else if("UserTask_5".equals(nodeId)||"UserTask_6".equals(nodeId)){//集团总经理
							$("#generalManagerIdea").removeAttr("readonly");
						}
					}else if("tryine_travelapply" == pdk && "UserTask_1" != nodeId){
						if("UserTask_2" == nodeId){//部门领导审核
							$("#directLeaderIdea").removeAttr("readonly");
						}else if("UserTask_3" == nodeId){//分公司总经理
							$("#branchLeaderIdea").removeAttr("readonly");
						}else if("UserTask_4" == nodeId){//人资总监
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_5".equals(nodeId)||"UserTask_8".equals(nodeId)){//集团总经理
							$("#generalManagerIdea").removeAttr("readonly");
						}else if("UserTask_7" == nodeId){//董事长
							$("#chairManIdea").removeAttr("readonly");
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
		<div class="control-group text-center">
			<h1>外出申请单</h1>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${oaTravelapply.office.id}" labelName="office.name" labelValue="${oaTravelapply.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small required" allowClear="true" notAllowSelectParent="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位：</label>
			<div class="controls">
				<form:input path="post" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-small required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外出时间：</label>
			<div class="controls">
				<input id="outTime" name="outTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTravelapply.outTime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({minDate:'%y-#{%M-1}-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:'#F{$dp.$D(\'plantobacktime\');}',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计返还时间：</label>
			<div class="controls">
				<input id="plantobacktime" name="plantobacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTravelapply.plantobacktime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'#F{$dp.$D(\'outTime\');}',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">随行人员：</label>
			<div class="controls">
				<form:input path="joinPeople" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外出地点：</label>
			<div class="controls">
				<form:input path="outPlace" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户姓名：</label>
			<div class="controls">
				<form:input path="customerInfo" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户电话：</label>
			<div class="controls">
				<form:input path="customerTelephone" htmlEscape="false" maxlength="64" class="input-xlarge required mobile"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外出事由：</label>
			<div class="controls">
				<form:textarea path="outReason" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<h1 class="text-center">审核批复</h1>
		</div>
		<c:if test="${'tryine_travelapply' eq result.processDefinitionKey}">
		<div class="control-group">
			<label class="control-label">直接上级意见：</label>
			<div class="controls">
				<form:textarea path="directLeaderIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分公司总经理意见：</label>
			<div class="controls">
				<form:textarea path="branchLeaderIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">人资总监意见：</label>
			<div class="controls">
				<form:textarea path="humanResourceIdea" htmlEscape="false"  maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">集团总经理意见：</label>
			<div class="controls">
				<form:textarea path="generalManagerIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">董事长意见：</label>
			<div class="controls">
				<form:textarea path="chairManIdea" htmlEscape="false"  maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div><span style="color:#317EB3;">*左键双击输入框快速输入“同意/不同意”</span></div>


		<flow:initFlow flowInitMap="${result }"></flow:initFlow>
	</form:form>
</body>
</html>