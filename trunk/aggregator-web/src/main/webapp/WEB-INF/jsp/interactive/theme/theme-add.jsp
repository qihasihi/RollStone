<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="js/interactive/theme.js"></script> 
		
	</head>
  
  <body>   
  <div class="subpage_head"><span class="ico55"></span><strong>
  <c:if test="${!empty theme}">
  	编辑主贴  	
  </c:if>
  <input type="hidden" value="${theme.themeid}" name="themeid" id="themeid"/>
  <c:if test="${empty theme}">
  	新建主帖
  </c:if>  
  </strong></div>
<div class="content1">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
    <col class="w100"/>
    <col class="w700"/>
    <tr>
      <th><span class="ico06"></span>标&nbsp;&nbsp;题：</th>
      <td><input name="themetitle" value="${theme.themetitle }" id="themetitle" type="text" class="w350" /></td>
    </tr>
    <tr>
      <th><span class="ico06"></span>内&nbsp;&nbsp;容：</th>
      <td><textarea name="themecontent" id="themecontent" class="h210 w650">${theme.themecontent }</textarea></td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td><a href="javascript:;" onclick="operateColumns()" class="an_small">确&nbsp;定</a><a href="1" target="_blank" class="an_small">取&nbsp;消</a></td>
    </tr>
  </table>
  <br>
</div>
  <%@include file="/util/foot.jsp" %>
</body>
</html>
