<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.RoleUser"%>
<%
UserInfo user = (UserInfo)request.getAttribute("user");
%>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
   <col class="w100"/>
   <col class="w200"/>
  <col class="w450"/>
   <tr>
     <th>用&nbsp;户&nbsp;名：</th>
     <td><%=user.getUsername() %></td>
      <th rowspan="5" align="left">
      <span class="font_strong p_tb_10">用户头像：</span>
      <div class="yhqx_touxiang">
      <img src='<%=user.getHeadimage()!="images/defaultheadsrc.png"?user.getHeadimage():"" %>' onerror="this.src='images/defaultheadsrc.png'" alt="用户头像" width="135" height="135" /></div>      </th>
   </tr>
   <tr>
     <th>真实姓名：</th> 
     <td><%=user.getRealname()!=null&&!user.getRealname().trim().equals("")?user.getRealname():"无" %></td>
   </tr>
   <tr>
     <th>性&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
     <td><%=user.getUsersex() %></td>
   </tr>
   <tr>
     <th>出生日期：</th>
     <td><%=user.getBirthdate()!=null?user.getBirthdate().toString().substring(0, 10):"无" %></td>
   </tr>
   <tr>
     <th>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</th>
     <td colspan="2"><%=user.getMailaddress()!=null&&!user.getMailaddress().trim().equals("")?user.getMailaddress():"无" %></td>
   </tr>
   <tr>
     <th>角&nbsp;&nbsp;&nbsp;&nbsp;色：</th>
     <td>
	<c:if test="${roList==null}">无</c:if>
	<c:if test="${roList!=null}">
     <c:forEach var="ro" items="${roList }">
   		${ro.rolename}&nbsp;&nbsp;&nbsp;
 	</c:forEach>
 	</c:if>
	</td>
   </tr>
   <tr>
     <th>用户职务：</th>
     <td colspan="2">
     <c:if test="${juList==null}">无</c:if>
     <c:forEach var="ju" items="${juList }">
   		${ju.rolename}&nbsp;&nbsp;&nbsp;
 	</c:forEach>
     </td>
   </tr>
 </table>
