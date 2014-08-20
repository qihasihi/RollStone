<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
   
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript">
         var courseid="${courseid}";
		 var pList,pBankList;
		$(function(){
            pList = new PageControl( {
                post_url : 'task?m=ajaxTaskList',
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


         function load_task_detial(taskid){
             if(typeof taskid=='undefined')
                 return;
             $.ajax({
                 url:"taskallot?m=ajaxTaskAllot",
                 type:"post",
                 data:{taskid:taskid},
                 dataType:'json',
                 cache: false,
                 error:function(){
                     alert('系统未响应，请稍候重试!');
                 },success:function(rmsg){
                     if(rmsg.type=="error"){
                         alert(rmsg.msg);
                     }else{
                         var htm='';
                         $('#p_obj_'+taskid+'').html('');
                         if(rmsg.objList.length){
                             if(rmsg.objList[0]!=null){
                                 $.each(rmsg.objList[0],function(idx,itm){
                                     $('#p_obj_'+taskid+'').append(itm.allotobj+"&nbsp;");
                                 });
                             }

                             var h='',type;
                             if(rmsg.objList[1]!=null){
                                 $.each(rmsg.objList[1],function(idx,itm){
                                     switch (itm.questiontype){
                                         case 1:type="其&nbsp;&nbsp;&nbsp;&nbsp;他：";break;
                                         case 2:type="填&nbsp;&nbsp;&nbsp;&nbsp;空：";break;
                                         case 3:type="单&nbsp;&nbsp;&nbsp;&nbsp;选：";break;
                                         case 4:type="多&nbsp;&nbsp;&nbsp;&nbsp;选：";break;
                                     }
                                     $('#s_questype_'+taskid+'').html(type);
                                     h+=itm.content.replace('<span name="fillbank"></span>',"_______");
                                 });
                             }
                             $('#ques_name_'+taskid+'').html(h);

                             var ohtm='';
                             if(rmsg.objList[2]!=null){
                                 $.each(rmsg.objList[2],function(ix,im){
                                     //var taskObj=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                     var taskObj=im.content;
                                     ohtm+='<li>';
                                     var type=im.questiontype==3?"radio":"checkbox";
                                     ohtm+='<input disabled type="'+type+'"/>';
                                     ohtm+=im.optiontype+".&nbsp;";
                                     ohtm+=taskObj;
                                     if(im.isright==1)
                                         ohtm+='<span class="ico12"></span>';
                                     ohtm+='</li>';
                                 });
                             }
                             $('#p_option_'+taskid+'').html(ohtm);
                         }
                     }
                 }
             });
         }

        function pLoad(url,courseid){
            $("li[name='li_nav']").each(function(idx,itm){
                $(itm).siblings().removeClass("crumb");
            });
            $("li[name='li_nav']").eq(1).addClass("crumb");
             $(window.parent.document).contents().find("#dv_course_element").load(url);
        }


         function getInvestReturnMethod(rps){
            var html='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var type="",criteria,taskObj='';
                    switch (itm.tasktype){
                        case 1:
                            criteria=itm.criteria==1?"查看":"提交心得";
                            type="资源学习&nbsp;&nbsp;&nbsp;&nbsp;";
                            break;
                        case 2:
                            criteria=itm.criteria==1?"查看":"发主帖";
                            type="互动交流&nbsp;&nbsp;&nbsp;&nbsp";
                            break;
                        case 3:
                            criteria=itm.criteria==1?"提交":"";
                            type="试&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;&nbsp;&nbsp;";
                            break;
                        case 4:
                            criteria=itm.criteria==1?"提交试卷":"";
                            type="成卷测试&nbsp;&nbsp;&nbsp;&nbsp;";
                            break;
                        case 5:
                            criteria=itm.criteria==1?"提交试卷":"";
                            type="自主测试&nbsp;&nbsp;&nbsp;&nbsp";
                            break;
                        case 6:
                            criteria="";
                            type="微课程学习&nbsp;&nbsp;&nbsp;&nbsp;";
                            break;
                    }

                    /**
                     *     <p class="title"><a class="ico49b"></a>任务8：试&nbsp;&nbsp;&nbsp;&nbsp;题</p>
                     <p class="title"><a class="ico49a"></a>任务6：资源学习&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-blue">请学习学习资源中的 《鸿门宴》话剧.flv</a></p>
                     <div class="text">
                     <p><strong>任务对象：</strong> 高一1班第一组、第二组 ； 高一2班</p>
                     <p><strong>完成标准：</strong> 学习心得</p>
                     <p class="font-black">&nbsp;&nbsp;&nbsp;&nbsp;请大家学习课堂视频，回顾、巩固自己薄弱的环节，不要错过任何的学习机会。只有好好学习，天天向上，才有可能成功。失败不要仅，要仅的是要继续好好学习。</p>
                     </div>
                     <p class="title"><a class="ico49a"></a>任务5：试&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-blue">请学习学习资源中的 《鸿门宴》话剧.flv</a></p>
                     <div class="text">
                     <p class="f_right">&nbsp;</p>
                     <p><strong>任务对象：</strong> 高一1班第一组、第二组 ； 高一2班</p>
                     <p><strong>完成标准：</strong> 学习心得</p>
                     <table border="0" cellspacing="0" cellpadding="0" class="font-black">
                     <col class="w50"/>
                     <col class="w860"/>
                     <tr>
                     <td valign="top"><strong>单&nbsp;&nbsp;&nbsp;&nbsp;选：</strong></td>
                     <td>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国______的批判<br>
                     <input type="radio" name="radio" id="radio" value="radio">
                     A. 民族性<br>
                     <input type="radio" name="radio" id="radio6" value="radio">
                     B. 国民性<br>
                     <input type="radio" name="radio" id="radio7" value="radio">
                     C. 传统性<br>
                     <input type="radio" name="radio" id="radio8" value="radio">
                     D. 文化性</td>
                     </tr>
                     </table>
                     </div>
                     <div class="jxxt_zhuanti_rw"></div>
                     * @type {*}
                     */

                    //taskObj=replaceAll(replaceAll(itm.taskobjname.toLowerCase(),"<p>",""),"</p>","");
                    taskObj=itm.taskobjname;
                    html+='<p class="title"><a class="ico49b"  id="a_show_'+itm.taskid+'" href="javascript:void(0);" onclick="showOrhide(this,\''+itm.taskid+'\')"></a>任务'+(rps.presult.pageSize*(rps.presult.pageNo-1)+(idx+1))+'：'+type;
                    if(itm.tasktype==1){
                        if(itm.resourcetype==1){
                            var url='teachercourse?toTeacherIdx&courseid='+courseid+'&tpresdetailid='+itm.taskvalueid+'&taskid='+itm.taskid;
                            html+='<a href="javascript:pLoad(\''+url+'\','+courseid+')" class="font-blue">'+taskObj+'</a>';
                        }else{
                            if(itm.remotetype==1){
                                html+='<a href="tpres?m=toRemoteResourcesDetail&hd_res_id='+itm.taskvalueid+'" class="font-blue">'+taskObj+'</a>';
                            }else{
                                html+='<a href="tpres?m=toRemoteResourcesDetail&res_id='+itm.taskvalueid+'" class="font-blue">'+taskObj+'</a>';
                            }
                        }
                    }else if(itm.tasktype==2){
                        html+='<a href="tptopic?m=toDetailTopic&topicid='+itm.taskvalueid+'&taskid='+itm.taskid+'&courseid='+courseid+'" class="font-blue">'+taskObj+'</a>';
                    }else if(itm.tasktype==4){
                        html+='<a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.taskvalueid+'" class="font-blue">'+taskObj+'</a>';
                    }else if(itm.tasktype==6){
                       // html+='<a href="tpres?m=previewMic&courseid='+itm.courseid+'&resid='+itm.taskvalueid+'" class="font-blue">'+taskObj+'</a>';
                        var url='teachercourse?toTeacherIdx&courseid='+courseid+'&tpresdetailid='+itm.taskvalueid+'&taskid='+itm.taskid;
                        html+='<a href="javascript:pLoad(\''+url+'\','+courseid+')" class="font-blue">'+taskObj+'</a>';
                    }else if(itm.tasktype==10){
                        html+='<a href="#" ></a>';
                    }
                    html+='</p>';
                    html+='<div id="div_task_'+itm.taskid+'" style="display:none;"  class="text">';
                   // html+='<p class="f_right"><a href="task?m=toTaskSuggestList&courseid='+courseid+'&taskid='+itm.taskid+'" target="_blank" class="font-darkblue">学生建议</a></p>';
                   // html+='<p><strong>任务对象：</strong><span id="p_obj_'+itm.taskid+'"></span></p>';
                    html+='<p><strong>完成标准：</strong> '+criteria+'</p>';
                    html+='<table border="0" cellspacing="0" cellpadding="0" class="black">';
                    html+='<col class="w50"/>'
                    html+='<col class="w860"/>';
                    html+='<tr>'
                    html+='<td valign="top"><strong id="s_questype_'+itm.taskid+'"></strong></td>';
                    html+='<td><span id="ques_name_'+itm.taskid+'"></span>';
                    html+='<ul id="p_option_'+itm.taskid+'"></ul></td></tr>';
                    html+='</table>';
                    html+='</div>';
                });
            }else{
                html='';　
            }
            $("#initItemList").html(html);

            if(rps.objList.length>0){
                pList.setPagetotal(rps.presult.pageTotal);
                pList.setRectotal(rps.presult.recTotal);
                pList.setPageSize(rps.presult.pageSize);
                pList.setPageNo(rps.presult.pageNo);
                $("#sp_task_count").html(rps.presult.recTotal);
            }else
            {
                pList.setPagetotal(0);
                pList.setRectotal(0);
                pList.setPageNo(1);
                $("#sp_task_count").html("0");
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


    </script>
	</head>
    <div class="content2">

        <p class="jxxt_trzt_number"><strong>学习任务数：</strong><span id="sp_task_count"></span></p>
        <div class="jxxt_trzt_rw" id="initItemList">

        </div>


        <form id="pListForm" name="pListForm">
            <p class="Mt20" id="pListaddress" align="center"></p>
        </form>
    </div>
</html>
