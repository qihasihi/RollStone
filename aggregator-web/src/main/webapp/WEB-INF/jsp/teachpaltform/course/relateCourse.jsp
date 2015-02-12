<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>


        <script type="text/javascript">
            var courseid="${courseid}";
            var pRelateCourse;
            var gradeid= "${param.gradeid}";
            $(function(){
                pRelateCourse = new PageControl( {
                    post_url : 'teachercourse?getRelateCourseList',
                    page_id : 'pageRelate',
                    page_control_name : "pRelateCourse",
                    post_form : document.pRelateCourseForm,
                    gender_address_id : 'pRelateCourseAddress',
                    http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                    http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                    return_type : 'json', //放回的值类型
                    page_no : 1, //当前的页数
                    page_size : 5, //当前页面显示的数量
                    rectotal : 0, //一共多少
                    pagetotal : 1,
                    operate_id : "itemList"
                });
                pageGo('pRelateCourse');
            });

            function preeDoPageSub(pObj){
                var param={};
                param.quoteid=courseid;
               // param.filtergrade=gradeid;
                pObj.setPostParams(param);
            }


            function getInvestReturnMethod(rps){
                var htm='';
                if(rps.objList!=null&&rps.objList.length>0){
                    $.each(rps.objList,function(idx,itm){
                        htm+='<tr>';
                        htm+='<td><input name="quote_course" value="'+itm.courseid+'" type="radio">';
                        if(itm.cuserid==${userid})
                            htm += "<span class='ico16' title='自建'></span>";
                        else if(itm.sourceType==1)
                            htm += "<span class='ico18' title='标准'></span>";
                        else if(itm.sourceType==2||itm.sourceType==3)
                            htm += "<span class='ico17' title='共享'></span>";
                        htm+='</td>';
                        htm+='<td><p><a href="teachercourse?m=toCourseDetial&courseid=' + itm.courseid +'&materialid='+itm.materialids+'" target="_blank">'+itm.coursename+'</a></p>';
                        htm+='<p class="font-darkgray">作者：'+(itm.schoolname==null?"北京四中网校":itm.schoolname) + "&nbsp;"+(itm.teachername==null?"":itm.teachername)+'&nbsp;&nbsp;&nbsp;&nbsp;年级：'+itm.gradevalue+'</p>';
                        htm+='<p>简介：'+(itm.introduction==null?"无":itm.introduction)+'</p>';
                        htm += "<p class='t_r'>资源：<b>"+itm.rescount+"</b> 试题：<b>"+itm.quescount+"</b>试卷：<b>"+itm.papercount+"</b>论题：<b>"+itm.topiccount+"</b>任务：<b>"+itm.taskcount+"</b>";
                        for(var i=1;i<6;i++)
                            if(itm.avgscore>=i)
                                htm += "<span class='ico_star1'></span>";
                            else if(itm.avgscore<i&&itm.avgscore>i-1)
                                htm += "<span class='ico_star2'></span>";
                            else
                                htm += "<span class='ico_star3'></span>";
                        htm += itm.avgscore + "</p>";
                        htm +='</td></tr>';
                    });
                }
                $("#itemList").html(htm);
                if(rps.objList.length>0){
                    pRelateCourse.setPagetotal(rps.presult.pageTotal);
                    pRelateCourse.setRectotal(rps.presult.recTotal);
                    pRelateCourse.setPageSize(rps.presult.pageSize);
                    pRelateCourse.setPageNo(rps.presult.pageNo);
                }else
                {
                    pRelateCourse.setPagetotal(0);
                    pRelateCourse.setRectotal(0);
                    pRelateCourse.setPageNo(1);
                }
                pRelateCourse.Refresh();
            }
        </script>
</head>
    <body>
    <div class="public_float public_float960">
        <p class="float_title"><strong>查看相关专题</strong></p>
        <div class="jxxt_add_zhuanti_nr">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w50"/>
                <col class="w860"/>
                <tbody id="itemList">
                    <%--<tr>
                        <td><input name="" type="radio">
                            <span class="ico16" title="自建"></span></td>
                        <td><p><a href="1" target="_blank">数学二元一次方程经典例题</a></p>
                            <p class="font-darkgray">作者：四川师范大学附属第二实验中学_卢大大艳&nbsp;&nbsp;&nbsp;&nbsp;年级：高一</p>
                            <p>简介：款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款 款了附件啦积分就开发就开发点击发建军节款</p>
                            <p class="t_r">资源：<b>100</b>试题：<b>10</b>试卷：<b>0</b>论题：<b>100</b>任务：<b>10</b><span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star2"></span><span class="ico_star3"></span> 3.5</p></td>
                    </tr>--%>
                </tbody>
            </table>
        </div>
        <form id="pRelateCourseForm" name="pRelateCourseForm"><p class="nextpage" id="pRelateCourseAddress" align="center"></p></form>

        <p class="p_tb_10 t_c"><a href="javascript:checkCourse(1);" class="an_public1">添&nbsp;加</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:$.fancybox.close();"  class="an_public1">取&nbsp;消</a>
    </div>
    </body>
</html>
