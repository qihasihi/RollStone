<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<%
    //教学平台
    modelType=2;
    Integer ettJid=null;
    if(u!=null){
        ettJid=u.getEttuserid();
    }

%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jxxt.css"/>
<script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
<script type="text/javascript">
    var ques_mp3_path='<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>';
    var bathPath='<%=basePath%>';
</script>

<div id="dv_award_zs" class="jxxt_float_jiangli" style="display:none;z-index:9999">获得<strong>1</strong>积分及<strong>1</strong>颗<span class="ico90"></span></div>
<div id="dv_award_jf"  class="jxxt_float_jiangli" style="display:none;z-index:9999">获得<strong>1</strong>积分</div>
<script type="text/javascript">
    var alertMsg="${sessionScope.msg}";
    var WinAlerts = window.alert;
    window.alert = function (e) {
        if (e != null &&e.indexOf("获得了")>-1&&(e.indexOf("蓝宝石")>-1|| e.indexOf("积分")))
        {

            //和谐了
            if(e.indexOf("蓝宝石")!=-1){
                <%if(ettJid!=null){%>
                 showModel('dv_award_zs','fade');
                <%}else{%>
                    showModel('dv_award_jf','fade');
                <%}%>
                $("#fade").hide();
            }else if(e.indexOf("积分")!=-1){
                showModel('dv_award_jf','fade');
                $("#fade").hide();
            }
            hideAwardDiv();
        }
        else
        {
            WinAlerts (e);
        }
    };
    $(function(){
        if(alertMsg.length>0){
            alert(alertMsg);
        }
    })
    function closeAwardDiv(){
        $("#dv_award_zs").hide('slow');
        $("#dv_award_jf").hide('slow');
//        $("#fade").hide();
    }

    //显示一秒后自动消失
    var awardTime=0;
    function hideAwardDiv(){
        if(awardTime==1){
            closeAwardDiv();
            awardTime=0;return;
        }
        awardTime++;
        setTimeout("hideAwardDiv()",2500);
    }

</script>
<%
    if(session.getAttribute("msg")!=null&&session.getAttribute("msg").toString().trim().length()>0)
        session.removeAttribute("msg");
%>
