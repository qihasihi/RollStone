<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>


<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var pList,pBankList;
var total;
$(function(){
    pList = new PageControl( {
        post_url : 'paper?m=ajaxPaperList',
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
    var html='',shtml='<li><a href="javascript:void(0);" onclick="showModel(\'dv_paper_name\')" class="kapian"><span class="ico82"></span></a></li>';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            if(itm.paperid>0){
                html+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                html+='<p class="one">'+itm.papername+'</p>';
                html+='<p class="two">';
                if(itm.objectivenum>0&&itm.subjectivenum>0)
                    html+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                else if(itm.objectivenum>0)
                    html+='<span class="bg1" style="width:100%">'+itm.objectivenum+'</span>';
                else if(itm.subjectivenum>0)
                    html+='<span class="bg2" style="width:100%">'+itm.subjectivenum+'</span>';
                html+='</p></a>';
                html+='<p class="pic">';
                if(itm.papertype==5)
                    html+='<a class="ico_wsp1" title="微视频"></a>';
                if(itm.taskflag<1)
                    html+='<a href="task?toAddTask&courseid='+courseid+'&tasktype=4&taskvalueid='+itm.paperid+'""><b><span class="ico51" title="发任务"></span></b></a>';
                else
                    html+='<b><span class="ico52" title="已发任务"></span></b>';
                html+='</p>';
                html+='</li>';
            }else{
                shtml+='<li>';
                if(itm.papertype==4)
                    shtml+='<a>';
                else{
                    if(itm.taskflag<1)
                        shtml+='<a href="paper?m=editPaperQuestion&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                    else
                        shtml+='<a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                }

                shtml+='<p class="one">'+itm.papername+'</p>';
                shtml+='<p class="two">';
                if(itm.objectivenum>0&&itm.subjectivenum>0)
                    shtml+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                else if(itm.objectivenum>0)
                    shtml+='<span class="bg1" style="width:100%">'+itm.objectivenum+'</span>';
                else if(itm.subjectivenum>0)
                    shtml+='<span class="bg2" style="width:100%">'+itm.subjectivenum+'</span>';
                else if(typeof itm.quesnum!='undefined')
                    shtml+='<span class="bg1" style="width:100%">'+itm.quesnum+'</span>';
                shtml+='</p></a>';
                shtml+='<p class="pic">';
                if(itm.papertype==5)
                    shtml+='<a class="ico_wsp1" title="微视频"></a>';
                if(itm.taskflag<1){
                    shtml+='<a href="task?toAddTask&courseid='+courseid+'&tasktype=4&taskvalueid='+itm.paperid+'""><b><span class="ico51" title="发任务"></span></b></a>';
                    shtml+='<a  href="javascript:doDelPaper('+itm.ref+')"><b><span class="ico04" title="删除"></span></b></a>';
                }else{
                    shtml+='<b><span class="ico52" title="已发任务"></span></b>';
                }
                shtml+='</p>';
                shtml+='</li>';
            }
        });
    }
    $("#ul_standard").html(html);
    $("#ul_native").html(shtml);


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
    var param={courseid:courseid};
    pObj.setPostParams(param);
}


function showCourseList(){
    var ulobj=$("#ul_courselist");
    if(ulobj.css("display")=="none")
        ulobj.show();
    else
        ulobj.hide();
}

    function doDelPaper(ref){
        if(typeof  ref=='undefined')
            return;
        if(!confirm("确认删除?"))return;
        $.ajax({
            url:"paper?m=doOperatePaper",
            type:"post",
            data:{ref:ref,flag:2},
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },success:function(rmsg){
                if(rmsg.type=="error"){
                    alert(rmsg.msg);
                }else{
                    alert(rmsg.msg);
                    pageGo('pList');
                }
            }
        });
    }
</script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div class="zhuanti">
    <p>${coursename }<a class="ico13" href="javascript:showCourseList();"></a></p>
    <ul  style="display:none;" id="ul_courselist">
        <c:if test="${!empty courseList}">
            <c:forEach items="${courseList}" var="c">
                <c:if test="${c.courseid!=courseid}">
                    <li>
                        <a href="task?toTaskList&courseid=${c.courseid }">${c.coursename }</a>
                    </li>
                </c:if>
            </c:forEach>
        </c:if>
    </ul>
</div>

<div class="subpage_nav">
    <ul>
        <li ><a href="task?toTaskList&courseid=${courseid }">学习任务</a></li>
        <li><a  href="tpres?toTeacherIdx&courseid=${courseid }&termid=${termid}">专题资源</a></li>
        <li><a href="tptopic?m=index&courseid=${courseid }">互动空间</a></li>
        <!--<li>
                <a href="commoncomment?m=toCourseCommentList&objectid=${courseid }">专题评价</a>
            </li> -->
        <li><a href="question?m=toQuestionList&courseid=${courseid}">试&nbsp;&nbsp;题</a></li>
        <li class="crumb"><a href="javascript:void (0);">试&nbsp;&nbsp;卷</a></li>
    </ul>
</div>



<div class="content1 font-black">
    <p class="jxxt_zhuanti_shijuan_text">图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题<a class="ico15" href="tpres?m=toRecycleIdx&type=5&courseid=${courseid}" title="回收站"></a></p>
    <c:if test="${!empty courselevel and courselevel ne 3}">
        <p><strong>标准试卷</strong></p>
        <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard">

        </ul>
    </c:if>

    <p class="p_t_10"><strong>自建试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_native">

    </ul>
</div>

<div class="public_windows public_input"  style="display: none;" id="dv_paper_name">
    <h3><a href="javascript:closeModel('dv_paper_name')"  title="关闭"></a>添加试卷</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 ">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>试卷名称：</th>
            <td><input name="papername" id="papername" type="text" class="w300"/></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:closeModel('dv_paper_name')" class="an_public1">取&nbsp;消</a>&nbsp;&nbsp;<a href="javascript:addPaper()" class="an_public1">下一步</a></td>
        </tr>
    </table>
</div>

<div class="content2" style="display: none">
    <div class="subpage_lm">
        <p class="f_right"><a class="ico15" target="_blank" href="tpres?m=toRecycleIdx&type=5&courseid=${courseid}" title="回收站"></a></p>

    </div>

    <div id="dv_task">
        <p class="jxxt_zhuanti_add font-darkblue"><a href="javascript:void(0);" onclick="showModel('dv_paper_name')" ><span class="ico26"></span>添加试卷</a></p>
        <div class="jxxt_zhuanti" id="initItemList">

        </div>
    </div>
    <form id="pListForm" name="pListForm" style="display: none;">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>



<%@include file="/util/foot.jsp" %>

</body>
</html>
