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
    <div class="subpage_head"><span class="ico55"></span><strong>批阅试卷—试题统计</strong></div>
    <div class="content1">
        <p><strong>试题序号：${idx}</strong></p>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w310"/>
            <col class="w310"/>
            <col class="w310"/>
            <tr>
                <th>学生</th>
                <th>是否批改</th>
                <th>得分</th>
            </tr>
            <c:if test="${!empty logs}">
                <c:forEach items="${logs}" var="itm">
                    <tr>
                        <td>${itm.stuname}</td>
                        <c:if test="${itm.ismarking==0}">
                            <td><span class="ico12" title="完成"></span></td>
                        </c:if>
                        <c:if test="${itm.ismarking==1}">
                            <td><span class="ico24" title="进行中"></span></td>
                        </c:if>
                        <td>
                           <c:if test="${itm.ismarking==1}">
                        <span class="ico24" title="进行中"></span>
                        </c:if>
                        <c:if test="${itm.ismarking==0}">
                        ${itm.score}
                        </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
        <br>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>
