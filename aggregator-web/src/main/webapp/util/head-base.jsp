<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.school.manager.SmsReceiverManager"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.SmsReceiver"%>
<%@page import="com.school.dao.SmsReceiverDAO"%>
 <%
    String pageType=null;
  	if(request.getAttribute("pageType")!=null)
  		pageType=(String)request.getAttribute("pageType");
  	 
    SmsReceiver smsreceiver = new SmsReceiver();
	UserInfo sms_user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
	if(null!=sms_user)
		smsreceiver.setReceiverid(sms_user.getUserid());
	else 
		smsreceiver.setReceiverid(0);
	smsreceiver.setStatus(0);
	ApplicationContext acc = WebApplicationContextUtils.getWebApplicationContext(application);
	SmsReceiverManager  smsReceiverManagerac=(SmsReceiverManager) acc.getBean("smsReceiverManager");
	List receiveSMSList = smsReceiverManagerac.getList(smsreceiver, null);
     String logoSrc=UtilTool.utilproperty.getProperty("LOGO_SRC");
  %>
<div id="header">
  <ul>
    <c:if test="${empty sessionScope.fromType||sessionScope.fromType!='lzx'}">
         <li <%= (pageType!=null&&pageType=="index"?"class='one crumb'":"")%>><a href="<%=basePath %>user?m=toIndex">首&nbsp;页</a></li>
      </c:if>
   <!-- <li <%= (pageType!=null&&pageType=="userview"?"class='two crumb'":"")%>><a href="user?m=userview">个人主页</a></li>
    <li><a href="sms?m=inbox" target="_blank">
  	消息中心<%=receiveSMSList!=null&&receiveSMSList.size()>0?"(<font color='red'>"+receiveSMSList.size()+"</font>)":"" %>
  	</a></li>-->
    <li><a href="javascript:;" onclick="loginDestory('<%=basePath %>')">退出</a></li>
  </ul>     
 <p><span></span><img src="<%=basePath %>images/<%=logoSrc %>" width="253" height="64" /></p>
</div>