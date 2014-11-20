<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    if(session.getAttribute("IP_PROC_NAME")==null||!session.getAttribute("IP_PROC_NAME").toString().equals(basePath))
        session.setAttribute("IP_PROC_NAME",basePath);
%>

<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var paperid="${paperid}";
var subjectid="${subjectid}";
var materialid="${materialid}";
var gradeid="${gradeid}";
var pListPaper,pBankList,pCloudPaper;
var pRelatePaper,pRelateClundPaper;
var total;
$(function(){
    pListPaper = new PageControl( {
        post_url : 'paper?m=getImportPaperList',
        page_id : 'page1',
        page_control_name : "pListPaper",
        post_form : document.pListForm_Paper,
        gender_address_id : 'pListaddress_paper',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 4, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });

    pCloudPaper = new PageControl( {
        post_url : 'paper?m=getImportCloudPaperList',
        page_id : 'page2',
        page_control_name : "pCloudPaper",
        post_form : document.pListForm_CloudPaper,
        gender_address_id : 'pListaddress_Cloudpaper',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getCloudPaperList, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 4, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });



    pRelatePaper = new PageControl( {
        post_url : 'paper?m=ImpCustomPaperList',
        page_id : 'page3',
        page_control_name : "pRelatePaper",
        post_form : document.pListForm_RPaper,
        gender_address_id : 'pListaddress_Rpaper',
        http_free_operate_handler : preDoPageSubRelate, //执行查询前操作的内容
        http_operate_handler : getInvestRelateReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 4, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });

    pRelateClundPaper = new PageControl( {
        post_url : 'paper?m=ImpStandardPaperList',
        page_id : 'page4',
        page_control_name : "pRelateClundPaper",
        post_form : document.pListForm_RCloudPaper,
        gender_address_id : 'pListaddress_RCloudpaper',
        http_free_operate_handler : preDoPageSubRelate, //执行查询前操作的内容
        http_operate_handler : getRelateCloudPaperList, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 4, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });




  /*  var coursename=$("#txt_search_paper");
    if(coursename.val().Trim().length>0&&coursename.val()!='输入专题名称'){
        pageGo('pListPaper');
        pageGo('pCloudPaper');
    } */

    pageGo("pRelatePaper");
    pageGo("pRelateClundPaper");

});



function getInvestReturnMethod(rps){
    var html='',shtml='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
                shtml+='<li><a href="javascript:" onclick="loadPaperDetail('+itm.paperid+','+itm.courseid+')">';
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
                shtml+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico43" title="导入"></span></b></a>';
                shtml+='</p>';
                shtml+='</li>';
        });
    }else{
        shtml='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
    }
    $("#ul_native_paper").html(shtml);

    if(rps.objList.length>0){
        pListPaper.setPagetotal(rps.presult.pageTotal);
        pListPaper.setRectotal(rps.presult.recTotal);
        pListPaper.setPageSize(rps.presult.pageSize);
        pListPaper.setPageNo(rps.presult.pageNo);
    }else
    {
        pListPaper.setPagetotal(0);
        pListPaper.setRectotal(0);
        pListPaper.setPageNo(1);
    }
    pListPaper.Refresh();
}

//关联的自建试卷
function getInvestRelateReturnMethod(rps){
    var html='',shtml='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            shtml+='<li><a href="javascript:" onclick="loadPaperDetail('+itm.paperid+','+itm.courseid+')">';
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
            shtml+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico43" title="导入"></span></b></a>';
            shtml+='</p>';
            shtml+='</li>';
        });
    }else{
        shtml='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
    }
    $("#ul_native_paper").html(shtml);

    if(rps.objList.length>0){
        pRelatePaper.setPagetotal(rps.presult.pageTotal);
        pRelatePaper.setRectotal(rps.presult.recTotal);
        pRelatePaper.setPageSize(rps.presult.pageSize);
        pRelatePaper.setPageNo(rps.presult.pageNo);
    }else
    {
        pRelatePaper.setPagetotal(0);
        pRelatePaper.setRectotal(0);
        pRelatePaper.setPageNo(1);
    }
    pRelatePaper.Refresh();
}







function getCloudPaperList(rps){
    var html='',shtml='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
                html+='<li><a href="javascript:" onclick="loadPaperDetail('+itm.paperid+','+itm.courseid+')">';
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
                html+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico43" title="导入"></span></b></a>';
                html+='</p>';
                html+='</li>';
        });
    }else{
        html='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
    }
    $("#ul_standard_paper").html(html);




    if(rps.objList.length>0){
        pCloudPaper.setPagetotal(rps.presult.pageTotal);
        pCloudPaper.setRectotal(rps.presult.recTotal);
        pCloudPaper.setPageSize(rps.presult.pageSize);
        pCloudPaper.setPageNo(rps.presult.pageNo);
    }else
    {
        pCloudPaper.setPagetotal(0);
        pCloudPaper.setRectotal(0);
        pCloudPaper.setPageNo(1);
    }
    pCloudPaper.Refresh();
}


//关联的标准试卷
function getRelateCloudPaperList(rps){
    var html='',shtml='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            html+='<li><a href="javascript:" onclick="loadPaperDetail('+itm.paperid+','+itm.courseid+')">';
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
            html+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico43" title="导入"></span></b></a>';
            html+='</p>';
            html+='</li>';
        });
    }else{
        html='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
    }
    $("#ul_standard_paper").html(html);




    if(rps.objList.length>0){
        pRelateClundPaper.setPagetotal(rps.presult.pageTotal);
        pRelateClundPaper.setRectotal(rps.presult.recTotal);
        pRelateClundPaper.setPageSize(rps.presult.pageSize);
        pRelateClundPaper.setPageNo(rps.presult.pageNo);
    }else
    {
        pRelateClundPaper.setPagetotal(0);
        pRelateClundPaper.setRectotal(0);
        pRelateClundPaper.setPageNo(1);
    }
    pRelateClundPaper.Refresh();
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
    $("#pListaddress_RCloudpaper").hide();
    $("#pListForm_RPaper").hide();
    var param={courseid:courseid,materialid:materialid,gradeid:gradeid,subjectid:subjectid};
    var coursename=$("#txt_search_paper");
    if(coursename.val().Trim().length<1||coursename.val()=='输入专题名称'){
        return;
    }
    param.coursename=coursename.val();
    pObj.setPostParams(param);
}


function preDoPageSubRelate(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid,materialid:materialid,gradeid:gradeid,subjectid:subjectid};
   // $("#pListaddress_Cloudpaper").hide();
   // $("#pListForm_Paper").hide();
    pObj.setPostParams(param);
}


    function importPaperQues(currentcourseid,currentpaperid){
        if(typeof paperid=='undefined'){
            alert('未获取到试卷标识!');
            return;
        }
        $.ajax({
            url:"paperques?m=doImportPaperQues",
            type:"post",
            data:{
                courseid:courseid,
                paperid:paperid,
                currentcourseid:currentcourseid,
                currentpaperid:currentpaperid
            },
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },success:function(rmsg){
                if(rmsg.type=="error"){
                    alert(rmsg.msg);
                }else{
//                    loadPaperQues();
                    loadDiv(3);
                }
            }
        });
    }

</script>
</head>
<body>
<p class="float_title"><a href="javascript:;" class="ico93" onclick="loadDiv(3)" title="返回"></a><span id="dv_model_mdname">导入试卷</span></p>
<div class="jxxt_float_w920 font-black">
    <p class="t_r public_input">
        <input id="txt_search_paper" name="txt_search_paper" type="text" class="w240" placeholder="专题名称" />
        <a  href="javascript:pageGo('pListPaper');pageGo('pCloudPaper');" class="an_search" title="查询"></a>
        图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题</p>
    <p><strong>标准试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard_paper">

    </ul>
        <form id="pListForm_CloudPaper" name="pListForm_CloudPaper" style="">
            <div class="nextpage" id="pListaddress_Cloudpaper" align="center"></div>
        </form>

        <form id="pListForm_RCloudPaper" name="pListForm_RCloudPaper" style="">
        <div class="nextpage" id="pListaddress_RCloudpaper" align="center"></div>
        </form>


    <p><strong>自建试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_native_paper">

    </ul>
        <form id="pListForm_Paper" name="pListForm_Paper" style="">
            <div class="nextpage" id="pListaddress_paper" align="center"></div>
        </form>

    <form id="pListForm_RPaper" name="pListForm_RPaper" style="">
        <div class="nextpage" id="pListaddress_Rpaper" align="center"></div>
    </form>
</div>

</body>
</html>