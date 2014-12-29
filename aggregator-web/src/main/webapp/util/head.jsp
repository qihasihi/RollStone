<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.school.manager.SmsReceiverManager"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.SmsReceiver"%>
<%@page import="com.school.dao.SmsReceiverDAO"%>
<%@ page import="com.school.entity.EttColumnInfo" %>
<%@ page import="com.school.util.MD5_NEW" %>
<%@ page import="com.school.entity.SchoolLogoInfo" %>
<%
     //如果是乐知行过来，则不显示头尾
 Object fromType=session.getAttribute("fromType");
    String pageType=null;
  	if(request.getAttribute("pageType")!=null)
  		pageType=(String)request.getAttribute("pageType");

    SmsReceiver smsreceiver = new SmsReceiver();
	UserInfo sms_user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
	if(null!=sms_user)
		smsreceiver.setReceiverid(sms_user.getUserid());
	else
		smsreceiver.setReceiverid(0);
	smsreceiver.setStatus(0);
	ApplicationContext acc = WebApplicationContextUtils.getWebApplicationContext(application);
	SmsReceiverManager  smsReceiverManagerac=(SmsReceiverManager) acc.getBean("smsReceiverManager");
	List receiveSMSList = smsReceiverManagerac.getList(smsreceiver, null);
     //String logoSrc=UtilTool.utilproperty.getProperty("LOGO_SRC");


    List<EttColumnInfo> ettColumnInfos =(List<EttColumnInfo>)request.getSession().getAttribute("ettColumnList");
    //logo
    SchoolLogoInfo logoObj = (SchoolLogoInfo)request.getSession().getAttribute("logoObj");

    String logosrc = null;
    if(logoObj!=null){
        logosrc=UtilTool.utilproperty.get("RESOURCE_FILE_PATH_HEAD")+"/"+logoObj.getLogosrc();
    }


    //加载网校联系人
    StringBuilder webimUrl=new StringBuilder("user?m=toEttUrl");
    if(isStudent){
        webimUrl.append("&mid=-1");
    }else{
        webimUrl.append("&isVip=0");
    }
  %>
<%--如果Ett网校帐号未绑定进入，则要加载fancybox--%>
<c:if test="${empty sessionScope.CURRENT_USER.ettuserid}">
    <script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.js"></script>
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css"/>
</c:if>
<c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
    <%if(modelType==2){%>
    <div class="jxxt_lzx_header">
    <%}else{%>
    <div id="header">
    <%}%>
</c:if>

<c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='ett'}">
    <%if(modelType==2){%>
        <div class="jxxt_lzx_header">
    <%}else{%>
         <div id="header">
    <%}%>
</c:if>
<script type="text/javascript">

    function loadWeImRight(){

        $.ajax({
            url:"user?m=loadJIDRight",
            dataType:'json',
            type:'post',
            cache: false,
            async:false,
            error:function(){
                alert('当前网络不稳定，请重试!');
            },success:function(rps){
                if(rps.type=="success"){
                  if(rps.objList[0]==0){
                      $("#li_web_im").load("util/webim.jsp",function(){
                          $("#webimopen").click();
                      });
                  }else if(rps.objList[0]==1){
                      <%--$("#a_open_toUrl").unbind("click");--%>
                      <%--$("#a_open_toUrl").bind("click",function(){--%>
                          <%--window.open("<%=webimUrl%>");--%>
                      <%--});--%>
                      <%--$("#a_open_toUrl").click();--%>
                      $("#dv_ettModelRegisterAccount").load("user?m=toBindEttAccount",function(){
                          $("#dv_ettModelRegisterAccount").show();
                          //如果是WEBIM.则根据此次判断
                          $("#dv_ettModelRegisterAccount input[name='toUrl']").val("WEBIM");
                          $("#a_ettmodel").click();
                      });
                  }
                }else
                    alert(rps.msg);
            }
        })
    }

    //乐知行
    function loadLZXWeImRight(){
        $.ajax({
            url:"user?m=loadJIDRight",
            dataType:'json',
            type:'post',
            cache: false,
            error:function(){
                alert('当前网络不稳定，请重试!');
            },success:function(rps){
                if(rps.type=="success"){
                    if(rps.objList[0]==0){
                        $("#li_web_im").load("util/webim.jsp",function(){
                            //加载
                            $("#webimopen").click();
                        });
                    }else{
                        showModel("dv_register_ettAccount",false);
                    }
                }else
                    alert(rps.msg);
            }
        })
    }

    function lzxLoadEttUserName(){
        $.ajax({
            url:"tpuser?loadEttUserName",
            type:"post",
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },
            success:function(json){
                if(json.objList[0]!=null&&json.objList[0].toString().length>0){
                    $("#u_userName").val(json.objList[0]);
                    $("#oldUserName").val(json.objList[0]);
                }
                if(typeof(isStudent)!="undefined"&&!isStudent){
                    $("#u_userName").attr("disabled",true);
                }
            }
        });
    }
    //验证当前帐号是否已经绑定了帐号
    //乐知行
    function loadEttModelJID(etUrl){
        var toEttUrl=etUrl;
        $.ajax({
            url:"user?m=loadJIDRight",
            dataType:'json',
            type:'post',
            cache: false,
            async:false,
            error:function(){
                alert('当前网络不稳定，请重试!');
            },success:function(rps){
                if(rps.type=="success"){
                    if(rps.objList[0]==0){
                        //如果是0，表示有存在的
                        $("#a_open_toUrl").unbind("click");
                        $("#a_open_toUrl").bind("click",function(){
                            window.open(toEttUrl);
                        });
                        $("#a_open_toUrl").click();
                    }else{
//                        showModel("dv_register_ettAccount",false);
                          //如果没有绑定，则加载fancybox.mousewheel和fancybox js,完后再加载相关页面,
                            //显示
                          $("#dv_ettModelRegisterAccount").load("user?m=toBindEttAccount",function(){
                              $("#dv_ettModelRegisterAccount").show();
                              //如果是WEBIM.则根据此次判断
                              $("#dv_ettModelRegisterAccount input[name='toUrl']").val(toEttUrl);
                              $("#a_ettmodel").click();
                          });

                    }
                }else
                    alert(rps.msg);
            }
        })
    }


    $(function(){
        $("#li_web_im a").attr("data-href","<%=webimUrl%>");
        //定义fancybox
        <c:if test="${empty sessionScope.CURRENT_USER.ettuserid}">
            v_fancyboxObj=$("#a_ettmodel").fancybox({
                'onClosed':function(){
                    $("#dv_ettModelRegisterAccount").hide();
                }
            });
        </c:if>
    })
</script>
<c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='lzx'&&sessionScope.fromType!='ett')}">
<div id="header">
</c:if>
  <ul>
    <!--如果不是乐知行，网校进入，则显示首页-->
      <c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='ett'&&sessionScope.fromType!='lzx')}">
          <li <%= (pageType!=null&&pageType=="index"?"class='one crumb'":"one")%>><a href="<%=basePath %>user?m=toIndex">首&nbsp;页</a></li>
      </c:if>
      <!--如果不是乐知行,则显示爱学，应用-->
    <c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='lzx'&&sessionScope.fromType!='ett')}">
          <%
              if(sms_user.getEttuserid()!=null&&sms_user.getDcschoolid()!=null){%>
                <%@include file="webim.jsp"%>
          <%}else{%>
              <li class="three" id="li_web_im">
                  <a  href="javascript:loadWeImRight();">爱&nbsp;学</a>
              </li>
          <%}%>
     </c:if>
      <!--如果是网校进入，则显示应用-->
      <c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='ett'&&sessionScope.fromType!='lzx')}">
          <li class="five"><a href="APP.html" target="_blank">应&nbsp;用</a></li>
      </c:if>

      <!--如果不是乐知行,网校进入，则显示退出-->
      <c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='ett'&&sessionScope.fromType!='lzx')}">
          <li class="four"><a href="javascript:;" onclick="loginDestory('<%=basePath %>')">退出</a></li>
      </c:if>
      <!--乐知行-->
    <c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
        <%
        if(modelType==2){//||modelType==1
            %>
        <li class="three" id="li_web_im"><a href="javascript:loadLZXWeImRight();">爱学</a></li>
        <%
        }
            if(isTeacher&&!isStudent&&modelType==2){
            //加载乐知行，教学首页，左上角的链接
            if(ettColumnInfos!=null&&ettColumnInfos.size()>0){
                String headcolumnico=UtilTool.utilproperty.getProperty("LZX_HEAD_COLUMN_ICO");
                for (EttColumnInfo ectmp:ettColumnInfos){
                    if(ectmp!=null&&ectmp.getIsShow()==0){
                        String cls="";
                        if(headcolumnico.indexOf(ectmp.getEttcolumnid().toString())!=-1){
                            String tmpa=headcolumnico.substring(headcolumnico.indexOf(ectmp.getEttcolumnid().toString()));
                            String[] tmparray=null;
                            if(tmpa.indexOf(",")!=-1)
                                 tmparray=tmpa.substring(0,tmpa.indexOf(",")).split("\\|");
                            else
                                tmparray=tmpa.split("\\|");
                            if(tmparray.length>1)
                                cls=tmparray[1];
                        }
                        if(cls!=null&&cls.trim().equals("two")){
                            %>
                            <li class="<%=cls%>" style="background-position: -150px -50px;" ><a href="javascript:;" onclick="loadEttModelJID('<%=ectmp.getEttcolumnurl()%>&isVip=<%=ectmp.getStatus()%>')"><%=ectmp.getEttcolumnname()%></a></li>
                        <%
                           }else{
                        %>
                        <li class="<%=cls%>" style="background-position:0px -50px;"><a href="javascript:;" onclick="loadEttModelJID('<%=ectmp.getEttcolumnurl()%>&isVip=<%=ectmp.getStatus()%>')"><%=ectmp.getEttcolumnname()%></a></li>
                     <%
                           }
                        }
                }
            }
          }
        %>
        <%--<li class="two"><a href="" target="_blank"></a></li>--%>
        <%--<li class="one"><a href="1" target="_blank">四中公开课</a></li>--%>
        <%--<li><a href="1" target="_blank">远程教研</a></li>--%>
    </c:if>
   <!-- <li <%= (pageType!=null&&pageType=="userview"?"class='two crumb'":"two")%>><a href="user?m=userview">个人主页</a></li>
    <li class="three"><a href="sms?m=inbox" target="_blank">
  	消息中心<%=receiveSMSList!=null&&receiveSMSList.size()>0?"(<font color='red'>"+receiveSMSList.size()+"</font>)":"" %>
  	</a></li>-->
    <c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='ett'}">
    </c:if>
  </ul>
<c:if test="${empty sessionScope.fromType||(sessionScope.fromType!='lzx'&&sessionScope.fromType!='ett')}">
 <p><span></span><img src="<%=basePath %><%=logosrc %>" width="253" height="64"/></p>
</c:if>
<c:if test="${!empty sessionScope.fromType&&(sessionScope.fromType=='lzx'||sessionScope.fromType=='ett')}">
    <%if(modelType==2){%>
    <p style="width:210px;height:50px"><span></span></p>
    <%}%>
    <%if(modelType==1){%>
      <p><span></span><img src="images/logo_140820.jpg" width="253" height="64"/></p>
    <%}%>
</c:if>
</div>
<div style="display:none" id="dv_ettModelRegisterAccount">
</div>
<a id="a_ettmodel" href="#dv_ettModelRegisterAccount"></a>
<a id="a_open_toUrl" href="javascript:;"></a>

<c:if test="${!empty sessionScope.fromType&&sessionScope.fromType=='lzx'}">
    <div style="display:none" id="dv_modify_ettAccount">
        <div class="jxxt_lzx_float">
            <div class="menu">
                <p class="close"><a href="javascript:;"
                                    onclick="closeModel('dv_modify_ettAccount');u_password.value='';u_new_password.value='';sp_m_uname_isright='6-12字符/6个汉字';p_u_pass_isright.innerHTML=p_u_newpass_isright.innerHTML='6-12字符';"
                                    title="关闭"></a></p>
                <h1>修改爱学账号</h1>
                <div class="input"> <span class="one"><strong>用&nbsp;户&nbsp;名</strong>
                  <input type="text" name="u_userName" maxlength="12" id="u_userName" onblur="validateUName('u_userName','p_m_uname_msg','sp_m_uname_isright',<%=(isStudent?"true":"false")%>);"/>
                     <input type="text" name="oldUserName" style="display:none" id="oldUserName"/>
                  </span><span id="sp_m_uname_isright">6-12字符/6个汉字</span>
                    <p class="two" id="p_m_uname_msg"></p>
                </div>
                <div class="input"> <span class="one"><strong>旧&nbsp;密&nbsp;码</strong>
                  <input type="password" name="u_password" id="u_password"/><!-- onblur="validatePass('u_password','p_u_pass_msg','p_u_pass_isright')"-->
                  </span><a href="javascript:;" onclick="changePassType('u_password','p_u_pass_msg','p_u_pass_isright')" class="ico92" title="显示密码"></a><span id="p_u_pass_isright">6-12字符</span>
                    <p class="two" id="p_u_pass_msg"></p>
                </div>
                <div class="input"> <span class="one"><strong>新&nbsp;密&nbsp;码</strong>
                <input type="password" name="u_new_password"  id="u_new_password" onblur="validatePass('u_new_password','p_u_new_pass_msg','p_u_newpass_isright',true);"/>
                    </span><a href="javascript:;" onclick="changePassType('u_new_password','p_u_new_pass_msg','p_u_newpass_isright')"  class="ico92" title="显示密码"></a><span id="p_u_newpass_isright">6-12字符</span>
                    <p class="two" id="p_u_new_pass_msg"></p>
                </div>
                <p class="an"><a href="javascript:;" onclick="doModifyEttAccount()">修&nbsp;&nbsp;改</a></p>
            </div>
        </div>

    </div>




    <div style="display: none" id="dv_register_ettAccount">
    <div class="jxxt_lzx_float" id="dv_do_register">
        <div class="menu">
            <p class="close"><a href="javascript:;"
                                onclick="closeModel('dv_register_ettAccount');userName.value='';password.value='';email.value='';u_password.value='';u_new_password.value='';sp_uname_isright.innerHTML='6-12字符/6个汉字';p_pass_isright.innerHTML='6-12字符';p_email_isright.innerHTML='';"
                                title="关闭"></a></p>
            <h1>注册爱学账号</h1>
            <div class="input">
           <span class="one"><strong>用&nbsp;户&nbsp;名</strong>
             <input type="text" name="userName" id="userName" onblur="validateUName('userName','p_uname_msg','sp_uname_isright')" maxlength="12"/>

           </span><span id="sp_uname_isright">6-12字符/6个汉字</span>
                <p class="two" id="p_uname_msg"></p>
            </div>
            <div class="input">
           <span class="one"><strong>密&nbsp;&nbsp;&nbsp;&nbsp;码</strong>
            <input type="password" maxlength="12" onblur="validatePass('password','p_pass_msg','p_pass_isright')" name="password" id="password" /></span>
                <a href="javascript:;" onclick="changePassType('password','p_pass_msg','p_pass_isright')" class="ico92" title="显示密码"></a>
                <span id="p_pass_isright">6-12字符</span>
                <p class="two" id="p_pass_msg"></p>
            </div>
            <div class="input">
           <span class="one"><strong>邮箱地址</strong>
            <input type="text" name="email" id="email"  onblur="validateEmail('email','p_email_msg','p_email_isright')"/></span><span id="p_email_isright"></span></a>
                <p class="two" id="p_email_msg"></p>
            </div>
            <p class="an"><a href="javascript:;" onclick="doRegister()">确&nbsp;&nbsp;定</a></p>
        </div>
    </div>
         <div class="jxxt_lzx_float" id="dv_register_ok" style="display:none">
            <div class="menu">
                <p class="close"><a href="javascript:;" onclick="closeModel('dv_register_ettAccount')" title="关闭"></a></p>
                <h2 title="注册成功"></h2>
                <p class="an"><a href="javascript:;" onclick="loadWeImRight();closeModel('dv_register_ettAccount');">立即体验</a></p>
            </div>
        </div>
    </div>
    <!--注删-->
    <script type="text/javascript">
        <%--网校WEBIm使用方法--%>
        //修改相关信息
        function doModifyEttAccount(){
            var uNameValidateBo=isStudent?validateUName('u_userName','p_m_uname_msg','sp_m_uname_isright',false):true;
            var oldPassValidateBo=true;//validatePass('u_password','p_u_pass_msg','p_u_pass_isright');
            var newPassValidateBo=validatePass('u_new_password','p_u_new_pass_msg','p_u_newpass_isright');
            if(uNameValidateBo){
                if(isStudent){
                   if($("#sp_m_uname_isright .ico12").length<1){
                       alert('用户名不符合标准，请更改!');return;
                   }
                }
                if(oldPassValidateBo&&newPassValidateBo){
                    var param={};
                    param.oldUserName=$("#oldUserName").val().Trim();
                    param.newUserName=$("#u_userName").val().Trim();
                    param.oldPwd=$("#u_password").val().Trim();
                    param.newPwd=$("#u_new_password").val().Trim();

                    $.ajax({
                        url:'tpuser?modifyEttUser',
                        dataType:'json',
                        type:'POST',
                        data:param,
                        cache: false,
                        error:function(){
                            alert('系统未响应!');
                        },success:function(rps){
                            if(rps.type=='error'){
                                if(rps.objList[0]!=null&&rps.objList[0].toString().length>0){
                                    alert(rps.objList[0]);
                                }
                            }else{
                                alert("修改成功!");
                            }
                        }
                    });
                }
            }
        }
        //注册
        function doRegister(){
            var uNameValidateBo=validateUName('userName','p_uname_msg','sp_uname_isright',false);
            var passValidateBo=validatePass('password','p_pass_msg','p_pass_isright');
            var emailValidateBo=validateEmail('email','p_email_msg','p_email_isright');
            //用户名通过
            if(uNameValidateBo&&$("#sp_uname_isright .ico12").length>0){
               //密码通过,邮箱通过
                if(passValidateBo&&emailValidateBo){
                    $.ajax({
                        type: "POST",
                        dataType:"json",
                        url: "operateEtt?m=doRegisterEttAccount",
                        data: {
                                userName:$("#userName").val().Trim(),
                                password:$("#password").val().Trim(),
                                email:$("#email").val().Trim()
                            },
                        error:function(){
                            alert('网络不通畅!');
                        },
                        success: function(rps){     //根据后台返回的result判断是否成功!
                            if(rps.type=="success"){
                                $("#dv_do_register").hide();
                                $("#dv_register_ok").show();
                            }else{
                               alert(rps.msg);
                            }
                        }
                    });
                }
            }
        }


        /**
         *验证用户名是否存在
         * @param cdataController
         * @param pmsgid
         * @param spisrightid
         */
        function validteHasUName(cdataController,pmsgid,spisrightid){
            var oldUName=$("#oldUserName").val();
            var odUName=(typeof(oldUName)=="undefined"?"":oldUName.Trim());
            var p={userName:cdataController.val().Trim()};
            if(odUName.length>0)
                p.oldUserName=odUName;
            //验证是否有重名
            $.ajax({
                type: "POST",
                dataType:"json",
                url: "operateEtt?m=validateUserName",
                data:p,
                error:function(){
                    $("#"+spisrightid).html("6-12字符/6个汉字");
                    $("#"+pmsgid).html("网络不通畅!");
                },
                success: function(rps){     //根据后台返回的result判断是否成功!
                    if(rps.type=="success"){
                        $("#"+pmsgid).html("");
                        $("#"+spisrightid).html('<a href="javascript:;" class="ico12" title="正确"></a>');
                    }else{
                        $("#"+pmsgid).html(rps.msg);
                        $("#"+spisrightid).html("6-12字符/6个汉字");
                    }
                }
            });
        }
        /**
         *   密码框可见
         * @param idController
         */
        function changePassType(idController,msgid,pisrightid){
            var controlObj=$("#"+idController);
            var ty=$("#"+idController).attr("type");
            var val=$("#"+idController).val();
            //$("#"+idController).replaceWith();
            controlObj.replaceWith('<input value="'+val+'" type="'+(ty=='password'?'text':'password')+'" maxlength="12" onblur="validatePass(\''+controlObj.attr("id")+'\',\''+msgid+'\',\''+pisrightid+'\')" name="'+controlObj.attr("name")+'" id="'+controlObj.attr('id')+'" />').clone().insertAfter().remove();
        }
        function validateEmail(controlid,pmsgid,spisrightid){
            $("#"+spisrightid).html("");
            var cdataController=$("#"+controlid);
            if(cdataController.val().Trim().length<1){
                $("#"+pmsgid).html("邮箱不能为空，请更改");
                return false;
            }
            //验证空格
            var reg=/^([\w\.-]{4,18})@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
            if(!reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("邮箱格式不符合要求!请更改");
                return false;
            }
            $("#"+pmsgid).html("");
            $("#"+spisrightid).html('<a href="javascript:;" class="ico12" title="正确"></a>');
            return true;
        }


        //验证密码
        function validatePass(controlid,pmsgid,spisrightid){
            $("#"+pmsgid).html("");
            var cdataController=$("#"+controlid);
            if(cdataController.val().Trim().length<6){
                $("#"+pmsgid).html("密码不能少于6个字符或多于12个字符!请更改");
                $("#"+spisrightid).html("6-12字符");
                return false;
            }
            //验证空格
            var reg=/\s/;
            if(reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("密码不符合要求!请更改");
                $("#"+spisrightid).html("6-12字符");
                return false;
            }
            //验证密码格式
            reg=/^[a-zA-Z0-9_]+$/;
            if(!reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("密码不符合要求!请更改");
                $("#"+spisrightid).html("6-12字符");
                return false;
            }
            //验证密码是否有数字
            reg=/[\d{1,}]+/;
            if(!reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("密码必须由字符与数字组成!请更改");
                $("#"+spisrightid).html("6-12字符");
                return false;
            }
            //验证密码是否有数字
            reg=/[a-zA-Z_]+/;
            if(!reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("密码必须由字符与数字组成!请更改");
                $("#"+spisrightid).html("6-12字符");
                return false;
            }
            $("#"+spisrightid).html('<a href="javascript:;" class="ico12" title="正确"></a>');
            return true;
        }
        //验证用户名
        function validateUName(controlid,pmsgid,spisrightid,isValidateHas){
            var cdataController=$("#"+controlid);
            var uNameLength=checkStrLength(cdataController.val().Trim());
            if(uNameLength<6||uNameLength>12){
                $("#"+pmsgid).html("用户名不能少于6个字或多于12个字!请更改");
                $("#"+spisrightid).html("6-12字符/6个汉字");
                return false;
            }
            //验证用户名是否存在空格
            var reg=/\s/;
            if(reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("用户名不符合要求!请更改");
                $("#"+spisrightid).html("6-12字符/6个汉字");
                return false;
            }
            //验证用户名格式
            reg=/^[a-zA-Z0-9_\u0391-\uFFE5]+$/
            if(!reg.test(cdataController.val().Trim())){
                $("#"+pmsgid).html("用户名不符合要求!请更改");
                $("#"+spisrightid).html("6-12字符/6个汉字");
                return false;
            }

            if(typeof(isValidateHas)=="undefined"||isValidateHas==true){
                $("#"+spisrightid).html("<img src='images/loading_smail.gif'/>");
                validteHasUName(cdataController,pmsgid,spisrightid);

            }else{
                $("#"+pmsgid).html("");
                $("#"+spisrightid).html('<a href="javascript:;" class="ico12" title="正确"></a>');
            }
            return true;
        }
    </script>
</c:if>
