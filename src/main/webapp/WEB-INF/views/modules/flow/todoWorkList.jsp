<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程代办任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
			
		  	$("a[name=flowGraph]").click(function(){
			    var pdk = $(this).attr("pdk");
			    var pii = $(this).attr("pii");
			    var obj = {};
			    var aw = screen.width-300;
			    var ah = screen.height-100;
			    	loc_x=(screen.availWidth-aw)/2;
			    	loc_y=(screen.availHeight-ah)/2;
			    window.open ("${ctx}/flow/work/getTaskDetailInfo?processDefinitionKey="+pdk+"&processInstanceId="+pii, "_blank",
			    "height=" + ah + ", width=" + aw + ",toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no,top=" + loc_y + ",left=" + loc_x + "")

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
		        url+="taskId="+tii+"&processInstanceId="+pii+"&id="+bizKey+"&processDefinitionKey="+pdk,obj,"dialogWidth=800px;dialogHeight=600px";
		       	location = url;
		      });

		});
		function page(n,s){
        	location = '${ctx}/flow/work/?pageNo='+n+'&pageSize='+s;
        }
		

	</script>

</head>
<body>
	<form id="searchForm" action="${ctx}/flow/work/" method="post" class="breadcrumb form-search">
		<li><label>流程名称：</label>
		<input type="text" name="processName" value="${params.processName}" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>发起人：</label><sys:treeselect id="initor" name="initor" value="${params.initor}" labelName="initorName1" labelValue="${params.initorName1}"
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
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<input class="btn btn-primary" type="button" onclick="clearInfo();" value="重置"/>	
	</form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>流程名称</th>
				<th>任务主题</th>
				<th>发起人</th>
				<th>发起时间</th>
				<th>当前步骤</th>
				<th>到达时间</th>
				<th>流程状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="index">
				<tr>
					<td>${(index.index+1)+page.pageSize*(page.pageNo-1)}</td>
					<td>${model.processDefinitionName}</td>
					<td><a name="doTask" href="#"
										formUri="${model.formUri}" tii="${model.taskInstanceId}"
										pii="${model.processInstanceId}" bk="${model.bizKey}"
										pdk="${model.processDefinitionKey}">${model.description}</a></td>
					<td>${model.userRealName}</td>
					<td><fmt:formatDate value="${model.PI_START_TIME}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.nodeName}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><a href="#" name="flowGraph" pdk="${model.processDefinitionKey}" pii="${model.processInstanceId}">查看</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
