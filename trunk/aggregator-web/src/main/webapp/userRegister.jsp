<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-yhqx.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数字化校园</title>
<script type="text/javascript">
var nameState = false;

$(function(){
	$("#username").keyup(function(){
		checkUsername();
	});
});

function checkUsername(fn){
	var username = $("#username").val();
	
	if(username.length==0){
		$("#checkedState").html("请输入用户名");
		nameState = false;
		return;
	}
	if(username.length>0&&username.length<6){
		$("#checkedState").html("<font color='red'>用户名长度请不要小于6！</font>");
		nameState = false;
		return;
	}
	if(username.length>=4&&username.length<=20){
		$("#checkedState").html("<img src='img/validateload.gif'/>");
		$.ajax({
			type: "POST",
			dataType:"json",
			url: "user?m=checkusername",
			data: "username="+$("#username").val(),
			success: function(rps){     //根据后台返回的result判断是否成功!  
					if(rps.type=="success"){
						$("#checkedState").html("<font color='green'>用户名可以使用</font>");
						nameState = true;
							
					}else{
						$("#checkedState").html("<font color='red'>用户名已存在，请重新输入！</font>");
						nameState = false;;
					}
				}
			});
		return;
	}
	if(username.length>20){
		$("#checkedState").html("<font color='red'>用户名长度请不要大于20！</font>");
		nameState = false;
		return;
	}
}

function doRegister(){
	if(!checkForm())
		return;
	var parms={};
	parms.username=$("#username").val().Trim();
	parms.password=$("#password").val().Trim();
	parms.mailaddress=$("#mailaddress").val().Trim();
	parms.realname=$("#realname").val().Trim();
	parms.role=$("#role").val().Trim();
	parms.gender=$("input[type='radio'][name='gender']:checked").val().Trim();
	
	$("#do_register").hide();
	$.ajax({
		url:'user?m=userregister',
		type:'POST',
		data:parms,
		dataType:'json',
		error:function(){$("#do_register").show();alert("网络异常")},
		success:function(rps){
		    $("#do_register").show();
			if(rps.type=="success"){ 
				alert(rps.msg);
				window.location.href="login.jsp";
			}else{
				alert(rps.msg);
			}
		}
	});
}

function checkForm(){
	if($("#username").val().Trim().length<1){
		alert('用户名不能为空!');
		return;
	}
	if(/.*[\u4e00-\u9fa5]+.*$/.test($("#username").val().Trim())){   
		alert('不能含有汉字！');   
		return false;   
	}  
	if($("#username").val().Trim().split(" ").length>1){   
		alert('用户名不能含空格！');   
		return false;   
	} 
	if($("#password").val().Trim().length<1){
		alert('密码不能为空!');
		return false;
	}
	if($("#passwordcheck").val()!=$("#password").val()){
		alert('请确认密码!');
		return false;
	}
	if($("#password").val().Trim().split(" ").length>1){   
		alert('密码不能含空格！');   
		return false;   
	} 
	if($("#mailaddress").val().Trim().length<1){
		alert('请输入您常用的邮箱地址!');
		return false;
	}
	if($("#realname").val().Trim().length<1){
		alert('请输入您的真实姓名!');
		return false;
	}
	if($("#role").val()==0){
		alert('请选择您的身份!');
		return false;
	}
	if($("input[type='radio'][name='gender']:checked").val()==null){
		alert('请选择性别!');
		return false;
	}
	return true;
}
</script>
</head>

<body>
<%@include file="/util/head.jsp" %>
<div class="jxpt_nav">
  <p class="f_left">当前位置：首页 &gt; 教学平台</p>
  <p><strong>卢艳</strong>您好! 您现在身份是
    <select name="select" id="select">
      <option>教务</option>
      <option>年级组长</option>
      <option>教研组长</option>
    </select>
  </p>
</div>

<div class="jxpt_layout">
<p class="jxpt_icon01"><span><a href="1">批量导入用户</a></span>个人信息</p>
<div class="p_20">
<form action="user?m=userregister" method="post" name="registerForm" id="registerForm">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a zt14">
    <col class="w100"/>
    <col class="w720"/>
    <tr>
      <th>用&nbsp;户&nbsp;名：</th>
      <td><input id="username" name="username" type="text" class="public_input w200" />
      <span id="checkedState">请输入用户名</span></td>
    </tr>
    <tr>
      <th>密&nbsp;&nbsp;&nbsp;&nbsp;码：</th>
      <td><input id="password" name="password" type="password" class="public_input w200" /></td>
    </tr>
    <tr>
      <th>确认密码：</th>
      <td><input id="passwordcheck" name="passwordcheck" type="password" class="public_input w200" /></td>
    </tr>
    <tr>
      <th>用户身份：</th>
      <td><select name="role" id="role">
        <OPTION selected value="0">--请选择--</OPTION>
<OPTION  value="2">教师</OPTION>
      </select></td>
    </tr>
    <tr>
      <th>真实姓名：</th>
      <td><input id="realname" name="realname" type="text" class="public_input w200" /></td>
    </tr>
    <tr>
      <th>性&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
      <td>
        <input type="radio" name="gender" id="radio" value="1" />
        男&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" name="gender" id="radio2" value="0" />
        女</td>
    </tr>
    <tr>
      <th>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</th>
      <td><input id="mailaddress" name="mailaddress" type="text" class="public_input w330" /><span class="font-blue1 p_l_10">用于找回密码！</span></td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td><a href="javascript:doRegister();" class="an_blue">注&nbsp;册</a></td>
    </tr>
  </table>
</form>
</div>
</div>

<div class="foot">主办方：*****分校&nbsp;&nbsp;&nbsp;&nbsp;协办方：北京四中龙门网络教育技术有限公司</div>
</body>
</html>
