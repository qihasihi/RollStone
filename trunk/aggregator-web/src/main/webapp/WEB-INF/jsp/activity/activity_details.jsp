<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>


  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'activity_details.jsp' starting page</title>
    
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
    				活动名称:
    			</th>
    			<td >
    				${activity.atname }
    			</td>
    		</tr>
    		<tr >
    			<th>
    				活动时间:
    			</th>
    			<td >
    				${activity.begintimestring }~${activity.endtimestring }
    			</td>
    			
    		</tr>
    		 <tr>
      <th>发&nbsp;起&nbsp;人：</th>
      <td>${activity.username }</td>
    </tr>
    		<tr >
    			<th>
    				电教器材:
    			</th>
    			<td >
    				${activity.audiovisual }
    			</td>
    		</tr>
    		<tr >
    			<th >
    				活动场地:
    			</th>
    			<td width="80%" colspan="3" align="left">
    				<c:forEach var="item" items="${activity.siteinfo}">
    					<b>${item.sitename }</b>&nbsp;&nbsp;
    				</c:forEach>
    			</td>
    		</tr>
    		<tr >
    			<th >
    				邀请的人:
    			</th>
    			<td >
    				<c:if test="${activity.issign==1}">
	    				<c:if test="${!empty activity.activityuserinfo}">
	    					<c:forEach var="item" items="${activity.activityuserinfo}">
	    					<b>${item.realname }</b>&nbsp;&nbsp;
	    					</c:forEach>
	    				</c:if>
	    				<c:if test="${empty activity.activityuserinfo}">
	    					暂无
	    				</c:if>
    				</c:if>
    			</td>
    		</tr>
    		<tr  >
    			<th >
    				 确认参加:
    			</th>
    			<td >
    				<c:if test="${!empty yuser}">
    					<c:forEach var="item" items="${yuser}">
    					<b>${item.realname }</b>&nbsp;&nbsp;
    					</c:forEach>
    				</c:if>
    				
    				<c:if test="${activity.issign==0}">
    					<c:if test="${!empty activity.activityuserinfo}">
	    					<c:forEach var="item" items="${activity.activityuserinfo}">
	    					<b>${item.realname }</b>&nbsp;&nbsp;
	    					</c:forEach>
	    				</c:if>
    				</c:if>
    				
    				<c:if test="${empty yuser}">
    					<c:if test="${activity.issign==0}">
    						<c:if test="${empty activity.activityuserinfo}">
    						         暂无
    						</c:if>    						
    					</c:if> 
    					<c:if test="${activity.issign==1}"  >
    						暂无
    					</c:if>					
    				</c:if>
    			</td>
    		</tr>
    		<tr >
    			<th >
    				可能参加:
    			</th>
    			<td >
    				<c:if test="${!empty yornuser}">
    					<c:forEach var="item" items="${yornuser}">
    					<b>${item.realname }</b>&nbsp;&nbsp;
    					</c:forEach>
    				</c:if>
    				<c:if test="${empty yornuser}">
    					暂无
    				</c:if>
    			</td>
    		</tr>
    		<tr >
    			<th >
    				不参加:
    			</th>
    			<td >
    				<c:if test="${!empty nuser}">
    					<c:forEach var="item" items="${nuser}">
    					<b>${item.realname }</b>&nbsp;&nbsp;
    					</c:forEach>
    				</c:if>
    				<c:if test="${empty nuser}">
    					暂无
    				</c:if>
    			</td>
    		</tr>
    		<tr>
    			<th>
    				活动简介:
    			</th>
    			<td>
    				${activity.content }
    			</td>
    		</tr>
    		<tr>
      <th>&nbsp;</th>
      <td><a href="javascript:window.close()" class="an_blue">关&nbsp;闭</a></td>
    </tr>
    	</table>
    </div><%@include file="/util/foot.jsp"  %>
  </body>

