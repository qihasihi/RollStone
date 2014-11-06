<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <%--<script src="util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        /**
        *加载内容
            * @param ty
         */
        function loadMsg(ty){
            if(typeof(ty)!="undefined"&&!isNaN(ty)){
                if(ty==1){   //选择论题的处理方式
                    $('#li_select_topic').parent().children('li').removeClass('crumb');
                    $('#li_select_topic').addClass('crumb');
                    $("#sp_tpc_typeName").html("选择论题");
                    $("#dv_loadTopic").hide();
                    $("#dv_loadTopic").load("task?m=toTaskElementDetial&tasktype=${param.tasktype}&courseid=${param.courseid}&op_type1=1"
                            ,function(rps){
                                $("#dv_loadTopic").html('<div class="jxxt_float_h480">'+rps+'</div>');
                               // $("dv_load_topic").html(rps);
                                $(".content1:last").removeClass("content1");
                                $(".subpage_head:last").remove();
                                $(".foot:last").remove();
                                $("#fade:last").remove();
                                $(".an_small").removeClass("an_small").addClass("an_public1").last().remove();
                                $("#dv_loadTopic").show();
                    });
                }else{      //添加论题的处理方式
                    $("#sp_tpc_typeName").html("添加论题");
                    $('#li_add_topic').parent().children('li').removeClass('crumb');
                    $('#li_add_topic').addClass('crumb');
                    $("#dv_loadTopic").hide();
                     $("#dv_loadTopic").load("tptopic?m=toAdmin&operate_type=1&courseid=${param.courseid}"
                             ,function(rps){
                                // $("dv_load_topic").html(rps);
                                 $(".content1:last").removeClass("content1");
                                 $(".subpage_head").remove();
                                 $(".foot").remove();
                                 $("#fade:last").remove();
                                 $(".an_small").removeClass("an_small").addClass("an_public1").last().remove();
                                 $("#dv_loadTopic").show();
                             });
                }
            }
        }
	</script>
</head>
<body>
<div class="public_float public_float960">
    <p class="float_title"><strong>互动交流&mdash;&mdash;<span id="sp_tpc_typeName">选择论题</span></strong></p>
    <div class="subpage_lm">
        <ul>
            <li class="crumb" id="li_select_topic">
                <a href="javascript:;"
                 onclick="loadMsg(1);">选择论题</a></li>
            <%--$('#li_select_topic').parent().children('li').removeClass('crumb');$('#li_select_topic').class('crumb');--%>
            <li id="li_add_topic"><a href="javascript:;"
                onclick="loadMsg(2);"
                >新建论题</a></li>
            <%--$('#li_add_topic').parent().children('li').removeClass('crumb');$('#li_add_topic').class('crumb');--%>
        </ul>
    </div>
    <div id="dv_loadTopic">
    </div>
    <%--<div class="nextpage"> <span><b class="before"></b></span><span><a href="1">1</a></span><span><a href="1">2</a></span><span class="crumb"><a href="1">3</a></span><span><a href="1">4</a></span><span><a href="1">5</a></span>&hellip;<span><a href="1">9</a></span><span><a href="1">10</a></span><span><a href="1">11</a></span><span><a href="1"><b class="after"></b></a></span> &nbsp;&nbsp;共20页&nbsp;&nbsp;去第--%>
        <%--<input name="textfield2" type="text" id="textfield2" value="3000" />--%>
        <%--页&nbsp;&nbsp;<span><a href="1">Go</a></span></div>--%>
    <%--<p class="t_c p_tb_10"><a href="1" target="_blank" class="an_public1">选&nbsp;择</a></p>--%>
</div>
<script type="text/javascript">
 //默认加载内容。
 $(function(){
    loadMsg(1);
 })
</script>
</body>
</html>
