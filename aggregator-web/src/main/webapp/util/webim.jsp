<%@ page language="java" pageEncoding="Utf-8" %>
<%@ page import="java.util.Date" %>

<%@ page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="com.school.util.MD5" %>

<%

    long jid = sms_user.getEttuserid();


    //1:学生 2:教师
    long userType=1;
    if(sms_user.getStuname()!=null&&sms_user.getStuname().length()>0)
        userType=1;
    else
        userType=2;
    long schoolId =sms_user.getDcschoolid();


    String webimtimestamp =String.valueOf(new Date().getTime());
    String webimtoken = DigestUtils.md5Hex(jid + webimtimestamp);
    String webimkey= MD5.getJDKMD5(jid + "#etttigase#");
    String testhost = "http://web.etiantian.com";

%>
<html>
<head>
    <title>im</title>
    <link rel="stylesheet" href="http://web.etiantian.com/js/ett/webim/css/webim.css" type="text/css"/>


   <%-- <script src="<%=jshost%>/js/o/jquery/1.7.1/jquery-1.7.1.js"></script>--%>
    <script src="http://web.etiantian.com/js/o/jplugins/ui/1.8.16/js/jquery-ui-1.8.16.custom.min.js"></script>
    <script src="http://web.etiantian.com/js/o/jplugins/cross-domian/1.0.3/jquery.xdomainrequest.min.js"></script>
    <script src="http://web.etiantian.com/js/o/jplugins/ba-postmessage/0.5/jquery.ba-postmessage.min.js"></script>
    <script src="http://web.etiantian.com/js/o/jplugins/ba-bbq/1.2.1/jquery.ba-bbq.min.js"></script>

    <script src="<%=testhost%>/js/ett/frame-bridge/jquery.top-dialog.js" charset="utf-8"></script>
    <script src="<%=testhost%>/js/ett/frame-bridge/jquery.top-bridge.js" charset="utf-8"></script>
    <script>
        $(document).ready(function () {


            var jsURL = "<%=testhost%>/js/ett/webim/webim-panel.html";
            var webim_info_url = "<%=testhost%>/js/ett/webim/webim-info.html";


            $("#webimopen").click(function () {
                $.framebridge.openDialog({windowId: "123456",windowClass:"webim_group_layout", target_url: jsURL,width: 262, height: 590, queryString:{jid:<%=jid %>,userType:<%=userType %>,schoolId:<%=schoolId %>,webimtoken:'<%=webimtoken %>',webimtimestamp:'<%=webimtimestamp %>',webimkey:'<%=webimkey%>'}, position: [100, 20]});
            });


            $(".etiantianbox-topdialog-header-close").die().live("click",function(){
                if($("#msgDialogIM").is(":hidden")){
                    var current =$.framebridge.getWindowData({windowId: "msgDialogIM",name:"jid"});
                    $.framebridge.command({action:"msgDialogIMClose",targetWindowId:'123456',msg:{to:current}});

                }
            });

            $(".webim_user_detail").die().live("click",function(){
                var jid=$(this).attr("data-jid");
                var uType =$(this).attr("data-utype");
                $.framebridge.openDialog({
                    windowId: 'webim-info',
                    windowClass:'webim_info_layout',
                    titleClass:"info_head",
                    autoOpen: true,
                    width: 404,
                    height: 305,
                    target_url: webim_info_url,
                    queryString: {
                        jid: <%=jid%>,
                        searchjId: jid,
                        userType: uType,
                        webimtoken: '<%=webimtoken %>',
                        webimtimestamp: '<%=webimtimestamp %>'
                    },
                    zindex:10000

                });
            });

            $.framebridge.handler({
                reviceMessage: function (params) {
                    var cnt =params.msg.cnt;
                    console.log("top handler reviceMessage",cnt);
                    if(cnt>0){
                        $("#webimcnt").html((cnt>9?'<b class="two">':'<b class="one">')+(cnt>99?"99+":cnt)+'</b>');
                    }else{
                        $("#webimcnt").html("");
                    }
                },
                updateTopName:function(params){
                    if($(".webim_user_detail").attr("data-jid")==params.msg.jid){
                        $(".webim_user_detail").find(".webim_user_name ").html(params.msg.uName);
                    }
                }
            });

            $.framebridge.openDialog({windowId: "123456",windowClass:"webim_group_layout", target_url: jsURL,width: 262, height: 590, queryString:{jid:<%=jid %>,userType:<%=userType %>,schoolId:<%=schoolId %>,webimtoken:'<%=webimtoken %>',webimtimestamp:'<%=webimtimestamp %>',webimkey:'<%=webimkey%>'}, position: [100, 20],autoOpen: false, autoCreate: true});
        });
    </script>
</head>
<body>
<li class="three">
    <a id="webimopen" href="javascript:void(0)" >爱&nbsp;学<span id="webimcnt"></span></a>
</li>

</body>
</html>
