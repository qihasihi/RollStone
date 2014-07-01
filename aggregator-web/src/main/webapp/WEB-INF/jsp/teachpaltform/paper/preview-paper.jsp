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
   // pageGo('pList');


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

</script>
</head>
<body>
<div class="zhuanti">
    <p>${paper.papername }</p>
    <p style="float: right">主观题：${paper.subjectivenum}&nbsp;客观题：${paper.objectivenum}</p>
    <p style="float: right"><a href="javascript:history.go(-1);">返回</a></p>
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
                        ${pq.score}
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
                    <br><br>
              </c:forEach>
          </c:if>
    </div>
</div>



<%@include file="/util/foot.jsp" %>

</body>
</html>
