$(function(){
    var updateid="${updateid}";
    $("input[name='classtimeType']").bind("click",function(){
        if($(this).val()==1){

            $("#list_ct p").hide();
            $("input[name='classes']:checked").each(function(idx,itm) {
                $("#p_"+$(this).val()).show();
            });
            $("input[name='tsclasses']:checked").each(function(idx,itm) {
                $("#p_ts_"+$(this).val()).show();
            });
            $("input[name='vclasses']:checked").each(function(idx,itm) {
              $("#p_v_"+$(this).val()).show();
            });
            $("#all_ct").hide();
            $("#list_ct").show('fast');
        }else{
            $("#list_ct").hide();
            $("#all_ct").show('fast');
        }
    });

    $("input[name='classes']").bind("click",function(){
        if($(this).attr("checked")){
            $("#p_"+$(this).val()).show();
        }else{
            $("#p_"+$(this).val()).hide();
            $("#"+$(this).val()+"_classtime").val("");
        }
    });

    $("input[name='tsclasses']").bind("click",function(){
        if($(this).attr("checked")){
            $("#p_ts_"+$(this).val()).show();
        }else{
            $("#p_ts_"+$(this).val()).hide();
            $("#"+$(this).val()+"_ts_classtime").val("");
        }
    });

    $("input[name='vclasses']").bind("click",function(){
        if($(this).attr("checked")){
            $("#p_v_"+$(this).val()).show();
        }else{
            $("#p_v_"+$(this).val()).hide();
            $("#"+$(this).val()+"_v_classtime").val("");
        }
    });
});

function addTeacherCourse(){
    $("#addButton").attr("href","");
    var term=$("#termid");
    var coursename=$("#courseName");
    var introduction=$("#introduction");
    var sharetype=$('input[name="sharetype"]:checked').val();
    var courselevel=$("#courselevel").val();
    var classtime=$("#all_classtime").val();
    var materiaids = new Array();     //教材专题编号
    var classes = new Array();  //教学班级
    var vclasses = new Array();   //虚拟班级
    var classTimeArray = new Array();     //教学班级开课时间
    var vclassTimeArray = new Array();     //虚拟班级开课时间

    var materialidvalues;

    $("input[name='materialid']:checked").each(function() {materiaids.push($(this).val());});
    $("input[name='classes']:checked").each(function() {classes.push($(this).val());});
    $("input[name='tsclasses']:checked").each(function() {classes.push($(this).val());});
    $("input[name='vclasses']:checked").each(function() {vclasses.push($(this).val());});
    if(coursename.val().Trim().length<1){
        alert('请输入专题名称!');
        coursename.focus();
        $("#addButton").attr("href","javascript:getTchingMaterial();");
        return;
    }
    if(coursename.val().length>50){
        alert('专题名称限50字以内!');
        coursename.focus();
        $("#addButton").attr("href","javascript:getTchingMaterial();");
        return;
    }
    if(materiaids.length<1&&$("#material_id").val==0){
        alert('请选择教材!');
        $("#addButton").attr("href","javascript:getTchingMaterial();");
        return;
    }
    if(term.val().Trim().length<1){
        alert('请选择学期!');
        $("#addButton").attr("href","javascript:getTchingMaterial();");
        term.focus();
        return;
    }
    if(classes.length<1 && vclasses.length<1){
        alert('请选择班级!');
        $("#addButton").attr("href","javascript:getTchingMaterial();");
        return;
    }
    var flag=false;

    //检查是否选择开课时间
    if($("input[type='radio'][name='classtimeType']:checked").val()==1){
        $("input[type='checkbox'][name='classes']:checked").each(function() {
                if($("#"+$(this).val()+"_classtime").val().Trim().length<1)
                    flag=true;
                classTimeArray.push($("#"+$(this).val()+"_classtime").val())}
        );
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_ts_classtime").val().Trim().length<1)
                    flag=true;
                classTimeArray.push($("#"+$(this).val()+"_ts_classtime").val())}
        );
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_v_classtime").val().Trim().length<1)
                    flag=true;
                vclassTimeArray.push($("#"+$(this).val()+"_v_classtime").val())}
        );
        if(flag){
            alert('请选择开课时间!');
            $("#addButton").attr("href","javascript:getTchingMaterial();");
            return;
        }
    }else{
        if(classtime.Trim().length<1){
            alert('请选择开课时间!');
            $("#addButton").attr("href","javascript:getTchingMaterial();");
            return;
        }
        $("input[type='checkbox'][name='classes']:checked").each(function() {classTimeArray.push(classtime)});
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {classTimeArray.push(classtime)});
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {vclassTimeArray.push(classtime)});
    }
    var maid= $("#material_id").val();
    if($("#materialid").val()==0){
        materialidvalues=materiaids.join(',');
    }else{
        materialidvalues=$("#materialid").val();
    }
    var material_id=$("input[name='materialid']:checked").val();
//    var selectCourseid='';
//    $("#selectedCourse li").each(function(){
//        selectCourseid+=$(this).attr("id")+'|';
//    });
    $.ajax({
        url:'teachercourse?m=addCourse',
        data:{
            termid:term.val(),
            coursename:coursename.val(),
            introduction:introduction.val(),
            sharetype:sharetype,
            courselevel:courselevel,
            gradeid:$("#gradeid").val(),
            subjectid:$("#subjectid").val(),
            classidstr:classes.join(','),
            vclassidstr:vclasses.join(','),
            materialidvalues:materialidvalues,
            classTimeArray:classTimeArray.join(','),
            vclassTimeArray:vclassTimeArray.join(','),
            selectcourseid:selectedCourseids
        },
        type:'POST',
        dataType:'json',
        error:function(){
            $("#addButton").attr("href","javascript:getTchingMaterial();");
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                if(maid!=null&&maid>0){
                            window.location.href="teachercourse?toTeacherCourseList&gradeid="+$("#gradeid").val()+"&materialid="+maid+"&currentSubjectid="+$("#subjectid").val();
                        }else{
                            var param={gradeid:$("#gradeid").val(),subjectid:$("#subjectid").val(),termid:term.val(),materialid:material_id};
                            $.ajax({
                                url:'tpteacherteamater?m=doSaveOrUpdate',
                                data:param,
                                type:'POST',
                                dataType:'json',
                                error:function(){
                            alert('异常错误,系统未响应！');
                        },success:function(rps){
                            if(rps.type=="success"){
                                alert(rps.msg);
                                closeModel("teaching_materia_div");
                                window.location.href="teachercourse?toTeacherCourseList&gradeid="+$("#gradeid").val()+"&materialid="+material_id+"&currentSubjectid="+$("#subjectid").val();
                            }else{
                                alert(rps.msg);
                            }
                        }
                    });
                }

            }else{
                alert("无法添加!"+rps.msg);
            }
        }
    });
}

function closeAddorUpdateWindow(){
    if(typeof(materialid)!='undefined'&&materialid!=null&&parseInt(materialid)>0){
        window.location.href="teachercourse?toTeacherCourseList&materialid="+materialid;
    }else{
        window.location.href="teachercourse?toTeacherCourseList";
    }
}

function updateTeacherCourse(){
    $("#updateButton").attr("href","");
    var courseid = $("#courseid");
    var term=$("#termid");
    var coursename=$("#courseName");
    var introduction=$("#introduction");
    var sharetype=$('input[name="sharetype"]:checked').val();
    var courselevel=$("#courselevel").val();
    var materiaids = new Array();
    var classtime=$("#all_classtime").val();
    var classes = new Array();  //教学班级
    var vclasses = new Array();   //虚拟班级
    var classTimeArray = new Array();     //教学班级开课时间
    var vclassTimeArray = new Array();     //虚拟班级开课时间

    $("input[name='materialid']:checked").each(function() {materiaids.push($(this).val());});
    $("input[name='classes']:checked").each(function() {classes.push($(this).val());});
    $("input[name='tsclasses']:checked").each(function() {classes.push($(this).val());});
    $("input[name='vclasses']:checked").each(function() {vclasses.push($(this).val());});
    if(courseid.val().Trim().length<1){
        alert('参数错误!');
        coursename.focus();
        $("#updateButton").attr("href","javascript:updateTeacherCourse();");
        return;
    }
    if(coursename.val().Trim().length<1){
        alert('请输入专题名称!');
        coursename.focus();
        $("#updateButton").attr("href","javascript:updateTeacherCourse();");
        return;
    }
    if(coursename.val().length>50){
        alert('专题名称限50字以内!');
        coursename.focus();
        $("#updateButton").attr("href","javascript:updateTeacherCourse();");
        return;
    }
//    if(materiaids.length<1){
//        alert('请选择教材!');
//        return;
//    }
    if(term.val().Trim().length<1){
        alert('请选择学期!');
        term.focus();
        $("#updateButton").attr("href","javascript:updateTeacherCourse();");
        return;
    }
    if(classes.length<1 && vclasses.length<1){
        alert('请选择班级!');
        $("#updateButton").attr("href","javascript:updateTeacherCourse();");
        return;
    }
    var flag=false;

    //检查是否选择开课时间
    if($("input[type='radio'][name='classtimeType']:checked").val()==1){
        $("input[type='checkbox'][name='classes']:checked").each(function() {
                if($("#"+$(this).val()+"_classtime").val().Trim().length<1)
                    flag=true;
                classTimeArray.push($("#"+$(this).val()+"_classtime").val())}
        );
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_ts_classtime").val().Trim().length<1)
                    flag=true;
                classTimeArray.push($("#"+$(this).val()+"_ts_classtime").val())}
        );
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_v_classtime").val().Trim().length<1)
                    flag=true;
                vclassTimeArray.push($("#"+$(this).val()+"_v_classtime").val())}
        );
        if(flag){
            alert('请选择开课时间!');
            return;
        }
    }else{
        if(classtime.Trim().length<1){
            alert('请选择开课时间!');
            return;
        }
        $("input[type='checkbox'][name='classes']:checked").each(function() {classTimeArray.push(classtime)});
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {classTimeArray.push(classtime)});
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {vclassTimeArray.push(classtime)});
    }

    $.ajax({
        url:'teachercourse?m=updateCourse',
        data:{
            courseid:courseid.val(),
            termid:term.val(),
            coursename:coursename.val(),
            introduction:introduction.val(),
            sharetype:sharetype,
            courselevel:courselevel,
            gradeid:$("#gradeid").val(),
            subjectid:$("#subjectid").val(),
            classidstr:classes.join(','),
            vclassidstr:vclasses.join(','),
           // materialidvalues:materiaids.join(','),
            classTimeArray:classTimeArray.join(','),
            vclassTimeArray:vclassTimeArray.join(',')
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("修改成功!");
                window.location.href="teachercourse?toTeacherCourseList&gradeid="+$("#gradeid").val()+"&currentSubjectid="+$("#subjectid").val();
            }else{
                $("#updateButton").attr("href","javascript:updateTeacherCourse();");
                alert("无法修改!"+rps.msg);
            }
        }
    });
}

function getTchingMaterial(){
    if($("#material_id").val()==undefined||$("#material_id").val()=="undefined"||$("#material_id").val()==0){
        var gradeid=$("#gradeid").val();
        var subjectid=$("#subjectid").val();
        $("#materia_button").hide();
        $.post('teachingmaterial?m=getTchingMaterialList',{gradeid:gradeid,subjectid:subjectid},
            function(rps){
                if(rps!=null
                    && rps.objList!=null
                    && rps.objList.length>0){
                    var html ="";
                    $.each(rps.objList,function(idx,itm) {
                        html+="<li  style=\"width:450px;\"><input  id='rdo_matid_"+itm.materialid+"' name='materialid' value='"+itm.materialid+"' type='radio' >" +
                            "<label for=\"rdo_matid_"+itm.materialid+"\">"+itm.materialname+"("+itm.versionname+")</span></td>";
                    });
                    $("#teaching_materia").html(html);
                    showModel("teaching_materia_div");
                    $("#materia_button").show();
                }else{
                    alert("无法找到学科和年级关联的教材，请联系管理添加。");
                }
            },"json");
    }else{
        addTeacherCourse();
    }


}

function getClassByOptions(){
    var gradeid=$("#gradeid").val();
    var subjectid=$("#subjectid").val();
    $.post('teachercourse?m=getClassByOptions',{gradeid:gradeid,subjectid:subjectid},
        function(rps){
            if(rps!=null
                && rps.objList!=null
                && rps.objList.length>0){
                $.each($("input[type='checkbox'][name='classes']"),function(idx,itm) {
                    $(itm).parent().remove();
                });

                $.each($("input[type='text'][name='classtime']"),function(idx,itm) {
                    $(itm).parent().remove();
                });

                var classeshtml ="";
                var classtimeHtml ="";
                $("input[name='classes']:checked").remove();
                $.each(rps.objList,function(idx,itm) {
                    classeshtml+="<li>";
                    classeshtml+="<input id='classes' type='checkbox' value='"+itm.classid+"' name='classes'>";
                    classeshtml+=itm.classinfo.classname;
                    classeshtml+="</li>";
                    classtimeHtml+= "<p id='p_"+itm.classid+"'>";
                    classtimeHtml+= "<span>"+itm.classinfo.classname+":</span>";
                    classtimeHtml+= "<input id='"+itm.classid+"_classtime' class='public_input w220' type='text' name='classtime' " +
                        "readonly='readonly' onfocus="+"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"+">";
                    classtimeHtml+= "</p>";
                });
                $("#ul_classes").html(classeshtml+$("#ul_classes").html());
                $("#list_ct").html(classtimeHtml+$("#list_ct").html());

                $("input[name='classes']").bind("click",function(){
                    if($(this).attr("checked"))
                        $("#p_"+$(this).val()).show();
                    else
                        $("#p_"+$(this).val()).hide();
                });

                $("input[name='vclasses']").bind("click",function(){
                    if($(this).attr("checked"))
                        $("#p_v_"+$(this).val()).show();
                    else
                        $("#p_v_"+$(this).val()).hide();
                });

                $("#classtimeType").attr("checked",true);
                $("#list_ct").hide();
                $("#all_ct").show();

            }else{
                alert("无法找到学科和年级关联的教材，请联系管理添加。");
            }
        },"json");
}

function toCourseLibrary(){
    window.location.href="teachercourse?m=toCourseLibrary&subjectid="+$("#subjectid").val()
        +"&gradeid="+$("#gradeid").val()
        +"&termid="+$("#termid").val();

}

/************************************************************************************************************************/

function delLi(id){
    $("#"+id).remove();
}

function addRelatedItem(){
    var coursename=$("#related").val();
    var courseid=$("#hcourse_id").val();
    var htm='';
    htm+='<li id="'+courseid+'">'+coursename+'&nbsp;<a href="javascript:delLi('+courseid+')">[X]</a></li>';
    $("#selectedCourse").after(htm);
    selectedCourseids+=courseid+'|';
    $("#related").val('');
    $("#hcourse_id").val('');
}

/**
 * 联想输入框的实现
 *
 * @param objid 页面输入框对应的obj标签的Id
 * @param flag 联想输入对应的操作,数字,与action中实现对应
 */
function inputThinking() {
// 输入框父节点Id
    var pid = "related_p";

//输入框的Id
    var inputId = "related";

// 获取输入框宽度，根据宽度自适应
    var width = $("#" + pid).css("width");
    if(width && width.indexOf('px') > 0) {
        width = width.substring(0, width.indexOf('px')) - 2;
    } else {
        width = 158;
    }

// 定义联想输入页面HTML, 定义输入框及联想输入页面的状态,状态1表示焦点状态，状态0表示非焦点状态
    var tipHTML = "<div id='thinking' style='background-color:white;position:fixed;z-index:999999;border:1px solid #CDE4EF; width:" + width + "px;overflow:hidden;overflow:hidden;display:none;background-image: theme/default/imagesmember_right1.png'><li>&nbsp;</li></div><input type='hidden' id='inputId' value='" + inputId + "'><input type='hidden' id='status' value='1'><input type='hidden' id='thinking_status' value='0'><style>li.sel{background:#4d77bb;color:white;}li{font-size:14px;height:18px;overflow:hidden;}</style>"

    /********** 将联想输入页面嵌入原始页面 **********/
    $("#" + inputId).parent().append(tipHTML);

    /********** 绑定输入框事件 **********/

// 输入框内容修改事件
    $("#" + inputId).bind("keyup", function(e) {
        if (e.keyCode == 38) {
// 向上
            next(false);
        } else if(e.keyCode == 40) {
// 向下
            next(true);
        } else {
            showThinking(inputId);
        }
    });

// 单击事件,将输入框的状态置为1
    $("#" + inputId).bind("click", function() {
// 设置输入框的状态为1
        $("#status").val(1);

        showThinking(inputId);
    });

// 单击事件,将输入框的状态置为1
    $("#" + inputId).bind("focus", function() {
// 设置输入框的状态为1
        $("#status").val(1);

        showThinking(inputId);
    });

// 失去焦点事件,将输入框的状态置为0
    $("#" + inputId).bind("focusout", function() {
// 设置输入框的状态为0
        $("#status").val(0);

// 当联想输入页面和输入框的状态均为0时隐藏联想输入页面
        if($("#thinking_status").val() && $("#thinking_status").val() == 0) {
            $("#thinking").css("display", "none");
        }
    });

    /********** 绑定联想输入页面事件 **********/

// 鼠标移动事件
    $("#thinking").bind("mousemove", function() {
// 设置联想输入页面的状态为1
        $("#thinking_status").val(1);
    });

// 鼠标移出事件
    $("#thinking").bind("mouseout", function() {
// 设置联想输入页面的状态为0
        $("#thinking_status").val(0);

// 当联想输入页面和输入框的状态均为2时隐藏联想输入页面
        if($("#status").val() && $("#status").val() == 0) {
            $("#thinking").css("display", "none");
        }
    });

    /********** end **********/
}

/**
 * 根据状态刷新数据，并显示联想输入框
 *
 * @param inputId 页面input输入框对应的Id
 * @param flag 联想输入对应的操作,数字,与action中实现对应
 */
function showThinking(inputId) {
    var value = $.trim($("#" + inputId).val());
    var materialid=$("#materialid").val();
// 只有输入框非空的时候才调用输入联想
    if(value && value != null && value != '') {
// 发送ajax初始化输入联想页面
        $.ajax({
            type: "POST",
            dataType:"json",
            cache: false,
            url: "teachercourse?m=getRelatedCourse",
            data: {course_name:value,materialid:materialid},
            success: function(rps) {
                if(rps.type="success") {
                    var htm='';
                    if(rps.objList!=null&&rps.objList.length>0){
                        $.each(rps.objList,function(idx,itm){
                            htm+='<a href="javascript:selectCourseName(\''+itm.COURSE_NAME+'\','+itm.COURSE_ID+')" >'+itm.COURSE_NAME+'</a><br>';
                        });
                     }
                    $("#thinking").html(htm);
                    $("#thinking").css("display", "block");
                }else{
                    alert("系统异常，请稍后重试")
                }
            },
            error: function(msg) {
                alert(msg);
            }
        });
    } else {
        $("#thinking").html("");
    }
}

function next(flag) {
    var obj = get($("#thinking li.sel"), 0);
    var next = null;
    if(obj) {
        next = get(flag? $(obj).next("li"): $(obj).prev("li"), 0);
    } else {
        next = flag? $("#thinking li").first(): $("#thinking li").last();
    }
    if(next) {
        if(obj) {
            $(obj).removeClass("sel");
        }
        $(next).addClass("sel");
    }
}

function get(array, index) {
    if(array.length && array.length > index) {
        return array[index];
    } else {
        return null;
    }
}

/**
 * 将选择的数据填充到输入框
 *
 * @param name 显示的数据内容
 */
function selectCourseName(name,id) {
    $("#hcourse_id").val(id);
    $("#" + $("#inputId").val()).val(name);
    $("#" + $("#inputId").val()).change(); // 触发change事件，更新输入联想提示内容
    $("#thinking").css('display', 'none');
}

/**
 * 将选择的节点数据填充到输入框
 *
 * @param obj 选择的节点
 */
function selectThinking(obj) {
    $("#" + $("#inputId").val()).val($(obj).text());
    $("#" + $("#inputId").val()).change(); // 触发change事件，更新输入联想提示内容
    $("#thinking").css('display', 'none');
}

