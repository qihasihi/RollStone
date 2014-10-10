<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数字化校园</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/yhqx.css"/>
    <script type="text/javascript">

    var state = "${state}";
    var sign = "login${login_hour}"; 
     
    function dosub(){
    	$("#lg").hide();
   		$("#lging").show();
   		var autoLogin=0;
   		if($("#autoLogin").attr("checked")==true)
   			autoLogin=1;
   		var remember=0;
   		if($("#remember").attr("checked")==true)
   			remember=1;
   		$.ajax({  
    		type: "POST",
    		dataType:"json",
    		url: "user?m=dologin",
    		data: {username:$("#username").val().Trim(),
    			password:$("#password").val().Trim(),
    			autoLogin:autoLogin,
    			remember:remember},
    		error:function(){alert('网络异常!');
    		$("#lg").show();
		   		$("#lging").hide();},
    		success: function(rps){     //根据后台返回的result判断是否成功!  
   				if(rps.type=="success"){
   					var href=location.href;
   					if(href.indexOf("/sz_school/user?m=userquit")!=-1){
   					href="user?m=toIndex";
	   				}else if(href.indexOf('/sz_school/')!=-1){
	   					href=href.substring(href.indexOf('/sz_school/')+11);
	   					if(href.Trim().length<1)
	   						href="user?m=toIndex"; 
	   				}
   					window.location.href=href;
   				}else{
   					$("#lg").show();
   			   		$("#lging").hide();
   					alert(rps.msg);
   				}
   			}
   		});
    }
   
   	function checkLoginForm(){
   		var username = $("#username");
   		var password = $("#password");
   		if(username.val().Trim().length<1){    			
   			alert("请输入用户名!</span>");
   			username.focus();
   			return;
   		}
   		if(password.val().Trim().length<1){
   			alert("请输入密码!</span>");
   			password.focus();
   			return;
   		}
   		
		var myDate = new Date();
		if(sign!="login"&&sign!="login"+myDate.getHours()){
			loginInfoCheck(true);
			return;
		}
		dosub();   		
   	}
   	
	$(function(){
		$("#password").keyup(function(event){
			 if(event.keyCode == 13){
				  $("#lgnBtn").click();
		       }
		});
		
		if(state!=null&&state.length>0)
			if(state=="no_login")
				alert("您尚未登录!");
		loginInfoCheck(false);
	});
	
	function loginInfoCheck(type){
   		$.ajax({
			url: "user?m=logincheck",
			type:"get",
			dataType:'json',
			cache: false, 
			error:function(){},
			success:function(rps){
				if(rps.type=="success"){
					if(rps.objList!=null && rps.objList.length>0){
						$("#remember").attr("checked",true);
						if(rps.objList[0].u_a=="auto")
							$("#autoLogin").attr("checked",true);
						$("#username").val(rps.objList[0].u_n);
						$("#password").val(rps.objList[0].u_p);
					}
					
					if(rps.objList[0].u_a=="auto"){
						if(state==null || state!="user_quit" ){
							$("#p_pb").show();
							countDown(3);
						}
					}
					
					if(type){
						dosub();
					}
				}
			}
		});
   	}
	
	function countDown(i){
		$("#Prompt_box").html("检测到账号，"+i+"秒后自动登录....");
		if(i>0)
			window.setTimeout("countDown("+(i-1)+")", 1000);;
		if(i==0)
			dosub();
		
	}
    </script>
</head>

<body class="yhqx_denglu_bg">
<div class="yhqx_denglu">
<div>
  <p><img src="images/logo_sz.png" width="361" height="114" /></p>
  <p class="input">用户名：
  <input id="username" name="username" type="text" />
  </p>
  <p class="input">密　码：
    <input id="password" name="password" type="password" />
  </p>
  <p id="lg"><a href="javascript:checkLoginForm();" class="yhqx_denglu_an"><span id="lgnBtn">登录</span></a>
  <p id="lging" style="display:none;"><span class="yhqx_denglu_an"><span id="lgnBtn">正在登录 . . .</span></span>
  <!-- <a href="userRegister.jsp">注册</a> --></p>
  <p id="p_pb" style="width:391px;height:20px;display:none;"><span id="Prompt_box" style="font-size:15px;float:right;"></span></p>
  <p class="text">  
    <input type="checkbox" name="remember" id="remember" value="1"/>
  记住账号密码<span class="p_lr_15">|</span>
  <input type="checkbox" name="autoLogin" id="autoLogin" value="1"/>
  自动登录 </p>  
</div>  
</div>

<div id="dv_test"></div>

</body>
</html>