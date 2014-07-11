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
    <div class="subpage_head"><span class="ico55"></span><strong>批阅试卷</strong></div>
    <div class="content1">
        <div class="jxxt_zhuanti_rw_piyue">
            <h2>${papername}</h2>
            <h3>注：客观题系统已自动完成批改</h3>
            <ul class="shiti">
                <c:if test="${!empty questionList}">
                    <c:forEach items="${questionList}" var="itm" varStatus="idx">
                        <c:if test="${itm.markingnum==itm.submitnum}">
                            <li class="over"><a href="1">${itm.orderidx}<b>${itm.markingnum}/${itm.submitnum}</b></a></li>
                        </c:if>
                        <c:if test="${itm.markingnum!=itm.submitnum}">
                            <li><a href="1">${itm.orderidx}<b>${itm.markingnum}/${itm.submitnum}</b></a></li>
                        </c:if>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>
