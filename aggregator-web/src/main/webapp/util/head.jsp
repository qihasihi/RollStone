<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.school.manager.SmsReceiverManager"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.SmsReceiver"%>
<%@page import="com.school.dao.SmsReceiverDAO"%>
<%@ page import="com.school.entity.EttColumnInfo" %>
<%@ page import="com.school.util.MD5_NEW" %>
<%
     //如果是乐知行过来，则不显示头尾
 Object fromType=session.getAttribute("fromType");
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


    List<EttColumnInfo> ettColumnInfos =(List<EttColumnInfo>)request.getSession().getAttribute("ettColumnList");
  %>
<c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
    <div class="jxxt_lzx_header">
</c:if>
<c:if test="${empty sessionScope.fromType||sessionScope.fromType!='lzx'}">
<div id="header">
</c:if>
  <ul>
      <c:if test="${empty sessionScope.fromType||sessionScope.fromType!='lzx'}">
             <li <%= (pageType!=null&&pageType=="index"?"class='one crumb'":"one")%>><a href="<%=basePath %>user?m=toIndex">首&nbsp;页</a></li>
         　　 <li class="four"><a href="javascript:;" onclick="loginDestory('<%=basePath %>')">退出</a></li>
      </c:if>
    <c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
        <%
            if(!isStudent&&modelType==2){
            //加载乐知行，教学首页，左上角的链接
            if(ettColumnInfos!=null&&ettColumnInfos.size()>0){
                String headcolumnico=UtilTool.utilproperty.getProperty("LZX_HEAD_COLUMN_ICO");
                for (EttColumnInfo ectmp:ettColumnInfos){
                    if(ectmp!=null){
                        String cls="";
                        if(headcolumnico.indexOf(ectmp.getEttcolumnid().toString())!=-1){
                            String tmpa=headcolumnico.substring(headcolumnico.indexOf(ectmp.getEttcolumnid().toString()));
                            String[] tmparray=null;
                            if(tmpa.indexOf(",")!=-1)
                                 tmparray=tmpa.substring(0,tmpa.indexOf(",")).split("\\|");
                            else
                                tmparray=tmpa.split("\\|");
                            if(tmparray.length>1)
                                cls=tmparray[1];
                        }
                        if(cls!=null&&cls.trim().equals("two")){
                            %>
                            <li class="<%=cls%>" style="background-position: -150px -50px;" ><a href="<%=ectmp.getEttcolumnurl()%>&isVip=<%=ectmp.getStatus()%>" target="_blank"><%=ectmp.getEttcolumnname()%></a></li>
                        <%
                           }else{
                        %>
                        <li class="<%=cls%>" style="background-position:0px -50px;"><a href="<%=ectmp.getEttcolumnurl()%>&isVip=<%=ectmp.getStatus()%>" target="_blank"><%=ectmp.getEttcolumnname()%></a></li>
                     <%
                           }
                        }
                }
            }
          }
        %>
        <%--<li class="two"><a href="" target="_blank"></a></li>--%>
        <%--<li class="one"><a href="1" target="_blank">四中公开课</a></li>--%>
        <%--<li><a href="1" target="_blank">远程教研</a></li>--%>
    </c:if>
   <!-- <li <%= (pageType!=null&&pageType=="userview"?"class='two crumb'":"two")%>><a href="user?m=userview">个人主页</a></li>
    <li class="three"><a href="sms?m=inbox" target="_blank">
  	消息中心<%=receiveSMSList!=null&&receiveSMSList.size()>0?"(<font color='red'>"+receiveSMSList.size()+"</font>)":"" %>
  	</a></li>-->

  </ul>
<c:if test="${empty sessionScope.fromType||sessionScope.fromType!='lzx'}">
 <p><span></span><img src="<%=basePath %>images/<%=logoSrc %>" width="253" height="64"/></p>
</c:if>
<c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
    <p style="width:210px;height:50px"><span></span></p>
<%--<p><span></span><img src="<%=basePath+UtilTool.utilproperty.getProperty("LZX_LOGO_SRC") %>" width="253" height="64"/></p>--%>
</c:if>
</div>