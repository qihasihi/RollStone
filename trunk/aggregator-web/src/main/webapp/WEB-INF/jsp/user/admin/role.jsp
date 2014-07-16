<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@page import="com.school.entity.TeacherInfo"%>
<%@page import="com.school.entity.ClassUser"%>
<%@page import="com.school.entity.SubjectUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${!empty identityList}">
	<c:forEach items="${identityList }" var="i">
		<option value="${i.roleid }">${i.rolename }</option>
	</c:forEach>
</c:if>