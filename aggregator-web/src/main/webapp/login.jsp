<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.filter.BaseInterceptor" %>
<%
    if(UtilTool._IS_SIMPLE_RESOURCE==2){
        response.sendRedirect("user?m=simpleResLogin");return;
    }
    String pcname=UtilTool.utilproperty.getProperty("PROC_NAME");

%>
<%@ include file="/util/common.jsp" %>

<html>
<head>
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
   					if(href.indexOf("/<%=pcname%>/user?m=userquit")!=-1){
   					    href="user?m=toIndex";
	   				}else if(href.indexOf('/<%=pcname%>/')!=-1){
	   					href=href.substring(href.indexOf('/<%=pcname%>/')+<%=pcname.length()+2%>);
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
   			alert("用户名不能为空，请输入用户名!");
   			username.focus();
   			return;
   		}
   		if(password.val().Trim().length<1){
   			alert("密码不能为空，请输入密码!");
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
				  checkLoginForm();
		       }
		});

		if(state!=null&&state.length>0)
			if(state=="no_login")
				alert("您尚未登录！");
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
<body>

<%
    String logoSrc=UtilTool.utilproperty.getProperty("LOGO_SRC");
%>
<div id="denglu_layout">
    <div class="denglu">
        <p class="logo"><span></span><img src="<%=basePath %>images/<%=logoSrc %>" width="253" height="64" /></p>
        <p><input id="username" name="username" type="text" /></p>
        <p><input id="password" name="password" type="password" /></p>
        <p class="info">
            <input type="checkbox" name="remember" id="remember" value="1">
            记住账号密码 ｜
            <input type="checkbox" name="autoLogin" id="autoLogin" value="1">
            自动登录</p>
        <p class="an_denglu"><a href="javascript:checkLoginForm();"  id="lgnBt" title="登录" ></a></p>
    </div>
</div>
</body>
</html>