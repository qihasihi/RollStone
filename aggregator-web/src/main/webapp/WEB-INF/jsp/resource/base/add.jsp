<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<%
    String nextId=request.getAttribute("nextId").toString();
%>
<html>
<head>
    <!-- 上传控件
    <script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="util/uploadControl/js/jquery-migrate-1.1.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
    <script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" />-->

    <script src="<%=basePath %>js/videoPlayer/swfobject.js" type="text/javascript"></script>
    <script src="<%=basePath %>uploadify/jquery.uploadify.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>uploadify/uploadify.css"/>
    <script type="text/javascript" src="<%=basePath %>js/resource/resbase.js"></script>


    <script type="text/javascript">
        var url='';
        var nextid="${nextId}";
        $(function(){
            url="<%=fileSystemIpPort%>upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=${nextId}";
            // InitUpload(url,false);
            //   UploadInit_NOLoadClick(url,false);
            //	var uuidObj = new UUID();


            //uploadify
            $("#uploadfile").uploadify({
                'buttonClass' : 'an_public1',
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
                            return;
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
                        }
                        param.filename=filename;
                        param.uptype=fileuptype;//上传文件的大小
                        //提示：您确定提交‘"+param.resname+"’的资源吗?


                        if(!confirm("您确定提交‘"+param.resname+"’的资源吗?"))return;
                        if(fileuptype==1){
                            var rsval=$("#hd_filename").val();
                            if(rsval.Trim().length<1){
                                return;
                            }
                            $("#a_dosub").hide();
                            $("#sp_rs_log").html("正在上传……");
                            var lastName=rsval.substring(rsval.lastIndexOf("."));
                            url+="&lastname="+lastName;
                            var resfile="uploadfile";

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
                                        $("#sp_rs_log").html('');
                                        $("#loading").hide();
                                        if(window.opener!=null&&typeof(window.opener.pmyRes)!="undefined"&&window.opener.pmyRes!=null){
                                            window.opener.pmyRes.pageGo();
                                            window.close();
                                        }else
                                            location.href="resource?m=toMyResList";
                                    }
                                }
                            });
                        }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                 //   alert('文件' + file.name + ' 没有上传。原因: ' + errorMsg);
                }
            });


        });

    </script>
</head>
<body>
<input type="hidden" name="resid" id="resid" value="${nextId}"/>
<input type="hidden" name="hd_filename" id="hd_filename">

<div class="subpage_head"><span class="ico26"></span><strong>上传资源</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w100"/>
        <col class="w700"/>
        <tr>
            <th><span class="ico06"></span>资源名称：</th>
            <td><input name="res_name" id="res_name" maxlength="30"  placeholder="30字以内!" type="text" class="w430" /></td>
        </tr>
        <th><span class="ico06"></span>资源类型：</th>
        <td><ul class="public_list1 font-black">
            <c:if test="${!empty resTypeDicList}">
                <c:forEach items="${resTypeDicList}" var="restype">
                    <!--去掉微视频-->
                    <c:if test="${restype.dictionaryvalue!=6}">
                        <input type="radio" value="${restype.dictionaryvalue}"  name="rdo_restype" id="rdo_restype_${restype.dictionaryvalue}"/>
                        <label for="rdo_restype_${restype.dictionaryvalue}">${restype.dictionaryname}</label>
                    </c:if>
                </c:forEach>
            </c:if>
        </ul></td>
        </tr>
        <tr>
            <th><span class="ico06"></span>分享等级：</th>
            <td><ul class="public_list1 font-black">
                <c:if test="${!empty resLevelDicList}">
                    <c:forEach items="${resLevelDicList}" var="reslevel">
                        <input type="radio" value="${reslevel.dictionaryvalue}" name="rdo_reslevel" id="rdo_reslevel_${reslevel.dictionaryvalue}"/>
                        <label for="rdo_reslevel_${reslevel.dictionaryvalue}">${reslevel.dictionaryname}</label>
                    </c:forEach>
                </c:if>
            </ul>
            </td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;资源简介：</th>
            <td><textarea  name="res_remark" id="res_remark" class="h90 w600"></textarea></td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;附件上传：</th>
            <td><p class="font-black">
                <input type="radio" id="rdo_xwj" name="rdo_upfile_pannel" value="radio" checked onclick="dv_super_file.style.display='NONE';p_res_file.style.display='block';file_uptype.value=1;"/>
                <label for="rdo_xwj">普通附件</label>&nbsp;&nbsp;&nbsp;&nbsp;

                <%--<input type="radio" name="rdo_upfile_pannel"  id="rdo_dwj" onclick="p_res_file.style.display='NONE';dv_super_file.style.display='block';file_uptype.value=2;UploadInit_NOLoadClick(url,false);" value="radio" />--%>
                <%--<label for="rdo_dwj">超大附件</label>&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <a href="javascript:;" onmousemove="var dvstyle=dv_allow_filetype.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                                                            onmouseout="dv_allow_filetype.style.display='none';" class="font-darkblue">支持的文件类型</a></p>
                <input type="hidden" value="" name="res_complate_file" id="res_complate_file"/>
                <input type="hidden" name="file_uptype" id="file_uptype" value="1"/>
                <div class="zyxt_add"  id="p_res_file">
                    <input type="file" name="uploadfile" id="uploadfile" class="w410" />
                      <span id="sp_rs_log">
                      </span>
                    <p class="font-gray">
                    <pre>提示： 1. 附件限一个， <1G。
       2. 视频限MP4格式，建议使用<a class="font-darkblue" href="<%=basePath%>uploadfile/cqmygysdssjtwmydtsgx/20141119.zip">格式工厂</a>等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒。</pre></p>
                </div>
                <div class="zyxt_add" id="dv_super_file" style="display:none">
                    <!-- <p><a href="1" class="an_public3">上&nbsp;传</a></p>-->
                    <div  id="dv_load_core_spfile"></div>
                    <p><span class="font-gray">提示： 1. 附件限一个， <2G。 2. 视频限MP4格式，建议使用<a class="font-darkblue" href="<%=basePath%>uploadfile/cqmygysdssjtwmydtsgx/20141119.zip">格式工厂</a>等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒。</span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="uploadfile/tmp/etiantian-upload.exe" target="_blank" class="font-darkblue">下载插件</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="template/IEUpload Solution.docx" target="_blank" class="font-darkblue">使用说明</a></p>
                </div></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a id="a_dosub" onclick="doCreateRes()" href="javascript:;" class="an_small">提&nbsp;交</a><a href="javascript:;" onclick="window.close();" class="an_small">取&nbsp;消</a></td>
        </tr>
    </table>
</div>
<%@include file="/util/foot.jsp" %>
<!--上传文件类型-->
<div class="public_windows font-black"  id="dv_allow_filetype" name="dv_allow_filetype" style="display:none;position: absolute">
    <h3>上传文件类型</h3>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您可以上传以下文件格式：</strong></p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;文档<span class="ico_doc1"></span>doc/docx<span class="ico_pdf1"></span>pdf<span class="ico_xls1"></span>xls/xlsx<span class="ico_txt1"></span>txt<span class="ico_wps1"></span>wps<span class="ico_vsd1"></span>vsd<span class="ico_rtf1"></span>rtf<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ico_ppt1"></span>ppt/pptx、pot、pps</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;图片<span class="ico_jpg1"></span>bmp  jpg  png  gif  tiff  pcx  tga</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;音频<span class="ico_mp31"></span>mp3 midi wma realaudi asf wav</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;视频<span class="ico_mp41"></span>mp4</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;动画<span class="ico_swf1"></span>swf</p>
</div>
</body>
</html>
