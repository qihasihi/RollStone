<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 

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
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1, 
				operate_id:"maintbl"
			});  
  
			if(typeof("${param.identityname}")!='undefined'
			 	||typeof("${param.username}")!='undefined'
				||typeof("${param.rolestr}")!='undefined'){ 
				$("#sel_identity").val("${param.identityname}");  
			 	$("#sel_username").val("${param.username}");
			 	$("#sel_role").load("user?m=loadrole",
					 	{identityname:"${param.identityname}",rolestr:"${param.rolestr}"},
					 	function(){
					 		$("#sel_role").val("${param.rolestr}");		 	
						});
			 	pageGo('p1');  
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
<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">   
      <li><a href="classyear?m=list">基础设置</a></li>
    <%//  <li><a href="dictionary?m=list">字典管理</a></li> %>
      <li class="crumb"><a href="user?m=list">用户管理</a></li>
      <li><a href="role?m=list">角色管理</a></li>  
      <li><a href="job?m=list">职务管理</a></li>
      <li><a href="dept?m=list">部门管理</a></li>
    </ul>  
  </div>

<div class="p_20">
  <table id="tbl_condition" border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
   
    <!--
    <tr>  
      <th>角&nbsp;色&nbsp;查&nbsp;询：</th>
      <td><ul class="public_list2">
       	<c:if test="${!empty roleList}">
  				<c:forEach items="${roleList}" var="r" varStatus="rIdx">
  					<li><input type="checkbox" name="sel_role" id="sel_role_${rIdx.index}" value="${r.roleid }"/>
  					<label for="sel_role_${rIdx.index }">${r.rolename}</label></li>
  				</c:forEach>  			 
  		</c:if>
      </ul></td>
    </tr>
     <tr>
      <th>部&nbsp;门&nbsp;查&nbsp;询：</th>
      <td><ul class="public_list2">
       <c:if test="${!empty deptList}">
  				<c:forEach items="${deptList}" var="d" varStatus="dIdx">
  				
  					<li><input type="checkbox" name="sel_dept" id="sel_dept_${rIdx.index}" value="${d.deptid }"/>
  					<label for="sel_role_${dIdx.index }">${d.deptname}</label></li>
  				</c:forEach>  			
  			</c:if>
      </ul></td>
    </tr> 
    <tr>
      <th>职&nbsp;务&nbsp;查&nbsp;询：</th>
      <td><ul class="public_list2">
        	<c:if test="${!empty jobList}">
  				<c:forEach items="${jobList}" var="r" varStatus="rIdx">
  					<li><input type="checkbox" name="sel_role" id="sel_role_${r.roleid }" value="${r.roleid }"/>
  					<label for="sel_role_${r.roleid }">${r.rolename}</label></li>
  				</c:forEach>  		   
  			</c:if>         
      </ul></td> 
    </tr>  -->
    
    <tr>
      <th>身份:</th>
      <td>
      	<select id="sel_identity" onchange="getRoleByIdentity()">
      		<option value="">全体</option>  
        	<c:if test="${!empty identityList}">
  				<c:forEach items="${identityList}" var="r" varStatus="rIdx">
  					<option value="${r.dictionaryvalue }">${r.dictionaryname }</option>
  				</c:forEach>  		   
  			</c:if>
  		</select>         
      </td>   
      <th>角色:</th>
      <td>
      	<select id="sel_role">
      		<option value="">全部</option>
  		</select>           
      </td>   
    </tr>  
    
    <tr>  
      <td><input id="sel_username" value="用户名/姓名" onblur="if(this.value==''){this.value='用户名/姓名';}" onfocus="if(this.value=='用户名/姓名'){this.value='';}" name="textfield5" type="text" class="public_input w200" /></td>
    </tr>   
    <tr>
      <th>&nbsp;</th> 
      <td><a id="a_select" href="javascript:pageGo('p1');"  class="an_blue">查&nbsp;询</a><a href="javascript:reset('tbl_condition')"  class="an_blue">重&nbsp;置</a></td>
    </tr>  
  </table>
  <h5 class="m_t_25"></h5>
  <p class="p_t_10"><a href="user?m=toAdd" class="an_darkred">添加用户</a></p><!-- <a id="set_enable_btn" href="javascript:dosetUserStateid(0);" class="an_darkred">启用选中用户</a><a id="set_enable_btn" href="javascript:dosetUserStateid(1);" class="an_darkred">禁用选中用户</a><a id="set_pright_btn" href="javascript:setPageRight('',false);" class="an_darkred">批量权限分配</a> --> 
 
  <table  border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <colgroup class="w100"></colgroup>
      <colgroup class="w130"></colgroup>
      <colgroup class="w250"></colgroup>
      <colgroup span="2" class="w150"></colgroup>
      <colgroup class="w180"></colgroup>
      <tbody id="maintbl">
     
      </tbody>
    </table>
    <form id="page1form" name="page1form"  method="post">
				<p align="center" id="page1address"></p>   
	</form>  
	
	
</div>


</div>

<script type="text/javascript">
	 
</script>
<%@include file="/util/foot.jsp" %>
  </body>
 
