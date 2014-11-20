
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%--<%@include file="/util/common-jsp/common-jxpt.jsp"%>--%>
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
var pList1;
$(function(){
    pList1 = new PageControl( {
        post_url : 'paperques?m=ajaxImportQuesList',
        page_id : 'page1',
        page_control_name : "pList1",
        post_form : document.pList1Form_ques,
        gender_address_id : 'pList1address_ques',
        http_free_operate_handler : preeDoPageSub_ques, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod_ques, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 5, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
    pageGo('pList1');


});

function dvCheckImport(val,ck){
    if(typeof(val)!="undefined"&&val!=null&&typeof(ck)!="undefined"&&ck!=null){
        var len=$("#dv_checkval input[value='"+val+"']").length;
        if(ck){
            if(len<1){
                $("#dv_checkval").append("<input type='checkbox' value='"+val+"'/>");
            }
        }else{
            if(len>0){
                $("#dv_checkval input[value='"+val+"']").remove();
            }
        }
    }
}


function getInvestReturnMethod_ques(rps){
    var shtml='';
    $("#dv_data_ques").html('');
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            var ckQLength=$("#dv_checkval input[value='"+itm.questionid+"']").length;
            shtml+='<tr><td>';
            if(typeof (itm.paperquesflag)!='undefined'&&itm.paperquesflag>0){

                var s1='<input type="checkbox"  name="question" disabled checked/>';
                dvCheckImport(itm.questionid,true);
                shtml+=s1;
            }else{
                shtml+='<input ' +(ckQLength>0?'checked':'')+
                        ' type="checkbox" name="ck_quesid" onclick="dvCheckImport('+itm.questionid+',this.checked)" value="'+itm.questionid+'"/>';
            }
            shtml+='</td><td>';

            var content=replaceAll(itm.content.toLowerCase(),'<span name="fillbank"></span>','______');
            if(itm.extension=='4'){
                shtml+='<div  class="p_t_10" id="sp_mp3_'+itm.questionid+'" ></div>'
                shtml+='<script type="text/javascript">';
                shtml+='playSound(\'play\',\'<%=basePath+UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/'+itm.questionid+'/001.mp3\',270,22,\'sp_mp3_'+itm.questionid+'\',false)';
                shtml+='<\/script>';
            }
            if(itm.extension!='4'){
                shtml+='<p>';
                shtml+='<span class="bg">'+itm.questiontypename+'</span>';
                shtml+=content;
                shtml+='</p>';
            }else{
                shtml+='<span class="bg">'+itm.questiontypename+'</span>';
                shtml+=content;
            }


            if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0&&itm.questiontype!=1){
                shtml+='<table border="0" cellpadding="0" cellspacing="0" class="tab">';
                shtml+='<col class="w30"/>';
                shtml+='<col class="w850"/>';
                $.each(itm.questionOptionList,function(ix,im){
                    shtml+='<tr>';
                    var type=itm.questiontype==3?"radio":"checkbox";
                    shtml+='<th><input  type="'+type+'"/></th>';
                    shtml+='<td>'+im.optiontype+".&nbsp;";
                    shtml+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                    if(im.isright==1)
                        shtml+='<span class="ico12"></span>';
                    shtml+='</td>';
                    shtml+='</tr>';
                });
                shtml+='</table>';
            }

            if(typeof itm.questionTeam!='undefined'&&itm.questionTeam.length>0&&itm.extension!='5'){
                $.each(itm.questionTeam,function(ix,m){
                    shtml+='<tr>';
                    shtml+='<td>&nbsp;</td>';
                    shtml+='<td>'+(ix+1)+'.'+m.content;
                    if(typeof m.questionOptionList!='undefined'&&m.questionOptionList.length>0){
                        shtml+='<table border="0" cellpadding="0" cellspacing="0" class="tab">';
                        shtml+='<col class="w30"/>';
                        shtml+='<col class="w850"/>';
                        $.each(m.questionOptionList,function(ix,im){
                            shtml+='<tr>';
                            var type=im.questiontype==3||im.questiontype==7?"radio":"checkbox";
                            shtml+='<th><input  type="'+type+'"/></th>';
                            shtml+='<td>'+im.optiontype+".&nbsp;";
                            shtml+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                            if(im.isright==1)
                                shtml+='<span class="ico12"></span>';
                            shtml+='</td>';
                            shtml+='</tr>';
                        });
                        shtml+='</table>';
                    }
                    shtml+='</td>';
                    shtml+='</tr>';
                });
            }

        });
    }
    $("#dv_data_ques").html(shtml);


    if(rps.objList.length>0){
        pList1.setPagetotal(rps.presult.pageTotal);
        pList1.setRectotal(rps.presult.recTotal);
        pList1.setPageSize(rps.presult.pageSize);
        pList1.setPageNo(rps.presult.pageNo);
    }else
    {
        pList1.setPagetotal(0);
        pList1.setRectotal(0);
        pList1.setPageNo(1);
    }
    pList1.Refresh();
}


function preeDoPageSub_ques(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }

    var param={courseid:courseid,materialid:materialid,gradeid:gradeid,subjectid:subjectid,paperid:paperid};
    var coursename=$("#txt_search");
    if(coursename.val().Trim().length>0&&coursename.val()!='输入专题名称'){
        param.coursename=coursename.val();
    }
    pObj.setPostParams(param);
}

/**
 * 添加导入试题
 */
 function addImportQues(){
    var objArray=$("#dv_checkval input[type='checkbox']");
    if(objArray.length<1){
        alert("请选择试题!");
        return;
    }
    var quesArray=new Array();
    $.each(objArray,function(idx,itm){
       quesArray.push($(itm).val());
    });
    resetBtnAttr("btn_addQues","an_small","an_gray_small","",2);
    $.ajax({
        url:"paperques?m=doAddImportQues",
        type:"post",
        data:{
            courseid:courseid,
            paperid:paperid,
            quesid:quesArray.join(",")
        },
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
            resetBtnAttr("btn_addQues","an_small","an_gray_small","addImportQues()",1);
        },success:function(rmsg){
            resetBtnAttr("btn_addQues","an_small","an_gray_small","addImportQues()",1);
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
<p class="float_title"><a href="javascript:;" class="ico93" onclick="loadDiv(3)" title="返回"></a><span id="dv_model_mdname">导入试题</span></p>
<p class="public_input t_r w940 p_t_10 p_b_20">
    <input name="txt_search" id="txt_search" type="text" class="w240" placeholder="专题名称" />
    <a href="javascript:pageGo('pList1');" class="an_search" title="查询"></a></p>
<div class="jxxt_float_h480">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w30"/>
        <col class="w880"/>
        <tbody id="dv_data_ques">

        </tbody>
       <!-- <tr>
            <td><input type="checkbox" name="checkbox2" id="checkbox2"></td>
            <td><p>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批判《药》等小说有一个共同的主题即使对中国的批判</p>
                <table border="0" cellpadding="0" cellspacing="0" class="tab">
                    <col class="w30"/>
                    <col class="w850"/>
                    <tr>
                        <th><input type="radio" name="radio" id="radio9" value="radio"></th>
                        <td>A. 民族性我国公安交通部门规定，从1993年7月1日起，在各种小轿车前排乘坐的人（包括司机）必须系好安全带。主要是防止汽车突然停止的时候，乘客会向前倾倒而发生事故。则汽车刹车时，乘客向前倾倒的原因是由于</td>
                    </tr>
                    <tr>
                        <th><input type="radio" name="radio" id="radio10" value="radio"></th>
                        <td>B. 国民性<span class="ico12"></span></td>
                    </tr>
                    <tr>
                        <th><input type="radio" name="radio" id="radio11" value="radio"></th>
                        <td> C. 传统性</td>
                    </tr>
                    <tr>
                        <th><input type="radio" name="radio" id="radio12" value="radio"></th>
                        <td>D. 文化性</td>
                    </tr>
                </table>
            </td>
        </tr>-->
    </table>
    </div>
    <form id="pList1Form_ques" name="pList1Form_ques">
        <p class="Mt20" id="pList1address_ques" align="center"></p>
    </form>
    <p class="t_c p_tb_10"><a href="javascript:addImportQues()"  id="btn_addQues" class="an_public1">添&nbsp;加</a></p>
    <div id="dv_checkval" style="display:none">

    </div>
</body>
</html>
