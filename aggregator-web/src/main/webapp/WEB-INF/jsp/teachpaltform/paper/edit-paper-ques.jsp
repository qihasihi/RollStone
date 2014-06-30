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
    pageGo('pList');


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
<div>
    <a href="">导入试卷</a>
    <a href="javascript:importQuesList()">导入试题</a>
    <a href="">新建试题</a>
</div>
<div class="zhuanti">
    <p>${paper.papername }</p>

</div>
<div class="content2">
    <div class="subpage_lm">

    </div>


    <form id="pListForm" name="pListForm" style="display: none;">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>



<%@include file="/util/foot.jsp" %>

</body>
</html>
