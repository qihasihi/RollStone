<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-14
  Time: 下午2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<%
    int sgrade = Integer.parseInt(request.getSession().getAttribute("session_grade").toString());
%>
<html>
<head>
`
<title>${sessionScope.CURRENT_TITLE}</title>
<!-- 上传控件 -->
<script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" />
<script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath %>comment/comment_star.css"/>
<script type="text/javascript" src="<%=basePath %>comment/commentScorePlug.js"></script>
<script type="text/javascript" src="js/resource/new_resbase.js"></script>
<script type="text/javascript"
        src="<%=basePath %>js/teachpaltform/resource.js"></script>　
<script type="text/javascript">
var courseid = "${param.courseid}";
var pList1;
var pList2;
var pList3;
var tasktype="${param.tasktype}";
var gradeid="${param.gradeid}";
var nextid="${nextid}";
var url="${fileSystemIpPort}upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id="+nextid;
var operate_type="${param.operate_type}";
$(function () {
    $("#grade option[value='<%=sgrade%>']").attr("selected",true);

    var bl = ${sign};
    if(bl){
        $("#remoteLi").show();
    }else{
        $("#remoteLi").hide();
    }
    //查询的本地资源？
    var url='tpres?toQueryLocalResourceList';
    pList1 = new PageControl( {
        post_url : url,
        page_id : 'page1',
        page_control_name : "pList1",
        post_form : document.pListForm1,
        gender_address_id : 'pListaddress1',
        http_free_operate_handler : preeDoPageSub1, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod1, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 5, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "mainTab1"
    });
    //查询的关联专题
    pList2 = new PageControl( {
        post_url : 'tpres?toQueryRelatedResourceList',
        page_id : 'page2',
        page_control_name : "pList2",
        post_form : document.pListForm2,
        gender_address_id : 'pListaddress2',
        http_free_operate_handler : preeDoPageSub2, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod2, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 5, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "mainTab2"
    });
    //糢糊查询
    pList3 = new PageControl( {
        post_url : 'tpres?toQueryLikeResourceList',
        page_id : 'page3',
        page_control_name : "pList3",
        post_form : document.pListForm3,
        gender_address_id : 'pListaddress3',
        http_free_operate_handler : preeDoPageSub3, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod3, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 5, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "mainTab3"
    });
    pageGo('pList1');
    pageGo('pList2');
    // pageGo('pList3');
});

function preeDoPageSub1(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid};
    param.taskflag=1;
    pObj.setPostParams(param);
}
function preeDoPageSub2(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid};
    //param.taskflag=1;
    pObj.setPostParams(param);
}
function preeDoPageSub3(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    var param={};
    param.gradeid =$("#grade").val();
    param.subjectid=${param.subjectid};
    var name = $("#likename").val();
    if(name==null||name.length<1){
        alert("请输入专题或者资源关键字");
        return;
    }else{
        param.name=$("#likename").val();
    }
    pObj.setPostParams(param);
}
function getInvestReturnMethod1(rps){
    var htm='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
            var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
            var type=itm.resourcetype==1?"学习资源":"教学参考";

            htm+='<tr>';
            htm+='<td><a href="javascript:subData('+itm.resid+')" class="ico51" title="发任务"></a>';
            htm+='<span class="'+itm.suffixtype+'"></span></td>';
            htm+='<td><p>'+itm.resname+'</p>';
            htm+='<p>'+introduce+'</p>';
            htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
            htm+='</tr>';
        });
        $("#mainTab1").html(htm);

        if(rps.objList.length>0){
            pList1.setPagetotal(rps.presult.pageTotal);
            pList1.setRectotal(rps.presult.recTotal);
            pList1.setPageSize(rps.presult.pageSize);
            pList1.setPageNo(rps.presult.pageNo);
            $("font[id='pList1font']").html(rps.presult.pageNo+"/"+rps.presult.pageTotal);
        }else
        {
            pList1.setPagetotal(0);
            pList1.setRectotal(0);
            pList1.setPageNo(1);
            $("font[id='pList1font']").html("1/1");
        }
        pList1.Refresh();
    }else{
        $("#mainTab1").html('<tr><td colspan=2>没有数据</td></tr>');
        $("font[id='pList1font']").html("1/1");
    }
}
function getInvestReturnMethod2(rps){
    var htm='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
            var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
            var type=itm.resourcetype==1?"学习资源":"教学参考";

            htm+='<tr>';
            htm+='<td>';
            if(itm.taskflag<1)
                htm+='<a href="javascript:subData2('+itm.resid+','+itm.courseid+')" class="ico51" title="发任务"></a>';
            else
                htm+='<a class="ico52" title="已发任务"></a>';

            htm+='<span class="'+itm.suffixtype+'"></span></td>';
            htm+='<td><p>'+itm.resname+'</p>';
            htm+='<p>'+introduce+'</p>';
            htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
            htm+='</tr>';
        });
        $("#mainTab2").html(htm);

        if(rps.objList.length>0){
            pList2.setPagetotal(rps.presult.pageTotal);
            pList2.setRectotal(rps.presult.recTotal);
            pList2.setPageSize(rps.presult.pageSize);
            pList2.setPageNo(rps.presult.pageNo);
            $("font[id='pList2font']").html(rps.presult.pageNo+"/"+rps.presult.pageTotal);
        }else
        {
            pList2.setPagetotal(0);
            pList2.setRectotal(0);
            pList2.setPageNo(1);
            $("font[id='pList2font']").html("1/1");
        }
        pList2.Refresh();
    }else{
        $("#mainTab2").html('<tr><td colspan=2>没有数据</td></tr>');
        $("font[id='pList2font']").html("1/1");
    }
}
function getInvestReturnMethod3(rps){
    var htm='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){
            var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
            var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
            var type=itm.resourcetype==1?"学习资源":"教学参考";

            htm+='<tr>';
            htm+='<td><a href="javascript:subData2('+itm.resid+','+itm.courseid+')" class="ico51" title="发任务"></a>';
            htm+='<span class="'+itm.suffixtype+'"></span></td>';
            htm+='<td><p><a href="javascript:void(0);" target="_blank">'+itm.resname+'</a></p>';
            htm+='<p>'+introduce+'</p>';
            htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
            htm+='</tr>';
        });
        $("#mainTab3").html(htm);

        if(rps.objList.length>0){
            pList3.setPagetotal(rps.presult.pageTotal);
            pList3.setRectotal(rps.presult.recTotal);
            pList3.setPageSize(rps.presult.pageSize);
            pList3.setPageNo(rps.presult.pageNo);
            $("font[id='pList3font']").html(rps.presult.pageNo+"/"+rps.presult.pageTotal);
        }else
        {
            pList3.setPagetotal(0);
            pList3.setRectotal(0);
            pList3.setPageNo(1);
            $("font[id='pList3font']").html("1/1");
        }
        pList3.Refresh();
    }else{
        htm+='<tr><td colspan="=20">没有资源</td></tr>';
        $("#mainTab3").html(htm);
        $("font[id='pList3font']").html("1/1");
    }
}
function subData(resid){
    if (window.opener != undefined) {
        //for chrome
        window.opener.returnValue =resid+",1";
    }
    else {
        window.returnValue =resid+",1";
    }
    window.close();
}
function subData2(resid,cid){
    $.ajax({
        url: 'tpres?m=addResource',
        type: 'post',
        data: {
            courseid: courseid,
            resArray: resid,
            resourcetype: 1
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
                if (window.opener != undefined) {
                    //for chrome
                    window.opener.returnValue =resid+",1,"+cid;
                }
                else {
                    window.returnValue =resid+",1,"+cid;
                }
                window.close();
            }
        }
    });

}
function showLike(){
    var name = $("#likename").val();
    if(name==null||name.length<1){
        alert("请输入专题或者资源关键字");
        return;
    }
    $("#local").hide();
    $("#dv_upload_local").hide();
    $("#like").show();
    pageGo('pList3');
}
function showUpload(){
    $("#local").hide();
    $("#like").hide();
    $("#dv_upload_local").show();
}
/************************************上传资源***************************************/
function subUploadRes(usertype){
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
                    if (window.opener != undefined) {
                        //for chrome
                        window.opener.returnValue =nextid+",1";
                    }
                    else {
                        window.returnValue =nextid+",1";
                    }
                    window.close();
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
                                if (window.opener != undefined) {
                                    //for chrome
                                    window.opener.returnValue =nextid+",1";
                                }
                                else {
                                    window.returnValue =nextid+",1";
                                }
                                window.close();
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
</script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务&mdash;&mdash;选择资源</strong></div>
<div class="content2">
    <div class="subpage_lm">
        <ul>
            <li class="crumb"><a href="task?m=toTaskElementDetial&operate_type=1&subjectid=${param.subjectid}&tasktype=1&courseid=${param.courseid}">本地资源</a></li>
            <li id="remoteLi"><a href="tpres?m=toRemoteResources&courseid=${courseid}&subjectid=${param.subjectid}"  >远程资源</a></li>
        </ul>
    </div>

    <div class="jxxt_zhuanti_rw_add">
        <p class="public_input f_right">
            <select name="select3" id="grade">
                <c:if test="${!empty gradeList}">
                    <c:forEach items="${gradeList}" var="g">
                        <option value="${g.gradeid}">${g.gradename}</option>
                    </c:forEach>
                </c:if>
            </select>
            <input name="textfield2" id="likename" type="text" class="w240" placeholder="资源名称/专题名称" />
            <a href="javascript:showLike()" class="an_search" title="查询"></a></p>
        <p class="font-darkblue p_b_20"><a href="javascript:showUpload()"><span class="ico26"></span>上传资源</a></p>
        <div id="local">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <caption>本专题资源库</caption>
                <tbody id="mainTab1">

                </tbody>

            </table>

            <div class="nextpage">
                <form id="pListForm1" name="pListForm1">
                    <span><a href="javascript:;"  onclick="pagePre('pList1');"><b class="before"></b></a></span>&nbsp;
                    <font  id="pList1font">1/1</font>&nbsp;
                    <span><a href="javascript:;" onclick="pageNext('pList1');"><b class="after"></b></a></span>
                    <p id="pListaddress1"  style="display:none"></p>
                </form>
            </div>
            <h6></h6>

            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <caption>关联专题资源库</caption>
                <tbody  id="mainTab2">

                </tbody>

            </table>
            <div class="nextpage">
                <form id="pListForm2" name="pListForm2">
                    <span><a href="javascript:;"  onclick="pagePre('pList2');"><b class="before"></b></a></span>&nbsp;
                    <font  id="pList2font">1/1</font>&nbsp;
                    <span><a href="javascript:;" onclick="pageNext('pList2');"><b class="after"></b></a></span>
                    <p class="Mt20" id="pListaddress2" align="center" style="display:none"></p>
                </form>
            </div>
        </div>
        <div id="like" style="display: none">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <tbody id="mainTab3">

                </tbody>

            </table>
            <div class="nextpage">
                <form id="pListForm3" name="pListForm3">
                    <span><a href="javascript:;"  onclick="pagePre('pList3');"><b class="before"></b></a></span>&nbsp;
                    <font id="pList3font">1/1</font>&nbsp;
                    <span><a href="javascript:;" onclick="pageNext('pList3');"><b class="after"></b></a></span>
                    <p class="Mt20" id="pListaddress3" align="center"  style="display:none"></p>
                </form>
            </div>
        </div>
        <div id="dv_upload_local" style="display: none">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
                <col class="w100"/>
                <col class="w700"/>

                <tr style="display: none;">
                    <th><span class="ico06"></span>资源类别：</th>
                    <td class="font-black">
                        <input  name="tp_res_type" checked="checked" type="radio" value="1"  />
                        学习资源&nbsp;&nbsp;&nbsp;&nbsp;
                        <input  name="tp_res_type" type="radio" value="2"  />
                        教学参考
                    </td>
                </tr>
                <tr>
                    <th><span class="ico06"></span>资源名称：</th>
                    <td><input name="res_name" id="res_name" class="w430" /></td>
                </tr>
                <tr>
                    <th><span class="ico06"></span>资源类型：</th>
                    <td class="font-black">
                        <c:if test="${!empty resType}">
                        <c:forEach items="${resType}" var="d">
                            <c:if test="${d.dictionaryvalue!=6}">
                            <input type="radio" name="res_type"  value="${d.dictionaryvalue}" />${d.dictionaryname}&nbsp;&nbsp;&nbsp;&nbsp;
                            </c:if>
                        </c:forEach>
                        </c:if>
                </tr>
                <tr>
                    <th>&nbsp;&nbsp;资源简介：</th>
                    <td><textarea id="res_remark" class="h90 w600"></textarea></td>
                </tr>
                <tr>
                    <th>&nbsp;&nbsp;附件上传：</th>
                    <td><p class="font-black">
                        <input name="rdo_uplaod" type="radio" value="1" checked  onclick="p_res_file.style.display='block';dv_super_file.style.display='NONE';" />
                        普通附件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;"
                                                       onmousemove="var dvstyle=dv_allow_filetype.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                                       onmouseout="dv_allow_filetype.style.display='none';" class="font-darkblue">支持的文件类型</a></p>
                        <div class="jxxt_zhuanti_zy_add" id="p_res_file">
                            <input type="file" name="uploadfile" id="uploadfile" class="w410" /><!--<a href="1" class="an_public3">上&nbsp;传</a>-->
                            <p class="font-gray">提示： 1. 附件限一个，<2G。<br>2. 视频限MP4格式，建议使用格式工厂等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒</p>
                        </div>
                        <div class="jxxt_zhuanti_zy_add" id="dv_super_file" style="display: none">
                            <div id="uploadcontrol_div" >
                                <!-- 加载控件 <span id="span_obj_add"></span>

                                        <script type="text/javascript">
                                            var url="${fileSystemIpPort}upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id="+nextid;
                                            if (navigator.userAgent.indexOf("MSIE") > 0) {
                                                //$("#uploader").attr("classid","clsid:787D6738-39C4-458C-BE8B-0084503FC021");
                                                //var h='<object classid="clsid:787D6738-39C4-458C-BE8B-0084503FC021" id="uploader"></object>';
                                                //$("#span_obj_add").html(h);
                                                window.uploader = new ActiveXObject("EttUploadPlugin.CoEttUploadPlugin");
                                            } else if (isMozilla = navigator.userAgent.indexOf("Chrome") > 0) {
                                                var h=' <object id="uploader" type="application/x-itst-activex" clsid="{787D6738-39C4-458C-BE8B-0084503FC021}" style="width: 10px; height: 10px"></object>';
                                                $("#span_obj_add").html(h);
                                            }else {
                                                alert("异常，上传控件目前只支持Chrome,IE!");
                                            }
                                        </script>
                                        <button id="btnUpload">上传文件</button>
                                        <br />
                                        <div id="divUploadInfo" class="info-container" data-bind="foreach: infos">
                                            <div style="height: 15px">
                                                <span class="info-filename" data-bind="text: filename"></span>
                                                <div class="info-text-right">
                                                    上传速度: <span data-bind="text: rate"></span>/s &nbsp;&nbsp;&nbsp; 文件大小：<span data-bind="text: fileSize"></span>
                                                </div>
                                            </div>
                                            <div class="info-pb-container">
                                                <div data-bind="attr: { id: 'upload_pb_' + index }" class="info-pb">
                                                    <div class="info-pb-label">等待上传...</div>
                                                </div>
                                            </div>
                                            <div style="height: 15px;padding-top:2px;">
                                                <button data-bind="attr: { id: 'upload_play_' + index }" class="info-button">开始</button>
                                                <button data-bind="attr: { id: 'upload_pause_' + index }" class="info-button">暂停</button>
                                                <button data-bind="attr: { id: 'upload_cancel_' + index }" class="info-button">取消</button>
                                            </div>
                                        </div>-->
                            </div>
                            <p><span class="font-gray">提示： 1. 附件限一个，<2G。<br>2. 视频限MP4格式，建议使用格式工厂等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒</span>&nbsp;&nbsp;&nbsp;&nbsp;<a class="font-darkblue" href="http://202.99.47.77/fileoperate/uploadfile/tmp/upload-chajian2013-07-29.exe">下载插件</a>
                                <a class="font-darkblue" href="template/IEUpload Solution.docx">使用说明</a></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>&nbsp;</th>
                    <td><a id="a_submit" onclick="subUploadRes(2)"   class="an_small">提&nbsp;交</a><!--<a href="javascript:void(0);" onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>--></td>
                </tr>
            </table>
        </div>
    </div>
    <!--上传须知与文件类型-->
    <div class="public_windows font-black" id="dv_allow_filetype" name="dv_allow_filetype" style="z-index:1000;display:none;position: absolute;background-color:white;padding: 10px 40px 40px 30px;">
        <h3><!--<a href="1" target="_blank" title="关闭"></a>-->上传文件类型</h3>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您可以上传以下文件格式：</strong></p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;文档<span class="ico_doc1"></span>doc/docx<span class="ico_pdf1"></span>pdf<span class="ico_xls1"></span>xls/xlsx<span class="ico_txt1"></span>txt<span class="ico_wps1"></span>wps<span class="ico_vsd1"></span>vsd<span class="ico_rtf1"></span>rtf<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ico_ppt1"></span>ppt/pptx、pot、pps</p>
        <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;图片<span class="ico_jpg1"></span>bmp  jpg  png  gif  tiff  pcx  tga</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;音频<span class="ico_mp31"></span>mp3 midi wma realaudi asf wav</p>
        <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;视频<span class="ico_mp41"></span>mp4</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;动画<span class="ico_swf1"></span>swf</p>
    </div>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
