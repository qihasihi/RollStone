<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
	<style>
</style>

		<script type="text/javascript">
		var courseid="${courseid}";
		var termid="${termid}";  
		var clickcount=0;
        var usertype=2;
		var pList,p2,editor,child_editor;
		$(function(){
            load_resource(1,1,true);
		});

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

                              /*      if (itm.resdegree == 3) {
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
                                        }
                                        dvhtm += '<a  class="ico04" title="删除" href="javascript:doDelResource(\'' + itm.resid + '\')"></a>';
                                        dvhtm += '<a  class="ico51" title="发任务" href="task?toAddTask&courseid=' + courseid + '&tasktype=1&taskvalueid=' + itm.resid + '"></a>';
                                        dvhtm += '</p>';
                                        dvhtm += '<p class="b"><span class="ico44" title="参考"></span></p>';
                                    } */


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

                                   /* dvhtm += '<p class="c" style="display: none;">';
                                    dvhtm += '<a  class="ico11" title="编辑" href="javascript:toUpdResource(\'' + itm.resid + '\')"></a>';
                                    dvhtm += '<a  class="ico04" title="删除" href="javascript:doDelResource(\'' + itm.resid + '\')"></a>';
                                    dvhtm += '<a  class="ico46" title="添加到学习资源" href="javascript:doUpdResType(' + itm.ref + ')"></a>';
                                    dvhtm += '</p>'; */

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

                        var aObj = $('a').filter(function () {
                            return this.id.indexOf('a_for_') != -1
                        });
                        if (isinit && aObj.length > 0)
                            $(aObj).get(0).click();


                        //分页
                        $("#p_xnrj_page").html('');
                        $("#sp_res_count").html('');
                        if (rps.presult.recTotal > 0 && rps.presult.pageTotal > 1) {
                            var prev = (pageno - 1) > 0 ? (pageno - 1) : 1;
                            var next = (pageno + 1) >= rps.presult.pageTotal ? rps.presult.pageTotal : (pageno + 1);
                            var page_split = '<span><a href="javascript:load_resource(' + type + ',1)"><b class="first"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + prev + ')"><b class="before"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + next + ')"><b class="after"></b></a></span><span><a href="javascript:load_resource(' + type + ',' + rps.presult.pageTotal + ')"><b class="last"></b></a></span>';
                            $("#p_xnrj_page").html(page_split);
                        }
                        $("#sp_res_count").html(rps.presult.recTotal);
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
            //genderHtml();
            //获取文件路径
            fileSystemIpPort=fileSystemIpPort.substring(0,fileSystemIpPort.indexOf("fileoperate"))+"fileoperate/";
            if(resid>0)
                fileSystemIpPort+="/clouduploadfile/";
            else
                fileSystemIpPort+="/uploadfile/";
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
                loadSWFPlayer(md5id, md5name, divid, false, lastname, 'mp3','images/mp3.jpg');
            } else if (type == 0) {
                htm += '<p>' + fname + '</p>';
            } else if (type == 5) {
                //showMp3Resource('div_show', 0, md5name, md5id,fileSystemIpPort);
                swfobjPlayer(md5id, md5name, divid, false, lastname, 'swf');
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
            $("#sp_download").html('<a class="ico59" title="下载" href="javascript:resourceDownLoadFile(\'' + resid + '\',\'' + fname + '\',1)"></a>');
            //资源详情
            load_resdetail(resid);

        }
        </script>
	</head>
    <div class="content2">
            <div class="jxxt_zhuanti_zy_layout">
                <div class="jxxt_zhuanti_zy_layoutR" >
                  <div id="dv_init">
                    <h1 id="h1_resname"></h1><h1 style="color: #0000ff" id="h1_promt"></h1>
                    <div class="right">
                        <div id="dv_res_detail">
                            <p><span class="font-black">发 布 者：</span></p>
                            <p><span class="font-black">发布时间：</span></p>
                            <p><span class="font-black">资源简介：</span></p>
                            <p></p>
                        </div>
                        <p class="number">
                            <!--<a href="1" class="ico58b" title="取消收藏"></a><a href="1" class="ico58a" title="收藏"></a>-->
                            <span id="sp_download"></span>
                            <!--<span class="ico46" title="浏览"></span><span id="sp_viewcount"></span>-->
                        </p>
                    </div>
                    <div class="left" id="div_show"></div>
                    <!--<div class="jxxt_zhuanti_zy_pl public_input">
                        <ul id="ul_comment">
                            <li id="li_studynote"><a href="javascript:loadStudyNotes(2);">学习心得</a></li>
                            <li  class="crumb"><a href="javascript:loadAllComment()">所有评论</a></li>
                        </ul>
                        <div class="fabiao" id="div_xheditor">

                        </div>
                        <div id="div_comment_htm">

                        </div>

                        <form id="pListForm" name="pListForm">
                            <p class="Mt20" id="pListaddress" name="pListaddress" align="center"></p>
                        </form>
                    </div>-->
                </div>
            </div>


            <div class="jxxt_zhuanti_zy_layoutL">
                    <p class="p_b_10 font-black">&nbsp;&nbsp;<strong>专题资源数：</strong>
                    <span>
                        <c:if test="${!empty resList}">
                            ${fn:length(resList)}
                        </c:if>
                    </span></p>
                    <ul class="one">
                        <li class="crumb" id="li_nav_1"><a  onclick="load_resource(1,1)">学习资源</a></li>
                        <li id="li_nav_2"><a onclick="load_resource(2,1)">教学参考</a></li>
                    </ul>
                    <ul class="two" id="ul_resource" >

                    </ul>
                    <div class="nextpage" id="p_xnrj_page">

                    </div>
            </div>
                <div class="clear p_t_20"></div>

            </div>
        </div>
</html>
