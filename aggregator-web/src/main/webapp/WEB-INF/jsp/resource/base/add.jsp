<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<%
    String nextId=request.getAttribute("nextId").toString();
%>
<html>
<head>

    <script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>
    <!-- 上传控件 -->
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-1.9.1.js"></script>
    <!-- <script type="text/javascript" src="util/uploadControl/js/jquery-migrate-1.1.1.min.js"></script> -->
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
    <script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>js/resource/resbase.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" />

    <script type="text/javascript">
        var url='';
        $(function(){
            url="<%=fileSystemIpPort%>upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=${nextId}";
            // InitUpload(url,false);
            //   UploadInit_NOLoadClick(url,false);
            //	var uuidObj = new UUID();
        });

    </script>
</head>
<body>
<input type="hidden" name="resid" id="resid" value="${nextId}"/>
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
                    <pre>提示： 1. 附件限一个， <2G。
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
