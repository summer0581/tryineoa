<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调休申请管理</title>
	<meta name="decorator" content="default"/>
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
				if(("UserTask_4" != nodeId && "tryine_bossdirectmanager_tiaoxiu" == pdk) ||
						("UserTask_1" != nodeId && "tryine_tiaoxiuapply" == pdk ) ){
					$("input,textarea,select").attr("readonly","readonly");
					$(".Wdate").removeAttr("onclick");
					$("#officeButton").remove();
					$("#officeName").unbind("click");
					if("tryine_bossdirectmanager_tiaoxiu" == pdk ){
						if("UserTask_1" == nodeId){
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_2" == nodeId){
							$("#chairManIdea").removeAttr("readonly");
						}
					}else if("tryine_tiaoxiuapply" == pdk){
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

	<form:form id="inputForm" modelAttribute="oaTiaoxiuapply" action="${ctx}/oa/oaTiaoxiuapply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group text-center">
			<h1>调休申请单</h1>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
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
					value="<fmt:formatDate value="${oaTiaoxiuapply.createDate}" pattern="yyyy-MM-dd"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否转正：</label>
			<div class="controls">
				<form:select path="isPositive">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">休息时间：</label>
			<div class="controls">
				<input id="restStarttime" name="restStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTiaoxiuapply.restStarttime}" pattern="yyyy-MM-dd HH:00:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',maxDate:'#F{$dp.$D(\'restEndtime\');}',isShowClear:false});"/> - 
				<input id="restEndtime" name="restEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTiaoxiuapply.restEndtime}" pattern="yyyy-MM-dd HH:00:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'#F{$dp.$D(\'restStarttime\');}',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">休息总小时：</label>
			<div class="controls">
				<form:input path="restHours" htmlEscape="false" maxlength="11" class="input-mini required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班时间：</label>
			<div class="controls">
				<input id="jiabanStarttime" name="jiabanStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanStarttime}" pattern="yyyy-MM-dd HH:00:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',maxDate:'#F{$dp.$D(\'jiabanEndtime\');}',isShowClear:false});"/> - 
				<input id="jiabanEndtime" name="jiabanEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanEndtime}" pattern="yyyy-MM-dd HH:00:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'#F{$dp.$D(\'jiabanStarttime\');}',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班总小时：</label>
			<div class="controls">
				<form:input path="jiabanHours" htmlEscape="false" maxlength="11" class="input-mini required digits "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位代理：</label>
			<div class="controls">
				<form:input path="postAgent" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">调休原因：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="4000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group text-center">
			<h1>审核批复</h1>
		</div>
		<div class="control-group">
			<label class="control-label">直接上级意见：</label>
			<div class="controls">
				<form:textarea path="directLeaderIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">总经理意见：</label>
			<div class="controls">
				<form:textarea path="generalManagerIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人资部意见：</label>
			<div class="controls">
				<form:textarea path="humanResourceIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">董事长意见：</label>
			<div class="controls">
				<form:textarea path="chairManIdea" htmlEscape="false" maxlength="4000" class="input-xlarge "/>
			</div>
		</div>

		<flow:initFlow flowInitMap="${result }"></flow:initFlow>
	</form:form>
</body>
</html>