<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    if(session.getAttribute("IP_PROC_NAME")==null||!session.getAttribute("IP_PROC_NAME").toString().equals(basePath))
        session.setAttribute("IP_PROC_NAME",basePath);
%>

<html>
<head>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var paperid="${paper.paperid}";
var pList,pBankList;
var total;
$(function(){
//    pList = new PageControl( {
//        post_url : 'paperques?m=ajaxPaperQuestionList',
//        page_id : 'page1',
//        page_control_name : "pList",
//        post_form : document.pListForm,
//        gender_address_id : 'pListaddress',
//        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
//        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
//        return_type : 'json', //放回的值类型
//        page_no : 1, //当前的页数
//        page_size : 9999, //当前页面显示的数量
//        rectotal : 0, //一共多少
//        pagetotal : 1,
//        operate_id : "initItemList"
//    });
  //  pageGo('pList');
  //  $("#p_operate").css("left",(findDimensions().width/2-parseFloat($("#p_operate").css("width"))/2)+"px");
  reSetScrollDiv();
  //加载试卷
  loadPaperQues();
});

function loadPaperQues(){
    $("#paper_detail").hide();
    $("#paper_detail").load("paper?toPreviewPaperModel&courseid=${courseid}&paperid="+paperid+"&op_type1=1&dropQuesNum=1&editQues=1"
            ,function(rps){
                // $("dv_load_topic").html(rps);
//                        $(".content1:last").removeClass("content1");
//                        $(".subpage_head:last").remove();
//                        $(".foot:last").remove();
//                        $("#fade:last").remove();
                $("#paper_detail .float_title").remove();
                $("#paper_detail #dv_paperdetail").removeClass("jxxt_float_h600");
                $(".an_small").removeClass("an_small").addClass("an_public1");
                if($("#dv_table>table").length<1){
                    $("#a_sb_taskpaper").hide();
                }else
                    $("#a_sb_taskpaper").show();
                //$("#dv_selectMic_child").show();
//                $("#dv_selectPaper").hide();

                $("#paper_detail").fadeIn("slow");
            });
}





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
        loadPaperQues();
    }else if(type==2){//导入试题
        loadPaperQues();
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
                    loadPaperQues();
                }
            }
        });
    }else if(type==4){
        loadPaperQues();
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
        var num=$(itm).html().split('-')[0];
        var htm=$(itm).html()//parseInt(maxnum)<=parseInt(num)?$(itm).html():$(itm).html()+'-'+maxnum;
        var quesid=$(itm).data().bind.split("|")[0];
        if(parseInt(maxnum) > parseInt(num) && idx!=0)
            num=maxnum;
        h+='<option data-bind="'+quesid+'|'+htm+'" value="'+num+'">'+htm+'</option>';
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
        var htm=$(this).find('option:selected').data().bind || "";
        spanObj.html(htm.split("|")[1]);
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
    var htm=$(obj).find('option:selected').data().bind || "";
    var descQuesid=htm.split('|')[0];
    var quesid=$(obj).parent().data().bind.split('|')[0];
    editQuesIdx(quesid,h,descQuesid);
    $(obj).parent().html(h);
}


/**
 * 修改试题顺序
 * @param taskid
 * @param orderidx
 */
function editQuesIdx(quesid,orderidx,descQuesid){
    if(typeof quesid=='undefined'||orderidx=='undefined')
        return;
    var param={ paperid:paperid,orderidx:orderidx,questionid:quesid};
    if(typeof descQuesid!='undefined'&&descQuesid.toString().length>0)
        param.descquesid=descQuesid
    $.ajax({
        url:"paperques?m=doUpdOrderIdx",
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

function xround(x, num){
    return Math.round(x * Math.pow(10, num)) / Math.pow(10, num) ;
}

/**
 * 修改试题分数
 * @param score
 * @param quesid
 */
function updateQuesScore(score,quesid,ref){
    if(typeof score=='undefined'||isNaN(score))
        return;
    var param={courseid:courseid,questionid:quesid,paperid:paperid,score:score};
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
                   $("#total_score").html(xround(rmsg.objList[0],1)+"&nbsp;分");
                   if(typeof ref!='undefined')
                       $("#group_"+ref).html(xround(rmsg.objList[1],1))
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
                loadPaperQues();
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
//    var divObj=document.getElementById("p_operate");
//    if(getScrollTop()>parseInt(divObj.style.top)){
//        divObj.style.top="0px";
////        $("#p_operate").css({"position":"fixed"})
//    }else{
//        divObj.style.top="100px";
//        $("#p_operate").css("position","");
//    }
//    setTimeout("reSetScrollDiv()",100);
}
</script>
</head>
<body>
    <p class="float_title"><a href="javascript:;" onclick="loadDiv(2);" class="ico93" title="返回"></a>新建试卷</p>
    <c:if test="${paper.paperid<0}">
         <div  id="p_operate" class="t_c">
             <a href="javascript:showImportPaper(${paper.paperid})" class="an_big">导入试卷</a>
             &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showImportQues(${paper.paperid})" class="an_big">导入试题</a>
             &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showCreateQues(${paper.paperid})" class="an_big">新建试题</a></div>
    </c:if>
    <div id="paper_detail" style="display:none">
    </div>
    <p class="t_c"><a href="javascript:doAddPaperTask();" id="a_sb_taskpaper" class="an_small">提&nbsp;交</a></p>
    <%--<p class="t_c p_tb_10"><a href="javascript:history.go(-1);" class="an_small">提&nbsp;交</a>&nbsp;&nbsp;&nbsp;&nbsp;<!--<a href="1" target="_blank" class="an_small">取&nbsp;消</a>--></p>--%>

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
