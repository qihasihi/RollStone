<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <%--<script src="util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        /**
        *加载内容
            * @param ty
         */
        function loadContent(ty){

                    $("#dv_selectMic_child").hide();
                    var url="tpres?m=queryMicViewListModel&tasktype=${param.tasktype}&courseid=${param.courseid}&op_type1=1";
                    <c:if test="${!empty param.videoid}">
                        url+='&videoid=${param.videoid}';
                    </c:if>

                    $("#dv_selectMic_child").load(url
                            ,function(rps){
                               // $("#dv_selectMic_child").html('<div class="jxxt_float_h560">'+rps+'</div>');
                               // $("dv_load_topic").html(rps);
                                $("#dv_selectMic_child .content2").removeClass("content2");
                                $("#dv_selectMic_child .subpage_head").remove();
                                $("#dv_selectMic_child .foot").remove();
                                $("#dv_selectMic_child #fade").remove();
                                $("#dv_selectMic_child .an_small").removeClass("an_small").addClass("an_public1").last().remove();
                                //$("#dv_selectMic_child").show();
                                $("#dv_selectMic_child").show();
                    });
        }


        function loadRelatePage(resid){
            var url = 'paper?m=toSelRelatePaper&resid='+resid+'&courseid=' + courseid;
            $("#dv_content").load(url,function(){
               // $("#dv_content").show();
               // $("#a_click").click();
            });
        }

        /**
        *加载微视频详情页面
        * @param resid
         */
        function loadMicDetail(resid){
           if(typeof(resid)=="undefined"||resid==null){
               alert('网络异常!参数异常!');
                return;
           }
            $("#dv_micdetail_child").load("tpres?m=previewMicModel&courseid=${param.courseid}&resid="+resid+"&op_type1=1"
                    ,function(rps){
                        // $("dv_load_topic").html(rps);
//                        $(".content1:last").removeClass("content1");
//                        $(".subpage_head:last").remove();
//                        $(".foot:last").remove();
//                        $("#fade:last").remove();
                        $("#dv_micdetail_child .an_small").removeClass("an_small").addClass("an_public1").last().remove();
                        //$("#dv_selectMic_child").show();
                        $("#dv_selectMic").hide();
                        $("#dv_micDetail").show();
                    });
        }

        function micDetailReturn(){
            $("#dv_micDetail").hide();
            $("#dv_selectMic").show();
        }
	</script>
</head>
<body>
<div class="public_float public_float960 public_float_jxxt" id="dv_selectMic">
    <p class="float_title"><strong>微课程学习&mdash;&mdash;选择微视频</strong></p>
    <div id="dv_selectMic_child">

    </div>
</div>

<div class="public_float public_float960" id="dv_micDetail" style="display:none">
    <p class="float_title"><a href="javascript:;" onclick="micDetailReturn()" class="ico93" title="返回"></a>视频详情</p>
    <div id="dv_micdetail_child">

    </div>
</div>
<script type="text/javascript">
 //默认加载内容。
 $(function(){
     loadContent();
 })
</script>
</body>
</html>
