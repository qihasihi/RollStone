

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

