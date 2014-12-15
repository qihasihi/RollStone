<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-11-20
  Time: 上午10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<script type="text/javascript">
    <%--网校栏目使用方法--%>

    /**
     *   密码框可见
     * @param idController
     */
    function changeEttPassType(idController,msgid,pisrightid){
        var controlObj=$("#"+idController);
        var ty=$("#"+idController).attr("type");
        var val=$("#"+idController).val();
        //$("#"+idController).replaceWith();
        controlObj.replaceWith('<input value="'+val+'" type="'+(ty=='password'?'text':'password')+'" maxlength="12" onblur="validateEttPass(\''+controlObj.attr("id")+'\',\''+msgid+'\',\''+pisrightid+'\')" name="'+controlObj.attr("name")+'" id="'+controlObj.attr('id')+'" />').clone().insertAfter().remove();
    }
    function validateEttEmail(controlid,pmsgid,spisrightid){
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
    function validateEttPass(controlid,pmsgid,spisrightid){
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
    function validateEttUName(controlid,pmsgid,spisrightid,isValidateHas){
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
                checkUName(cdataController,pmsgid,spisrightid);
        }else{
            $("#"+pmsgid).html("");
            $("#"+spisrightid).html('<a href="javascript:;" class="ico12" title="正确"></a>');
        }
        return true;
    }
    /**
     *验证用户名是否存在
     * @param cdataController
     * @param pmsgid
     * @param spisrightid
     */
    function checkUName(cdataController,pmsgid,spisrightid){
        var p={userName:cdataController.val().Trim()};
        //验证是否有重名
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "operateEtt?m=checkEttUserName",
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

    //注册网校帐号
    function doRegisterEttAccount(){
        var uNameValidateBo=validateEttUName('add_ettUserName','p_add_msg_ettUserName','sp_add_ettUserName_isRight',false)
        var passValidateBo=validateEttPass('add_ettPass','p_add_msg_ettPass','sp_add_ettPass_isRight');
        var emailValidateBo=validateEttEmail('add_ettEmail','p_add_msg_ettEmail','sp_add_ettEmail_isRight');
        //用户名通过
        if(uNameValidateBo&&$("#sp_add_ettUserName_isRight .ico12").length>0){
            //密码通过,邮箱通过
            if(passValidateBo&&emailValidateBo){
                $.ajax({
                    type: "POST",
                    dataType:"json",
                    async:false,
                    url: "operateEtt?m=registerEttAccount",
                    data: {
                        userName:$("#add_ettUserName").val().Trim(),
                        password:$("#add_ettPass").val().Trim(),
                        email:$("#add_ettEmail").val().Trim()
                    },
                    error:function(){
                        alert('网络不通畅!');
                    },
                    success: function(rps){     //根据后台返回的result判断是否成功!
                        if(rps.type=="success"){
                            $("#a_open_toUrl").unbind("click");
                            $("#a_open_toUrl").bind("click",function(){
                                var toUrlHtm=$("#dv_op_ettAccountColumn input[name='toUrl']").val();
                                if(toUrlHtm.Trim()=="WEBIM"){
                                    $("#li_web_im").load("util/webim.jsp",function(){
                                        $("#webimopen").click();
                                    });

                                }else{
                                    window.open(toUrlHtm);
                                }
                            });
                            //取消弹出层
                            $.fancybox.close();
                            $("#a_open_toUrl").click();
                        }else{
                            alert(rps.msg);
                        }
                    }
                });
            }
        }
    }

    //注册网校帐号
    function doBindEttAccount(){
      //  var uNameValidateBo=validateEttUName('up_ettUserName','p_up_msg_ettUserName','sp_up_ettUserName_isRight',false)
        var passValidateBo=$("#sp_up_ettUserName_isRight .ico12").length>0?true:false;
//        var emailValidateBo=validateEttEmail('add_ettEmail','p_add_msg_ettEmail','sp_add_ettEmail_isRight');
        //用户名通过
        //uNameValidateBo&&
        if($("#sp_up_ettUserName_isRight .ico12").length>0){
            //密码通过,邮箱通过
            if(passValidateBo){
                $.ajax({
                    type: "POST",
                    dataType:"json",
                    async:false,
                    url: "operateEtt?m=bindEttAccount",
                    data: {
                        userName:$("#up_ettUserName").val().Trim(),
                        password:$("#up_ettPass").val().Trim()
                    },
                    error:function(){
                        alert('网络不通畅!');
                    },
                    success: function(rps){     //根据后台返回的result判断是否成功!
                        if(rps.type=="success"){
                            $("#a_open_toUrl").unbind("click");
                            $("#a_open_toUrl").bind("click",function(){
                                var toUrlHtm=$("#dv_op_ettAccountColumn input[name='toUrl']").val();
                                if(toUrlHtm.Trim()=="WEBIM"){
                                    $("#li_web_im").load("util/webim.jsp",function(){
                                        $("#webimopen").click();
                                    });
                                }else{
                                    window.open(toUrlHtm);
                                }
                            });
                            //取消弹出层
                            $.fancybox.close();
                            $("#a_open_toUrl").click();
                        }else{
                            alert(rps.msg);
                        }
                    }
                });
            }
        }
    }



</script>
</head>
<body>


<div class="home_bdyzh_float" id="dv_op_ettAccountColumn">
    <%--显示当前用户的用户名，真实姓名，年级--%>
    <ul class="info">
        <li>${sessionScope.CURRENT_USER.username}</li>
        <li>${sessionScope.CURRENT_USER.realname}</li>
        <li>
            <c:if test="${empty gradeidList}">
                无
            </c:if>
            <c:if test="${!empty gradeidList}">
                <c:forEach items="${gradeidList}" var="itm" varStatus="itmIdx">
                    <c:if test="${itmIdx.index!=0&&itmIdx.index<2}">
                        、
                    </c:if>
                    ${itm}
                    <c:if test="${itmIdx.index%3==0&&itmIdx.index!=0&&fn:length(gradeidList)>3}">
                        ...
                    </c:if>
                </c:forEach>
            </c:if>
        </li>
    </ul>
        <%--存入要跳转的地址--%>
        <input type="hidden" value="" id="toUrl" name="toUrl"/>
    <div class="menu" >
        <h1>绑定云账号</h1>
        <lable><input type="radio" checked="checked"
                      onclick="dv_hasEttAccount.style.display='none';
                      dv_newEttAccount.style.display='block';" id="rdo_newCreate" name="rdo_ettAccount"/>
            <label for="rdo_newCreate">新建账号</label>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio"  onclick="dv_newEttAccount.style.display='none';
                      dv_hasEttAccount.style.display='block';" id="rdo_has" name="rdo_ettAccount"/>
            <label for="rdo_has">已有网校账号</label></lable>
      <div id="dv_hasEttAccount" style="display:none">
        <div class="input">
         <span class="one"><strong>用&nbsp;户&nbsp;名</strong>
          <input type="text" name="up_ettUserName" id="up_ettUserName"
                 onblur="
                 if(this.value.length<1){
                    p_up_msg_ettUserName.innerHTML='用户名不符合要求!请更改';
                    sp_up_ettUserName_isRight.innerHTML='6-12字符/6个汉字';
                 }else{
                     sp_up_ettUserName_isRight.innerHTML='<a href=\'javascript:;\' class=\'ico12\' title=\'正确\'></a>';
                     p_up_msg_ettUserName.innerHTML='';
                  }"
                 maxlength="12" />
          </span><span id="sp_up_ettUserName_isRight">6-12字符/6个汉字</span>
                <p class="two" id="p_up_msg_ettUserName"></p>
            </div>
            <div class="input">
          <span class="one"><strong>密&nbsp;&nbsp;&nbsp;&nbsp;码</strong>
          <input type="password" name="up_ettPass" onblur="
            if(this.value.length<1){
                    p_up_msg_ettPass.innerHTML='密码不符合要求!请更改';
                    sp_up_ettPass_isRight.innerHTML='';
                 }else{
                     sp_up_ettUserName_isRight.innerHTML='<a href=\'javascript:;\' class=\'ico12\' title=\'正确\'></a>';
                     p_up_msg_ettPass.innerHTML='';
                  }
          " id="up_ettPass" />
          </span><a href="javascript:;" onclick="changeEttPassType('up_ettPass','p_up_msg_ettPass','sp_up_ettPass_isRight')" class="ico92" title="显示密码"></a>
                <span id="sp_up_ettPass_isRight"></span>
                <p class="two" id="p_up_msg_ettPass"></p>
            </div>
            <p class="an"><a href="javascript:;" onclick="doBindEttAccount()">绑定并登录</a></p>
      </div>

        <div id="dv_newEttAccount">
            <div class="input">
         <span class="one"><strong>用&nbsp;户&nbsp;名</strong>
          <input type="text" name="add_ettUserName" id="add_ettUserName" onblur="validateEttUName('add_ettUserName','p_add_msg_ettUserName','sp_add_ettUserName_isRight')"/>
          </span><span id="sp_add_ettUserName_isRight">6-12字符/6个汉字</span>
                <p class="two" id="p_add_msg_ettUserName"></p>
            </div>
            <div class="input">
          <span class="one"><strong>密&nbsp;&nbsp;&nbsp;&nbsp;码</strong>
          <input type="password" name="add_ettPass" id="add_ettPass"  onblur="validateEttPass('add_ettPass','p_add_msg_ettPass','sp_add_ettPass_isRight')"  />
          </span><a href="javascript:;" class="ico92" title="显示密码"
                    onclick="changeEttPassType('add_ettPass','p_add_msg_ettPass','sp_add_ettPass_isRight')"></a>
                <span id="sp_add_ettPass_isRight"></span>
                <p class="two" id="p_add_msg_ettPass"></p>
            </div>
            <div class="input">
          <span class="one"><strong>邮箱地址</strong>
          <input type="text" name="add_ettEmail" onblur="validateEttEmail('add_ettEmail','p_add_msg_ettEmail','sp_add_ettEmail_isRight')" id="add_ettEmail" />
          </span><span id="sp_add_ettEmail_isRight">
                <%--<a href="javascript:;" style="cursor:default" class="ico94" title="正确"></a>--%>
            </span>
                <p class="two" id="p_add_msg_ettEmail"></p>
            </div>
            <p class="an"><a href="javascript:;" onclick="doRegisterEttAccount()">绑定并登录</a></p>
        </div>



    </div>

</div>

</body>
</html>
