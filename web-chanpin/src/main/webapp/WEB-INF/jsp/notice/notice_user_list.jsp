<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
String userid=user.getRef();

%>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'notice_user_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=basePath %>js/notice/notice.js"></script>
	<script type="text/javascript">
			var type = '${param.type}';
            var p1;
            $(function(){
                p1 = new PageControl({
                    post_url:'notice?m=ajaxuserlist',
                    page_id:'page1',
                    page_control_name:'p1',
                    post_form:document.page1form,
                    gender_address_id:'page1address',
                    http_operate_handler:noticeUserListReturn,
                    return_type:'json',
                    page_no:1,
                    page_size:20,
                    rectotal:0,
                    pagetotal:1,
                    operate_id:"mainList"
                });

                pageGo("p1");
            });
</script>
  </head>
  
  <body>
  <div class="public_title"><span class="ico09"></span>通知公告</div>
  <div class="ej_content">
      <ul class="one" id="mainList">

      </ul>
          <form id="page1form" name="page1form" method="post">
              <div class="nextpage" align="right" id="page1address"></div>
          </form>
  </div>


  </body>

