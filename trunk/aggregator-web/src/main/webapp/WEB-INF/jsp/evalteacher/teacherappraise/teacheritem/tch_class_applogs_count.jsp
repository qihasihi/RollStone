<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/util/common.jsp" %> 
<%@ page import="com.school.entity.TeacherInfo"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
<title>数字化校园</title>
</head>

<body>
<%@include file="/util/head.jsp" %>

<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
 <p class="pj_title">教师各评价项得分列表</p>
 <p class="font14 p_b_10">&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${dataList !=null}">${dataList[0].TEACHER_NAME}&nbsp;&nbsp;${dataList[0].CLASSNAME}</c:if>
</p>
  <div class="p_20">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <col class="w760"/>
    <col class="w200"/>
    <tr>
        <th>评价题目</th>
        <th>平均得分(百分制)</th>
      </tr>
     <c:forEach items="${dataList}" var="item" varStatus="stauts">
      <tr>
        <td>${item.NAME}</td>
        <td>${item.SCORE}</td>
      </tr>
      </c:forEach>
      
  </table>
</div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
