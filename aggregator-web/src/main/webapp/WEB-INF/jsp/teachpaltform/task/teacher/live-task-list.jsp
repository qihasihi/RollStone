
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/tptask.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var pList,pBankList;
var total;
$(function(){
    pList = new PageControl( {
        post_url : 'task?m=ajaxLiveTaskList',
        page_id : 'page1',
        page_control_name : "pList",
        post_form : document.pListForm,
        gender_address_id : 'pListaddress',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 10, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
    pageGo('pList');
});


function preeDoPageSub(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    var param={};
    pObj.setPostParams(param);
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

/**
 <tr>
 <th>直播课任务名称</th>
 <th>学科</th>
 <th>时间</th>
 <th>专题名称</th>
 <th>学校名称</th>
 <th>操作</th>
 </tr>
*
* @param rps
 */

function getInvestReturnMethod(rps){
    var html='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            html+='<tr>';
            html+='<td>'+itm.taskobjname+'</td>';
            html+='<td>'+itm.subjectname+'</td>';
            html+='<td>'+itm.btimeString+"<br>"+itm.etimeString+'</td>';
            html+='<td>'+itm.coursename+'</td>';
            html+='<td>'+itm.name+'</td>';
            html+='<td><a href="javascript:;" onclick="showModel(\'dv_luzhi\');$(\'#hd_task_id\').val('+itm.taskid+')">录制进入</a></td>';
            html+='</tr>';
        });
    }else{
        html='';
    }
    $("#itemList").html(html);



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


    function doSub(){
        var teacherName=$("#name").val();
        var taskid=$("#hd_task_id").val();
        if(teacherName.length<1){
            alert("请输入教师名称!");return;
        }
        if(taskid.length<1){
            alert("系统未获取任务标识!");return;
        }

        $.ajax({
            url:'task?m=GenerateLiveTaskUrl',
            type:'post',
            data:{
                name:teacherName,
                taskid:taskid
            },
            dataType:'json',
            error:function(){

            },
            success:function(rps){
                if(rps.objList!=null&&rps.objList.length>0){
                    $.each(rps.objList,function(ix,itm){
                        if(typeof itm.liveaddress!='undefined'&&itm.liveaddress.length>0)
                            location.href=itm.liveaddress;
                    });
                }
            }
        })
    }

</script>
</head>
<body>

<div class="zhuanti">

</div>


<div class="content2">

    <input type="hidden" id="hd_task_id"/>
    <center><h2>直播课列表</h2></center>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <col class="w220"/>
        <col class="w50"/>
        <col class="w200"/>
        <col class="w150"/>
        <col class="w220"/>
        <col class="w110"/>
        <tr>
            <th>直播课任务名称</th>
            <th>学科</th>
            <th>时间</th>
            <th>专题名称</th>
            <th>学校名称</th>
            <th>操作</th>
        </tr>

        <tbody  id="itemList">

        </tbody>
    </table>

    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>


<div id="dv_luzhi" style="display:none">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input ">
        <col class="w90"/>
        <col class="w600"/>
        <tr>
            <th>请填写教师名称：</th>
            <td><input name="name" id="name" type="text" value="录制" class="w350"/></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td>
                <a href="javascript:;" onclick="doSub()" class="an_public1">确定</a>&nbsp;
                <a href="javascript:closeModel('dv_luzhi');" onclick="" class="an_public1">取消</a>
            </td>
        </tr>
    </table>
</div>


<%@include file="/util/foot.jsp" %>
</body>
</html>
