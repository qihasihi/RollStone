<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<%--<%@include file="/util/common-jsp/common-jxpt.jsp"%>--%>
<%
    String basePath="";

    String proc_name1=request.getHeader("x-etturl");
    if(proc_name1==null){
        proc_name1=request.getContextPath().replaceAll("/","");
    }else{
        if(proc_name1.indexOf("/")==-1)
            proc_name1+="/";
        ///group1/1.jsp
        proc_name1=proc_name1.substring(0,proc_name1.substring(1).indexOf("/")+1).replaceAll("/","");
    }

    String ipStr1=request.getServerName();
    if(request.getServerPort()!=80){
        ipStr1+=":"+request.getServerPort();
    }
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    basePath = (request.getScheme() + "://"
            + ipStr1
            +"/"+proc_name1+"/");
    String random=System.currentTimeMillis()+"";
        random=random.substring(random.length()-4);
    %>

<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
    <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1}">
        <script src="demo/js/jquery-jsmartdrag.js" type="text/javascript"></script>
        <style type="text/css">
            /*body{-moz-user-select: none;color:white;font-size:1.5em;}*/
            .jsmartdrag-source{cursor:pointer;}
            .jsmartdrag-source-hover{opacity:0.5;}
            .jsmartdrag-cursor-hover{opacity:0.5;}
            .jsmartdrag-target-hover{opacity:0.5;}
        </style>
    </c:if>
<script type="text/javascript">

var tmpcourseid<%=random%>="${courseid}";
var paperid_t<%=random%>="${paper.paperid}";
var pBankList<%=random%>;
var total<%=random%>;
var allquesid<%=random%>="${allquesid}";
var paperSumScore<%=random%>="${paper.score}" || 0;
var papertype<%=random%>=${paper.papertype};
var quesOrderIdx<%=random%>=1;childOrderIdx<%=random%>=1;
$(function(){
    $("li[name='li_nav<%=random%>']").each(function(idx,itm){
        $(itm).bind("click",function(){
            $(itm).siblings().removeClass("crumb").end().addClass("crumb");
        })
    })



    if(papertype<%=random%>==1||papertype<%=random%>==2){
        var allqueslength=allquesid<%=random%>.split(",").length;
        var avgScore=parseInt(paperSumScore<%=random%>/allqueslength);
//        alert($("span[name='avg_score<%=random%>']").length+"   "+avgScore)
        $("span[name='avg_score<%=random%>']").html(avgScore);
//        $("#group_628772").html(10);
        if(paperSumScore<%=random%>%allqueslength>0){
            //// 使用数组翻转函数
            var yuScore=paperSumScore<%=random%>%allqueslength;
            var s=jQuery.makeArray($("span[name='avg_score<%=random%>'][id^='score_']")).reverse();
            $.each(s,function(idx,itm){
                if(yuScore<1)return;
                $(itm).html(parseFloat($(itm).html())+1);
                yuScore-=1;
            });
        }
    }
    var quesArray=$("table").filter(function(){return this.id.indexOf('dv_ques_')!=-1});
    $.each(quesArray,function(ix,im){
//        alert(1);
        var score= $("#group_"+$(im).find("span[id*='group_']")).html();
//        alert(2);
        var childArray=$(im).find("span[name='avg_score<%=random%>'][id^='score_']");
//        alert(3);
        if(childArray.length>0){
            score=0;
            $.each(childArray,function(i,m){
                score+=parseFloat($(m).html());
            })
        }
//        alert(score);
//        $("#group_"+$(im).find("span[id*='group_']")).html(score);
//        alert(5);
    });

    showQues<%=random%>Num<%=random%>();
    paperDivShow<%=random%>();
});


function showOrhide(aobj, taskid) {
    var status=$("#div_task_"+taskid);
    if(status.css("display")=="none"){
        load_task_detial(taskid);
        $(status).show();
        $(aobj).removeClass().addClass("ico49a");
    }else{
        $(status).hide();
        $(aobj).removeClass().addClass("ico49b");
    }
}

/**
 * 初始化进入
 */
function paperDivShow<%=random%>(){
    $('#dv_view').hide();
    $('#dv_paper').show();
//            if(typeof(currentQuesid)=="undefined"){
//                currentQuesid=$("#dv_paper table:first").attr("data-bind");
//                currentQIdx=$("#dv_paper table:first #sp_tbl_qidx").html();
//            }
    var currentQIdx=$("#dv_paperdetail<%=random%>>table").filter(function(){return this.style.display!='none';}).attr("data-idx");
    if(typeof(currentQIdx)=="undefined"||currentQIdx==null){
        <c:if test="${!empty param.qid}">
            //得到qid对应下的idx
            var dataIdx=$("#dv_ques_${param.qid}").attr("data-idx");
            showQues<%=random%>(dataIdx);
        </c:if>
        <c:if test="${empty param.qid}">
            showQues<%=random%>(1);
        </c:if>

    }

}
/**
 * 加载试卷中的试题
 *
 *
 *
 */
function showQues<%=random%>(idx){
    // $("#dv_paper table:not(data-idx='"+idx+"')").hide();
    //隐藏dv_ques
    $("#dv_paperdetail<%=random%> table[id*='dv_ques_']").hide();
    var cuTagName=$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).attr("tagName").toUpperCase();
    if(cuTagName=="TR"){//如果是tr，则显示table
        $("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).next("tr").show();
        $("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).parent().parent().show();
    }else{
        $("#dv_paperdetail<%=random%>>table[id*='questeam_']").hide();
    }
    $("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).show();
    //序号的样式
    $("#ul_xuhao<%=random%>>li").removeClass("blue_big").removeClass("blue").addClass("blue");
    $("#ul_xuhao<%=random%> #li_"+idx).removeClass("blue").addClass("blue_big");
    //得到题号，当前的问题
    var currentQuesid=$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).attr("data-bind");
    var currentQIdx=$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").eq((idx-1)).attr("data-idx");

    if(currentQIdx>=$("#ul_xuhao<%=random%>>li").length){
        $("#a_next<%=random%><%=random%>").hide();
    }else
        $("#a_next<%=random%><%=random%>").show();
    if(currentQIdx<=1){
        $("#a_free<%=random%>").hide();
    }else
        $("#a_free<%=random%>").show();
}

/**
 *下一题
 */
function next<%=random%>(ty){
//            currentQuesid=$("#dv_paper table:eq("+currentQIdx+")").attr("data-bind");
    //当前的序号
    var currentQIdx=$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").filter(function(){return this.style.display!='none';}).attr("data-idx");
    if(typeof(currentQIdx)=="undefined"||currentQIdx==null){
        currentQIdx=0;
    }
//            currentQIdx=$("#dv_paper table:eq("+(currentQIdx-1)+") #sp_tbl_qidx").html();
    var next<%=random%>QIdx=parseInt(currentQIdx)+1
    if(ty<1)
        next<%=random%>QIdx=parseInt(currentQIdx)-1;
    if(next<%=random%>QIdx>$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").length){
        next<%=random%>QIdx=1;
    }else if(next<%=random%>QIdx<1)
        next<%=random%>QIdx=6;
    showQues<%=random%>(next<%=random%>QIdx);
    //序号的样式
    $("#ul_xuhao<%=random%>>li").removeClass("blue_big").removeClass("blue").addClass("blue");
    $("#ul_xuhao<%=random%> #li_"+next<%=random%>QIdx).removeClass("blue").addClass("blue_big");
}
<%int idx=0;%>
/**
 *得到序列
 */
function showQues<%=random%>Num<%=random%>(){
    var h='';
    var qtlength=$("#dv_paperdetail<%=random%> table[id*='dv_ques_']").length;
    if(qtlength<1){
        $("#a_next<%=random%><%=random%>").hide();
        $("#a_free<%=random%>").hide();
    }
    //得到平均分
    var allqueslength=allquesid<%=random%>.split(",").length;
    var avgScore=parseInt(paperSumScore<%=random%>/allqueslength);

        $("#dv_paperdetail<%=random%> table[id*='dv_ques_']").each(function(idx,itm){
            h+='<li class="blue quesNumli<%=random%>" id="li_'+(idx+1)+'" data-bind="'+(idx+1)
                    +'"><a href="javascript:;" onclick="showQues<%=random%>('+(idx+1)+')">'+(idx+1)+'</a></li>';

            var sumScore=0;
            $("#dv_paperdetail<%=random%> #"+itm.id+" tr[id*='dv_ques_'] span[id*='score_']").each(function(ix,im){
              sumScore+=parseFloat(($(this).text().Trim().length>0?$(this).text().Trim():0));
            });
            if(sumScore==0){
                sumScore=avgScore;
            }
            $("#dv_paperdetail<%=random%> #"+itm.id+" span[id*='group_']").html(sumScore);
            $("#dv_paperdetail<%=random%> #"+itm.id+" tr[id*='dv_ques_']").next().show();
        });


    $("#ul_xuhao<%=random%>").html(h);
    if($("#ul_xuhao<%=random%>>li").length>20){
        $("#ul_xuhao<%=random%>").css({'width':'700px','overflow-y':'auto','height':'100px'});
    }
    <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
         $("#dv_bf_dvpdtail<%=random%>").html($("#dv_paperdetail<%=random%> #dv_table<%=random%>").html());
        js = $("#dv_paperdetail<%=random%> .quesNumli<%=random%>").jsmartdrag({
            target:'#dv_paperdetail<%=random%> .quesNumli<%=random%>',
            afterDrag:afterDrag<%=random%>
        });
    </c:if>
}
<c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
function afterDrag<%=random%>(selected,currentObj,targetSelected){
    if(selected){
        // alert("将元素ID为"+currentObj.attr("id")+"移动到了元素ID为"+targetSelected.attr("id")+"上");
//        if(targetSelected.attr("id")==$(".quesNumli<%=random%>:first").attr("id")){
//            $(targetSelected).before(currentObj);
//        }else if(targetSelected.attr("id")==$(".quesNumli<%=random%>:last").attr("id")){
//            $(currentObj).before(targetSelected);
//        }else
        var isReOrder=false;
            var targetIdx=$(targetSelected).attr("data-bind");
            var currentIdx=$(currentObj).attr("data-bind");
            if(targetIdx>currentIdx){
                $(targetSelected).after(currentObj);
                $("#dv_bf_dvpdtail<%=random%>>table[data-idx='"+targetIdx+"']").after($("#dv_bf_dvpdtail<%=random%>>table[data-idx='"+currentIdx+"']"));
                isReOrder=true;
            }else if(targetIdx<currentIdx){
                $(targetSelected).before(currentObj);
                $("#dv_bf_dvpdtail<%=random%>>table[data-idx='"+targetIdx+"']").before($("#dv_bf_dvpdtail<%=random%>>table[data-idx='"+currentIdx+"']"));
                isReOrder=true;
            }
            //重新排序
            if(isReOrder){
                /*********************先修改页面元素，再根据页面元素的相关值修改数据库********************/
                //修改table题
                $("#dv_bf_dvpdtail<%=random%>>table").each(function(idx,itm){
                    $(this).attr("data-idx",idx+1);
                });
                //修改li
                $("#ul_xuhao<%=random%>>li").each(function(idx,itm){
                    //修改idx
                    $(this).attr("id","li_"+(idx+1)).attr("data-bind",(idx+1));
                    //修改事件，及html
                    $(this).children("a").attr("onclick","").unbind("click")
                            .html((idx+1)).bind("click",function(){
                                showQues<%=random%>((idx+1));
                            });
                });
                //数据库请求。
                quesNumOrder<%=random%>(paperid_t<%=random%>);
            }

        //更新SQL
     }
}
/**
 * 试题问题顺序重新排序
 * @param pid
 */
function quesNumOrder<%=random%>(pid){
    if(typeof(pid)=="undefined"||pid==null){
        alert("错误，参数异常!");return;
    }
    var p={paperid:pid};
    //得到问题的顺序(试题组当一道题处理)
    var qidstr='';
    for(z=0;z<$("#dv_bf_dvpdtail<%=random%>>table").length;z++){
        var qid=$("#dv_bf_dvpdtail<%=random%>>table").eq(z).attr("data-bind");
        var oidx=$("#dv_bf_dvpdtail<%=random%>>table").eq(z).attr("data-idx");
        if(qidstr.length>0)
            qidstr+=','
        qidstr+=qid+'|'+oidx;
    }
    p.qidstr=qidstr;
    //执行数据库操作
    $.ajax({
        url: 'paperques?m=doSaveQuesOrder',
        type: 'post',
        data: p,
        dataType: 'json',
        cache: false,
        success: function (rps) {
            if (rps.type == "error") {
                //失败 状态回滚
                $("#dv_bf_dvpdtail<%=random%>").html($("#dv_paperdetail<%=random%> #dv_table<%=random%>").html());
                alert(rps.msg);
            }else{
                //成功，显示相关页面
                $("#dv_paperdetail<%=random%> #dv_table<%=random%>").html($("#dv_bf_dvpdtail<%=random%>").html());
                var currentQIdx=$("#ul_xuhao<%=random%> .blue_big").attr("data-bind");
                showQues<%=random%>(currentQIdx);
            }
            js = $("#dv_paperdetail<%=random%> .quesNumli<%=random%>").jsmartdrag({
                target:'#dv_paperdetail<%=random%> .quesNumli<%=random%>',
                afterDrag<%=random%>:afterDrag<%=random%>
            });

        }
    });
}
//生成分数录入框
function onClickScore<%=random%>(spid){
    var txtScore=$("#"+spid+">a").html().Trim();
    $("#"+spid).html("<input type='text' id='txt_inputScore<%=random%>' maxlength=4 name='txt_inputScore<%=random%>' style='width:25px;height:15px'/>");
    $("#"+spid+" #txt_inputScore<%=random%>").val(txtScore)
    $("#"+spid+" #txt_inputScore<%=random%>").select();
    $("#"+spid+" #txt_inputScore<%=random%>").focus();
    //离开事件
    var qid=$("#"+spid).attr("data-bind").split("|")[0];
    var qtref=$("#"+spid).attr("data-bind").split("|")[1];
    $("input[name='txt_inputScore<%=random%>']").bind("blur",function(){
        if(this.value.Trim().length<1||isNaN(this.value.Trim())){
            this.focus();
            this.style.borderBottomColor='red';
            this.style.borderWidth='1px';
            return;
        }
        //执行数据库操作
        if(qtref==null||qtref.Trim().length<1||qtref.Trim()=="null")
        qtref=undefined;
        var scoreFen=this.value.Trim();
        updateQuesScore<%=random%>(scoreFen,qid,qtref,spid);

    });
    $("input[name='txt_inputScore<%=random%>']").bind("keyup",function(){
       // alert(e.keyCode);
        this.value = this.value.replace(/[^\d\.]/g, '');
    });
}


/**
 * 修改试题分数
 * @param score
 * @param quesid
 */
function updateQuesScore<%=random%>(score,quesid,ref,spid){
    if(typeof score=='undefined'||isNaN(score))
        return;
    var param={courseid:tmpcourseid<%=random%>,questionid:quesid,paperid:paperid_t<%=random%>,score:score};
    if(typeof ref!='undefined')
        param.ref=ref;
    $.ajax({
        url:"paperques?m=doUpdPaperQuesScore",
        type:"post",
        data:param,
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                if(rmsg.objList.length>0){
                    $("#total<%=random%>_score").html(xround(rmsg.objList[0],1));
                    if(typeof ref!='undefined')
                        $("#group_"+ref).html(xround(rmsg.objList[1],1))
                }
                //将分数录入
                $("#"+spid).html("<a href='javascript:;' onclick=\"onClickScore<%=random%>('"+spid+"')\">"+score+"</a>");
            }
        }
    });
}
function xround(x, num){
    return Math.round(x * Math.pow(10, num)) / Math.pow(10, num) ;
}

</c:if>
</script>
</head>
<body>
<p class="float_title"><a href="javascript:;" onclick="paperDetailReturn(${param.isselect})" class="ico93" title="返回"></a>试卷详情</p>
<p class="float_text1">
    <c:if test="${!empty paper.subjectivenum and !empty paper.objectivenum}">
        <span class="f_right"><strong>主观题：<span class="font-blue">${paper.subjectivenum}</span></strong>
            &nbsp;&nbsp;<strong>客观题：<span class="font-blue">${paper.objectivenum}</span>
            &nbsp;&nbsp;总分值：<span class="font-blue"><span id="total<%=random%>_score">${!empty paper.score?paper.score:0}</span>&nbsp;分</span></strong></span>
    </c:if>
    <strong>${paper.papername}</strong></p>
    <div class="jxxt_float_h600" id="dv_paperdetail<%=random%>" style="height:530px">

    <div class="jxxt_zhuanti_rw_ceshi">
        <p class="jxxt_zhuanti_rw_ceshi_an"><a href="javascript:;" onclick="next<%=random%>(-1)" id="a_free<%=random%>" class="an_test1">上一题</a><a  href="javascript:;" onclick="next<%=random%>(1)" id="a_next<%=random%><%=random%>" class="an_test1">下一题</a></p>
        <ul id="ul_xuhao<%=random%>">
        </ul>
        <div class="clear"></div>
    </div>
    <div id="dv_table<%=random%>" style="width:950px;height:395px;overflow-y:auto;overflow-x: hidden;color:black">
        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq" varStatus="pqIdx">
                <c:set var="teamSize" value="${fn:length(pq.questionTeam)}"/>
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="dv_ques_${pq.questionid}"
                       data-bind="${pq.questionid}" style="display:none"
                        data-idx="<%=++idx%>">
                    <col class="w30"/>
                    <col class="w880"/>
                    <caption>
                      <span class="f_right">
                          <%--如果editQues不等于1，则表示不需要修改铵钮--%>
                          <c:if test="${!empty param.editQues&&param.editQues==1&&pq.paperid<0}">
                            <c:if test="${pq.questiontype<6}">
                                <a   href="javascript:showUpdQues('${pq.paperid}','${pq.questionid}')" class="ico11" title="编辑"></a>
                            </c:if>
                            <a href="javascript:doDelPaperQues('${pq.questionid}')" class="ico04" title="删除"></a>&nbsp;
                            <%--<c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0}">--%>
                                <%--<span class="font-blue"  style="cursor: pointer" data-bind="${pq.questionid}" id="group_${pq.ref}">--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${empty pq.questionTeam and fn:length(pq.questionTeam)==0}">--%>
                                <%--<span class="font-blue"  style="cursor: pointer" data-bind="${pq.questionid}|" id="score_${pq.questionid}">--%>
                            <%--</c:if>--%>
                        </c:if>
                              <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0&&!empty pq.questionTeam}">
                                    <span class="font-blue" id="group_${pq.ref}" name="avg_score<%=random%>" data-bind="${pq.questionid}|${pq.ref}">
                                        <a href="javascript:;" onclick="onClickScore<%=random%>('score_${pq.questionid}')">
                                                ${pq.score}
                                        </a></span>
                                </c:if>
                                 <c:if test="${empty param.dropQuesNum||param.dropQuesNum!=1||paper.paperid>0}">
                                    <span class="font-blue" id="group_${pq.ref}" name="avg_score<%=random%>" data-bind="${pq.questionid}|${pq.ref}">
                                                ${pq.score}
                                    </span>
                                </c:if>
                                <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0&&empty pq.questionTeam}">
                                    <span class="font-blue" id="score_${pq.questionid}" name="avg_score<%=random%>" data-bind="${pq.questionid}|"><a href="javascript:;" onclick="onClickScore<%=random%>('score_${pq.questionid}')">
                                        ${pq.score}
                                    </a></span>
                                </c:if>
                              分</span>
                        <span class="bg">${pq.questiontypename}</span>
                    </caption>

                    <tr>
                        <td>

                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td>
                            ${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}

                            <c:if test="${pq.extension eq 4}">
                                <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                                <script type="text/javascript">
                                    playSound('play','<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                                </script>
                                ${pq.analysis}
                            </c:if>

                            <c:if test="${!empty pq.questionOption and pq.questiontype ne 1}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w880"/>
                                    <c:forEach items="${pq.questionOption}" var="option">
                                        <tr>
                                            <th>
                                                <c:if test="${pq.questiontype eq 3 or pq.questiontype eq 7 }">
                                                    <input disabled type="radio">
                                                </c:if>
                                                <c:if test="${pq.questiontype eq 4 or pq.questiontype eq 8 }">
                                                    <input disabled type="checkbox">
                                                </c:if>
                                            </th>
                                            <td>
                                                ${option.optiontype}&nbsp;${option.content}
                                                <c:if test="${option.isright eq 1}">
                                                    <span class="ico12"></span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>


                        </td>
                    </tr>
                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension ne 5}">
                        <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                            <tr id="dv_ques_${c.questionid}" data-bind="${c.questionid}" >
                                <td>&nbsp;</td>
                                <td><p><span class="width font-blue">
                                    <span class="font-blue"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}"
                                          name="avg_score<%=random%>" id="score_${c.questionid}">
                                         <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
                                    <a href="javascript:;" onclick="onClickScore<%=random%>('score_${c.questionid}')">
                                        </c:if>
                                    ${c.score}
                                     <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
                                            </a>
                                      </c:if>
                                    </span>分</span>
                                    <span data-bind="${c.questionid}">
                                          <%--<script type="text/javascript">--%>
                                              <%--var qteamSize="${fn:length(pq.questionTeam)}";--%>
                                              <%--var tmpIdx=quesOrderIdx<%=random%>-qteamSize;--%>
                                              <%--document.write(tmpIdx+${cidx.index});--%>
                                          <%--</script>--%>
                                            <%--${pqIdx.index+1-fn:length(pq.questionTeam)+cidx.index}--%>
                                        ${cidx.index+1}
                                        </span>. ${c.content}</p>
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <col class="w30"/>
                                        <col class="w880"/>

                                        <c:forEach items="${c.questionOption}" var="option">
                                            <tr>
                                                <th>
                                                    <c:if test="${c.questiontype eq 3 or c.questiontype eq 7}">
                                                        <input disabled type="radio">
                                                    </c:if>
                                                    <c:if test="${c.questiontype eq 4 or c.questiontype eq 8 }">
                                                        <input disabled type="checkbox">
                                                    </c:if>
                                                </th>
                                                <td>
                                                    ${option.optiontype}&nbsp;${option.content}
                                                    <c:if test="${option.isright eq 1}">
                                                        <span class="ico12"></span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>

                            <tr style="display:none" id="tr_jiexi">
                                <td>&nbsp;</td>
                                <td>
                                    <c:if test="${c.questiontype<3 }">
                                        <p>
                                            <strong>正确答案：</strong>${c.correctanswer}
                                        </p>
                                    </c:if>
                                    <p><strong>答案解析：</strong>${c.analysis}</p>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>


                    <c:if test="${pq.questiontype<6}">
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <p>
                                    <strong>正确答案：</strong>
                                    <c:if test="${pq.questiontype eq 1 and pq.questionid>0}">
                                        <c:if test="${!empty pq.questionOption}">
                                            ${pq.questionOption[0].content}
                                        </c:if>
                                    </c:if>

                                    <c:if test="${pq.questiontype eq 1 and pq.questionid<0}">
                                        ${pq.correctanswer}
                                    </c:if>

                                    <c:if test="${pq.questiontype eq 2 }">
                                        ${pq.correctanswer}
                                    </c:if>
                                    <c:if test="${pq.questiontype eq 3 or  pq.questiontype eq 4 }">
                                        <c:if test="${!empty pq.questionOption}">
                                            <c:forEach items="${pq.questionOption}" var="option">
                                                <c:if test="${option.isright eq 1}">
                                                    ${option.optiontype}&nbsp;
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                </p>
                                <p>
                                    <strong>答案解析：</strong>${pq.analysis}
                                </p>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension eq 5}">
                        <tr>
                            <td>&nbsp;</td>
                            <td><p><strong>正确答案及答案解析：</strong></p>
                                <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                                    <p><span class="width font-blue">
                                        <span class="font-blue" name="avg_score<%=random%>"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}"
                                                                           id="score_${c.questionid}">
                                         <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
                                            <a href="javascript:;" onclick="onClickScore<%=random%>('score_${c.questionid}')">
                                         </c:if>
                                             ${c.score}
                                        <c:if test="${!empty param.dropQuesNum&&param.dropQuesNum==1&&paper.paperid<0}">
                                            </a>
                                        </c:if>

                                    </span>分
                                        </span><span data-bind="${c.questionid}"  class="font-blue">
                                          <%--<script type="text/javascript">--%>
                                              <%--var qteamSize="${fn:length(pq.questionTeam)}";--%>
                                              <%--var qoIdx=quesOrderIdx<%=random%>;--%>
                                              <%--var tmpIdx=quesOrderIdx<%=random%>-qteamSize;--%>
                                              <%--document.write(tmpIdx+${cidx.index});--%>
                                          <%--</script>--%>
                                        <%--${pqIdx.index+1-fn:length(pq.questionTeam)}--%>
                                        </span>.
                                        <c:forEach items="${c.questionOption}" var="option">
                                            <c:if test="${option.isright eq 1}">
                                                ${option.optiontype}
                                            </c:if>
                                        </c:forEach>
                                        &nbsp;&nbsp;${c.analysis}
                                    </p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>


                </table>
            </c:forEach>
        </c:if>
    </div>
    </div>
    <div id="dv_bf_dvpdtail<%=random%>" style="display: none">

    </div>


</body>
</html>
