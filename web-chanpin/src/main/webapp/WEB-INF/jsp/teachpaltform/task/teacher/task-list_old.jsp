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
                post_form : document.pBankListForm,
                gender_address_id : 'pBankListaddress',
                http_free_operate_handler : preeDoBankPageSub, //执行查询前操作的内容
                http_operate_handler : getBankInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "bankList"
            });
            pageGo('pBankList');
		});


         function showOrhide(aobj, taskid) {
             load_task_detial(taskid);
             var status=$("#div_task_"+taskid);
             if(status.css("display")=="none"){
                 $(status).show();
                 $(aobj).find('img').attr('src','images/an10_130423.png');
             }else{
                 $(status).hide();
                 $(aobj).find('img').attr('src','images/an09_130423.png');
             }
         }



         function getInvestReturnMethod(rps){
            var html='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var type="",criteria,status;
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
                            break;
                    }
                    if(itm.taskstatus=="3"){
                        status="任务已结束";
                    }else if(itm.taskstatus=="1"){
                        status="任务未开始";
                    }else{
                        status=itm.taskstatus;
                    }

                    html+='<tr>';
                    html+='<td>'+status+'</td>';
                    html+='<td>'+itm.taskname+'</td>';
                    html+='<td>'+type+'</td>';
                    html+='<td>'+itm.taskobjname+'</td>';
                    html+='<td>'+itm.stucount+'/'+itm.totalcount+'</td>';
                    html+='<td><a id="a_show_'+itm.taskid+'" href="javascript:void(0);" onclick="showOrhide(this,\''+itm.taskid+'\')"><img  src="images/an10_130423.png" width="12" height="12" align="middle" /></a></td>';
                    if(itm.taskstatus=="1")
                        html+='<td><a href="task?doUpdTask&courseid='+courseid+'&taskid='+itm.taskid+'">修改</a>&nbsp;<a href="javascript:doDelTask('+itm.taskid+')">删除</a></td>';
                    else{
                        html+='<td><a href="task?m=toTaskPerformance&taskid='+itm.taskid+'">查看统计</a><a href="javascript:doDelTask('+itm.taskid+')">删除</a></td>';
                    }
                    html+='</tr>';
                    html+='<tr><td colspan="7">';
                    html+='<div id="div_task_'+itm.taskid+'" style="display:none;">';
                    html+='<p id="p_remark_'+itm.taskid+'">任务描述：'+(typeof itm.taskremark !='undefined'?itm.taskremark:"")+'</p>';
                    html+='<p id="p_obj_'+itm.taskid+'">任务对象：</p>';
                    html+='<p id="p_norm_'+itm.taskid+'">完成标准：'+criteria+'</p>';
                    html+='<p id="p_ques_'+itm.taskid+'"></p>';
                    html+='<p id="p_option_'+itm.taskid+'"></p>';
                    html+='</div></td></tr>';
                });
            }else{
                html='<p>暂无数据!</p>';
            }
            $("#initItemList").html(html);

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
                     html+='<tr>';
                     html+='<td>'+type+questype+'</td>';
                     html+='<td>'+status+'</td>';
                     html+='<td>'+itm.taskobjname+'</td>';
                     html+='<td>'+itm.criteria+'</td>';
                     html+='<td><a>查看</a>&nbsp;';
                     if(typeof itm.flag!='undefined'){
                        html+='<a>已启用</a>';
                     }else{
                         html+='<a href="task?doUpdTask&courseid='+courseid+'&taskid='+itm.taskid+'">启用</a>';
                     }
                     html+='</td></tr>';

                     if(itm.questionOptionList!=null&&itm.questionOptionList.length>0){
                         html+='<tr><td><ul>';
                         $.each(itm.questionOptionList,function(idx,im){
                             html+='<li>';
                             var type=itm.questiontype==3?"radio":"checkbox";
                             html+='<input type="'+type+'"/>';
                             html+=im.optiontype;
                             html+=im.content;
                             html+='</li>';
                         });
                         html+='</ul></td></tr>';
                     }

                 });
             }else{
                 html='<p>暂无数据!</p>';
             }
             $("#bankList").html(html);

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
		<div class="jxpt_layout">
		<%@include file="/util/nav.jsp" %>
     <div class="jxpt_keti_title">
    <p>${coursename }<a href="javascript:showCourseList();"><img src="images/an01_130423.png" width="27" height="28" align="absbottom" class="p_l_20" /></a></p>
    <ul class="font-white" style="display:none;" id="ul_courselist">  
    <!-- <li><a href="1" target="_blank">美丽的天空</a></li> -->
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
  <div class="public_ejlable_layout">
    <ul class="">  
      <li class="crumb"><a href="task?toTaskList&courseid=${courseid }">学习任务</a></li>
      <li><a  href="tpres?toTeacherIdx&courseid=${courseid }&termid=${termid}">专题资源</a></li>   
      <li><a href="activecolumns/toStuIdx?teachercourseid=${courseid }">互动空间</a></li>
      <li>    
		<a href="commoncomment?m=toCourseCommentList&objectid=${courseid }">专题评价</a>
	 </li> 
    </ul>
  </div>  
  <div class="jxpt_content_layout">  
    <p id="p_task_add" class="p_t_10 font14"><img src="images/an02_130423.png" width="20" height="21" align="middle" />&nbsp;<a href="task?toAddTask&courseid=${param.courseid }&termid=${param.termid}" >添加任务</a></p>
    <div class="jxpt_zrgl_layoutL f_right">
      <p class="font_strong p_b_10"><a href="task?m=toTaskSuggestList&courseid=${courseid}">学生建议</a></p>
      <div class="jxpt_zrgl_content">
      	<!--<c:if test="${!empty taskList and !empty suggestList}">
			<c:forEach items="${taskList}" var="tp" varStatus="idx">
				<p id="p_suggest_${tp.taskid }" style="display:none;"  class="font_strong p_t_5">任务${idx.index+1 }：</p>
		        <ul id="div_suggest_${tp.taskid }" class="jxpt_keti_list">
		            
		        </ul> 
			</c:forEach>
		</c:if> -->

       <!-- <p class="font_strong p_t_5">任务三：</p>
        <ul class="jxpt_keti_list">
          <li>王小名放学要回家</li>
          <li>称资源名称好好学习天向上。</li>
          <li>称资源名称好好学习天天向上多多少少。</li>
        </ul> -->
      </div>
    </div>
     <div class="jxpt_keti_layoutL" >
         <div>
             <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
                 <colgroup class="w300"></colgroup>
                 <colgroup span="2" class="w100"></colgroup>
                 <colgroup class="w140"></colgroup>
                 <colgroup span="3" class="w110"></colgroup>
                 <tbody  id="initItemList">

                 </tbody>
             </table>
             <!-- <p class="t_r">共&nbsp;<span class="font-red">70</span>&nbsp;条记录&nbsp;&nbsp;每页显示&nbsp;<span class="font-red">20</span>&nbsp;条&nbsp;&nbsp;第&nbsp;<span class="font-red">1/5</span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page01_b.gif" title="首页" width="21" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page02_b.gif"  title="上一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page03_b.gif" title="下一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page04_a.gif" title="末页" width="21" height="16" border="0" /></a></p> -->
             <form id="pListForm" name="pListForm">
                 <p class="Mt20" id="pListaddress" align="center"></p>
             </form>
         </div>

         <div>
             <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
                 <colgroup class="w300"></colgroup>
                 <colgroup span="2" class="w100"></colgroup>
                 <colgroup class="w140"></colgroup>
                 <colgroup span="3" class="w110"></colgroup>
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
                     <th></th>
                 </tr>
                 <tbody  id="bankList">

                 </tbody>
             </table>
             <!-- <p class="t_r">共&nbsp;<span class="font-red">70</span>&nbsp;条记录&nbsp;&nbsp;每页显示&nbsp;<span class="font-red">20</span>&nbsp;条&nbsp;&nbsp;第&nbsp;<span class="font-red">1/5</span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page01_b.gif" title="首页" width="21" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page02_b.gif"  title="上一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page03_b.gif" title="下一页" width="14" height="16" border="0" /></a>&nbsp;&nbsp;<a href="1" target="_blank"><img src="images/page04_a.gif" title="末页" width="21" height="16" border="0" /></a></p> -->
             <form id="pBankListForm" name="pBankListForm">
                 <p class="Mt20" id="pBankListaddress" align="center"></p>
             </form>

         </div>


     </div>
  </div>
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
