<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<%
	Integer grade_leader_id=UtilTool._ROLE_GRADE_LEADER_ID;
	Integer grade_fu_leader_id=UtilTool._ROLE_GRADE_FU_LEADER_ID;
%>
<head>
	<style type="text/css">
		h5 {
		    border-bottom: 1px dashed #CDCDCD;
    		margin: 10px auto;
		}
	</style>
	<script type="text/javascript" src="<%=basePath %>js/role.js"></script>
	<script type="text/javascript">
  	 
  	/*********功能权限控制**********/   
    $(function(){
    	$("#div_add select[id='identity']").val("教职工");             
 		$("#div_add select[id='identity']").change(); 
 		load_role(); 
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
					page_size:10,				//当前页面显示的数量
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
	<%@include file="/util/head-base.jsp"%>
	<%@include file="/util/nav-base.jsp"%>
	
	<div id="nav">
    <ul>
      <li class="crumb"><a href="user?m=list">用户管理</a></li>
      <li><a href="cls?m=list">组织管理</a></li>
      <li><a href="term?m=list">系统设置</a></li>  
    </ul>  
</div>


	<div class="content">
 <div class="contentT">
    <div class="contentR">
      <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <colgroup span="3" class="w180"></colgroup>
      <caption>系统角色</caption>
      <tbody id="datatbl">
		 <tr>
         <th>名称</th>
         <th>隶属身份</th>
         <th>操作</th>
        </tr>
       <tr>
         <td>班主任</td>
         <td>教职工</td>
         <td><a href="1" class="font-blue">查看权限</a></td>
        </tr>
       <tr class="trbg1">
         <td>&nbsp;</td>
         <td><br></td>
         <td>&nbsp;</td>
        </tr>
       <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
        </tr>
       <tr class="trbg1">
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
        </tr>
	  </tbody>
   </table>
     <form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
		<p align="center" id="page1address"></p>
	 </form>
   <h6></h6>
   <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
     <colgroup span="3" class="w180"></colgroup>
     <caption>自定义角色<br><a href="javascript:showModel('div_add')" class="an_small m_t_5">新建角色</a></caption>
     <tbody id="freetbl">
	     <tr>
	       <th>名称</th>
	       <th>隶属身份</th>
	       <th>操作</th>
	     </tr>
	     <tr>
	       <td>班主任</td>
	       <td>教职工</td>
	       <td><a href="1" class="font-blue">修改</a>&nbsp;&nbsp;<a href="1" class="font-blue">删除</a></td>
	     </tr>
	     <tr class="trbg1">
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	     </tr>
	     <tr>
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	     </tr>
	     <tr class="trbg1">
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	       <td>&nbsp;</td>
	     </tr>
     </tbody>
   </table>
    </div>
   
   <div class="contentL"> 
    <ul>
    <li><a href="user?m=list">查询</a></li>
    <li><a href="user?m=toAdd">添加</a></li> 
    <li class="crumb"><a href="role?m=list">角色管理</a></li>
    </ul>
   </div>
   <div class="clear"></div>
</div>
<div class="contentB"></div>
</div>
    
 <div class="public_windows jcpt_yfgl_float01" id="div_add" style="display: none;">
  <h3><a href="javascript:closeModel('div_add')" title="关闭"></a>新建角色</h3>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
     <tr class="public_tab1">
        <th><span class="ico06"></span>名&nbsp;&nbsp;&nbsp;&nbsp;称：</th>
        <td><input id="add_rolename" type="text"/></td>
    </tr>
      <tr>
        <th><span class="ico06"></span>隶属身份：</th>
        <td>
        	<select id="identity" onchange="identityChange('div_add',this)">
						<c:if test="${!empty identityList}">
							<c:forEach items="${identityList}" var="i">
								<option value="${i.dictionaryvalue}">
									${i.dictionaryname }
								</option>
							</c:forEach>
						</c:if>
			</select>
			<span id="sp_admin"></span>
       	</td>
      </tr>
      <tr>
        <th><span class="ico06"></span>选择权限：</th>
        <td><select id="column" onchange="columnChange('div_add')">
						<option value="">
							请选择栏目
						</option>
						<c:if test="${!empty columnList}">
							<c:forEach items="${columnList}" var="c">
								<option value="${c.columnid}">
									${c.columnname}
								</option>
							</c:forEach>
						</c:if>
			</select>
			
			<select id="column_right">

			</select>
          &nbsp;&nbsp;<a id="add_right" onclick="edit_ul('div_add')" class="an_public2">添&nbsp;加</a>
          <div class="jcpt_yfgl_info" id="div_ul_result">
          
          </div>
        </td>
    </tr>
      <tr>
        <th>&nbsp;&nbsp;角色说明：</th>
        <td>
        	<textarea onkeyup="checkLength(this)" id="remark" class="w300" ></textarea>
        	<br/>
        	<span style="float:right"></span>
        </td>
    </tr>
      <tr>
        <th>&nbsp;</th>
        <td class="t_r"><a class="an_public1" href="javascript:doAdd();">创建</a></td>
      </tr>
  </table>
</div>




 <div class="public_windows jcpt_yfgl_float01" id="div_upd" style="display: none;">
  <h3><a href="javascript:closeModel('div_upd')" title="关闭"></a>修改角色</h3>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
     <tr class="public_tab1">
        <th><span class="ico06"></span>名&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="hidden" id="hidden_for_id"> </th>
        <td><input id="upd_rolename" type="text"/></td>
    </tr>
      <tr>
        <th><span class="ico06"></span>隶属身份：</th>
        <td>
        	<select id="identity" onchange="identityChange('div_upd',this)">
						<c:if test="${!empty identityList}">
							<c:forEach items="${identityList}" var="i">
								<option value="${i.dictionaryvalue}">
									${i.dictionaryname }
								</option>
							</c:forEach>
						</c:if>
			</select>
			<span id="sp_admin"></span>
       	</td>
      </tr>
      <tr>
        <th><span class="ico06"></span>选择权限：</th>
        <td><select id="column" onchange="columnChange('div_upd')">
						<option value="">
							请选择栏目
						</option>
						<c:if test="${!empty columnList}">
							<c:forEach items="${columnList}" var="c">
								<option value="${c.columnid}">
									${c.columnname}
								</option>
							</c:forEach>
						</c:if>
			</select>
			
			<select id="column_right">

			</select>
          &nbsp;&nbsp;<a id="add_right" onclick="edit_ul('div_upd')" class="an_public2">添&nbsp;加</a>
          <div class="jcpt_yfgl_info" id="div_ul_result">
           
          </div>
        </td>
    </tr>
      <tr>
        <th>&nbsp;&nbsp;角色说明：</th>
        <td>
        	<textarea id="remark" onkeyup="checkLength(this)" class="w300" ></textarea>
        	<br/> 
        	<span style="float:right"></span>
        </td>
    </tr>
      <tr>
        <th>&nbsp;</th>
        <td class="t_r"><a class="an_public1" href="javascript:doUpd();">修改</a></td>
      </tr>
  </table> 
</div> 

 
<div class="public_windows" id="div_sys_role" style="display: none;">
<h3><a href="javascript:closeModel('div_sys_role')" title="关闭"></a>角色权限</h3>
  <div class="jcpt_yfgl_float02"> 
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab3">
  <col class="w100" />
  <col class="w320" />
  	<tbody id="tb_sys_role">
   
    </tbody>
    </table>
    </div>
</div>





 <%@include file="/util/foot.jsp" %> 
</body>

