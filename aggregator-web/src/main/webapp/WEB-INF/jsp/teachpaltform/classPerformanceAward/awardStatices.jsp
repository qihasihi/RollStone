<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-9-2
  Time: 下午5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8"/>
</head>

<body>
<div class="subpage_head"><span class="ico19"></span><strong>课程积分</strong></div>
<div class="content1">
    <p class="t_r">总积分：<span class="font-red">600</span>&nbsp;&nbsp;&nbsp;任务数：<span class="font-red">20</span>&nbsp;&nbsp;&nbsp;出勤数：<span class="font-red">10</span></p>
   <c:if test="${clsObj.dctype==3}">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup span="2" class="w130"></colgroup>
        <colgroup class="w140"></colgroup>
        <colgroup span="2" class="w130"></colgroup>
        <colgroup class="w150"></colgroup>
        <tr>
            <th>姓名</th>

            <th>网上得分</th>
            <th>网下表现得分</th>
            <th>小组得分</th>
            <th>课程积分</th>
            <th>积分排行榜<a href="1" class="ico48a"></a><a href="1" class="ico48b"></a></th>
        </tr>
        <c:if test="${!empty dataList}">
               <c:forEach items="${dataList}" var="d" varStatus="dIdx">
                   <tr class="${dIdx.index%2==0?'trbg1':''}" id="tr_${d.USER_ID}">
                       <td><a href="1" target="_blank">${d.STU_NAME}</a></td>
                       <td>${!empty d.WSDF?d.WSDF:'--'}</td>
                       <td>${!empty d.WXDF?d.WXDF:'--'}</td>
                       <td>${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}</td>
                       <td>${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}</td>
                       <td>${d.RNUM}</td>
                   </tr>
               </c:forEach>
        </c:if>
    </table>
   </c:if>
<c:if test="${empty clsObj.dctype||clsObj.dctype!=3}">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup span="4" class="w160"></colgroup>
        <colgroup class="w190"></colgroup>
        <tr>
            <th>姓名</th>
            <th>网上得分</th>
            <th>小组得分</th>
            <th>课程积分</th>
            <th>积分排行榜<a href="1" class="ico48a"></a><a href="1" class="ico48b"></a></th>
        </tr>
        <c:if test="${!empty dataList}">
            <c:forEach items="${dataList}" var="d" varStatus="dIdx">
                <tr  class="${dIdx.index%2==0?'trbg1':''}" id="tr_${d.USER_ID}">
                    <td><a href="1" target="_blank">${d.STU_NAME}</a></td>
                    <td>${!empty d.WSDF?d.WSDF:'--'}</td>
                    <%--<td>${!empty d.WXDF?d.WXDF:'--'}</td>--%>
                    <td>${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}</td>
                    <td>${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}</td>
                    <td>${d.RNUM}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    </c:if>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
