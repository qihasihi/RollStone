<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>



   
<html> 
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="<%=basePath %>js/teachpaltform/tptask.js"></script> 
		<script type="text/javascript">
         var courseid="${courseid}";
         var taskid="${taskid}";
		 var pList;
		$(function(){
            pList = new PageControl( {
                post_url : 'task?m=ajaxTaskSuggest',
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
		});


         function getInvestReturnMethod(rps){
            var html='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+='<tr';
                    if(idx%2==0)
                        html+=' class="trbg1"';
                    html+='>';
                    html+='<td>'+itm.suggestcontent+'</td>';
                   // html+='<td>'+itm.taskname+'</td>';
                    if(itm.isanonymous>0)
                        html+='<td>匿名</td>';
                    else{
                        html+='<td>'+itm.realname+'</td>';
                    }
                    html+='<td>'+itm.ctimeString+'</td>';
                    html+='</tr>';
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
            if(taskid.length<1){
                alert('异常错误，系统未获取到任务标识!');
                return;
            }
            var param={courseid:courseid,taskid:taskid};
            pObj.setPostParams(param);
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
    <div class="subpage_head"><span class="ico55"></span><strong>学生建议</strong></div>
    <div class="content1">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w700"/>
            <col class="w100"/>
            <col class="w140"/>
            <tr>
                <th>建议内容</th>
                <th>提议者</th>
                <th>提议时间</th>
            </tr>
            <tbody  id="initItemList">

            </tbody>
        </table>
        <form id="pListForm" name="pListForm">
            <p class="Mt20" id="pListaddress" align="center"></p>
        </form>

    </div>
 <%@include file="/util/foot.jsp" %>
	</body>
</html>
