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
var pList,pBankList;
var total;
$(function(){
    pList = new PageControl( {
        post_url : 'paper?m=getImportPaperList',
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
    var coursename=$("#txt_search");
    if(coursename.val().Trim().length>0&&coursename.val()!='输入专题名称')
        pageGo('pList');
});



function getInvestReturnMethod(rps){
    var html='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            html+='<p><a  href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">'+itm.papername+'</a>&nbsp;<a href="javascript:importPaperQues('+itm.courseid+','+itm.paperid+')">添加</a></p>';
        });
    }
    $("#dv_data").html(html);



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
    var param={courseid:courseid,materialid:materialid,gradeid:gradeid,subjectid:subjectid};
    var coursename=$("#txt_search");
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
                    alert(rmsg.msg)
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
    <a href="#">添加试题----导入试卷</a>
</div>
<div class="zhuanti">
    <input type="text" id="txt_search" onfocus="if(this.value=='输入专题名称'){this.value=''}" onblur="if(this.value==''){this.value='输入专题名称'}"/><a href="javascript:pageGo('pList');">搜索</a>
</div>
<div class="content2">
    <div class="subpage_lm" id="dv_data">
         <c:if test="${!empty coursePaperList}">
             <c:forEach items="${coursePaperList}" var="c">
                 <p>
                     <a target="_top" href="paper?toPreviewPaper&courseid=${c.courseid}&paperid=${c.paperid}">${c.papername}</a>
                     <a href="javascript:importPaperQues('${c.courseid}','${c.paperid}')">添加</a>
                     主观题：${c.objectivenum}&nbsp;客观题：${c.subjectivenum}
                 </p>
             </c:forEach>
         </c:if>
    </div>


    <form id="pListForm" name="pListForm" style="display: none;">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>



<%@include file="/util/foot.jsp" %>

</body>
</html>
