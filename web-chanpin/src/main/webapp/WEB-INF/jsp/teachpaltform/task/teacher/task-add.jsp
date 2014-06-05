<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript"
            src="<%=basePath %>js/teachpaltform/tptask.js"></script>
    <script type="text/javascript">
        var courseid="${param.courseid}";
        var questype="${param.questype}";
        $(function(){
            $("#task_type").val(${param.tasktype});
            if(questype.length<1)
                questype=3
            //任务类型
            changeTaskType("${param.tasktype}","${param.taskvalueid}");

        });
        function changeTaskType(type,taskvalueid){
            $("#tr_ques_obj").hide();
            var tasktype=$("#task_type").val();
            if(typeof type!='undefined' && type.length>0)
                tasktype=type;
            if(tasktype=="1"){//资源
                queryResource(courseid,'tr_task_obj',taskvalueid)
            }else if(tasktype=="2"){//论题
                queryInteraction(courseid,'tr_task_obj',taskvalueid);
            }else if(tasktype=="3"){ //问题类型
                queryQuestionType('tr_task_obj',questype,true,taskvalueid);
            }
            //加载完成标准
            initTaskCriteria(tasktype);
        }

        function selectClassObj(obj,classid){
            if(typeof(classid)!='undefined'){
                $("#p_group_"+classid+" input").each(function(){$(this).attr("checked",obj.checked)});
            }
           // qryTopicByCls();
        }
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w120"/>
        <col class="w700"/>
        <tr>
            <th><span class="ico06"></span>任务类型：</th>
            <td>  <select name="select" id="task_type" onchange="changeTaskType()">
                <option value="">==请选择==</option>
                <c:if test="${!empty tasktypeList}">
                    <c:forEach var="tp" items="${tasktypeList}">
                        <option value="${tp.dictionaryvalue }">${tp.dictionaryname }</option>
                    </c:forEach>
                </c:if>
            </select >
            </td>
        </tr>

        <tr id="tr_task_obj">

        </tr>

        <tr id="tr_ques_obj" style="display: none">
            <th><span class="ico06"></span>选择试题：</th>
            <td class="font-black">
                <a href="javascript:showTaskElement(3)"  class="font-darkblue">>>&nbsp;选择已有试题</a>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showDialogPage(3)" class="font-darkblue">>>&nbsp;添加试题</a>
                <div class="jxxt_zhuanti_add_shiti"  id="tb_ques" style="display:none;">
                    <table border="0" cellspacing="0" cellpadding="0" class="font-black">
                        <col class="w70"/>
                        <col class="w600"/>
                        <tr>
                            <td valign="top"><strong>试题编号：</strong></td>
                            <td>
                                    <span id="sp_ques_id"></span>
                                    <input type="hidden" id="hd_questionid"/>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top"><strong id="s_questype"></strong></td><td ><span id="ques_name"></span><ul id="td_option"></ul></td>
                        </tr>
                        <tr><td valign="top" id="th_analysis"><strong>答案解析：</strong></td><td> <div id="ques_answer"></div></td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>

        <tr>
            <th><span class="ico06"></span>选对象-时间:</th>
            <td>
                <c:if test="${!empty courseclassList}">
                    <c:forEach var="cc" items="${courseclassList}" varStatus="idx">

                                <p class="f_right w410">
                                    <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" class="public_input w140" />~
                                    <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly" id="e_time_${cc.classid}" type="text"class="public_input w140" />
                                </p>
                                <p class="font-black"><input name="ck_cls" type="checkbox"  onclick="selectClassObj(this,'${cc.classid }')" value="${cc.classid }" id="ckb_${cc.classid}" /> ${cc.classgrade }${cc.classname }</p>


                           <!-- <c:if test="${idx.index!=0}">
                                <p class="f_right w410">
                                    <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" class="w140" />~
                                    <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"  id="e_time_${cc.classid}" type="text" class="w140" />
                                </p>
                                <p class="font-black"><input name="ck_cls" type="checkbox"  onclick="selectClassObj(this,'${cc.classid }')" value="${cc.classid }" id="ckb_${cc.classid}" /> ${cc.classgrade }${cc.classname }</p>
                            </c:if> -->

                        <ul class="public_list3" id="p_group_${cc.classid }">
                        </ul>
                    </c:forEach>
                    <p class="font-darkblue"><a href="group?m=toGroupManager&classid=${courseclassList[0].classid}&classtype=${courseclassList[0].classtype}"  target="_blank">&gt;&gt;&nbsp;小组管理</a></p></td>
                </c:if>
        </tr>
        <tr>
            <th><span class="ico06"></span>完成标准：</th>
            <td class="font-black" id="td_criteria">
            </td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;任务描述：</th>
            <td>
                <textarea id="task_remark" name="task_remark" class="ckeditor" class="h90 w600"></textarea>
            </td>
        </tr>
        <tr>
            <th></th>
            <td>
                <a href="javascript:void(0);" onclick="doSubManageTask(undefined);" class="an_small">提&nbsp;交</a>
                <a href="javascript:history.go(-1);"  class="an_small">取&nbsp;消</a>
            </td>
        </tr>
    </table>
    <br>
</div>
<%@include file="/util/foot.jsp" %>
<script type="text/javascript">
    <c:if test="${!empty groupList}">
    <c:forEach var="c" items="${groupList}">
    var trobj=$("#p_group_${c.classid}");
    if(trobj.length>0){
        var flag=$("#ckb_${c.classid}").attr("checked");
        var html='<li><input id="ck_group_${c.groupid}"  type="checkbox" name="ck_group" value="${c.groupid}" />'
                +'<label for="ck_group_${c.groupid}"> ${c.groupname}</label></li>';
        if(flag){
            html='<li><input id="ck_group_${c.groupid}"  type="checkbox" name="ck_group" value="${c.groupid}" />'
                    +'<label for="ck_group_${c.groupid}"> ${c.groupname}</label></li>';
        }
        trobj.append(html);
    }
    </c:forEach>
    </c:if>


    $("input[name='ck_cls']").each(function(idx,itm){
        $(itm).bind("click",function(){
            $('ul[id="p_group_'+itm.value+'"] input[name="ck_group"]').attr("checked",false);
        });
    });

    $("input[name='ck_group']").each(function(idx,itm){
        $(itm).bind("click",function(){
            var p=$(itm).parent().parent("ul");
            var tmpId=p.attr("id").substring(p.attr("id").lastIndexOf('_')+1);
            $('#ckb_'+tmpId+'').attr("checked",false);
        });
    });


</script>
</body>
</html>
