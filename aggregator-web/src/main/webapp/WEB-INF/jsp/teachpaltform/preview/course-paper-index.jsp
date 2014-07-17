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
            var pList,pBankList;
            var total;
            $(function(){
                pList = new PageControl( {
                    post_url : 'paper?m=ajaxPaperList',
                    page_id : 'page1',
                    page_control_name : "pList",
                    post_form : document.pListForm,
                    gender_address_id : 'pListaddress',
                    http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                    http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                    return_type : 'json', //放回的值类型
                    page_no : 1, //当前的页数
                    page_size : 9999, //当前页面显示的数量
                    rectotal : 0, //一共多少
                    pagetotal : 1,
                    operate_id : "initItemList"
                });
                pageGo('pList');


                $("li[name='li_nav']").each(function(idx,itm){
                    $(itm).bind("click",function(){
                        $(itm).siblings().removeClass("crumb").end().addClass("crumb");
                    })
                })
            });


            function showOrhide(aobj, taskid) {
                var status=$("#div_task_"+taskid);
                if(status.css("display")=="none"){
                    load_task_detial(taskid);
                    $(status).show();
                    $(aobj).removeClass().addClass("ico49a");
                }else{
                    $(status).hide();
                    $(aobj).removeClass().addClass("ico49b");
                }
            }



            function getInvestReturnMethod(rps){
                var html='',shtml='';
                if(rps.objList!=null&&rps.objList.length>0){
                    $.each(rps.objList,function(idx,itm){
                        if(itm.paperid>0){
                            html+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                            html+='<p class="one">'+itm.papername+'</p>';
                            html+='<p class="two">';
                            if(itm.objectivenum>0&&itm.subjectivenum>0)
                                html+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                            else if(itm.objectivenum>0)
                                html+='<span class="bg2" style="width:100%">'+itm.objectivenum+'</span>';
                            else if(itm.subjectivenum>0)
                                html+='<span class="bg1" style="width:100%">'+itm.subjectivenum+'</span>';
                            html+='</p></a>';
                            html+='<p class="pic">';
                            html+='</p>';
                            html+='</li>';
                        }else{
                            shtml+='<li>';
                            if(itm.papertype==4)
                                shtml+='<a>';
                            else
                                shtml+='<a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                            shtml+='<p class="one">'+itm.papername+'</p>';
                            shtml+='<p class="two">';
                            if(itm.objectivenum>0&&itm.subjectivenum>0)
                                shtml+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                            else if(itm.objectivenum>0)
                                shtml+='<span class="bg1" style="width:100%">'+itm.objectivenum+'</span>';
                            else if(itm.subjectivenum>0)
                                shtml+='<span class="bg2" style="width:100%">'+itm.subjectivenum+'</span>';
                            else if(typeof itm.quesnum!='undefined')
                                shtml+='<span class="bg1" style="width:100%">'+itm.quesnum+'</span>';
                            shtml+='</p></a>';
                            shtml+='<p class="pic">';
                            shtml+='</p>';
                            shtml+='</li>';
                        }
                    });
                }
                $("#ul_standard").html(html);
                $("#ul_native").html(shtml);


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
                pObj.setPostParams(param);
            }


            function showCourseList(){
                var ulobj=$("#ul_courselist");
                if(ulobj.css("display")=="none")
                    ulobj.show();
                else
                    ulobj.hide();
            }

            function doDelPaper(ref){
                if(typeof  ref=='undefined')
                    return;
                if(!confirm("确认删除?"))return;
                $.ajax({
                    url:"paper?m=doOperatePaper",
                    type:"post",
                    data:{ref:ref,flag:2},
                    dataType:'json',
                    cache: false,
                    error:function(){
                        alert('系统未响应，请稍候重试!');
                    },success:function(rmsg){
                        if(rmsg.type=="error"){
                            alert(rmsg.msg);
                        }else{
                            alert(rmsg.msg);
                            pageGo('pList');
                        }
                    }
                });
            }
        </script>
	</head>

    <div class="content1 font-black">
        <p class="jxxt_zhuanti_shijuan_text">图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题<!--<a class="ico15" href="tpres?m=toRecycleIdx&type=5&courseid=${courseid}" title="回收站"></a>--></p>
        <p><strong>标准试卷</strong></p>
        <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard">
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:100%">8</span></p></a>
                <p class="pic"><a class="ico_wsp1" title="微视频"></a><a href="1"><b><span class="ico51" title="发任务"></span></b></a><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg2" style="width:100%">8</span></p></a>
                <p class="pic"><a class="ico_wsp1" title="微视频"></a><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:50%">8</span><span class="bg2" style="width:50%">2</span></p></a>
                <p class="pic"><a class="ico_wsp1" title="微视频"></a><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:50%">6</span><span class="bg2" style="width:50%">4</span></p></a>
                <p class="pic"><a class="ico_wsp1" title="微视频"></a><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
        </ul>
        <p class="p_t_10"><strong>自建试卷</strong></p>
        <ul class="jxxt_zhuanti_shijuan_list" id="ul_native">
            <li><a href="1" target="_blank" class="kapian"><span class="ico82"></span></a></li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:100%">8</span></p></a>
                <p class="pic"><a href="1"><b><span class="ico51" title="发任务"></span></b></a><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg2" style="width:100%">8</span></p></a>
                <p class="pic"><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:50%">8</span><span class="bg2" style="width:50%">2</span></p></a>
                <p class="pic"><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:50%">6</span><span class="bg2" style="width:50%">4</span></p></a>
                <p class="pic"><b><span class="ico52" title="已发任务"></span></b><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
            <li><a href="1" target="_blank">
                <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
                <p class="two"><span class="bg1" style="width:100%">8</span></p></a>
                <p class="pic"><a href="1"><b><span class="ico51" title="发任务"></span></b></a><a href="1"><b><span class="ico04" title="删除"></span></b></a></p>
            </li>
        </ul>
    </div>
    <form id="pListForm" name="pListForm" style="display: none;">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</html>
