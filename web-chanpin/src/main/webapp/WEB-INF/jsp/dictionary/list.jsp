
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<%	request.setAttribute("isSelect",this.validateFunctionRight(response,u,new BigDecimal(7),false));	 //查询功能权限
    request.setAttribute("isAdd",this.validateFunctionRight(response,u,new BigDecimal(8),false));	 //添加功能权限
    request.setAttribute("isUpdate",this.validateFunctionRight(response,u,new BigDecimal(0),false));	 //修改功能权限
 %>
  <head> 
  	<script type="text/javascript" src="<%=basePath %>js/dictionary.js"></script>
  	<script type="text/javascript">
  	var isSelect=<%=this.validateFunctionRight(response,u,new BigDecimal(7),false)%>;	 //查询功能权限
    var isAdd=<%=this.validateFunctionRight(response,u,new BigDecimal(8),false)%>;	 //添加功能权限
    var isDelete=<%=this.validateFunctionRight(response,u,new BigDecimal(9),false)%>;	 //删除功能权限
    var isUpdate=<%=this.validateFunctionRight(response,u,new BigDecimal(10),false)%>;	 //修改功能权限
    
    /*********功能权限控制**********/
    $(function(){
    	$("#add_btn").hide(); 
    	$("#sel_btn").hide();
    	
    	if(isSelect){ $("#sel_btn").show();}
    	if(isAdd){ $("#add_btn").show();}
    });
     
  	var p1;
	$(function(){
	 	if(isSelect){
	 	//翻页控件
			p1=new PageControl({
				post_url:'dictionary?m=ajaxlist',
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
	 	}
	});
	
  	</script>
  </head>
  
  <body>
  	<%@include file="/util/head.jsp" %>
    <%@include file="/util/nav-base.jsp" %>
  
  <div class="jxpt_layout">
<div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="classyear?m=list">基础设置</a></li>
      <li  class="crumb"><a href="dictionary?m=list">字典管理</a></li>
      <li><a href="user?m=list">用户管理</a></li>
      <li><a href="role?m=list">角色管理</a></li>
      <li><a href="job?m=list">职务管理</a></li>
      <li><a href="dept?m=list">部门管理</a></li>
    </ul>
  </div>
  
  
<div class="p_20">
    <p>
      <input id="sel_dictionaryname"  type="text" class="public_input w200" />
      &nbsp;&nbsp;<a href="javascript:pageGo('p1');" id="sel_btn"  class="an_blue_small">查询</a><a href="javascript:showModel('div_add');" class="an_blue_small">新建</a></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_25">
      <colgroup span="2" class="w160"></colgroup>
      <colgroup span="2" class="w250"></colgroup>
      <colgroup class="w140"></colgroup>
      <tbody  id="datatbl">
      <tr>
        <th>显示的值</th>
        <th>实际的值</th>
        <th>备注</th>  
        <th>区分类型</th>
        <th>操作</th>
      </tr>
      <tr>
        <td>14班【高一】</td>
        <td>文科班</td>
        <td>行政班</td>
        <td>RS_RESOURCE_DOEM_RIGHT_TYPE</td>
        <td><a href="1" target="_blank" class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="1" target="_blank" class="font-lightblue">删除</a></td>
      </tr>
      <tr class="trbg2">
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
      </tr>
      <tr class="trbg2">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      </tbody>
    </table> 
    <form action="/dictionary/ajaxlist" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  	</form>
</div>
</div>


<div class="public_windows_layout w395 h250" id="div_add" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_add')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">新建字典</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td>显示的值：</td>
      <td><input name="textfield3" id="add_dictionary_name"/ type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input name="textfield7" id="add_dictionary_value" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>区分类型： </td>
      <td><input name="textfield3" id="add_dictionary_type" type="text" class="public_input w200" /></td>
    </tr>
    <tr>  
      <td>备注：</td>
      <td><textarea name="textfield3" style="height:30px;"  id="add_dictionary_description" class="public_input w200" ></textarea></td>
    </tr>  
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doAdd();">添加</a><a class="an_gray"  href="reset('div_add')">重置</a></p>
</div>

<div class="public_windows_layout w395 h250" id="div_upd" style="display:none;">
	<input id="hidden_for_id"  type="hidden"/>
  <p class="f_right"><a href="javascript:closeModel('div_upd')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">修改字典</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>  
    <col class="w260"/>
    <tr>
      <td>显示的值：</td>
      <td><input name="textfield3" id="upd_dictionary_name"/ type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>实际的值：</td>
      <td><input name="textfield7" id="upd_dictionary_value" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>区分类型： </td>
      <td><input name="textfield3" id="upd_dictionary_type" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <td>备注：</td>
      <td><textarea name="textfield3" style="height:30px;" id="upd_dictionary_description" class="public_input w200" ></textarea></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doUpd();">修改</a><a class="an_gray"  href="reset('div_upd')">重置</a></p>
</div>


<%@include file="/util/foot.jsp" %>
</body>

