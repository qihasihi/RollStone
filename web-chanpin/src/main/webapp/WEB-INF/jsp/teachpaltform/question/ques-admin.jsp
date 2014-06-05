<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
	<script type="text/javascript" src="js/teachpaltform/ques.js"></script>
		<script type="text/javascript">
			var courseid="${courseid}";
			var p1;
            var quesType=0;
			$(function(){
                $("input[name='rdo_ques_type']").each(function(idx,itm){
                    $(itm).bind("click",function(){
                       pageGo('p1');
                    });
                });
				p1=new PageControl({
						post_url:'question?m=questionAjaxList',
						page_id:'page1',
						page_control_name:"p1",		//分页变量空间的对象名称
						post_form:document.page1form,		//form
						gender_address_id:'page1address',		//显示的区域
						http_free_operate_handler:validateParam,		//执行查询前操作的内容
						http_operate_handler:questionReturn,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:10,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_data" 
					});
					pageGo('p1');
			});
            function changeLiCss(idx){
                var obj =  $("#ultype").children();
                $.each(obj,function(idx,itm){
                    $(itm).removeClass("crumb");
                });
                $("#li_"+idx).addClass("crumb");
                quesType=idx;
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
                          <a href="question?m=toQuestionList&courseid=${c.courseid }">${c.coursename }</a>
                      </li>
                  </c:if>
              </c:forEach>
          </c:if>
      </ul>
  </div>
  <div class="subpage_nav">
      <ul>
          <li ><a href="task?toTaskList&courseid=${courseid }">学习任务</a></li>
          <li><a  href="tpres?toTeacherIdx&courseid=${courseid }&termid=${termid}">专题资源</a></li>
          <li><a href="tptopic?m=index&courseid=${courseid }">互动空间</a></li>
          <!--<li>
                <a href="commoncomment?m=toCourseCommentList&objectid=${courseid }">专题评价</a>
            </li> -->
          <li class="crumb"><a href="question?m=toQuestionList&courseid=${courseid}">试&nbsp;&nbsp;题</a></li>
      </ul>
  </div>

         <div class="content2">
             <div class="jxxt_zhuanti_hdkj_add">
                 <p class="f_right"><a class="ico15" target="_blank"  href="tpres?m=toRecycleIdx&type=3&courseid=${courseid}" title="回收站"></a></p>
                 <p class="font-darkblue"><a href="question?m=toAddQuestion&courseid=${courseid}" target="_blank"><span class="ico26"></span>添加试题</a></p>
             </div>

             <div class="subpage_lm">

                    <div id="dv_filter" style="display: none"></div>
                       <div id="detail" style="display: none"></div>
                        <ul id="ultype">
                            <c:if test="${!empty quesTypeList}">
                                <li id="li_0" class="crumb"><a href="javascript:changeLiCss(0);pageGo('p1');" target="_self">全部</a></li>
                                <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                                   <!-- <li onclick="changeQuesType(${t.dictionaryvalue})" id="rdo_type_${idx.index+1}" type="radio" value="${t.dictionaryvalue}" name="rdo_ques_type"/>
                                    <label for="rdo_type_${idx.index+1}">${t.dictionaryname}</label>-->
                                    <li id="li_${t.dictionaryvalue}"><a href="javascript:changeLiCss(${t.dictionaryvalue});pageGo('p1');" target="_self">${t.dictionaryname}</a></li>
                                </c:forEach>
                            </c:if>
                        </ul>
              </div>
        <div class="jxxt_zhuanti_shiti" id="tbl_body_data">

   	    </div>
             <form name="page1form" id="page1form" action="" method="post">
                 <div id="page1address" style="display:none"></div>
             </form>
             </div>
         <%@include file="/util/foot.jsp" %>
  </body>
</html>
