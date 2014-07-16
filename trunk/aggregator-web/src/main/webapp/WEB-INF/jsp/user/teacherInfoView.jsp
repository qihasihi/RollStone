<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.TeacherInfo"%>
<%@page import="com.school.entity.ClassUser"%>
<%@page import="com.school.entity.SubjectUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    TeacherInfo  teacher  = (TeacherInfo)request.getAttribute("teacher");
%>


<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
    <col class="w100"/>
    <col class="w760"/>
    <tr>
        <th>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
        <td><%=teacher.getTeachername()!=null&&!teacher.getTeachername().equals("")?teacher.getTeachername():"无" %></td>
    </tr>
    <tr>
        <th>性&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
        <td><%=teacher.getTeachersex()!=null&&!teacher.getTeachersex().equals("")?teacher.getTeachersex():"无" %></td>
    </tr>
    <tr>
        <th>电&nbsp;&nbsp;&nbsp;&nbsp;话：</th>
        <td><%=teacher.getTeacherphone()!=null&&!teacher.getTeacherphone().equals("")?teacher.getTeacherphone():"无" %></td>
    </tr>
    <tr>
        <th>教授科目：</th>
        <td>
            <% if(request.getAttribute("sublist") == null){   %>
            无
            <% }else{
                for(SubjectUser sub : ((List<SubjectUser>)request.getAttribute("sublist"))){ %>
            <%=sub.getSubjectname()%>&nbsp;&nbsp;&nbsp;
            <% }
            }%>
        </td>
    </tr>
    <tr>
        <th>班&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
        <td>
            <% if(request.getAttribute("class") == null){   %>
            无
            <% }else{
                for(ClassUser cu : ((List<ClassUser>)request.getAttribute("class"))){ %>
            <%=cu.getClassgrade() %><%=cu.getClassname() %>-<%=cu.getSubjectname() %>&nbsp;&nbsp;&nbsp;
            <%}}%>
        </td>
    </tr>
    <tr>
        <td colspan="2"></td>
    </tr>
</table>
