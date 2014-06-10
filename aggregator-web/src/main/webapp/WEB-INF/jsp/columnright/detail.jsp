<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>

<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="<%=basePath %>js/columnpage.js"></script>
		<script type="text/javascript">
			$(function(){
				//翻页控件
				p1=new PageControl({
					post_url:'columnright?m=getpageright&ref=${entity.ref}',
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称
					post_form:document.page1form,		//form
					//http_free_operate_handler:beforajaxList,
					gender_address_id:'page1address',		//显示的区域						
					http_operate_handler:afterajaxListByDetail,	//执行成功后返回方法
					return_type:'json',								//放回的值类型
					page_no:1,					//当前的页数
					page_size:20,				//当前页面显示的数量
					rectotal:0,				//一共多少
					pagetotal:1,
					operate_id:"datatbl"
				});
				//延迟800毫秒加载
				setTimeout(function(){pageGo("p1");},800);
			});
		</script>
	</head> 
	<body>
	<%@include file="/util/head.jsp" %>
		<div class="jxpt_layout">
            <%@include file="/util/nav-base.jsp" %>
	 
			<p class="jxpt_icon05">
				栏目权限信息&nbsp;&nbsp;<span style="font-size:12px"><a class="font-lightblue" href="javascript:toEdit('${entity.ref }')">[修改]</a></span>				
			</p> 
			<ul style="font-size:15px;padding-left: 20px;
				background: url('images/bg08_121022.png') repeat-x 
		    	height: 26px;
		    	padding-top: 14px;
		    	padding-bottom:14px;		    
			">						
				<li style="padding-bottom:5px"><span>*&nbsp;栏目权限：</span>
					<span id="sp_columnrightname">${entity.columnrightname }</span>
				</li>
				<li style="padding-bottom:5px">	
				<span>*&nbsp;所属栏目：</span>
					<span id="sp_columnid">${entity.columnname}<input type="hidden" value="${entity.columnid }"/></span>				
					<select name="sel_column" id="sel_column" style="display:none">
						<c:if test="${!empty columnList}">
			    		<c:forEach items="${columnList}" var="coltmp">
			    			<option value="${coltmp.columnid}">${coltmp.columnname}<span style="color:gray">[${coltmp.path}]</span></option>
			    		</c:forEach>
			    	</c:if>
					</select>
				</li> 
				<li id="up_bottom_li" style="display:none"><a href="javascript:doUpdate('${entity.ref }');" class="an_blue_small">保存</a>
				<a href="javascript:cannelEdit('${entity.ref }');" class="an_blue_small">关闭</a>
				</li>
			
			</ul>
			<p class="jxpt_icon05">
				相关页面权限列表
			</p>  
			<div class="jxpt_content_layout">  
			<p class="p_t_10">
				<span style="float:left"><a href="javascript:loadColumnRightByColumnRightId('${entity.ref}')"  class="an_blue">编辑权限</a></span>
			</p><p>&nbsp;</p><p>&nbsp;</p>
				<table id="initItemList" border="0" cellpadding="0" cellspacing="0" class="public_tab2">
					<colgroup>
						<col class="w200" /><col class="w110" />
						<col class="w160" /><col class="w110" />
					</colgroup>
					<tr>
						<th>权限名称</th>
						<th>权限路径</th>
						<th>备注</th> 
					</tr> 
					<tbody id="datatbl">
					<c:if test="${!empty crprList}">
						<c:forEach items="${crprList}" var="crp">
							<tr>
								<td>${crp.pagename }</td>
								<td>${crp.pagevalue }</td>
								<td>${crp.pagerightinfo.remark}</td>
							</tr>
						</c:forEach>
					</c:if>
					</tbody>
				</table>
				 <form action="/dictionary/ajaxlist" id="page1form" name="page1form" method="post">
			  			<p align="center" id="page1address"></p>
			  	</form>
			</div>
		</div>
		 <!-- 调班DIV -->
  <div id="dv_editpageright" style="display: none" class="public_windows_layout w410 w600">
   <p class="f_right"><a href="javascript:closeModel('dv_editpageright');document.getElementById('sel_column').style.display='none';pageGo('p1');" title="关闭"><span class="public_windows_close"></span></a></p>
   <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10 zt14">	
	     <col class="w260"/>
	     <col class="w50"/>
	     <col class="w260"/>	         
	     <tr><td colspan="3" align="center"><h4>分配页面权限</h4></td></tr>
	     <tr>
	       <td colspan="3" align="left"> 
	       <input name="txt_search_1" id="txt_search_1" onfocus="this.value='';" type="text" class="public_input w250" value="模糊查询（页面权限名称/路径）" />
	       <a href="javascript:;" onclick="pagerightSearch('txt_search_1','sel_op_1')" class="an_gray">查询</a>
	       </td> 
	      </tr>
	     <tr> 
	       <td>
	       <select id="sel_columnid" class="w250 public_input" onchange="pagerightSearch('txt_search_1','sel_op_1')">
	       		<option value="">==全部栏目==</option>
	       		<c:if test="${!empty columnList}">
			    		<c:forEach items="${columnList}" var="coltmp">			    			
			    			<option value="${coltmp.columnid}">${coltmp.columnname}<span style="color:gray">[${coltmp.path}]</span></option>
			    		</c:forEach>
			    </c:if>	       		
	       </select>
	       </td>
	      <td></td>
	      <td>已选页面权限</td>
	      </tr>
	     <tr>
	       <td><select id="sel_op_1" multiple="multiple" name="sel_op_1" class="public_input h200 w250 "></select></td>	      
	       <td rowspan="2" valign="middle" align="center" style="padding-right:10px">
	       		<br/><br/>
	       		<a href="javascript:;" onclick="pageRightAddToRight()" class="an_gray">添加</a><br/><br/>       		
	       		<a href="javascript:;" onclick="doDelPageRight()" class="an_gray">删除</a><br/><br/>
	       		<a href="javascript:;" onclick="doSubUpdatePageRight('${entity.columnrightid}')" class="an_gray">确定</a><br/><br/>
	       		<span  style="display:none" id="validateload"><img alt="加载中" src="images/loading_smail.gif" border="0"/></span>
	       	</td>
	       <td><select id="sel_op_2"  multiple="multiple" name="sel_op_2" class="public_input h200 w260"></select></td>
	      </tr>
	   </table>	   	
	   </p>
  </div>

		
		
		
		 <%@include file="/util/foot.jsp" %>
	</body>
</html>
