<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-11-15
  Time: 上午10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
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
                                        alert(rps.msg);
                                        $("#hd_elementid").val(nextid);
                                        $("#resource_type").val(1);
                                        queryResource(courseid, 'tr_task_obj', nextid);
                                        $.fancybox.close();
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
</body>
</html>
