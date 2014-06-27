<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>

</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div class="zhuanti">
    <p>${coursename }<a class="ico13" href="javascript:showCourseList();"></a></p>
    <ul  style="display:none;" id="ul_courselist">
        <c:if test="${!empty courseList}">
            <c:forEach items="${courseList}" var="c">
                <c:if test="${c.courseid!=courseid}">
                    <li><a href="tpres?toTeacherIdx&courseid=${c.courseid }&termid=${c.termid}">${c.coursename }</a></li>
                </c:if>
            </c:forEach>
        </c:if>
    </ul>
</div>

<div class="subpage_nav">
    <ul>
        <li ><a href="task?toTaskList&courseid=${courseid }">学习任务</a></li>
        <li class="crumb"><a>专题资源</a></li>
        <li><a href="tptopic?m=index&courseid=${courseid }">互动空间</a></li>
        <!-- <li>
                <a href="commoncomment?m=toCourseCommentList&objectid=${courseid }">专题评价</a>
            </li>-->
        <li><a href="question?m=toQuestionList&courseid=${courseid}">试&nbsp;&nbsp;题</a></li>
        <li><a href="paper?toPaperList&courseid=${courseid}">试&nbsp;&nbsp;卷</a></li>
    </ul>
</div>

<div class="content2">
    <div class="jxxt_zhuanti_zy_layout">　　　
        <div class="jxxt_zhuanti_zy_layoutR" >　
            <div id="dv_obj"></div>
            <div id="dv_init">
                <h1 id="h1_resname"></h1><h1 style="color: #0000ff" id="h1_promt"></h1>
                <div class="right">
                    <div id="dv_res_detail">
                        <p><span class="font-black">发 布 者：</span></p>
                        <p><span class="font-black">发布时间：</span></p>
                        <p><span class="font-black">资源简介：</span></p>
                        <p></p>
                    </div>
                    <!--p class="number">
                        <span id=""><a  style="color:#34a4c6"  title="收藏">收藏</a></span><span style="color:#34a4c6 " id="sp_collect_count"></span>
                        <span id="sp_download"></span><span style="color:#34a4c6 " id="sp_download_count"></span>
                        <span ><a style="color:#34a4c6 ">浏览量:</a></span><span style="color:#34a4c6 " id="sp_viewcount"></span>
                    </p-->
                    <ul class="font-blue">
                        <li><strong id="sp_collect"><a>收藏</a></strong><br/><font id="sp_collect_count"></font></li>
                        <li><strong id="sp_download"><a href="#">下载</a></strong><br/><font id="sp_download_count"></font></li>
                        <li><strong>浏览量</strong><br/>
                            <font id="sp_viewcount"></font></li>
                    </ul>

                </div>
                <div class="left" id="div_show"></div>
                <div class="jxxt_zhuanti_zy_pl public_input">
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
                </div>
            </div>
        </div>


        <div class="jxxt_zhuanti_zy_layoutL">
            <p class="t_c p_b_10"><a  href="javascript:showUploadDiv(2)" class="an_big">添加资源</a><a href="javascript:loadCheckPage()" class="an_big">审核资源</a></p>
            <p class="f_right"><a href="tpres?m=toRecycleIdx&type=2&courseid=${courseid}" target="_blank" class="ico15" title="回收站"></a></p>
            <ul class="one">
                <li class="crumb" id="li_nav_1"><a  onclick="load_resource(1,1)">学习资源</a></li>
                <li id="li_nav_2"><a onclick="load_resource(2,1)">教学参考</a></li>
            </ul>
            <ul class="two" id="ul_resource" >

            </ul>
            <div class="nextpage" id="p_xnrj_page">

            </div>
        </div>
        <div class="clear"></div>

    </div>
</div>


<div class="public_windows" id="dv_upd_resource" style="display: none">
    <h3><a href="javascript:void(0);" onclick="closeModel('dv_upd_resource')" title="关闭"></a>修改资源</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w80"/>
        <col class="w350"/>
        <tr>
            <th>资源名称：</th>
            <td><input name="upd_res_name" id="upd_res_name"  class="w300" /></td>
        </tr>
        <tr>
            <th>资源类型：</th>
            <td class="font-black">
                <c:if test="${!empty resType}">
                    <c:forEach items="${resType}" var="d">
                        <input type="radio" name="upd_res_type"  value="${d.dictionaryvalue}" />${d.dictionaryname}&nbsp;&nbsp;
                    </c:forEach>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>资源简介：</th>
            <td><textarea id="upd_res_remark" class="h90 w300"></textarea></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a  onclick="doUpdResource(2)" class="an_public1">确&nbsp;认</a></td>
            <input type="hidden" id="hd_resid"/>
        </tr>
    </table>
</div>
<%@include file="/util/foot.jsp" %>

<!-- 页面隐藏域 -->
<input type="hidden" id="hd_resdetailid"/>
<input type="hidden" id="hd_comment_flag" value="1"/>
　

<script type="text/javascript" src="<%=basePath %>js/resource/new_resbase.js"></script>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/resource.js"></script>　
<script type="text/javascript">
    var courseid="${courseid}";
    var termid="${termid}";
    var courseSubject="${subjectid}";
    var clickcount=0;
    var usertype=2;
    var basepath='<%=basePath %>';
    var checkFlag="${param.ckflag}";
    var pList,p2,editor,child_editor;
    var tpresdetailid="${param.tpresdetailid}";
    $(function(){
        load_resource(1,"${pageno}",true);
        pList = new PageControl( {
            post_url : 'commoncomment?m=getCommentList',
            page_id : 'page1',
            page_control_name : "pList",
            post_form : document.pListForm,
            gender_address_id : 'pListaddress',
            http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
            http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
            return_type : 'json', //放回的值类型
            page_no : 1, //当前的页数
            page_size : 5, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "initItemList"
        });
        p2 = new PageControl( {
            post_url : 'tpres?m=getStuNotetList',
            page_id : 'page2',
            page_control_name : "p2",
            post_form : document.pListForm,
            gender_address_id : 'pListaddress',
            http_free_operate_handler : preeDoStuNotePageSub, //执行查询前操作的内容
            http_operate_handler : getStuNoteReturnMethod, //执行成功后返回方法
            return_type : 'json', //放回的值类型
            page_no : 1, //当前的页数
            page_size : 5, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "initItemList1"
        });
    });

    /**
     * 资源评论
     * @param rps
     */
    function getInvestReturnMethod(rps){
        var html='';
        if(rps.objList[0]!=null&&rps.objList[0].length>0){
            $.each(rps.objList[0],function(idx,itm){
                html+='<div class="pinglun" id="div_comment_'+itm.commentid+'">';
                html+='        <p class="font-black"><span class="font-blue">'+itm.cuserinfo.realname+'：</span>'+itm.commentcontext+'</p>';
                html+='        <p>'+itm.ctimeString+'&nbsp;&nbsp;<a href="javascript:loadReplyTextArea(\''+itm.commentid+'\',\''+itm.commentuserid+'\',\''+itm.commentobjectid+'\');" class="ico45" title="回复"></a></p>';
                html+='<p class="pic"><img src="'+itm.cuserinfo.headimage+'" width="38" height="38" /></p>';
                html+='<div id="div_reply_'+itm.commentid+'"></div>';
                html+='<div style="display:none;" id="p_rframe_'+itm.commentid+'"></div>';
                html+='</div>';
            });
        }else{
            html='<p>暂无数据!</p>';
        }
        $("div[id='div_comment_htm']").html(html);
        $("div[id='div_comment_htm']").hide();
        $("div[id='div_comment_htm']").show("fast");

        if(rps.objList[1]!=null&&rps.objList[1].length>0){
            $.each(rps.objList[1],function(idx,itm){
                var htm='';
                htm+='<div class="one" id="dv_'+itm.commentid+'">';
                htm+='        <p class="font-black"><span class="font-blue">'+itm.ruserinfo.realname;
                if(typeof(itm.torealname)!="undefined"&&itm.torealname!=null&&itm.torealname.Trim().length>0){
                    htm+='&nbsp;回复'+itm.torealname+'&nbsp;说：';
                }
                htm+='</span>'+itm.commentcontext+'</p>';
                htm+='        <p>'+itm.ctimeString+'&nbsp;&nbsp;<a href="javascript:loadReplyTextArea(\''+itm.commentid+'\',\''+itm.reportuserid+'\',\''+itm.commentobjectid+'\')" class="ico45" title="回复"></a></p>';
                htm+='<p class="pic"><img src="'+itm.cuserinfo.headimage+'" alt="" width="38" height="38" /></p>';
                htm+='</div>';
                htm+='<div  style="display:none;" id="p_rframe_'+itm.commentid+'"></div>';
                htm+='<div id="div_reply_'+itm.commentid+'"></div>';

                if($("#div_reply_"+itm.commentparentid+" div").length>0)
                    $("#div_reply_"+itm.commentparentid+" div:last").after(htm);
                else
                    $("#div_reply_"+itm.commentparentid).append(htm);
            });
        }

        if(rps.objList.length>0){
            pList.setPagetotal(rps.presult.pageTotal);
            pList.setRectotal(rps.presult.recTotal);
            pList.setPageSize(rps.presult.pageSize);
            pList.setPageNo(rps.presult.pageNo);
        }else
        {
            pList.setPagetotal(0);
            pList.setRectotal(0);
            pList.setPageNo(1);
        }
        pList.Refresh();
    }

    function preeDoPageSub(pObj){
        if(typeof(pObj)!='object'){
            alert("异常错误，请刷新页面重试!");
            return;
        }
        if(courseid.length<1){
            alert('异常错误，系统未获取到专题标识!');
            return;
        }
        var param={courseid:courseid};
        var resdetailid=$("#hd_resdetailid").val();
        var flag=$("#hd_comment_flag").val();
        if(resdetailid.Trim().length>0&&flag.Trim().length<1)
            param.resdetailid=resdetailid;
        pObj.setPostParams(param);
    }

    function showCourseList(){
        var ulobj=$("#ul_courselist");
        if(ulobj.css("display")=="none")
            ulobj.show();
        else{
            ulobj.hide();
        }
    }
</script>
</body>
</html>
