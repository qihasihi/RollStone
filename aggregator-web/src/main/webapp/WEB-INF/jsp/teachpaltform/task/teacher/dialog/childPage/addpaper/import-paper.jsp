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
var pListPaper,pBankList;
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
        page_size : 9999, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
    var coursename=$("#txt_search_paper");
    if(coursename.val().Trim().length>0&&coursename.val()!='输入专题名称')
        pageGo('pListPaper');
});



function getInvestReturnMethod(rps){
    var html='',shtml='';
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
                html+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico02" title="导入"></span></b></a>';
                html+='</p>';
                html+='</li>';
            }else{
                shtml+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
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
                shtml+='<a href="javascript:importPaperQues('+courseid+','+itm.paperid+')"><b><span class="ico02" title="导入"></span></b></a>';
                shtml+='</p>';
                shtml+='</li>';
            }
        });
    }else{
        html='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
        shtml='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
    }
    $("#ul_standard_paper").html(html);
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

function preeDoPageSub(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid,materialid:materialid,gradeid:gradeid,subjectid:subjectid};
    var coursename=$("#txt_search_paper");
    if(coursename.val().Trim().length<1||coursename.val()=='输入专题名称'){
        return;
    }
    param.coursename=coursename.val();
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
                    loadPaperQues();
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
        <input id="txt_search_paper" name="txt_search_paper" type="text" class="w240" placeholder="资源名称/专题名称" />
        <a  href="javascript:pageGo('pListPaper');" class="an_search" title="查询"></a>
        图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题</p>
    <div style="width:920px;height:610px;overflow-x:hidden;overflow-y:auto;">
    <p><strong>标准试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard_paper">
       <!-- <li><a href="1" target="_blank">
            <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
            <p class="two"><span class="bg1" style="width:100%">8</span></p></a>
            <p class="pic"><a href="1"><b><span class="ico02" title="导入"></span></b></a></p>
        </li> -->
        <c:if test="${fn:length(standardList)<1}">
            <li><img src="images/pic06_140722.png" width="215" height="160"></li>
        </c:if>
        <c:if test="${!empty standardList}">
            <c:forEach items="${standardList}" var="c">
                <c:if test="${c.paperid>0}">
                    <li>
                        <a href="paper?toPreviewPaper&courseid=${c.courseid}&paperid=${c.paperid}">
                            <p class="one">${c.papername}</p>
                            <p class="two">
                                <c:if test="${c.objectivenum>0 and c.subjectivenum>0}">
                                    <span class="bg1" style="width:50%">${c.objectivenum}</span><span class="bg2" style="width:50%">${c.subjectivenum}</span>
                                </c:if>

                                <c:if test="${c.objectivenum>0 and c.subjectivenum<1}">
                                    <span class="bg1" style="width:100%">${c.objectivenum}</span>
                                </c:if>
                                <c:if test="${c.subjectivenum>0 and c.objectivenum<1}">
                                    <span class="bg2" style="width:100%">${c.subjectivenum}</span>
                                </c:if>
                            </p>
                        </a>
                        <p class="pic">
                            <a href="javascript:importPaperQues('${c.courseid}','${c.paperid}')"><b><span class="ico02" title="导入"></span></b></a>
                        </p>
                    </li>
                </c:if>
            </c:forEach>
        </c:if>


    </ul>
    <p><strong>自建试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_native_paper">
        <c:if test="${fn:length(nativeList)<1}">
            <li><img src="images/pic06_140722.png" width="215" height="160"></li>
        </c:if>
        <c:if test="${fn:length(nativeList)>0}">
            <c:forEach items="${nativeList}" var="c">
                <c:if test="${c.paperid<0}">
                    <li>
                        <a href="paper?toPreviewPaper&courseid=${c.courseid}&paperid=${c.paperid}">
                            <p class="one">${c.papername}</p>
                            <p class="two">
                                <c:if test="${c.objectivenum>0 and c.subjectivenum>0}">
                                    <span class="bg1" style="width:50%">${c.objectivenum}</span><span class="bg2" style="width:50%">${c.subjectivenum}</span>
                                </c:if>

                                <c:if test="${c.objectivenum>0 and c.subjectivenum<1}">
                                    <span class="bg1" style="width:100%">${c.objectivenum}</span>
                                </c:if>
                                <c:if test="${c.subjectivenum>0 and c.objectivenum<1}">
                                    <span class="bg2" style="width:100%">${c.subjectivenum}</span>
                                </c:if>
                                <c:if test="${!empty c.quesnum}">
                                    <span class="bg1" style="width:100%">${c.quesnum}</span>
                                </c:if>
                            </p>
                        </a>
                        <p class="pic">
                            <a href="javascript:importPaperQues('${c.courseid}','${c.paperid}')"><b><span class="ico02" title="导入"></span></b></a>
                        </p>
                    </li>
                </c:if>
            </c:forEach>
        </c:if>

    </ul>
    </div>

</div>
<form id="pListForm_Paper" name="pListForm_Paper" style="display: none;">
    <p class="Mt20" id="pListaddress_paper" align="center"></p>
</form>
</body>
</html>
