<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-9-4
  Time: 下午2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title>移动端任务统计</title>
</head>
<body>
    <p><span>${taskinfo.imtaskcontent}</span></p>
    <c:if test="${!empty taskinfo.imtaskattach}">

    </c:if>
    <p><span>${taskinfo.imtaskanalysis}</span></p>
<hr style="width: 900px">
    <c:if test="${!empty replyList}">
        <c:forEach items="${replyList}" var="itm">
            <p>
                <span><img src="${itm.uPhoto}"/></span>&nbsp;&nbsp;&nbsp;<span>${itm.uName}</span><br>
                <span>${itm.replyDetail}</span>
            </p>
        </c:forEach>
    </c:if>
</body>
</html>
