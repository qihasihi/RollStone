<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<%  
	Integer leader_role_id=UtilTool._ROLE_GRADE_LEADER_ID;
	Integer leader_fu_role_id=UtilTool._ROLE_GRADE_FU_LEADER_ID;
%>  
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/grade.js"></script>
  	<script type="text/javascript">
  	
  	/*********功能权限控制**********/
    $(function(){ 
    	//$("#add_btn").hide();
    	//$("#sel_btn").hide();
    	
    	//if(isSelect){ $("#sel_btn").show();}
    	//if(isAdd){ $("#add_btn").show();}
    }); 
    var leader_roleid=<%=leader_role_id%>,leader_fu_roleid=<%=leader_fu_role_id%>;
    var isSelect = true,isUpdate=false,isSetRoleUser=true,isDelete=false;  
  	var p1,uploadControl;  
	$(function(){  
	 	//if(isSelect){ 
			//翻页控件 
				p1=new PageControl({
					post_url:'grade?m=ajaxlist',
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称
					post_form:document.page1form,		//form
//					http_free_operate_handler:beforajaxList,
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


	//指定用户
	function setGradeLeader(gradeid,roleid){
		if(typeof(gradeid)=="undefined" || isNaN(gradeid)){  
			alert("系统未获取到年级标识!请刷新后重试!");  
			return;
		}	
		var param = "dialogWidth:500px;dialogHeight:700px;status:no;location:no";
		var returnValue=window.showModalDialog("teacher?m=toSetGradeLeader&gradeid="+gradeid,param);
		if(returnValue==null||returnValue.Trim().length<1){  
			alert("您未选择用户或未提交!");   
			return;  
		}
		var uidarr = returnValue.split(",");   
		if(uidarr.length<1){ 
			alert("系统未获取到用户标识!请刷新后重试!"); 
			return;
		}  
		var uidstr='';
		$.each(uidarr,function(idx,itm){
			if(uidstr.length>0)
				uidstr+=",";
			uidstr+=itm;    
		});     
		$.ajax({ 
			url:'roleuser?m=doSetGradeLeader', 
			data:{ 
				gradeid : gradeid,
				roleid:roleid,
				useridArray : uidstr  
			},  
			type:'post',
			dataType:'json',
			error:function(){alert("网络异常!");},
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
					return;
				}else{
					pageGo('p1');  
					alert(rps.msg);
				}
			}
		});
	} 

	 /* uploadControl=new ajaxUpload({
	 		_posturl:'role?m=uploadImg', 
	 		_filename:'upload',
	 		//_postparam:{uid:111},
	 		_success:imguploadSuccess,
	 		_error:errormethod,
	 		_returnType:'json'
	 	});	
		
		function errormethod(rps){
	 		alert(rps.msg);
	 	}
	 
		function imguploadSuccess(rps){
		 	if(rps.type=='error'){
		 		alert(rps.msg);
		 		return;	 
	 		}
	 		alert(rps.msg);
	  	} */
  	</script>
  </head>
  
  <body>
  <%@include file="/util/head.jsp" %>
 <%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li class="crumb"><a href="classyear?m=list">基础设置</a></li>
     <%//  <li><a href="dictionary?m=list">字典管理</a></li> %>
      <li><a href="user?m=list">用户管理</a></li>
      <li><a href="role?m=list">角色管理</a></li>
      <li><a href="job?m=list">职务管理</a></li>
      <li><a href="dept?m=list">部门管理</a></li>
    </ul>
  </div>

<div class="yhqx_content">
<div class="yhqx_contentR">
    <p>
     <!--  <select name="select2" id="select2">
        <option>请选择</option>
        <option>2013-2014 上学期</option>
        <option>教师</option>
      </select>
      &nbsp;&nbsp;<a href="1" class="an_blue_small">查询</a> --><!-- <a href="javascript:showModel('div_add')" class="an_blue_small">新建</a> --></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10"> 
      <colgroup span="4" class="w200"></colgroup> 
      <colgroup class="w190"></colgroup> 
      <tbody id="datatbl" >
      
     
      <tr>
        <th>年级名称</th>
        <th>实际的值</th>
        <th>创建时间</th>
        <th>操作</th>
      </tr>
      <tr>
        <td>高中一年级</td>
        <td>高一</td>
        <td>2013-6-14 16:09:04</td>
        <td><a href="1" target="_blank" class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="1" target="_blank" class="font-lightblue">删除</a></td>
      </tr>
      <tr class="trbg2">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td><a href="1" target="_blank" class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="1" target="_blank" class="font-lightblue">删除</a></td>
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
    <form action="/grade/ajaxlist" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form>
</div>

<div class="yhqx_contentL">
  <ul>
  	<li><a href="classyear?m=list">学年管理</a></li>
    <li><a href="term?m=list">学期管理</a></li>
    <li><a href="subject?m=list">学科管理</a></li>
    <li class="crumb"><a href="grade?m=list">年级管理</a></li>
    <li ><a href="cls?m=list">班级管理</a></li>
  </ul>
</div>
<div class="clear"></div>

 
</div>

<div class="public_windows_layout w395 h250" id="div_add" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_add')" title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">新建年级</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td> 年级名称： </td>
      <td><input name="textfield4" id="add_gradename" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input name="textfield6" id="add_gradevalue" type="text" class="public_input w200" /></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doAdd();">添加</a><a class="an_gray"  href="javascript:reset('div_add')">重置</a></p>
</div>


<div class="public_windows_layout w395 h250" id="div_upd" style="display:none;">
<input id="hidden_for_id"  type="hidden"/>
  <p class="f_right"><a href="javascript:closeModel('div_upd')" title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">修改年级</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr> 
      <td> 年级名称： </td>  
      <td><input name="textfield4" id="upd_gradename" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input name="textfield6" id="upd_gradevalue" type="text" class="public_input w200" /></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doUpd();">修改</a><a class="an_gray"  href="javascript:reset('div_upd')">重置</a></p> 
</div>

</div>
  	<%@include file="/util/foot.jsp" %>
  </body>

