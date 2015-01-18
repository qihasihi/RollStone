<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %>
<%@page import="com.school.entity.UserInfo"%>
<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    int dcSchoolID=user.getDcschoolid().intValue();
%>
<%
request.setAttribute("isSelect",true);
request.setAttribute("isAdd",true);
request.setAttribute("isUpdate",true);
request.setAttribute("isSetUserStatus",true);
request.setAttribute("isSetPright",true);
request.setAttribute("isSetService",true); 


%>
  <head>
  <style type="text/css">
  </style>  
  	<script type="text/javascript" src="<%=basePath %>js/user.js"></script>
  	<script type="text/javascript">
  	var isSelect=true;	 //查询功能权限
    var isAdd=true;	 //添加功能权限
    var isUpdate=true;	 //修改功能权限
  	var isSetUserStatus=true; //设置用户状态
  	var isSetPright=true; //设置路径权限
  	var isSetService=true;	 //设置功能权限
//  	var year="${CURRENT_YEAR}"; 
  
  	
  	/***功能权限***/
  	$(function(){
  		$("#add_btn").hide();
  		$("#sel_btn").hide();
  		$("#set_enable_btn").hide();
  		$("#set_pright_btn").hide();
  		$("#set_service_btn").hide();
  		
  		if(isSelect){ $("#sel_btn").show();}
  		if(isAdd){ $("#add_btn").show();}
  		if(isSetUserStatus){ $("#set_enable_btn").show();}
  		if(isSetPright){ $("#set_pright_btn").show();}
  		if(isSetService){ $("#set_service_btn").show();}
  	});

  	var p1;
	$(function(){
	 	if(isSelect){
	 		//翻页控件
			p1=new PageControl({
				post_url:'roleuser?m=getUserByCondition',
				page_id:'page1',
				page_control_name:"p1",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				http_free_operate_handler:beforajaxList,
				gender_address_id:'page1address',		//显示的区域						
				http_operate_handler:afterajaxList,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:10,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1, 
				operate_id:"maintbl"
			});  
  
			if("${param.identityname}".length>0
			 	||"${param.username}".length>0 
				||"${param.rolestr}".length>0){ 
				$("#sel_identity").val("${param.identityname}");  
			 	$("#sel_username").val("${param.username}");
			 	$("#sel_role").load("user?m=loadrole",
					 	{identityname:"${param.identityname}",rolestr:"${param.rolestr}"},
					 	function(){
					 		$("#sel_role").val("${param.rolestr}");
					 		pageGo('p1');  		 	
						});
		 	}       
	 	}     
	}); 
	 
	function resetCondition(){
		$("input").filter(function(){
			if(this.type.toLowerCase()=="text" ){
				this.value='';
			}else if(this.type.toLowerCase()=="checkbox" || this.type.toLowerCase()=="radio"){
				this.checked = false;
			}
		});
	}
 
	
  	</script>
  </head>
  
  <body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>

<div id="nav">
    <ul>
      <li class="crumb"><a href="user?m=list">用户管理</a></li>
      <li><a href="cls?m=list">组织管理</a></li>
        <li ><a href="sysm?m=logoconfig">系统设置</a></li>
        <li><a href="sysm?m=toAdminPerformance">使用统计</a></li>
    </ul>  
</div>
  
<div class="content">
 <div class="contentT">
    <div class="contentR">
    <div class="jcpt_yfgl">
      <table id="tbl_condition" border="0" cellspacing="0" cellpadding="0" class="public_tab1 public_input">
       <col class="w50"/>
       <col class="w240"/>
       <col class="w50"/>
       <col class="w240"/>
      <tr>
        <th width="45">身份：</th>
        <td width="154"> 	<select id="sel_identity" onchange="getRoleByIdentity()">
      		<option value="">全体</option>  
        	<c:if test="${!empty identityList}">
  				<c:forEach items="${identityList}" var="r" varStatus="rIdx">
  					<option value="${r.dictionaryvalue }">${r.dictionaryname }</option>
  				</c:forEach>  		   
  			</c:if>
  		</select>   </td>
        <th width="45">角色：</th>
        <td width="66">
        	<select id="sel_role">
      			<option value="">全部</option>
  			</select>    
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><input  id="sel_username" value="用户名/姓名" onblur="if(this.value==''){this.value='用户名/姓名';}" onfocus="if(this.value=='用户名/姓名'){this.value='';}" name="textfield28" type="text"  class="w200"></td>
        <td colspan="2"><a href="javascript:pageGo('p1');" class="an_search" title="搜索"></a></td>
          <input type="hidden" id="dcSchoolID" name="dcSchoolID" value="<%=dcSchoolID%>">
        </tr>
    </table>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
       <col class="w120"/>
       <col class="w100"/>
       <col class="w60"/>
       <col class="w80"/>
       <col class="w200"/>
       <col class="w200"/>
       <tbody id="maintbl">
     
        </tbody> 
     </table>
     <!--  <div class="nextpage">共<span class="font-blue">70</span>条记录&nbsp;&nbsp;每页显示<span class="font-blue">20</span>条&nbsp;&nbsp;第<span class="font-blue">1/5</span>页&nbsp;&nbsp;转到<input name="textfield" type="text" id="textfield" value="3000" />页&nbsp;<a href="1"><img src="../images/an01_131126.gif" title="GO" width="19" height="16" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank"><img src="../images/page01_b.gif" title="首页" width="21" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="../images/page02_b.gif"  title="上一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="../images/page03_b.gif" title="下一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="../images/page04_a.gif" title="末页" width="21" height="16" border="0" /></a>
     </div>-->
      <form id="page1form" name="page1form"  method="post" style="display:none;">
				<p align="center" id="page1address"></p>
	</form>
   </div>
   
   <div class="contentL">
    <ul> 
    <li class="crumb"><a href="user?m=list">查询</a></li>
    <%if(u!=null&&(u.getIsactivity()==null||u.getIsactivity()==0)){%>
        <li><a href="user?m=toAdd">添加</a></li>
    <%}%>
    <% if(visible){%>
        <li><a href="role?m=list">角色管理</a></li>
    <%}%>
    </ul>
   </div>
   <div class="clear"></div>
</div>
<div class="contentB"></div>
</div>

<%@include file="/util/foot.jsp" %>
  </body>
  
