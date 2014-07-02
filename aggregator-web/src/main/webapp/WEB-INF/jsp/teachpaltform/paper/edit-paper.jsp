<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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


    $("li[name='li_nav']").each(function(idx,itm){
        $(itm).bind("click",function(){
            $(itm).siblings().removeClass("crumb").end().addClass("crumb");
        })
    })
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


function showDialogPage(type,paperid){
    var url;
    if(type==1){
        url="paper?m=dialogPaper&courseid="+courseid+"&paperid="+paperid;
    }else if(type==2){
        url="paper?m=dialogQuestion&courseid="+courseid+"&paperid="+paperid;
    }else if(type==3){
        url='question?m=toAddQuestion&operate_type=3&courseid='+courseid;
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
    }
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

}
</script>
</head>
<body>
<div>
    <a href="javascript:showDialogPage(1,'${paper.paperid}')">导入试卷</a>
    <a href="javascript:showDialogPage(2,'${paper.paperid}')">导入试题</a>
    <a href="javascript:showDialogPage(3,'${paper.paperid}')">新建试题</a>
</div>
<div class="zhuanti">
    <p>${paper.papername }</p>
    <p style="float: right">主观题：${paper.subjectivenum}&nbsp;客观题：${paper.objectivenum}</p>
</div>
<div class="content2">
    <div class="subpage_lm">
        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq">
                <p>${pq.orderidx}/${fn:length(pqList)}</p>
                <p>
                    <c:if test="${pq.paperid>0}">
                        参
                    </c:if>
                        ${pq.questiontype==1?"其他":pq.questiontype==2?"填空题":pq.questiontype==3?"单选题":pq.questiontype==4?"多选题":""}&nbsp;
                        ${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                    <c:forEach items="${pq.questioninfo.questionOption}" var="option">
                        <c:if test="${pq.questiontype eq 3 }">
                            <br><input disabled type="radio">
                        </c:if>
                        <c:if test="${pq.questiontype eq 4 }">
                            <br><input disabled type="checkbox">
                        </c:if>
                        ${option.optiontype}&nbsp;${option.content};
                        <c:if test="${option.isright eq 1}">
                            <span class="ico12"></span>
                        </c:if>
                    </c:forEach>
                </p>
                <p style="float: right">
                        <span  style="cursor: pointer" id="score_${pq.questionid}">${pq.score}</span>
                </p>
                <p>
                    正确答案：
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
                    答案解析：${pq.analysis}
                </p>
                <br><br><br>
            </c:forEach>
        </c:if>
    </div>
</div>



<%@include file="/util/foot.jsp" %>
<script type="text/javascript">
    $("span").filter(function(){return this.id.indexOf('score_')!=-1}).each(function(idx,itm){
       $(itm).bind("click",function(){
           genderInput(itm,$(itm).html());
       });
    });
</script>
</body>
</html>
