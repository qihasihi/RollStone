<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@page import="com.school.entity.UserInfo"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@include file="/util/common.jsp" %> 

<HTML>		
<HEAD>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="imagetoolbar" content="no">
<script src="<%=basepath %>js/common/ajaxfileupload.js"></script>
<script src="<%=basepath %>js/common/jquery.form.pack.2.24.js"></script>
<script src="<%=basepath %>js/common/jquery.imgareaselect.js"></script>
<link  rel="stylesheet" type="text/css" href="css/imgareaselect-default.css"/>
</HEAD>
<BODY>
<%
UserInfo user = (UserInfo)request.getAttribute("user");
%>
<script type="text/javascript">

$(document).ready(function () {
    
		$("#changePwsdForm").submit(function() {
			var options = {
				url : "user?m=changepassword", //提交给哪个执行
				type : "POST",
				dataType : "json",
				error : function() {
					alert('异常错误!系统未响应!');
				},
				success : function(rps) {
					if (rps.type == "success") {
						alert("修改成功!");
						closeModel("changePassword");
					} else {
						alert(rps.msg);
					}
				}
			};
			$("#changePwsdForm").ajaxSubmit(options);
			return false; //为了不刷新页面,返回false，反正都已经在后台执行完了，没事！
		});
	});
	
	function submitChangePassword() {
		if ($("#password").val().Trim().length < 1) {
			alert('请输入原始密码！');
			return false;
		}
		if ($("#new_password").val().Trim().length < 1) {
			alert('请输入新密码！');
			return false;
		}
		if ($("#new_password2").val().Trim().length < 1) {
			alert('请再次输入新密码！');
			return false;
		}
		if ($("#new_password").val() != $("#new_password2").val()) {
			alert('两次密码输入不一致！');
			return false;
		}
		$("#changePwsdForm").submit();
	}

	var imgwidth = 0;
	var imgheight = 0;
	var imgareaselectObj;
	function preview(img, selection) {
		if (!selection.width || !selection.height)
			return;

		var scaleX = 50 / selection.width;
		var scaleY = 50 / selection.height;

		$('#previewimg').css({
			width : Math.round(scaleX * imgwidth),
			height : Math.round(scaleY * imgheight),
			marginLeft : -Math.round(scaleX * selection.x1),
			marginTop : -Math.round(scaleY * selection.y1)
		});

		$('input[name="src"]').val(
		$(img).attr("src").replace('<%=basePath%>',''));
		$('input[name="x1"]').val(selection.x1);
		$('input[name="y1"]').val(selection.y1);
		$('input[name="x2"]').val(selection.x2);
		$('input[name="y2"]').val(selection.y2); 
	 
	}

function uploadImg(){
	   var t=document.getElementById('upload');
	   		if(t.value.Trim().length<1){
	   			alert('您尚未选择图片，请选择！');
	   			return;
	   		}
	   		var lastname=t.value.Trim().substring(t.value.Trim().lastIndexOf('.')).toLowerCase();;
	   		if(lastname!='.jpg'&&lastname!='.png'&&lastname!='.gif'&&lastname!='.bmp'){
	   			alert('您选择的图片不正确，目前只支持jpg,gif,png,bmp图片!');
	   			return;
	   		} 
	   		var filePath = $("#upload").val();
	   		$.ajaxFileUpload({
				url:"user?m=saveheadsrcfile", 
				fileElementId:'upload',
				dataType: 'json',
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
						showModel('cuthear_div',false);
						darray=data.success.split("|"); 
						$("#myimage").attr("src",darray[0]);
						$("#previewimg").attr("src",darray[0]);
						imgwidth=darray[1];
						imgheight=darray[2];   
						//显示出来
						$("#current_photo").attr("src",darray[0]);
						$("#cuthear_div").show('fast');
						imgareaselectObj=$('#myimage').imgAreaSelect({
							 aspectRatio: '1:1',
							 handles: true, 
							 instance:true,
							 fadeSpeed: 200, onSelectChange: preview
						});
						 
					}
				}, 
				error: function (data, status, e)
				{
					alert(e);
				}
	   		});
	 
	   }
	   //提交切割图片
	   
	   function subheadcut(){
	   	 var src=$('input[name="src"]').val();
		 var x1=$('input[name="x1"]').val();
		 var y1=$('input[name="y1"]').val();
		 var x2=$('input[name="x2"]').val();
		 var y2=$('input[name="y2"]').val();  
		 var userid=$('input[name="h_userid"]').val();
		 var username=$('input[name="h_username"]').val();  
	   	  
	   	  //参数是否正确
	   	if(src.length<1
	   		||x1.Trim().length<1||isNaN(x1.Trim())
	   		||x2.Trim().length<1||isNaN(x2.Trim())
	   		||y1.Trim().length<1||isNaN(y1.Trim())
	   		||y2.Trim().length<1||isNaN(y2.Trim())
	   	){
	   		alert("请选择截图区域，默认将按全图比例缩放！");
	   		return;
	   	}
	   	
	  $.ajax({
	 	url:"user?m=docuthead",
		type:"post",			
		dataType:'json',
		cache: false,
		data:{src:src,x1:x1,y1:y1,x2:x2,y2:y2,userid:userid,username:username},
		error:function(){
			alert('系统未响应，请稍候重试!');
		},
		success:function(json){
		    if(json.type == "error"){
		    	alert(json.msg);
		    }else{
		   	 	closeModel('cuthear_div');
		   	 	$("#current_photo").attr("src",json.objList[0]);
		    	imgareaselectObj.setOptions({remove:true,hide:true});   
		    } 
		}
	});  
}
	   
  function updateUserinfo(){
	var parms = {}
	parms.birthdate=$("#birthdate").val();
	parms.mailaddress=$("#mailaddress").val();
	$("#do_update").hide();
	$.ajax({
	 	url:"user?m=updateuserinfo",
		type:"post",			
		dataType:'json',
		cache: false,
		data:parms,
		error:function(){
        	$("#do_update").show();
			alert('异常错误!系统未响应!');
		},
	    success: function(rps){ 
      		$("#do_update").show();
			if(rps.type=="success"){
				alert("修改成功");
				selfDiv(1);
			}else{
				alert("修改失敗");
			}
        }
	        
	});  
  }		
</script>
<form id="updateForm" action="user?m=updateuserinfo" method="post">
<input type="hidden" name="userid" value="<%=user.getUserid() %>"/>
<input type="hidden" name="username" value="<%=user.getUsername() %>"/>
<div id="userinfo_edit">
    <p>姓名：<%=user.getRealname()%></p>
    <p>性别：<%=user.getUsersex()%></p>
    <p>国籍：</p>
    <p>民族：</p>
    <p>出生年月：<input  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"   id="birthdate"  name="birthdate" type="text" class="InputBorder W160"  value='<%=user.getBirthdate()!=null?user.getBirthdate():""%>'/>&nbsp;&nbsp;
        <select id="birthdayright">
            <option value="1">公开</option>
            <option value="2">公开月日</option>
            <option value="3">不公开</option>
        </select>
    </p>
    <p>特长爱好：<input type="text"/></p>
    <p>手机号：<input type="tesxt"/>&nbsp;&nbsp;
        <select id="phoneright">
            <option value="1">公开</option>
            <option value="2">指定人</option>
            <option value="3">不公开</option>
        </select>
    </p>
    <p>QQ：<input type="tesxt"/>&nbsp;&nbsp;
        <select id="qqright">
            <option value="1">公开</option>
            <option value="2">指定人</option>
            <option value="3">不公开</option>
        </select>
    </p>
    <p>家庭住址：<%=user.getAddress()%>&nbsp;&nbsp;
        <select id="addressright">
            <option value="1">公开</option>
            <option value="2">指定人</option>
            <option value="3">不公开</option>
        </select>
    </p>
    <a href="javascript:updateUserinfo();" class="an_blue_small">保存 </a>

</div>

<div id="userinfo_show">
        <p>姓名：<%=user.getRealname()%></p>
        <p>性别：<%=user.getUsersex()%></p>
        <p>国籍：</p>
        <p>民族：</p>
        <p>出生年月：<input  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"   id="birthdate"  name="birthdate" type="text" class="InputBorder W160"  value='<%=user.getBirthdate()!=null?user.getBirthdate():""%>'/>&nbsp;&nbsp;
            <select id="birthdayright">
                <option value="1">公开</option>
                <option value="2">公开月日</option>
                <option value="3">不公开</option>
            </select>
        </p>
        <p>特长爱好：<input type="text"/></p>
        <p>手机号：<input type="tesxt"/>&nbsp;&nbsp;
            <select id="phoneright">
                <option value="1">公开</option>
                <option value="2">指定人</option>
                <option value="3">不公开</option>
            </select>
        </p>
        <p>QQ：<input type="tesxt"/>&nbsp;&nbsp;
            <select id="qqright">
                <option value="1">公开</option>
                <option value="2">指定人</option>
                <option value="3">不公开</option>
            </select>
        </p>
        <p>家庭住址：<%=user.getAddress()%>&nbsp;&nbsp;
            <select id="addressright">
                <option value="1">公开</option>
                <option value="2">指定人</option>
                <option value="3">不公开</option>
            </select>
        </p>
        <a href="javascript:updateUserinfo();" class="an_blue_small">保存 </a>
</div>
</form>


</BODY>
</HTML>	