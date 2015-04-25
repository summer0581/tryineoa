<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
</head>

<body>
<form method="post" id="form" action="FlowManager">
<div class="popup">
    <table width="100%" class="fix-table">
			<thead>
                <th width="30">&nbsp;</th>
                <th width="">令牌编号</th>
                <th width="">令牌名称</th>
                <th>开始时间</th>
                <th width="">节点进入时间</th>
                <th width="">节点编号</th>
                <th width="">流程实例编号</th>
                <th width="">父令牌编</th>
             </thead>
		    <c:forEach items="${result.dataList}" var="dataList" varStatus="index">
		    <tr>
		      <td class="num"><c:out value="${index.index+1}"/></td>
		      <td>${dataList.tokenId}</td>
		      <td>${dataList.name}</td>
		      <td class="time"><fmt:formatDate value="${dataList.startTime}" type="both"/></td>
		      <td class="time"><fmt:formatDate value="${dataList.nodeEnterTime}" type="both"/></td>
		      <td>${dataList.nodeId}</td>
		      <td>${dataList.parentTokenId}</td>
		    </tr>
		    </c:forEach>
      </table>
      </div>
</form>
</body>
<script type="text/javascript">

</script>
</html>