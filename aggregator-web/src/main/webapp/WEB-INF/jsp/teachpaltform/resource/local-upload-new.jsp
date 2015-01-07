<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String proc_name=request.getHeader("x-etturl");
    if(proc_name==null){
        proc_name=request.getContextPath().replaceAll("/","");
    }else{
        ///group1/1.jsp
        proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1).replaceAll("/","");
    }
    String ipStr=request.getServerName()+":"+request.getServerPort();
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + ipStr
            +"/"+proc_name + "/";
    session.setAttribute("IP_PROC_NAME",basePath);
%>
<!-- 上传控件
<script type="text/javascript" src="<%=basePath %>js/common/uploadControl.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery.json-2.4.js"></script>


<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath %>util/uploadControl/js/knockout-2.2.1.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" /> -->
<script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>
<script src="<%=basePath %>js/videoPlayer/swfobject.js" type="text/javascript"></script>
<script src="<%=basePath %>uploadify/jquery.uploadify.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>uploadify/uploadify.css"/>



<style type="text/css">
    .yixuan {
        /*border-bottom: 1px dashed #CEDCE3;*/
        color: #000000;
        margin: 0 auto 10px;
        padding: 5px 10px 10px;
        width: 910px;
    }

 /*    .uploadify-button {
          background-color: transparent;
          border: none;
          padding: 0;
      }
    .uploadify:hover .uploadify-button {
        background-color: transparent;
    } */


</style>

<script type="text/javascript">
    //资源库
    var operate_type="${param.operate_type}";
    var usertype=${param.usertype};
    var p1,pCourse,pCourseRes;
    var courseid="${param.courseid}";
    var nextid=${nextid};
    var md5id="${nextMd5Id}";

    var url="${fileSystemIpPort}upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id="+nextid;
    var processUrl="${fileSystemIpPort}";
    //var url="http://192.168.8.189/fileoperate/upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id="+nextid;
    $(function(){
        $("#uploadfile").uploadify({
            'buttonClass' : 'an_public1',
            //'buttonImage' : 'uploadify/browseBtn.png',
            'fileObjName' : 'uploadfile',
            swf           : 'uploadify/uploadify.swf',
            uploader      : 'uploadify/uploadFile.jsp',
           // uploader      : 'tpres?doUploadResource',
            buttonText    : '选择文件',
            width         : '90',
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
                    case -100:     alert("上传的文件数量已经超出系统限制的"+$('#uploadfile').uploadify('settings','queueSizeLimit')+"个文件！"); break;
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
                            closeModel('dv_loading');
                            alert(rps.msg);
                            //resetBtnAttr("a_submit","an_small","an_gray_small","doUploadResource("+usertype+")",1);
                            $("#dv_obj").html('');
                            usertype == 2 ? load_resource(crestype, 1,true) : loadStuResourcePage(courseid);
                        }
                    }
                });

            },
            'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                //alert('文件： ' + file.name + ' 未上传，原因： ' + errorString);
            }
        });




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
            page_size:10,				//当前页面显示的数量
            rectotal:0,				//一共多少
            page_order_by:" r.diff_type DESC,r.is_mic_copiece,IFNULL(cr.operate_time,cr.c_time) DESC,cr.res_id ",
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
            page_size:10,				//当前页面显示的数量
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

        if(usertype==1)
            $("#tr_res_type").hide();

    });

    var allowViewDoc=true;
    function timer(){
        setTimeout(function(){
            allowViewDoc=true;
        },1000);
    }
    /**
     *显示预览
     * @param loc
     * @param path
     * @param imgd
     *
     */
    function showDocView(resid,loc,path){
        if(typeof(resid)=="undefined"||resid==null)
            return;
        if(!allowViewDoc){
            alert("别着急!休息一会再点!");
            return;
        }
        allowViewDoc=false;
        timer();
        $.ajax({
            url:"resource?m=ajx_mkDocFile",
            data:{resid:resid},
            dataType:'json',
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                    return;
                }
                var p=loc+"/"+path+"/001.swf";
                //验证是否存在

                loadSwfReview(p,'dv_show_doc_view',980,800);
                showModel('dv_doc_prview');
            },
            type:"POST"
        });
    }



</script>

<input type="hidden" id="hd_filename"/>
<div class="public_windows public_input" id="dv_tishi" style="display: none;">
    <h3><a href="1" target="_blank" title="关闭"></a>添加资源</h3>
    <p class="font-black p_t_10 t_c"><strong id="s_tishi"></strong>&nbsp;&nbsp;</p>
    <p class="t_c p_t_20"><a href="javascript:doUploadResource(${param.usertype})" class="an_public1">确&nbsp;认</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:closeModel('dv_tishi');isTishi=false;"  class="an_public1">取&nbsp;消</a></p>
</div>

<div class="public_windows public_input" id="dv_loading" style="display: none;">
    <h3><!--<a href="1" target="_blank" title="关闭"></a>-->添加资源</h3>
    <div  class="h210 t_c">
        <p class="font-black p_t_20"><strong>正在为您努力上传<strong>资源</strong></strong></p>
        <p class="ico91"></p>
    </div>
</div>



<div id="dv_upload"  >
<div class="subpage_lm">
    <ul>
        <li id="li_dv_upload_local" class="crumb"><a onclick="changeTab('dv_upload_local')">本地上传</a></li>
        <c:if test="${param.usertype eq 2}">
            <li id="li_dv_upload_course"><a onclick="changeTab('dv_upload_course')">通过专题查找</a></li>
            <li id="li_dv_upload_resource"><a onclick="changeTab('dv_upload_resource')">通过资源库查找</a></li>
        </c:if>

    </ul>
</div>
<div id="dv_upload_local">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w100"/>
        <col class="w600"/>

        <tr id="tr_res_type">
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
                    <!--去掉微视频-->
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
                普通附件
                <!--<input type="radio" name="rdo_uplaod"  value="2"  onclick="p_res_file.style.display='NONE';dv_super_file.style.display='block';UploadInit_CourseResource(url,false);" />
                超大附件-->&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;"
                                               onmousemove="var dvstyle=dv_allow_filetype.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                               onmouseout="dv_allow_filetype.style.display='none';" class="font-darkblue">支持的文件类型</a>
                <!-- <a class="font-darkblue" href="http://202.99.47.77/fileoperate/uploadfile/tmp/upload-chajian2013-07-29.exe">下载插件</a>
                 <a class="font-darkblue" href="template/IEUpload Solution.docx">使用说明</a>-->
            </p>
                <div class="jxxt_zhuanti_zy_add" id="p_res_file">
                    <input type="file" name="uploadfile" id="uploadfile" class="w410" /><!--<a href="1" class="an_public3">上&nbsp;传</a>--><span id="upload_process"></span>
                    <p>1. 附件限一个，<1G。 <br>2. 视频限MP4格式，建议使用<a class="font-darkblue" href="<%=basePath%>uploadfile/cqmygysdssjtwmydtsgx/20141119.zip">格式工厂</a>等软件转换，视频编码为：AVC（H264），比特率为：300-500KB/秒 </p>
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
                                        </div> -->
                    </div>
                    <p><span class="font-gray">提示： 附件仅限一个，>100M  <3G。</span>&nbsp;&nbsp;&nbsp;&nbsp;<a class="font-darkblue" href="http://202.99.47.77/fileoperate/uploadfile/tmp/upload-chajian2013-07-29.exe">下载插件</a>
                        <a class="font-darkblue" href="template/IEUpload Solution.docx">使用说明</a></p>
                </div>
            </td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a id="a_submit" onclick="doUploadResource(${param.usertype})"   class="an_small">提&nbsp;交</a>
                <!--<a href="javascript:void(0);" onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>-->
            </td>
        </tr>
    </table>
</div>

<div id="dv_upload_course" style="display: none">
    <div id="p_upload_course2" style="display: none;">
        <div class="jxxt_zhuanti_zy_add_info">
            <p class="back">
                <a class="one"  href="javascript:;" onclick="p_upload_course2.style.display='NONE';p_upload_course1.style.display='block';dv_detail_course.style.display='block';">返回</a>
            </p>
            <p class="font-black" id="p_coursename">

            </p>
            <p>类型：<span id="sp_type"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作者：<span id="sp_author"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年级：<span id="sp_grade"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="sp_star"></span></p>
            <!--<p class="font-darkblue">搜到的资源&nbsp;<span class="font-red">40</span></p>-->
        </div>
        <input id="hd_courseid" type="hidden">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
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
        <p class="t_c"><a href="javascript:void(0);" onclick="sub_res('dv_upload_course',1)" class="an_small_long">添加到学习资源</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="sub_res('dv_upload_course',2)" class="an_small_long">添加到教学参考</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <!--<a onclick="p_upload_course2.style.display='NONE';p_upload_course1.style.display='block';dv_detail_course.style.display='block';" class="an_small">取&nbsp;消</a>-->
        </p><!--hideUploadDiv()-->
    </div>

    <p class="public_input t_l" id="p_upload_course1">
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
                    <option  value="${g.dictionaryvalue}">${g.dictionaryname}</option>
                </c:forEach>
            </c:if>
        </select>

        <input id="txt_course" name="txt_course" type="text" class="w220" />
        <a onclick="pageGo('pCourseRes')" class="an_search" title="查询"></a>
    </p>

    <div id="dv_detail_course">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w350"/>
            <col class="w80"/>
            <col class="w90"/>
            <col class="w80"/>
            <col class="w150"/>
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
        <div id="PropertyArea" >

            <div class="jxpt_ziyuan_zrss">
                <p><strong>已选择：</strong></p>
            </div>

            <div class="jxpt_ziyuan_zrss_option" >
            </div>
        </div>
    </div>

    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black" style="float: left;width:100%">
        <col class="w50"/>
        <col class="w700"/>
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
    <p class="t_c p_tb_10"><a onclick="sub_res('dv_upload_resource',1)"  class="an_small_long">添加到学习资源</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="sub_res('dv_upload_resource',2)"  class="an_small_long">添加到教学参考</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <!--<a onclick="hideUploadDiv()" class="an_small">取&nbsp;消</a>-->
    </p>

</div>
</div>


<!--上传须知与文件类型-->
<div class="public_windows font-black" id="dv_allow_filetype" name="dv_allow_filetype" style="z-index: 5;display:none;position: absolute;background-color:white;padding: 10px 40px 40px 30px;">
    <h3><!--<a href="1" target="_blank" title="关闭"></a>-->上传文件类型</h3>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您可以上传以下文件格式：</strong></p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;文档<span class="ico_doc1"></span>doc/docx<span class="ico_pdf1"></span>pdf<span class="ico_xls1"></span>xls/xlsx<span class="ico_txt1"></span>txt<span class="ico_wps1"></span>wps<span class="ico_vsd1"></span>vsd<span class="ico_rtf1"></span>rtf<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ico_ppt1"></span>ppt/pptx、pot、pps</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;图片<span class="ico_jpg1"></span>bmp  jpg  png  gif  tiff  pcx  tga</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;音频<span class="ico_mp31"></span>mp3 midi wma realaudi asf wav</p>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;&nbsp;视频<span class="ico_mp41"></span>mp4<!--wmv  rmvb  mpeg  mp4  avi  flv--></p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;动画<span class="ico_swf1"></span>swf</p>
</div>


<!--doc预览-->
<div class="public_windows" style="width:575px;height:490px;display:none"   id="dv_doc_prview">
    <h3 style="float:right;"><a href="javascript:;" onclick="document.getElementById('fade').style.display='none';dv_doc_prview.style.display='none';"  title="关闭"></a></h3>
    <div class="zyxt_float_fxzy public_input" id="dv_show_doc_view">

    </div>
</div>
<!--遮照层-->
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
<div id="loading" style='display:none;position: absolute;z-index:1005;'><img src="images/loading.gif"/></div>



<script type="text/javascript">
    <c:if test="${!empty materialInfo}">
    $("#sel_grade").val("${materialInfo.gradeid}");
    load_material("${materialInfo.teachingmaterialid}");
    //$("#sel_material").val();
    </c:if>
    <c:if test="${!empty relateCourse}">
         $("#sel_courselevel").val(2);
    </c:if>

</script>