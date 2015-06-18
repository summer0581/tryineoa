<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加班申请管理</title>
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
			if("demoDoNext" == action){
				if(("UserTask_4" != nodeId && "tryine_bossdirectmanager_jiaban" == pdk) ||
						("UserTask_1" != nodeId && "tryine_jiabanapply" == pdk ) ){
					$("input,textarea,select").attr("readonly","readonly");
					$("input[type='radio']").attr("disabled","disabled");
					$(".Wdate").removeAttr("onclick");
					$("#officeButton").remove();
					$("#officeName").unbind("click");
					if("tryine_bossdirectmanager_jiaban" == pdk ){
						if("UserTask_1" == nodeId){
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_2" == nodeId){
							$("#chairManIdea").removeAttr("readonly");
						}
					}else if("tryine_jiabanapply" == pdk){
						if("UserTask_2" == nodeId){
							$("#directLeaderIdea").removeAttr("readonly");
						}else if("UserTask_3" == nodeId){
							$("#branchLeaderIdea").removeAttr("readonly");
						}else if("UserTask_4" == nodeId){
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_5" == nodeId){
							$("#chairManIdea").removeAttr("readonly");
						}
					}
				}else{
					$("#directLeaderIdea,#generalManagerIdea,#branchLeaderIdea,#humanResourceIdea,#chairManIdea").attr("readonly","readonly");
				}

			}else if("demoCompleteTask" == action){
				$("#directLeaderIdea,#generalManagerIdea,#branchLeaderIdea,#humanResourceIdea,#chairManIdea").attr("readonly","readonly");
			}
		});
	</script>
</head>
<body>

	<form:form id="inputForm" modelAttribute="oaJiabanapply" action="${ctx}/oa/oaJiabanapply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group text-center">
			<h1>加班申请单</h1>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${oaJiabanapply.office.id}" labelName="office.name" labelValue="${oaJiabanapply.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位：</label>
			<div class="controls">
				<form:input path="post" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填报日期：</label>
			<div class="controls">
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium "
					value="<fmt:formatDate value="${oaJiabanapply.createDate}" pattern="yyyy-MM-dd"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班时间：</label>
			<div class="controls">
				<input id="starttime" name="starttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaJiabanapply.starttime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({minDate:'%y-#{%M-1}-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:'#F{$dp.$D(\'endtime\');}',isShowClear:false});"/> -
				<input id="endtime" name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaJiabanapply.endtime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'#F{$dp.$D(\'starttime\');}',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总小时：</label>
			<div class="controls">
				<form:input path="hours" htmlEscape="false" maxlength="11" class="input-mini required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班时段：</label>
			<div class="controls">
				<form:radiobuttons path="shiduan" items="${fns:getDictList('jiaban_shiduan')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班地点：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group text-center">
			<h1>审核批复</h1>
		</div>
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
		<div class="control-group">
			<label class="control-label">人力行政中心意见：</label>
			<div class="controls">
				<form:textarea path="humanResourceIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div><span style="color:#317EB3;">*左键双击输入框快速输入“同意/不同意”</span></div>
		<!-- <div class="control-group">
			<label class="control-label">董事长意见：</label>
			<div class="controls">
				<form:textarea path="chairManIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div> -->
		<flow:initFlow flowInitMap="${result }"></flow:initFlow>
	</form:form>
</body>
</html>