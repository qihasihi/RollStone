<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.school.entity.StudentInfo"%>
<%@page import="com.school.entity.ClassInfo"%>
<%
	StudentInfo stu = (StudentInfo)request.getAttribute("student");
ClassInfo cla = (ClassInfo)request.getAttribute("class");
%>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
  <tr>
    <th>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
    <td><%= stu.getStuname() %></td>
  </tr>
  <tr>
    <th>学&nbsp;&nbsp;&nbsp;&nbsp;号：</th>
    <td><%= stu.getStuno() %></td>
  </tr>
  <tr>
    <th> 性&nbsp;&nbsp;&nbsp;&nbsp;别： </th>
    <td><%= stu.getStusex()==null?"未填写":stu.getStusex() %></td> 
  </tr>
  <tr>
    <th>班&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td>
    <c:forEach var="cla" items="${classes}">
   		${cla.classinfo.classgrade}${cla.classinfo.classname}&nbsp;&nbsp;&nbsp;
 	</c:forEach>
      </td>
  </tr>
  <tr>
    <th>监&nbsp;护&nbsp;人：</th>
    <td></td>
  </tr>
  <tr>
    <th>监护电话：</th>
    <td></td>
  </tr>
  <tr>
    <th>家庭住址：</th>
    <td></td>
  </tr>
</table>
