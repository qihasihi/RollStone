<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
            shtml+='<p>';
            if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0){
                $.each(itm.questionOptionList,function(ix,im){
                    var type=itm.questiontype==3?"radio":"checkbox";
                    shtml+='<input disabled type="'+type+'"/>';
                    shtml+=im.optiontype+".&nbsp;";
                    shtml+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                    if(im.isright==1)
                        shtml+='<span class="ico12"></span>';
                    shtml+='<br>';
                });
            }
            shtml+='</p>';
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
               window.returnValue='ok';
               window.close();
            }
        }
    });
 }
</script>
</head>
<body>
<div>
    <a href="#">添加试题----导入试题</a>
</div>
<div class="zhuanti" >
    <input type="text" id="txt_search" onfocus="if(this.value=='输入专题名称'){this.value=''}" onblur="if(this.value==''){this.value='输入专题名称'}"/><a href="javascript:pageGo('pList');">搜索</a>

</div>
<div class="content2" >
    <div class="subpage_lm">
        <table  id="dv_data"></table>
        <input type="button" onclick="addImportQues()" value="确定" />
    </div>


    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>



<%@include file="/util/foot.jsp" %>

</body>
</html>
