<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<%
Integer grade_leader_id=UtilTool._ROLE_GRADE_LEADER_ID;
Integer grade_fu_leader_id=UtilTool._ROLE_GRADE_FU_LEADER_ID;
%>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/role.js"></script>
  	<script type="text/javascript">
  	    
  	/*********功能权限控制**********/ 
    $(function(){  
    	//$("#add_btn").hide();  
    	//$("#sel_btn").hide();
    	 
    	//if(isSelect){ $("#sel_btn").show();}  
    	//if(isAdd){ $("#add_btn").show();}
    });  
    var grade_leader_id=<%=grade_leader_id%>;    
    var grade_fu_leader_id=<%=grade_fu_leader_id%>;
    var isSelect = true,isUpdate=false,isSetRoleUser=true,isDelete=false;  
  	var p1,uploadControl; 
	$(function(){
	 	//if(isSelect){  
			//翻页控件
				p1=new PageControl({
					post_url:'role?m=ajaxlist',
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
			//}
	});

	/*  uploadControl=new ajaxUpload({
	 		_posturl:'role?m=uploadImg', 
	 		_filename:'upload',
	 		//_postparam:{uid:111},
	 		_success:imguploadSuccess,
	 		_error:errormethod,
	 		_returnType:'json'
	 	}); */	
		/** 
		*文件上传失败
		*/
		function errormethod(rps){
	 		alert(rps.msg);
	 	}
	 	/**
	 	*文件上传成功
	 	*/
		function imguploadSuccess(rps){
		 	if(rps.type=='error'){
		 		alert(rps.msg);
		 		return;	 
	 		}
	 		alert(rps.msg);
	 	}
  	</script>
  </head>
  
  <body>
  <%@include file="/util/head.jsp" %>
 <%@include file="/util/nav.jsp" %>


<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li ><a href="classyear?m=list">基础设置</a></li>
   <%//  <li><a href="dictionary?m=list">字典管理</a></li> %>
      <li><a href="user?m=list">用户管理</a></li>  
      <li><a href="role?m=list">角色管理</a></li>
      <li class="crumb"><a href="job?m=list">职务管理</a></li>
      <li><a href="dept?m=list">部门管理</a></li>
    </ul>   
  </div> 
   
<div class="p_20">
	<input type="hidden" value="1" id="flag"/> 
    <p>
      <input id="sel_rolename" name="textfield7" type="text" class="public_input w200" />
      &nbsp;&nbsp;<a href="javascript:pageGo('p1')" class="an_blue_small">查询</a><!-- <a href="javascript:showModel('div_add')" class="an_blue_small">新建</a> --></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_25">  
      <colgroup span="4" class="w180"></colgroup>  
      <tbody id="datatbl">
	      <tr>
	        <th>角色名称</th>
	        <th>创建时间</th>
	        <th>修改时间</th>
	        <th>操作</th>
	      </tr>
	      <tr>
	        <td>学生龙活虎</td>
	        <td>2013-6-15 10:29:45</td>
	        <td>2013-6-15 10:29:45</td>
	        <td><a href="1" target="_blank" class="font-lightblue">分配权限</a>&nbsp;&nbsp;<a href="1" target="_blank" class="font-lightblue">指定用户</a></td>
	      </tr>
	      <tr class="trbg2">
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr class="trbg2">
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
      </tbody>  
    </table>
    	<form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form> 
</div>
</div>

<div class="public_windows_layout w395 h250" id="div_add" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_add')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">新建角色</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr> 
      <td>         
      
角色名称：     
</td>  
      <td><input id="add_rolename" name="textfield3" type="text" class="public_input w200" /></td>
    </tr>     
        
  </table>    
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doAdd();">添加</a><a class="an_gray"  href="javascript:reset('div_add')">重置</a></p>
</div>  


<div class="public_windows_layout w395 h250" id="div_upd" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_upd')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">修改角色</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td>
  
角色名称：
</td>
      <td><input id="upd_rolename" name="textfield3" type="text" class="public_input w200" /></td>
    </tr>  
    <tr> 
      <td>创建时间：</td>
      <td id="upd_ctime">2013-6-15 10:32:33 </td>
    </tr>  
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doUpd();">修改</a><a class="an_gray"  href="javascript:reset('div_upd')">重置</a></p>
</div>
    
 

 <%@include file="/util/foot.jsp" %> 
</body>

