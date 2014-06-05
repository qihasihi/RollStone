<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<% 	request.setAttribute("isSelect",this.validateFunctionRight(response,u,new BigDecimal(31),false));	 //查询功能权限
  %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/teacher.js"></script>
  	<script type="text/javascript">
  	var isSelect=<%=this.validateFunctionRight(response,u,new BigDecimal(31),false)%>;	 //查询功能权限
  	var p1;
	$(function(){
	 	if(isSelect){
	 		//翻页控件
			p1=new PageControl({
				post_url:'teacher?m=ajaxlist',
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
				operate_id:"datatbl"
			});
			pageGo('p1');
			/**********功能权限操作**************/
			$("#sel_btn").show();
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
	
	document.onkeypress=function(e){
		e = e || window.event;
        var code;
        if(e.keyCode)  
        {    
            code=e.keyCode;  
        }  
        else if(e.which)  
        {  
            code   =   e.which;  
        }

        if(code==13)
        {
        	pageGo("p1");
        }
	}  
  	</script>
  </head>
  
  <body>
  	<div style="font-size:13px;width:70%;position:absolute;left:50px;border:1px dashed black;">
  		系统管理>教师列表<br>
  		<a href="<%=basePath %>index.jsp" >返回首页</a>
  		<c:if test="${isSelect}">
  		<!-- <a href="javascript:void(0);" onclick="toShowDIV('div_add',undefined)">添加角色</a> -->
  		<p>教师姓名：<input id="sel_teachername"  type="text" /> </p>
  		<p>登录帐号：<input id="sel_username"  type="text" /> </p>
  		<p>身份证号：<input id="sel_teachercardid"  type="text" /> </p>
  		<p>授课科目：<input id="sel_coursename"  type="text" /> </p>
  		<p>教师性别：<input type="radio" id="sel_teachersex_1" name="sel_teachersex" value="男" /><label for="sel_teachersex_1">男</label>
  			<input type="radio" id="sel_teachersex_2" name="sel_teachersex" value="女" /><label for="sel_teachersex_2">女</label>
  		</p>
  		<p><input id="sel_btn" style="display:none;" type="button" onclick="pageGo('p1')" value="查询"/> <input type="button" onclick="resetCondition()" value="重置"/> </p>
  		<table id="datatbl" align="center" style="text-align:center;font-size:13;width:100%">
  			<tr>
  				<th>教师姓名</th>
  				<th>登录帐号</th>
  				<th>授课学科</th>
  				<th>教师性别</th> 
  				<th>教师电话</th>
  				<th>邮箱</th>
  				<th>身份证号</th>
  			</tr>
  		</table>
  		<form action="" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form>
  		</c:if>
  	</div>
  </body>

