<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

   
<html> 
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="<%=basePath %>js/teachpaltform/tptask.js"></script> 
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




            pBankList = new PageControl( {
                post_url : 'task?m=ajaxTaskBankList',
                page_id : 'page2',
                page_control_name : "pBankList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoBankPageSub, //执行查询前操作的内容
                http_operate_handler : getBankInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "bankList"
            });
            //pageGo('pBankList');

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
                    }
                    if(typeof itm.taskobjname!='undefined')
                        taskObj=itm.taskobjname;//taskObj=replaceAll(replaceAll(itm.taskobjname.toLowerCase(),"<p>",""),"</p>","");

                    html+='<div class="jxxt_zhuanti_rw">';
                    html+='<div class="jxxt_zhuanti_rwR">';
                    html+='<div class="title">';
                    html+='<p class="f_right"><span class="ico35"></span><b>'+itm.stucount+'/'+itm.totalcount+'</b>';
                    if(itm.taskstatus=="1")
                        html+='<a class="ico11" title="修改" href="task?doUpdTask&courseid='+courseid+'&taskid='+itm.taskid+'"></a><a title="删除" class="ico04" href="javascript:doDelTask('+itm.taskid+','+itm.stucount+')"></a>';
                    else{
                        html+='<a class="ico37" title="查看统计" href="task?toTaskPerformance&taskid='+itm.taskid+'&questype='+itm.questiontype+'"></a><a class="ico04" title="删除" href="javascript:doDelTask('+itm.taskid+')"></a>';
                    }
                    html+='</p>';
                    html+='<p><a class="ico49b"  id="a_show_'+itm.taskid+'" href="javascript:void(0);" onclick="showOrhide(this,\''+itm.taskid+'\')"></a><a href="javascript:void(0);" onclick="$(this).prev().click();">任务'+(rps.presult.pageSize*(rps.presult.pageNo-1)+(idx+1))+'：'+type+'</a>';
                    if(itm.tasktype==1){
                        html+='<a href="tpres?toTeacherIdx&courseid='+courseid+'&tpresdetailid='+itm.taskvalueid+'&taskid='+itm.taskid+'" class="font-blue">'+taskObj+'</a>';
                    }else if(itm.tasktype==2){
                        html+='<a href="tptopic?m=toDetailTopic&topicid='+itm.taskvalueid+'&taskid='+itm.taskid+'&courseid='+courseid+'" class="font-blue">'+taskObj+'</a>';
                    }
                    html+='</p>';
                    html+='</div>';
                    html+='<div id="div_task_'+itm.taskid+'" style="display:none;"  class="text">';
                    html+='<p class="f_right"><a href="task?m=toTaskSuggestList&courseid='+courseid+'&taskid='+itm.taskid+'" target="_blank" class="font-darkblue">学生建议</a></p>';
                    html+='<p><strong>任务对象：</strong><span id="p_obj_'+itm.taskid+'"></span></p>';
                    html+='<p><strong>完成标准：</strong> '+criteria+'</p>';
                    html+='<p><strong>任务描述：</strong><span class="width">'+(typeof itm.taskremark !='undefined'?itm.taskremark:"")+'</span></p>';
                    html+='<table border="0" cellspacing="0" cellpadding="0" class="black">';
                    html+='<col class="w50"/>'
                    html+='<col class="w750"/>';
                    html+='<tr>'
                    html+='<td><strong id="s_questype_'+itm.taskid+'"></strong></td>';
                    html+='<td><span id="ques_name_'+itm.taskid+'"></span>';
                    html+='<ul id="p_option_'+itm.taskid+'"></ul></td></tr>';
                    html+='</table>';
                    html+='</div>';
                    html+='</div>';
                    html+='<div class="jxxt_zhuanti_rwL">';
                    if(itm.taskstatus=="3"){
                        html+='<p><span class="ico_time"></span>任务已结束</p>';
                    }else if(itm.taskstatus=="1"){
                        html+='<p class="blue"><span class="ico_time"></span>任务未开始</p>';
                    }else{
                        html+='<p class="green"><span class="ico_time"></span>任务进行中</p>';
                        html+='<input type="hidden" name="hd_task_status" value="'+itm.taskid+'"/>';
                    }

                    html+='</div>';
                    html+='<div class="clear">&nbsp;</div>';
                    html+='</div>';
                });
            }else{
                html='';
            }
            $("#initItemList").html(html);
            $("#dv_task").show();
            $("#tbl_bank").hide();

             if($("input[name='hd_task_status']").length>0){
                 var taskid=$("input[name='hd_task_status']").get(0).value;
                 $('a[id="a_show_'+taskid+'"]').click();
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
            pObj.setPostParams(param);
        }

         function preeDoBankPageSub(pObj){
             if(typeof(pObj)!='object'){
                 alert("异常错误，请刷新页面重试!");
                 return;
             }
             if(courseid.length<1){
                 alert('异常错误，系统未获取到专题标识!');
                 return;
             }
             var cloudtype=$("#sel_level").val();
             var tasktype=$("#sel_type").val();
             var param={courseid:courseid};
             if(cloudtype!=null&&cloudtype.length>0)
                param.cloudtype=cloudtype;
             if(tasktype!=null&&tasktype.length>0)
                 param.tasktype=tasktype;
             pObj.setPostParams(param);
         }

         function getBankInvestReturnMethod(rps){
             var html='';
             if(rps.objList!=null&&rps.objList.length>0){
                 $.each(rps.objList,function(idx,itm){
                     var type="",criteria,status='',questype='';
                     switch (itm.tasktype){
                         case 1:
                             criteria=itm.criteria==1?"查看":"提交心得";
                             type="资源学习";
                             break;
                         case 2:
                             criteria=itm.criteria==1?"查看":"发主帖";
                             type="互动交流";
                             break;
                         case 3:
                             criteria=itm.criteria==1?"提交":"";
                             type="试题";
                             switch (itm.questiontype){
                                 case 1:questype="_问答";break;
                                 case 2:questype="_填空";break;
                                 case 3:questype="_单选";break;
                                 case 4:questype="_多选";break;
                             }
                             break;
                     }
                     if((typeof itm.cloudstatus!='undefined') && (itm.cloudstatus==3||itm.cloudstatus==4) ){
                         status='参考';
                     }else{
                         status='自建';
                     }
                    // var taskObj=replaceAll(replaceAll(itm.taskobjname.toLowerCase(),"<p>",""),"</p>","");
                     html+='<tr>';
                     html+='<td>'+type+questype+'</td>';
                     html+='<td>'+status+'</td>';
                     html+='<td><p>';
                     if(itm.tasktype==1){
                         html+='<a target="_blank" href="tpres?toTeacherIdx&courseid='+courseid+'&tpresdetailid='+itm.taskvalueid+'&taskid='+itm.taskid+'" >';
                     }else if(itm.tasktype==2){
                         html+='<a target="_blank" href="tptopic?m=toDetailTopic&topicid='+itm.taskvalueid+'&taskid='+itm.taskid+'&courseid='+courseid+'">';
                     }else if(itm.tasktype==3){
                         html+='<a target="_blank" href="question?m=todetail&id='+itm.taskvalueid+'">';
                     }
                     if(typeof itm.taskobjname!='undefined')
                        html+=replaceAll(itm.taskobjname.toLowerCase(),'<span name="fillbank"></span>',"_______")+'</a></p>';
                     html+='<p id="dv_option_'+itm.taskid+'">';
                     if(itm.questionOptionList!=null&&itm.questionOptionList.length>0){
                         $.each(itm.questionOptionList,function(idx,im){
                             var type=itm.questiontype==3?"radio":"checkbox";
                             html+='<input disabled type="'+type+'"/>';
                             html+=im.optiontype+".&nbsp;";
                             //html+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                             html+=im.content;
                             if(im.isright==1)
                                 html+='<span class="ico12"></span>';
                             html+='<br>';
                         });
                     }
                     html+='</p>';
                     html+='</td>';
                     html+='<td>'+criteria+'</td>';
                     html+='<td>';
                     if(itm.tasktype==1){
                         html+='<a target="_blank" href="tpres?toTeacherIdx&courseid='+courseid+'&tpresdetailid='+itm.taskvalueid+'&taskid='+itm.taskid+'" class="ico46" title="浏览"></a>&nbsp;';
                     }else if(itm.tasktype==2){
                         html+='<a target="_blank" href="tptopic?m=toDetailTopic&topicid='+itm.taskvalueid+'&taskid='+itm.taskid+'&courseid='+courseid+'"  class="ico46" title="浏览"></a>&nbsp;';
                     }else if(itm.tasktype==3){
                         html+='<a target="_blank" href="question?m=todetail&id='+itm.taskvalueid+'" class="ico46" title="浏览"></a>&nbsp;';
                     }
                     if(typeof itm.flag!='undefined'){
                        html+='<span class="ico43" title="已启用"></span>';
                     }else{
                         html+='<a  class="ico02" title="启用" href="task?doUpdTask&courseid='+courseid+'&taskid='+itm.taskid+'"></a>';
                     }
                     html+='</td></tr>';



                 });
             }else{
                 html='<p>暂无数据!</p>';
             }
             $("#bankList").html(html);
             $("#tbl_bank").show();
             $("#dv_task").hide();

             if(rps.objList.length>0){
                 pBankList.setPagetotal(rps.presult.pageTotal);
                 pBankList.setRectotal(rps.presult.recTotal);
                 pBankList.setPageSize(rps.presult.pageSize);
                 pBankList.setPageNo(rps.presult.pageNo);
             }else
             {
                 pBankList.setPagetotal(0);
                 pBankList.setRectotal(0);
                 pBankList.setPageNo(1);
             }
             pBankList.Refresh();
         }

        function showCourseList(){
			var ulobj=$("#ul_courselist");
			if(ulobj.css("display")=="none")
				ulobj.show();    
			else
				ulobj.hide();   	  
		}  
    </script>
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
                        <li>
                            <a href="task?toTaskList&courseid=${c.courseid }">${c.coursename }</a>
                        </li>
                    </c:if>
                </c:forEach>
            </c:if>
        </ul>
    </div>

    <div class="subpage_nav">
        <ul>
            <li class="crumb"><a>学习任务</a></li>
            <li><a  href="tpres?toTeacherIdx&courseid=${courseid }&termid=${termid}">专题资源</a></li>
            <li><a href="tptopic?m=index&courseid=${courseid }">互动空间</a></li>
            <!--<li>
                <a href="commoncomment?m=toCourseCommentList&objectid=${courseid }">专题评价</a>
            </li> -->
            <li><a href="question?m=toQuestionList&courseid=${courseid}">试&nbsp;&nbsp;题</a></li>
        </ul>
    </div>
    <div class="content2">
        <div class="subpage_lm">
            <p class="f_right"><a class="ico15" target="_blank" href="tpres?m=toRecycleIdx&type=1&courseid=${courseid}" title="回收站"></a></p>
            <ul>
                <li name="li_nav" id="li_" class="crumb"><a href='javascript:pageGo("pList")'>已发任务</a></li>
                <li name="li_nav"><a href='javascript:pageGo("pBankList")'>任务库</a></li>
            </ul>
        </div>

        <div id="dv_task">
            <p class="jxxt_zhuanti_add font-darkblue"><a href="task?toAddTask&courseid=${param.courseid }&termid=${param.termid}" ><span class="ico26"></span>添加任务</a></p>
            <div class="jxxt_zhuanti" id="initItemList">

            </div>
        </div>

        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2" id="tbl_bank" style="display: none">
            <col class="w110"/>
            <col class="w110"/>
            <col class="w500"/>
            <col class="w120"/>
            <col class="w110"/>
            <tr>
                <th>
                    <select id="sel_type" onchange="pageGo('pBankList')">
                        <option value="">任务类型</option>
                        <option value="1">资源学习</option>
                        <option value="2">互动交流</option>
                        <option value="ques_1">试题_问答</option>
                        <option value="ques_2">试题_填空</option>
                        <option value="ques_3">试题_单选</option>
                        <option value="ques_4">试题_多选</option>
                    </select>
                </th>
                <th>
                    <select id="sel_level" onchange=" pageGo('pBankList')">
                        <option value="">出处</option>
                        <option value="-1">参考</option>
                        <option value="1">自建</option>
                    </select>
                </th>
                <th>关联内容</th>
                <th>完成标准</th>
                <th>操作</th>
            </tr>
          <!--  <tr>
                <td>试题_填空</td>
                <td>自建</td>
                <td><p>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国______的批判<br>
                    <input type="radio" name="radio" id="radio2" value="radio">
                    A. 民族性<br>
                    <input type="radio" name="radio" id="radio3" value="radio">
                    B. 国民性<br>
                    <input type="radio" name="radio" id="radio4" value="radio">
                    C. 传统性<br>
                    <input type="radio" name="radio" id="radio5" value="radio">
                    D. 文化性<br>
                </p></td>
                <td>学习心得</td>
                <td><a href="1" class="ico46" title="浏览"></a><a href="1" class="ico02" title="启用"></a></td>
            </tr> -->
            <tbody  id="bankList">

            </tbody>
        </table>

        <form id="pListForm" name="pListForm">
            <p class="Mt20" id="pListaddress" align="center"></p>
        </form>
    </div>



 <%@include file="/util/foot.jsp" %>
		<script type="text/javascript">
			<c:if test="${!empty suggestList}">
				<c:forEach items="${suggestList}" var="s">
					var isanonymous="${s.isanonymous}";   
					var htm='<li ';  
					if(typeof(isanonymous)!='undefined'&&isanonymous<1)
						htm+=' onmouseover="genderShowdiv(\'${s.userinfo.realname}\',\'${s.ctimeString}\')" onmouseout="$(\'#div_tmp_show01\').remove();" ';
					htm+='>${s.suggestcontent}</li>';     
					var divobj=$("#div_suggest_${s.taskid}");    
					if(divobj.length>0){
						$("#p_suggest_${s.taskid}").show();   
						divobj.append(htm);
					}  
				</c:forEach>  
			</c:if> 
		</script>
	</body>
</html>
