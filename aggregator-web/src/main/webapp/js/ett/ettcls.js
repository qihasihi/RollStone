/**
 * 获取网校班级
 */
function loadCls(pageno,clsid){
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
                        var clsname=itm.classgrade+itm.classname;
                        var cname=qhs.GetLength(clsname)>32?clsname.substring(0,16)+'...':clsname;
                        h+='<a title="'+clsname+'" id="a_for_'+itm.classid+'" href="javascript:loadClsDetial('+itm.classid+')">'+cname+'</a></li>';
                        $("#ul_class").append(h);
                    });
                }else{
                    $("#dv_detail").hide('fast');
                }

                $("#ul_class li").each(function(idx,itm){
                    $(itm).click(function(){
                        $(this).siblings().removeClass('crumb');
                        $(this).addClass('crumb');
                    })
                });


                var clsIdObj='a_for_';
                if(typeof clsid!='undefined'&&clsid.toString().length>0)
                    clsIdObj='a_for_'+clsid;
                var aObj = $('a').filter(function () {
                    return this.id.indexOf(clsIdObj) != -1
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
               loadCls(1,clsid);
            }
        }
    });
}

/**
 * 删除班级用户
 * 1:教师
 * 2：学生
 * @param clsid
 */
function delClassUser(ref,clsid){
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
               // $("#li_"+ref).remove();
               loadClsDetial(clsid);
            }
        }
    });
}


/**
 * 添加班级学生
 * @param clsid
 */
function doAddClsStudent(clsid){
    if(typeof clsid=='undefined')
        return;

    var msg='数据验证完毕!确认操作?',param={clsid:clsid,groupflag:1},jidArray=new Array(),nameArray=new Array();
    var liArray=$("#ul_cls_stu li");
    if(liArray.length<1){
        param.flag=1;
        msg='确认删除当前班级所有学生!?';
    }
    $.each(liArray,function(idx,itm){
        var jid=$(itm).data().bind.split("|")[0];
        var name=$(itm).data().bind.split("|")[1];
        jidArray.push(jid);
        nameArray.push(name);
    });
    if(jidArray.length>0&&nameArray.length>0){
        param.jidStr=jidArray.join(",");
        param.nameStr=nameArray.join(",");
    }


    if(!confirm(msg))return;
    $.ajax({
        url: 'tpuser?doAddClsStudent',
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
                closeModel('dv_add_student');
                loadCls(1,clsid);
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
    $("#txt_stuName").val('');

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
            var wx='',sx='';
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    $.each(rps.objList[0],function(idx,itm){
                        wx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85a"></span>'+itm.realname+'</li>';
                    });

                }

                if(rps.objList[1]!=null&&rps.objList[1].length>0){
                    $.each(rps.objList[1],function(idx,itm){
                        sx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85b"></span>'+itm.realname+'</li>';
                    });
                }
            }
            $("#ul_wx_stu").html(wx);
            $("#ul_cls_stu").html(sx);


            $("#dv_add_student li").click(function(){
                var id=$(this).attr('id');
                var parentId=$(this).parent('ul').attr('id');
                var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
                var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
                var descObj=$('#'+descObj+' li[id="'+id+'"]');

                if(descObj.length<1){
                    $(this).children('span').attr("class",clssName);
                    $(this).appendTo( $("#"+descId));
                }else
                    alert('当前学生已存在!');

            });
        }
    });
}





/**
 * 获取网校学生
 */
function loadWXStudentByName(clsid){
    if(typeof clsid=='undefined')
        return;
    var param={clsid:clsid};
    var stuName=$("#txt_stuName");
    if(stuName.val().Trim().length>0)
        param.stuName=stuName.val();

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
            var wx='';
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    $.each(rps.objList[0],function(idx,itm){
                        wx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85a"></span>'+itm.realname+'</li>';
                    });

                }

            }
            $("#ul_wx_stu").html(wx);


            $("#dv_add_student li").click(function(){
                var id=$(this).attr('id');
                var parentId=$(this).parent('ul').attr('id');
                var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
                var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
                var descObj=$('#'+descObj+' li[id="'+id+'"]');

                if(descObj.length<1){
                    $(this).children('span').attr("class",clssName);
                    $(this).appendTo( $("#"+descId));
                }else
                    alert('当前学生已存在!');

            });
        }
    });
}



function setAllStudent(){
    var wxArray=$("#ul_wx_stu li");
    if(wxArray.length>0){
        $.each(wxArray,function(ix,im){
            var id=$(im).attr('id');
            var parentId=$(im).parent('ul').attr('id');
            var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
            var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
            var descObj=$('#'+descObj+' li[id="'+id+'"]');

            if(descObj.length<1){
                $(im).children('span').attr("class",clssName);
                $(im).appendTo( $("#"+descId));
            }
        });
    }
}

function reSetStudent(){
    var sxArray=$("#ul_cls_stu li");

    if(sxArray.length>0){
        $.each(sxArray,function(ix,im){
            var id=$(im).attr('id');
            var parentId=$(im).parent('ul').attr('id');
            var descId=parentId=='ul_cls_stu'?'ul_wx_stu':'ul_cls_stu';
            var clssName=parentId=='ul_wx_stu'?'ico85a':'ico85b';
            var descObj=$('#'+descObj+' li[id="'+id+'"]');

            if(descObj.length<1){
                $(im).children('span').attr("class",clssName);
                $(im).appendTo( $("#"+descId));
            }
        });
    }
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
                $("#p_edit").hide();
                $("#a_addTeacher").attr("href","javascript:;");
                $("#a_addStudent").attr("href","javascript:;");
                $("#stu_count").html('0');
                $("#tea_count").html('0');
            } else {

                /**
                 *   je.getObjList().add(clsList);
                 je.getObjList().add(bzrList);
                 je.getObjList().add(teaList);
                 je.getObjList().add(stuList);
                 */
                if(rps.objList[0]!=null&&rps.objList[1]!=null){
                    $("#p_edit").show();
                    var clsname=rps.objList[0].classname;
                    $("#p_edit").html('<a href="javascript:;" id="a_to_upd" class="ico11" title="编辑"></a><a href="javascript:void(0)" onclick="doDelCls(\''+rps.objList[0].classid+'\')" class="ico04" title="删除"></a>');
                    $("#s_clsname").html(clsname);
                    $("#add_teacher_clsname").html(clsname);
                    $("#bzr_img").attr("src",rps.objList[1].headimage);
                    $("#s_bzr").html('<b >班主任：'+rps.objList[1].realname+'</b>班级类型：'+(rps.objList[0].dctype==2?"网校班级":"爱学课堂")+'');

                    //添加学生教师 增加Click
                    $("#a_addTeacher").attr("href","javascript:loadWXTeacher("+rps.objList[0].classid+")");
                    $("#a_addStudent").attr("href","javascript:loadWXStudent("+rps.objList[0].classid+")");
                    $("#a_stuName").attr("href","javascript:loadWXStudentByName("+rps.objList[0].classid+")");
                    $("#a_sub_student").attr("href","javascript:doAddClsStudent("+rps.objList[0].classid+")");
                    $("#a_to_upd").attr("href","javascript:toUpdClass("+rps.objList[0].classid+")");
                }

                if(rps.objList[2]!=null){
                    $("#tea_count").html(rps.objList[2].length);
                    $.each(rps.objList[2],function(idx,itm){
                        var h='<li id="li_'+itm.ref+'">';
                        h+='<img src="'+itm.headimage+'" width="80" height="80">'+itm.realname+'<b>'+itm.subjectname+'</b>';
                        h+='<p class="ico"><a class="ico34" href="javascript:void(0);" onclick="delClassUser(\''+itm.ref+'\','+clsid+')" title="移出"></a></p>';
                        h+='</li>';
                        $("#ul_tea").append(h);
                    });
                }

                if(rps.objList[3]!=null){
                    $("#stu_count").html(rps.objList[3].length);
                    $.each(rps.objList[3],function(idx,itm){
                        var h='<li id="li_'+itm.ref+'">';
                        h+='<img src="'+itm.headimage+'" width="80" height="80" />'+itm.realname;
                        h+='<p class="ico"><a class="ico34" href="javascript:void(0);" onclick="delClassUser(\''+itm.ref+'\','+clsid+')" title="移出"></a></p>';
                        h+='</li>';
                        $("#ul_stu").append(h);
                    });
                }
            }
        }
    });
}



/**
 * 获取班级详情
 * 修改
 * @param clsid
 */
function toUpdClass(clsid){
    if(typeof clsid=='undefined')
        return;
    showModel('dv_edit');

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
                    var clsname=rps.objList[0].classname;

                    $("#dv_edit input[id='cls_name']").val(rps.objList[0].classname);
                    $("#dv_edit select[id='type']").val(rps.objList[0].dctype);
                    $("#dv_edit select[id='year']").val(rps.objList[0].year);
                    $("#dv_edit select[id='grade']").val(rps.objList[0].classgrade);
                    $("#dv_edit select[id='bzr']").val(rps.objList[1].ettuserid);
                    if(typeof rps.objList[0].clsnum !='undefined' )
                        $("#dv_edit input[id='num']").val(rps.objList[0].clsnum);
                    if(typeof rps.objList[0].verifyTimeString !='undefined' )
                        $("#dv_edit input[id='verify_time']").val(rps.objList[0].verifyTimeString);
                    if(typeof rps.objList[0].allowjoin !='undefined' )
                        $("#dv_edit input[name='rdo'][value='"+rps.objList[0].allowjoin+"']").attr("checked",true);
                    $("#dv_edit input[id='invite_code']").next("span").remove();
                    if(rps.objList[0].dctype==2&&schoolid=="1"){
                        $("#dv_edit tr[id='tr_invite']").show();
                        if(typeof rps.objList[0].invitecode !='undefined'&&rps.objList[0].invitecode.toString().length>0){
                            $("#dv_edit input[id='invite_code']").after('<span>'+rps.objList[0].invitecode+'</span>');
                            $("#dv_edit a[id='btn_invitecode']").hide();
                            $("#dv_edit input[id='invite_code']").hide();
                        }else{
                            $("#dv_edit a[id='btn_invitecode']").show();
                            $("#dv_edit input[id='invite_code']").empty();
                            $("#dv_edit input[id='invite_code']").show();
                        }
                    }else{
                        $("#dv_edit a[id='btn_invitecode']").show();
                        $("#dv_edit input[id='invite_code']").empty();
                        $("#dv_edit tr[id='tr_invite']").hide();
                    }


                    //添加确认Click
                    $("#a_sub_upd").attr("href","javascript:sub_cls("+rps.objList[0].classid+")");

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
    var allowJoin=$("#"+dvObj+" input[name='rdo']:checked");
    var invitecode=$("#"+dvObj+" input[id='invite_code']");

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
    if(allowJoin.length>0&&allowJoin.val().length>0)
        param.allowJoin=allowJoin.val();
    if(num.val().length>0)
        param.num=num.val();
    if(schoolid=="1"&&invitecode.val().length>0)
        param.invitecode=invitecode.val().Trim();

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
            $("#btn_invitecode").show();
        },success:function(rps){
            alert(rps.msg);
            if(rps.type=='success'){
                closeModel(dvObj);
                $("#dv_add input[id='btn_invitecode']").empty();
                loadCls(1,clsid=='undefined'?rps.objList[0]:clsid);
            }
        }
    });
}




/**
 * 删除班级
 * @param clsid
 */
function doDelCls(clsid){
    if(typeof clsid=='undefined')
        return;
    if(!confirm('确认删除班级?\n\n提示：删除后无法恢复!'))return;
    $.ajax({
        url: 'tpuser?m=doDelCls',
        type: 'post',
        data: {clsid:clsid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                loadCls(1);
            }
        }
    });
}

/**
 * 生成公共家长邀请码
 */
function genderInviteCode(dvObj){
    $.ajax({
        url: 'tpuser?genderInviteCode',
        type: 'post',
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList[0]!=null&&rps.objList[0].toString().length>0){
                    $("#"+dvObj+" input[id='invite_code']").val(rps.objList[0]);
                    $("#"+dvObj+" a[id='btn_invitecode']").hide();
                }
            }
        }
    });
}
