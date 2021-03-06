<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调休申请管理</title>
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
				if(("UserTask_4" != nodeId && "tryine_bossdirectmanager_tiaoxiu" == pdk) ||
						("UserTask_1" != nodeId && "tryine_tiaoxiuapply" == pdk ) ){
					$("input,textarea,select").attr("readonly","readonly");
					$(".Wdate").removeAttr("onclick");
					$("#officeButton").remove();
					$("#officeName").unbind("click");
					if("tryine_bossdirectmanager_tiaoxiu" == pdk ){
						if("UserTask_1" == nodeId){//人资总监
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_2".equals(nodeId)||"UserTask_7".equals(nodeId)){//集团总经理
							$("#generalManagerIdea").removeAttr("readonly");
						}else if("UserTask_5".equals(nodeId)){//董事长
							$("#chairManIdea").removeAttr("readonly");
						}
					}else if("tryine_tiaoxiuapply" == pdk){
						if("UserTask_2" == nodeId){//部门领导审核
							$("#directLeaderIdea").removeAttr("readonly");
						}else if("UserTask_3" == nodeId){//分公司总经理
							$("#branchLeaderIdea").removeAttr("readonly");
						}else if("UserTask_4" == nodeId){//人资总监
							$("#humanResourceIdea").removeAttr("readonly");
						}else if("UserTask_7".equals(nodeId)||"UserTask_8".equals(nodeId)){//集团总经理
							$("#generalManagerIdea").removeAttr("readonly");
						}else if("UserTask_5" == nodeId){//董事长
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
		
		function openLeaveDetail(){
			var url = "${ctx}/oa/oaJiabanapply/jiabanDayList?createBy.id="+$("#createBy_id").val();
			windowOpen(url,"",screen.width-300,screen.height-50);
		}
		
	</script>
</head>
<body>

	<form:form id="inputForm" modelAttribute="oaTiaoxiuapply" action="${ctx}/oa/oaTiaoxiuapply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="createBy_id" name="createBy.id" value="${oaTiaoxiuapply.createBy.id }"/>
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
					value="<fmt:formatDate value="${oaTiaoxiuapply.restStarttime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({minDate:'%y-#{%M-1}-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'restEndtime\');}',isShowClear:false});"/> - 
				<input id="restEndtime" name="restEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaTiaoxiuapply.restEndtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'restStarttime\');}',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<input type="button" value="查看加班详情" class="btn btn-primary" onclick="openLeaveDetail()"></input>
				<span class="help-inline"><font color="red">还剩余调休：</font>${canTiaoxiuHours }小时 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">休息总小时：</label>
			<div class="controls">
				<form:input path="restHours" htmlEscape="false" maxlength="11" class="input-mini required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">加班时间：</label>
			<div class="controls">
				<input id="jiabanStarttime" name="jiabanStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanStarttime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'jiabanEndtime\');}',isShowClear:false});"/> - 
				<input id="jiabanEndtime" name="jiabanEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanEndtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'jiabanStarttime\');}',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班总小时：</label>
			<div class="controls">
				<form:input path="jiabanHours" htmlEscape="false" maxlength="11" class="input-mini required number "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">岗位代理：</label>
			<div class="controls">
				<form:input path="postAgent" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">调休原因：</label>
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
		<div class="control-group">
			<label class="control-label">集团总经理意见：</label>
			<div class="controls">
				<form:textarea path="generalManagerIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">集团董事长意见：</label>
			<div class="controls">
				<form:textarea path="chairManIdea" htmlEscape="false" maxlength="2000" class="input-xlarge "/>	
			</div>
		</div>
		<div><span style="color:#317EB3;">*左键双击输入框快速输入“同意/不同意”</span></div>
		<flow:initFlow flowInitMap="${result }"></flow:initFlow>
	</form:form>
</body>
</html>