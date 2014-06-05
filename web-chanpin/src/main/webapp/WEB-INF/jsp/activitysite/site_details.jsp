<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>



  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'site_details.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body><%@include file="/util/head.jsp"  %>   
	  <div class="jxpt_layout">
	   <%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon06"><span><a href="activity?m=list">返回</a></span>活动详情</p>
  <div class="p_20">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a zt14">
    <col class="w100" />
    <col class="w760" />
    		<tr>
    			<th>
    				场地名称:
    			</th>
    			<td>
    				${siteinfo.sitename }
    			</td>
    		</tr>
    		
    		<tr>
    			<th>
    				场地位置:
    			</th>
    			<td  >
    				${siteinfo.siteaddress }
    			</td>
    		</tr>
    		<tr >
    			<th>
    				容纳人数:
    			</th>
    			<td >
    				${siteinfo.sitecontain }
    			</td>
    		</tr>
    		<tr >
    			<th>
    				场地简介:
    			</th>
    			<td >
    				${siteinfo.baseinfo }
    			</td>
    		</tr>
    		<tr >
    			<th>
    				使用情况:
    			</th>
    			<td >
    				<c:if test="${!empty activityList}">
	    				<c:forEach var="item" items="${activityList}">
	    					${item.atname }&nbsp;&nbsp;${item.begintimestring }~${item.endtimestring }<br/>
	    				</c:forEach>
    				</c:if>
    				<c:if test="${empty activityList}">
    					暂无信息
    				</c:if>
    			</td>
    		</tr>
    		<tr>
      <th>&nbsp;</th>
      <td><a href="javascript:window.close()" class="an_blue">关&nbsp;闭</a></td>
    </tr>
    	</table>
    	</div><%@include file="/util/foot.jsp"  %>
  </body>

