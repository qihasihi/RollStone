<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-29
  Time: 上午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="zsdx">
    <div class="timu">${resname} </div>
    <c:if test="${!empty replyList}">
        <c:forEach var="itm" items="${replyList}">
            <div class="over">
                <b><img src="images/pic01_140811.png" width="36" height="36"></b>
                <p class="title"><span>${itm.replyDate}</span>${itm.uName}</p>
                <c:if test="${!empty itm.replyAttach}">
                    <c:if test="${itm.attachType==1}">
                        <img src="${itm.attachType}" width="99" height="22">
                    </c:if>
                </c:if>
                <c:if test="${!empty itm.replyAttach}">
                    <c:if test="${itm.attachType==2}">
                        <img src="images/pic02_140811.png" width="99" height="22">
                    </c:if>
                </c:if>
                <c:if test="${empty itm.replyAttach}">
                    <p>${itm.replyDetail}</p>
                </c:if>
            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
