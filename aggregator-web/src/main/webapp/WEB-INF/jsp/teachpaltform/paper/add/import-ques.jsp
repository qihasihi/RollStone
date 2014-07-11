
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>


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
var pList;
$(function(){
    pList = new PageControl( {
        post_url : 'paperques?m=ajaxImportQuesList',
        page_id : 'page1',
        page_control_name : "pList",
        post_form : document.pListForm,
        gender_address_id : 'pListaddress',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 15, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
    pageGo('pList');


});




function getInvestReturnMethod(rps){
    var shtml='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            shtml+='<tr><td>';
            if(typeof (itm.paperquesflag)!='undefined'&&itm.paperquesflag>0)
                shtml+='<input type="checkbox"  name="question" disabled checked/>';
            else
                shtml+='<input type="checkbox" name="ck_quesid"  value="'+itm.questionid+'"/>';
            var content=replaceAll(itm.content.toLowerCase(),'<span name="fillbank"></span>','______');
            shtml+='</td>';
            shtml+='<td>';
            shtml+='<p>'+content+'</p>';
            if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0){
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
            shtml+='</td>';
            shtml+='</tr>';
        });
    }
    $("#dv_data").html(shtml);


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
    var objArray=$("input[name='ck_quesid']:checked");
    if(objArray.length<1){
        alert("请选择试题!");
        return;
    }
    var quesArray=new Array();
    $.each(objArray,function(idx,itm){
       quesArray.push($(itm).val());
    });

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
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
               alert(rmsg.msg);
                if (window.opener != undefined) {
                    //for chrome
                    window.opener.returnValue ='ok';
                }
                else {
                    window.returnValue ='ok';
                }
                window.close();
            }
        }
    });
 }
</script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加试卷—导入试题</strong></div>
<div class="content1">
    <div class=" t_r p_b_10 public_input">
        <input  id="txt_search" name="textfield2" type="text" class="w300" placeholder="专题名称" />
        <a href="javascript:pageGo('pList');" class="an_search" title="查询"></a>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w30"/>
        <col class="w880"/>
        <tbody id="dv_data">

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
    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>

    <p class="t_c p_tb_10"><a href="javascript:addImportQues()"  class="an_small">添&nbsp;加</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.close();"  class="an_small">取&nbsp;消</a></p>
</div>

<%@include file="/util/foot.jsp" %>

</body>
</html>
