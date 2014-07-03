<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-3
  Time: 下午2:18
  description:批阅统计
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
    试题序号：${idx}
<table>
    <tr>
        <th>学生姓名</th>
        <th>批阅情况</th>
        <th>得分</th>
    </tr>
    <c:if test="${!empty logs}">
        <c:forEach items="${logs}" var="itm">
            <tr>
                <td>${itm.stuname}</td>
                <td>${itm.ismarking}</td>
                <td>${itm.score}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
