<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-3
  Time: 上午10:00
  desctiption: 批阅试卷 主页
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title>批阅试卷主页</title>
</head>
<body>
    <table>
        <c:if test="${!empty questionList}">
            <tr><td colspan="5">${papername}</td></tr>
            <tr height="100">
                <c:forEach items="${questionList}" var="itm" varStatus="idx">

                        <td width="100" style="text-align: center">${itm.orderidx} &nbsp;&nbsp; ${itm.markingnum}/${itm.submitnum}</td>
                    <c:if test="${(idx.index+1)%5==0}">
                      </tr><tr height="100">
                    </c:if>

                </c:forEach>
            </tr>
        </c:if>
    </table>
</body>
</html>
