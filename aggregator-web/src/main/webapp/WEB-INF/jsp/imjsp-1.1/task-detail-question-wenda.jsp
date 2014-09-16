<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-26
  Time: 下午6:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<%
    long mTime = System.currentTimeMillis();
    int offset = Calendar.getInstance().getTimeZone().getRawOffset();
    Calendar c = Calendar.getInstance();
    c.setTime(new Date(mTime - offset));
    String currentDay =UtilTool.DateConvertToString(c.getTime(),UtilTool.DateType.type1);
    System.out.println("当前页面"+"------------"+"task-detail-question-wenda.jsp"+"         当前位置------------进入jsp      当前时间------"+currentDay);
%>
<div class="zxcs_test">
    <h1>${type}</h1>
    <div class="title">${content}</div>
    <c:if test="${usertype eq 2}">
        <p class="jiexi"> 答案与解析</p>
        <div>${analysis}</div>
    </c:if>
    <c:if test="${!empty answer and !empty userRecord and quesType!=3 and quesType!=4}">
        <c:forEach items="${userRecord}" var="itm">
            <div class="wenda">
                <b><img src="${itm.uPhoto}" width="36" height="36"></b>
                <p class="title"><span>${itm.REPLYDATE}前</span>${itm.uName}</p>
                <p>${itm.REPLYDETAIL}</p>
            </div>
        </c:forEach>
    </c:if>
</div>
<%
    System.out.println("当前页面"+"------------"+"task-detail-question-wenda.jsp"+"         当前位置------------jsp结束      当前时间------"+currentDay);
%>
</body>
</html>
