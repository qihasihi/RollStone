<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@page import="com.school.entity.ParentInfo"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.ClassUser"%>
<%@include file="/util/common.jsp" %> 
<script>
function associateCheck(){
	var parentRef=$("#parentRef").val();
	var child_username=$("#child_username").val();
	var child_password=$("#child_password").val();
	if(child_username==null||child_username.Trim().length<1){
		alert('请输入完整的子女账号信息！');
	}
	if(child_password==null||child_password.Trim().length<1){
		alert('请输入完整的子女账号信息！');
	}
	$.ajax({
	 	url:"parent?m=associatechild",
		type:"POST",			
		dataType:'json',
		data:{ref:parentRef,child_username:child_username,child_password:child_password},
		error:function(){
			alert('系统未响应，请稍候重试!');
		},
		success:function(json){
		    if(json.type == "error"){
		    	alert(json.msg);
		    }else{
		    	alert("匹配成功！！");
		    	closeModel('associateChild');
		    } 
		}
	});
}
</script>
<%
ParentInfo pat = (ParentInfo)request.getAttribute("pat");
UserInfo stu = (UserInfo)request.getAttribute("stu");
if(stu!=null){
%>
          <table width="1000" border="0" cellspacing="2">
			 <tr>
              <td width="230" align="center">子女用户名：</td>
              <td width="760" colspan="2"><%=stu.getUsername() %></td>
            </tr>
            <tr>
              <td align="center">子女姓名：</td>
              <td colspan="2"><%=stu.getRealname() %></td>
            </tr>
            <tr>
              <td align="center">子女班级：</td>
              <td colspan="2">
               <% List<ClassUser> claList = (List<ClassUser>)request.getAttribute("claList");
               for(ClassUser cu: claList){%>
            	   <%=cu.getClassgrade()%><%=cu.getClassname()%>
              <%}%>
              </td>
            </tr>
</table>
<%}else{%>
您还没有对您的子女账号进行绑定......
<%}%>
<div style="margin: 5px;background-color: white" class="white_content" id="associateChild" >
      <form id="associateChildForm" name="associateChildForm" method="post" action="parent?m=associateChild">
	<input type="hidden" id="parentRef" name="ref" value="<%= pat.getRef()%>"/>
      <table width="471" border="0">
        <tr>
    <td colspan="3" align="right"><a href="javascript:closeModel('associateChild');" ><img src="images/icon04.gif" title="关闭"  width="20" height="20" /></a></td>
    </tr>
  <tr>
    <th colspan="3">关联子女信息</th>
    </tr>
  <tr>
    <td width="121" align="right">用户名：</td>
    <td width="233"><label>
      <input id="child_username" name="child_username" type="text" size="30">
    </label></td>
    <td width="95">&nbsp;</td>
  </tr>
  <tr>
    <td align="right">密码：</td>
    <td><input id="child_password" name="child_password" type="text" size="30"></td>
    <td>&nbsp;</td>
  </tr>
    <td>&nbsp;</td>
    <td align="center"><label>
      <input type="button" name="Submit" onClick="associateCheck()" value="关联验证">
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" value="重置">
    </label></td>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
</div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
