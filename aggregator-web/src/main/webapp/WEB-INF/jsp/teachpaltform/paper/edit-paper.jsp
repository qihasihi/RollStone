<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>


<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var paperid="${paper.paperid}";
var pList,pBankList;
var total;
$(function(){
    pList = new PageControl( {
        post_url : 'paperques?m=ajaxPaperQuestionList',
        page_id : 'page1',
        page_control_name : "pList",
        post_form : document.pListForm,
        gender_address_id : 'pListaddress',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 9999, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
  //  pageGo('pList');
  //  $("#p_operate").css("left",(findDimensions().width/2-parseFloat($("#p_operate").css("width"))/2)+"px");
  reSetScrollDiv();
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



function getInvestReturnMethod(rps){
    var html='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){

        });
    }
    $("#initItemList").html(html);



    if(rps.objList.length>0){
        pList.setPagetotal(rps.presult.pageTotal);
        pList.setRectotal(rps.presult.recTotal);
        pList.setPageSize(rps.presult.pageSize);
        pList.setPageNo(rps.presult.pageNo);
    }else
    {
        pList.setPagetotal(0);
        pList.setRectotal(0);
        pList.setPageNo(1);
    }
    pList.Refresh();
}
function preeDoPageSub(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid,paperid:paperid};
    pObj.setPostParams(param);
}


function showDialogPage(type,paperid,quesid,e){
    var url;
    if(type==1){
        url="paper?m=dialogPaper&courseid="+courseid+"&paperid="+paperid;
    }else if(type==2){
        url="paper?m=dialogQuestion&courseid="+courseid+"&paperid="+paperid;
    }else if(type==3){
        url='question?m=toAddQuestion&operate_type=3&courseid='+courseid;
    }else if(type==4){
        url='question?m=toUpdQuestion&courseid='+courseid+"&paperid="+paperid+"&questionid="+quesid;
    }

    var paramObj={width:1000,height:700}

    var left =findDimensions().width/2-parseFloat($("#p_operate").css("width"))/2;
    var top =100;//findDimensions().height/2-parseFloat($("#p_operate").css("height"))/2-120;


    var param = 'dialogWidth:'+paramObj.width+'px;dialogHeight:'+paramObj.height+'px;dialogLeft:'+left+'px;dialogTop:'+top+'px;status:no;location:no';
    var returnValue=window.showModalDialog(url,"",param);
    if (returnValue == undefined) {
        returnValue = window.returnValue;
    }
    if(returnValue==null||returnValue.toString().length<1){
        alert("操作取消!");
        return;
    }
    if(type==1){//导入试卷
        window.reload();
    }else if(type==2){//导入试题
        window.reload();
    }else if(type==3){ //新建试题
        $.ajax({
            url:"paperques?m=doAddQues",
            type:"post",
            data:{courseid:courseid,questionid:returnValue,paperid:paperid},
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },success:function(rmsg){
                if(rmsg.type=="error"){
                    alert(rmsg.msg);
                }else{
                   alert(rmsg.msg);
                   window.reload();
                }
            }
        });
    }else if(type==4){
        window.reload();
    }
}


function spanClick(obj,total){
    var idxObj=$("select[id='sel_order_idx']");
    if(idxObj.length>0)
        idxChange(idxObj,total);
    var idxVal=$(obj).data().bind.split("|")[1];
    var h='<select id="sel_order_idx">';
    var bindObj=$("span").filter(function(){return this.id.indexOf("idx_")!=-1});
    $.each(bindObj,function(idx,itm){
        var maxnum=$(itm).data().bind.split("|")[1];
        var num=$(itm).html();
        var htm=parseInt(maxnum)<=parseInt(num)?$(itm).html():$(itm).html()+'-'+maxnum;
        if(parseInt(maxnum) > parseInt(num) && idx!=0)
            num=maxnum;
        h+='<option data-bind="'+$(itm).html()+'" value="'+num+'">'+htm+'</option>';
    });
    h+='<option value="0">调至最后</option>';
    h+='</select>';
    $(obj).html(h);
    $("select[id='sel_order_idx']").focus();
    if(idxVal!=null&&idxVal>0)
        $("select[id='sel_order_idx']").val(idxVal);
    $(obj).unbind('click');

    $("select[id='sel_order_idx']").bind("blur",function(){
        var spanObj=$(this).parent();
        spanObj.html($(this).find('option:selected').data().bind);
        $(spanObj).bind("click",function(){
            spanClick(spanObj,total);
        });
    });
    $("select[id='sel_order_idx']").bind("change",function(){
        idxChange(this,total);
    });

}

function idxChange(obj,total){
    //恢复事件
    $(obj).parent().bind("click",function(){
        spanClick(this,total);
    });
    var h=$(obj).find('option:selected').val();
    var quesid=$(obj).parent().data().bind.split('|')[0];
    editQuesIdx(quesid,h);
    $(obj).parent().html(h);
}


/**
 * 修改试题顺序
 * @param taskid
 * @param orderidx
 */
function editQuesIdx(quesid,orderidx){
    if(typeof quesid=='undefined'||orderidx=='undefined')
        return;

    $.ajax({
        url:"paperques?m=doUpdOrderIdx",
        type:"post",
        data:{
            paperid:paperid,
            orderidx:orderidx,
            questionid:quesid
        },
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                alert(rmsg.msg);
                window.reload();
            }
        }
    });

}




function genderInput(obj,score){
    var iptObj=$("input[id='score_input']");
    if(iptObj.length>0)
        scoreChange(iptObj,score);
    var h='<input type="text" class="w30"  id="score_input" value="'+score+'" />';
    $(obj).html(h);
    $("input[id='score_input']").focus();
    $(obj).unbind('click');
    $("input[id='score_input']").bind("blur",function(){
        var spanObj=$(this).parent();
        var score=this.value
        if(score<1)
            return;
        spanObj.html(score);
        var quesid=$(spanObj).data().bind.split("|")[0];
        var ref=$(spanObj).data().bind.split("|")[1];
        updateQuesScore(score,quesid,ref);
        $(spanObj).bind("click",function(){
            genderInput(spanObj,score);
        });
    });
    $("input[id='score_input']").bind("keyup", function() {
        this.value = this.value.replace(/[^\d\.]/g, '');
    });


}

function scoreChange(obj,score){
    //恢复事件
    if(score<1)
        return;
    $(obj).parent().bind("click",function(){
        genderInput(this,score);
    });
    $(obj).parent().html(score);
    var quesid=$(obj).parent().data().bind.split("|")[0];
    var ref=$(obj).parent().data().bind.split("|")[1];
    updateQuesScore(score,quesid,ref);
}


/**
 * 修改试题分数
 * @param score
 * @param quesid
 */
function updateQuesScore(score,quesid,ref){
    if(typeof score=='undefined'||isNaN(score))
        return;
    var param={questionid:quesid,paperid:paperid,score:score};
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
                alert(rmsg.msg);
                if(rmsg.objList.length>0){
                   $("#total_score").html(rmsg.objList[0]);
                   if(typeof ref!='undefined')
                       $("#group_"+ref).html(rmsg.objList[1])
                }
            }
        }
    });
}

/**
 * 删除试卷试题
 * @param quesid
 */
function doDelPaperQues(quesid){
    if(typeof quesid=='undefined'||isNaN(quesid))
        return;
    if(!confirm("确认删除?"))return;
    $.ajax({
        url:"paperques?m=doDelPaperQues",
        type:"post",
        data:{questionid:quesid,paperid:paperid},
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                alert(rmsg.msg);
                window.reload();
            }
        }
    });
}


function reSetScrollDiv(){
    //$("#p_operate").css({"left":(findDimensions().width/2-parseFloat($("#p_operate").css("width"))/2)+"px"});
   /* var divObj=document.getElementById("p_operate");
    if(getScrollTop()>parseInt(divObj.style.top)){
        divObj.style.top="0px";
        $("#p_operate").css({"position":"fixed"})
    }else{
        divObj.style.top="100px";
        $("#p_operate").css("position","");
    } */
    var divObj=document.getElementById("p_operate");
    if(getScrollTop()>parseInt(divObj.style.top)){
        divObj.style.top="0px";
        $("#p_operate").css({"position":"fixed"})
    }else{
        divObj.style.top="100px";
        $("#p_operate").css("position","");
    }
    setTimeout("reSetScrollDiv()",100);
}
</script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加试卷</strong></div>
<div class="content2">

     <div  id="p_operate" class="jxxt_zhuanti_shijuan_add_an"    ><a href="javascript:showDialogPage(1,'${paper.paperid}','',this)" class="an_big">导入试卷</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showDialogPage(2,'${paper.paperid}','',this)" class="an_big">导入试题</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showDialogPage(3,'${paper.paperid}','',this)" class="an_big">新建试题</a></div>
    <div class="jxxt_zhuanti_shijuan_add font-black public_input">
        <p class="title">
            <strong class="f_right" >总分值：<span id="total_score" class="font-blue">${paper.score}&nbsp;分</span></strong><strong>${paper.papername}</strong>
        </p>

        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq">
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="dv_ques_${pq.questionid}" data-bind="${pq.questionid}">
                    <col class="w30"/>
                    <col class="w910"/>
                    <caption><span class="f_right">
                        <c:if test="${pq.questiontype<6}">
                            <a   href="javascript:showDialogPage(4,'${pq.paperid}','${pq.questionid}',this)" class="ico11" title="编辑"></a>
                        </c:if>
                        <a href="javascript:doDelPaperQues('${pq.questionid}')" class="ico04" title="删除"></a>&nbsp;<span class="font-blue">
                        <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0}">
                            <span class="font-blue"  style="cursor: pointer" data-bind="${pq.questionid}" id="group_${pq.ref}">
                        </c:if>
                        <c:if test="${empty pq.questionTeam and fn:length(pq.questionTeam)==0}">
                            <span class="font-blue"  style="cursor: pointer" data-bind="${pq.questionid}|" id="score_${pq.questionid}">
                        </c:if>
                         ${pq.score}</span>分</span>
                    </span>
                        <span id="idx_${pq.questionid}" data-bind="${pq.questionid}|${pq.orderidx+(fn:length(pq.questionTeam)>0?fn:length(pq.questionTeam)-1:0)}"  class="font-blue">${pq.orderidx}</span><c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0}">-${pq.orderidx+fn:length(pq.questionTeam)-1}</c:if>
                        <!--/${fn:length(pqList)+fn:length(childList)}-->
                            </caption>

                    <tr>
                        <td>
                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td>
                            <span class="bg">${pq.questiontypename}</span>${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                            <c:if test="${pq.extension eq 4}">
                                <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                                <script type="text/javascript">
                                    playSound('play','<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                                </script>
                            </c:if>
                            <c:if test="${!empty pq.questionOption}">
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
                                            ${option.optiontype}&nbsp;${option.content};
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
                            <tr>
                                <td>&nbsp;</td>
                                <td><p><span class="width font-blue"><span class="font-blue"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}" id="score_${c.questionid}">
                                        ${c.score}</span>分</span><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>. ${c.content}</p>
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
                                                        ${option.optiontype}&nbsp;${option.content};
                                                    <c:if test="${option.isright eq 1}">
                                                        <span class="ico12"></span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>

                            <tr>
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
                                    <c:if test="${pq.questiontype eq 1 or  pq.questiontype eq 2 }">
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
                           <p><span class="width font-blue"><span class="font-blue"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}" id="score_${c.questionid}">${c.score}</span>
                                        分</span><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>.
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
    <p class="t_c p_tb_10"><a href="javascript:history.go(-1);" class="an_small">提&nbsp;交</a>&nbsp;&nbsp;&nbsp;&nbsp;<!--<a href="1" target="_blank" class="an_small">取&nbsp;消</a>--></p>
</div>


<%@include file="/util/foot.jsp" %>
<script type="text/javascript">
    $("span").filter(function(){return this.id.indexOf('score_')!=-1}).each(function(idx,itm){
       $(itm).bind("click",function(){
           genderInput(itm,$(itm).html().Trim());
       });
    });

    total="${fn:length(pqList)}";

    $("span").filter(function(){return this.id.indexOf('idx_')!=-1}).each(function(idx,itm){
        $(itm).bind("click",function(){
            spanClick(itm,total);
        });
    });

    $("div").filter(function(){return this.id.indexOf('dv_ques_')!=-1}).each(function(idx,itm){
        var quesid=$(itm).data().bind;
        $(itm).hover(
                function(){
                    $("#edit_"+quesid+"").show();
                },
                function(){
                    $("#edit_"+quesid+"").hide();
                });
    });
</script>
</body>
</html>
