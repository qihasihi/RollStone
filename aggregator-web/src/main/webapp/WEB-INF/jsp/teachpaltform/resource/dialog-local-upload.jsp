<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.SpringBeanUtil" %>
<%@ page import="com.school.manager.teachpaltform.TpCourseResourceManager" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    //List<TpResourceBaseInfo> resourceList=(ArrayList<TpResourceBaseInfo>)request.getAttribute("resList");
    //List<TpResourceBaseInfo> resourceStuList=(ArrayList<TpResourceBaseInfo>)request.getAttribute("resStuList");
    //int idx=0,stuidx=0;
    String basepath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort();

%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>

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


    //资源库
    var courseid="${param.courseid}";
    var nextid="${nextid}"
    var operate_type="${param.operate_type}";
    var p1,pCourse;
    var courseSubject="${subjectid}";
    var url="${fileSystemIpPort}upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id="+nextid;
    $(function(){
        lb=new LeibieControl({controlid:"lb"},true);
        p1=new PageControl({
            post_url:'resource?m=ajaxListByValues',
            page_id:'page1',
            page_control_name:"p1",		//分页变量空间的对象名称
            post_form:document.page1form,		//form
            http_free_operate_handler:beforeAjaxCourseList,
            gender_address_id:'page1address',		//显示的区域
            http_operate_handler:afterAjaxCourseList,	//执行成功后返回方法
            return_type:'json',								//放回的值类型
            page_no:1,					//当前的页数
            page_size:5,				//当前页面显示的数量
            rectotal:0,				//一共多少
            pagetotal:1,
            operate_id:"resData"
        });

        pCourse=new PageControl({
            post_url:'tpres?m=ajaxCourseResList',
            page_id:'pCourse1',
            page_control_name:"pCourse",		//分页变量空间的对象名称
            post_form:document.page2form,		//form
            http_free_operate_handler:beforeAjaxCourseResList,
            gender_address_id:'page2address',		//显示的区域
            http_operate_handler:afterAjaxCourseResList,	//执行成功后返回方法
            return_type:'json',								//放回的值类型
            page_no:1,					//当前的页数
            page_size:5,				//当前页面显示的数量
            rectotal:0,				//一共多少
            pagetotal:1,
            operate_id:"courseResData"
        });

        pCourseRes=new PageControl({
            post_url:'tpres?m=ajaxCourseList',
            page_id:'pCourseRes',
            page_control_name:"pCourseRes",		//分页变量空间的对象名称
            post_form:document.pageCourseResform,		//form
            http_free_operate_handler:beforeQryCourseList,
            gender_address_id:'pageCourseResaddress',		//显示的区域
            http_operate_handler:afterQryCourseList,	//执行成功后返回方法
            return_type:'json',								//放回的值类型
            page_no:1,					//当前的页数
            page_size:10,				//当前页面显示的数量
            rectotal:0,				//一共多少
            pagetotal:1,
            operate_id:"tbl_course"
        });
    });


</script>
</head>
<body>




<div id="dv_upload"  >
<div class="subpage_head"><span class="ico55"></span><strong>添加资源</strong></div>
<div class="subpage_nav">
    <ul>
        <li id="li_dv_upload_local" class="crumb"><a onclick="changeTab('dv_upload_local')">本地上传</a></li>
        <li id="li_dv_upload_course"><a onclick="changeTab('dv_upload_course')">通过专题查找</a></li>
        <li id="li_dv_upload_resource"><a onclick="changeTab('dv_upload_resource')">通过资源库查找</a></li>
    </ul>
</div>
<div class="content1">
<div id="dv_upload_local">
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
                <input type="radio" name="res_type"  value="${d.dictionaryvalue}" />${d.dictionaryname}&nbsp;&nbsp;&nbsp;&nbsp;
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
                普通附件&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="rdo_uplaod"  value="2"  onclick="p_res_file.style.display='NONE';dv_super_file.style.display='block';UploadInit_CourseResource(url,false);" />
                超大附件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;"
                                               onmousemove="var dvstyle=dv_allow_filetype.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                               onmouseout="dv_allow_filetype.style.display='none';" class="font-darkblue">支持的文件类型</a></p>
                <div class="jxxt_zhuanti_zy_add" id="p_res_file">
                    <input type="file" name="uploadfile" id="uploadfile" class="w410" /><!--<a href="1" class="an_public3">上&nbsp;传</a>-->
                    <p class="font-gray">提示：附件仅限一个，100M以内。视频资源<20M可实时转换播放，>20M需等待，第二天可播放。</p>
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
                        <p><span class="font-gray">提示： 附件仅限一个，>100M  <3G。</span>&nbsp;&nbsp;&nbsp;&nbsp;<a class="font-darkblue" href="http://202.99.47.77/fileoperate/uploadfile/tmp/upload-chajian2013-07-29.exe">下载插件</a>
                            <a class="font-darkblue" href="template/IEUpload Solution.docx">使用说明</a></p>
                    </div>
            </td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a id="a_submit" onclick="doUploadResource(${param.usertype})"   class="an_small">提&nbsp;交</a><!--<a href="javascript:void(0);" onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>--></td>
        </tr>
    </table>
</div>

<div id="dv_upload_course" style="display: none">
    <div id="p_upload_course2" style="display: none;">
        <div class="jxxt_zhuanti_shiti_add_info">
            <p class="font-black" id="p_coursename">

            </p>
            <p>类型：<span id="sp_type"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作者：<span id="sp_author"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年级：<span id="sp_grade"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="sp_star"></span></p>
            <!--<p class="font-darkblue">搜到的资源&nbsp;<span class="font-red">40</span></p>-->
        </div>
        <input id="hd_courseid" type="hidden">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black" style="width:920px;float:left">
            <col class="w50"/>
            <col class="w700"/>
            <tbody id="courseResData">

            </tbody>
            <!-- <tr>
                 <td><input type="checkbox" name="checkbox2" id="checkbox2"><span class="ico_doc1"></span></td>
                 <td>
                     <p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                     <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                     <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>1463</b><span class="ico59" title="下载"></span><b>1523</b><span class="ico41" title="赞"></span><b>15463</b></p>
                 </td>
             </tr>

             <tr>
                 <td><input type="checkbox" name="checkbox2" id="checkbox2"><span class="ico_xls1"></span></td>
                 <td>
                     <p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                     <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                     <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>1463</b><span class="ico59" title="下载"></span><b>1523</b><span class="ico41" title="赞"></span><b>15463</b></p></td>
             </tr>
             <tr>
                 <td><input type="checkbox" name="checkbox" id="checkbox"><span class="ico_tex1"></span></td>
                 <td><span class="ico_txt1"></span><span class="ico_ppt1"></span><span class="ico_rtf1"></span><span class="ico_vsd1"></span><span class="ico_mp31"></span><span class="ico_swf1"></span><span class="ico_mp41"></span><span class="ico_jpg1"></span><span class="ico_wps1"></span></td>
             </tr> -->
        </table>

        <form action="/role/ajaxlist" id="page2form" name="page2form" method="post" >
            <p align="center" id="page2address"></p>
        </form>
        <p class="t_c"><a href="javascript:void(0);" onclick="sub_res('dv_upload_course',1)" class="an_small_long">添加到学习资源</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="sub_res('dv_upload_course',2)" class="an_small_long">添加到教学参考</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="p_upload_course2.style.display='NONE';p_upload_course1.style.display='block';dv_detail_course.style.display='block';" class="an_small">取&nbsp;消</a></p><!--hideUploadDiv()-->
    </div>
    <p class="public_input p_t_10" id="p_upload_course1">
        <select id="sel_grade" onchange="load_material()">
            <c:if test="${!empty gradeList}">
                <c:forEach items="${gradeList}" var="g">
                    <option value="${g.gradeid}">${g.gradevalue}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="sel_material">

        </select>

        <select id="sel_courselevel">
            <c:if test="${!empty courseLevel}">
                <option value="">==请选择专题类型==</option>
                <c:forEach items="${courseLevel}" var="g">
                    <c:if test="${g.dictionaryvalue eq 2}">
                        <option selected value="${g.dictionaryvalue}">${g.dictionaryname}</option>
                    </c:if>
                    <c:if test="${g.dictionaryvalue ne 2}">
                        <option  value="${g.dictionaryvalue}">${g.dictionaryname}</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>

        <input id="txt_course" name="txt_course" type="text" class="w240" />
        <a onclick="pageGo('pCourseRes')" class="an_search" title="查询"></a>
    </p>

    <div id="dv_detail_course">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                <col class="w500">
                <col class="w90">
                <col class="w100">
                <col class="w100">
                <col class="w150">
            <tr>
                <th>专题名称</th>
                <th>数量</th>
                <th>作者</th>
                <th>年级</th>
                <th>总评分</th>
            </tr>
            <tbody id="tbl_course">
            <!-- <tr>
                 <td><p><a href="1" target="_blank">是否可以提供更多的视频资源？是否可以提供更多的视频资源？是否可以提供更多的</a></p></td>
                 <td><span class="font-red">300</span></td>
                 <td>王美联</td>
                 <td>高一</td>
                 <td><span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star2"></span><span class="ico_star3"></span>&nbsp;3.5</td>
             </tr> -->
            </tbody>
        </table>

        <form action="/role/ajaxlist" id="pageCourseResform" name="pageCourseResform" method="post">
            <p align="center" id="pageCourseResaddress"></p>
        </form>
    </div>

</div>

<div id="dv_upload_resource" style="display: none;">
    <div class="jxxt_zhuanti_zy_add_info">
        <p class="public_input t_l">
            <input id="searchValue" name="searchValue" type="text" class="w350" />
            <a href="javascript:void(0);"
               onclick="var resname=$('#searchValue').val(); if(resname.Trim().length<1){alert('请输入关键字后点击搜索按钮!');return;}else{pageGo('p1')}" class="an_search" title="查询"></a></p>
        <div id="PropertyArea">
            <div class="jxpt_ziyuan_zrss">
                <p><strong>已选择：</strong></p>
            </div>

            <div class="jxpt_ziyuan_zrss_option">
            </div>
        </div>
    </div>

    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black" style="float: left">
        <col class="w50"/>
        <col class="w860"/>
        <tbody id="resData" >
        <!-- <tr>
             <td><input type="checkbox" name="checkbox2" id="checkbox2">
                 <span class="ico_doc1"></span></td>
             <td><p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                 <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                 <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>1463</b><span class="ico59" title="下载"></span><b>1523</b><span class="ico41" title="赞"></span><b>15463</b></p></td>
         </tr>
         <tr>
             <td><input type="checkbox" name="checkbox2" id="checkbox2">
                 <span class="ico_pdf1"></span></td>
             <td><p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                 <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                 <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>1463</b><span class="ico59" title="下载"></span><b>1523</b><span class="ico41" title="赞"></span><b>15463</b></p></td>
         </tr>
         <tr>
             <td><input type="checkbox" name="checkbox2" id="checkbox2">
                 <span class="ico_xls1"></span></td>
             <td><p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                 <p>款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                 <p class="jxxt_zhuanti_zy_add_text">北京四中_李静&nbsp;&nbsp;&nbsp;课件&nbsp;&nbsp;<span class="ico46" title="浏览"></span><b>1463</b><span class="ico59" title="下载"></span><b>1523</b><span class="ico41" title="赞"></span><b>15463</b></p></td>
         </tr>
         <tr>
             <td><input type="checkbox" name="checkbox" id="checkbox">
                 <span class="ico_tex1"></span></td>
             <td><span class="ico_txt1"></span><span class="ico_ppt1"></span><span class="ico_rtf1"></span><span class="ico_vsd1"></span><span class="ico_mp31"></span><span class="ico_swf1"></span><span class="ico_mp41"></span><span class="ico_jpg1"></span><span class="ico_wps1"></span></td>
         </tr>-->
        </tbody>
    </table>

    <form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
        <p align="center" id="page1address"></p>
    </form>
    <p class="t_c p_tb_10"><a onclick="sub_res('dv_upload_resource',1)"  class="an_small_long">添加到学习资源</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="sub_res('dv_upload_resource',2)"  class="an_small_long">添加到教学参考</a><!--<a onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>--></p>

</div>
</div>
</div>


<!--上传须知与文件类型-->
<div class="public_windows font-black" id="dv_allow_filetype" name="dv_allow_filetype" style="display:none;position: absolute;background-color:white;padding: 10px 40px 40px 30px;">
    <h3><!--<a href="1" target="_blank" title="关闭"></a>-->上传文件类型</h3>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您可以上传以下文件格式：</strong></p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;文档<span class="ico_doc1"></span>doc/docx<span class="ico_pdf1"></span>pdf<span class="ico_xls1"></span>xls/xlsx<span class="ico_txt1"></span>txt<span class="ico_wps1"></span>wps<span class="ico_vsd1"></span>vsd<span class="ico_rtf1"></span>rtf<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ico_ppt1"></span>ppt/pptx、pot、pps</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;图片<span class="ico_jpg1"></span>bmp  jpg  png  gif  tiff  pcx  tga</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;音频<span class="ico_mp31"></span>mp3 midi wma realaudi asf wav</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;视频<span class="ico_mp41"></span>wmv  rmvb  mpeg  mp4  avi  flv</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;动画<span class="ico_swf1"></span>swf</p>
</div>
<%@include file="/util/foot.jsp" %>
<script type="text/javascript">
    <c:if test="${!empty materialInfo}">
        $("#sel_grade").val("${materialInfo.gradeid}");
        load_material("${materialInfo.teachingmaterialid}");
    </c:if>
</script>

</body>
