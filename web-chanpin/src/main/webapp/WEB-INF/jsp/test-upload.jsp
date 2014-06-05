<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@page import="com.school.entity.UserInfo"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@include file="/util/common.jsp" %> 
<%
String basepath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort(); %>
<HTML>		
<HEAD>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="imagetoolbar" content="no">
    <script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>


    <script src="<%=basepath %>js/common/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>

<script src="<%=basepath %>js/common/jquery.form.pack.2.24.js"></script>
<script src="<%=basepath %>js/common/jquery.imgareaselect.js"></script>
<link  rel="stylesheet" type="text/css" href="css/imgareaselect-default.css"/>
</HEAD>
<BODY>
<%
UserInfo user = (UserInfo)request.getAttribute("user");
%>
<script type="text/javascript">


$(function(){
    //document.domain = "http://202.99.47.77/sz_school";
   // alert(document.domain);
});


function uploadImg(){
	   var t=document.getElementById('uploadfile');
	   		if(t.value.Trim().length<1){
	   			alert('您尚未选择图片，请选择！');
	   			return;
	   		}
//	   		var lastname=t.value.Trim().substring(t.value.Trim().lastIndexOf('.')).toLowerCase();;
//	   		if(lastname!='.jpg'&&lastname!='.png'&&lastname!='.gif'&&lastname!='.bmp'){
//	   			alert('您选择的图片不正确，目前只支持jpg,gif,png,bmp图片!');
//	   			return;
//	   		}
	   		var filePath = $("#uploadfile").val();
	   		$.ajaxFileUpload({
				url:"<%=basepath%>/fileoperate/upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=123",
				fileElementId:'uploadfile',
				dataType: 'json',
                secureuri:false,
				type:'POST',
				success: function (data, status)
				{
					if(typeof(data.error) != 'undefined')
					{
						if(data.error != '')
						{
							alert(data.error);
						}else
						{
							alert(data.msg);
						}
					}else{
					//初始化组件
					   // document.write("ok!");
					}
				}, 
				error: function (data, status, e)
				{
					alert(e);
				}
	   		});

	   }

	   

</script>
<body>
<form id="updateForm" action="user?m=updateuserinfo" method="post">
<input type="hidden" name="userid" value="123"/>
<input type="hidden" name="username" value="123"/>
<div id="userinfo_edit">
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
     <col class="w220" />
     <col class="w600" />
     <tr>
       <td><div class="yhqx_touxiang">
       <img id="current_photo" src='' onerror="this.src='images/defaultheadsrc.png'" alt="用户头像" width="135" height="135" />
    </div></td>
    <td><p class="font_strong p_tb_10">头像修改：</p>
      <p>请选择您要上传的新照片：小于10M，格式要求.jpg、.JPG、.gif 、.Gif、.png 或.PNG</p>
      <p class="p_t_10">
        <input id="uploadfile" name="uploadfile" type="file" class="w360" />
      <a href="javascript:uploadImg();" class="an_blue_small">确定 </a></p></td>
  </tr>
</table>

</div>
  </form>

<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
</BODY>
</HTML>	