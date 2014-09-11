<%@ page import="com.school.util.UtilTool" %>
<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-9-6
  Time: 下午4:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%  String proc_name=UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + UtilTool.utilproperty.getProperty("IP_ADDRESS")
            +"/"+proc_name + "/";%>
<script src="<%=basePath %>util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<div id="dv_coursescore_child">
<h1>课程积分－<c:if test="${!empty subObj}">${subObj.subjectname}</c:if></h1>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <c:if test="${!empty clsObj&&clsObj.dctype!=3}">
        <colgroup span="5" class="w120"></colgroup>
        <colgroup class="w150"></colgroup>
    </c:if>
    <c:if test="${empty clsObj||clsObj.dctype==3}">
        <colgroup span="3" class="w100"></colgroup>
        <colgroup class="w120"></colgroup>
        <colgroup span="2" class="w100"></colgroup>
        <colgroup class="w130"></colgroup>
    </c:if>
    <tr>
        <th>姓名</th>
        <th>任务完成率</th>
        <th>网上得分</th>
        <c:if test="${empty clsObj||clsObj.dctype==3}">
         <th>网下表现得分</th>
        </c:if>
        <th>小组得分</th>
        <th>课程积分</th>
        <th>积分排行榜<a href="javascript:;" onclick="loadCourseScore(${param.subjectid},${param.classid},'${param.termid}',1)" class="ico48a"></a><a href="javascript:;" onclick="loadCourseScore(${param.subjectid},${param.classid},'${param.termid}',2)"  class="ico48b"></a></th>
    </tr>
    <c:if test="${!empty dataMapList}">
           <c:forEach items="${dataMapList}" var="dm" varStatus="dmIdx">
               <tr class="${dmIdx.index%2==0?"trbg1":""}">
                   <td><a href="task?m=tostuSelfPerformance&termid=${param.termid}&subjectid=${param.subjectid}&userid=${dm.USER_ID}" target="_blank">${dm.STU_NAME}</a></td>
                   <td>${dm.COMPLATETASKBL}%</td>
                   <td>${dm.WSSCORE}</td>
                   <c:if test="${empty clsObj||clsObj.dctype==3}">
                     <td>${dm.WXSCORE}</td>
                   </c:if>
                   <td>${dm.GROUP_SCORE}</td>
                   <td>${dm.COURSE_TOTAL_SCORE}</td>
                   <td>${dm.RANK}</td>
               </tr>
           </c:forEach>
    </c:if>
</table>
</div>