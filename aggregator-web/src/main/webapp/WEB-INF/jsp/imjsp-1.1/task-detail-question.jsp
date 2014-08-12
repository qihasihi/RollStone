<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-11
  Time: 下午2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var content="${content}";
        content = content.replaceAll("");
    </script>
</head>
<body>
<div class="zxcs_test">
    <h1>${type}</h1>
    <div class="title">${content}</div>
    <c:if test="${!empty optionNum}">
        <ul class="daan">
            <c:forEach items="${optionNum}" var="itm">
                <li><span class="blue">${itm.OPTION_TYPE}、</span>${itm.CONTENT}
                    <p class="red">${itm.NUM}%<c:if test="${itm.ISRIGHT eq 1}">正确</c:if> <c:if test="${itm.ISRIGHT eq 0}">错误</c:if></p></li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${!empty option}">

        <ul class="daan">
            <c:forEach items="${option}" var="itm">
                <li><span class="blue">${itm.optiontype}、</span>${itm.content}
                    <c:if test="${!empty answer}">
                        <c:forEach items="${answer}" var="im">
                            <c:if test="${itm.optiontype eq im.answercontent and itm.isright==1}">
                                <b class="right"></b>
                            </c:if>
                            <c:if test="${itm.optiontype eq im.answercontent and itm.isright==0}">
                                <b class="wrong"></b>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty answer}">
                        <c:if test="${itm.isright==1}">
                            <b class="right"></b>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <p class="jiexi"><c:if test="${!empty rightNum}"><span>${rightNum}%答对</span></c:if> 答案与解析</p>
    <div>${analysis}</div>
</body>
</html>
