<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<%
    //教学平台
    modelType=2;%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jxxt.css"/>
<script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
<script type="text/javascript">
    var ques_mp3_path='<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>';
</script>


<div id="dv_award_zs" class="jxxt_float_jiangli" style="display:none">获得<strong>1</strong>积分及<strong>1</strong>颗<span class="ico90"></span></div>
<div id="dv_award_jf"  class="jxxt_float_jiangli" style="display:none">获得<strong>1</strong>积分</div>

<script type="text/javascript">
    var WinAlerts = window.alert;
    window.alert = function (e) {
        if (e != null &&e.indexOf("获得了")>-1&&(e.indexOf("蓝宝石")>-1|| e.indexOf("积分")))
        {
            //和谐了
            if(e.indexOf("蓝宝石")!=-1){
                showModel('dv_award_zs','fade');
            }else if(e.indexOf("积分")!=-1){
                showModel('dv_award_jf','fade');
            }
        }
        else
        {
            WinAlerts (e);
        }

    };

    var bs_time=0;
    function BsTimer(){

    }

</script>