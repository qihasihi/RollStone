<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<%
jcore.jsonrpc.common.JsonRpcRegister.registerObject(request,"PageUtilTool",com.school.util.PageUtil.PageUtilTool.class);
%>
<%
	request.setAttribute("isSelect",true);	 //查询功能权限
    request.setAttribute("isAdd",true);	 //添加功能权限
    request.setAttribute("isUpdate",true);	 //修改功能权限
 %>
<head>
	<script type="text/javascript" src="<%=basePath %>js/classyear.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common/JsonRpcClient.js"></script>
	<script type="text/javascript">
	var isSelect=true;	 //查询功能权限
    var isAdd=true;	 //添加功能权限
    var isDelete=true;	 //删除功能权限
    var isUpdate=true;	 //修改功能权限
    
    /*********功能权限控制**********/
    $(function(){
    	$("#add_btn").hide();
    	$("#sel_btn").hide();
    	
    	if(isSelect){ $("#sel_btn").show();}
    	if(isAdd){ $("#add_btn").show();}
    }); 
    
	var p1;
	$(function(){
		//翻页控件
			if(isSelect){
				p1=new PageControl({
					post_url:'classyear?m=ajaxlist',
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称 
					post_form:document.page1form,		//form 
					//http_free_operate_handler:beforajaxList, 
					gender_address_id:'page1address',		//显示的区域						
					http_operate_handler:afterajaxList,	//执行成功后返回方法 
					return_type:'json',								//放回的值类型
					page_no:1,					//当前的页数
					page_size:20,				//当前页面显示的数量
					rectotal:0,				//一共多少
					pagetotal:1,
					operate_id:"maintbl"
				});
				pageGo('p1');
				 
			}
	});
	
	
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
      <!-- <select name="select2" id="select2">
        <option>请选择</option>
        <option>2013-2014 上学期</option>
        <option>教师</option>
      </select>
      &nbsp;&nbsp;<a href="javascript:pageGo('p1')" class="an_blue_small">查询</a> --><a href="javascript:showModel('div_add')" class="an_blue_small">新建</a></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <colgroup span="5" class="w140"></colgroup>
      <colgroup class="w90"></colgroup>
      <tbody id="maintbl">
      <tr>
        <th>学年名称</th>
        <th>实际的值</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>创建时间</th>
        <th>操作</th>
      </tr>
      <tr>
        <td>2012--2013 学年</td>
        <td>2012--2013</td>
        <td>2013-6-14 16:08:44</td>
        <td>2013-6-14 16:09:00</td>
        <td>2013-6-14 16:09:04</td>
        <td><a href="1" target="_blank" class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="1" target="_blank" class="font-lightblue">删除</a></td>
      </tr>
      <tr class="trbg2">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
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
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr class="trbg2">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      </tbody>
    </table>
    <form action="/classyear/ajaxlist" id="page1form" name="page1form"
		method="post">
		<p id="page1address"></p>
	</form> 
</div>

<div class="yhqx_contentL">
  <ul>
     <li   class="crumb"><a href="classyear?m=list">学年管理</a></li>
    <li><a href="term?m=list">学期管理</a></li>
    <li><a href="subject?m=list">学科管理</a></li>
    <!-- <li ><a href="grade?m=list">年级管理</a></li> -->
    <li ><a href="cls?m=list">班级管理</a></li>
  </ul>
</div>
<div class="clear"></div>

 
</div>

<div class="public_windows_layout w395 h250" id="div_add" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_add')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">新建学年</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td> 学年名称： </td>
      <td><input id="add_classyear_name" name="textfield3" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input id="add_classyear_value" name="textfield7" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>开始时间： </td>
      <td><input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="btime"  name="btime" type="text" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>结束时间：</td>
      <td><input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="etime"  name="etime"  class="public_input w200" /></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray" href="javascript:doAdd();">添加</a><a class="an_gray"  href="javascript:reset('div_add')">重置</a></p>
</div>

<div class="public_windows_layout w395 h250" id="div_upd" style="display:none;">
<input id="hidden_for_upd" type="hidden" value="" />
  <p class="f_right"><a href="javascript:closeModel('div_upd')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">修改学年</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td> 学年名称： </td>
      <td><input id="upd_classyear_name" name="textfield3" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input id="upd_classyear_value" name="textfield7" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>开始时间： </td>
      <td><input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="upd_btime"  name="upd_btime" type="text" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>结束时间：</td>
      <td><input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="upd_etime"  name="upd_etime"  class="public_input w200" /></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray" href="javascript:doUpd();">修改</a><a class="an_gray"  href="javascript:reset('div_upd')">重置</a></p>
</div>
 
</div> 
  	 <%@include file="/util/foot.jsp" %>
</body>
