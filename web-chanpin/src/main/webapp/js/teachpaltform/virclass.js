var virClassId='';

$(function(){
    $("#ts_grade").change(function(){
        getTrusteeShipTeacher();
    });

    $("#grade").change(function(){
        refreshClassSelect();
    });

    $("#tchname").bind("input propertychange",function(){
        getTrusteeShipTeacher();
    });
});

function addVirtualClass(){
    var virtualclassname=$("#new_virtual_class_name");
    if(virtualclassname.val().Trim().length<1){
        alert('请输入虚拟班级名称!');
        virtualclassname.focus();
        return;
    }
    if(virtualclassname.val().length>50){
        alert('虚拟班级名称限50字以内!');
        virtualclassname.focus();
        return;
    }
    $.ajax({
        url:'virtualclass?m=addVirtualClass',
        data:{
            virtualclassname:virtualclassname.val()
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("添加成功!");
                closeModel('add_virtual_class_Div');
                window.location.href='virtualclass?m=toClassManager&gradeid='+gradeid+'&subjectid='+subjectid;
            }else{
                alert("无法添加，"+rps.msg);
            }
        }
    });
}

function changeVirClassStatus(virtualclassid,type){
    var status=2;
    if(type){
        status=1;
    }
    $.ajax({
        url:'virtualclass?m=changeVirClassStatus',
        data:{
            virtualclassid:virtualclassid,
            status:status
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                var html="";
                if(type){
                    html+="<a class='ico01' title='禁用' onclick='changeVirClassStatus("+virtualclassid+",false);'></a>";
                } else{
                    html+="<a class='ico02' title='启用' onclick='changeVirClassStatus("+virtualclassid+",true);'></a>";
                }
                $("#virclass_status_"+virtualclassid).html(html);
                alert("删除成功!");
                window.location.href="virtualclass?m=toClassManager&gradeid="+gradeid+"&subjectid="+subjectid;

            }else{
                alert("无法修改，"+rps.msg);
            }
        }
    });
}

function searchStudent(){
    var grade=$("#grade");
    var classid=$("#class_id");
    var stuname=$("#stu_name");
    $.ajax({
        url:'virtualclassstudent?m=searchStudentList',
        data:{
            grade:grade.val(),
            classid:classid.val(),
            stuname:stuname.val(),
            virtualclassid:virClassId
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="error"){
                alert("无法添加，"+rps.msg);
            }else{
                if(rps.objList!=null && rps.objList.length>0){
                    var html="";
                    $.each(rps.objList,function(idx,itm){
                        html+="<li id='list_"+itm.USER_ID+"'>";
                        html+="<a class='ico12' href="+"javascript:moveStudent("+itm.USER_ID+",'"+itm.STU_NAME+"','add');"+"></a>";
                        //html+=itm.STU_NAME;
                        html+="<span id='span_"+itm.USER_ID+"'>"+itm.STU_NAME+"</span>";
                        html+="</li>";
                    });
                    $("#searchStuList").html(html);

                }
            }
        }
    });
}

function getVirClassStudents(virclassid){
    $("#checkedStuList").html("");
    $("#searchStuList").html("");
    virClassId=virclassid;
    $.ajax({
        url:'virtualclassstudent?m=getVirClassStudent',
        data:{
            virtualclassid:virclassid
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="error"){
                alert("无法添加，"+rps.msg);
            }else{
                if(rps.objList!=null && rps.objList.length>0){
                    var html="";
                    $.each(rps.objList,function(idx,itm){
                        html+="<li id='check_"+itm.userid+"'>";
                        html+="<a class='ico50' href="+"javascript:moveStudent("+itm.userid+",'"+itm.stuname+"','del');"+"></a>";
                        html+="<span id='span_"+itm.userid+"'>"+itm.stuname+"</span>";
                        html+="<input name='checkedStu' type='hidden' value='"+itm.userid+"'>";
                        html+="</li>";
                    });
                    $("#checkedStuList").html(html);

                }
            }
        }
    });
}

function getTrusteeShipTeacher(title,tsclass,classtype){
    $("#trusteeShipTitle").html(title);
    $("#shipClassId").val(tsclass);
    $("#shipClassType").val(classtype);
    var grade= $("#ts_grade").val();
    var tchname= $("#tchname").val();
    $.ajax({
        url:'trusteeship?m=getTrusteeShipTchs',
        data:{
            grade:grade,
            tchname:tchname,
            subjectid:subjectid
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
            $("#ts_teacher").html("");
        },success:function(rps){
            if(rps.type=="error"){
                alert("查询出错……");
                $("#ts_teacher").html("");
            }else{
                if(rps.objList!=null && rps.objList.length>0){
                    var html="";
                    $.each(rps.objList,function(idx,itm){
                        if(itm.USER_ID==userid){

                        }else{
                            html+="<option value='"+itm.USER_ID+"' >"+itm.TEACHER_NAME+"</option>";
                        }

                    });
                    $("#ts_teacher").html(html);
                }else{
                    $("#ts_teacher").html("");
                }
            }
        }
    });
}

function moveStudent(userid,username,type){
    var html='';
    if(type=="add"){
        html+="<li id='check_"+userid+"'>";
        html+="<a class='ico50' href="+"javascript:moveStudent("+userid+",'"+username+"','del');"+"></a>";
        html+="<span id='span_"+userid+"'>"+username+"</span>";
        html+="<input name='checkedStu' type='hidden' value='"+userid+"'>";
        html+="</li>";
        $("#list_"+userid).remove();
        $("#checkedStuList").append(html);
    }
    if(type=="del"){
        html+="<li id='list_"+userid+"'>";
        html+="<a class='ico12' href="+"javascript:moveStudent("+userid+",'"+username+"','add');"+"></a>";
        html+="<span id='span_"+userid+"'>"+username+"</span>";
        html+="</li>";
        $("#check_"+userid).remove();
        $("#searchStuList").append(html);
    }
}




function moveAllStudent(type){
    var html='';

    if(type=="add"){
        $("li").each(function(index){
            var username=$(this).children("span").text();
            var liid=$(this).attr("id");
            //alert(liid);
            if(liid.substring(0,5)=="list_")
            {
                var userid= liid.substr(5,liid.length);
               // alert(liid+"  "+userid);
                moveStudent(userid,username,type)
             }
        })
    }
    if(type=="del"){

        $("li").each(function(index){
            var username=$(this).children("span").text();
            var liid=$(this).attr("id");
            //alert(liid);
            if(liid.substring(0,6)=="check_")
            {
                var userid= liid.substr(6,liid.length);
                //alert(liid+"  "+userid);
               moveStudent(userid,username,type)
            }
        })

    }
}


function submitCheckedStu(){
    var students = new Array();
    $("input[name='checkedStu']").each(function() {students.push($(this).val());});
    if(students.length<1){
        alert("还没选择学生");
        return;
    }
    $.ajax({
        url:'virtualclassstudent?m=addVirtualClassStudents',
        data:{
            virtualclassid:virClassId,
            students:students.join(",")
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("添加成功!");
                window.location.reload();
            }else{
                alert("无法添加，"+rps.msg);
            }
        }
    });
}

function addTrusteeShip(){
    var classId=$("#shipClassId").val();
    var classType=$("#shipClassType").val();
    var receiveteacherid=$("#ts_teacher");
    if(classId==null||classType==null){
        alert('班级参数错误!');
        return;
    }
    if(receiveteacherid==null
        || receiveteacherid.val()==null
        || receiveteacherid.val().Trim().length<1){
        alert('请选择托管老师!');
        receiveteacherid.focus();
        return;
    }

    $.ajax({
        url:'trusteeship?m=addTrusteeShip',
        data:{
            receiveteacherid:receiveteacherid.val(),
            trustclassid:classId,
            trustclasstype:classType
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("添加成功!");
                window.location.reload();
            }else{
                alert("无法添加，"+rps.msg);
            }
        }
    });
}

function delTrusteeShip(classid,classtype){
    if(classid==null || classtype==null){
        alert('班级参数错误！');
        return;
    }
    $.ajax({
        url:'trusteeship?m=delTrusteeShip',
        data:{
            trustclassid:classid,
            trustclasstype:classtype
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("已经取消!");
                window.location.reload();
            }else{
                alert("无法取消，"+rps.msg);
            }
        }
    });
}

function refreshClassSelect(){
    var grade=$("#grade");
    if(grade==0 || grade.val().Trim().length<1){
        grade=null;
        return;
    }
    $.ajax({
        url:'cls?m=ajaxlist',
        data:{
            classgrade:grade.val()
        },
        type:'POST',
        dataType:'json',
        error:function(){
        },success:function(rps){
            var html="<option value='0'>—请选择—</option>";
            $.each(rps.objList,function(idx,itm){
                html+="<option value='"+itm.classid+"' >"+itm.classgrade+itm.classname+"</option>";
            });
            $("#class_id").html(html);
        }
    });
}