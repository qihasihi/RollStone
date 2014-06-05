<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<%
	Integer grade_leader_id=UtilTool._ROLE_GRADE_LEADER_ID;
	Integer grade_fu_leader_id=UtilTool._ROLE_GRADE_FU_LEADER_ID;
%>
<head>
	<script type="text/javascript" src="<%=basePath %>js/role.js"></script>
	<script type="text/javascript">
  	 
  	/*********功能权限控制**********/   
    $(function(){
            
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
	<%@include file="/util/head.jsp"%>
	<%@include file="/util/nav.jsp"%>


	<div class="jxpt_layout">
		<div class="public_lable_layout">
			<ul class="public_lable_list">
				<li>
					<a href="classyear?m=list">基础设置</a>
				</li>
				<%//  <li><a href="dictionary?m=list">字典管理</a></li> %>
				<li>
					<a href="user?m=list">用户管理</a>
				</li>
				<li class="crumb">
					<a href="role?m=list">角色管理</a>
				</li>
				<li>
					<a href="job?m=list">职务管理</a>
				</li>
				<li>
					<a href="dept?m=list">部门管理</a>
				</li>
			</ul>
		</div>

		<div class="p_20">
			<p>
				<!--<input id="sel_rolename" name="textfield7" type="text" class="public_input w200" />
      &nbsp;&nbsp;<a href="javascript:pageGo('p1')" class="an_blue_small">查询</a> <a href="javascript:showModel('div_add')" class="an_blue_small">新建</a> -->
			</p>
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab2 m_t_25">
				<colgroup span="3" class="w200"></colgroup>
				<tbody id="datatbl">

				</tbody>
			</table>
			<form action="/role/ajaxlist" id="page1form" name="page1form"
				method="post">
				<p align="center" id="page1address"></p>
			</form>
		</div>



		<div class="p_20">
			<p>
				<!--<input id="sel_rolename" name="textfield7" type="text" class="public_input w200" />
      &nbsp;&nbsp;<a href="javascript:pageGo('p1')" class="an_blue_small">查询</a>  -->
				<a href="javascript:showModel('div_add')" class="an_blue_small">新建角色</a>
			</p>
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab2 m_t_25">
				<colgroup span="3" class="w200"></colgroup>
				<tbody id="freetbl">

				</tbody>
			</table>
		</div>


	</div>

	<div class="public_windows_layout w690 h600" id="div_add"
		style="display: none;">
		<p class="f_right">
			<a href="javascript:closeModel('div_add')" title="关闭"><span
				class="public_windows_close"></span>
			</a>
		</p>
		<p class="t_c font14 font_strong p_t_10">
			新建角色
		</p>
		<table border="0" cellpadding="0" cellspacing="0"
			class="public_tab1 m_a_10">
			<col class="w80" />
			<col class="w130" />
			<col class="w180" />
			<tr>
				<td>
					角色名称：
				</td>
				<td>
					<input id="add_rolename" name="textfield3" type="text"
						class="public_input w200" />
				</td>
			</tr>

			<tr>
				<td>
					隶属身份：
				</td>
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
				<td>
					选择权限：
				</td>
				<td>
					<select id="column" onchange="columnChange('div_add')">
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
				</td>
				<td>
					<select id="column_right">

					</select>
					<input type="button" id="add_right" onclick="edit_ul('div_add')"
						value="添加" />
				</td>

			</tr>

			<tr>
				<td colspan="3">
					<div
						style="border: 1px solid black; overflow-y: auto; height: 300px;">
						<ul id="ul_right">
						</ul>
					</div>
				</td>
			</tr>

			<tr>
				<td>
					角色说明：
				</td>
				<td>
					<textarea id="remark" style="width: 210px; height: 40px;"></textarea>
				</td>
			</tr>

		</table>
		<p class="p_t_10 t_c">
			<a class="an_gray" href="javascript:doAdd();">添加</a><a 
				class="an_gray" href="javascript:closeModel('div_add')">取消</a>
		</p> 
	</div>


	<div class="public_windows_layout w690 h600" id="div_upd"
		style="display: none;">
		<p class="f_right">
			<a href="javascript:closeModel('div_upd')" title="关闭"><span
				class="public_windows_close"></span>
			</a>
		</p>
		<p class="t_c font14 font_strong p_t_10">
			修改角色
		</p>
		<table border="0" cellpadding="0" cellspacing="0"
			class="public_tab1 m_a_10">
			<col class="w80" />
			<col class="w260" />
			<tr>
				<td>
					角色名称： <input type="hidden" id="hidden_for_id"> 
				</td>
				<td>
					<input id="upd_rolename" name="textfield3" type="text"
						class="public_input w200" />
				</td>
			</tr>
			<tr>
				<td>
					隶属身份：
				</td>
				<td>
					<select id="identity" onchange="identityChange('div_upd',this)">
	      	<c:if test="${!empty identityList}">  
	      		<c:forEach items="${identityList}" var="i"> 
	      			<option value="${i.dictionaryvalue}">${i.dictionaryname }</option>
	      		</c:forEach> 
	      	</c:if>
      	</select>
      	<span id="sp_admin"></span>
      </td>
    </tr>
    
     <tr> 
      <td>
		选择权限：     
	  </td>  
      <td>
      	<select id="column" onchange="columnChange('div_upd')">
      		<option value="">请选择栏目</option> 
      		<c:if test="${!empty columnList}">
      			<c:forEach items="${columnList}" var="c">
      				<option value="${c.columnid}">${c.columnname}</option>
      			</c:forEach>
      		</c:if> 
      	</select>
      </td>
      <td>
      	<select id="column_right">
      			
      	</select>
      	 <input type="button" id="add_right" onclick="edit_ul('div_upd')" value="添加"/>
      </td> 
     
    </tr>
    
    <tr>
    	<td colspan="3">
    		<div style="border:1px solid black;overflow-y:auto;height:300px; ">
    			<ul id="ul_right">
    			</ul>
    		</div> 
    	</td>
    </tr>
    
    <tr> 
      <td>
		角色说明：       
	  </td>  
      <td>
      	<textarea id="remark" style="width:210px;height:40px;"></textarea>
      </td>
    </tr>     
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doUpd();">修改</a><a class="an_gray"  href="javascript:closeModel('div_upd')">取消</a></p>
</div>
    
 

 <%@include file="/util/foot.jsp" %> 
</body>

