<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程我参与的任务</title>
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
			    //window.open("${ctx}/flow/work/getTaskDetailInfo?processDefinitionKey="+pdk+"&processInstanceId="+pii,"_blank");
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
		        url+="viewonly=true&taskId="+tii+"&processInstanceId="+pii+"&id="+bizKey+"&processDefinitionKey="+pdk,obj,"dialogWidth=800px;dialogHeight=600px";
		       	location = url;
		      });

		});
		function page(n,s){
        	location = '${ctx}/flow/work/?pageNo='+n+'&pageSize='+s;
        }
		

	</script>

</head>
<body>
	<form id="searchForm" action="${ctx}/flow/work/allMyJoinWorkList" method="post" class="breadcrumb form-search">
		<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>流程名称</th>
				<th>流程主题</th>
				<c:if test="${result.processType != 'initor'}"><th>发起人</th></c:if>
				<th>发起时间</th>
				<th>修改时间</th>
				<th>当前步骤</th>
				<th>流程状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="index">
				<tr>
					<td>${(index.index+1)+page.pageSize*(page.pageNo-1)}</td>
					<td><a name="doTask" href="#"
										formUri="${model.formUri}" tii="${model.taskInstanceId}"
										pii="${model.processInstanceId}" bk="${model.bizKey}"
										pdk="${model.processDefinitionKey}">${model.processDefinitionName}</a></td>
					<td>${model.subject}</td>
					<c:if test="${result.processType != 'initor'}"><td>${model.startAuthorRealName}</td></c:if>
					<td><fmt:formatDate value="${model.startTime}" type="both"/></td>
					<td><fmt:formatDate value="${model.updateTime}" type="both"/></td>
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
