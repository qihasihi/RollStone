/**
 * 获取网校班级
 */
function loadCls(pageno){
    var param={};
    var grade=$("#sel_grade");
    var year=$("#sel_year");
    var type=$("#sel_type");

    if(grade.val().Trim().length>0)
        param.grade=grade.val();
    if(year.val().Trim().length>0)
        param.year=year.val();
    if(type.val().Trim().length>0)
        param.type=type.val();


    $.ajax({
        url: 'tpuser?m=ajaxClsList&pageResult.pageNo=' + pageno + "&pageResult.pageSize=10&t=" + new Date().getTime(),
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {

            $("#ul_class").html('');
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList.length>0){
                    $("#dv_detail").show('fast');
                    $.each(rps.objList,function(idx,itm){
                      var h='<li';
                        if(idx==0)
                            h+=' class="crumb "';
                        h+='>';
                        var clsname=itm.year+'年'+itm.classname+itm.classgrade+'普通班';
                        var cname=qhs.GetLength(clsname)>20?clsname.substring(0,20)+'...':clsname;
                        h+='<a title="'+clsname+'" id="a_for_'+itm.classid+'" href="javascript:loadClsDetial('+itm.classid+')">'+cname+'</a></li>';
                        $("#ul_class").append(h);
                    });
                }else{
                    $("#dv_detail").hide('fast');
                }
                var aObj = $('a').filter(function () {
                    return this.id.indexOf('a_for_') != -1
                });
                if (aObj.length > 0)
                    $(aObj).get(0).click();

                //分页
                $("#dv_page").html('');
                if (rps.presult.recTotal > 0 && rps.presult.pageTotal > 1) {
                    var prev = (parseInt(pageno) - 1) > 0 ? (parseInt(pageno) - 1) : 1;
                    var next = (parseInt(pageno) + 1) >= rps.presult.pageTotal ? rps.presult.pageTotal : (parseInt(pageno) + 1);
                    var page_split = '<span><a href="javascript:loadCls(' + prev + ')"><b class="before"></b></a></span>&nbsp;';
                    page_split+=pageno+'/'+rps.presult.pageTotal;
                    page_split+='&nbsp;<span><a href="javascript:loadCls(' + next + ')"><b class="after"></b></a></span>';
                    $("#dv_page").html(page_split);
                }
            }
        }
    });
}


/**
 * 设置任课老师
 * @param clsid
 */
function doSetClassTeacher(clsid){
    if(typeof clsid=='undefined')
        return;
    var subject=$("#add_subject");
    var jid=$("#teacherList");
    var realname=$("#teacherList").find("option:selected").text();
    if(subject.val().Trim().length<1){
        alert('请选择学科!');
        subject.focus();
        return;
    }
    if(jid.val().Trim().length<1){
        alert('请选择教师!');
        jid.focus();
        return;
    }

    var param={clsid:clsid,subjectid:subject.val(),jid:jid.val(),realname:realname};
    $.ajax({
        url: 'tpuser?doSetClassTeacher',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
               closeModel('dv_add_teacher');
               loadCls(1);
            }
        }
    });
}

/**
 * 删除班级用户
 * @param clsid
 */
function delClassUser(ref){
    if(typeof ref=='undefined')
        return;
    if(!confirm('确认操作?'))return;

    var param={ref:ref};
    $.ajax({
        url: 'tpuser?delClassUser',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                $("#li_"+ref).remove();
            }
        }
    });
}


/**
 * 获取网校教师
 */
function loadWXTeacher(clsid){
    if(typeof clsid=='undefined')
        return;

    $("#a_sub_teacher").attr("href","javascript:doSetClassTeacher("+clsid+");");
    showModel('dv_add_teacher');
    $("#teacherList").html('');
    var param={clsid:clsid};
    $.ajax({
        url: 'tpuser?loadWXTeacher',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                var h='<option value="">选择教师</option>';
                if(rps.objList.length>0){
                    $.each(rps.objList,function(idx,itm){
                        h+='<option value="'+itm.ettuserid+'">'+itm.realname+'</option>';
                    });
                }
                $("#teacherList").html(h);
            }
        }
    });
}




/**
 * 获取网校学生
 */
function loadWXStudent(clsid){
    if(typeof clsid=='undefined')
        return;
    showModel('dv_add_student');

    var param={clsid:clsid};
    $.ajax({
        url: 'tpuser?loadWXStudent',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {

            }
        }
    });
}



/**
 * 获取班级详情
 * @param clsid
 */
function loadClsDetial(clsid){
    if(typeof clsid=='undefined')
        return;
    $.ajax({
        url: 'tpuser?m=ajaxClsDetail',
        type: 'post',
        data: {clsid:clsid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {

            $("#s_clsname").html('');
            $("#s_bzr").html('');
            $("#ul_tea").html('');
           $("#ul_stu").html('');

            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                /**
                 *   je.getObjList().add(clsList);
                 je.getObjList().add(bzrList);
                 je.getObjList().add(teaList);
                 je.getObjList().add(stuList);
                 */
                if(rps.objList[0]!=null&&rps.objList[1]!=null){
                    var clsname=rps.objList[0].year+'年'+rps.objList[0].classname+rps.objList[0].classgrade+'普通班';
                    $("#p_edit").html('<a href="javascript:showModel(\'dv_edit\')" class="ico11" title="编辑"></a><a href="javascript:void(0)" onclick="doDelCls(\''+rps.objList[0].classid+'\')" class="ico04" title="删除"></a>');
                    $("#s_clsname").html(clsname);
                    $("#add_teacher_clsname").html(clsname);
                    $("#s_bzr").html('<b >班主任：'+rps.objList[1].realname+'</b>班级类型：'+(rps.objList[0].dctype==2?"网校班级":"爱学课堂")+'');

                    //添加学生教师 增加Click
                    $("#a_addTeacher").attr("href","javascript:loadWXTeacher("+rps.objList[0].classid+")");
                    $("#a_addStudent").attr("href","javascript:loadWXStudent("+rps.objList[0].classid+")");
                }

                if(rps.objList[2]!=null){
                    $("#tea_count").html(rps.objList[2].length);
                    $.each(rps.objList[2],function(idx,itm){
                        var h='<li id="li_'+itm.ref+'">';
                        h+='<img src="" width="80" height="80">'+itm.realname+'<b>'+itm.subjectname+'</b>';
                        h+='<p class="ico"><a class="ico34" href="javascript:void(0);" onclick="delClassUser(\''+itm.ref+'\')" title="移出"></a></p>';
                        h+='</li>';
                        $("#ul_tea").append(h);
                    });
                }

                if(rps.objList[3]!=null){
                    $("#stu_count").html(rps.objList[3].length);
                    $.each(rps.objList[3],function(idx,itm){
                        var h='<li>';
                        h+='<img src="" width="80" height="80">'+itm.realname;
                        h+='<p class="ico"><a class="ico34" href="javascript:void(0);" onclick="delClassUser(\''+itm.ref+'\')" title="移出"></a></p>';
                        h+='</li>';
                        $("#ul_stu").append(h);
                    });
                }
            }
        }
    });
}
/**
 * 操作ett班级
 */
function sub_cls(clsid){
    var dvObj=typeof clsid=='undefined'?'dv_add':'dv_edit';

    var clsname=$("#"+dvObj+" input[id='cls_name']");
    var verifyTime=$("#"+dvObj+" input[id='verify_time']");
    var num=$("#"+dvObj+" input[id='num']");
    var type=$("#"+dvObj+" select[id='type']");
    var bzrJid=$("#"+dvObj+" select[id='bzr']");
    var bzrName=$(bzrJid).find("option:selected").text();
    var year=$("#"+dvObj+" select[id='year']");
    var grade=$("#"+dvObj+" select[id='grade']");
    var allowJoin=$("#"+dvObj+" input[name='rdo']");

    if(clsname.val().Trim().length<1){
        alert('请输入班级名称!');
        clsname.focus();return;
    }
    if(type.val().length<1){
        alert('请选择班级类型!');
        type.focus();return;
    }
    if(bzrJid.val().length<1){
        alert('请选择班主任!');
        bzrJid.focus();return;
    }
    if(year.val().length<1){
        alert('请选择学年!');
        grade.focus();return;
    }
    if(grade.val().length<1){
        alert('请选择年级!');
        grade.focus();return;
    }

    var param={dcschoolid:schoolid,clsname:clsname.val(),type:type.val(),jid:bzrJid.val(),realname:bzrName,gradeid:grade.val(),year:year.val()};
    if(verifyTime.val().length>0)
        param.verifyTime=verifyTime.val();
    if(allowJoin.val().length>0)
        param.allowJoin=allowJoin.val();
    if(num.val().length>0)
        param.num=num.val();

    var url='tpuser?m=doAddCls';
    if(typeof clsid!='undefined')
        url='tpuser?m=doUpdCls&clsid='+clsid;
    $.ajax({
        url:url,
        dataType:'json',
        type:'POST',
        data:param,
        cache: false,
        error:function(){
            //alert('异常错误!系统未响应!');
        },success:function(rps){
            alert(rps.msg);
            if(rps.type=='success'){
                closeModel(dvObj);
            }
        }
    });
}