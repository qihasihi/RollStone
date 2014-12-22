<!DOCTYPE HTML>
<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-11-4
  Time: 上午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

    int sgrade = Integer.parseInt(request.getSession().getAttribute("session_grade").toString());
    String downUrl = request.getSession().getAttribute("IP_PROC_NAME").toString();
%>
<html>
<head>
    <title></title>

    <script type="text/javascript" src="js/common/uploadControl.js"></script>
    <script src="js/common/ajaxfileupload.js" type="text/javascript"></script>
    <script src="js/videoPlayer/swfobject.js" type="text/javascript"></script>
    <script src="uploadify/jquery.uploadify.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="uploadify/uploadify.css"/>

    <script type="text/javascript">
    var courseid = "${param.courseid}";
    var pList1;
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
        var url='tpres?toQueryLocalResourceListUnion';
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
            page_size : 20, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "mainTab1"
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
            page_size : 20, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "mainTab3"
        });
        pageGo('pList1');
        // pageGo('pList3');
        $("#uploadfile").uploadify({
            'buttonClass' : 'an_public1',
            //'buttonImage' : 'uploadify/browseBtn.png',
            'fileObjName' : 'uploadfile',
            swf           : 'uploadify/uploadify.swf',
            uploader      : 'uploadify/uploadFile.jsp',
            // uploader      : 'tpres?doUploadResource',
            buttonText    : '选择文件',
            width         : '100',
            height        : '20',
            auto          : false,
            multi         : false,
            fileSizeLimit :'1GB',
            queueSizeLimit : 1,
            //'progressData' : 'speed',
            itemTemplate  : '<div id="\\${fileID}" class="uploadify-queue-item">\
                    <div class="cancel">\
                        <a href="javascript:$(\'#\\${instanceID}\').uploadify(\'cancel\', \'\\${fileID}\')">X</a>\
                    </div>\
                    <span class="fileName">\\${fileName} (\\${fileSize})</span><span class="data"></span>\
                </div>',
            onSelect:function(file){
                var zzSuffix='<%=UtilTool._VIEW_SUFFIX_TYPE_REGULAR%>';
                if(file.type.length>0&&zzSuffix.indexOf(file.type.toLowerCase())>-1){
                    if(file.type.toLowerCase()!='.mp4'){
                        $('#uploadfile').uploadify('cancel', '*');
                        alert("仅限MP4格式的视频，请转换后再上传!");
                    }
                }
                $("#hd_filename").val(file.name);
            },
            'onSelectError':function(file, errorCode, errorMsg){
                switch(errorCode) {
                    case -100:     alert("上传的文件数量已经超出系统限制的"+$('#uploadfile').uploadify('settings','queueSizeLimit')+"个文件！");  break;
                    case -110:     alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#uploadfile').uploadify('settings','fileSizeLimit')+"大小!");break;
                    case -120:     alert("文件 ["+file.name+"] 大小异常!"); break;
                    case -130:     alert("文件 ["+file.name+"] 类型不正确!");break;
                }
            },
            'onFallback':function(){
                alert("您未安装FLASH控件，无法上传图片!请安装FLASH控件后再试!");
            },
            'onUploadStart':function(file){
                var param={};
                param.resid=nextid;
                $("#uploadfile").uploadify("settings", "formData",param);
            },
            'onUploadSuccess':function(file){
                var fname=file.name;
                var lastname = fname.substring(fname.lastIndexOf("."));
                var resname = $("#res_name");
                var restype = $("input[name='res_type']:checked");
                var crestype = $("input[name='tp_res_type']:checked").val();
                var resintroduce = $("#res_remark");
                var resid = nextid;
                var isConvert=false;
                var allowTypes='.xls,.xlsx,.doc,.docx,.ppt,.pptx,.xml,.pdf,.txt,.vsd,.rtf',msg='数据验证完毕!确认提交?';
                var param = {
                    resid: resid,
                    resname: resname.val(),
                    restype: restype.val(),
                    resourcetype: crestype,
                    resintroduce: resintroduce.val(),
                    courseid: courseid,
                    usertype: 2,
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
                    isConvert=true;
                }

                if (!confirm(msg))
                    return;
                else{
                    if(isConvert)
                        showModel('dv_loading','',200);
                }

                /*提交按钮不可用*/
                resetBtnAttr("a_submit","an_small","an_gray_small","",2);
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
                            $("#hd_elementid").val(nextid);
                            $("#resource_type").val(1);
                            queryResource(courseid, 'tr_task_obj', nextid);
                            $.fancybox.close();
                        }
                    }
                });

            },
            'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                alert('文件： ' + file.name + ' 未上传，原因： ' + errorString);
            }
        });


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
                var resname = itm.resname;
                if(resname.length>25){
                    resname=resname.substring(0,25)+"...";
                }
                htm+='<li><a href="javascript:subDataLocal('+itm.resid+','+itm.courseid+')"><b class="ico51" title="发任务"></b></a><span class="'+itm.suffixtype+'"></span>'+resname+'</li>';
            });
            $("#mainUl").html(htm);
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
            $("#mainUl").html('<tr><td colspan=2>没有数据</td></tr>');
            $("font[id='pList1font']").html("1/1");
        }
    }

    function getInvestReturnMethod3(rps){
        var htm='';
        if(rps.objList!=null&&rps.objList.length>0){
            $.each(rps.objList,function(idx,itm){
                var resname = itm.resname;
                if(resname.length>25){
                    resname=resname.substring(0,25)+"...";
                }
                htm+='<li><a href="javascript:subDataLocal('+itm.resid+','+itm.courseid+')"><b class="ico51" title="发任务"></b></a><span class="'+itm.suffixtype+'"></span>'+resname+'</li>';
            });
            $("#mainUl3").html(htm);

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
            $("#mainUl3").html(htm);
            $("font[id='pList3font']").html("1/1");
        }
    }
    function subDataLocal(resid,cid){
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
                    $("#hd_elementid").val(resid);
                    $("#resource_type").val(1);
                    queryResource(cid, 'tr_task_obj', resid);
                    $.fancybox.close();
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
        $("#localSelect").hide();
        $("#local").hide();
        $("#like").hide();
        $("#dv_upload_local").show();
        $("#localLi").removeClass("crumb");
        $("#upLi").addClass("crumb");
        $("#remoteLi").removeClass("crumb");
    }

    function showLocal(){
        $("#localSelect").show();
        $("#local").show();
        $("#like").hide();
        $("#dv_upload_local").hide();
        $("#localLi").addClass("crumb");
        $("#upLi").removeClass("crumb");
        $("#remoteLi").removeClass("crumb");
    }

    function showRemote(){
        var url = "tpres?m=toRemoteResources&courseid=${courseid}&subjectid=${param.subjectid}";
        $("#dv_content").load(url,function(){
            $("#dv_content").show();
        });
    }
    /************************************上传资源***************************************/
    function subUploadRes(usertype){
        var resname = $("#res_name");
        var restype = $("input[name='res_type']:checked");
        var crestype = $("input[name='tp_res_type']:checked").val();
        var resintroduce = $("#res_remark");
        //var uploadfile = $("#uploadfile");
        var uploadfile=$("#uploadfile-queue .uploadify-queue-item")
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
        if(uploadfile.length<1){
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
            var fname=$("#hd_filename").val();
            if (fname.Trim().length < 1) {
                alert('您尚未选择文件，请选择！');
                return;
            }
            $('#uploadfile').uploadify('upload','*')
        }
    }
    </script>
</head>
<body>
<input type="hidden" id="hd_filename" name="hd_filename">
<div class="public_float public_float960">
    <p class="float_title"><strong>资源学习&mdash;&mdash;选择资源</strong></p>
    <div class="subpage_lm">
        <ul>
            <li id="localLi" class="crumb"><a href="javascript:showLocal();">本地资源</a></li>
            <li id="remoteLi"><a href="javascript:showRemote();">远程资源</a></li>
            <li id="upLi"><a href="javascript:showUpload()">上传资源</a></li>
        </ul>
    </div>
    <div class="jxxt_zhuanti_rw_add">
        <p class="public_input t_r" id="localSelect">
            <select name="select3" id="grade">
                <c:if test="${!empty gradeList}">
                    <c:forEach items="${gradeList}" var="g">
                        <option value="${g.gradeid}">${g.gradename}</option>
                    </c:forEach>
                </c:if>
            </select>
            <input name="textfield2" id="likename" type="text" class="w240" placeholder="资源名称/专题名称" />
            <a href="javascript:showLike()" class="an_search" title="查询"></a></p>
        <div id="local">
            <ul class="public_float_list" id="mainUl">

            </ul>
            <div class="nextpage m_t_15"> <form id="pListForm1" name="pListForm1">

                <p id="pListaddress1"  style="display:none"></p>
            </form></div>
        </div>
        <div id="like" style="display: none">
            <ul class="public_float_list" id="mainUl3"></ul>
            <div class="nextpage">
                <form id="pListForm3" name="pListForm3">
                    <p class="Mt20" id="pListaddress3" align="center"  style="display:none"></p>
                </form>
            </div>
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
                        <p >提示： 1. 附件限一个，<1G。<br>2. 视频限MP4格式，建议使用<a class="font-darkblue" href="<%=downUrl%>uploadfile/cqmygysdssjtwmydtsgx/20141119.zip">格式工厂</a>等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒</p>
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
                <td><a id="a_submit" onclick="subUploadRes(2)"   class="an_public1">提&nbsp;交</a><!--<a href="javascript:void(0);" onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>--></td>
            </tr>
        </table>
    </div>

</div>

</body>
</html>
