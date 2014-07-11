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


function showDialogPage(type,paperid,quesid){
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
    var param = "dialogWidth:500px;dialogHeight:700px;status:no;location:no";
    var returnValue=window.showModalDialog(url,param);
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
    mousePostion
    var idxVal=$(obj).html();
// style="position: absolute;left: '+mousePostion.x+'px;top:'+mousePostion.y+'px"
    var h='<select id="sel_order_idx">';
    for(var i=1;i<=total;i++){
        h+='<option value="'+i+'">'+i+'</option>';
    }
    h+='</select>';
    $(obj).html(h);
    $("select[id='sel_order_idx']").focus();
    if(idxVal!=null&&idxVal>0)
        $("select[id='sel_order_idx']").val(idxVal);
    $(obj).unbind('click');

    $("select[id='sel_order_idx']").bind("blur",function(){
        var spanObj=$(this).parent();
        spanObj.html(this.value);
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
    var quesid=$(obj).parent().data().bind;
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
    var h='<input type="text" id="score_input" value="'+score+'" />';
    $(obj).html(h);
    $("input[id='score_input']").focus();
    $(obj).unbind('click');
    $("input[id='score_input']").bind("blur",function(){
        var spanObj=$(this).parent();
        var score=this.value
        if(score<1)
            return;
        spanObj.html(score);
        var quesid=$(spanObj).data().bind;
        updateQuesScore(score,quesid);
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
    var quesid=$(obj).parent().data().bind;
    updateQuesScore(score,quesid);
}


/**
 * 修改试题分数
 * @param score
 * @param quesid
 */
function updateQuesScore(score,quesid){
    if(typeof score=='undefined'||isNaN(score))
        return;
    $.ajax({
        url:"paperques?m=doUpdPaperQuesScore",
        type:"post",
        data:{questionid:quesid,paperid:paperid,score:score},
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
    var posX,posY;
    if (window.innerHeight) {
        posX = window.pageXOffset;
        posY = window.pageYOffset;
    }
    else if (document.documentElement && document.documentElement.scrollTop) {
        posX = document.documentElement.scrollLeft;
        posY = document.documentElement.scrollTop;
    }
    else if (document.body) {
        posX = document.body.scrollLeft;
        posY = document.body.scrollTop;
    }
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

    <!--
       background: url("images/bg02_131230.png") no-repeat scroll 0 -69px rgba(0, 0, 0, 0);
    height: 30px;
    margin: 0 auto;
    padding: 35px 5px 0;
    width: 990px;
    -->
    <p  id="p_operate" style="top: 100px;" class="t_c p_tb_10"><a href="javascript:showDialogPage(1,'${paper.paperid}')" class="an_big">导入试卷</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showDialogPage(2,'${paper.paperid}')" class="an_big">导入试题</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showDialogPage(3,'${paper.paperid}')" class="an_big">新建试题</a></p>
    <div class="jxxt_zhuanti_shijuan_add font-black public_input">
        <p class="title"><strong class="f_right">总分值：<span id="total_score" class="font-blue">${paper.score}&nbsp;分</span></strong><strong>${paper.papername}</strong></p>

        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq">
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="dv_ques_${pq.questionid}" data-bind="${pq.questionid}">
                    <col class="w30"/>
                    <col class="w910"/>
                    <caption><span class="f_right"><a   href="javascript:showDialogPage(4,'${pq.paperid}','${pq.questionid}')" class="ico11" title="编辑"></a><a href="javascript:doDelPaperQues('${pq.questionid}')" class="ico04" title="删除"></a>&nbsp;<span class="font-blue">
                         <span class="font-blue"  style="cursor: pointer" data-bind="${pq.questionid}" id="score_${pq.questionid}">${pq.score}</span>分</span></span>
                        <span id="idx_${pq.questionid}" data-bind="${pq.questionid}"  class="font-blue">${pq.orderidx}</span>/${fn:length(pqList)}</caption>


                    <tr>
                        <td>
                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td><span class="bg">${pq.questiontype==1?"其他":pq.questiontype==2?"填空题":pq.questiontype==3?"单选题":pq.questiontype==4?"多选题":""}：</span>${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                            <c:if test="${!empty pq.questioninfo.questionOption}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w880"/>

                                <c:forEach items="${pq.questioninfo.questionOption}" var="option">
                                    <tr>
                                        <th>
                                            <c:if test="${pq.questiontype eq 3 }">
                                                <input disabled type="radio">
                                            </c:if>
                                            <c:if test="${pq.questiontype eq 4 }">
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

                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <p>
                                <strong>正确答案：</strong>
                                <c:if test="${pq.questiontype eq 1 or  pq.questiontype eq 2 }">
                                    ${pq.correctanswer}
                                </c:if>
                                <c:if test="${pq.questiontype eq 3 or  pq.questiontype eq 4 }">
                                    <c:forEach items="${pq.questioninfo.questionOption}" var="option">
                                        <c:if test="${option.isright eq 1}">
                                            ${option.optiontype}&nbsp;
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </p>
                            <p>
                                <strong>答案解析：</strong>${pq.analysis}
                            </p>
                        </td>
                    </tr>
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
           genderInput(itm,$(itm).html());
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
