<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<% 	request.setAttribute("isSelect",this.validateFunctionRight(response,u,new BigDecimal(40),false));	 //查询功能权限
    request.setAttribute("isAdd",this.validateFunctionRight(response,u,new BigDecimal(39),false));	 //添加功能权限
    request.setAttribute("isUpdate",this.validateFunctionRight(response,u,new BigDecimal(42),false));	 //修改功能权限
  %>
  <head>
  <style type="text/css">
  		.zyfx_htWindows1{
  			background: url("<%=basePath%>images/bg13_110331.gif") no-repeat scroll 0 0 transparent;
		    height: 102px;
		    padding: 12px 7px 7px 0;
		    width: 109px;
  		}
  		ul.zyfx_htList li {
		    height: 18px;
		    line-height: 18px;
		    padding: 3px 15px;
		    width: 79px;
		    list-style-type: none;
		    font-family: "宋体";
    		font-size: 12px;
    		border: 0;
    		margin: 0;
		}
		
	    .IndexWindows2 {
		    background: url("<%=basePath%>css/images/bg07_100618.gif") no-repeat scroll 0 -174px transparent;
		    height: 166px;
		    line-height: 22px;
		    padding: 10px 12px;
		    position: absolute;
		    top: 28px;
		    width: 269px;
		    z-index: 1;
		}
  </style>
  	<script type="text/javascript" src="<%=basePath %>js/service.js"></script>
  	<script type="text/javascript">	
  	  	var isAdd=<%=this.validateFunctionRight(response,u,new BigDecimal(39),false)%>;	 //添加功能权限
  		var isSelect=<%=this.validateFunctionRight(response,u,new BigDecimal(40),false)%>;	 //查询功能权限
    	var isDelete=<%=this.validateFunctionRight(response,u,new BigDecimal(41),false)%>;	 //删除功能权限
    	var isUpdate=<%=this.validateFunctionRight(response,u,new BigDecimal(42),false)%>;	 //修改功能权限
    	
  		$(function(){
  			/*******功能权限操作********/
  			if(isSelect){
  				$(".dtree").show();			
  				initList();
  			}
  			$(document).bind("click",bodyClick);
  		});
  		
  	</script>
  </head>
  
  <body>
  	<div style="font-size:13px;width:70%;position:absolute;left:50px;border:1px dashed black;">
  		系统管理&gt;功能服务列表<br>
  		<a href="<%=basePath %>index.jsp" >返回首页</a>
  		<!-- <a href="javascript:void(0);" onclick="toShowDIV('div_add',undefined)">添加</a> -->
  	
  		<!-- dtree -->
  		<c:if test="${isSelect}">  		
  		<div class="dtree" style="display:none;">
  			<p><a href="javascript: d.openAll();"><img src="images/an26.gif" alt="展开" title="展开" width="61" height="21" border="0" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: d.closeAll();"><img src="images/an27.gif" alt="关闭" title="关闭" width="61" height="21" border="0" /></a></p>
  			<p style="color:red;">鼠标左键点击节点弹出菜单[灰色为不可用状态]</p>
  			<div id="_dtree">
  			
  			</div>
  		</div>
  		</c:if>
  		<!-- dtree -->
  	</div>
 
  		<c:if test="${isAdd}">
  		 <div class="zyfx_htWindows1" style="position: absolute;display:none;" id="for_root">
	        <ul class="zyfx_htList"> 
	          <li  onmouseover="mouseoverLi(this);"  onclick="javascript:to_addNode('for_root','div_add');">添加</li>
	        </ul>
    	</div>
    	</c:if>
    	<div class="zyfx_htWindows1" style="position: absolute;display:none;" id="for_node">
	        <ul class="zyfx_htList"> 
	          <script type="text/javascript">
	          	var html ='';
	          	if(isAdd){
		          html+='<li  onmouseover="mouseoverLi(this);"  onclick="javascript:to_addNode(\'for_root\',\'div_add\');">添加</li>';
		        }
		        if(isUpdate){
		          html+='<li  onmouseover="mouseoverLi(this);"  onclick="javascript:to_addNode(\'for_root\',\'div_upd\');">编辑名称</li>';
		        }
		        if(isDelete){
		          html+='<li  onmouseover="mouseoverLi(this);"  onclick="javascript:doDel();">删除</li>';
		        }
		        document.write(html); 
	          </script>
	        </ul>
    	</div>
    	<c:if test="${isAdd}">
    	<!-- 添加 -->
		<div id="div_add" class="white_content W650">
			<table cellpadding="0px" cellspacing="0px" border="0px"
				bgcolor="white">
				<tr>
					<td class="modelLeftTop"></td>
					<td class="modelTop"></td>
					<td class="modelRightTop"></td>
				</tr>
				<tr>
					<td class="modelLeft"></td>
					<td>
						<div align="right"><a href="javascript:closeModel('div_add');">
							<img
									src="images/an14.gif" alt="关闭" width="15" height="15"
									border="0" /></a> 
						</div>	
						<div id="a_fasongId" style="display:none"></div>				
					<table cellspacing="10" cellpadding="2" border="0" class="Tc"
								style="text-align: left;" id="layerList">													
							<tr>
  					<td>功能名称：</td>
  					<td><input type="text" id="add_servicename"/></td>
  				</tr>
  				<tr>
  					<td>功能状态：</td>
  					<td>
  						<input type="radio" value="0" checked="checked" id="add_enable" name="add_enable" />
  						<label for="add_enable">可用</label>
  						<input type="radio" value="1" id="add_enable_1" name="add_enable" />
  						<label for="add_enable_1">禁用</label>
  					</td>
  				</tr>
  				<tr>
  					<td align="center" colspan="2"><input type="button" onclick="doAdd()" value="添加"/><input type="button"  onclick="reset('div_add')" value="重置"/></td>
  				</tr>
							</table>
					</td>
					<td class="modelRight"></td>
				</tr>
				<tr>
					<td class="modelLeftBottom"></td>
					<td class="modelBottom"></td>
					<td class="modelRightBottom"></td>
				</tr>
			</table>
		</div>
		
		</c:if>
		<c:if test="${isUpdate}">
		<!-- 修改 -->
		<div id="div_upd" class="white_content W650">
			<table cellpadding="0px" cellspacing="0px" border="0px"
				bgcolor="white">
				<tr>
					<td class="modelLeftTop"></td>
					<td class="modelTop"></td>
					<td class="modelRightTop"></td>
				</tr>
				<tr>
					<td class="modelLeft"></td>
					<td>
						<div align="right"><a href="javascript:closeModel('div_upd');">
							<img
									src="images/an14.gif" alt="关闭" width="15" height="15"
									border="0" /></a> 
						</div>	
						<div id="a_fasongId" style="display:none"></div>				
					<table cellspacing="10" cellpadding="2" border="0" class="Tc"
								style="text-align: left;" id="layerList">													
						<tr>
  					<td>功能名称：</td>
  					<td><input type="text" id="upd_servicename"/></td>
  				</tr>
  				<tr>
  					<td>功能状态：</td>
  					<td>
  						<input type="radio" value="0" id="upd_enable" name="upd_enable" />
  						<label for="upd_enable">可用</label>
  						<input type="radio" value="1" id="upd_enable_1" name="upd_enable" />
  						<label for="upd_enable_1">禁用</label>
  					</td>
  				</tr>
  				
  				<tr>
  					<td align="center" colspan="2"><input type="button" onclick="doUpd()" value="修改"/><input type="button" onclick="reset('div_upd')" value="重置"/></td>
  				</tr>
							</table>
					</td>
					<td class="modelRight"></td>
				</tr>
				<tr>
					<td class="modelLeftBottom"></td>
					<td class="modelBottom"></td>
					<td class="modelRightBottom"></td>
				</tr>
			</table>
		</div>
		</c:if>
    <!-- ----遮蔽层--- -->
    <input type="hidden" id="ref">
    <div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
  </body>

