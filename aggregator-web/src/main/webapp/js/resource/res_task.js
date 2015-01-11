


function toSaveCoursePage(){
    if(global_gradeid<0){
        alert("没有获取到年级参数!");
        return;
    }
    if(global_subjectid<0){
        alert("没有获取到学科参数!");
        return;
    }
    var materialid=$("#material_id").val();
    if(materialid.length<1||materialid=="0"){
        getTchingMaterial(true);
        return;
    }
}


/**
 *
 * @param isinit 是否是第一次添加
 */
function getTchingMaterial(isinit){
    if(global_gradeid<0){
        alert("没有获取到年级参数！");
        return;
    }
    if(global_subjectid<0){
        alert("没有获取到学科参数！");
        return;
    }
    $("#addReportBtn").attr("href","javascript:selectMaterial()");
    $.post('teachingmaterial?m=getTchingMaterialList',{gradeid:global_gradeid,subjectid:global_subjectid,isindex:1},
        function(rps){
            if(rps!=null
                && rps.objList!=null
                && rps.objList.length>0){
                var html ="";
                var material_id = $("#material_id").val();
                $.each(rps.objList,function(idx,itm) {
                    var gradename=typeof  itm.gradename=='undefined'?"":itm.gradename;
                    var materialName=gradename+itm.materialname+"("+itm.versionname+")";
                    if(itm.materialname=='其它')
                        materialName=gradename+'其它';
                    html+="<li><input id='rdo_matid_"+itm.materialid+"' name='materialid' value='"+itm.materialid+"' type='radio' title='"+materialName;
                    if(material_id!="undefind"&&material_id.length>0){
                        if(material_id==itm.materialid){
                            html+="' checked='checked";
                        }
                    }
                    html+="' /><label for=\"rdo_matid_"+itm.materialid+"\">"+materialName+"</label></li>";
                });
                $("#teaching_materia").html(html);
                //showModel("teaching_materia_div");
                $("#materia_button").show();
                if(isinit){
                    $("#addReportBtn").attr("href","javascript:selectMaterial(true)");
                }
            }else{
                alert("无法找到学科和年级关联的教材，请联系管理添加!");
            }
        },"json");
    $("#teaching_materia_div").show();
   // $("#a_click").click();
}


function selectMaterial(isinit){
    $("#addReportBtn").show();
    var material_id=$("input[name='materialid']:checked").val();
    if(material_id==null){
        alert("教材参数错误！");
        return ;
    }
    if(global_gradeid<0){
        alert("没有获取到年级参数！");
        return;
    }
    if(global_subjectid<0){
        alert("没有获取到学科参数！");
        return;
    }
    if(termid<1){
        alert("没有获取到学科参数！");
        return;
    }

    $("#material_name").html($("input[name='materialid']:checked").attr("title"));
    $("#material_id").val(material_id);

    //得到相关ID grade_id,subject_id,term_id
    //存入数据库，这一项的选项。
    var param={gradeid:global_gradeid,subjectid:global_subjectid,termid:termid,materialid:material_id};
    $.ajax({
        url:'tpteacherteamater?m=doSaveOrUpdate',
        data:param,
        type:'POST',
        dataType:'json',
        error:function(){
        },success:function(rps){
            if(rps.type=="success"){
                var url='teachercourse?toSaveCourse&gradeid='+global_gradeid+'&subjectid='+global_subjectid;
                $("#dv_addCourse").load(url,function(){
                     $("#teaching_materia_div").hide();
                     $("#dv_course_parent").show();
                });
                if(isinit){
                    $("#addReportBtn").hide();
                    toSaveCoursePage();
                }
            }else{
                alert(rps.msg);
                $("#material_id").val('');
            }
        }
    });
    //$.fancybox.close();
}



//不一致的时候
function changeOneStartTime(itm,classid){
    var stime = $(itm).val();
    var starttime = new Date(stime.replace(/-/g,"/"));
    var sevenDayMiller = 1000*60*60*24*7;
    var newTime = new Date(starttime.getTime() + sevenDayMiller);
    var sNewTime = ChangeTimeToString(newTime);
    sNewTime = sNewTime+" "+stime.split(" ")[1];
    // if(endtype==0){
    $("#all_classEndTime").val(sNewTime);
    // }else{
    $("#"+classid+"_classEndTime").val(sNewTime);
    // }
}

//专题开始时间选择后，结束时间默认显示一周后的时间
function changgeAllStartTime(itm){
    // var endtype = $("input[name='classEndTimeType'][checked]").val();
    var stime = $(itm).val();
    var starttime = new Date(stime.replace(/-/g,"/"));
    var sevenDayMiller = 1000*60*60*24*7;
    var newTime = new Date(starttime.getTime() + sevenDayMiller);
    var sNewTime = ChangeTimeToString(newTime);
    sNewTime = sNewTime+" "+stime.split(" ")[1];
    if(!validateTwoDate(sNewTime,termendtime))
        sNewTime=termendtime;
    // if(endtype==0){
    $("#all_classEndTime").val(sNewTime);
    // }else{
    $("input[name='classEndTime']").each(function(idx,itm){
        $(itm).val(sNewTime);
    });
    // }
}

// 将日期类型转换成字符串型格式 yyyy-MM-dd hh:mm
function ChangeTimeToString ( DateIn )
{

    var Year = 0 ;

    var Month = 0 ;

    var Day = 0 ;

    var Hour = 0 ;

    var Minute = 0 ;

    var CurrentDate = "" ;

    // 初始化时间

    Year       = DateIn . getFullYear ();

    Month      = DateIn . getMonth ()+ 1 ;

    Day        = DateIn . getDate ();

    Hour       = DateIn . getHours ();

    Minute     = DateIn . getMinutes ();
    CurrentDate = Year + "-" ;
    if ( Month >= 10 )
    {
        CurrentDate = CurrentDate + Month + "-" ;
    }
    else
    {
        CurrentDate = CurrentDate + "0" + Month + "-" ;
    }
    if ( Day >= 10 )
    {
        CurrentDate = CurrentDate + Day ;
    }
    else
    {
        CurrentDate = CurrentDate + "0" + Day ;
    }
    return CurrentDate ;

}


function addTeacherCourse(){
    var term=termid;
    var residObj=$("#objectid")
    var coursename=$("#courseName");
    var introduction=$("#introduction");
    var sharetype=$('input[name="sharetype"]:checked').val();
    var courselevel=$("#courselevel").val();
    var classtime=$("#all_classtime").val();
    var classEndTime=$("#all_classEndTime").val().Trim().length<1?'0':$("#all_classEndTime").val();
    var materiaids = new Array();     //教材专题编号
    var classes = new Array();  //教学班级
    var vclasses = new Array();   //虚拟班级
    var classTimeArray = new Array();     //教学班级开课时间
    var vclassTimeArray = new Array();     //虚拟班级开课时间
    var classEndTimeArray = new Array();     //教学班级结束时间
    var vclassEndTimeArray = new Array();     //虚拟班级结束时间

    var materialidvalues;

    $("input[name='materialid']:checked").each(function() {materiaids.push($(this).val());});
    $("input[name='classes']:checked").each(function() {classes.push($(this).val());});
    $("input[name='tsclasses']:checked").each(function() {classes.push($(this).val());});
    $("input[name='vclasses']:checked").each(function() {vclasses.push($(this).val());});
    if(residObj.val().Trim().length<1){
        alert('系统未获取到资源标识!');
        return;
    }
    if(coursename.val().Trim().length<1){
        alert('请输入专题名称!');
        coursename.focus();
        return;
    }
    if(coursename.val().length>50){
        alert('专题名称限50字以内!');
        coursename.focus();
        return;
    }
    var maid= $("#material_id").val();
    if(maid.length<1){
        alert('请选择教材!');
        return;
    }
    if(term.length<1){
        alert('系统未获取到学期!');
        return;
    }
    if(classes.length<1 && vclasses.length<1){
        alert('请选择班级!');
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


    //检查是否选择课程结束时间
    if($("input[type='radio'][name='classEndTimeType']:checked").val()==1){
        $("input[type='checkbox'][name='classes']:checked").each(function() {
                if($("#"+$(this).val()+"_classEndTime").val().Trim().length<1)
                    flag=true;
                classEndTimeArray.push($("#"+$(this).val()+"_classEndTime").val().Trim().length<1?'0':$("#"+$(this).val()+"_classEndTime").val())}
        );
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_ts_classEndTime").val().Trim().length<1)
                    flag=true;
                classEndTimeArray.push($("#"+$(this).val()+"_ts_classEndTime").val().Trim().length<1?'0':$("#"+$(this).val()+"_ts_classEndTime").val())}
        );
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {
                if($("#"+$(this).val()+"_v_classEndTime").val().Trim().length<1)
                    flag=true;
                vclassEndTimeArray.push($("#"+$(this).val()+"_v_classEndTime").val().Trim().length<1?'0':$("#"+$(this).val()+"_v_classEndTime").val())}
        );
        /*if(flag){
         alert('请选择开课时间!');
         $("#addButton").attr("href","javascript:getTchingMaterial();");
         return;
         }*/
    }else{
        /* if(classEndTime.Trim().length<1){
         alert('请选择开课时间!');
         $("#addButton").attr("href","javascript:getTchingMaterial();");
         return;
         }*/
        $("input[type='checkbox'][name='classes']:checked").each(function() {classEndTimeArray.push(classEndTime)});
        $("input[type='checkbox'][name='tsclasses']:checked").each(function() {classEndTimeArray.push(classEndTime)});
        $("input[type='checkbox'][name='vclasses']:checked").each(function() {vclassEndTimeArray.push(classEndTime)});
    }




    materialidvalues=maid;
//    var selectCourseid='';
//    $("#selectedCourse li").each(function(){
//        selectCourseid+=$(this).attr("id")+'|';
//    });
    var selectedcourseids='';
    $("#selectedCourse li").each(function(ix,im){
        selectedcourseids+=$(this).attr("id")+"|";
    });
//    $("#addButton").attr("href","");
//    $("#addButton").removeClass("an_small");
//    $("#addButton").addClass("an_gray_small");
    //$("#addButton").attr("href","javascript:;");

    $.ajax({
        url:'teachercourse?m=addCourseByRes',
        data:{
            courseid:addCourseId,
            resid:residObj.val(),
            termid:term,
            coursename:coursename.val(),
            introduction:introduction.val(),
            sharetype:sharetype,
            courselevel:courselevel,
            gradeid:global_gradeid,
            subjectid:global_subjectid,
            classidstr:classes.join(','),
            vclassidstr:vclasses.join(','),
            materialidvalues:materialidvalues,
            classTimeArray:classTimeArray.join(','),
            vclassTimeArray:vclassTimeArray.join(','),
            classEndTimeArray:classEndTimeArray.join(','),
            vclassEndTimeArray:vclassEndTimeArray.join(','),
            selectcourseid:selectedcourseids
        },
        type:'POST',
        dataType:'json',
        error:function(data,status,e){
            $("#addButton").attr("href","javascript:addTeacherCourse();");
        },
        success:function(rps){
            if(rps.type=="success"){
                $.fancybox.close();
            }else{
                alert("无法添加!"+rps.msg);
                $("#addButton").attr("href","javascript:addTeacherCourse();");
            }
        }
    });
}

