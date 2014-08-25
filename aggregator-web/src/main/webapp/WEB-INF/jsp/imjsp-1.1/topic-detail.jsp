<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-25
  Time: 上午9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="hzjl">
    <h1>${topic.topictitle} </h1>
    <div class="black">${topic.topiccontent}</div>
    <c:if test="${!empty themeList}">
        <c:forEach items="${themeList}" var="itm">
            <div class="info">
                <b><img src="images/pic01_140811.png" width="36" height="36"></b>
                <p class="title"><span>${itm.C_TIME}</span>${itm.USER_NAME}</p>
                <div class="black">${itm.THEME_TITLE}</div>
                <c:if test="${itm.SOURCE_ID!=null and itm.SOURCE_ID!=1}">
                    <p>${itm.THEME_CONTENT}</p>
                </c:if>
                <c:if test="${itm.SOURCE_ID!=null and itm.SOURCE_ID==1}">
                    <c:if test="${itm.IM_ATTACH_TYPE eq 1}">
                        <img src="${itm.IM_ATTACH}" width="160" height="90" class="img">
                    </c:if>
                    <c:if test="${itm.IM_ATTACH_TYPE eq 2}">
                        <img src="${itm.IM_ATTACH}" width="160" height="90" class="img">
                    </c:if>
                </c:if>

            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
