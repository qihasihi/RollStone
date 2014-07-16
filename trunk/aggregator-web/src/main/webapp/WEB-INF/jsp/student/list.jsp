<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<% 	request.setAttribute("isSelect",this.validateFunctionRight(response,u,new BigDecimal(32),false));	 //查询功能权限  
  %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/student.js"></script>
  	<script type="text/javascript">
  	var isSelect=<%=this.validateFunctionRight(response,u,new BigDecimal(32),false)%>;	 //查询功能权限
  	
  	
  	/*********功能权限控制**********/
    $(function(){
    	$("#sel_btn").hide();
    	
    	if(isSelect){ $("#sel_btn").show();}
    });
     
  	var p1;
	$(function(){
	 	if(isSelect){
	 		//翻页控件
		/*	p1=new PageControl({
				post_url:'student?m=ajaxlist',
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
			*/
			loadJQGrid();
			
			
			
	 	}
		
	});
	
	/**加载JQGrid
	*/
	function loadJQGrid(){		
	$("#tbl_jgrid").tableGrid({
  				url:"student?m=jqgridlist",  						
  				colNames:['学生ID','学生编号','学生姓名','学生性别','家庭住址','监管人','监管人电放','学生帐号'],
  				colModel:[{name:'stuid',index:'s.stu_id',editable:false,editoptions:{readonly:true,size:10}},
  						  {name:'stuno',index:'s.stu_no',search:false,editable:true,editoptions:{size:15}},
  						  {name:'stuname',index:'s.stu_name',editable:true,editoptions:{size:15}},
  						  //select 这样设置 edittype:'select', formatter:'select', formatoptions:{baseLinkUrl:'myrul.php', addParam: '&action=edit', idName:'myid'}
  						  {name:'stusex',index:'s.stu_sex',
  						    edittype:'select',editable:true 
  						  ,editoptions:{value:"男:男;女:女"}},
  						  {name:'stuaddress',index:'s.stu_address',editoptions:{size:20}},
  						  {name:'linkman',index:'s.linkman',editoptions:{size:16}},
  						  {name:'linkmanphone',index:'s.linkman_phone',editoptions:{size:16}},
  						  {name:'username',index:'u.user_name',editoptions:{size:16}} 						  
  						  ],  			
  				pager:'#div_pager',  		
  				prmNames:{id:"stuid",
  							editoper:"modify",
  							addoper:"ajaxsave", 
  							deloper:"del"},
  				jsonReader:{id:'stuid'},  			
				title:'课学生信息管理', 
				editurl:'student',isAdd:false,isUpdate:false,isDel:false});
	
	}
	
	
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
  		系统管理>学生列表<br>
  		<a href="<%=basePath %>index.jsp" >返回首页</a>
  		<!-- <a href="javascript:void(0);" onclick="toShowDIV('div_add',undefined)">添加角色</a> -->
  	<!-- 	<c:if test="${isSelect}">
  		<p>学生编号：<input id="sel_stunolike"  type="text" /> </p>
  		<p>学生姓名：<input id="sel_stuname"  type="text" /> </p>
  		<p>学生帐号：<input id="sel_userid"  type="text" /> </p>
  		<p>学生性别：<input type="radio" id="sel_stusex_1" name="sel_stusex" value="男" /><label for="sel_stusex_1">男</label>
  			<input type="radio" id="sel_stusex_2" name="sel_stusex" value="女" /><label for="sel_stusex_2">女</label>
  		</p>
  		<p>学案导学学生：<input type="radio" value="是" id="sel_islearnguide_1" name="sel_islearnguide" /><label for="sel_islearnguide_1">是</label>
  			<input type="radio" value="否" id="sel_islearnguide_2" name="sel_islearnguide" /><label for="sel_islearnguide_2">否</label>
  		</p>
  		<p><input id="sel_btn" type="button" onclick="pageGo('p1')" value="查询"/> <input type="button" onclick="resetCondition()" value="重置"/> </p>
  		<table id="datatbl" align="center" style="text-align:center;font-size:13;width:100%">
  			<tr>
  				<th>学生编号</th>
  				<th>学生姓名</th>
  				<th>学生性别</th>
  				<th>家庭住址</th>
  				<th>监管人</th>
  				<th>监管人电话</th>
  				<th>学生帐号信息</th>
  			</tr>
  		</table>
  		<form action="" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form>
  		</c:if>
  		 -->
  		<table id="tbl_jgrid"></table>
  		<div id="div_pager"></div> 
  	</div>
  	
  	
  </body>

