<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.school.entity.teachpaltform.TpCourseInfo"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
<script type="text/javascript">
    var currtenTab=1; //当前学科导航栏页数
    var tabSize=8; //学科导航栏每页显示数量
    var courseid="${param.courseid}";
    $(function(){

        $("input[name='classes']").bind("click",function(){
            pageGo("pList");
        });
        pList = new PageControl( {
            post_url : 'commoncomment?m=ajaxCourseCLasslist',
            page_id : 'page1',
            page_control_name : "pList",
            post_form : document.pListForm,
            gender_address_id : 'pListaddress',
            http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
            http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
            return_type : 'json', //放回的值类型
            page_no : 1, //当前的页数
            page_size : 20, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "initItemList"
        });
        pageGo("pList");
       // $("input[name='classes']")[0].click();
    });

    function preeDoPageSub(pObj){
        var classvalue=$("input[name='classes']:checked").val();
        var param={};
        if(classvalue.split("_").length==2){
            if(classvalue.split("_")[0]=="c")
                param.classtype=1;
            if(classvalue.split("_")[0]=="vc")
                param.classtype=2;
            param.classid=classvalue.split("_")[1];
            param.tchid=${tchid};

        }
        param.courseid=courseid;
        pObj.setPostParams(param);
    }

    function getInvestReturnMethod(rps){
        var html="";
        var avgHtml="总评分：<span class='ico_star3'></span><span class='ico_star3'></span><span class='ico_star3'></span><span class='ico_star3'></span><span class='ico_star3'></span>0";
        if(rps!=null&&rps.presult.list!=null&&rps.presult.list[0].length>0){
            $.each(rps.presult.list[0],function(idx,itm){
                html+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
                html+="<td>"+itm.studentinfo.stuno+"</td>";
                html+="<td>"+itm.studentinfo.stuname+"</td>";
                html+="<td><p>"+itm.commentcontext+"</p></td>";
                html+="<td><p><span class='font-gray f_right'>"+itm.ctimeString+"</span>";
                for(var i=1;i<6;i++)
                    if(itm.scoreinfo.score>=i)
                        html += "<span class='ico_star1'></span>";
                    else if(itm.scoreinfo.score<i&&itm.scoreinfo.score>i-1)
                        html += "<span class='ico_star2'></span>";
                    else
                        html += "<span class='ico_star3'></span>";
                html+=itm.scoreinfo.score;
                html+="</p><td>";
                html+="</tr>";
            });

            avgHtml="总评分：";
            for(var i=1;i<6;i++)
                if(rps.presult.list[1].AVG_SCORE>=i)
                    avgHtml += "<span class='ico_star1'></span>";
                else if(rps.presult.list[1].AVG_SCORE<i&&rps.presult.list[1].AVG_SCORE>i-1)
                    avgHtml += "<span class='ico_star2'></span>";
                else
                    avgHtml += "<span class='ico_star3'></span>";
            avgHtml+=rps.presult.list[1].AVG_SCORE;
            if(rps.presult.list[0].length>0){
                pList.setPagetotal(rps.presult.pageTotal);
                pList.setRectotal(rps.presult.recTotal);
                pList.setPageSize(rps.presult.pageSize);
                pList.setPageNo(rps.presult.pageNo);
            }else{
                pList.setPagetotal(0);
                pList.setRectotal(0);
                pList.setPageNo(1);
            }
            pList.Refresh();
        }else{
            html+="<tr><td colspan='4'>沒有数据！</td></tr>";
        }
        $("#commentList").html(html);
        $("#totalScore").html(avgHtml);
    }

</script>
</head>
    <body>
    <div class="subpage_head"><span class="ico55"></span><strong>专题评价</strong></div>
    <div class="content1">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
            <col class="w80"/>
            <col class="w860"/>
            <tr>
                <td><strong>选择班级：</strong></td>
                <td><ul class="public_list2 font-black">
                    <li>
                        <input type="radio" name="classes" id="classes0" value="0" checked/>全部
                    </li>
                    <c:forEach var="cl" items="${classes }">
                        <li>
                            <input type="radio" name="classes" id="classes${cl.classid}" value="c_${cl.CLASS_ID}" />${cl.CLASS_GRADE}${cl.CLASS_NAME}
                        </li>
                    </c:forEach>

                    <c:forEach var="vcl" items="${vclasses }">
                        <li>
                            <input type="radio" name="classes" id="classes${vcl.virtualclassid}" value="vc_${vcl.VIRTUAL_CLASS_ID}" />虚拟 ${vcl.VIRTUAL_CLASS_NAME}
                        </li>
                    </c:forEach>
                </ul></td>
            </tr>
        </table>
        <p id="totalScore" class="font-black">
            总评分：<span class="ico_star3"></span><span class="ico_star3"></span><span class="ico_star3"></span><span class="ico_star3"></span><span class="ico_star3"></span> 0.0
        </p>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w110"/>
            <col class="w110"/>
            <col class="w450"/>
            <col class="w270"/>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>评语</th>
                <th>评分</th>
            </tr>
            <tbody id="commentList">
            </tbody>
        </table>

        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>

    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
