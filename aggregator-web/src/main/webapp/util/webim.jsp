<%@ page language="java" pageEncoding="Utf-8" %>
<%@ page import="java.util.Date" %>

<%@ page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="com.school.util.MD5" %>
<%@ page import="com.school.entity.UserInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.entity.RoleUser" %>
<%@ page import="com.school.util.UtilTool" %>

<%
    UserInfo sms_user1=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    long jid = sms_user1.getEttuserid();
    long schoolId =sms_user1.getDcschoolid();


    List<RoleUser> cruList1=sms_user1.getCjJRoleUsers();
    boolean isWxJw1=false;
    if(cruList1!=null){
        for(RoleUser ru : cruList1){
            if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_TEACHER_ID)){
              if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_WXJW_ID)){
                  isWxJw1=true;
                  break;
              }
            }
        }
    }

    //3:学生 2:教务:1教师
    long userType=1;
    if(sms_user1.getStuname()!=null&&sms_user1.getStuname().length()>0)
        userType=3;
    else if(isWxJw1||schoolId<50000)
        userType=2;
    else
        userType=1;


    String webimtimestamp =String.valueOf(new Date().getTime());
    String webimtoken = DigestUtils.md5Hex(jid + webimtimestamp);
    String webimkey= MD5.getJDKMD5(jid + "#etttigase#");

    String testhost1 = "http://web.etiantian.com";
%>
    <title>im</title>
    <link rel="stylesheet" href="http://web.etiantian.com/js/ett/webim/css/webim.css" type="text/css"/>

    <script>
        $(document).ready(function () {


            var jsURL = "<%=testhost1%>/js/ett/webim/webim-panel.html";
            var webim_info_url = "<%=testhost1%>/js/ett/webim/webim-info.html";


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
                    zindex:20000

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

<li class="three">
    <a id="webimopen" href="javascript:void(0)" >爱&nbsp;学<span id="webimcnt"></span></a>
</li>