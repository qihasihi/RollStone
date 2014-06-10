<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var pList;
        var tasktype="${param.tasktype}";
        var questiontype="${param.questype}";
        $(function () {
            var url='';
            switch (tasktype){
                case '1':url='tpres?toQueryResourceList';break;
                case '2':url='task?toQryTopicList';break;
                case '3':url='question?m=questionAjaxList';break;
            }

            pList = new PageControl( {
                post_url : url,
                page_id : 'page1',
                page_control_name : "pList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
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
            if(courseid.length<1){
                alert('异常错误，系统未获取到专题标识!');
                return;
            }
            var param={courseid:courseid};
            if(tasktype=="3")
                param.questype=questiontype;
            param.taskflag=1;
            pObj.setPostParams(param);
        }

        function getInvestReturnMethod(rps){
            var htm='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    switch (tasktype){
                        case '1':{
                            var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
                            var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
                            var type=itm.resourcetype==1?"学习资源":"教学参考";

                            htm+='<tr>';
                            htm+='<td><input type="radio" name="rdo_data" id="rdo_data" value="'+itm.resid+'" />';
                            htm+='<span class="'+itm.suffixtype+'"></span></td>';
                            htm+='<td><p><a href="javascript:void(0);" target="_blank">'+itm.resname+'</a></p>';
                            htm+='<p>'+introduce+'</p>';
                            htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
                            htm+='</tr>';
                            break;
                        }
                        case '2':{
                            htm+='<tr><td><input type="radio" name="rdo_data" id="rdo_data" value="'+itm.topicid+'"></td>';
                            htm+='<td><p><a href="#" >'+itm.topictitle+'</a></p>';
                            htm+='<p>'+itm.topiccontent+'</p>';
                            //<p class="jxxt_zhuanti_zy_add_text">北京四中_李静</p>
                            htm+='</td></tr>';
                            break;
                        }
                        case '3':{
                            var quesname=replaceAll(replaceAll(replaceAll(itm.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                            quesname=replaceAll(quesname.toLowerCase(),'<span name="fillbank"></span>','_______');
                            htm+='<tr>';
                            htm+='<td><input type="radio" name="rdo_data" id="rdo_data" value="'+itm.questionid+'"></td>';
                            htm+='<td><p>'+quesname+'</p>';
                            htm+='<p>';
                            if(itm.questionOptionList!=null&&itm.questionOptionList.length>0){
                                $.each(itm.questionOptionList,function(idx,im){
                                    var type=itm.questiontype==3?"radio":"checkbox";
                                    htm+='<input disabled type="'+type+'"/>';
                                    htm+=im.optiontype+".&nbsp;";
                                    htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                    if(im.isright==1)
                                        htm+='<span class="ico12"></span>';
                                    htm+='<br>';
                                });
                            }
                            htm+='</p></td>';
                            htm+='</tr>';
                            break;
                        }
                    }
                });
            }
            $("#initItemList").html(htm);

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

        function sub_data(){
            var dataObj=$('input[name="rdo_data"]:checked');
            var type=tasktype=='1'?"资源":tasktype=='2'?"论题":"试题";
            if(dataObj.length<1){
                alert('请选择'+type+'!');
                return;
            }
            if(tasktype=='1'){
                $.ajax({
                    url: 'tpres?m=doUpdResTypeById',
                    type: 'post',
                    data: {courseid: courseid,resid:dataObj.val()},
                    dataType: 'json',
                    cache: false,
                    error: function () {
                        alert('网络异常!')
                    },
                    success: function (rps) {
                        if (rps.type == 'error') {
                            alert(rps.msg);
                        } else {
                        }
                    }
                });
            }

            window.returnValue=dataObj.val();
            window.close();
        }
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务&mdash;&mdash;选择${!empty param.tasktype and param.tasktype eq '1'?"资源":param.tasktype eq '2'?"论题":"试题"}</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w50"/>
        <col class="w880"/>
        <tbody id="initItemList">

        </tbody>
        <!--试题-->
       <!-- <tr>
            <td><input type="radio" name="radio" id="radio3" value="radio"></td>
            <td><p>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批判《药》等小说有一个共同的主题即使对中国的批判</p>
                <p>
                    <input type="radio" name="radio" id="radio2" value="radio">
                    A. 民族性<br>
                    <input type="radio" name="radio" id="radio" value="radio">
                    B. 国民性<br>
                    <input type="radio" name="radio" id="radio4" value="radio">
                    C. 传统性<br>
                    <input type="radio" name="radio" id="radio5" value="radio">
                    D. 文化性</p></td>
        </tr> -->
        <!--资源
         <tr>
      <td><input type="radio" name="radio" id="radio3" value="radio">
        <span class="ico_doc1"></span></td>
      <td><p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
        <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
        <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件</p></td>
    </tr>
        -->
        <!--论题
         <tr>

    </tr>
        -->

    </table>
    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
    <p class="t_c p_tb_10"><a href="javascript:sub_data()"  class="an_small">选&nbsp;择</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.close();"  class="an_small">取&nbsp;消</a></p>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>