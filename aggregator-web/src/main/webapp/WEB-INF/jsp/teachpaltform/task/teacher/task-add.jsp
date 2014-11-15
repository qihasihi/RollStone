<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <%--<script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-1.9.1.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" />--%>
    <%--<script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>--%>

    <%--<link rel="stylesheet" type="text/css" href="<%=basePath %>comment/comment_star.css"/>--%>
    <%--<script type="text/javascript" src="<%=basePath %>comment/commentScorePlug.js"></script>--%>
    <%--<script type="text/javascript" src="js/resource/new_resbase.js"></script>--%>


    <%--<script type="text/javascript"--%>
            <%--src="<%=basePath %>js/teachpaltform/resource.js"></script>　--%>

    <%--<script type="text/javascript"--%>
            <%--src="fancybox/jquery-1.6.2.min.js"></script>--%>

    <script type="text/javascript"
            src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript"
            src="fancybox/jquery.fancybox-1.3.4.js"></script>

    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css"/>

    <script type="text/javascript"
            src="<%=basePath %>js/teachpaltform/tptask.js"></script>

    <script type="text/javascript">
        var courseid="${param.courseid}";
        var gradeid="${gradeid}";
        var subjectid="${subjectid}";
        var materialid="${materialid}";
        var questype="${param.questype}";
        var objectiveQuesCount="${objectiveQuesCount}";
        var fancyboxObj;
        $(function(){


            <c:if test="${!empty isWXCLS}">
                showModel('dv_show_model');
            </c:if>



            fancyboxObj=$("#a_click").fancybox({
                'onClosed':function(){
                    $("#dv_content").hide();
                }

            });         
            $("#task_type").val(${param.tasktype});
            if(questype.length<1)
                questype=3
            //任务类型
            changeTaskType("${param.tasktype}","${param.taskvalueid}");

            $("#time_rdo0").attr("checked",true);

            <c:if test="${empty hasVideo}">
            $("#task_type option[value=6]").remove();
            </c:if>
            <c:if test="${empty hasPaper}">
             $("#task_type option[value=4]").remove();
            </c:if>
            <c:if test="${empty hasQuestion}">
            $("#task_type option[value=5]").remove();
            </c:if>



            $("input[name='time_rdo']").bind("click",function(){
                if($(this).val()==1){
                    $("#list_ct p").hide();

                    $("input[name='ck_cls']:checked").each(function(idx,itm) {
                        $("#p_"+$(this).val()).show();
                    });
                    $("input[name='ck_group']:checked").each(function(idx,itm) {
                        var p=$(itm).parent().parent("ul");
                        var tmpId=p.attr("id").substring(p.attr("id").lastIndexOf('_')+1);
                        $("#p_"+tmpId).show();
                    });

                    $("#all_ct").hide();
                    $("#list_ct").show('fast');
                }else{
                    $("#list_ct").hide();
                    $("#all_ct").show('fast');
                }
            });

            $("input[name='ck_cls']").bind("click",function(){
                if($(this).attr("checked")){
                    $("#p_"+$(this).val()).show();
                }else{
                    $("#p_"+$(this).val()).hide();
                    $("#b_time_"+$(this).val()+"").val("");
                    $("#e_time_"+$(this).val()+"").val("");
                }
            });


            $("input[name='ck_group']").bind("click",function(){
                var p=$(this).parent().parent("ul");
                var tmpId=p.attr("id").substring(p.attr("id").lastIndexOf('_')+1);
                var len=$('#p_group_'+tmpId+' input:checked').length;
                if(len>0)
                    $("#p_"+tmpId).show();
                else{
                    $("#p_"+tmpId).hide();
                    $("#b_time_"+tmpId+"").val("");
                    $("#e_time_"+tmpId+"").val("");
                }
                var unLen=$('#p_group_'+tmpId+' input').length;
                if(unLen>0)
                    $("#p_ck_"+tmpId+"").attr("checked",len==0?false:len==unLen);
            });


            $("input[name='p_ck_g']").each(function(idx,itm){
                var clsid=$(itm).val();
                $(itm).bind("click",function(){
                    $("#p_group_"+clsid+" input[type='checkbox']").attr("checked",$(itm).attr("checked"));
                    $('input[id="ckb_'+clsid+'"]').attr("checked",false);
                });
            })

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
            }else if(tasktype=="4"){
                queryPaper(courseid,'tr_task_obj',4,taskvalueid);
            }else if(tasktype=="5"){
                queryPaper(courseid,'tr_task_obj',5,taskvalueid);
            }else if(tasktype=="6"){
                queryMicView(courseid,'tr_task_obj',6,taskvalueid)
            }else if(tasktype=="10"){
                queryLiveLession(courseid,'tr_task_obj',10,taskvalueid)
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
        function toUpdCoursePage(){
            var url= "teachercourse?m=toSaveOrUpdate&courseid="+courseid+"&gradeid="+gradeid+"&subjectid="+subjectid;
            if(materialid.length>0){
                url+="&materialid="+materialid;
            }
            window.location.href=url;
        }
    </script>


</head>
<body>


<div class="public_float public_float450" id="dv_show_model" style="display: none;">
    <p class="float_title"><strong>提示</strong></p>
    <p class="float_tishi">当前班级类型为<span class="font-red">网校班级</span>，不支持在电脑上添加任务。</p>
</div>

<input type="hidden" id="returnValue"/>
<div class="subpage_head">
    <span class="back"><a  href="javascript:history.go(-1)">返回</a></span>
    <span class="ico55"></span><strong>添加任务</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w120"/>
        <col class="w700"/>
        <input type="hidden" id="resource_type"/>
        <input type="hidden" id="remote_type"/>
        <input type="hidden" id="resource_name"/>
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

        <a id="a_click" href="#dv_content"></a>
        <div id="dv_content"  style="display: none;"></div>

        <tr id="tr_ques_obj" style="display: none">
            <th><span class="ico06"></span>选择试题：</th>
            <td class="font-black">
                <a href="javascript:loadTaskElement(3)"  class="font-darkblue">>>&nbsp;选择试题</a>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showDialogPage(3)" class="font-darkblue">>>&nbsp;添加试题</a>--%>
                <div class="jxxt_zhuanti_add_shiti"  id="tb_ques" style="overflow-y: auto;display:none;">
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
            <th><span class="ico06"></span>任务对象：</th>
            <td>
                <c:if test="${!empty courseclassList and courseclassList[0].classid ne 0}">
                    <c:forEach var="cc" items="${courseclassList}" varStatus="idx">

                        <!-- <p class="f_right w410">
                        <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" class="public_input w140" />~
                        <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly" id="e_time_${cc.classid}" type="text"class="public_input w140" />
                        </p> -->
                        <p class="font-black"><input name="ck_cls" data-bind="${cc.classtype}" type="checkbox"   onclick="selectClassObj(this,'${cc.classid }')" value="${cc.classid }" id="ckb_${cc.classid}" /> ${cc.classgrade }${cc.classname }</p>


                        <!-- <c:if test="${idx.index!=0}">
                        <p class="f_right w410">
                        <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" class="w140" />~
                        <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"  id="e_time_${cc.classid}" type="text" class="w140" />
                        </p>
                        <p class="font-black"><input name="ck_cls" type="checkbox"  onclick="selectClassObj(this,'${cc.classid }')" value="${cc.classid }" id="ckb_${cc.classid}" /> ${cc.classgrade }${cc.classname }</p>
                    </c:if> -->
                        <p class="font-black" style="margin: 15px 0;padding: 0 0 0 24px;"><input type="checkbox" value="${cc.classid}" name="p_ck_g" id="p_ck_${cc.classid}"/>选中所有小组</p>
                        <ul class="public_list3" id="p_group_${cc.classid }">

                        </ul>
                    </c:forEach>
                    <p class="font-darkblue"><a href="group?m=toGroupManager&classid=${courseclassList[0].classid}&classtype=${courseclassList[0].classtype}&gradeid=${gradeid}&subjectid=${subjectid}"  target="_blank">&gt;&gt;&nbsp;小组管理</a></p>
                </c:if>

                <c:if test="${empty courseclassList or courseclassList[0].classid==0}">
                    <a class="font-blue" href="javascript:toUpdCoursePage()">请设置任务对象!</a>
                </c:if>
            </td>
        </tr>

        <tr>
            <th><span class="ico06"></span>任务时间：</th>
            <td class="font-black">
                <p><input id="time_rdo0" name="time_rdo" type="radio" value="0"/>
                    所有班级一致&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <c:if test="${fn:length(courseclassList)>1}">
                    <input id="time_rdo1"  name="time_rdo" value="1" type="radio"/>不一致</p>
                </c:if>
                <div id="all_ct" class="jxxt_zhuanti_add_time">
                    <p>
                        <span class="f_right">
                            <%Integer dctype=1;%>
                            <c:forEach var="cc" items="${ courseclassList}">
                                <c:if test="${cc.dctype eq 3}">
                                    <%dctype=3;%>
                                </c:if>
                            </c:forEach>

                    <%if(dctype==3){%>
                        <input placeholder="设置开始时间" class="w140"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'all_class_etime\',{H:-1})}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="all_class_btime"  name="all_classtime" type="text" /> - <input placeholder="设置结束时间" class="w140" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'all_class_btime\',{H:1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'${courseclassList[0].begintimeString}'})" readonly="readonly" id="all_class_etime"  name="all_classtime" type="text" />
                    <%}else{%>
                        <input placeholder="设置开始时间" class="w140"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'all_class_etime\',{H:-1})}',startDate:'${courseclassList[0].begintimeString}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="all_class_btime"  name="all_classtime" type="text" /> - <input placeholder="设置结束时间" class="w140" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'all_class_btime\',{H:1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'${courseclassList[0].begintimeString}'})" readonly="readonly" id="all_class_etime"  name="all_classtime" type="text" />
                    <%}%>

                        </span>&nbsp;&nbsp;&nbsp;所有班级：
                    </p>
                </div>
                <div id="list_ct" style="display:none" class="jxxt_zhuanti_add_time">
                    <c:forEach var="cc" items="${ courseclassList}">
                        <p id="p_${cc.classid}"  style="display:none">
                            <c:if test="${cc.dctype eq 3}">
                                <span class="f_right"><input placeholder="设置开始时间" class="w140" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'e_time_${cc.classid}\',{H:-1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" /> - <input placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'b_time_${cc.classid}\',{H:1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'${cc.begintimeString}'})" readonly="readonly" id="e_time_${cc.classid}" type="text" class="w140" />
                            </c:if>

                            <c:if test="${cc.dctype ne 3}">
                                <span class="f_right"><input placeholder="设置开始时间" class="w140" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'e_time_${cc.classid}\',{H:-1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'${cc.begintimeString}',alwaysUseStartDate:true})" readonly="readonly"   id="b_time_${cc.classid}"  name="b_time" type="text" /> - <input placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'b_time_${cc.classid}\',{H:1})}',dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'${cc.begintimeString}'})" readonly="readonly" id="e_time_${cc.classid}" type="text" class="w140" />
                            </c:if>
                            </span>&nbsp;&nbsp;&nbsp;${cc.classgrade}${cc.classname}：
                        </p>
                    </c:forEach>

                </div>
            </td>
        </tr>


        <tr>
            <th><span class="ico06"></span>完成标准：</th>
            <td class="font-black" id="td_criteria">
            </td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;任务描述：</th>
            <td>
                <textarea id="task_remark" name="task_remark" class="h90 w600"></textarea>
            </td>
        </tr>
        <tr>
            <th></th>
            <td>
                <a href="javascript:doSubManageTask(undefined);" id="submit_btn"  class="an_small">提&nbsp;交</a>
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
                +'<label for="ck_group_${c.groupid}"> ${c.groupname}&nbsp;(${c.groupnum})</label></li>';
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
            $('input[id="p_ck_'+itm.value+'"]').attr("checked",false);
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
<!--上传文件类型-->
<div class="public_windows font-black"  id="dv_allow_filetype" name="dv_allow_filetype" style="display:none;position: absolute;z-index:99999">
    <h3>上传文件类型</h3>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您可以上传以下文件格式：</strong></p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;文档<span class="ico_doc1"></span>doc/docx<span class="ico_pdf1"></span>pdf<span class="ico_xls1"></span>xls/xlsx<span class="ico_txt1"></span>txt<span class="ico_wps1"></span>wps<span class="ico_vsd1"></span>vsd<span class="ico_rtf1"></span>rtf<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ico_ppt1"></span>ppt/pptx、pot、pps</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;图片<span class="ico_jpg1"></span>bmp  jpg  png  gif  tiff  pcx  tga</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;音频<span class="ico_mp31"></span>mp3 midi wma realaudi asf wav</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;视频<span class="ico_mp41"></span>mp4</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;动画<span class="ico_swf1"></span>swf</p>
</div>
</body>
</html>
