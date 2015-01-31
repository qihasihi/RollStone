<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
                case '4':url='paper?toQueryTaskPaperList';break;
                case '5':url='question?m=questionAjaxList';break;
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
            else if(tasktype=="4")
                param.type=4;
            else if(tasktype=="5")
                param.type=5;
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
                            htm+='<td><span class="bg">'+itm.questiontypename+'</span>';
                            if(typeof(itm.showExamYearMsg)!="undefined"&&itm.showExamYearMsg!=null){
                                htm+='<span>('+itm.showExamYearMsg+')</span>';
                            }
                            htm+='<p>'+quesname+'</p>'
                            htm+='<p>';
                            if(itm.questionOptionList!=null&&itm.questionOptionList.length>0&&itm.questiontype!=1){
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
                        case '4':{
                            htm+='<tr><td><input type="radio" name="rdo_data" id="rdo_data" value="'+itm.paperid+'"></td>';
                            htm+='<td><p><a href="#" >'+itm.papername+'</a></p>';
                            //htm+='<p>'+itm.topiccontent+'</p>';
                            //<p class="jxxt_zhuanti_zy_add_text">北京四中_李静</p>
                            htm+='</td></tr>';
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
            var type=tasktype=='1'?"资源":tasktype=='2'?"论题":tasktype=='3'?"试题":tasktype=='4'?"试卷":"";
            if(dataObj.length<1){
                alert('请选择'+type+'!');
                return;
            }
            var questype = $("input[name='rdo_ques_type']:checked").val();
            var returnValue=dataObj.val();
            $.ajax({
                url: "question?m=questionAjaxList",
                type: "post",
                data: {questype: questype, courseid: courseid, questionid: returnValue},
                dataType: 'json',
                cache: false,
                error: function () {
                    alert('系统未响应，请稍候重试!');
                }, success: function (rmsg) {
                    if (rmsg.type == "error") {
                        alert(rmsg.msg);
                    } else {
                        var htm = '';
                        $("#ques_name").html('');
                        $("#ques_answer").html('');
                        $("#td_option").hide();
                        $("#td_option").html('');
                        if (rmsg.objList.length && typeof returnValue != 'undeinfed' && returnValue.toString().length > 0) {
                            $("#hd_questionid").val(rmsg.objList[0].questionid);
                            $("#sp_ques_id").html(rmsg.objList[0].questionid);
                            load_ques_detial(returnValue, questype);
                        }
                        $("#tr_ques_obj").show();
                        $.fancybox.close();
                    }
                }
            });

        }

    </script>


</head>
<body>
<div class="public_float public_float960">
    <p class="float_title"><strong>试题&mdash;&mdash;选择试题</strong></p>
    <div class="subpage_lm">
        <ul>
            <li class="crumb"><a href="javascript:;">选择试题</a></li>
            <li><a href="javascript:loadDialogPage(3)">新建试题</a></li>
        </ul>
    </div>
    <p class="public_input t_r w940 p_b_10">
        <!--  <input name="textfield2" type="text" class="w240" placeholder="专题名称" />
          <a href="1" class="an_search" title="查询"></a>-->
    </p>
    <div class="jxxt_float_h460">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
            <col class="w30"/>
            <col class="w880"/>
            <tbody id="initItemList">

            </tbody>
        </table>
    </div>
    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
    <p class="t_c p_tb_10"><a href="javascript:sub_data()"  class="an_public1">选&nbsp;择</a>
        <!--&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.close();"  class="an_small">取&nbsp;消</a>-->
    </p>


</div>
</body>
</html>
