/**
 * 小文件上传
 */
function uploadMinFile(resfile){
//sp_rs_log
    var rsval=$("#"+resfile).val();
    if(rsval.Trim().length<1){
        return;
    }
    $("#sp_rs_log").html("正在上传……");
    var lastName=rsval.substring(rsval.lastIndexOf("."))
    url+="&lastname="+lastName;

    $.ajaxFileUpload({
        url:url,
        fileElementId:resfile,
        dataType: 'json',
        secureuri:false,
        type:'POST',
        success: function (data, status)
        {
            //  data=eval("("+data.Trim()+")");
            if(typeof(data.error) != 'undefined')
            {
                if(data.error != '')
                {
                    // alert(data.error);
                    $("#sp_rs_log").html("<font color=\"red\">"+data.error+"</font>");
                }else
                {
                    $("#sp_rs_log").html(rsval.substring(rsval.lastIndexOf("\\")+1)+"  上传成功!");
                    $("#res_complate_file").val(rsval);
                    $("#"+resfile).val(rsval);
                    // alert(data.msg);
                }
            }else{
                $("#sp_rs_log").html(rsval.substring(rsval.lastIndexOf("\\")+1)+"  上传成功!");
                $("#res_complate_file").val(rsval.substring(rsval.lastIndexOf("\\")+1));
                $("#"+resfile).val(rsval);
            }
        },
        error: function (data, status, e)
        {
            $("#sp_rs_log").html("<font color=\"red\">"+e+"</font>");
        }
    });
}
/**
 * 上传资源
 * @param resid
 */
function doCreateRes(){
    var param={};
    var residObj=$("#resid").val().Trim();
    if(residObj.length<1){
        alert('异常错误，资源ID没有正常生成，请重新刷新页面后，选择加载!');return;
    }
    param.resid=residObj;
    var resnameObj=$("#res_name");
    if(resnameObj.val().Trim().length<1){
        alert('错误，资源名称您尚未输入，请输入!');
        resnameObj.focus();return;
    }
    param.resname=resnameObj.val().Trim();
    //资源类型
    var restypeObj=$("input[name='rdo_restype']:checked");
    if(restypeObj.length<1){
        alert('错误，您尚未选择资源类型，请选择!');
        return;
    }
    //资源类型值
    var restype=restypeObj.val().Trim();
    if(restype.length<1){
        alert('系统错误，未发现您选择的资源类型值!请反馈给网校，进行维护处理!');
        return;
    }
    param.restype=restype;
    //分享等级
    var reslevelObj=$("input[name='rdo_reslevel']:checked");
    if(reslevelObj.length<1){
        alert('错误，您尚未选择资源分享等级，请选择!');
        return;
    }
    //分享等级值
    var reslevel=reslevelObj.val().Trim()
    if(reslevel.length<1){
        alert('系统错误，未发现您选择的资源分享等级值!请反馈给网校，进行维护处理!');
        return;
    }
    param.sharestatus=reslevel;//分享类型
    //简介
    var resremark=$("#res_remark").val().Trim();
    if(resremark.length>3000){
        alert('异常错误，资源简介字数过长，请尽量保持在3000字符以内!');
        resremark.focus();return;
    }
    if(resremark.length>0){
        param.resintroduce=resremark;
    }
    //判断是什么类型的上传，小文件还是大文件
    var fileuptype=$("#file_uptype").val().Trim();
    var filename=null;
    if(fileuptype==1){//小文件
        filename=$("#hd_filename").val();
        if(filename.length<1){
            //alert("错误，您选择的文件正在上传，尚未上传完毕，请耐心等候，上传完毕后再进行提交!");return;
            alert("请选择文件!");return;
        }
        $('#uploadfile').uploadify('upload','*')


    }else if(fileuptype==2){//大文件
        if(uploadControl.fileAttribute.length<1){
            alert('错误，您尚未选择文件上传或文件尚未上传完成，请文件上传完成后再进行提交!');return;
        }
        $.each(uploadControl.fileAttribute,function(idx,itm){
            if(itm!=null&&itm!=undefined){
                filename=itm.filename;
                param.filesize=itm.size;
            }
        })
    }
    param.filename=filename;
    param.uptype=fileuptype;//上传文件的大小
    //提示：您确定提交‘"+param.resname+"’的资源吗?


    if(!confirm("您确定提交‘"+param.resname+"’的资源吗?"))return;
    if(fileuptype==1){
        var rsval=$("#uploadfile").val();
        if(rsval.Trim().length<1){
            return;
        }
    $("#a_dosub").hide();
    $("#sp_rs_log").html("正在上传……");
    var lastName=rsval.substring(rsval.lastIndexOf("."));
    url+="&lastname="+lastName;
    var resfile="uploadfile";
    $.ajaxFileUpload({
        url:url,
        fileElementId:resfile,
        dataType: 'json',
        secureuri:false,
        type:'POST',
        success: function (data, status)
        {
            //  data=eval("("+data.Trim()+")");
            if(typeof(data.error) != 'undefined')
            {
                if(data.error != '')
                {
                    $("#a_dosub").show();
                    // alert(data.error);
                    $("#sp_rs_log").html("<font color=\"red\">"+data.error+"</font>");
                }else
                {
                    $("#sp_rs_log").html("上传资源完毕…正在更新记录…");
                    //执行提交
                    $.ajax({
                        url:'resource?m=doadd',
                        data:param,
                        type:'post',
                        dataType:'json',
                        error:function(){
                            alert('异常错误，网络异常!无法连接报务器!');
                        },success:function(rps){
                            if(rps.type=="error"){
                                alert(rps.msg);
                                $("#a_dosub").show();
                            }else{
                                alert(rps.msg);
                                if(typeof(window.opener.pmyRes)!="undefined"&&window.opener.pmyRes!=null){
                                    window.opener.pmyRes.pageGo();
                                    window.close();
                                }else
                                    location.href="resource?m=toMyResList";
                            }
                        }
                    });
                }
            }else{
                $("#sp_rs_log").html("上传资源完毕…正在更新记录…");
                //执行提交
                $.ajax({
                    url:'resource?m=doadd',
                    data:param,
                    type:'post',
                    dataType:'json',
                    error:function(){
                        alert('异常错误，网络异常!无法连接报务器!');
                    },success:function(rps){
                        if(rps.type=="error"){
                            alert(rps.msg);
                            $("#a_dosub").show();
                        }else{
                            alert(rps.msg);
                            if(typeof(window.opener.pmyRes)!="undefined"&&window.opener.pmyRes!=null){
                                window.opener.pmyRes.pageGo();
                                window.close();
                            }else
                                location.href="resource?m=toMyResList";
                        }
                    }
                });
            }
        },
        error: function (data, status, e)
        {
            $("#a_dosub").show();
            $("#sp_rs_log").html("<font color=\"red\">"+e+"</font>");
        }
    });
    }else if(fileuptype==2){
        //执行提交
        $.ajax({
            url:'resource?m=doadd',
            data:param,
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误，网络异常!无法连接报务器!');
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                }else{
                    alert(rps.msg);
                    if(typeof(window.opener.pmyRes)!="undefined"&&window.opener.pmyRes!=null){
                        window.opener.pmyRes.pageGo();
                        window.close();
                    }else
                        location.href="resource?m=toMyResList";
                }
            }
        });
    }




}

//updateResColumn('${resObj.resid}','resname','sp_name','sp_up_btn_resname')
/**
 * 更新资源字段
 * @param resid 资源ID
 * @param upcol  更新的列
 * @param val_id 要更新的值地点
 * @param op_id 按钮地点
 * @param type 生成的类型: 1:文本框   3:textare
 */
function updateResColumn(resid,upcol,val_id,op_id,type){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert('异常错误，参数异常!原因：resid is empty!');return;
    }
    if(typeof(upcol)=="undefined"||upcol==null||upcol.Trim().length<1){
        alert('异常错误，参数异常!原因：upcol is empty!');return;
    }
    if(typeof(val_id)=="undefined"||val_id==null||val_id.Trim().length<1){
        alert('异常错误，参数异常!原因：val_id is empty!');return;
    }
    if(typeof(op_id)=="undefined"||op_id==null||op_id.Trim().length<1){
        alert('异常错误，参数异常!原因：op_id is empty!');return;
    }
    if(typeof(type)=="undefined"||type==null)
        type=1;
    //变成选框
    var colVal=$("#"+val_id).html();
    var h="",control_type="";
    if(type==1){
        h+='<input type="text" maxlength="100" name="input_val" id="input_val" value="'+colVal+'"/>';
        control_type="input";
    }else if(type==3){
        h+='<textarea name="textare_val" id="textare_val" class="w880">'+colVal+'</textarea>';
        control_type="textare";
    }
    //更新页面控件，等待编辑
    $("#"+val_id).html(h);
    $("#"+op_id).hide();
    //绑定事件，失去焦点进行更新
    $("#"+control_type+"_val").focus();
    $("#"+control_type+"_val").bind("blur",function(){
        //进行更新
        //alert(resid+"  "+upcol+"  "+this.value);
        var val=this.value.Trim();
        //验证参数
        if(val.length<1){
            alert('错误，资源名称不能为空!请重新输入!');
            $(this).focus();return;
        }
        if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
            alert('异常错误，参数异常!原因：resid is empty!');return;
        }
        if(typeof(upcol)=="undefined"||upcol==null||upcol.Trim().length<1){
            alert('异常错误，参数异常!原因：upcol is empty!');return;
        }
        if(typeof(op_id)=="undefined"||op_id==null||op_id.Trim().length<1){
            alert('异常错误，参数异常!原因：op_id is empty!');return;
        }
        if(val.length>1000){
            alert("错误，不能大于1000个字符!请重新录入!");
            $(this).focus();return;
        }
        var param={
            resid:resid.Trim()
        };
        eval("(param."+upcol+"=val)");
        //进行ajax提交
        var controltype=control_type,opid=op_id;
        $.ajax({
            url:'resource?m=doUpdateColumn',
            data:param,
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误，网络异常!无法连接报务器!');
                $("#"+controltype+"_val").focus();
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                    $("#"+controltype+"_val").focus();
                }else{
                    $("#"+controltype+"_val").parent().html( $("#"+controltype+"_val").val());//更新页面
                    $("#"+opid).show();
                }
            }
        });
    });

}
/**
 * 更新资源字段
 * @param resid 资源ID
 * @param upcol  更新的列
 * @param val_id 要更新的值地点
 * @param op_id 按钮地点
 * @param type 生成的类型: 1:文本框   3:textare
 */
function updateSelectCol(resid,upcol,val_id,op_id,type){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert('异常错误，参数异常!原因：resid is empty!');return;
    }
    if(typeof(upcol)=="undefined"||upcol==null||upcol.Trim().length<1){
        alert('异常错误，参数异常!原因：upcol is empty!');return;
    }
    if(typeof(val_id)=="undefined"||val_id==null||val_id.Trim().length<1){
        alert('异常错误，参数异常!原因：val_id is empty!');return;
    }
    if(typeof(op_id)=="undefined"||op_id==null||op_id.Trim().length<1){
        alert('异常错误，参数异常!原因：op_id is empty!');return;
    }
    var h='<select id="sel_'+upcol+'_val" name="sel_'+upcol+'_sub">'+$("#sel_"+upcol).html()+'</select>';
    $("#"+op_id).hide();

     $("#"+val_id).html(h);

    $("#sel_"+upcol+"_val option[value='"+eval(type)+"']").attr("selected",true);
    $("#sel_"+upcol+"_val").bind("change",function(){
        var param={
            resid:resid.Trim()
        };
        var opval=this.value.Trim();
        if(opval.length<1){
            alert("异常错误，原因：你当前要修改的值为空!请重新选择!");
            this.focus();
            return;
        }
        eval("(param."+upcol+"=opval)");
        //进行ajax提交
        var opid=op_id;
        var ucol=upcol;
        $.ajax({
            url:'resource?m=doUpdateColumn',
            data:param,
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误，网络异常!无法连接报务器!');
                $("#sel_grade_val").focus();
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                    $("#sel_"+ucol+"_val").focus();return;
                }else{
                    eval("("+upcol+"='"+$("#sel_"+ucol+"_val option:selected").val()+"')");
                    $("#sel_"+ucol+"_val").parent().html($("#sel_"+ucol+"_val option:selected").text());//更新页面
                    $("#"+opid).show();
                }
            }
        });
    });

}
/**
 * 更新资源字段
 * @param resid 资源ID
 * @param upcol  更新的列
 * @param val_id 要更新的值地点
 * @param op_id 按钮地点
 * @param type 生成的类型: 1:文本框   3:textare
 */
function updateSelectColumn(resid,upcol,val_id,op_id){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert('异常错误，参数异常!原因：resid is empty!');return;
    }
    if(typeof(upcol)=="undefined"||upcol==null||upcol.Trim().length<1){
        alert('异常错误，参数异常!原因：upcol is empty!');return;
    }
    var upColArray=upcol.split("|")
    if(typeof(val_id)=="undefined"||val_id==null||val_id.Trim().length<1){
        alert('异常错误，参数异常!原因：val_id is empty!');return;
    }
    var validArray=val_id.split("|");
    if(typeof(op_id)=="undefined"||op_id==null||op_id.Trim().length<1){
        alert('异常错误，参数异常!原因：op_id is empty!');return;
    }
    var h='<select id="sel_grade_val" name="sel_grade_sub">'+$("#sel_gd_hd").html()+'</select>';
    $("#"+validArray[0]).html(h);
    $("#"+op_id).hide();
    h='<select id="sel_subject_val" name="sel_grade_sub">'+$("#sel_sb_hd").html()+'</select><a class="an_public3" id="a_grd_enter" href="javascript:;">确定</a>';
    $("#"+validArray[1]).html(h);
    $("#"+op_id).hide();
//    loadTeacherSub(grade,'sel_subject_val');
//
//    $("#sel_grade_val").bind("change",function(){
//        loadTeacherSub(this.value,'sel_subject_val',subject);
//    });
    $("#sel_grade_val option[value='"+grade+"']").attr("selected",true);
    $("#sel_subject_val option[value='"+subject+"']").attr("selected",true);
   // loadTeacherSub(grade,'sel_subject_val',subject);
    //$("#sel_subject_val option[value='"+subject+"']").attr("selected",true);
    //$("select[name='sel_grade_sub']")
    $("a[id='a_grd_enter']").bind("click",function(){
        //进行更新
        //alert(resid+"  "+upcol+"  "+this.value);
        var val=$("#sel_subject_val").val().Trim();
        //验证参数
        if(val.length<1){
            alert('错误，您要更新的数据不能为空!请重新输入!');
            $(this).focus();return;
        }
        if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
            alert('异常错误，参数异常!原因：resid is empty!');return;
        }
        if(typeof(op_id)=="undefined"||op_id==null||op_id.Trim().length<1){
            alert('异常错误，参数异常!原因：op_id is empty!');return;
        }
        var param={
            resid:resid.Trim(),
            grade:$("#sel_grade_val").val(),
            subject: $("#sel_subject_val").val()
        };
        //进行ajax提交
        var opid=op_id;
        $.ajax({
            url:'resource?m=doUpdateColumn',
            data:param,
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误，网络异常!无法连接报务器!');
                $("#sel_grade_val").focus();
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                    $("#sel_grade_val").focus();
                }else{
                    grade=$("#sel_grade_val").val();
                    subject=$("#sel_subject_val").val();

                    $("#sel_grade_val").parent().html($("#sel_grade_val option:selected").text());//更新页面
                    $("#sel_subject_val").parent().html($("#sel_subject_val option:selected").text()+"学科");
                    $("#"+opid).show();
                }
            }
        });
    });


}
/**
 * 加载学科
 * @param grade 年级
 */
function loadTeacherSub(grade,sbid,subjectid){
    if(typeof(grade)=="undefined"||grade==null||grade.Trim().length<1){
        alert('错误，参数异常!原因：grade is empty！');return;
    }
    $.ajax({
        url:'resource?m=getteachersub',
        data:{gradeid:grade},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误，网络异常!无法连接报务器!');
            $("#"+controltype+"_val").focus();
        },success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                var h='';
                $.each(rps.objList,function(idx,itm){
                    if(typeof(subjectid)!="undefined"&&subjectid==itm.subjectid){
                        h+='<option value="'+itm.subjectid+'" selected>'+itm.subjectname+'</option>';
                    }else
                    h+='<option value="'+itm.subjectid+'">'+itm.subjectname+'</option>';
                })
                $("#"+sbid).html(h);
            }
        }
    });
}
/**
 * 更新字段
 * @param resid  资源ID
 * @param opcol 操作的列名
 * @param opid  操作的值控件ID
 */
function updateResColumn_Type(resid,opcol,opid){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert('异常错误，原因：参数异常! resid is empty!');return;
    }
    if(typeof(opcol)=="undefined"||opcol==null||opcol.Trim().length<1){
        alert('异常错误，原因：参数异常! opcol is empty!');return;
    }
    if(typeof(opid)=="undefined"||opid==null||opid.Trim().length<1){
        alert('异常错误，原因：参数异常! opid is empty!');return;
    }
    var param={resid:resid.Trim()};
    var opval=$("#"+opid).val().Trim();
    eval("(param."+opcol+"=opval)");
    $.ajax({
        url:'resource?m=doUpdateColumn',
        data:param,
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误，网络异常!无法连接报务器!');
            $("#"+controltype+"_val").focus();
        },success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                //修改成功
            }
        }
    });
}

function resizeimg(ImgD, iwidth, iheight) {
    var image = new Image();
    image.src = ImgD.src;
    setTimeout(function(){
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
        }},300);
}

function addResourceComment(){
    var commenttype=$("#commenttype").val();
    var commentobjectid=$("#objectid").val();
    var commentcontext=$("#commentcontext").val();
    var anonymous=0;

    if(typeof(commenttype)=="undefined"||commenttype==null){
        alert('参数不足，无法提交评论！');return;
    }
    if(typeof(commentobjectid)=="undefined"||commentobjectid==null){
        alert('参数不足，无法提交评论！');return;
    }
    if(typeof(commentcontext)=="undefined"||commentcontext==null||commentcontext.Trim().length<1){
        alert('请输入评论内容，否则无法提交评论！');return;
    }
    if(commentcontext.Trim().length>200){
        alert('评论内容大于200字！');return;
    }
    var anonymous=0;
    if($("#anonymous").attr("checked"))
        anonymous=1;
    $.ajax({
        url:"commoncomment?m=ajaxsave",
        type:'POST',
        data:{
            commenttype:commenttype,
            commentobjectid:commentobjectid,
            commentcontext:commentcontext,
            anonymous:anonymous,
            commentparentid:0
        },
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                $("#commentcontext").val("");
                alert("评论成功！");
                pageGo('p1');
                //修改数量
                if(commenttype==1){
                    $("#sp_commentnum").html(parseInt($("#sp_commentnum").html())+1);
                }
            }
        }
    });
}

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
                    var t=parseInt($("#dv_store span:last").html())+1;

                    htm+='<a href="javascript:;" onclick="operateStore(2,\''+resid+'\',\'dv_store\')">';
                    htm+='<span class="ico58b" title="取消收藏"></span><span>'+t+'</span>';
                    htm+='</a>';
                }else{
                    var t=parseInt($("#dv_store span:last").html())-1;
                    htm+='<a href="javascript:;" onclick="operateStore(1,\''+resid+'\',\'dv_store\')">';
                    htm+='<span class="ico58a" title="收藏"></span><span>'+t+'</span>';
                    htm+='</a>';
                }
                $("#"+dvid).html(htm);
            }
        }
    });
}

function operateRecommend(resid){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    }
    $.ajax({
        url:'operaterecord?m=recomendres',
        type:'POST',
        data:{resid:resid},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
                return;
            }
            var sp=$("#recomend_status span:last").html();
            if(!isNaN(sp))
                $("#recomend_status span:last").html(parseInt($("#recomend_status span:last").html())+1);
            var h='<a style="cursor:default"><span class="ico73" title="已推荐"></span><span>'+$("#recomend_status span:last").html()+'</span></a>';
            $("#recomend_status").html(h);
        }
    });
}

function operatePraise(resid){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    }
    $.ajax({
        url:'operaterecord?m=praiseres',
        type:'POST',
        data:{resid:resid},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
                return;
            }
            var t=$("#praise_status span:last").html();
            if(!isNaN(t))
                $("#praise_status span:last").html(parseInt($("#praise_status span:last").html())+1);
            var h='<a style="cursor: default"><span class="ico41" title="已赞"></span>'+$("#praise_status span:last").html()+'</a>';

            $("#praise_status").html(h);
        }
    });
}

function resourceReport(resid){
    $("#addReportBtn").hide();
    var content=$("#report_content").val();
    if(typeof(content)=="undefined"||content==null||content.Trim().length<1){
        alert("请填写举报原因！");return;
    }

    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
        alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    }
    $.ajax({
        url:'resourcereport?m=addresourcereport',
        type:'POST',
        data:{resid:resid,content:content},
        dataType:'json',
        error:function(){alert("网络异常");$("#addReportBtn").show();},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
                $("#addReportBtn").show();
                return;
            }
            $(".ico74").attr({"onclick":"","title":"已举报"});
            alert("举报成功！");
            closeModel('resource_report');
        }
    });
}

function showInputData(type){
    if(type){
        $("[id='display_data']").each(function() {
            $(this).hide();
        });
        $("[id='input_data']").each(function() {
            $(this).show();
        });
    }else{
        $("[id='display_data']").each(function() {
            $(this).show();
        });
        $("[id='input_data']").each(function() {
            $(this).hide();
        });
    }
}

function doSupportOrOppose(commentid,type){
    $.ajax({
        url:"commoncomment?m=supportOrOppose",
        type:'POST',
        data:{
            commentid:commentid,
            type:(type?1:0)
        },
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                if(type)
                    $("#"+commentid+"_support").html(parseInt($("#"+commentid+"_support").html())+1);
                else
                    $("#"+commentid+"_oppose").html(parseInt($("#"+commentid+"_oppose").html())+1);
               // try{pageGo('p1');}catch(e){}
                $("#p_operate_"+commentid+" a").attr("href","javascript:;");
                $("#p_operate_"+commentid+" a").css("cursor","default");
                $("#p_operate_"+commentid+" a .ico41").attr("title","已操作");
                $("#p_operate_"+commentid+" a .ico72").attr("title","已操作");
            }
        }
    });
}
/**
 * 逻辑删除资源
 * @param resid
 */
function doDelRes(resid){
    if(typeof(resid)=="undefined"){
        alert("异常错误，参数异常!原因：resid is empty!");return;
    }
    if(!confirm("您确定要删除该资源吗?"))
        return;

    $.ajax({
        url:"resource?m=doDeleResource",
        type:'POST',
        data:{ resid:resid },
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
               alert(rps.msg)   ;
                try{pageGo('pmyRes');}catch(e){
                    window.close(); //关闭页面
                }
            }
        }
    });

}
/**
 * 分享资源
 * @param resid
 */
function doShareRes(){

    var resid=$("#dv_share input[type='hidden']").val();
    if(typeof(resid)=="undefined"||resid.Trim().length<1){
        alert('异常错误，原因：没有发现您要共享的资源ID!');return;
    }
    var shareTypeId=$("input[name='rdo_share_status']:checked").val();
    if(typeof(shareTypeId)=="undefined"||shareTypeId.Trim().length<1){
        alert('异常错误，原因：没有发现您要共享类型!');return;
    }
    var param={resid:resid,sharestatus:shareTypeId};
    //不用提示，直接提交
    $.ajax({
        url:"resource?m=doUpdateColumn",
        type:'POST',
        data:param,
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
//                if(shareTypeId==2)
//                    $("#a_share_"+resid).remove();
              alert("共享资源成功!");
              pageGo('pmyRes');
              closeModel('dv_share');
            }
        }
    });
}
