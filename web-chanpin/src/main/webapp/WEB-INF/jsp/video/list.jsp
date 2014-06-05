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
 	function setTime(){
 	 	var refArr=$("input[name='rdo']:checked");
 	 	if(refArr.length<1){
 	 	 	alert('请选择转换时间段!');
 	 	 	return;  
 	 	} 
		$.ajax({   
			url:'videotime?m=update', 
			data:{ 
				ref:$(refArr).get(0).value
			},       
			type:'post',  
			dataType:'json',
			error:function(){alert("网络异常!");},
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
					return;
				}else{  
					alert(rps.msg);
					window.reload();
				}        
			}
		});   
    }
	
	
</script>
</head>

<body>
	<%@include file="/util/head.jsp" %>
	 <%@include file="/util/nav.jsp" %>
	 <div class="jxpt_layout">
 

<div class="yhqx_content">
<div class="yhqx_contentR">    
    
   <table id="tbl_condition" border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
    <col class="w120"/>
    <col class="w780"/>
    <tr>
    	<th>功能使用说明：</th>
    	<td>选定一个时间段，控制视频转换时间，以减少视频转换功能对服务器造成的压力!</td>
    </tr>         
    <tr>      
      <th>转换时间设置：</th>
      <td><ul class="public_list2">  
       	<c:if test="${!empty vtList}">  
  			<c:forEach items="${vtList}" var="r" varStatus="rIdx">
  				<li>
  					<c:if test="${r.flag==0}">
  						<input type="radio" name="rdo" id="sel_role_${rIdx.index}"  value="${r.ref }"/>
  					</c:if>
  					<c:if test="${r.flag==1}">
  						<input checked="checked" type="radio" name="rdo" id="sel_role_${rIdx.index}"  value="${r.ref }"/>
  					</c:if>  
  					<label for="sel_role_${rIdx.index }">${r.begintimeString}~${r.endtimeString }</label>
  				</li>
  			</c:forEach>  			   
  		</c:if>  
      </ul></td>      
    </tr>  
  </table>    
  <p>
  	<a href="javascript:void(0);" onclick="setTime();" class="an_blue_small">确定</a>
  </p>  
</div>

<div class="yhqx_contentL">
  <ul>
     <li class="crumb"><a href="videotime?m=toList">视频转换管理</a></li>
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
