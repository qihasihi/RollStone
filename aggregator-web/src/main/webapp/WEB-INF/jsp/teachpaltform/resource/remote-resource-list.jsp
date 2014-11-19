<!DOCTYPE HTML>
<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-11-4
  Time: 上午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

    int sgrade = Integer.parseInt(request.getSession().getAttribute("session_grade").toString());
    String downUrl = request.getSession().getAttribute("IP_PROC_NAME").toString();
%>
<html>
<head>
    <base target="_self">
    <title></title>
    <script type="text/javascript">
        var pListG;
        var pListZ;
        var versionid="${versionid}";
        var courseid="${courseid}";
        $(function(){
            $("#grade option[value='<%=sgrade%>']").attr("selected",true);
            var bl1 = ${sign1};
            var bl2 = ${sign2};
            if(bl1){
                $("#gaoDiv").show();
            }else{
                $("#gaoDiv").hide();
            }
            if(bl2){
                $("#zhiDiv").show();
            }else{
                $("#zhiDiv").hide();
            }
            //getRemoteResources(true);
            //查询的本地资源？
            var urlG='tpres?m=getRemoteResources1';
            pListG = new PageControl( {
                post_url : urlG,
                page_id : 'pageG',
                page_control_name : "pListG",
                post_form : document.pListFormG,
                gender_address_id : 'pListaddressG',
                http_free_operate_handler : preeDoPageSubG, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethodG, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 4, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "gaoqing"
            });
            var urlZ='tpres?m=getRemoteResources2';
            pListZ = new PageControl( {
                post_url : urlZ,
                page_id : 'pageZ',
                page_control_name : "pListZ",
                post_form : document.pListFormZ,
                gender_address_id : 'pListaddressZ',
                http_free_operate_handler : preeDoPageSubZ, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethodZ, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 10, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "zhishi"
            });
            pageGo('pListG');
            pageGo('pListZ');
        });
        function dosub(taskvalueid,resourcetype,remotetype,resname){
            $("#hd_elementid").val(taskvalueid);
            $("#resource_type").val(resourcetype);
            $("#remote_type").val(remotetype);
            $("#dv_res_name").html('<span class="ico_mp41"></span>'+resname);
            $("#resource_name").val(resname);
            $.fancybox.close();
        }

        function showRemoteDetail(hdid,resid){
            var url ='';
            if(hdid!=0){
                url = 'tpres?m=toRemoteResourcesDetail&hd_res_id='+hdid;
            }else if(resid!=0){
                url = 'tpres?m=toRemoteResourcesDetail&res_id='+resid;
            }
            var param = "dialogHeight:800px;dialogWidth:900px;status:no;location:no";
            window.showModalDialog(url, "", param);
        }
        //高清组织参数
        function preeDoPageSubG(pObj){
            var param={};
            if($("#keyword").val().length>0){
                param.keyword=$("#keyword").val();
                param.versionid = versionid;
            }
            param.subjectid=4;
            param.gradeid = $("#grade").val();
            param.courseid=courseid;
            pObj.setPostParams(param);
        }

        //查询远程高清
        function getInvestReturnMethodG(rps){
            if(rps.type=="success"){
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    var htm='';
                    $.each(rps.objList[0],function(idx,itm){
                        var h="";
                        var hname = itm.HD_NAME;
                        if(hname.length>25){
                            hname = hname.substring(0,25);
                        }
                        h='<a class="ico51" href="javascript:dosub('+itm.HD_RES_ID+','+2+','+1+',\''+itm.HD_NAME+'\')" title="发任务"></a>';
                        if(rps.objList[1]!=null&&rps.objList[1].length>0){
                            $.each(rps.objList[1],function(ix,im){
                                if(itm.HD_RES_ID==im.taskvalueid){
                                    h='<span class="ico52" title="已发任务"></span>';
                                    return false;
                                }
                            });
                            htm+='<li><a class="kapian" target="_blank"  href="tpres?m=toRemoteResourcesDetail&hd_res_id='+itm.HD_RES_ID+'"><p><img width="215" height="122"  src="'+itm.IMG_URL+'"/></p><p class="text">'+itm.TEACHER_NAME+'&nbsp;&nbsp;'+hname+'</p></a>' +
                                    '<p class="pic">'+h+'</p></li>';
                        }else{
                            htm+='<li><a class="kapian" target="_blank"  href="tpres?m=toRemoteResourcesDetail&hd_res_id='+itm.HD_RES_ID+'"><p><img width="215" height="122"  src="'+itm.IMG_URL+'"/></p><p class="text">'+itm.TEACHER_NAME+'&nbsp;&nbsp;'+hname+'</p></a>' +
                                    '<p class="pic"><a class="ico51" href="javascript:dosub('+itm.HD_RES_ID+','+2+','+1+',\''+itm.HD_NAME+'\')" title="发任务"></a></p></li>';
                        }
                    });
                    $("#gaoqing").html(htm);
                }else{
                    $("#gaoqing").html(' <li><img src="images/pic06_140722.png" width="215" height="160"></li>');
                }
                if(rps.objList[0].length>0){
                    pListG.setPagetotal(rps.presult.pageTotal);
                    pListG.setRectotal(rps.presult.recTotal);
                    pListG.setPageSize(rps.presult.pageSize);
                    pListG.setPageNo(rps.presult.pageNo);
                    $("font[id='pListGfont']").html(rps.presult.pageNo+"/"+rps.presult.pageTotal);
                }else
                {
                    pListG.setPagetotal(0);
                    pListG.setRectotal(0);
                    pListG.setPageNo(1);
                    $("font[id='pListGfont']").html("1/1");
                }
                pListG.Refresh();
            }else{

            }
        }

        //高清组织参数
        function preeDoPageSubZ(pObj){
            var param={};
            if($("#keyword").val().length>0){
                param.keyword=$("#keyword").val();
                param.versionid = versionid;
            }
            param.subjectid=4;
            param.gradeid = $("#grade").val();
            param.courseid=courseid;
            pObj.setPostParams(param);
        }

        //查询远程高清
        function getInvestReturnMethodZ(rps){
            if(rps.type=="success"){
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    var htm='';
                    $.each(rps.objList[0],function(idx,itm){
                        var h="";
                        h='<a href="javascript:dosub('+itm.RES_ID+','+2+','+2+',\''+itm.RES_NAME+'\')"><b class="ico51" title="发任务"></b></a>';
                        if(rps.objList[1]!=null&&rps.objList[1].length>0){
                            $.each(rps.objList[1],function(ix,im){
                                if(itm.RES_ID==im.taskvalueid){
                                    h='<b class="ico52" title="已发任务"></b>';
                                    return false;
                                }
                            });
                            htm+='<li>'+h+'<a target="_blank" href="tpres?m=toRemoteResourcesDetail&res_id='+itm.RES_ID+'" >'+itm.RES_NAME+'</a></li>';
                        }else{
                            htm+='<li><a href="javascript:dosub('+itm.RES_ID+','+2+','+2+',\''+itm.RES_NAME+'\')"><b class="ico51" title="发任务"></b></a><a target="_blank" href="tpres?m=toRemoteResourcesDetail&res_id='+itm.RES_ID+'" >'+itm.RES_NAME+'</a></li>';
                        }
                    });
                    $("#zhishi").html(htm);
                }else{
                    $("#zhishi").html('<li>没有资源</li>');
                }
                if(rps.objList[0].length>0){
                    pListZ.setPagetotal(rps.presult.pageTotal);
                    pListZ.setRectotal(rps.presult.recTotal);
                    pListZ.setPageSize(rps.presult.pageSize);
                    pListZ.setPageNo(rps.presult.pageNo);
                    $("font[id='pListZfont']").html(rps.presult.pageNo+"/"+rps.presult.pageTotal);
                }else
                {
                    pListZ.setPagetotal(0);
                    pListZ.setRectotal(0);
                    pListZ.setPageNo(1);
                    $("font[id='pListZfont']").html("1/1");
                }
                pListZ.Refresh();
            }else{

            }
        }

        //进入远程资源页
        function getRemoteResources(type){
            if(!type){
                pageNo=1;
                pageNo2=1;
            }
            $.ajax({
                url:'tpres?m=getRemoteResources',
                data:{
                    gradeid:3,
                    subjectid:4,
                    versionid:versionid,
                    pageNow:pageNo,
                    pageSize:8,
                    courseid:courseid,
                    keyword:$("#keyword").val()
                },
                type:'POST',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type=="success"){
                        if(rps.objList[0]!=null&&rps.objList[0].length>0){
                            var htm='';
                            $.each(rps.objList[0],function(idx,itm){
                                var h="";
                                var hname = itm.HD_NAME;
                                if(hname.length>25){
                                    hname = hname.substring(0,25);
                                }
                                h='<a class="ico51" href="javascript:dosub('+itm.HD_RES_ID+','+2+','+1+',\''+itm.HD_NAME+'\')" title="发任务"></a>';
                                if(rps.objList[2]!=null&&rps.objList[2].length>0){
                                    $.each(rps.objList[2],function(ix,im){
                                        if(itm.HD_RES_ID==im.taskvalueid){
                                            h='<span class="ico52" title="已发任务"></span>';
                                            return false;
                                        }
                                    });
                                    htm+='<li><a class="kapian"  href="javascript:showRemoteDetail('+itm.HD_RES_ID+',0)"><p><img width="215" height="122"  src="'+itm.IMG_URL+'"/></p><p class="text">'+itm.TEACHER_NAME+'&nbsp;&nbsp;'+hname+'</p></a>' +
                                            '<p class="pic">'+h+'</p></li>';
                                }else{
                                    htm+='<li><a class="kapian"  href="javascript:showRemoteDetail('+itm.HD_RES_ID+',0)"><p><img width="215" height="122"  src="'+itm.IMG_URL+'"/></p><p class="text">'+itm.TEACHER_NAME+'&nbsp;&nbsp;'+hname+'</p></a>' +
                                            '<p class="pic"><a class="ico51" href="javascript:dosub('+itm.HD_RES_ID+','+2+','+1+',\''+itm.HD_NAME+'\')" title="发任务"></a></p></li>';
                                }
                            });
                            $("#gaoqing").html(htm);
                        }else{
                            $("#gaoqing").html(' <li><img src="images/pic06_140722.png" width="215" height="160"></li>');
                        }
                        if(rps.objList[1]!=null){
                            if(rps.objList[1]=="0"){
                                $("#moreGaoqing").show();
                                // pageNo=pageNo+1;
                            }else{
                                $("#moreGaoqing").hide();
                            }
                        }
                        if(rps.objList[3]!=null&&rps.objList[3].length>0){
                            var htm='';
                            $.each(rps.objList[3],function(idx,itm){
                                var h="";
                                h='<a href="javascript:dosub('+itm.RES_ID+','+2+','+2+',\''+itm.RES_NAME+'\')"><b class="ico51" title="发任务"></b></a>';
                                if(rps.objList[5]!=null&&rps.objList[5].length>0){
                                    $.each(rps.objList[5],function(ix,im){
                                        if(itm.RES_ID==im.taskvalueid){
                                            h='<b class="ico52" title="已发任务"></b>';
                                            return false;
                                        }
                                    });
                                    htm+='<li>'+h+'<a  href="javascript:showRemoteDetail(0,'+itm.RES_ID+')" >'+itm.RES_NAME+'</a></li>';
                                }else{
                                    htm+='<li><a href="javascript:dosub('+itm.RES_ID+','+2+','+2+',\''+itm.RES_NAME+'\')"><b class="ico51" title="发任务"></b></a><a  href="javascript:showRemoteDetail(0,'+itm.RES_ID+')" >'+itm.RES_NAME+'</a></li>';
                                }
                            });
                            $("#zhishi").html(htm);
                        }else{
                            $("#zhishi").html('<li>没有资源</li>');
                        }
                        if(rps.objList[4]!=null){
                            if(rps.objList[4]=="0"){
                                $("#moreZhishi").show();
                                // pageNo2=pageNo2+1;
                            }else{
                                $("#moreZhishi").hide();
                            }
                        }
                    }else{

                    }
                }
            });
        }

        function showUpload(){
            $("#remoteDiv").hide();
            $("#dv_upload_local").show();
            $("#localLi").removeClass("crumb");
            $("#upLi").addClass("crumb");
            $("#remoteLi").removeClass("crumb");
        }

        function showLocal(){
            var url = "task?m=toTaskElementDetial&operate_type=1&subjectid=${param.subjectid}&tasktype=1&courseid=${param.courseid}";
            $("#dv_content").load(url,function(){
                $("#dv_content").show();
            });
        }

        function showRemote(){
            $("#remoteDiv").show();
            $("#dv_upload_local").hide();
            $("#localLi").removeClass("crumb");
            $("#upLi").removeClass("crumb");
            $("#remoteLi").addClass("crumb");
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
                    alert('错误，请添加文件!');
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
                            subData(nextid);
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
                                        if (rps.type == "error") {
                                            alert(rps.msg);
                                        } else {
                                            alert(rps.msg);
                                            $("#hd_elementid").val(nextid);
                                            $("#resource_type").val(1);
                                            queryResource(courseid, 'tr_task_obj', nextid);
                                            $.fancybox.close();
                                        }
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
    <%--<div class="subpage_head"><span class="ico55"></span><strong>添加任务&mdash;&mdash;选择资源</strong></div>--%>
    <%--<div class="content2">--%>
        <%--<div class="subpage_lm">--%>
            <%--<ul>--%>
                <%--<li id="localLi"><a href="javascript:showLocal();">本地资源</a></li>--%>
                <%--<li id="remoteLi"  class="crumb"><a href="javascript:showRemote();">远程资源</a></li>--%>
                <%--<li id="upLi"><a href="javascript:showUpload()">上传资源</a></li>--%>
            <%--</ul>--%>
        <%--</div>--%>

        <%--<div class="jxxt_zhuanti_rw_add" id="remoteDiv">--%>
            <%--<p class="public_input t_r">--%>
                <%--&lt;%&ndash;<select name="select3" id="select3">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<option selected>高一</option>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<option>教职工</option>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<option>学生</option>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                <%--<input name="textfield2"  id="keyword" type="text" class="w240" placeholder="资源名称/专题名称" />--%>
                <%--<a href="javascript:getRemoteResources(false)" class="an_search" title="查询"></a></p>--%>
           <%--<div id="gaoDiv">--%>
                <%--<p class="font-black p_b_10"><strong>高清课堂</strong></p>--%>
                <%--<ul class="gqkt font-black" id="gaoqing">--%>

                <%--</ul>--%>
                <%--<p class="font-darkblue t_c clearit" id="moreGaoqing"><a href="javascript:getMoreResources()" ><span class="ico49a"></span>查看更多资源</a></p>--%>
           <%--</div>--%>
            <%--<div id="zhiDiv">--%>
                <%--<p class="font-black p_tb_10 clearit"><strong>知识导学</strong></p>--%>
                <%--<ul class="zsdx font-black" id="zhishi">--%>

                <%--</ul>--%>
                <%--<p class="font-darkblue t_c" id="moreZhishi"><a href="javascript:getMoreZhishi()" ><span class="ico49a"></span>查看更多资源</a></p>--%>
            <%--</div>--%>
        <%--</div>--%>



        <div class="public_float public_float960">
            <p class="float_title"><strong>资源学习&mdash;&mdash;选择资源</strong></p>
            <div class="subpage_lm">
                    <ul>
                        <li id="localLi"><a href="javascript:showLocal();">本地资源</a></li>
                        <li id="remoteLi"  class="crumb"><a href="javascript:showRemote();">远程资源</a></li>
                        <li id="upLi"><a href="javascript:showUpload()">上传资源</a></li>
                    </ul>
            </div>

            <div class="jxxt_zhuanti_rw_add" id="remoteDiv">
                <p class="public_input t_r">
                    <select name="select3" id="grade">
                        <c:if test="${!empty gradeList}">
                            <c:forEach items="${gradeList}" var="g">
                                <option value="${g.gradeid}">${g.gradename}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <input name="textfield2" id="keyword" type="text" class="w240" placeholder="资源名称/专题名称" />
                    <a href="javascript:pageGo('pListG');pageGo('pListZ');" class="an_search" title="查询"></a></p>
                <p class="font-black p_b_10"><strong>高清课堂</strong></p>
                <ul class="gqkt font-black" id="gaoqing">

                </ul>
                <div class="nextpage m_t_15"> <form id="pListFormG" name="pListFormG">
                    <p class="Mt20" id="pListaddressG"></p>
                </form></div>

                <p class="font-black p_tb_10 clearit"><strong>知识导学</strong></p>
                <ul class="zsdx font-black" id="zhishi">

                </ul>
                <div class="nextpage m_t_15"> <form id="pListFormZ" name="pListFormZ">
                    <p class="Mt20" id="pListaddressZ"></p>
                </form></div>
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
                                <p class="font-gray">提示： 1. 附件限一个，<2G。<br>2. 视频限MP4格式，建议使用格<a class="font-darkblue" href="<%=downUrl%>uploadfile/cqmygysdssjtwmydtsgx/20141119.zip">格式工厂</a>等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒</p>
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

    <%--</div>--%>
</body>
</html>
