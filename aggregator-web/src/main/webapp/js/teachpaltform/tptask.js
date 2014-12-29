$(function () {

    $("label").filter(function () {
        return $(this).attr('for').indexOf('ck_group_') != -1
    }).bind("mouseover", function () {
        var groupid = $(this).prev('input').val();
        genderShowdiv(groupid);
    });
    $("label").filter(function () {
        return $(this).attr('for').indexOf('ck_group_') != -1
    }).bind("mouseout", function () {
        var divObj = $("div").filter(function () {
            return this.id.indexOf('div_tmp_show') != -1
        });
        if (divObj.length > 0)
            $(divObj).remove();
    });
    $("body").bind("mouseover", function () {
        var divObj = $("div").filter(function () {
            return this.id.indexOf('div_tmp_show') != -1
        });
        if (divObj.length > 0)
            $(divObj).remove();
    })
})

/**
 * 任务--选择元素
 * @param type
 */
var returnValue;
function showTaskElement(type) {
    var url = 'task?m=toTaskElementDetial&tasktype=' + type + '&courseid=' + courseid;
    if (type == 3) {
        var questype = $("input[name='rdo_ques_type']:checked").val();
        url += '&questype=' + questype;
    } else if (type == 4) {//成卷测试
        url = 'paper?m=toSelTaskPaper&tasktype=' + type + '&courseid=' + courseid;
    } else if (type == 6){//微视频
        url = 'tpres?m=queryMicViewList&tasktype=' + type + '&courseid=' + courseid;
    }

    var paramObj={width:1000,height:700}

    var left =findDimensions().width/2-parseFloat($("#tr_ques_obj").css("width"))-100;
    var top =100;//findDimensions().height/2-parseFloat($("#p_operate").css("height"))/2-120;
    var param = 'dialogWidth:'+paramObj.width+'px;dialogHeight:'+paramObj.height+'px;dialogLeft:'+left+'px;dialogTop:'+top+'px;status:no;location:no';
    var returnValue = window.showModalDialog(url, "", param);
    /*  if(window.showModalDialog)
     returnValue = window.showModalDialog(url, "", param);
     else{
     var win=window.open(url, "", param);
     if(!win.closed)return;
     }
     */

    if (returnValue == undefined) {
        returnValue = window.returnValue;
    }
    if (returnValue == null || returnValue.toString().length < 1) {
        alert("操作取消!");
        return;
    }
    if (type == 1) {//资源
        queryResource(courseid, 'tr_task_obj', returnValue)
    } else if (type == 2) {//论题
        queryInteraction(courseid, 'tr_task_obj', returnValue);
    } else if (type == 3) { //问题类型
        var questype = $("input[name='rdo_ques_type']:checked").val();
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
                }
            }
        });
    } else if (type == 4) {
        queryPaper(courseid, 'tr_task_obj', 4, returnValue);
    } else if (type == 6) {
        queryMicView(courseid, 'tr_task_obj', 6, returnValue);
    }
}

function showTaskElementTopic(type) {
    var url = 'task?m=toTaskElementDetial&tasktype=' + type + '&courseid=' + courseid;
    if (type == 3) {
        var questype = $("input[name='rdo_ques_type']:checked").val();
        url += '&questype=' + questype;
    } else if (type == 4) {//成卷测试
        url = 'paper?m=toSelTaskPaper&tasktype=' + type + '&courseid=' + courseid;
    } else if (type == 6){//微视频
        url = 'tpres?m=queryMicViewList&tasktype=' + type + '&courseid=' + courseid;
    }

    if (returnValue == undefined) {
        returnValue = window.returnValue;
    }
    if (returnValue == null || returnValue.toString().length < 1) {
        alert("操作取消!");
        return;
    }
    if (type == 1) {//资源
        queryResource(courseid, 'tr_task_obj', returnValue)
    } else if (type == 2) {//论题
        queryInteraction(courseid, 'tr_task_obj', returnValue);
    } else if (type == 3) { //问题类型
        var questype = $("input[name='rdo_ques_type']:checked").val();
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
                }
            }
        });
    } else if (type == 4) {
        queryPaper(courseid, 'tr_task_obj', 4, returnValue);
    } else if (type == 6) {
        queryMicView(courseid, 'tr_task_obj', 6, returnValue);
    }
}






function loadTaskElement(type) {
    var url = 'task?m=toTaskElementDetial&tasktype=' + type + '&courseid=' + courseid;
    if (type == 3) {
        var questype = $("input[name='rdo_ques_type']:checked").val();
        url += '&questype=' + questype;
    } else if (type == 4) {//成卷测试
        url = 'paper?m=toSelTaskPaper&tasktype=' + type + '&courseid=' + courseid;
    } else if (type == 6){//微视频
        //url = 'tpres?m=queryMicViewList&tasktype=' + type + '&courseid=' + courseid;
        url = 'task?m=toSelMicForTask&tasktype=' + type + '&courseid=' + courseid;
    }else if(type==2){
        url='task?m=toSelTopicForTask&courseid='+courseid+'&tasktype='+type;
    }
    $("#dv_content").load(url,function(){
        $(".quesNumli").remove();
        $("#dv_content").show();
        $("#a_click").click();
    });
}

function test(type){
    if (returnValue == null || returnValue.toString().length < 1) {
        alert("操作取消!");
        return;
    }
    if (type == 1) {//资源
        queryResource(courseid, 'tr_task_obj', returnValue)
    } else if (type == 2) {//论题
        queryInteraction(courseid, 'tr_task_obj', returnValue);
    } else if (type == 3) { //问题类型
        var questype = $("input[name='rdo_ques_type']:checked").val();
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
                }
            }
        });
    } else if (type == 4) {
        queryPaper(courseid, 'tr_task_obj', 4, returnValue);
    } else if (type == 6) {
        queryMicView(courseid, 'tr_task_obj', 6, returnValue);
    }
}

/**
 * 任务--添加元素
 * @param type
 */
function showDialogPage(type) {
    var url;
    if (type == 1) {
        url = "tpres?m=dialogUpload&operate_type=1&usertype=2&courseid=" + courseid;
    } else if (type == 2) {
        url = "tptopic?m=toAdmin&operate_type=1&courseid=" + courseid;
    } else if (type == 3) {
        var questype = $("input[name='rdo_ques_type']:checked").val();
        url = 'question?m=toAddQuestion&operate_type=1&questype=' + questype + '&courseid=' + courseid + '&flag=1';
    }
    var param = "dialogHeight=800px;dialogWidth=900px;center:yes;status:no;scroll:yes;help:no";
    var returnValue = window.showModalDialog(url, "", param);
    if (returnValue == undefined) {
        returnValue = window.returnValue;
    }
    if (returnValue == null || returnValue.toString().length < 1) {
        alert("操作取消!");
        return;
    }
    if (type == 1) {//资源
        queryResource(courseid, 'tr_task_obj', returnValue)
    } else if (type == 2) {//论题
        queryInteraction(courseid, 'tr_task_obj', returnValue);
    } else if (type == 3) { //问题类型
        var questype = $("input[name='rdo_ques_type']:checked").val();
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
                    if (rmsg.objList.length && typeof returnValue != 'undeinfed') {
                        $("#hd_questionid").val(rmsg.objList[0].questionid);
                        $("#sp_ques_id").html(rmsg.objList[0].questionid);
                        load_ques_detial(returnValue, questype);
                    }
                    $("#tr_ques_obj").show();
                }
            }
        });
    }
}




function loadDialogPage(type) {
    var url;
    if (type == 1) {
        url = "tpres?m=dialogUpload&operate_type=1&usertype=2&courseid=" + courseid;
    } else if (type == 2) {
        url = "tptopic?m=toAdmin&operate_type=1&courseid=" + courseid;
    } else if (type == 3) {
        var questype = $("input[name='rdo_ques_type']:checked").val();
        url = 'question?m=toDialogAddQuestion&operate_type=1&questype=' + questype + '&courseid=' + courseid + '&flag=1';
    }
    $("#dv_content").load(url,function(){

    });

}



/**
 * 获取问题类型
 * @param trobj
 * @return
 */
function queryQuestionType(trobj, questype, boo, questionid) {
    $.ajax({
        url: 'task?toQryQuestionType',
        type: 'post',
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            var html = '<th><span class="ico06"></span>选择题型：</th>';
            if (json.objList.length > 0) {
                html += '<td>';
                $.each(json.objList, function (idx, itm) {
                    html += '<input  id="rdo_type_' + (idx + 1) + '" type="radio" value="' + itm.dictionaryvalue + '" name="rdo_ques_type"/>';
                    html += '<label for="rdo_type_' + (idx + 1) + '">' + itm.dictionaryname + '</label>&nbsp;';
                });
                html += '</td>';
            } else {
                html = '<th><span class="ico06"></span>选择题型：</th><td>暂无数据!</td>';
            }
            $('#' + trobj).show();
            $('#' + trobj).html(html);

            if (questype > 0 && boo) {
                $("#tb_ques").show();
                $("input[name='rdo_ques_type']").filter(function () {
                    return this.value == questype
                }).attr('checked', true);
                $("input[name='rdo_ques_type']").each(function (idx, itm) {
                    $(itm).bind("click", function () {
                        changeQuesType(itm.value);
                    })
                });
                changeQuesType(questype, questionid);
            } else {
                $("#tb_ques").show();
                $("input[name='rdo_ques_type']").filter(function () {
                    return this.value == questype
                }).attr('checked', true);
                $("input[name='rdo_ques_type']").filter(function () {
                    return this.value != questype
                }).attr('disabled', true);
                changeQuesType(questype, questionid);
            }
        }
    });
}

/**
 * 弹出选择资源页面
 * */
function showResourceElementJsp(){
    var url = 'task?m=toTaskElementDetial&operate_type=1&gradeid='+gradeid+'&subjectid='+subjectid+'&tasktype=1&courseid=' + courseid;
    $("#dv_content").load(url,function(){
        $("#dv_content").show();
        $("#a_click").click();
    });
    //var param = "dialogHeight:800px;dialogWidth:900px;status:no;location:no";

//    var returnValue = window.showModalDialog(url, "", param);
//    if (returnValue == undefined) {
//        returnValue = window.returnValue;
//    }
//    if (returnValue == null || returnValue.toString().length < 1) {
//        alert("操作取消!");
//        return;
//    }
//    var values=returnValue.split(",");
//    if(values.length>0){
//        if(values.length==2){
//            $("#hd_elementid").val(values[0]);
//            $("#resource_type").val(values[1]);
//            queryResource(courseid, 'tr_task_obj', values[0]);
//        }else if(values.length==3){
//            $("#hd_elementid").val(values[0]);
//            $("#resource_type").val(values[1]);
//            queryResource(values[2], 'tr_task_obj', values[0]);
//        }else{
//            $("#hd_elementid").val(values[0]);
//            $("#resource_type").val(values[1]);
//            $("#remote_type").val(values[2]);
//            $("#dv_res_name").html('<span class="ico_mp41"></span>'+values[3]);
//            $("#resource_name").val(values[3]);
//        }
//    }

}

/**
 * 获取资源
 * @param courseid
 * @param trobj
 * @return
 */
function queryResource(courseid,trobj,taskvalueid,taskstatus){
    if(typeof(courseid)=='undefined'||courseid.length<1){
        alert('异常错误，系统未获取到课题标识!');
        return;
    }

    $.ajax({
        url:'tpres?toQueryResourceList',
        type:'post',
        data:{courseid:courseid,resid:taskvalueid},
        dataType:'json',
        error:function(){
            alert('网络异常!');
        },
        success:function(json){
            var htm='';
            htm+='<th><span class="ico06"></span>选择资源：</th>';
            htm+='<td class="font-black">';
            if(typeof taskstatus=='undefined')
                htm+='<p><a class="font-darkblue"  href="javascript:showResourceElementJsp()">>> 选择资源</a><!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="font-darkblue"  href="javascript:showDialogPage(1)">>> 添加资源</a>--></p>';
            htm+='<div class="jxxt_zhuanti_add_ziyuan" id="dv_res_name"></div>';
            htm+='<input type="hidden" id="hd_elementid"/>';
            htm+='</td>';
            $("#"+trobj).html(htm);
            $("#tb_ques").hide();

            if(json.objList.length>0&&typeof taskvalueid!='undefined'&&taskvalueid.toString().length>0){
                $("#dv_res_name").html('<span class='+json.objList[0].suffixtype+'></span>'+json.objList[0].resname);
                $("#hd_elementid").val(json.objList[0].resid);
            }
            if(typeof(taskvalueid)!='undefined'&&taskvalueid.toString().length>0)
                $("select[id='sel_resource']").val(taskvalueid);
        }
    });
}


/**
 * 添加任务--成卷测试模式窗体
 *
 */
function doAddPaperTask(){
    $.ajax({
        url: 'paper?toQueryTaskPaperList',
        type: 'post',
        data: {courseid: courseid, paperid: paperid, type: 4},
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            if (json.objList.length > 0 && typeof paperid != 'undefined' && paperid.toString().length > 0) {
                $("#tb_ques").hide();
                $("#dv_paper_name").html(json.objList[0][0].papername);
                $("#hd_elementid").val(json.objList[0][0].paperid);
                $.fancybox.close();
            }
        }
    });
}


/**
 * 获取试卷
 * @param courseid
 * @param trobj
 * @return
 */
function queryPaper(courseid, trobj, type, taskvalueid, taskstatus, quesnum) {
    if (typeof(courseid) == 'undefined' || courseid.length < 1) {
        alert('异常错误，系统未获取到课题标识!');
        return;
    }

    $.ajax({
        url: 'paper?toQueryTaskPaperList',
        type: 'post',
        data: {courseid: courseid, paperid: taskvalueid, type: type},
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            var htm = '';
            htm += '<th><span class="ico06"></span>选择试卷：</th>';
            htm += '<td class="font-black">';
            if (type == 4) {
                if(typeof taskstatus == 'undefined')
                    htm += '<p><a class="font-darkblue"  href="javascript:loadTaskElement(' + type + ')">>> 选择试卷</a></p>';
                htm += '<div class="jxxt_zhuanti_add_ziyuan" id="dv_paper_name"></div>';
            } else if (type == 5) {
                htm = '';
                /*htm='<th><span class="ico06"></span>试题数量：</th>';
                 htm+='<td class="font-black">';
                 if(objectiveQuesCount.length<1||parseInt(objectiveQuesCount)<1)
                 htm+='当前专题暂无客观题!';
                 else{
                 htm+='<select id="sel_ques_num">';
                 for(var i=1;i<=objectiveQuesCount;i++){
                 htm+='<option value="'+i+'">'+i+'</option>'
                 }
                 htm+='</select>';
                 }*/
            }
            htm += '<input type="hidden" id="hd_elementid"/>';
            htm += '</td>';
            $("#" + trobj).html(htm);
            $("#tb_ques").hide();

            if (typeof quesnum != 'undefined' && quesnum.toString().length > 0)
                $("select[id='sel_ques_num']").val(quesnum);
            if (typeof taskstatus != 'undefined')
                $("select[id='sel_ques_num']").attr("disabled", true);

            if (json.objList.length > 0 && typeof taskvalueid != 'undefined' && taskvalueid.toString().length > 0) {
                var papername;
                if(json.objList[0].length>0){
                    papername=json.objList[0][0].papername;
                }else if(json.objList[1].length>0)
                    papername=json.objList[1][0].papername;
                $("#dv_paper_name").html(papername);
                $("#hd_elementid").val(taskvalueid);
            }
            if (typeof(taskvalueid) != 'undefined' && taskvalueid.toString().length > 0)
                $("select[id='sel_resource']").val(taskvalueid);
        }
    });
}

/**
 * 获取微视频资源
 * @param courseid
 * @param trobj
 * @param type
 * @param taskvalueid
 * @param taskstatus
 * @param quesnum
 */
function queryMicView(courseid, trobj, type, taskvalueid,taskstatus) {
    if (typeof(courseid) == 'undefined' || courseid.length < 1) {
        alert('异常错误，系统未获取到课题标识!');
        return;
    }

    $.ajax({
        url: 'tpres?toQueryResourceList',
        type: 'post',
        data: {courseid: courseid, resid: taskvalueid, difftype: 1},
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            var htm = '';
            htm += '<th><span class="ico06"></span>选择微视频：</th>';
            htm += '<td class="font-black">';
            if(typeof taskstatus=='undefined')
                htm += '<p><a class="font-darkblue"  href="javascript:loadTaskElement(' + type + ')">>> 选择微视频</a></p>';
            htm += '<div class="jxxt_zhuanti_add_ziyuan" id="dv_res_name"></div>';
            htm += '<input type="hidden" id="hd_elementid"/>';
            htm += '</td>';
            $("#" + trobj).html(htm);
            $("#tb_ques").hide();

            if(json.objList.length>0&&typeof taskvalueid!='undefined'&&taskvalueid.toString().length>0){
                $("#dv_res_name").html('<span class='+json.objList[0].suffixtype+'></span>'+json.objList[0].resname);
                $("#hd_elementid").val(json.objList[0].resid);
            }
            if (typeof(taskvalueid) != 'undefined' && taskvalueid.toString().length > 0)
                $("select[id='sel_resource']").val(taskvalueid);
        }
    });
}


function queryLiveLession(courseid, trobj, type, taskvalueid,taskstatus) {
    if (typeof(courseid) == 'undefined' || courseid.length < 1) {
        alert('异常错误，系统未获取到课题标识!');
        return;
    }

    $.ajax({
        url: 'task?toQueryLiveList',
        type: 'post',
        data: {courseid: courseid, taskid: taskvalueid},
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            var htm = '';
            htm += '<th><span class="ico06"></span>直播主题：</th>';
            htm += '<td class="font-black">';
            htm += '<input placeholder="输入直播主题" class="w300" type="text" id="task_name"';
            if(typeof taskstatus!='undefined')
                htm+=' disabled ';
            htm+='>';
            htm += '<input type="hidden" id="hd_elementid"/>';
            htm += '</td>';
            $("#" + trobj).html(htm);
            $("#tb_ques").hide();

            if(json.objList.length>0&&typeof taskvalueid!='undefined'&&taskvalueid.toString().length>0){
                $("#task_name").val(json.objList[0].taskname);
                $("#hd_elementid").val(json.objList[0].taskid);
            }
        }
    });

}

function qryTopicByCls(taskvalueid) {
    var task_type = $("#task_type").val();
    if (task_type.length > 0 && task_type == "2") {
        $('#tr_task_obj').html('');
        queryInteraction(courseid, 'tr_task_obj', taskvalueid);
    }
}

/**
 * 任务对象班级
 * @param obj
 * @param classid
 */
function selectClassObj(obj, classid) {
    if (typeof(classid) != 'undefined') {
        $("#p_group_" + classid + " input").each(function () {
            $(this).attr("checked", obj.checked)
        });
    }

    // qryTopicByCls();
}

/**
 * 获取互动论题
 * @param courseid
 * @param trobj
 * @return
 */
function queryInteraction(courseid, trobj, taskvalueid, taskstatus) {
    if (typeof(courseid) == 'undefined' || courseid.length < 1) {
        alert('异常错误，系统未获取到课题标识!');
        return;
    }

    var task_type = $("#task_type").val();
    if (task_type.length < 1) {
        alert('请选择任务类型!');
        return;
    }
    var cls = $("input[type='checkbox']").filter(function () {
        return this.id.indexOf("ckb_") != -1 && this.checked == true
    });
    var clsid = '';
    $.each(cls, function (idx, itm) {
        if (clsid.length > 0)
            clsid += ",";
        clsid += $(itm).val();
    });
    /*if(clsid.length<1&&task_type=="2"){
     alert('请选择任务对象班级!');
     return;
     }*/

    $.ajax({
        url: 'task?toQryTopicList',
        type: 'post',
        data: {courseid: courseid, clsid: clsid, topicid: taskvalueid},
        dataType: 'json',
        error: function () {
            alert('网络异常!');
        },
        success: function (json) {
            var htm = '';
            htm += '<th><span class="ico06"></span>选择论题：</th>';
            htm += '<td class="font-black">';
            if (typeof taskstatus == 'undefined')
                htm += '<p><a class="font-darkblue"  href="javascript:loadTaskElement(2)">>> 选择论题</a></p>';
            htm += '<div class="jxxt_zhuanti_add_ziyuan" id="dv_topictitle"></div>';
            htm += '<input type="hidden" id="hd_elementid"/>';
            htm += '</td>';
            $("#tb_ques").hide();
            $("#" + trobj).html(htm);

            if (json.objList.length > 0 && typeof taskvalueid != 'undefined' && taskvalueid.toString().length > 0) {
                $("#dv_topictitle").html(json.objList[0].topictitle);
                $("#hd_elementid").val(json.objList[0].topicid);
            }

            if (typeof(taskvalueid) != 'undefined' && taskvalueid.toString().length > 0) {
                $("select[id='sel_topic']").val(taskvalueid);
            }

        }
    });
}


function changeQuesType(type, questionid) {
    if (isNaN(type)) {
        alert('系统未获取到问题类型!');
        return;
    }

    var h = '';
    switch (parseInt(type)) {
        case 1:
            h = "其&nbsp;&nbsp;&nbsp;&nbsp;他：";
            break;
        case 2:
            h = "填&nbsp;&nbsp;&nbsp;&nbsp;空：";
            break;
        case 3:
            h = "单&nbsp;&nbsp;&nbsp;&nbsp;选：";
            break;
        case 4:
            h = "多&nbsp;&nbsp;&nbsp;&nbsp;选：";
            break;
    }
    $("#s_questype").html(h);
    $("#hd_questionid").val('');
    $("#sp_ques_id").html('');

    $.ajax({
        url: "question?m=questionAjaxList",
        type: "post",
        data: {questype: type, courseid: courseid, questionid: questionid},
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
                $("#td_option").html('');
                if (rmsg.objList.length && typeof questionid != 'undefined' && questionid.toString().length > 0) {
                    $("#hd_questionid").val(rmsg.objList[0].questionid);
                    $("#sp_ques_id").val(rmsg.objList[0].questionid);
                    load_ques_detial(rmsg.objList[0].questionid, type);
                }
                $("#tr_ques_obj").show();
            }
        }
    });
}

/**
 * 加载问题详情
 * @param questionid
 * @param type
 */
function load_ques_detial(questionid, type) {
    if (typeof (questionid) == 'undefined')
        questionid = $("#sel_ques").val();
    if (typeof (type) == 'undefined')
        type = $("input[name='rdo_ques_type']:checked").val();

    if (isNaN(questionid) || isNaN(type))return;
    $.ajax({
        url: "question?m=questionAjaxList",
        type: "post",
        data: {questype: type, courseid: courseid, questionid: questionid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                var htm = '';
                if (rmsg.objList.length) {
                    $.each(rmsg.objList, function (idx, itm) {
                        $("#ques_name").html(itm.content);
                        $("#sp_ques_id").html(questionid);
                        if (typeof itm.analysis != 'undefined')
                            $("#ques_answer").html(itm.analysis);

                        if (itm.questionOptionList != null && itm.questionOptionList.length > 0 &&itm.questiontype!=1) {
                            $.each(itm.questionOptionList, function (idx, im) {
                                var type = itm.questiontype == 3 ? "radio" : "checkbox";
                                htm += '<li><input disabled type="' + type + '"/>';
                                htm += im.optiontype + ".&nbsp;";
                                //htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                htm += im.content;
                                if (im.isright > 0)
                                    htm += '<span class="ico12"></span>';
                                htm += '</li>';
                            });
                            //$("#td_option").parent("tr").show();
                            $("#td_option").html(htm);
                            $("#td_option").show();
                        }
                    });
                }
            }
        }
    });
}

function load_task_detial(taskid) {
    if (typeof taskid == 'undefined')
        return;
    $.ajax({
        url: "taskallot?m=ajaxTaskAllot",
        type: "post",
        data: {taskid: taskid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                var htm = '';
                $('#p_obj_' + taskid + '').html('');
                if (rmsg.objList.length) {
                    if (rmsg.objList[0] != null) {

                        $.each(rmsg.objList[0], function (idx, itm) {
                            var timeObj = itm.taskstatus == 1 ? itm.tasktime : itm.taskstatus == 3 ? "已结束" : itm.taskstatus;
                            var tmpTime = '<strong';
                            if(itm.taskstatus!="3"&&itm.taskstatus!="1")
                                tmpTime+=' style="color:#22ac38;"'
                            tmpTime+='><span class="ico_time"></span>' + timeObj + '</strong>';
                            if (idx != 0)
                                tmpTime = '<br>' + tmpTime;

                            if (typeof itm.allotname != 'undefined' && !$('span[id="sp_' + itm.taskid + itm.allotid + '"]').length) {

                                $('#p_obj_' + taskid + '').append('<span id="sp_' + itm.taskid + itm.allotid + '">' + tmpTime + itm.allotname + itm.allotobj + '&nbsp;</span>');
                                var dvObj = $('#dv_group_' + taskid + '');
                                if (dvObj.length < 1) {
                                    var h = '<div style="display: none;float:right;z-index: 9999" class="float" id="dv_group_' + taskid + '">';
                                    var str = itm.allotname.length > 12 ? itm.allotname.substring(0, 12) + '...' : itm.allotname;
                                    var str1 = itm.allotobj.length > 12 ? itm.allotobj.substring(0, 12) + '...' : itm.allotobj;
                                    h += '<p><strong>' + str + '</strong></p>';
                                    h += '<ul id="ul_group_' + taskid + '">';
                                    h += '<li>' + str1 + '</li>'
                                    h += '</ul>';
                                    h += '</div>';
                                    $('#p_obj_' + taskid + '').append(h);
                                }
                            } else if (!$('span[id="sp_' + itm.taskid + itm.allotid + '"]').length) {
                                $('#p_obj_' + taskid + '').append('<span id="sp_' + itm.taskid + itm.allotid + '">' + tmpTime + itm.allotobj + '&nbsp;</span>');

                            } else {
                                //$('#p_obj_'+taskid+'').append(itm.allotobj+"&nbsp;");
                                var groupname = itm.allotobj.length > 12 ? itm.allotobj.substring(0, 12) + '...' : itm.allotobj;
                                $('#ul_group_' + taskid + '').append('<li>' + groupname + '</li>');
                                var obj = $('#sp_' + itm.taskid + itm.allotid + "");
                                var str = isIE ? obj.get(0).innerText : obj.get(0).textContent;
                                var tmpStr = itm.allotobj;
                                //123+123>5  5-3
                                if (str.length + itm.allotobj.length > 52) {
                                    tmpStr = itm.allotobj.substring(0, 52 - str.length) + '...';
                                    if (str.lastIndexOf('...') == -1) {
                                        $(obj).append(tmpStr + "&nbsp;");
                                        $(obj).hover(
                                            function () {
                                                $('#dv_group_' + taskid + '').show();
                                            }, function () {
                                                $('#dv_group_' + taskid + '').hide();
                                            });
                                    }
                                } else
                                    $(obj).append(tmpStr + "&nbsp;");
                            }
                        });
                    }

                    var h = '', type;
                    if (rmsg.objList[1] != null) {
                        $.each(rmsg.objList[1], function (idx, itm) {
                            switch (itm.questiontype) {
                                case 1:
                                    type = "其&nbsp;&nbsp;&nbsp;&nbsp;他：";
                                    break;
                                case 2:
                                    type = "填&nbsp;&nbsp;&nbsp;&nbsp;空：";
                                    break;
                                case 3:
                                    type = "单&nbsp;&nbsp;&nbsp;&nbsp;选：";
                                    break;
                                case 4:
                                    type = "多&nbsp;&nbsp;&nbsp;&nbsp;选：";
                                    break;
                            }
                            $('#s_questype_' + taskid + '').html(type);
                            h += replaceAll(itm.content.toLowerCase(), '<span name="fillbank"></span>', "_______");
                        });
                    }
                    $('#ques_name_' + taskid + '').html(h);

                    var ohtm = '';
                    if (rmsg.objList[2] != null) {
                        $.each(rmsg.objList[2], function (ix, im) {
                            //var taskObj=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                            if(im.questiontype==1)return;
                            var taskObj = im.content;
                            ohtm += '<li>';
                            var type = im.questiontype == 3 ? "radio" : "checkbox";
                            ohtm += '<input disabled type="' + type + '"/>';
                            ohtm += im.optiontype + ".&nbsp;";
                            ohtm += taskObj;
                            if (im.isright == 1)
                                ohtm += '<span class="ico12"></span>';
                            ohtm += '</li>';
                        });
                    }
                    $('#p_option_' + taskid + '').html(ohtm);
                }
            }
        }
    });
}


/**
 * 生成任务完成标准设置
 * @return
 */
function initTaskCriteria(tasktype) {
    if (isNaN(tasktype)) {
        alert('未获取到任务类型,无法设置任务完成标准!');
        return;
    }
    $("#td_criteria").parent().show();

    var htm = '';
    if (tasktype == "1") {//资源学习
        htm += '<input value="1" name="ck_criteria" type="radio" />查看&nbsp;';
        htm += '<input value="2" name="ck_criteria" type="radio" />提交心得&nbsp;';
        //htm+='<input value="comment" name="ck_criteria" type="checkbox" />点评';
    } else if (tasktype == "2") {//互动交流 
        htm += '<input value="1" name="ck_criteria" type="radio" />查看&nbsp;';
        htm += '<input value="2" name="ck_criteria" type="radio" />发主帖&nbsp;';
    } else if (tasktype == "3") {//课后作业
        htm += '<input checked value="1" name="ck_criteria" type="radio" />提交&nbsp;';
        $("#td_criteria").parent().hide();
        //htm+='<input value="right" name="ck_criteria" type="radio" />提交并正确';
    } else if(tasktype=="4"||tasktype=="5") {
        htm += '<input checked value="1" name="ck_criteria" type="radio" />提交试卷';
        $("#td_criteria").parent().hide();
    } else if(tasktype=="6"){
        htm += '<input  value="1" name="ck_criteria" type="radio" />查看视频';
        htm += '<input checked value="2" name="ck_criteria" type="radio" />提交试卷';
        $("#td_criteria").parent().hide();
    } else if(tasktype=="10"){
        htm += '<input checked value="1" name="ck_criteria" type="radio" />进入直播课';
        $("#td_criteria").parent().hide();
    }

    $("#td_criteria").html(htm);
}


/**
 * 管理任务
 * @return
 */
function doSubManageTask(taskid) {
    //任务相关
    var tasktype = $("#task_type");
    var timeType = $("input[name='time_rdo']:checked").val();
    var all_btime = $("#all_class_btime"), all_etime = $("#all_class_etime");
    var taskname=$("#task_name");
    //var taskremark = CKEDITOR.instances["task_remark"].getData();
    var taskremark=$("#task_remark").val();
    var criteriatype = $("input[name='ck_criteria']:checked");
    var groupvalidate = $("input[name='ck_group']");
    var grouparr = $("input[name='ck_group']:checked");
    var clsarr = $("input[name='ck_cls']:checked");
    var clstype=new Array();
    /*.filter(function(){
     var tmpId=this.id.substring(this.id.lastIndexOf('_')+1);
     return $('#p_group_'+tmpId+' input').length<1;
     })*/
    //论题与资源
    var quesid = $("#hd_questionid").val();
    var resourceid = $("#hd_elementid").val();
    var topicid = $("#hd_elementid").val();
    var paperid = $("#hd_elementid").val();
    var micresid = $("#hd_elementid").val();
    var quesNum = $("#sel_ques_num").val();

    var url = '', iserror = '', param = {}, paramStr = '?t=' + new Date().getTime(), rflag = false, btimeArray = '', etimeArray = '', clsArray = '';


    if (tasktype.val().length < 1) {
        alert('请选择任务类型!');
        tasktype.focus();
        return;
    }
    param.tasktype = tasktype.val();

    if(tasktype==6)
        criteriatype=2

    if (grouparr.length < 1 && clsarr.length < 1) {
        alert("请选择任务对象!");
        return;
    }

    if (timeType == 0) {
        if (all_btime.val().length < 1) {
            alert('请设置任务开始时间!');
            return;
        } else if (all_etime.val().length < 1) {
            alert('请设置任务结束时间!');
            return;
        }
    }


    $.each(grouparr, function (idx, itm) {
        var p = $(itm).parent().parent("ul");
        var tmpId = p.attr("id").substring(p.attr("id").lastIndexOf('_') + 1);
        var btimeObj = $('#b_time_' + tmpId + '');
        if (timeType == 1) {
            if (btimeObj.val().length < 1) {
                iserror = '请设置任务开始时间!';
            }
            var etimeObj = $('#e_time_' + tmpId + '');
            if (etimeObj.val().length < 1) {
                iserror = '请设置任务结束时间!';
            }
            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'btimeClsArray=' + btimeObj.val();

            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'etimeClsArray=' + etimeObj.val();
        } else {
            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'btimeClsArray=' + all_btime.val();

            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'etimeClsArray=' + all_etime.val();
        }

        if (paramStr.Trim().length > 0)
            paramStr += '&';
        paramStr += 'groupArray=' + $(itm).val();


    });

    /*班级*/
    $.each(clsarr, function (idx, itm) {
        var tmpId = itm.id.substring(itm.id.lastIndexOf('_') + 1);
        var btimeObj = $('#b_time_' + tmpId + '');
        if (timeType == 1) {
            if (btimeObj.val().length < 1) {
                iserror = '请设置任务开始时间!';
            }
            var etimeObj = $('#e_time_' + tmpId + '');
            if (etimeObj.val().length < 1) {
                iserror = '请设置任务结束时间!';
            }
            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'btimeClsArray=' + btimeObj.val();

            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'etimeClsArray=' + etimeObj.val();
        } else {
            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'btimeClsArray=' + all_btime.val();

            if (paramStr.Trim().length > 0)
                paramStr += '&';
            paramStr += 'etimeClsArray=' + all_etime.val();
        }

        if (paramStr.Trim().length > 0)
            paramStr += '&';
        paramStr += 'clsArray=' + $(itm).val();

    });

    if (iserror.length) {
        alert(iserror);
        return;
    }

    if(clsarr.length>0){
        $.each(clsarr,function(ix,im){
            var type=$(im).data().bind;
            clstype.push(type);
        });
        param.clstype=clstype.join(",");
    }


    /*	if(taskname.val().Trim().length<1){
     alert('请填写任务名称!');
     taskname.focus();
     return;
     }
     param.taskname=taskname.val();*/

    //课后作业
    var quesoptionArray = $("textarea").filter(function () {
        return this.id.indexOf("ques_option_") != -1
    });
    if (tasktype.val() == "3") {
        if (quesid.length < 1) {
            alert('请选择试题!');
            return;
        }
        param.taskvalueid = quesid;
    } else if (tasktype.val() == "2") {
        if (topicid.length < 1) {
            alert('请选择论题!');
            return;
        }
        param.taskvalueid = topicid;
    } else if (tasktype.val() == "1") {
        if (resourceid.length < 1) {
            alert('请选择资源!');
            return;
        }
        param.taskvalueid=resourceid;
        param.resourcetype=$("#resource_type").val();
        param.remotetype=$("#remote_type").val();
        param.resourcename=$("#resource_name").val();
    } else if (tasktype.val() == "4") {
        if (paperid.length < 1) {
            alert('请选择试卷!');
            return;
        }
        param.taskvalueid = paperid;
    } else if (tasktype.val() == "5") {
        /*if (quesNum.length < 1) {
            alert('请选择试卷题目数量!');
            return;
        }
        param.quesnum = quesNum;*/
        param.taskvalueid = paperid;
    }else if (tasktype.val() == "6") {
        if (micresid.length < 1) {
            alert('请选择微视频!');
            return;
        }
        param.taskvalueid = micresid;
    }else if (tasktype.val() == "10") {
        if(taskname.val().Trim().length<1){
             alert('请填写任务名称!');
             return;
         }
         param.taskname=taskname.val();
         param.taskvalueid = 1;
    }


    param.courseid = courseid;
    param.taskremark = taskremark;
    if (!(typeof(questype) != 'undefined' && questype == '5') && criteriatype.length < 1) {
        alert('请设置任务完成标准!');
        return;
    }
    $.each(criteriatype, function (ix, im) {
        paramStr += "&taskcri=" + $(im).val();
    });

    if (!confirm('数据验证完毕!确认提交?'))
        return;
    resetBtnAttr("submint_btn","an_small","an_gray_small","",2);

    if (typeof(taskid) != 'undefined') {
        url = 'task?doSubUpdTask';
        param.taskid = taskid;
    } else {
        url = 'task?doSubAddTask';
    }
    //开始向后台添加数据  
    $.ajax({
        url: url,
        type: "post",
        data: paramStr + '&' + $.param(param),
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
                resetBtnAttr("submint_btn","an_small","an_gray_small","doSubManageTask(undefined)",1);
            } else {
                alert(rmsg.msg);
                location.href = 'task?toTaskList&courseid=' + courseid;
            }
        }
    });
}

/**
 *
 * @param taskid
 * @param doFlag 是否有学生完成任务
 */
function doDelTask(taskid, doFlag) {
    if (typeof taskid == 'undefined')
        return;
    var msg = '是否删除？ ';
    if (typeof(doFlag) != 'undefined' && doFlag > 0)
        msg = "任务中已有学生作答数据，" + msg;
    if (!confirm(msg))return;

    $.ajax({
        url: "task?m=doDelTask",
        type: "post",
        data: {taskid: taskid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                alert(rmsg.msg);
                pageGo('pList');
            }
        }
    });

}

/**
 * 修改任务排序
 * @param taskid
 * @param orderidx
 */
function doUpdOrderIdx(taskid, orderidx) {
    if (typeof taskid == 'undefined' || orderidx == 'undefined')
        return;

    $.ajax({
        url: "task?m=doUpdOrderIdx",
        type: "post",
        data: {
            taskid: taskid,
            orderidx: orderidx,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                alert(rmsg.msg);
                pageGo('pList');
            }
        }
    });

}


/**
 * 回答任务
 * @param tasktype
 * @param taskid
 * @param quesid
 * @param groupid
 * @param questype
 */
function doStuSubmitQues(tasktype, taskid, quesid, groupid, questype) {
    if (typeof(tasktype) == 'undefined' || typeof(taskid) == 'undefined' ||
        typeof(quesid) == 'undefined' || courseid.length < 1
        ) {
        alert('未知的任务信息!请联系相关人员!');
        return;
    }
    //回答
    var txt_taskanswer = $("#txt_taskanswer_" + quesid);
    //填空
    var txt_fb_option = $("input[name='txt_fb_" + quesid + "']");
    var txt_fb_answer = $("input[name='txt_fb_" + quesid + "']").filter(function () {
        return this.value.Trim().length > 0
    });
    //选择
    var optionArray = $("input[name='ques_option_" + quesid + "']:checked");

    var paramStr = 't=' + new Date().getTime();
    var param = {tasktype: tasktype, taskid: taskid, quesid: quesid, courseid: courseid};//,groupid:groupid
    if (typeof(questype) != 'undefined' && !isNaN(questype))
        param.questype = questype;

    if (tasktype == 3) {
        if (questype == 1) {
            if (txt_taskanswer.val().Trim().length < 1) {
                alert('请输入问答题答案!');
                txt_taskanswer.focus();
                return;
            }
            param.quesanswer = txt_taskanswer.val();

        } else if (questype == 2) {
            if (txt_fb_option.length < 1) {
                alert('抱歉,未发现填空题回答输入框!');
                return;
            }
            if (txt_fb_option.length > txt_fb_answer.length) {
                alert('请完成填空录入后提交!');
                return;
            }
            $.each(txt_fb_answer, function (idx, itm) {
                if (paramStr.length > 0)
                    paramStr += "&";
                paramStr += "fbanswerArray=" + $(itm).val();
            });

        } else if (questype == 3 || questype == 4) {
            if (optionArray.length < 1) {
                alert('请选择选项后提交!');
                return;
            }
            $.each(optionArray, function (idx, itm) {
                if (paramStr.length > 0)
                    paramStr += "&";
                paramStr += "optionArray=" + $(itm).val();
            });
        }
    } else if (tasktype == 1) {
       /* var tmp=null;
        if(ueditorArray.length>0){
            $.each(ueditorArray,function(idx,itm){
                if(itm==taskid){
                    tmp=ueditorObjArray[idx];return;
                }
            });
        }
        var obj=tmp.getContent();
        if (obj.Trim().length < 1) {
            alert('请输入学习心得后提交!');
            return;
        } */
        var resContent=$("#txt_taskanswer_"+taskid);
        if (resContent.val().Trim().length < 1) {
            alert('请输入学习心得!');
            resContent.focus();
            return;
        }
        param.quesanswer = resContent.val().Trim();


    }

    //alert(paramStr);
    if (!confirm('数据验证完毕!确认提交?'))
        return;

    $("#a_task_"+taskid).hide();

    var isTishiAnnex=false;
    //附件上传
    var annexObj=$("#txt_f_"+taskid);
    if(annexObj.length>0&&annexObj.val().Trim().length>0){
        //先附件上传
        if(!isTishiAnnex){
            alert("您选择了附件上传,将会先提交附件再提交数据，请耐心等待上传完毕!");
            isTishiAnnex=true;
        }
        //   var lastname = annexObj.val().substring(annexObj.val().lastIndex("/"));


        var url="task?doStuSubmitTask&hasannex=1";
        $.ajaxFileUpload({
            url: url+"&"+paramStr + '&' + $.param(param),
            fileElementId: 'txt_f_'+taskid.toString()+'',
            dataType: 'json',
            secureuri: false,
            type: 'POST',
            success: function (rps, status) {
                if(rps.type=="success"){
                    alert(rps.msg);
                    pageGo('pList');
                }else{
                    $("#a_task_"+taskid).show();
                    alert(rps.msg);
                }
            },
            error: function (data, status, e) {
                alert(e);
                $("#a_task_"+taskid).show();
            }
        });
    }else{
        //开始向后台添加数据
        $.ajax({
            url: 'task?doStuSubmitTask',
            type: "post",
            data: paramStr + '&' + $.param(param),
            dataType: 'json',
            cache: false,
            error: function () {
                alert('系统未响应，请稍候重试!');
            }, success: function (rmsg) {
                if (rmsg.type == "error") {
                    alert(rmsg.msg);
                    $("#a_task_"+taskid).show();
                } else {
                    alert(rmsg.msg);
                    pageGo('pList');
                }
            }
        });
    }
}


/**
 * 获取未完成任务人员
 * @param taskid
 */
function loadNoCompleteStu(taskid, usertypeid, taskstatus) {
    if (typeof taskid == 'undefined')
        return;

    $.ajax({
        url: 'task?m=ajaxNoCompleteTaskUser',
        type: "post",
        data: {taskid: taskid, courseid: courseid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                var h = '';
                $("#dv_nocomplete_data").html(h);
                if (rmsg.objList.length > 0) {
                    $.each(rmsg.objList, function (idx, itm) {
                        if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                            if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                                $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                            else {
                                $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                                $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                            }
                        else {
                            $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }

                    });
                    if (taskstatus != '3'){
                        //$("#dv_nocomplete_data").append('<p class="t_c"><a href="javascript:doSendTaskMsg(' + taskid + ',' + usertypeid + ')"  class="an_public1">发提醒</a></p>');
                    }

                }
                showModel("dv_nocomplete");
            }
        }
    });
}


function doSendTaskMsg(taskid, usertypeid) {
    if (typeof taskid == 'undefined') {
        alert('参数异常!请刷新页面重试!');
        return;
    }

    var uidArray = $("input[name='hd_uid']");
    if (uidArray.length < 1) {
        alert('没有发现未完成任务人员!');
        return;
    }
    var dataArray = new Array();
    uidArray.each(function () {
        dataArray.push($(this).val());
    })
    $.ajax({
        url: 'task?m=doSendTaskMsg',
        type: "post",
        data: {
            uidArray: dataArray.join(','),
            taskid: taskid,
            usertypeid: usertypeid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                alert(rmsg.msg);
                closeModel('dv_nocomplete');
            }
        }
    });

}


/**
 *  提交学生建议
 * @param taskid
 * @return
 */
function doSubSuggest(taskid) {
    if (typeof(taskid) == 'undefined') {
        alert('异常错误!参数异常,请刷新页面重试!');
        return;
    }
    if (typeof(courseid) == 'undefined') {
        alert('异常错误!参数异常,请刷新页面重试!');
        return;
    }
    var msg = $("#txt_suggest_" + taskid);
    if (msg.val().Trim().length < 1) {
        alert('请输入建议内容后提交!');
        msg.focus();
        return;
    }
    var isanonmous = $("input[id='ck_anonmous_" + taskid + "']:checked");
    var param = {
        courseid: courseid,
        taskid: taskid,
        suggestcontent: msg.val().Trim(),
        isanonmous: 0
    };
    if (isanonmous.length > 0)
        param.isanonmous = isanonmous.val();

    //开始向后台添加数据   
    $.ajax({
        url: 'task?doSubmitSuggest',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                alert(rmsg.msg);
                $("#txt_suggest_" + taskid).val('');
                closeModel('div_suggest_' + taskid + '');
            }
        }
    });

}

function genderShowdiv(groupid) {
    var divObj = $("div").filter(function () {
        return this.id.indexOf('div_tmp_show') != -1
    });
    if (divObj.length > 0)
        $(divObj).remove();

    $.ajax({
        url: 'task?m=loadGroupStudent',
        type: "post",
        data: {
            groupid: groupid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.type == "error") {
                alert(rmsg.msg);
            } else {
                //得到准确的 鼠标位置
                var y = mousePostion.y;
                var x = mousePostion.x;
                //判断是不是IE8以下的浏览器浏览
                if ($.browser.msie && (parseInt($.browser.version) <= 7)) {
                    y += parseFloat(document.documentElement.scrollTop);
                    x += parseFloat(document.documentElement.scrollLeft);
                }
                y += 5;
                var username = '', groupname = '';
                if (rmsg.objList.length > 0) {
                    username += '<ul>';
                    $.each(rmsg.objList, function (idx, itm) {
                        username += '<li>' + itm.stuname + '</li>';
                        groupname = itm.groupname;
                    });
                    username += '</ul>';
                }
                username = '<p><strong>' + groupname + '成员</strong></p>' + username;
                var h = '<div id="div_tmp_show01" class="jxxt_zhuanti_add_float" '
                    + 'style="position:absolute;left:'
                    + x + 'px;top:' + y + 'px;background-color:white">' + username + '</div>';
                $("body").append(h);
            }
        }
    });

}

/**
 * 查询学生完成情况---选择题
 * @return
 */
function xzloadStuPerformance(classid, tasktype, questionid, classtype) {
    $("#a_class_" + classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");

    if (typeof(classid) == 'undefined' || isNaN(classid)) {
        alert('异常错误，参数错误!111');
        return;
    }
    if (typeof(taskid) == 'undefined' || taskid.length < 1) {
        alert('异常错误，参数错误!222');
        return;
    }
    var param = {};
    param.subjectid=g_subjectid;
    param.taskid = taskid;
    if (classid != null && classid.toString().length > 0) {
        param.classid = classid;
        param.classtype = classtype;
    } else {
        classtype=0;
        param.classid = 0;
        classid=0;
        param.classtype = 0;
    }
    if (tasktype == 3) {
        param.questype = questype;
        param.questionid = questionid;
    }

    $.ajax({
        url: 'task?xzloadStuPerformance',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            var img = '<img src="task/img/'+taskid+'/'+classid+'/'+classtype+'/op"/>';
            $("#piediv").html(img);
            if (rmsg.objList[0].length > 0) {
                var totalnum = 0;
                var rightnum = 0;
                var finishnum = 0;
                $.each(rmsg.objList[0], function (idx, itm) {
                    totalnum += parseInt(itm.TOTALNUM);
                    rightnum += parseInt(itm.RIGHTNUM);
                    finishnum += parseInt(itm.FINISHNUM);

                });
                var fn = parseFloat(parseInt(finishnum)) / parseInt(totalnum) * 100;
                var finishhtml = fn.toFixed(2);
                finishhtml += "%";
                $("#finishnum").html(finishhtml);
               if(finishnum>0){
                   var rn = parseFloat(parseInt(rightnum))/ parseInt(finishnum) * 100;
                   var righthtml = rn.toFixed(2);
                   righthtml += "%";
                   $("#rightnum").html(righthtml);
               }else{
                   $("#rightnum").html("0.00%");
               }
            } else {
                $("#finishnum").html("0");
                $("#rightnum").html("0");
            }
            if (rmsg.objList[1].length > 0) {
                var optionhtm = '';
                optionhtm += '<tr>';
                optionhtm += '<th>选项</th>';
                optionhtm += '<th>百分比</th>';
                optionhtm += '</tr>';
                $.each(rmsg.objList[1], function (idx, itm) {
                    optionhtm += '<tr>';
                    optionhtm += '<td>' + itm.OPTION_TYPE + '</td>';
                    optionhtm += '<td>' + itm.NUM + '%</td>';
                    optionhtm += '</tr>';
                });
                var imghtml = '<img src=images/taskPie.png" width="193" height="140">';
                $("#piediv").html();
            } else {
                optionhtm += '<tr><td>暂无数据!</td></tr>';
            }
            var recordhtm = '';
            if (rmsg.objList[2].length > 0) {
                if (rmsg.objList[3].length > 0) {
                    $.each(rmsg.objList[3], function (ix, im) {
                        recordhtm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                        recordhtm += '<colgroup span="4" class="w190"></colgroup>';
                        recordhtm += '<colgroup class="w180"></colgroup>';
                        if(classid==0)
                            recordhtm += '<colgroup class="w180"></colgroup>';
                        recordhtm += '<caption>' + im.groupname + '</caption>';
                        recordhtm += '<tr>';
                        if(classid==0)
                            recordhtm += '<th>班级</th>';
                        recordhtm += '<th>学号</th>';
                        recordhtm += '<th>姓名</th>';
                        recordhtm += '<th>学习时间</th>';
                        recordhtm += '<th>作答内容</th>';
                        recordhtm += '<th>是否完成</th>';
                        recordhtm += '</tr>';
                        var signnature=0;
                        $.each(rmsg.objList[2], function (idx, itm) {
                            $.each(im.tpgroupstudent2, function (i, m) {
                                if (m.userid == itm.userinfo.userid) {signnature++;
                                    recordhtm += '<tr';
                                    if (idx % 2 == 0) {
                                        recordhtm += ' class="trbg1"';
                                    }
                                    recordhtm += '>';
                                    if(classid==0)
                                        recordhtm += '<td>' + itm.clsname + '</td>';
                                    if(typeof(itm.userinfo.stuNo)=='undefined')
                                        recordhtm += '<td>' + '--' + '</td>';
                                    else
                                        recordhtm += '<td>' + itm.userinfo.stuNo + '</td>';
                                    recordhtm += '<td>' + itm.userinfo.stuname + '</td>';
                                    if (typeof(itm.ctimeString) != 'undefined')
                                        recordhtm += '<td>' + itm.ctimeString + '</td>';
                                    else
                                        recordhtm += '<td></td>';
                                    recordhtm += '<td>';
                                    if (itm.isright == 1) {
                                        recordhtm += '<span class="font-red">' + itm.answercontent + '</span>';
                                    } else {
                                        recordhtm += itm.answercontent;
                                    }
                                    recordhtm += '</td>';
                                    if (itm.status > 0)
                                        recordhtm += '<td><span class="ico12" title="完成"></span></td>';
                                    else
                                        recordhtm += '<td><span class="ico24" title="进行中"></span></td>';
                                    recordhtm += '</tr>';
                                }

                            });

                        });
                        if(signnature==0){
                            recordhtm+='<tr><td>暂无数据!</td></tr>';
                        }
                        recordhtm += '</table>';
                    });
                } else {
                    recordhtm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                    recordhtm += '<colgroup span="4" class="w190"></colgroup>';
                    recordhtm += '<colgroup class="w180"></colgroup>';
                    if(classid==0)
                        recordhtm += '<colgroup class="w180"></colgroup>';
                    recordhtm += '<tr>';
                    if(classid==0)
                        recordhtm += '<th>班级</th>';
                    recordhtm += '<th>学号</th>';
                    recordhtm += '<th>姓名</th>';
                    recordhtm += '<th>学习时间</th>';
                    recordhtm += '<th>作答内容</th>';
                    recordhtm += '<th>是否完成</th>';
                    recordhtm += '</tr>';
                    $.each(rmsg.objList[2], function (idx, itm) {
                        recordhtm += '<tr';
                        if (idx % 2 == 0) {
                            recordhtm += ' class="trbg1"';
                        }
                        recordhtm += '>';
                        if(classid==0)
                            recordhtm += '<td>' + itm.clsname + '</td>';
                        if(typeof(itm.userinfo.stuNo)=='undefined')
                            htm += '<td>' + '--' + '</td>';
                        else
                            htm += '<td>' + itm.userinfo.stuNo + '</td>';
                        recordhtm += '<td>' + itm.userinfo.stuname + '</td>';
                        if (typeof(itm.ctimeString) != 'undefined')
                            recordhtm += '<td>' + itm.ctimeString + '</td>';
                        else
                            recordhtm += '<td></td>';
                        recordhtm += '<td>';
                        if (itm.isright == 1) {
                            recordhtm += '<span class="font-red">' + itm.answercontent + '</span>';
                        } else {
                            recordhtm += itm.answercontent;
                        }
                        recordhtm += '</td>';
                        if (itm.status > 0)
                            recordhtm += '<td><span class="ico12" title="完成"></span></td>';
                        else
                            recordhtm += '<td><span class="ico24" title="进行中"></span></td>';
                        recordhtm += '</tr>';
                    });
                    recordhtm += '</table>';
                }
            } else {
                recordhtm += '<table><tr><td>暂无数据!</td></tr></table>';
            }
            if(rmsg.objList[4]!=null&&rmsg.objList[4].length>0){
                $("#dv_nocomplete_data").html('');
                $.each(rmsg.objList[4], function (idx, itm) {
                    if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                        if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        else {
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }
                    else {
                        $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                        $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                        $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                    }

                });
            }
            $("#notcomplete").html(rmsg.objList[4].length)
            if(rmsg.objList[4]!=null&&rmsg.objList[4].length>0){
                //$("#sendMsg").html('<a href="javascript:doSendTaskMsg(' + taskid + ',' + classid + ')"  class="an_public3">发提醒</a>');

            }
            $("#optionTbl").hide();
            $("#optionTbl").html(optionhtm);
            $("#optionTbl").show("");
            $("#mainTbl").hide();
            $("#mainTbl").html(recordhtm);
            $("#mainTbl").show("");
        }
    });
}

/**
 * 查询学生完成情况----主观题
 * @return
 */
function zgloadStuPerformance(classid, tasktype, questionid, classtype) {
    $("#a_class_" + classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");

    if (typeof(classid) == 'undefined' || isNaN(classid)) {
        alert('异常错误，参数错误!111');
        return;
    }
    if (typeof(taskid) == 'undefined' || taskid.length < 1) {
        alert('异常错误，参数错误!222');
        return;
    }
    var param = {};
    param.subjectid=g_subjectid;
    param.taskid = taskid;
    if (classid != null && classid.toString().length > 0) {
        param.classid = classid;
        param.classtype = classtype;
    } else {
        param.classid = 0;
        classid=0;
        param.classtype = 0;
    }
    if (tasktype == 3) {
        param.questype = questype;
        param.questionid = questionid;
    }
    $.ajax({
        url: 'task?zgloadStuPerformance',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.objList[0] != null && rmsg.objList[0].length > 0) {
                var totalnum = 0;
                var rightnum = 0;
                var finishnum = 0;
                $.each(rmsg.objList[0], function (idx, itm) {
                    totalnum += parseInt(itm.TOTALNUM);
                    rightnum += parseInt(itm.RIGHTNUM);
                    finishnum += parseInt(itm.FINISHNUM);

                });
                var fn = parseFloat(parseInt(finishnum)) / parseInt(totalnum) * 100;
                var finishhtml = fn.toFixed(2);
                finishhtml += "%";
                $("#finishnum").html(finishhtml);
            } else {
                $("#finishnum").html("0");
            }
            var htm = '';

            if (rmsg.objList[1] != null && typeof rmsg.objList[1] != 'undefined' && rmsg.objList[1].length > 0) {
                if (rmsg.objList[2] != null && rmsg.objList[2].length > 0) {
                    $.each(rmsg.objList[2], function (ix, im) {
                        htm += '<table border="0" id="recordList_'+im.groupid+'" cellpadding="0" cellspacing="0" class="public_tab2">';
                        htm += '<colgroup span="4" class="w190"></colgroup>';
                        htm += '<colgroup class="w180"></colgroup>';
                        htm += '<colgroup class="w180"></colgroup>';
                        if(classid==0)
                            htm += '<colgroup class="w180"></colgroup>';
                        htm += '<caption>' + im.groupname + '</caption>';
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<th>班级</th>';
                        htm += '<th>学号</th>';
                        htm += '<th>姓名</th>';
                        htm += '<th>学习时间</th>';
                        htm += '<th>作答内容</th>';
                        htm += '<th>是否完成</th>';
                        htm += '</tr>';
                        var signnature = 0;
                        $.each(rmsg.objList[1], function (idx, itm) {
                            $.each(im.tpgroupstudent2, function (i, m) {
                                if (m.userid == itm.userinfo.userid) {signnature++;
                                    htm += '<tr>';
                                    if(classid==0)
                                        htm += '<td>' + itm.clsname + '</td>';
                                    if(typeof(itm.userinfo.stuNo)=='undefined')
                                        htm += '<td>' + '--' + '</td>';
                                    else
                                        htm += '<td>' + itm.userinfo.stuNo + '</td>';
                                    htm += '<td>' + itm.userinfo.stuname + '</td>';
                                    if (typeof(itm.ctimeString) != 'undefined')
                                        htm += '<td>' + itm.ctimeString + '</td>';
                                    else
                                        htm += '<td></td>';
                                    if (typeof(itm.answercontent) != 'undefined') {
                                        htm += '<td>' + itm.answercontent;
                                        if(typeof(itm.replyattachList)!="undefined"&&itm.replyattachList.length>0){
                                            if(itm.replyattachList.length>0){
                                                //htm+='<p><span><br>答题附件：';
                                                $.each(itm.replyattachList,function(index,itmObj){
                                                    var name=(index+1)+itmObj.substring(itmObj.lastIndexOf("."));
                                                    var url = itmObj.substring(0,itmObj.lastIndexOf("."))+"_1"+itmObj.substring(itmObj.lastIndexOf("."));
                                                    htm+='<a class="font-blue" target="_blank" href="'+itmObj+'">'+name+'</a>&nbsp;';
                                                });
                                               // htm+='</span></p>';
                                            }
                                        }
                                        htm+='</td>';
//                                        if(itm..replyattach.length>0&&typeof(itm.replyattachList)!="undefined"){
//                                            if(itm.questionAnswerList[0].replyattachList.length>0){
//                                                htm+='<p><span><br>答题附件：';
//                                                $.each(itm.questionAnswerList[0].replyattachList,function(idx,itm){
//                                                    var name=itm.substring(itm.lastIndexOf("/")+1);
//                                                    htm+='<a class="font-blue" target="_blank" href="'+itm+'">'+name+'</a>&nbsp;';
//                                                });
//                                                htm+='</span></p>';
//                                            }
//                                        }

                                    }
                                    else {
                                        if (itm.status > 0 && (tasktype == 1 || tasktype == 2)) {
                                            htm += '<td>已查看</td>';
                                        } else {
                                            htm += '<td></td>';
                                        }
                                    }
                                    if (itm.status > 0)
                                        htm += '<td><span class="ico12" title="完成"></span></td>';
                                    else
                                        htm += '<td><span class="ico24" title="进行中"></span></td>';
                                    htm += '</tr>';
                                }

                            });
                        });
                        if(signnature==0){
                            htm+='<tr><td colspan="5">暂无数据!</td></tr>';
                        }
                        htm += '</table>';
                    });
                } else {
                    htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                    htm += '<colgroup span="4" class="w190"></colgroup>';
                    htm += '<colgroup class="w180"></colgroup>';
                    if(classid==0)
                        htm += '<colgroup class="w180"></colgroup>';
                    htm += '<tr>';
                    if(classid==0)
                        htm += '<th>班级</th>';
                    htm += '<th>学号</th>';
                    htm += '<th>姓名</th>';
                    htm += '<th>学习时间</th>';
                    htm += '<th>作答内容</th>';
                    htm += '<th>是否完成</th>';
                    htm += '</tr>';
                    $.each(rmsg.objList[1], function (idx, itm) {
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<td>' + itm.clsname + '</td>';
                        if(typeof(itm.userinfo.stuNo)=='undefined')
                            htm += '<td>' + '--' + '</td>';
                        else
                            htm += '<td>' + itm.userinfo.stuNo + '</td>';
                        htm += '<td>' + itm.userinfo.stuname + '</td>';
                        if (typeof(itm.ctimeString) != 'undefined')
                            htm += '<td>' + itm.ctimeString + '</td>';
                        else
                            htm += '<td></td>';
                        if (typeof(itm.answercontent) != 'undefined') {
                            htm += '<td>' + itm.answercontent;
                            if(typeof(itm.replyattachList)!="undefined"&&itm.replyattachList.length>0){
                                if(itm.replyattachList.length>0){
                                    //htm+='<p><span><br>答题附件：';
                                    $.each(itm.replyattachList,function(index,itmObj){
                                        var name=(index+1)+itmObj.substring(itmObj.lastIndexOf("."));
                                        var url = itmObj.substring(0,itmObj.lastIndexOf("."))+"_1"+itmObj.substring(itmObj.lastIndexOf("."));
                                        htm+='<a class="font-blue" target="_blank" href="'+itmObj+'">'+name+'</a>&nbsp;';
                                    });
                                    //htm+='</span></p>';
                                }
                            }
                            htm+='</td>';
//                                        if(itm..replyattach.length>0&&typeof(itm.replyattachList)!="undefined"){
//                                            if(itm.questionAnswerList[0].replyattachList.length>0){
//                                                htm+='<p><span><br>答题附件：';
//                                                $.each(itm.questionAnswerList[0].replyattachList,function(idx,itm){
//                                                    var name=itm.substring(itm.lastIndexOf("/")+1);
//                                                    htm+='<a class="font-blue" target="_blank" href="'+itm+'">'+name+'</a>&nbsp;';
//                                                });
//                                                htm+='</span></p>';
//                                            }
//                                        }

                        }
                        else {
                            if (itm.status > 0 && (tasktype == 1 || tasktype == 2)) {
                                htm += '<td>已查看</td>';
                            } else {
                                htm += '<td></td>';
                            }
                        }
                        if (itm.status > 0)
                            htm += '<td><span class="ico12" title="完成"></span></td>';
                        else
                            htm += '<td><span class="ico24" title="进行中"></span></td>';
                        htm += '</tr>';
                    });
                    htm += '</table>';
                }
            } else {
                htm += '<table><tr><td>暂无数据!</td></tr></table>';
            }
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                $("#dv_nocomplete_data").html('');
                $.each(rmsg.objList[3], function (idx, itm) {
                    if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                        if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        else {
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }
                    else {
                        $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                        $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                        $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                    }

                });
            }
            $("#notcomplete").html(rmsg.objList[3].length)
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                //$("#sendMsg").html('<a href="javascript:doSendTaskMsg(' + taskid + ',' + classid + ')"  class="an_public3">发提醒</a>');

            }
            $("#mainTbl").hide();
            $("#mainTbl").html(htm);
            $("#mainTbl").show("");
        }
    });
}

/**
 * 查询学生完成情况----主观题
 * @return
 */
function cjloadStuPerformance(classid, classtype) {
    $("#a_class_" + classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");

    if (typeof(classid) == 'undefined' || isNaN(classid)) {
        alert('异常错误，参数错误!111');
        return;
    }
    if (typeof(taskid) == 'undefined' || taskid.length < 1) {
        alert('异常错误，参数错误!222');
        return;
    }
    var param = {};
    param.subjectid=g_subjectid;
    param.taskid = taskid;
    if (classid != null && classid.toString().length > 0) {
        param.classid = classid;
        param.classtype = classtype;
    } else {
        param.classid = 0;
        classid=0;
        param.classtype = 0;
    }
    $.ajax({
        url: 'task?cjloadStuPerformance',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.objList[0] != null && rmsg.objList[0].length > 0) {
                var totalnum = 0;
                var rightnum = 0;
                var finishnum = 0;
                $.each(rmsg.objList[0], function (idx, itm) {
                    totalnum += parseInt(itm.TOTALNUM);
                    rightnum += parseInt(itm.RIGHTNUM);
                    finishnum += parseInt(itm.FINISHNUM);

                });
                var fn = parseFloat(parseInt(finishnum)) / parseInt(totalnum) * 100;
                var finishhtml = fn.toFixed(2);
                finishhtml += "%";
                $("#finishnum").html(finishhtml);
            } else {
                $("#finishnum").html("0");
            }
            var htm = '';
            var quesCol = '';
            var quesNum = 0;
            if(rmsg.objList[4]!=null){
                $.each(rmsg.objList[4], function (ix, im) {
                    quesCol+='<th>'+(ix+1)+'</th>';
                    quesNum++;
                });
            }
            if (rmsg.objList[1] != null && typeof rmsg.objList[1] != 'undefined' && rmsg.objList[1].length > 0) {
                if (rmsg.objList[2] != null && rmsg.objList[2].length > 0) {
                    $.each(rmsg.objList[2], function (ix, im) {
                        htm+='<p><strong>'+im.groupname+'</strong></p><div class="jxxt_zhuanti_rw_tongji_chengjuan">';
                        htm += '<table border="0" id="recordList_'+im.groupid+'" cellpadding="0" cellspacing="0" class="public_tab2"';
                        if (classid != null && classid.toString().length > 0) {
                           htm+=' style="width:'+(330+(quesNum*50))+'px"';
                        } else {
                            htm+=' style="width:'+(430+(quesNum*50))+'px"';
                        }
                        htm+='>';
                        if(classid==0)
                            htm+='<colgroup class="w100"></colgroup>';
                        htm += '<colgroup class="w80"></colgroup>';
                        htm += '<colgroup class="w120"></colgroup>';
                        htm += '<colgroup span="'+(1+quesNum)+'" class="w50"></colgroup>';
                        htm += '<colgroup class="w80"></colgroup>';
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<th>班级</th>';
                        htm += '<th>姓名</th>';
                        htm += '<th>学习时间</th>';
                        htm += '<th>得分</th>';
                        htm += quesCol;
                        htm += '<th>查看试卷</th>';
                        htm += '</tr>';
                        var signnature = 0;
                        $.each(rmsg.objList[1], function (idx, itm) {
                            $.each(im.tpgroupstudent2, function (i, m) {
                                if (m.userid == itm[1]) {signnature++;
                                    if(idx%2==0)
                                        htm+='<tr class="trbg1">';
                                    else
                                        htm += '<tr>';
                                    if(classid==0)
                                        htm += '<td>' + itm[4] + '</td>';
                                    htm += '<td>' + itm[0] + '</td>';
                                    var studytime = itm[2];
                                    studytime=studytime.substring(0,16);
                                    htm += '<td>'+studytime+'</td>';
                                    htm += '<td id="td_0">'+itm[3]+'</td>';
                                    $.each(rmsg.objList[4], function (quesnum, ques) {
                                        var answer='';
                                        if(classid==0){
                                            answer = itm[quesnum+5];
                                        }else{
                                            answer = itm[quesnum+4];
                                        }
                                        var answers = answer.split("|");
                                        if(answers[2]==1){
                                            if(answers[1]==1){
                                                htm+='<td id="td_'+(quesnum+1)+'">&radic;';
                                                htm+='<input type="hidden" value="1"/>';
                                                htm+='</td>';
                                            }else{
                                                htm+='<td id="td_'+(quesnum+1)+'"><span class="font-red">&times;</span>';
                                                htm+='<input type="hidden" value="1"/>';
                                                htm+='</td>';
                                            }
                                        }else{
                                            htm+='<td id="td_'+(quesnum+1)+'">'+answers[0];
                                            htm+='<input type="hidden" value="2"/>';
                                            htm+='</td>';
                                        }
                                    });
                                    htm+='<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid='+taskid+'&userid='+itm[1]+'&flag=1">查看卷面</a></td>';
                                    htm += '</tr>';
                                }

                            });
                        });
                        if(signnature==0){
                            htm+='<tr><td colspan="15">暂无数据!</td></tr>';
                        }
                        htm += '</table>';
                        htm +='</div>';
                    });
                } else {
                    htm+='<div class="jxxt_zhuanti_rw_tongji_chengjuan">';
                    htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2"';
                    if (classid != null && classid.toString().length > 0) {
                        htm+=' style="width:'+(330+(quesNum*50))+'px"';
                    } else {
                        htm+=' style="width:'+(430+(quesNum*50))+'px"';
                    }
                    htm+='>';
                    if(classid==0)
                        htm+='<colgroup class="w100"></colgroup>';
                    htm += '<colgroup class="w80"></colgroup>';
                    htm += '<colgroup class="w120"></colgroup>';
                    htm += '<colgroup span="'+(1+quesNum)+'" class="w50"></colgroup>';
                    htm += '<colgroup class="w80"></colgroup>';
                    htm += '<tr>';
                    if(classid==0)
                        htm += '<th>班级</th>';
                    htm += '<th>姓名</th>';
                    htm += '<th>学习时间</th>';
                    htm += '<th>得分</th>';
                    htm += quesCol;
                    htm += '<th>查看试卷</th>';
                    htm += '</tr>';
                    $.each(rmsg.objList[1], function (idx, itm) {
                        if(idx%2==0)
                            htm+='<tr class="trbg1">';
                        else
                            htm += '<tr>';
                        if(classid==0)
                            htm += '<td>' + itm[4] + '</td>';
                        htm += '<td>' + itm[0] + '</td>';
                        var studytime = itm[2];
                        studytime=studytime.substring(0,16);
                        htm += '<td>'+studytime+'</td>';
                        htm += '<td id="td_0">'+itm[3]+'</td>';
                        $.each(rmsg.objList[4], function (quesnum, ques) {
                            var answer='';
                            if(classid==0){
                                answer = itm[quesnum+5];
                            }else{
                                answer = itm[quesnum+4];
                            }
                            var answers = answer.split("|");
                            if(answers[2]=="1"){
                                if(answers[1]=="1"){
                                    htm+='<td id="td_'+(quesnum+1)+'">&radic;';
                                    htm+='<input type="hidden" value="1"/>';
                                    htm+='</td>';
                                }else{
                                    htm+='<td id="td_'+(quesnum+1)+'"><span class="font-red">&times;</span>';
                                    htm+='<input type="hidden" value="1"/>';
                                    htm+='</td>';
                                }
                            }else{
                                htm+='<td id="td_'+(quesnum+1)+'">'+answers[0];
                                htm+='<input type="hidden" value="2"/>';
                                htm+='</td>';
                            }
                        });
                        htm+='<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid='+taskid+'&userid='+itm[1]+'&flag=1">查看卷面</a></td>';
                        htm += '</tr>';
                    });
                    htm += '</table>';
                    htm +='</div>';
                }
            } else {
                htm+='<div class="jxxt_zhuanti_rw_tongji_chengjuan">';
                htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2"';
                if (classid != null && classid.toString().length > 0) {
                    htm+=' style="width:'+(330+(quesNum*50))+'px"';
                } else {
                    htm+=' style="width:'+(430+(quesNum*50))+'px"';
                }
                htm+='>';
                if(classid==0)
                    htm+='<colgroup class="w100"></colgroup>';
                htm += '<colgroup class="w80"></colgroup>';
                htm += '<colgroup class="w120"></colgroup>';
                htm += '<colgroup span="'+(1+quesNum)+'" class="w50"></colgroup>';
                htm += '<colgroup class="w80"></colgroup>';
                htm += '<tr>';
                if(classid==0)
                    htm += '<th>班级</th>';
                htm += '<th>姓名</th>';
                htm += '<th>学习时间</th>';
                htm += '<th>得分</th>';
                htm += quesCol;
                htm += '<th>查看试卷</th>';
                htm += '</tr>';
                htm+='<tr><td colspan="15">暂无数据!</td></tr></table></div>';
            }
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                $("#dv_nocomplete_data").html('');
                $.each(rmsg.objList[3], function (idx, itm) {
                    if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                        if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        else {
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }
                    else {
                        $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                        $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                        $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                    }

                });
            }
            $("#notcomplete").html(rmsg.objList[3].length)
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                //$("#sendMsg").html('<a href="javascript:doSendTaskMsg(' + taskid + ',' + classid + ')"  class="an_public3">发提醒</a>');

            }
            $("#mainTbl").hide();
            $("#mainTbl").html(htm);
            $("#mainTbl").show();



            writeUnderTr(classid,rmsg.objList[4]);
        }
    });
}

function writeUnderTr(classid,quesArray){
    $("table").each(function(i,tab){
        var htm='';
        htm += '<tr>';
        if(classid==0){
            htm+='<td colspan="3">正确率（平均分）</td>';
        }else{
            htm+='<td colspan="2">正确率（平均分）</td>';
        }

        var scoreTdArray= $("#"+tab.id+" td[id='td_0']");
        var totalScore = 0;
        $.each(scoreTdArray,function(idx,itm){
            if($(itm).text()!='--')
                totalScore+=parseFloat($(itm).text());
        });
        var totalNum = $(tab).find("tr").length-1;
        htm+='<td>'+(totalScore/totalNum).toFixed(2)+'</td>';
        $.each(quesArray,function(idx,itm){
            var scoreTdArray2= $("#"+tab.id+" td[id='td_"+(idx+1)+"']");
            var totalScore2 = 0;
            var rightNum = 0;
            var type = 0;
            $.each(scoreTdArray2,function(ix,im){
                type = $("#"+im.id+" input[type='hidden']").val();
                if(type=="1"){
                    if($(im).text().Trim()=="&radic;"||$(im).text().Trim()=="√"){
                        rightNum+=1;
                    }
                }else{
                    if($(im).text()!='--')
                        totalScore2+=parseFloat($(im).text());
                }
            });
            if(type=="1"){
                htm+='<td>'+(rightNum/totalNum*100).toFixed(2)+'%</td>';
            }else{
                htm+='<td>'+(totalScore2/totalNum).toFixed(2)+'</td>';
            }
        });
        htm+='<td></td>';
        htm+='</tr>';
        $(tab).append(htm);
    });
}

/**
 * 查询学生试卷完成情况
 * @return
 */
function loadPaperPerformance(classid, tasktype, paperid, classtype) {
    $("#a_class_" + classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");

    if (typeof(classid) == 'undefined' || isNaN(classid)) {
        alert('异常错误，参数错误! \n\nClassd is undefined!');
        return;
    }
    if (typeof(taskid) == 'undefined' || taskid.toString().length < 1) {
        alert('异常错误，参数错误! \n\n Taskid is undefined!');
        return;
    }
    var param = {};
    param.taskid = taskid;
    if (classid != null && classid.toString().length > 0 && classid != '0') {
        param.classid = classid;
        param.classtype = classtype;
    } else {
        param.classid = 0;
        classid=0;
        param.classtype = 0;
    }
    if (tasktype == 4 || tasktype == 5) {
        param.paperid = paperid;
    }
    if (orderstr.length > 0)
        param.orderstr = orderstr;
    $.ajax({
        url: 'task?loadPaperPerformance',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.objList[0] != null && rmsg.objList[0].length > 0) {
                var totalnum = 0;
                var rightnum = 0;
                var finishnum = 0;
                $.each(rmsg.objList[0], function (idx, itm) {
                    totalnum += parseInt(itm.TOTALNUM);
                    rightnum += parseInt(itm.RIGHTNUM);
                    finishnum += parseInt(itm.FINISHNUM);

                });
                var fn = parseFloat(parseInt(finishnum)) / parseInt(totalnum) * 100;
                fn=isNaN(fn)?0:fn;
                var finishhtml = fn.toFixed(2);
                finishhtml += "%";
                $("#finishnum").html(finishhtml);
            } else {
                $("#finishnum").html("0");
            }
            var htm = '';
            var css = cssObj == 1 ? 'ico48a' : 'ico48b';
            if (rmsg.objList[1] != null && typeof rmsg.objList[1] != 'undefined' && rmsg.objList[1].length > 0) {
                if (rmsg.objList[2] != null && rmsg.objList[2].length > 0) {
                    $.each(rmsg.objList[2], function (ix, im) {
                        htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                        if (tasktype == 4)
                            if(classid==0)
                                htm += '<colgroup class="w190"></colgroup><colgroup class="w100" span="2"></colgroup><colgroup class="w150"></colgroup><colgroup class="w100" span="4"></colgroup>';
                            else
                                htm += '<colgroup class="w130" span="2"></colgroup><colgroup class="w160"></colgroup><colgroup class="w130" span="4"></colgroup>';
                        else if (tasktype == 5||tasktype==6)
                            if(classid==0)
                                htm += '<colgroup class="w240"></colgroup><colgroup class="w110" span="2"></colgroup><colgroup class="w150"></colgroup><colgroup class="w110" span="3"></colgroup>';
                            else
                                htm += '<colgroup class="w150" span="2"></colgroup><colgroup class="w190"></colgroup><colgroup class="w150" span="3"></colgroup>';
                        htm += '<caption>' + im.groupname + '</caption>';
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<th>班级</th>';
                        htm += '<th>学号</th>';
                        htm += '<th>姓名</th>';
                        htm += '<th>学习时间</th>';
                        if(tasktype==6){
                            htm += '<th>微视频学习</th>';
                            htm += '<th>试卷</th>';
                        }else
                            htm += '<th>是否完成</th>';
                        if(tasktype!=6)
                            htm += '<th>得分</th>';
                        if (tasktype == 4)
                            htm += '<th>排名<a href="javascript:void(0);"  onclick="DataSort(\'t.rank+0\',this)"  class="' + css + '"></a></th>';
                        htm += '<th>查看试卷</th>';
                        htm += '</tr>';
                        $.each(rmsg.objList[1], function (idx, itm) {
                            $.each(im.tpgroupstudent2, function (i, m) {
                                if (typeof itm!='undefined'&&typeof m!='undefined'&&im.groupname== m.groupname&& m.userid == itm.userinfo.userid){
                                    htm += '<tr>';
                                    if(classid==0)
                                        htm += '<td>' + itm.clsname + '</td>';
                                    htm += '<td>' + (typeof itm.userinfo.stuNo=='undefined'?'--':itm.userinfo.stuNo) + '</td>';
                                    htm += '<td>' + itm.userinfo.stuname + '</td>';
                                    if (typeof(itm.ctimeString) != 'undefined')
                                        htm += '<td>' + itm.ctimeString + '</td>';
                                    else
                                        htm += '<td></td>';
                                    if(tasktype==6){
                                        if (itm.creteriatype==2){
                                            htm += '<td><span class="ico12" title="完成"></span></td>';
                                            htm += '<td><span class="ico12" title="完成"></span></td>';
                                        }else{
                                            htm += '<td><span class="ico12" title="完成"></span></td>';
                                            htm += '<td><span>---</span></td>';
                                        }

                                    }else{
                                        if (itm.status > 0)
                                            htm += '<td><span class="ico12" title="完成"></span></td>';
                                        else
                                            htm += '<td><span class="ico24" title="进行中"></span></td>';
                                        if (typeof(itm.score) != 'undefined') {
                                            htm += '<td>' + parseFloat(itm.score) + '</td>';
                                        } else {
                                            htm += '<td>---</td>';
                                        }
                                    }
                                    if (tasktype == 4){
                                        if(typeof itm.rank != 'undefined' && typeof(itm.score) != 'undefined')
                                            htm += '<td>' + parseInt(itm.rank) + '</td>';
                                        else
                                            htm += '<td>---</td>';
                                    }
                                    if(tasktype==6&&itm.creteriatype==2)
                                        htm += '<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid=' + itm.taskid + '&userid=' + itm.uid + '&flag=1">查看卷面</a></td>'
                                    else if(tasktype==6&&itm.creteriatype==1)
                                        htm +='<td>---</td>'
                                    else
                                        htm += '<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid=' + itm.taskid + '&userid=' + itm.uid + '&flag=1">查看卷面</a></td>'
                                    htm += '</tr>';
                                }else{
                                    htm += '<tr><td colspan="7">暂无数据!</td></tr>';
                                }
                            });

                        });
                        htm += '</table>';
                    });
                } else {
                    htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                    if (tasktype == 4)
                        if(classid==0)
                            htm += '<colgroup class="w190"></colgroup><colgroup class="w100" span="2"></colgroup><colgroup class="w150"></colgroup><colgroup class="w100" span="4"></colgroup>';
                        else
                            htm += '<colgroup class="w130" span="2"></colgroup><colgroup class="w160"></colgroup><colgroup class="w130" span="4"></colgroup>';
                    else if (tasktype == 5||tasktype==6)
                        if(classid==0)
                            htm += '<colgroup class="w240"></colgroup><colgroup class="w110" span="2"></colgroup><colgroup class="w150"></colgroup><colgroup class="w110" span="3"></colgroup>';
                        else
                            htm += '<colgroup class="w150" span="2"></colgroup><colgroup class="w190"></colgroup><colgroup class="w150" span="3"></colgroup>';
                    htm += '<tr>';
                    if(classid==0)
                        htm += '<th>班级</th>';
                    htm += '<th>学号</th>';
                    htm += '<th>姓名</th>';
                    htm += '<th>学习时间</th>';
                    if(tasktype==6){
                        htm += '<th>微视频学习</th>';
                        htm += '<th>试卷</th>';
                    }else
                        htm += '<th>是否完成</th>';
                    if(tasktype!=6)
                        htm += '<th>得分</th>';
                    if (tasktype == 4)
                        htm += '<th>排名<a href="javascript:void(0);"  onclick="DataSort(\'t.rank+0\',this)"  class="' + css + '"></a></th>';
                    htm += '<th>查看试卷</th>';
                    htm += '</tr>';
                    $.each(rmsg.objList[1], function (idx, itm) {
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<td>' + itm.clsname + '</td>';
                        htm += '<td>' + (typeof itm.userinfo.stuNo=='undefined'?'--':itm.userinfo.stuNo) + '</td>';
                        htm += '<td>' + itm.userinfo.stuname + '</td>';
                        if (typeof(itm.ctimeString) != 'undefined')
                            htm += '<td>' + itm.ctimeString + '</td>';
                        else
                            htm += '<td></td>';
                        if(tasktype==6){
                            if (itm.creteriatype==2){
                                htm += '<td><span class="ico12" title="完成"></span></td>';
                                htm += '<td><span class="ico12" title="完成"></span></td>';
                            }else{
                                htm += '<td><span class="ico12" title="完成"></span></td>';
                                htm += '<td><span>---</span></td>';
                            }

                        }else{
                            if (itm.status > 0)
                                htm += '<td><span class="ico12" title="完成"></span></td>';
                            else
                                htm += '<td><span class="ico24" title="进行中"></span></td>';
                            if (typeof(itm.score) != 'undefined') {
                                htm += '<td>' + parseFloat(itm.score) + '</td>';
                            } else {
                                htm += '<td>---</td>';
                            }
                        }

                        if (tasktype == 4){
                            if(typeof itm.rank != 'undefined' && typeof(itm.score) != 'undefined')
                                htm += '<td>' + parseInt(itm.rank) + '</td>';
                            else
                                htm += '<td>---</td>';
                        }
                        if(tasktype==6&&itm.creteriatype==2)
                            htm += '<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid=' + itm.taskid + '&userid=' + itm.uid + '&flag=1">查看卷面</a></td>'
                        else if(tasktype==6&&itm.creteriatype==1)
                            htm +='<td>---</td>'
                        else
                            htm += '<td><a class="font-darkblue" href="paperques?m=teaViewStuPaper&taskid=' + itm.taskid + '&userid=' + itm.uid + '&flag=1">查看卷面</a></td>'
                        htm += '</tr>';
                    });
                    htm += '</table>';
                }

            } else {
                htm += '<table><tr><td>暂无数据!</td></tr></table>';
            }


            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                $("#dv_nocomplete_data").html('');
                $.each(rmsg.objList[3], function (idx, itm) {
                    if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                        if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        else {
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }
                    else {
                        $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                        $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                        $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                    }

                });
            }
            $("#notcomplete").html(rmsg.objList[3].length)
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                //$("#sendMsg").html('<a href="javascript:doSendTaskMsg(' + taskid + ',' + classid + ')"  class="an_public3">发提醒</a>');

            }

            $("#mainTbl").hide();
            $("#mainTbl").html(htm);
            $("#mainTbl").show("");

            $("#mainTbl table").each(function(idx,itm){
                var obj=$(itm).find("td:has(a)");
                if(typeof obj=='object'&&obj.length>0){
                    $(itm).find("td:contains('暂无数据')").remove();
                }else{
                    $(itm).find("td:contains('暂无数据'):not(:first)").remove();
                }
            })

        }
    });
}




/**
 * 查询学生直播课完成情况
 * @return
 */
function loadLiveLessionPerformance(classid, tasktype, paperid, classtype) {
    $("#a_class_" + classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");

    if (typeof(classid) == 'undefined' || isNaN(classid)) {
        alert('异常错误，参数错误! \n\nClassd is undefined!');
        return;
    }
    if (typeof(taskid) == 'undefined' || taskid.toString().length < 1) {
        alert('异常错误，参数错误! \n\n Taskid is undefined!');
        return;
    }
    var param = {};
    param.taskid = taskid;
    if (classid != null && classid.toString().length > 0 && classid != '0') {
        param.classid = classid;
        param.classtype = classtype;
    } else {
        param.classid = 0;
        classid=0;
        param.classtype = 0;
    }
    if (tasktype == 4 || tasktype == 5) {
        param.paperid = paperid;
    }
    if (orderstr.length > 0)
        param.orderstr = orderstr;
    $.ajax({
        url: 'task?loadPaperPerformance',
        type: "post",
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统未响应，请稍候重试!');
        }, success: function (rmsg) {
            if (rmsg.objList[0] != null && rmsg.objList[0].length > 0) {
                var totalnum = 0;
                var rightnum = 0;
                var finishnum = 0;
                $.each(rmsg.objList[0], function (idx, itm) {
                    totalnum += parseInt(itm.TOTALNUM);
                    rightnum += parseInt(itm.RIGHTNUM);
                    finishnum += parseInt(itm.FINISHNUM);

                });
                var fn = parseFloat(parseInt(finishnum)) / parseInt(totalnum) * 100;
                fn=isNaN(fn)?0:fn;
                var finishhtml = fn.toFixed(2);
                finishhtml += "%";
                $("#finishnum").html(finishhtml);
            } else {
                $("#finishnum").html("0");
            }
            var htm = '';
            var css = cssObj == 1 ? 'ico48a' : 'ico48b';
            if (rmsg.objList[1] != null && typeof rmsg.objList[1] != 'undefined' && rmsg.objList[1].length > 0) {
                if (rmsg.objList[2] != null && rmsg.objList[2].length > 0) {
                    $.each(rmsg.objList[2], function (ix, im) {
                        htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                        if(classid==0)
                            htm += '<colgroup class="w240"></colgroup><colgroup class="w150" span="2"></colgroup><colgroup class="w150" span="2">';
                        else
                            htm += '<colgroup class="w190" span="2"></colgroup><colgroup class="w190" span="2">';
                        htm += '<caption>' + im.groupname + '</caption>';
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<th>班级</th>';
                        htm += '<th>学号</th>';
                        htm += '<th>姓名</th>';
                        htm += '<th>学习时间</th>';
                        htm += '<th>是否完成</th>';
                        htm += '</tr>';
                        if(rmsg.objList[1].length>0){
                            $.each(rmsg.objList[1], function (idx, itm) {
                                $.each(im.tpgroupstudent2, function (i, m) {
                                    if (typeof(m)!='undefined'&&typeof itm!='undefined'&&m.userid == itm.userinfo.userid) {
                                        htm += '<tr>';
                                        if(classid==0)
                                            htm += '<td>' + itm.clsname + '</td>';
                                        htm += '<td>' + (typeof itm.userinfo.stuNo=='undefined'?'--':itm.userinfo.stuNo) + '</td>';
                                        htm += '<td>' + itm.userinfo.stuname + '</td>';
                                        if (typeof(itm.ctimeString) != 'undefined')
                                            htm += '<td>' + itm.ctimeString + '</td>';
                                        else
                                            htm += '<td></td>';
                                        if (itm.status > 0)
                                            htm += '<td><span class="ico12" title="完成"></span></td>';
                                        else
                                            htm += '<td><span class="ico24" title="进行中"></span></td>';
                                        htm += '</tr>';
                                    }else{
                                        htm+='<tr><td colspan="4">暂无数据!</td></tr>';
                                    }
                                });
                            });
                        }

                        htm += '</table>';
                    });
                } else {
                    htm += '<table border="0" id="recordList" cellpadding="0" cellspacing="0" class="public_tab2">';
                    if(classid==0)
                        htm += '<colgroup class="w240"></colgroup><colgroup class="w150" span="2"></colgroup><colgroup class="w150" span="2">';
                    else
                        htm += '<colgroup class="w190" span="2"></colgroup><colgroup class="w190" span="2">';
                    htm += '<tr>';
                    if(classid==0)
                        htm += '<th>班级</th>';
                    htm += '<th>学号</th>';
                    htm += '<th>姓名</th>';
                    htm += '<th>学习时间</th>';
                    htm += '<th>是否完成</th>';
                    htm += '</tr>';
                    $.each(rmsg.objList[1], function (idx, itm) {
                        htm += '<tr>';
                        if(classid==0)
                            htm += '<td>' + itm.clsname + '</td>';
                        htm += '<td>' + (typeof(itm.userinfo.stuNo)=='undefined'?"--":itm.userinfo.stuNo) + '</td>';
                        htm += '<td>' + itm.userinfo.stuname + '</td>';
                        if (typeof(itm.ctimeString) != 'undefined')
                            htm += '<td>' + itm.ctimeString + '</td>';
                        else
                            htm += '<td></td>';
                        if (itm.status > 0)
                            htm += '<td><span class="ico12" title="完成"></span></td>';
                        else
                            htm += '<td><span class="ico24" title="进行中"></span></td>';
                        htm += '</tr>';
                    });
                    htm += '</table>';
                }

            } else {
                htm += '<table><tr><td>暂无数据!</td></tr></table>';
            }


            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
                $("#dv_nocomplete_data").html('');
                $.each(rmsg.objList[3], function (idx, itm) {
                    if ($('p[id="p_stu_' + itm.classid + '"]').length > 0)
                        if ($('ul[id="ul_stu_' + itm.classid + '"]').length > 0)
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        else {
                            $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                            $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                        }
                    else {
                        $("#dv_nocomplete_data").append('<p id="p_stu_' + itm.classid + '"><strong>' + itm.taskobjname + '</strong><span class="font-red"></span></p>');
                        $("#dv_nocomplete_data").append('<ul id="ul_stu_' + itm.classid + '"></ul>');
                        $('ul[id="ul_stu_' + itm.classid + '"]').append('<li><input type="hidden" name="hd_uid" value="' + itm.userid + '" />' + itm.realname + '</li>');
                    }

                });
            }
            $("#notcomplete").html(rmsg.objList[3].length)
            if(rmsg.objList[3]!=null&&rmsg.objList[3].length>0){
               // $("#sendMsg").html('<a href="javascript:doSendTaskMsg(' + taskid + ',' + classid + ')"  class="an_public3">发提醒</a>');

            }

            $("#mainTbl").hide();
            $("#mainTbl").html(htm);
            $("#mainTbl").show("");

            if($("#mainTbl td:contains('暂无数据')").length>1){
                $("#mainTbl td:contains('暂无数据'):not(:first)").remove();
            }

            $("#mainTbl table").each(function(idx,itm){
                var obj=$(itm).find("td:has(span)");
                if(typeof obj=='object'&&obj.length>0){
                    $(itm).find("td:contains('暂无数据')").remove();
                }else{
                    $(itm).find("td:contains('暂无数据'):not(:first)").remove();
                }
            })
        }
    });
}



