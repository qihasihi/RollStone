/**************************/


/**
 * 资源上传---资源库
 * @param p
 */
function beforeAjaxCourseList(p) {
    var values = "";
    var resname = "";
    resname = $("#searchValue").val();
    var param = {};

    if (lb.params.subjectvalues != null && lb.params.subjectvalues.Trim().length > 0) {
        param.subjectvalues = lb.params.subjectvalues;
    }
    if (lb.params.gradevalues != null && lb.params.gradevalues.Trim().length > 0) {
        param.gradevalues = lb.params.gradevalues;
    }
    if (lb.params.restypevalues != null && lb.params.restypevalues.Trim().length > 0) {
        param.restypevalues = lb.params.restypevalues;
    }
    if (lb.params.filetypevalues != null && lb.params.filetypevalues.Trim().length > 0) {
        param.filetypevalues = lb.params.filetypevalues;
    }
    if (lb.params.timerange != null && lb.params.timerange.Trim().length > 0) {
        param.timerange = lb.params.timerange;
    }
    if(lb.params.sharestatusvalues!=null&&(lb.params.sharestatusvalues+"").length>0){
        param.sharestatusvalues = lb.params.sharestatusvalues;
    }

    if (resname.Trim().length > 0) {
        param.resname = resname.Trim();
    }
    /*关联教材的年级学科查询资源*/
    param.isunion=1;
    param.sharestatus=1;    /*允许本地共享*/
    param.currentcourseid=courseid;
    p.setPostParams(param);
}


function afterAjaxCourseList(rps) {
    var html = "";
    if (rps != null && rps.presult.list.length > 0) {
        $.each(rps.objList, function (idx, itm) {
            html += '<tr>';
            html += '<td>';
            if(itm.resflag>0)
                html+='<input type="checkbox" disabled checked>';
            else
                html+='<input type="checkbox"  name="ck_course_resource" value=' + itm.resid + '>';
            html += '<span class="' + itm.suffixtype + '"></span></td>';
            html += '<td><p><a href="resource?m=todetail&resid=' + itm.resid + '" target="_blank">' + itm.resname + '</a></p>';
            html += '<p>' + (itm.resintroduce == null ? "" : itm.resintroduce) + '</p>';
            html += '<p class="jxxt_zhuanti_zy_add_text">' + (typeof itm.username == 'undefined' ? "" : itm.username) + '&nbsp;&nbsp;&nbsp;' + itm.restypename + '、' + itm.filetypename + '&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>' + itm.clicks + '</b><span class="ico59" title="下载"></span><b>' + itm.downloadnum + '</b><span class="ico41" title="赞"></span><b>' + itm.praisenum + '</b></p></td>';
            html += '</tr>';
        });
    } else {
        html += "<tr>";
        html += "<td></td><p><strong>暂无数据！</strong></p>";
        html += "</tr> ";
    }

    p1.setPageSize(rps.presult.pageSize);
    p1.setPageNo(rps.presult.pageNo);
    p1.setRectotal(rps.presult.recTotal);
    p1.setPagetotal(rps.presult.pageTotal);
    p1.Refresh();
    $("#resData").html(html);
}


/**
 * 资源上传---查询专题下的资源
 * @param cid 当前专题ID
 */
function load_courseres_detail(cid) {
    if (!cid)return;
    $("#hd_courseid").val(cid);

    $.ajax({
        url: 'tpres?m=ajaxCourseList',
        type: 'post',
        data: {
            courseid: cid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                var htm = '';
                if (rps.objList.length && rps.objList[0] != null) {
                    var type = rps.objList[0].courselevel == 3 ? "自建专题" : "标准/共享专题";
                    var name = typeof rps.objList[0].teachername != "undefined" ? rps.objList[0].teachername : "北京四中网校";
                    var materialname = typeof rps.objList[0].materialname == 'undefined' ? "" : "(" + rps.objList[0].materialname + ")";
                    $("#sp_type").html(type);
                    $("#sp_author").html(name);
                    $("#sp_grade").html(rps.objList[0].gradename);
                    $("#p_coursename").html(rps.objList[0].coursename + materialname);
                    for (var i = 1; i < 6; i++) {
                        if (rps.objList[0].avgscore >= i)
                            htm += "<span class='ico_star1'></span>";
                        else if (rps.objList[0].avgscore < i && rps.objList[0].avgscore > i - 1)
                            htm += "<span class='ico_star2'></span>";
                        else
                            htm += "<span class='ico_star3'></span>";
                    }
                    $("#sp_star").html(htm);
                }
                $("#p_upload_course1").hide();
                $("#p_upload_course2").show();
                pageGo("pCourse");
            }
        }
    });
}


/**
 * 资源上传---加载专题与资源数量
 */
function load_course() {
    var sel_grade = $("#sel_grade").val();
    var sel_material = $("#sel_material").val();
    var sel_courselevel = $("#sel_courselevel").val();
    var txt_course = $("#txt_course").val();


    $.ajax({
        url: 'tpres?m=ajaxCourseList',
        type: 'post',
        data: {
            gradeid: sel_grade,
            materialid: sel_material,
            courselevel: sel_courselevel,
            coursename: txt_course
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                var htm = '';
                if (rps.objList.length) {
                    $.each(rps.objList, function (idx, itm) {
                        if (itm.courseid == courseid)return;
                        var tname = typeof itm.teachername == 'undefined' ? "N/N" : itm.teachername;
                        htm += '<tr>';
                        htm += '<td><p><a href="javascript:load_courseres_detail(\'' + itm.courseid + '\')">' + itm.coursename + "(" + itm.materialname + ")" + '</a></p></td>';
                        htm += '<td><span class="font-red">' + itm.resnum + '</span></td>';
                        htm += '<td>' + tname + '</td>';
                        htm += '<td>' + itm.gradename + '</td>';
                        htm += '<td>';
                        for (var i = 1; i < 6; i++) {
                            if (itm.avgscore >= i)
                                htm += "<span class='ico_star1'></span>";
                            else if (itm.avgscore < i && itm.avgscore > i - 1)
                                htm += "<span class='ico_star2'></span>";
                            else
                                htm += "<span class='ico_star3'></span>";
                        }
                        htm += '</td>';
                        htm += '</tr>';
                    });
                }
                $("#tbl_course").html(htm);
            }
        }
    });
}

/**
 * 资源上传---专题资源
 * @param p
 */

function beforeQryCourseList(p) {
    var sel_grade = $("#sel_grade").val();
    var sel_material = $("#sel_material").val();
    var sel_courselevel = $("#sel_courselevel").val();
    var txt_course = $("#txt_course").val();
    var param = {
        gradeid: sel_grade,
        materialid: sel_material,
        courselevel: sel_courselevel,
        coursename: txt_course,
        subjectid: courseSubject,
        currentcourseid:courseid
    };
    p.setPostParams(param);
}

function afterQryCourseList(rps) {
    var htm = "";
    if (rps != null && rps.presult.list.length > 0) {
        $.each(rps.objList, function (idx, itm) {
            if (itm.courseid == courseid)return;
            var tname = typeof itm.teachername == 'undefined' ? "北京四中网校" : itm.teachername;
            var versionname= typeof itm.versionname == 'undefined'||itm.versionname.length<1 ? "" :itm.versionname;
            var materialname = typeof itm.materialname == 'undefined'||itm.materialname.length<1 ? "" : "(" + itm.materialname+versionname + ")";

            htm += '<tr>';
            htm += '<td><p><a href="javascript:load_courseres_detail(\'' + itm.courseid + '\')">' + itm.coursename + materialname + '</a></p></td>';
            htm += '<td><span class="font-red">' + itm.resnum + '</span></td>';
            htm += '<td>' + tname + '</td>';
            htm += '<td>' + itm.gradename + '</td>';
            htm += '<td>';
            for (var i = 1; i < 6; i++) {
                if (itm.avgscore >= i)
                    htm += "<span class='ico_star1'></span>";
                else if (itm.avgscore < i && itm.avgscore > i - 1)
                    htm += "<span class='ico_star2'></span>";
                else
                    htm += "<span class='ico_star3'></span>";
            }
            htm += '</td>';
            htm += '</tr>';
        });
    } else {
        htm += "<tr><td>暂无数据！</td></tr>";
    }
    $("#tbl_course").html(htm);

    pCourseRes.setPageSize(rps.presult.pageSize);
    pCourseRes.setPageNo(rps.presult.pageNo);
    pCourseRes.setRectotal(rps.presult.recTotal);
    pCourseRes.setPagetotal(rps.presult.pageTotal);
    pCourseRes.Refresh();
}


/**
 * 资源上传---专题资源库
 * @param p
 */

function beforeAjaxCourseResList(p) {

    var param = {};
    var selcourseid = $("#hd_courseid").val();
    param.courseid = selcourseid;
    param.currentcourseid=courseid;
    p.setPostParams(param);
}

function afterAjaxCourseResList(rps) {
    var html = "";
    if (rps != null && rps.presult.list.length > 0) {
        $.each(rps.objList, function (idx, itm) {
            html += '<tr>';
            html += '<td>';
            if(itm.resflag>0)
                html+='<input type="checkbox" disabled checked>';
            else
                html+='<input type="checkbox"  name="ck_course_resource" value=' + itm.resid + '>';
            html+='<span class="' + itm.suffixtype + '"></span></td>';
            html += '<td>';
            html += '<p><a href="resource?m=todetail&resid=' + itm.resid + '" target="_blank">' + itm.resname + '</a></p>';
            html += '<p>' + (itm.resintroduce == null ? "" : itm.resintroduce) + '</p>';
            html += '<p class="jxxt_zhuanti_zy_add_text">' + (typeof itm.username == 'undefined' ? "--" : itm.username) + '&nbsp;&nbsp;&nbsp;' + itm.restypename + ',' + itm.filetypename + '&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>' + itm.clicks + '</b><span class="ico59" title="下载"></span><b>' + itm.downloadnum + '</b><span class="ico41" title="赞"></span><b>' + itm.praisenum + '</b></p></td>';
            html += '</tr>';
        });
    } else {
        html += "<tr><td></td><p>暂无数据!</p></tr>";
    }

    pCourse.setPageSize(rps.presult.pageSize);
    pCourse.setPageNo(rps.presult.pageNo);
    pCourse.setRectotal(rps.presult.recTotal);
    pCourse.setPagetotal(rps.presult.pageTotal);
    pCourse.Refresh();
    $("#courseResData").html(html);
    $("#dv_detail_course").hide();
}

/**
 * 资源上传---资源库上传
 */

function sub_res(dvobj,resourcetype) {
    //var resourcetype = $("input[name='tp_res_type']:checked").val();
    var resObj = $("#" + dvobj + " input[name='ck_course_resource']:checked");
    if (resObj.length < 1) {
        alert("请选择资源!");
        return;
    }


    var resArray = '';
    $.each(resObj, function (idx, itm) {
        if (resArray.length > 0)
            resArray += ",";
        resArray += itm.value;
    });

    $.ajax({
        url: 'tpres?m=addResource',
        type: 'post',
        data: {
            courseid: courseid,
            resArray: resArray,
            resourcetype: resourcetype
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                if (typeof operate_type!='undefined' && operate_type.length>0) {
                    window.returnValue = resArray.split(",")[0];
                    window.close();
                }
                hideUploadDiv();
                usertype==2?load_resource(resourcetype,1):load_stu_resource(1,1);
            }
        }
    });
}

function hideUploadDiv() {
    genderHtml();
    //window.reload();
}

function genderHtml() {
    /* var collecthtm=usertype==1?'<span id="sp_collect"></span>':'';
     var htm=['<h1 id="h1_resname"></h1>','<div class="right">',
     '<div id="dv_res_detail">',
     '<p><span class="font-black">发 布 者：</span></p>',
     '<p><span class="font-black">发布时间</span></p>',
     '<p><span class="font-black">资源简介：</span></p>',
     '<p></p>',
     '</div>',
     '<p class="number">',
     collecthtm,
     '<span id="sp_download"></span><span class="ico46" title="浏览"></span><span id="sp_viewcount"></span>',
     '</p>',
     '</div>',
     '<div class="left" id="div_show"></div>',
     '<div class="jxxt_zhuanti_zy_pl public_input">',
     '<ul id="ul_comment">',
     '<li id="li_studynote"><a href="javascript:loadStudyNotes(\''+usertype+'\');">学习心得</a></li>',
     '<li  class="crumb"><a href="javascript:loadAllComment()">所有评论</a></li>',
     '</ul>',
     '<div class="fabiao" id="div_xheditor">',
     '</div>',
     '<div   id="dv_study_note" >',
     '</div>',
     '<div id="div_comment_htm">',
     '</div>',
     '<form id="pListForm" name="pListForm">',
     '<p class="Mt20" id="pListaddress" align="center"></p>',
     '</form>',
     '</div>'];
     $("#dv_obj").html(htm.join(''));*/

    $("#dv_obj").html('');
    $("#dv_init").show();
}


/**
 * 资源上传---查询教材
 */
function load_material() {
    var gradeid = $("#sel_grade").val();
    if (gradeid.length < 1)
        return;

    $.ajax({
        url: 'teachingmaterial?m=getTchingMaterialList',
        type: 'post',
        data: {
            gradeid: gradeid,subjectid:courseSubject
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                var htm = '<option value="">==请选择教材版本==</option>';
                if (rps.objList.length) {
                    //htm+='<option value="">全部</option>';
                    $.each(rps.objList, function (idx, itm) {
                        var versionname=typeof itm.versionname !='undefined'&&itm.versionname.length>0?'('+itm.versionname+')':'';
                        htm += '<option value="' + itm.materialid + '">' + itm.subjectname + itm.materialname +versionname+ '</option>';
                    })
                }
                $("#sel_material").html(htm);
            }
        }
    });
}



/**
 * 控制上传DIV
 */
function showUploadDiv(usertype) {
    $("#dv_init").hide();
    $("#dv_obj").load("tpres?m=loadUpload&courseid=" + courseid + "&usertype=" + usertype);
}

function loadCheckPage() {
    $("#dv_init").hide();
    $("#dv_obj").load("tpres?toResourceCheckList&courseid=" + courseid);
}

function loadStuResourcePage(courseid) {
    $("#dv_init").hide();
    $("#dv_obj").load("tpres?toStuResourceList&courseid=" + courseid);
}

/**
 * 取消收藏资源
 * @param ref
 */
function doDelOneResource(ref,resid) {
    if (typeof(ref) == "undefined" || isNaN(ref)) {
        alert('异常错误!未获取到资源收藏标识!');
        return;
    }
    if (!confirm("确认操作?")) {
        return;
    }

    $.ajax({
        url: 'tpres?doCancelCollect',
        type: 'post',
        data: {
            ref: ref,
            resid:resid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                pageGo("pList");
            }
        }
    });

}


/**
 *删除学生资源
 */
function doDelStuResource(ref) {
    if (typeof(ref) == "undefined" || ref.length < 1) {
        alert('异常错误!未获取到资源详情标识!');
        return;
    }
    if (!confirm("您确认删除该资源?")) {
        return;
    }

    $.ajax({
        url: 'tpres?doDelOneResource',
        type: 'post',
        data: {
            ref: ref
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                pageGo("pStuList");
            }
        }
    });

}


/**
 * 切换选项卡
 * @param dvid
 */
function changeTab(dvid) {
    $("div").filter(function () {
        return this.id.indexOf("dv_upload_") != -1
    }).hide();
    $("#" + dvid).show();
    $("li").filter(function () {
        return this.id.indexOf("li_dv_upload_") != -1
    }).removeClass();
    $("#li_" + dvid).addClass("crumb");
}


/**
 * 普通文件上传
 */

function uploadResource() {
    var t = document.getElementById('uploadfile');
    if (t.value.Trim().length < 1) {
        alert('您尚未选择文件，请选择！');
        return;
    }
    var lastname = t.value.substring(t.value.lastIndexOf("."));
    $.ajaxFileUpload({
        url: url + "&lastname=" + lastname,
        fileElementId: 'uploadfile',
        dataType: 'json',
        secureuri: false,
        type: 'POST',
        success: function (data, status) {
            if (typeof(data.error) != 'undefined') {
                if (data.error != '') {
                    alert(data.error);
                } else {
                    alert(data.msg);
                }
            } else {
            }
        },
        error: function (data, status, e) {
            alert(e);
        }
    });
}


/**
 * 上传资源 usertype 1 学生2教师
 *
 * @return
 */
function doUploadResource(usertype) {
    var resname = $("#res_name");
    var restype = $("input[name='res_type']:checked");
    var crestype = $("input[name='tp_res_type']:checked").val();
    var resintroduce = $("#res_remark");
    var uploadfile = $("#uploadfile");
    var resid = nextid;
    var allowTypes='.xls,.xlsx,.doc,.docx,.ppt,.pptx,.xml,.pdf,.txt,.vsd,.rtf',msg='数据验证完毕!确认提交?';

    if (typeof resid == 'undefined') {
        alert('异常错误!系统未获取到资源标识!');
        return;
    }
    if (typeof usertype == 'undefined') {
        alert('错误的用户类型!');
        return;
    }
    if (courseid.length < 1) {
        alert('异常错误,系统未获取到专题标识!');
        return;
    }

    if (resname.val().Trim().length < 1) {
        alert('请填写资源名称!');
        resname.focus();
        return;
    }

    if (restype.length < 1) {
        alert('请选择资源类型!');
        return;
    }

    var fname = '';
    if (uploadfile.val().length < 1) {
        if (typeof(uploadControl.fileAttribute) == 'undefined' || uploadControl.fileAttribute.length < 1) {
            alert('请添加上传文件!');
            return;
        }
        $.each(uploadControl.fileAttribute, function (ix, im) {
            if (typeof(im) != "undefined" && im != undefined && im.filename.Trim().length > 0) {
                if (fname.Trim().length > 0)
                    fname += '|';
                fname += im.filename.Trim() + '*' + im.size;
            }
        })
        if (fname.length < 1) {
            alert('异常错误，原因：请添加文件!');
            return;
        }

        var param = {
            resid: resid,
            resname: resname.val(),
            restype: restype.val(),
            resourcetype: crestype,
            resintroduce: resintroduce.val(),
            courseid: courseid,
            usertype: usertype,
            filename: fname
        };
        var lastname=fname.substring(fname.lastIndexOf("."));
        var allowTypeArray=allowTypes.split(",");
        var returnVal=false;
        for(var n=0;n<allowTypeArray.length;n++){
            var altype=allowTypeArray[n];
            if(altype.length>0){
                if(lastname.toLowerCase()==altype.toLowerCase()){
                    returnVal=true;
                }
            }
        }
        if(returnVal){
            msg+='\n\n提示：当前文件需要转换请等待!';
        }

        if (!confirm(msg)) {
            return;
        }
        /*提交按钮不可用*/
        $("#a_submit").remove();
        $.ajax({
            url: 'tpres?doUploadResource',
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
                    alert(rps.msg);
                    if (operate_type.length) {
                        window.returnValue = nextid;
                        window.close();
                    }
                    $("#dv_obj").html('');
                    usertype == 2 ? load_resource(crestype, 1) : loadStuResourcePage(courseid);//load_stu_resource(1, 1)
                    // nextid=rps.objList[0];
                }
            }
        });
    } else {
        fname = uploadfile.val();
        var t = document.getElementById('uploadfile');
        if (t.value.Trim().length < 1) {
            alert('您尚未选择文件，请选择！');
            return;
        }
        var lastname = t.value.substring(t.value.lastIndexOf("."));
        $.ajaxFileUpload({
            url: url + "&lastname=" + lastname,
            fileElementId: 'uploadfile',
            dataType: 'json',
            secureuri: false,
            type: 'POST',
            success: function (data, status) {
                if (typeof(data.error) != 'undefined') {
                    if (data.error != '') {
                        alert(data.error);
                    } else {
                        alert(data.msg);
                    }
                } else {
                    var param = {
                        resid: resid,
                        resname: resname.val(),
                        restype: restype.val(),
                        resourcetype: crestype,
                        resintroduce: resintroduce.val(),
                        courseid: courseid,
                        usertype: usertype,
                        filename: fname
                    };


                    var allowTypeArray=allowTypes.split(",");
                    var returnVal=false;
                    for(var n=0;n<allowTypeArray.length;n++){
                        var altype=allowTypeArray[n];
                        if(altype.length>0){
                            if(lastname.toLowerCase()==altype.toLowerCase()){
                                returnVal=true;
                            }
                        }
                    }
                    if(returnVal){
                        msg+='\n\n提示：当前文件需要转换请等待!';
                    }

                    if (!confirm(msg)) {
                        return;
                    }
                    /*提交按钮不可用*/
                    $("#a_submit").remove();
                    $.ajax({
                        url: 'tpres?doUploadResource',
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
                                alert(rps.msg);
                                if (operate_type.length) {
                                    window.returnValue = nextid;
                                    window.close();
                                }
                                $("#dv_obj").html('');
                                usertype == 2 ? load_resource(crestype, 1) : loadStuResourcePage(courseid);//load_stu_resource(1, 1)
                                // nextid=rps.objList[0];
                            }
                        }
                    });
                }
            },
            error: function (data, status, e) {
                alert(e);
            }
        });
    }

}
/*教师资源首页*/


/**
 * 加载专题资源
 * ajax
 */
function load_resource(type, pageno, isinit) {
    if (typeof courseid == 'undefined')
        return;
    $("li").filter(function () {
        return this.id.indexOf("li_nav") != -1
    }).removeClass("crumb");
    $('#li_nav_' + type + '').addClass("crumb");

    $.ajax({
        url: 'tpres?m=ajaxCourseResList&pageResult.pageNo=' + pageno + "&pageResult.pageSize=10&t=" + new Date().getTime(),
        type: 'post',
        data: {
            resourcetype: type,
            courseid: courseid,
            resstatus: 1
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                $("#ul_resource").html('');
                if (rps.objList.length) {
                    $.each(rps.objList, function (idx, itm) {
                        var filename = itm.resname + itm.filesuffixname, dvhtm = '', htm = '';
                        if (type == 1) {
                            //<p class="c"><a href="1" class="ico11" title="编辑"></a><a href="1" class="ico04" title="删除"></a><a href="1" class="ico51" title="发任务"></a><a href="1" class="ico46" title="学生可见"></a><a href="1" class="ico47" title="学生不可见"></a></p>

                            if (itm.resdegree == 3) {
                                dvhtm += '<p class="c" style="display: none;">';
                                dvhtm += '<a  class="ico11" title="编辑" href="javascript:toUpdResource(\'' + itm.resid + '\')"></a>';
                                if (itm.taskflag < 1) {
                                    dvhtm += '<a  class="ico04" title="删除" href="javascript:doDelResource(\'' + itm.resid + '\')"></a>';
                                    dvhtm += '<a  class="ico51" title="发任务" href="task?toAddTask&courseid=' + courseid + '&tasktype=1&taskvalueid=' + itm.resid + '"></a>';
                                }
                                dvhtm += '</p>';
                                if (itm.usertype == 1)
                                    dvhtm += '<p class="b"><span class="ico35"></span></p>';
                            } else {
                                dvhtm += '<p class="c" style="display: none;">';
                                if (itm.taskflag < 1) {
                                    dvhtm += '<a  class="ico11" title="编辑" href="javascript:toUpdResource(\'' + itm.resid + '\')"></a>';
                                    dvhtm += '<a  class="ico51" title="发任务" href="task?toAddTask&courseid=' + courseid + '&tasktype=1&taskvalueid=' + itm.resid + '"></a>';
                                    dvhtm += '<a  class="ico04" title="删除" href="javascript:doDelResource(\'' + itm.resid + '\')"></a>';
                                }
                                dvhtm += '</p>';
                                dvhtm += '<p class="b"><span class="ico44" title="参考"></span></p>';
                            }


                            htm += '<li id="li_' + itm.resid + '" >';
                            htm += '<p class="a">';
                            if (itm.taskflag < 1)
                                htm += '<span class="' + itm.suffixtype + '"></span>';
                            else {
                                htm += '<span class="' + replaceAll(itm.suffixtype, "1", "2") + '"></span>';
                            }
                            htm += '</p>';

                            if (itm.resourseType == "other") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '"  href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',0,\'\',\'\',\'\',' + itm.resid + ',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "doc") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',4,\'\',\'\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'' + itm.swfpath + '\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "jpeg") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',1,\'' + itm.imgpath + '\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "video") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',2,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "swf") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',5,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "mp3") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',3,\'\',\'001\',\'\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            htm += '</li>';

                            $("#ul_resource").append(htm);
                            $("#ul_resource").hide();
                            $("#ul_resource").show('fast');
                            var liObj = $("ul[id='ul_resource'] li").eq(idx);
                            if (liObj) {
                                $(liObj).hover(
                                    function () {
                                        $(this).children(".c").show();
                                    },
                                    function () {
                                        $(this).children(".c").hide();
                                    }
                                )
                            }

                        } else {

                            dvhtm += '<p class="c" style="display: none;">';
                            dvhtm += '<a  class="ico11" title="编辑" href="javascript:toUpdResource(\'' + itm.resid + '\')"></a>';
                            dvhtm += '<a  class="ico04" title="删除" href="javascript:doDelResource(\'' + itm.resid + '\')"></a>';
                            dvhtm += '<a  class="ico46" title="添加到学习资源" href="javascript:doUpdResType(' + itm.ref + ')"></a>';
                            dvhtm += '</p>';

                            htm += '<li id="li_' + itm.resid + '" >';
                            htm += '<p class="a"><span class="' + itm.suffixtype + '"></span></p>';
                            if (itm.resourseType == "other") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',0,\'\',\'\',\'\',' + itm.resid + ',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "doc") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',4,\'\',\'\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'' + itm.swfpath + '\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "jpeg") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',1,\'' + itm.imgpath + '\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "video") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',2,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "swf") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',5,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "mp3") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',3,\'\',\'001\',\'\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            htm += '</li>';

                            $("#ul_resource").append(htm);
                            $("#ul_resource").hide();
                            $("#ul_resource").show('fast');

                            var liObj = $("ul[id='ul_resource'] li").eq(idx);
                            if (liObj) {
                                $(liObj).hover(
                                    function () {
                                        $(this).children(".c").show();
                                    },
                                    function () {
                                        $(this).children(".c").hide();
                                    }
                                )
                            }
                        }
                    });
                }

                if (typeof(tpresdetailid) != 'undefined' && tpresdetailid.toString().length > 0) {
                    if( $('a[id="a_for_' + tpresdetailid + '"]').length>0)
                        $('a[id="a_for_' + tpresdetailid + '"]').get(0).click();
                    //eval(f);
                } else {
                    var aObj = $('a').filter(function () {
                        return this.id.indexOf('a_for_') != -1
                    });
                    if (isinit && aObj.length > 0)
                        $(aObj).get(0).click();
                }

                //分页
                $("#p_xnrj_page").html('');
                if (rps.presult.recTotal > 0 && rps.presult.pageTotal > 1) {
                    var prev = (pageno - 1) > 0 ? (pageno - 1) : 1;
                    var next = (pageno + 1) >= rps.presult.pageTotal ? rps.presult.pageTotal : (pageno + 1);
                    var page_split = '<span><a href="javascript:load_resource(' + type + ',1)"><b class="first"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + prev + ')"><b class="before"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + next + ')"><b class="after"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + rps.presult.pageTotal + ')"><b class="last"></b></a></span>';
                    $("#p_xnrj_page").html(page_split);
                }
            }
        }
    });
}


/**
 * 加载专题资源
 * ajax
 */
function load_stu_resource(type, pageno, isinit) {
    if (typeof courseid == 'undefined')
        return;
    $.ajax({
        url: 'tpres?m=ajaxCourseResList&pageResult.pageNo=' + pageno + "&pageResult.pageSize=10&t=" + new Date().getTime(),
        type: 'post',
        data: {
            resourcetype: type,
            courseid: courseid,
            resstatus: 1
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                $("#ul_resource").html('');
                if (rps.objList.length) {
                    $.each(rps.objList, function (idx, itm) {
                        var filename = itm.resname + itm.filesuffixname, dvhtm = '', htm = '';
                        if (type == 1) {
                            if (itm.resdegree == 3) {
                                if (itm.usertype == 1)
                                    dvhtm += '<p class="b"><span class="ico35"></span></p>';
                            } else {
                                dvhtm += '<p class="b"><span class="ico44" title="参考"></span></p>';
                            }


                            htm += '<li id="li_' + itm.resid + '" >';
                            htm += '<p class="a">';
                            if (itm.taskflag < 1)
                                htm += '<span class="' + itm.suffixtype + '"></span>';
                            else {
                                htm += '<span class="' + replaceAll(itm.suffixtype, "1", "2") + '"></span>';
                            }
                            htm += '</p>';

                            if (itm.resourseType == "other") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',0,\'\',\'\',\'\',' + itm.resid + ',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "doc") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',4,\'\',\'\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'' + itm.swfpath + '\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "jpeg") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',1,\'' + itm.imgpath + '\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "video") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',2,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "swf") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',5,\'\',\'001\',\'' + itm.filesize + '\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            if (itm.resourseType == "mp3") {
                                htm += '<p id="p_' + itm.resid + '"><a id="a_for_' + itm.resid + '" href="javascript:void(0);" onclick="showResource(\'' + itm.md5Id + '\',\'' + filename + '\',\'div_show\',3,\'\',\'001\',\'\',\'' + itm.resid + '\',\'\',\'\',this)">' + filename + '</a></p>';
                                htm += dvhtm;
                            }
                            htm += '</li>';

                            $("#ul_resource").append(htm);
                            $("#ul_resource").hide();
                            $("#ul_resource").show('fast');

                            var liObj = $("ul[id='ul_resource'] li").eq(idx);
                            if (liObj) {
                                $(liObj).hover(
                                    function () {
                                        $(this).children(".c").show();
                                    },
                                    function () {
                                        $(this).children(".c").hide();
                                    }
                                )
                            }
                        }
                    });
                }

                if (typeof(tpresdetailid) != 'undefined' && tpresdetailid.toString().length > 0) {
                    if( $('a[id="a_for_' + tpresdetailid + '"]').length>0)
                        $('a[id="a_for_' + tpresdetailid + '"]').get(0).click();
                    //eval(f);
                } else {
                    var aObj = $('a').filter(function () {
                        return this.id.indexOf('a_for_') != -1
                    });
                    if (isinit && aObj.length > 0)
                        $(aObj).get(0).click();
                }

                //分页
                $("#p_xnrj_page").html('');
                if (rps.presult.recTotal > 0 && rps.presult.pageTotal > 1) {
                    var prev = (pageno - 1) > 0 ? (pageno - 1) : 1;
                    var next = (pageno + 1) >= rps.presult.pageTotal ? rps.presult.pageTotal : (pageno + 1);
                    var page_split = '<span><a href="javascript:load_stu_resource(' + type + ',1)"><b class="first"></b></a></span><span><a href="javascript:load_stu_resource(' + type + ',' + prev + ')"><b class="before"></b></a></span><span><a href="javascript:load_stu_resource(' + type + ',' + next + ')"><b class="after"></b></a></span><span><a href="javascript:load_stu_resource(' + type + ',' + rps.presult.pageTotal + ')"><b class="last"></b></a></span>';
                    $("#p_xnrj_page").html(page_split);
                }
            }
        }
    });
}


/**
 * 获取专题资源修改数据
 * @param resid
 */
function toUpdResource(resid,tmp_courseid) {
    if (typeof resid == 'undefined') {
        alert('异常错误!系统未获取资源ID!');
        return;
    }
    if (typeof tmp_courseid != 'undefined' && tmp_courseid.length>0)
        courseid=tmp_courseid;

    $("input[name='upd_res_type']").attr("checked", false);
    $("#upd_res_remark").html('');
    $("#hd_resid").val(resid);

    $.ajax({
        url: 'tpres?m=ajaxCourseResList',
        type: 'post',
        data: {resid: resid, courseid: courseid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                if (rps.objList.length) {
                    $.each(rps.objList, function (idx, itm) {
                        $("#upd_res_name").val(itm.resname);
                        $('input[name="upd_res_type"][value='+itm.restype+']').attr("checked", true);
                        $("#upd_res_remark").val(itm.resintroduce);
                    });
                    showModel("dv_upd_resource");
                }
            }
        }
    });
}

/**
 * 修改专题资源
 * @param resid
 */
function doUpdResource(usertype) {
    var resid = $("#hd_resid").val();
    if (typeof resid == 'undefined') {
        alert('异常错误!系统未获取资源ID!');
        return;
    }
    var resname = $("#upd_res_name").val();
    if (resname.length < 1) {
        alert("请输入资源名称!");
        return;
    }
    var restype = $("input[name='upd_res_type']:checked").val();
    if (restype.length < 1) {
        alert('请选择资源类型!');
        return;
    }
    var resintroduce = $("#upd_res_remark").val();
    var param = {resid: resid, courseid: courseid, resname: resname, usertype: usertype, restype: restype};
    if (resintroduce.length > 0)
        param.resintroduce = resintroduce;

    $.ajax({
        url: 'tpres?m=doUpdResource',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                closeModel('dv_upd_resource');
                usertype==2?load_resource(1,1):load_stu_resource(1,1);
            }
        }
    });
}

function doDelResource(resid) {
    if (typeof resid == 'undefined') {
        alert('异常错误!系统未获取资源ID!');
        return;
    }
    if (!confirm("确认删除?"))return;
    $.ajax({
        url: 'tpres?m=doDelResource',
        type: 'post',
        data: {resid: resid, courseid: courseid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                $('#li_' + resid + '').remove();
            }
        }
    });
}

/**
 * 添加到学习资源
 */
function doUpdResType(ref) {
    if (typeof ref == 'undefined') {
        alert('异常错误!系统未获取资源ID!');
        return;
    }
    if (!confirm('确认添加到学习资源?'))return;

    $.ajax({
        url: 'tpres?m=doUpdResType',
        type: 'post',
        data: {ref: ref},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                //alert(rps.msg);
                load_resource(1, 1);
            }
        }
    });
}


function genderShowdiv(htm, obj) {
    $("#div_tmp_show01").remove();
    //得到准确的 鼠标位置
    var y = mousePostion.y - 5;
    var x = mousePostion.x - 5;
    //判断是不是IE8以下的浏览器浏览
    /* if ($.browser.msie && (parseInt($.browser.version) <= 7)){
     y+=parseFloat(document.documentElement.scrollTop);
     x+=parseFloat(document.documentElement.scrollLeft);
     }*/
    //y+=5;
    var h = '<div id="div_tmp_show01" '
        + 'style="position:absolute;padding:5px;border:1px solid green;left:'
        + x + 'px;top:' + y + 'px;background-color:white">' + htm + '</div>';
    $("body").append(h);
//    $("#div_tmp_show01").bind("mouseout",function(){
//       $(this).remove();
//    });
}


function load_resdetail(resid) {
    if (typeof  resid == 'undefined')
        return;
    $.ajax({
        url: 'resource?m=ajaxList',
        type: 'post',
        data: {resid: resid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var htm = "";
            $("#h1_promt").html('');
            if (rps.objList.length) {
                $.each(rps.objList, function (idx, itm) {
                    htm += '<p><span class="font-black">发 布 者：</span>' + (typeof itm.username == 'undefined' ? "" : itm.username) + '</p>';
                    htm += '<p><span class="font-black">发布时间：</span>' + itm.ctimeString + '</p>';
                    htm += '<p><span class="font-black">资源简介：</span></p>';
                    htm += '<div style="height:350px;overflow-x: auto">';
                    htm += '<p style="word-wrap:break-word;word-break: break-all;">' + (typeof itm.resintroduce == 'undefined' ? "" : replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","")) + '</p>';
                    htm +='</div>';
                    /*if(typeof itm.username != 'undefined'&&itm.username.toString().length>0
                            &&itm.username=='北京四中网校')
                        $("#h1_promt").html("当前资源不支持在线预览，请下载后查看!");*/
                });
            }
            $("#dv_res_detail").html(htm);
        }
    });
}


/*学生资源首页*/


/**
 * 转换。
 *
 * @param fileSize
 *            单位为 KB
 */
function toUpSizeConvert(fileSize) {
    var returnVal = null;
    if ((fileSize / 1024) < 1024) {
        var temp = fileSize / 1024;
        var tempArray = temp.toString().split(".");
        if (tempArray.length > 1) {
            returnVal = tempArray[0] + "." + tempArray[1].substring(0, 2) + "K";
        } else
            returnVal = tempArray[0] + "K";
    } else if (((fileSize / 1024) > 1024)
        && ((fileSize / (1024 * 1024)) < 1024)) {
        var temp = fileSize / (1024 * 1024);
        var tempArray = temp.toString().split(".");
        if (tempArray.length > 1) {
            returnVal = tempArray[0] + "." + tempArray[1].substring(0, 2) + "M";
        } else
            returnVal = tempArray[0] + "M";
    } else {
        var temp = fileSize / (1024 * 1024 * 1024);
        var tempArray = temp.toString().split(".");
        if (tempArray.length > 1) {
            returnVal = tempArray[0] + "." + tempArray[1].substring(0, 2) + "G";
        } else
            returnVal = tempArray[0] + "G";
    }
    return returnVal;
}


function swfobjPlayer(resmd5id, filemd5name, playeraddressid, isshow, lastname, filetype, imagepath,resid) {
    // 清空
    // 由于SWFplayer生成的时候，是替换DIV层。如果想重新生成一个,需重新生成DIV并且删除SWFPlayer
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']").parent()
            .append('<div id="' + playeraddressid + '" style="width:560px;height:370px;"></div>');
    $("object[name='player1']").remove();


    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").after(
            '<div id="'+playeraddressid+'" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();

    var lastnameobj = ".mp4";
    if (lastname != null && lastname.length > 0)
        lastnameobj = lastname + lastnameobj;

    fileSystemIpPort=fileSystemIpPort.substring(0,fileSystemIpPort.indexOf("fileoperate"))+"fileoperate/";
    if(resid>0)
        fileSystemIpPort+="/clouduploadfile/";
    else
        fileSystemIpPort+="/uploadfile/";

    var filepath = fileSystemIpPort  + resmd5id + "/" + filemd5name + lastnameobj;
    if (typeof(filetype) != "undefined")
        filepath = fileSystemIpPort  + resmd5id + "/" + filemd5name+lastname;

    var flashvars = "";
    var params = {
        menu: "true",
        scale: "noScale",
        allowFullscreen: "true",
        allowScriptAccess: "always",
        bgcolor: "#FFFFFF",
        wmode: "transparent"
    };
    var attributes = {
        id: "player1",
        name: "player1"
    };
    swfobject.embedSWF(filepath, playeraddressid, "560", "500", "9.0.0", "flexpaper/swfobject/expressInstall.swf", flashvars, params, attributes);
    if (isshow)
        showModel("swfplayer", false);
}

/**
 * 加载SWF视频播放器
 *
 * @param resmd5id
 *            RES_ID标识的MD5加密
 * @param filemd5name
 *            FILE_NAME 的MD5加密[加后缀名]例：10a588b503909378cde366ef95b41d25.rmvb
 * @param playeraddressid
 *            播放器的位置
 * @return
 */
function loadSWFPlayer(resmd5id, filemd5name, playeraddressid, isshow, lastname, filetype, imagepath,resid) {
    // 清空
    // 由于SWFplayer生成的时候，是替换DIV层。如果想重新生成一个,需重新生成DIV并且删除SWFPlayer
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='player1']").remove();

    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();

    var lastnameobj = ".mp4";
    if (lastname != null && lastname.length > 0)
        lastnameobj = lastname + lastnameobj;

    fileSystemIpPort=fileSystemIpPort.substring(0,fileSystemIpPort.indexOf("fileoperate"))+"fileoperate/";
    if(resid>0)
        fileSystemIpPort+="/clouduploadfile/";
    else
        fileSystemIpPort+="/uploadfile/";

    var filepath = fileSystemIpPort  + resmd5id + "/" + filemd5name + lastnameobj;
    if (typeof(filetype) != "undefined"&&filetype.length>0)
        filepath = fileSystemIpPort   + resmd5id + "/" + filemd5name+lastname;
    // 配置参数

    var jwplayerSetup = {
        'id': 'player1',
        'width': '560',
        'height': '500',
        'file': filepath,
        'primary': 'flash',
        'controlbar': 'over',
        'controlbar.idlehide': 'true',
        'modes': [
            {type: 'flash', src: 'js/common/videoPlayer/new/jwplayer.flash.swf', //
                config: {
                    provider: "http",
                    autostart: "false",
                    menu: "false"
                }
            },
            {type: 'html5'}
        ], events: {
            onReady: function () {
                jwplayer().play();
            }
        }
    };
    /*var jwplayerSetup={
     'width': '667',
     'height': '520',
     //'playlist': "/ett20/studyrvice_class/free/toxml_jwp6.jsp?resourceID=<%=resourceID%>&frame=1",
     'file':  filepath,
     analytics: {
     cookies: false,
     enabled: false
     },
     primary: "flash",
     abouttext: "Etiantian.com",
     aboutlink: "http://www.etiantian.com",
     autostart: "false",
     events:{
     onReady:function(){
     jwplayer().play();
     }
     }
     }; */


    // 加载
    if (typeof(imagepath) != "undefined" && imagepath.Trim().length > 0)
        jwplayerSetup.image = imagepath;
    jwplayer(playeraddressid).setup(jwplayerSetup);
    if (isshow)
        showModel("swfplayer", false);
}

// ///////////////////////程序公用方法
/**
 * 下载资源准备
 *
 * @param resid
 *            资源ID (必带)
 * @param fname
 *            资源名称 (全名,如没有，则下载此资源的全部文件)
 * @return
 */
function prepareDownLoad(resid, resstate, fname) {
    if (typeof (resid) == "undefined" || resid == null || resid == "") {
        alert('异常错误!请刷新页面后重试!如多次发现类似情况，请主动联系管理员!');
        return;
    }
    // 如果fname存在
    var fnameParam = "";
    if (typeof (fname) != "undefined" && fname != null && $.trim(fname) != "") {
        fnameParam = replaceAll(encodeURI(fname), "&", "|");
    }
    var localStr = "http://202.99.47.104/resource/onefiledownload.jsp?res_id="
        + resid;

    if (fnameParam != "") {
        localStr += "&filename=" + fnameParam;
    }
    // 跳转链接
    window.open(localStr);
}

/**
 * 关闭视频层。或者图片层
 *
 * @return
 */
function closeVideoPlayer() {
    if (typeof (document.getElementById("player1")) != "undefined"
        && document.getElementById("player1") != null) {
        //document.getElementById("player1").sendEvent('PLAY', 'false');
        // 由于此播放器是替换DIV，关闭时必须重新生成一个。
        $("object[name='player1']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
        $("object[name='player1']").remove();

        $("object[name='div_show']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
        $("object[name='div_show']").remove();
    } else if (typeof (document.getElementById("b_sound")) != "undefined"
        && document.getElementById("b_sound") != null) {
        $("object[id='b_sound']").remove();
    } else if (typeof($("object[id='FlexPaperViewer']"))) {
        $("object[id='FlexPaperViewer']").after(
            '<div id="div_show" style="width:560px;height:370px;"></div>');
        $("object[id='FlexPaperViewer']").remove();
    }
    showAndHidden('swfplayer', 'hide');
    closeModel('swfplayer');
}

/**
 *
 * @param imgaddress
 * @param resmd5id
 * @param resmd5name
 * @return
 */
function previewImg(imgaddress, resmd5id, resmd5name,resid) {
    if (typeof (imgaddress) == "undefined" || imgaddress == null
        || $.trim(imgaddress) == "" || typeof (resmd5id) == "undefined"
        || resmd5id == null || $.trim(resmd5id) == ""
        || typeof (resmd5name) == "undefined" || resmd5name == null
        || $.trim(resmd5name) == "") {
        alert('异常错误!参数不正确!');
        return;
    }
    //删除SWF
    if (typeof($("object[id='FlexPaperViewer']")))
        $("object[id='FlexPaperViewer']").after(
            '<div id="div_show" style="width:560px;height:370px;"></div>');
    $("object[id='FlexPaperViewer']").remove();

    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='player1']").remove();

    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();

   // resmd5name = resmd5name.substring(0, resmd5name.lastIndexOf("."));

    fileSystemIpPort=fileSystemIpPort.substring(0,fileSystemIpPort.indexOf("fileoperate"))+"fileoperate/";
    if(resid>0)
        fileSystemIpPort+="/clouduploadfile/";
    else
        fileSystemIpPort+="/uploadfile/";


    var imgurl = fileSystemIpPort  + resmd5id + "/"
        + resmd5name + "_zhong.jpg";

    var html = "<img src=\"" + imgurl + "\" />";
    $("#" + imgaddress).html(html);
    showModel("swfplayer", false);
}



/**
 * 验证是否发任务
 * @param resid
 */
function checkConvertStatus(resid) {
    if (typeof resid == 'undefined')
        return;
    $.ajax({
        url: 'tpres?checkConvertStatus',
        type: 'post',
        data: {
            resid: resid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList != null && rps.objList.length > 0) {
                    alert(rps.msg);
                    convertDocResource(resid);
                }
            }
        }
    });
}

/**
 * 转换文档类资源
 * @param resid
 */
function convertDocResource(resid) {
    if (typeof resid == 'undefined')
        return;
    $.ajax({
        url: 'tpres?convertDocResource',
        type: 'post',
        data: {
            resid: resid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if( $('a[id="a_for_' + resid + '"]').length>0)
                    $('a[id="a_for_' + resid + '"]').get(0).click();
            }
        }
    });
}





/**
 * 显示资源
 *
 * @param divid
 *            所在DIV
 * @param type
 *            类型
 * @param preimg
 *            预览图
 * @param docmd5
 * @return
 */
function showResource(md5id, fname, divid, type, preimg, md5name, size, resid, resdetailid, swfpath, liobj) {
    //获取文件路径
    fileSystemIpPort=fileSystemIpPort.substring(0,fileSystemIpPort.indexOf("fileoperate"))+"fileoperate/";
    if(resid>0)
        fileSystemIpPort+="/clouduploadfile/";
    else
        fileSystemIpPort+="/uploadfile/";
    //转换文件
    checkConvertStatus(resid);
    genderHtml();
    var afname='<a style="color:#000000;" title="下载" href="javascript:resourceDownLoadFile(\'' + resid + '\',\'' + fname + '\',1)">'+fname+'</a>';
    $("#h1_resname").html(afname);
    //选中效果  
    $("#ul_resource li").removeClass();
    $(liobj).parent().parent().addClass("crumb");

    if (typeof(tarray[1]) != "undefined")
        clearTimeout(tarray[1]);
    var htm = '';
    $("#" + divid).removeClass();
    $("#" + divid).attr("style", "");
    $("#" + divid).css({"width": "560", "height": "500"});
    $("#hd_resdetailid").val(resid)
    //删除播放器
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']").after(
            '<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='player1']").remove();

    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").parent()
            .append('<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();
    //删除音频
    if (typeof($("object[id='b_sound']")))
        $("object[name='player1']").after(
            '<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[id='b_sound']").remove();
    //删除SWF
    if (typeof($("object[id='FlexPaperViewer']")))
        $("object[id='FlexPaperViewer']").after(
            '<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[id='FlexPaperViewer']").remove();
    var lastname = "";
    if (typeof(fname) != 'undefined' && fname.Trim().length > 0 && fname.indexOf(".") != -1)
        lastname = fname.substring(fname.lastIndexOf(".")).toLowerCase();

    if (type == 1) {
        htm = "<img src='" + preimg + "' width='560' height='370'  title='" + fname + "' id='img_res'  />";
    } else if (type == 2) {
        htm += "<div style='color:gray;font-size:10px' id='progress_1'>";
        htm += "</div>";
        videoConvertProgress(resid, fname, md5name, 1, md5id, fileSystemIpPort);
    } else if (type == 4) {
        loadSwfReview(swfpath, divid, 560, 500);
    } else if (type == 3) {
        //showMp3Resource('div_show', 0, md5name, md5id,fileSystemIpPort);
        var priviewimg='';
        loadSWFPlayer(md5id, md5name, divid, false, lastname, 'mp3','images/mp3.jpg',resid);
    } else if (type == 0) {
       // htm += '<p>' + fname + '</p>';
    } else if (type == 5) {
        //showMp3Resource('div_show', 0, md5name, md5id,fileSystemIpPort);  
        swfobjPlayer(md5id, md5name, divid, false, lastname, 'swf','',resid);
        //var path=fileSystemIpPort+"uploadfile/" + md5id + "/"+ md5name;
        //loadSwfReview(path,'div_show',560,370);
    }
    if (type != 3) {
        $("#" + divid).html(htm);
        $("#" + divid).hide();
    }
    if (type == 1) {
        var imgobj = $("#img_res").get(0);
        resizeimg(imgobj, 560, 500);
    }
    if (type != 3)
        $("#" + divid).show('slow');


    // 控制下载
    //$("#sp_download").html('<a class="ico59" title="下载" href="javascript:resourceDownLoadFile(\'' + resid + '\',\'' + fname + '\',1)"></a>');
    //$("#sp_collect").html('<a  style="color:#34a4c6" class="ico58a" title="收藏" href="javascript:doCollectResource(\'' + resid + '\');"></a>');
    $("#sp_download").html('<a  style="color:#34a4c6"  title="下载" href="javascript:resourceDownLoadFile(\'' + resid + '\',\'' + fname + '\',1)">下载</a>');
    // 收藏
    // 好评  
    if (isStudent)
        $("#sp_collect").html('<a  style="color:#34a4c6"  title="收藏" href="javascript:doCollectResource(\'' + resid + '\');">收藏</a>');
    else
        $("#sp_collect").html('<a  style="color:#34a4c6"  title="收藏" href="javascript:doCollectResource(\'' + resid + '\');">收藏</a>');
    isCollected(resid);

    // 点击量
    doAddResourceView(resid);
    loadResourceViewCount(resid);
    //收藏量
    loadResCollectCount(resid);
    //下载量
    loadResDownLoadCount(resid);

    //资源详情
    load_resdetail(resid);
    // 加载评论框
    generXheditor(resid);
    // 评论 
    loadAllComment();
    checkStudyNotes(resid);

    //学习心得
    if(typeof tpresdetailid!='undeinfed'&& tpresdetailid.toString().length>0)
        loadStudyNotes(1);
}


/**
 *添加收藏
 *@params type: 类型  1:添加收藏  2:取消收藏
 *@params resid: 资源ID
 *@params dvid:显示的地址ID
 */
function operateStore(type,resid,dvid){
    if(typeof(type)=="undefined"||type==null){
        alert('异常错误，请刷新页面后重试!原因：type is empty!');return;
    }
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    }
    var u='store?m=';
    if(type==1)
        u+='doadd';
    else
        u+='dodelete';
    $.ajax({
        url:u,
        type:'POST',
        data:{resid:resid},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                var htm='';
                if(type==1){
                    htm+='<a href="javascript:;" onclick="operateStore(2,\''+resid+'\',\'dv_store\')">';
                    htm+='取消收藏';
                    htm+='</a>';
                }else{
                    htm+='<a href="javascript:;" onclick="operateStore(1,\''+resid+'\',\'dv_store\')">';
                    htm+='收藏';
                    htm+='</a>';
                }
                $("#"+dvid).html(htm);
            }
        }
    });
}

/**
 * 验证是否发任务
 * @param resid
 */
function checkStudyNotes(resid) {
    if (typeof resid == 'undefined')
        return;

    $.ajax({
        url: 'task?checkStudyNotes',
        type: 'post',
        data: {
            resid: resid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList != null && rps.objList.length > 0) {
                    $("#li_studynote").show();
                } else {
                    $("#li_studynote").hide();
                }
            }
        }
    });
}


var tarray = new Array();
/**
 * 视频文件处理(包括两种状态: 1.未转换完成时，2.转换完成)
 * @param resid    资源标识
 * @param fname    资源正确名称
 * @param md5fname    资源MD5加密后的名称
 * @param idxstate    第几个视频资源
 * @return
 */
function videoConvertProgress(resid, fname, md5fname, idxstate, path, baseIpPort, imgpath) {
    tarray[idxstate] = null;
    if (typeof(resid) != "undefined" && resid != null &&
        typeof(fname) != "undefined" && fname != null) {
        var urlpath = "http://202.99.47.104/resource/get_video_state.jsp?jsoncallback=?";
        if (typeof(baseIpPort) != "undefined" && baseIpPort.Trim().length > 0) {
            var tmppath=baseIpPort.substring(0,baseIpPort.indexOf("fileoperate"))+"fileoperate/";
            urlpath = tmppath + "get_video_state.jsp?jsoncallback=?";
        }
        var lastname = "";
        if (typeof(fname) != 'undefined' && fname.Trim().length > 0 && fname.indexOf(".") != -1)
            lastname = fname.substring(fname.lastIndexOf(".")).toLowerCase();
        //var urlpath="http://localhost:1990/fileoperate/getvideostate.jsp?res_id="+resid+"&filename="+encodeURI(fname);
        $.getJSON(urlpath, {res_id: resid, file_name_md5: md5fname + lastname, path: path}, function (data) {
            if (data != null && data != "") {
                if (data.type == "success") {
                    var progress = data.progress;
                    if (parseFloat(progress) >= 95) {
                        if (typeof(tarray[idxstate]) != "undefined" && tarray[idxstate] != null)
                            clearTimeout(tarray[idxstate]);
                        progress = "完成";
                        //$("#img_video").unbind("click");
                        //$("#img_video").bind("click",function(){  
                        loadSWFPlayer(path, md5fname, 'div_show', false, lastname,'','',resid);
                        $("#progress_" + idxstate).html("转换进度:" + progress);
                        return;
                        //});    

                    } else {
                        progress += "%";
                        $("#img_video").unbind("click");
                        $("#img_video").bind("click", function () {
                            alert('该视频文件正在进行转换!无法进行预览!\n当前进度:' + progress + "\n\n提示：2分钟更新一次!");
                        });
                    }
                    $("#progress_" + idxstate).html("转换进度:" + progress);
                    tarray[idxstate] = setTimeout("videoConvertProgress('" + resid + "','" + fname + "','" + md5fname + "'," + idxstate + ",'" + path + "','" + baseIpPort + "','" + imgpath + "')", 1000 * 60 * 2);
                    return;
                }
            }
            $("#progress_" + idxstate).html("转换进度:排列中!");
            $("#img_video").attr({"src": "images/video_gszh.jpg", "width": "433", "height": "270"});
            tarray[idxstate] = setTimeout("videoConvertProgress('" + resid + "','" + fname + "','" + md5fname + "'," + idxstate + ",'" + path + "','" + baseIpPort + "','" + imgpath + "')", 1000 * 60 * 2);
            $("#img_video").unbind("click");
            $("#img_video").bind("click", function () {
                alert('该视频文件正在排列准备进行转换!无法进行预览!\n\n提示：2分钟更新一次!');
            });
            //tarray[idxstate]=setTimeout("videoConvertProgress('"+resid+"','"+fname+"','"+md5fname+"',"+idxstate+",'"+path+"','"+baseIpPort+"','"+imgpath+"')",1000*60*2);

        });
    }
}


function loadAllComment(isshowall) {
    $("#dv_study_note").html('');
    var valobj = $("#hd_comment_flag");
    var resdetailid = $("#hd_resdetailid").val();
    if (isshowall) {
        valobj.val(1);
        $('#ul_comment').children('li').removeClass('crumb').end().children('li').eq(1).addClass('crumb');
    } else {
        valobj.val('');
        if (resdetailid.Trim().length < 1) {
            alert('请选择资源后进行查看!');
            return;
        }
        generXheditor(resdetailid);
        $('#ul_comment').children('li').removeClass('crumb').end().children('li').eq(1).addClass('crumb');
    }

    // 显示评论框和分页按钮  
    $("#pListForm").show();
    $("#div_xheditor").show();
    pageGo("pList");
}

/**
 * 检验是否收藏过
 *
 * @param resdetailid
 * @return
 */
function isCollected(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    $.ajax({
        url: 'tpres?doCheckIsCollect',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList.length > 0)
                    $("#sp_collect").html('<a href="javascript:void(0);">已收藏</a>');
            }
        }
    });

}

/**
 * 添加收藏
 *
 * @return
 */
function doCollectResource(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    if (typeof (courseid) == "undefined") {
        alert('异常错误,系统未获取到课题标识!');
        return;
    }

    if (!confirm('确认收藏?')) {
        return;
    }
    $.ajax({
        url: 'tpres?doCollectResource',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                $("#sp_collect").html('<a href="javascript:void(0);">已收藏</a>');
            }
        }
    });

}
/**
 * 添加点击量
 *
 * @param resdetailid
 * @return
 */
function doAddResourceView(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    if (typeof (courseid) == "undefined") {
        alert('异常错误,系统未获取到专题标识!');
        return;
    }

    $.ajax({
        url: 'tpres?doAddResourceView',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            }
        }
    });
}


/**
 * 浏览量
 * @param resdetailid
 */
function loadResourceViewCount(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    if (typeof (courseid) == "undefined") {
        alert('异常错误,系统未获取到专题标识!');
        return;
    }
    $("#sp_viewcount").html('');

    $.ajax({
        url: 'tpres?loadResourceViewCount',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList.length&&rps.objList[0]!=null) {
                    $("#sp_viewcount").html(rps.objList[0]);
                }
            }
        }
    });
}

/**
 * 收藏量
 * @param resdetailid
 */
function loadResCollectCount(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    if (typeof (courseid) == "undefined") {
        alert('异常错误,系统未获取到专题标识!');
        return;
    }
    $("#sp_collect_count").html('');

    $.ajax({
        url: 'tpres?loadResourceCollectCount',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList.length&&rps.objList[0]!=null) {
                    $("#sp_collect_count").html(rps.objList[0]);
                }
            }
        }
    });
}

/**
 * 下载量
 * @param resdetailid
 */
function loadResDownLoadCount(resdetailid) {
    if (typeof (resdetailid) == "undefined") {
        alert('异常错误,系统未获取到资源标识!');
        return;
    }
    if (typeof (courseid) == "undefined") {
        alert('异常错误,系统未获取到专题标识!');
        return;
    }
    $("#sp_download_count").html('');

    $.ajax({
        url: 'tpres?loadResourceDownLoadCount',
        type: 'post',
        data: {
            resourceid: resdetailid,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if (rps.objList.length&&rps.objList[0]!=null) {
                    $("#sp_download_count").html(rps.objList[0]);
                }
            }
        }
    });
}


function qryCommentScore(resdetailid) {
    if (typeof(resdetailid) == "undefined" || resdetailid.length < 1) {
        return;
    }
    $.ajax({
        url: 'tpres?qryCommentScore',
        type: 'post',
        data: {
            resourceid: resdetailid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var startVal = "a";
            if (typeof (rps.objList[0]) != "undefined"
                && !isNaN(rps.objList[0])) {
                startVal = rps.objList[0] + "";
            }
            comment_star = creatCommentForm('div_comment', 5, true, true,
                startVal, function (scoreobj) {
                    doAddCommentScore(scoreobj, resdetailid);
                });
        }
    });
}

/**
 * 添加好评
 *
 * @sval分数
 * @return
 */
function doAddCommentScore(sval, resdetailid) {
    if (typeof (sval) == "undefined" || isNaN(sval)) {
        return;
    }
    if (typeof (resdetailid) == "undefined" || resdetailid.length < 1) {
        return;
    }
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        return;
    }

    $.ajax({
        url: 'tpres?doAddCommentScore',
        type: 'post',
        data: {
            resourceid: resdetailid,
            scoreval: sval,
            courseid: courseid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
            }
        }
    });
}

function showMp3Resource(divid, pidx, filemd5name, md5Id, tmpport) {
    // 删除播放器
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']")
            .parent()
            .append(
                '<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='player1']").remove();

    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").after(
            '<div id="div_show" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();

    loadSWFPlayer(path, filemd5name, divid, false, lastname);

    /*	var htm = '';  
     htm += '<img src="images/mp3.jpg" width="75" height="52" />';   
     htm += '<span style="color:blue;font-size:10px" id="play_operate_' + pidx
     + '" filemd5name="' + filemd5name + '">';  
     htm += '<a href="javascript:;" onclick="operateSound(' + pidx + ',\''  
     + md5Id + '\',\'' + filemd5name + '\',\'play\',\''+tmpport+'\')">试听</a>';
     htm += '</span>';
     $("#" + divid).html(htm); */
}


/**
 * 播放或停止音乐
 * @param idx
 * @param filereadlyname
 * @return
 */
function showModeloperateSound(playAddress, md5id, filereadlyname, type, tmpport,resid) {
    var lastname = "";
    if (typeof(filereadlyname) != 'undefined' && filereadlyname.Trim().length > 0 && filereadlyname.indexOf(".") != -1)
        lastname = filereadlyname.substring(filereadlyname.lastIndexOf(".")).toLowerCase();
    loadSWFPlayer(md5id, '001', 'div_show', true, lastname, 'mp3','images/mp3.jpg',resid);
}

function resizeimg(ImgD, iwidth, iheight) {

    var image = new Image();
    image.src = ImgD.src;
    setTimeout(function () {
        if (image.width > 0 && image.height > 0) {
            if (image.width / image.height >= iwidth / iheight) {
                if (image.width > iwidth) {
                    ImgD.width = iwidth;
                    ImgD.height = (image.height * iwidth) / image.width;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }

            } else {
                if (image.height > iheight) {
                    ImgD.height = iheight;
                    ImgD.width = (image.width * iheight) / image.height;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }

            }
        }
    }, 300);
}

/**
 * 生成评论框
 *
 * @return
 */
function generXheditor(resdetailid, isstuipt) {
    var htm = '<textarea id="xheditor"></textarea>';
    if (isstuipt)
        htm += '<p class="t_r"><a href="javascript:void(0);" onclick="doSubStudyNote(\'' + resdetailid + '\')"  class="an_small">发&nbsp;表</a></p>';
    else
        htm += '<p class="t_r"><a href="javascript:void(0);" onclick="doSubResourceComment(\'' + resdetailid + '\')"  class="an_small">发&nbsp;表</a></p>';
    $("#div_xheditor").html(htm);
    if(isstuipt){
       /* editor = new UE.ui.Editor({
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars: [
                [ 'Undo', 'Redo', 'Bold',, 'fullscreen', 'removeformat','insertimage']
            ],
            // initialFrameWidth: "580px",
            // initialFrameHeight: "500px",
            autoHeightEnabled: false
        });
        textarea:'xheditor'; //与textarea的name值保持一致
        //editor.render('editor');
        editor.render('xheditor'); */
    }
    /*  editor = new UE.ui.Editor({
     //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
     toolbars: [
     ['Undo', 'Redo', 'Bold', 'fullscreen', 'removeformat','insertimage']
     ],
     initialFrameWidth: "715px",
     initialFrameHeight: "75px",
     autoHeightEnabled: false
     });
     textarea:'xheditor'; //与textarea的name值保持一致
     editor.render('xheditor'); */
}


/**
 * 提交资源学习心得
 * @param resid
 */
function doSubStudyNote(resid) {
    if (typeof resid == 'undefined')
        return;
    var commentobj = $("#xheditor");
    if (commentobj.val().Trim().length < 1) {
        alert('请输入学习心得后提交!');
        commentobj.focus();
        return;
    }
    var param = {
        courseid: courseid,
        answercontent: commentobj.val().Trim(),
        resid: resid
    };
    if (typeof (taskid) != 'undefined' && taskid.length > 0)
        param.taskid = taskid;
    $.ajax({
        url: 'task?m=doSubStudyNotes',
        dataType: 'json',
        type: "post",
        cache: false,
        data: param,
        error: function () {
            alert('异常错误!系统未响应!');
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                $("#div_xheditor").hide();
                loadStudyNotes(2);
                try {
                    $("#xheditor").val("");
                } catch (e) {
                }

            }
        }
    });
}


/**
 * 资源心得
 * @param rps
 */
function getStuNoteReturnMethod(rps) {
    var html = '';
    if (rps.objList[0] != null && rps.objList[0].length > 0) {
        $.each(rps.objList[0], function (idx, itm) {
            html += '<div class="pinglun" id="div_comment_' + itm.ref + '">';
            html += '        <p class="font-black"><span class="font-blue">' + itm.cuserinfo.realname + '：</span>' + itm.answercontent + '</p>';
            html += '        <p>' + itm.ctimeString + '&nbsp;&nbsp;<a href="javascript:loadStuNoteReplyTextArea(\'' + itm.ref + '\',\'' + itm.userid + '\',\'' + itm.quesparentid + '\');" class="ico45" title="回复"></a></p>';
            html += '<p class="pic"><img src="' + itm.cuserinfo.headimage + '" width="38" height="38" /></p>';
            html += '<div id="div_reply_' + itm.ref + '"></div>';
            html += '<div style="display:none;" id="p_rframe_' + itm.ref + '"></div>';
            html += '</div>';
        });
    } else {
        html = '<p>暂无数据!</p>';
    }
    $("div[id='div_comment_htm']").html(html);
    $("div[id='div_comment_htm']").hide();
    $("div[id='div_comment_htm']").show("fast");

    if (rps.objList[1] != null && rps.objList[1].length > 0) {
        $.each(rps.objList[1], function (idx, itm) {
            var htm = '';
            htm += '<div class="one" id="dv_' + itm.ref + '">';
            htm += '        <p class="font-black"><span class="font-blue">' + itm.ruserinfo.realname;
            if (typeof(itm.torealname) != "undefined" && itm.torealname != null && itm.torealname.Trim().length > 0) {
                htm += '&nbsp;回复' + itm.torealname + '&nbsp;说：';
            }
            htm += '</span>' + itm.answercontent + '</p>';
            htm += '        <p>' + itm.ctimeString + '&nbsp;&nbsp;<a href="javascript:loadStuNoteReplyTextArea(\'' + itm.ref + '\',\'' + itm.replyuserid + '\',\'' + itm.quesparentid + '\')" class="ico45" title="回复"></a></p>';
            htm += '<p class="pic"><img src="' + itm.cuserinfo.headimage + '" alt="" width="38" height="38" /></p>';
            htm += '</div>';
            htm += '<div  style="display:none;" id="p_rframe_' + itm.ref + '"></div>';
            htm += '<div id="div_reply_' + itm.ref + '"></div>';
            if ($("#div_reply_" + itm.quesid + " div").length > 0)
                $("#div_reply_" + itm.quesid + " div:last").after(htm);
            else
                $("#div_reply_" + itm.quesid).after(htm);
        });
    }

    if (rps.objList.length > 0) {
        p2.setPagetotal(rps.presult.pageTotal);
        p2.setRectotal(rps.presult.recTotal);
        p2.setPageSize(rps.presult.pageSize);
        p2.setPageNo(rps.presult.pageNo);
    } else {
        p2.setPagetotal(0);
        p2.setRectotal(0);
        p2.setPageNo(1);
    }
    p2.Refresh();
}

function preeDoStuNotePageSub(pObj) {
    if (typeof(pObj) != 'object') {
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if (courseid.length < 1) {
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param = {courseid: courseid};
    var resdetailid = $("#hd_resdetailid").val();
    var flag = $("#hd_comment_flag").val();
    if (resdetailid.Trim().length > 0 && flag.Trim().length < 1)
        param.resdetailid = resdetailid;
    pObj.setPostParams(param);
}


/**
 * 评论
 *
 * @return
 */
function doSubResourceComment(resdetailid) {
    if (typeof(resdetailid) == "undefiend" || resdetailid.length < 1) {
        alert("异常错误,参数异常!请刷新页面重试!");
        return;
    }
    if (typeof(courseid) == "undefiend" || courseid.length < 1) {
        alert("异常错误,参数异常!请刷新页面重试!");
        return;
    }
    var commentobj = $("#xheditor");
    if (commentobj.val().Trim().length < 1) {//editor.getContent().length < 1
        alert('请输入回复内容后提交!');
        commentobj.focus();
        return;
    }
    var param = {
        courseid: courseid,
        commentcontext: commentobj.val(), // editor.getContent(),
        commentobjectid: resdetailid
    };
    if (typeof (taskid) != 'undefined' && taskid.length > 0)
        param.taskid = taskid;
    if (typeof (groupid) != 'undefined' && groupid.length > 0)
        param.groupid = groupid;
    $.ajax({
        url: 'commoncomment?m=doSubResourceComment',
        dataType: 'json',
        type: "post",
        cache: false,
        data: param,
        error: function () {
            alert('异常错误!系统未响应!');
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                try {
                    pageGo("pList");
                    // $("#div_xheditor").html('');
                    $("#xheditor").val('');
                    $("#pListForm").show();
                } catch (e) {
                }

            }
        }
    });

}


/**
 * 加载回复框
 *
 * @return
 */
function loadStuNoteReplyTextArea(id, touserid, resdetailid) {
    if (typeof (touserid) == 'undefined' || touserid.length < 1) {
        return;
    }
    var replyobj = $("textarea").filter(
        function () {
            return this.id.indexOf("children_reply") != -1
                && this.id != "children_reply_" + id + "";
        });
    if (replyobj.length > 0) {
        if (!confirm("确认移除正在编辑的回复吗?")) {
            return;
        }
        $.each(replyobj, function (idx, itm) {
            $(itm).parent().remove();
            //$(itm).hide();
        });
    }
    var htm = '';
    htm += '<div class="two">';
    htm += '<textarea id="children_reply_' + id + '"></textarea>';
    htm += '<p class="t_r"><a onclick="doStuNoteReply(\'' + id + '\',\'' + touserid + '\',\'' + resdetailid + '\')" class="an_small">确&nbsp;定</a></p>';
    htm += '</div>';
    $("div[id='p_rframe_" + id + "']").html(htm);
    $("div[id='p_rframe_" + id + "']").show('fast');
//	$("div[id='p_rframe_" + id + "'] textarea").xheditor( {
//		tools : 'Removeformat,|,Link,Unlink,|,Emot'
//	});
    /*  child_editor = new UE.ui.Editor({
     //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
     toolbars: [
     ['Undo', 'Redo', 'Bold', 'fullscreen', 'removeformat','insertimage']
     ],
     //        initialFrameWidth: "450px",
     //        initialFrameHeight: "80px",
     autoHeightEnabled: false
     });
     textarea:'children_reply_'+id+''; //与textarea的name值保持一致
     child_editor.render('children_reply_'+id+'');
     */
}

/**
 * 回复
 *
 * @param id
 * @param touserid
 * @return
 */
function doStuNoteReply(id, touserid, resid) {
    var resdetailid = $("#hd_resdetailid").val();
    if (typeof(resid) != 'undefined' && resid.length > 0)
        resdetailid = resid;
    if (typeof (id) == "undefined" || id.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (touserid) == "undefined" || touserid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (resdetailid) == "undefined" || resdetailid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }

    var replyObj = $("#children_reply_" + id);
    if (replyObj.val().Trim().length < 1) {//child_editor.getContent().length < 1
        alert("请输入回复内容后提交!");
        replyObj.focus();
        return;
    }
    var param = {
        parentcommentid: id,
        replycontent: replyObj.val().Trim(),//child_editor.getContent(),
        touserid: touserid,
        courseid: courseid,
        resdetailid: resdetailid
    };
    $
        .ajax({
            url: "tpres?m=doReplyResourceStuNote",
            dataType: 'json',
            type: "post",
            cache: false,
            data: param,
            error: function () {
                alert('异常错误!系统未响应!');
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    alert(rps.msg);
                    pageGo("p2");
                    var htm = '';
                    if (rps.objList.length > 0) {
                        $
                            .each(
                            rps.objList,
                            function (idx, itm) {
                                htm += '<div class="one" id="dv_' + itm.ref + '">';
                                htm += '        <p class="font-black"><span class="font-blue">' + itm.ruserinfo.realname;
                                if (typeof(itm.torealname) != "undefined" && itm.torealname != null && itm.torealname.Trim().length > 0) {
                                    htm += '&nbsp;回复' + itm.torealname + '&nbsp;说：';
                                }
                                htm += '</span>' + itm.answercontent + '</p>';
                                htm += '        <p>' + itm.ctimeString + '&nbsp;&nbsp;<a href="javascript:loadReplyTextArea(\'' + itm.ref + '\',\'' + itm.replyuserid + '\',\'' + itm.quesparentid + '\')" class="ico45" title="回复"></a></p>';
                                htm += '<p class="pic"><img src="' + itm.cuserinfo.headimage + '" alt="" width="38" height="38" /></p>';
                                htm += '</div>';
                                htm += '<div  style="display:none;" id="p_rframe_' + itm.ref + '"></div>';
                                htm += '<div id="div_reply_' + itm.ref + '"></div>';
                            });


                        if ($("#div_reply_" + id + " div").length > 0)
                            $("#div_reply_" + id + " div:last").after(htm);
                        else
                            $("#div_reply_" + id).append(htm);

                    }
                }
            }
        });
}


/**
 * 加载回复框
 *
 * @return
 */
function loadReplyTextArea(id, touserid, resdetailid) {
    if (typeof (touserid) == 'undefined' || touserid.length < 1) {
        return;
    }
    var replyobj = $("textarea").filter(
        function () {
            return this.id.indexOf("children_reply") != -1
                && this.id != "children_reply_" + id + "";
        });
    if (replyobj.length > 0) {
        if (!confirm("确认移除正在编辑的回复吗?")) {
            return;
        }
        $.each(replyobj, function (idx, itm) {
            $(itm).parent().remove();
            $(itm).hide();
        });
    }
    var htm = '';
    htm += '<div class="two">';
    htm += '<textarea id="children_reply_' + id + '"></textarea>';
    htm += '<p class="t_r"><a onclick="doReply(\'' + id + '\',\'' + touserid + '\',\'' + resdetailid + '\')" class="an_small">确&nbsp;定</a></p>';
    htm += '</div>';
    $("div[id='p_rframe_" + id + "']").html(htm);
    $("div[id='p_rframe_" + id + "']").show('fast');

    /*    child_editor = new UE.ui.Editor({
     //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
     toolbars: [
     ['Undo', 'Redo', 'Bold', 'fullscreen', 'removeformat','insertimage']
     ],
     initialFrameWidth: "605px",
     initialFrameHeight: "75px",
     autoHeightEnabled: false
     });
     textarea:'children_reply_'+id+''; //与textarea的name值保持一致
     child_editor.render('children_reply_'+id+''); */
}

/**
 * 回复
 *
 * @param id
 * @param touserid
 * @return
 */
function doReply(id, touserid, resid) {
    var resdetailid = $("#hd_resdetailid").val();
    if (typeof(resid) != 'undefined' && resid.length > 0)
        resdetailid = resid;
    if (typeof (id) == "undefined" || id.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (touserid) == "undefined" || touserid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (resdetailid) == "undefined" || resdetailid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }

    var replyObj = $("#children_reply_" + id);
    if (replyObj.val().Trim().length < 1) {
        alert("请输入回复内容后提交!");
        replyObj.focus();
        return;
    }
    var param = {
        parentcommentid: id,
        replycontent: replyObj.val(),
        touserid: touserid,
        courseid: courseid,
        resdetailid: resdetailid
    };
    $
        .ajax({
            url: "commoncomment?m=doReplyResource",
            dataType: 'json',
            type: "post",
            cache: false,
            data: param,
            error: function () {
                alert('异常错误!系统未响应!');
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    var htm = '';
                    $
                        .each(
                        rps.objList,
                        function (idx, itm) {
                            htm += '<div class="one" id="dv_' + itm.commentid + '">';
                            htm += '        <p class="font-black"><span class="font-blue">' + itm.ruserinfo.realname;
                            if (typeof(itm.torealname) != "undefined" && itm.torealname != null && itm.torealname.Trim().length > 0) {
                                htm += '&nbsp;回复' + itm.torealname + '&nbsp;说：';
                            }
                            htm += '</span>' + itm.commentcontext + '</p>';
                            htm += '        <p>' + itm.ctimeString + '&nbsp;&nbsp;<a href="javascript:loadReplyTextArea(\'' + itm.commentid + '\',\'' + itm.reportuserid + '\',\'' + itm.commentobjectid + '\')" class="ico45" title="回复"></a></p>';
                            htm += '<p class="pic"><img src="' + itm.cuserinfo.headimage + '" alt="" width="38" height="38" /></p>';
                            htm += '</div>';
                            htm += '<div  style="display:none;" id="p_rframe_' + itm.commentid + '"></div>';
                            htm += '<div id="div_reply_' + itm.commentid + '"></div>';
                        });
                    if ($("#div_reply_" + id + " div").length > 0)
                        $("#div_reply_" + id + " div:last").after(htm);
                    else
                        $("#div_reply_" + id).append(htm);
                    //删除回复框
                    $(replyObj).parent().remove();
                }
            }
        });
}

/**
 * 评论删除
 *
 * @param id
 * @return
 */
function deleteReply(id, type) {
    if (typeof (id) == "undefined" || id.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (typeof (type) == "undefined" || isNaN(type)) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if (!confirm('确认删除该回复吗?'))
        return;
    $.ajax({
        url: "rescomment?doDeleteRepley",
        dataType: 'json',
        type: "post",
        cache: false,
        data: {
            ref: id,
            type: type
        },
        error: function () {
            alert('异常错误!系统未响应!');
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                $("#dv_" + id).hide('fast');
                $("#dv_" + id).remove();
            }
        }
    });
}

/**
 * 加载学习心得
 *
 * @return
 */
function loadStudyNotes(usertype) {
    var resid = $("#hd_resdetailid").val();
    if (typeof (resid) == "undefined" || resid.length < 1) {
        alert('请选择资源后查看心得!');
        return;
    }
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    $('#ul_comment').children('li').removeClass('crumb').end().children('li').eq(0).addClass('crumb');
    $("#div_xheditor").html('');

    var param = {
        resourceid: resid,
        courseid: courseid,
        usertype: usertype
    };

    $
        .ajax({
            url: "task?loadStudyNotes",
            dataType: 'json',
            type: "post",
            cache: false,
            data: param,
            error: function () {
                alert('系统未响应!');
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    var htm = '';
                    pageGo("p2");
                    if (rps.objList[0] != null && rps.objList[1] != null && rps.objList[1]=="on") {
                        $("#div_xheditor").hide();
                        if (usertype == 1) {
                            htm += '<p>我的心得&nbsp;<a  class="ico11" title="编辑" href="javascript:genderStuNoteTextArea(' + rps.objList[0].ref + ')"></a><div id="dv_updnote" class="two">' + rps.objList[0].answercontent + '</div></p>';
                        }
                    } else {
                        if (usertype == 1 && rps.objList[1] != null &&  rps.objList[1]=='on')
                            generXheditor(resid, true);
                        else if(usertype == 1 && (typeof rps.objList[1]=='undefined'||rps.objList[1]==null)){
                            $("#dv_study_note").hide();
                            loadAllComment();
                            return;
                        }
                    }

                    $("div[id='dv_study_note']").html(htm);
                    $("#dv_study_note").hide();
                    $("#dv_study_note").show("fast");
                    $("#div_comment_htm").html('');
                }
            }
        });
}


/**
 * 加载回复框
 * @return
 */
function genderStuNoteTextArea(ref) {
    if (typeof (ref) == 'undefined') {
        return;
    }
    if ($("#txt_updnote").length > 0)return;

    var divObj = $("#dv_updnote");
    var htm = divObj.html();
    divObj.html('<textarea id="txt_updnote">' + htm + '</textarea><p class="t_r"><a href="javascript:doUpdStuNote(' + ref + ')" class="an_small">确定</a><a  class="an_small" href="javascript:cancelUpdStuNote()">取消</a></p>');

    /* note_editor = new UE.ui.Editor({
     //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
     toolbars: [
     ['Undo', 'Redo', 'Bold', 'fullscreen', 'removeformat','insertimage']
     ],
     //        initialFrameWidth: "450px",
     //        initialFrameHeight: "80px",
     autoHeightEnabled: false
     });
     textarea:'txt_updnote'; //与textarea的name值保持一致
     note_editor.render('txt_updnote'); */
}

function cancelUpdStuNote() {
    $("#dv_updnote").html($("#txt_updnote").html())
}

function doUpdStuNote(ref) {
    if (typeof  ref == 'undefined')
        return;
    var valObj = $("#txt_updnote");
    if (valObj.val().Trim().length < 1) {
        alert("请输入学习心得!");
        valObj.focus();
        return;
    }

    $
        .ajax({
            url: "task?doUpdStudyNotes",
            dataType: 'json',
            type: "post",
            cache: false,
            data: {
                ref: ref,
                content: valObj.val().Trim()
            },
            error: function () {
                alert('系统未响应!');
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    alert(rps.msg);
                    loadStudyNotes(1);
                }
            }
        });
}


/**
 * 生成评星
 *
 * @return
 */
function generStar(rankscore, startval) {
    var sHtml = "<ul>";
    for (var i = 0; i < startval; i++) {
        if (typeof (rankscore) == "undefined" || isNaN(rankscore))
            sHtml += "<li title='" + (i + 1) + "分' class='star_out'></li>";
        else if (i < rankscore)
            sHtml += "<li title='" + (i + 1) + "分' class='star_hover'></li>";
        else
            sHtml += "<li title='" + (i + 1) + "分' class='star_out'></li>";
    }
    sHtml += "</ul>";
    return sHtml;
}

/**
 * 好评排行
 *
 * @return
 */
function loadRankScore() {
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误,参数异常!请刷新页面后重试!');
        return;
    }
    $.ajax({
        url: "tpres?loadRankScoreList",
        dataType: 'json',
        type: "post",
        cache: false,
        data: {
            courseid: courseid
        },
        error: function () {
            alert('异常错误!系统未响应!');
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                var html = '';
                if (rps.objList.length > 0) {
                    $.each(rps.objList, function (idx, itm) {
                        html += '<li><span class="f_right">';
                        for (var i = 0; i < 5; i++) {
                            if (typeof(itm.rankscore) == "undefined" || isNaN(itm.rankscore))
                                html += '<img src="images/star02.gif"/>';
                            else if (i < itm.rankscore)
                                html += '<img src="images/star01.gif"/>';
                            else
                                html += '<img src="images/star02.gif"/>';
                        }
                        html += '<strong class="font-red">' + itm.rankscore + '</strong></span>' + itm.filename + '</li>';
                    });
                }
                $("#div_scorerank").html(html);
            }
        }
    });
}

/**
 * 点击排行
 *
 * @return
 */
function loadViewRank() {
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误,参数异常!请刷新页面后重试!');
        return;
    }
    $
        .ajax({
            url: "tpres?loadViewRankList",
            dataType: 'json',
            type: "post",
            cache: false,
            data: {
                courseid: courseid
            },
            error: function () {
                alert('异常错误!系统未响应!');
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    var html = '';
                    if (rps.objList.length > 0) {
                        $.each(rps.objList, function (idx, itm) {
                            html += '<li>';
                            html += '<span class="f_right font-red">' + itm.viewcount + '</span>';
                            html += itm.filename;
                            html += '</li>';
                        });
                    }
                    $("#div_viewcount").html(html);
                }
            }
        });
}

function loadNewComment(pageno) {
    if (typeof (courseid) == "undefined" || courseid.length < 1) {
        alert('异常错误,参数异常!请刷新页面后重试!');
        return;
    }
    $
        .ajax({
            url: 'tpres?loadNewCommentList&pageResult.pageNo='
                + pageno + "&pageResult.pageSize=5&t="
                + new Date().getTime(),
            type: 'post',
            data: {
                courseid: courseid
            },
            dataType: 'json',
            error: function () {
                alert("系统未响应！");
            },
            success: function (rps) {
                if (rps.objList.length > 0) {
                    var htm = '';
                    $
                        .each(
                        rps.objList,
                        function (idx, itm) {
                            htm += '<li>';
                            htm += '<p>';
                            htm += '<span class="f_right font-gray">' + itm.ctimeString + '</span>' + itm.cuserinfo.realname + '</p>';
                            htm += '<p style="word-break:break-all"><span class="font-blue">' + itm.filename + '：</span>' + itm.content + '</p>';
                            htm += '</li>';
                        });
                    $("#div_newcomment").html(htm);

                    if (rps.presult.recTotal > 0
                        && rps.presult.pageTotal > 1) {
                        var prev = (pageno - 1) > 0 ? (pageno - 1) : 1;
                        var next = (pageno + 1) >= rps.presult.pageTotal ? rps.presult.pageTotal
                            : (pageno + 1);
                        var page_split = '<a href="javascript:loadNewComment('
                            + prev
                            + ')" class="an_nextpage">上一页</a>'
                            + pageno
                            + '/'
                            + rps.presult.pageTotal
                            + '<a href="javascript:loadNewComment('
                            + next
                            + ')" class="an_nextpage">下一页</a>';
                        $("#p_page_address").html(page_split);
                    }
                }
            }
        });

}


/**
 *显示文档
 */
function showModelDoc(swfpath, showdiv) {
    //删除SWF
    if (typeof($("object[id='FlexPaperViewer']")))
        $("object[id='FlexPaperViewer']").after(
            '<div id="div_show" style="width:560px;height:370px;"></div>');
    $("object[id='FlexPaperViewer']").remove();

    loadSwfReview(swfpath, showdiv, 560, 500);
    showModel("swfplayer", false);
}


function showModelOther(playAddress, fname, resbaseid) {
    //删除SWF
    if (typeof($("object[id='FlexPaperViewer']")))
        $("object[id='FlexPaperViewer']").after(
            '<div id="div_show" style="width:560px;height:370px;"></div>');
    $("object[id='FlexPaperViewer']").remove();

    var htm = '<div><a href="javascript:resourceDownLoadFile(\'' + resbaseid + '\',\'' + fname + '\',1)">' + fname + '</a></div>';
    $("#" + playAddress).html(htm);
    showModel("swfplayer", false);
}