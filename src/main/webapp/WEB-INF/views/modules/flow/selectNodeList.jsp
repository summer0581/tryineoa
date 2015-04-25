<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>节点选择</title>
<meta name="decorator" content="default"/>
<script type="text/javascript"
	src="${ctxStatic}/flow/js/common.js"></script>
<style>
	.fix-table tr.odd {
	  background: #f7f7f7;
	}
	.fix-table tbody tr.selected { background-color: #fed4c7;}
	.fix-table tbody tr:HOVER { background-color: #fbf2ef;}
	.fix-table td{
		cursor:pointer;
	}
</style>
</head>
<body>
<div style="padding:8px;">

<div class="content">
	<table id="dataList" width="100%" class="table table-striped table-bordered table-condensed fix-table">
		<thead>
			<th>选择</th>
			<th>节点编号</th>
			<th>节点名称</th>
		</thead>
		<tbody>
			<c:forEach items="${result.dataList}" var="list" varStatus="index">
				<tr>
					<td><input type="radio" nodeId="${list.nodeId}" name="checked"/></td>
					<td>${list.nodeId}</td>
					<td>${list.nodeName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>
<script>
$(document).ready(function() {
	Fix.Util.ClickTr(null,false,true,0);
});
</script>
</body>

</html>