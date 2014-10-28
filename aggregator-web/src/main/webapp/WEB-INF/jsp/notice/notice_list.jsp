<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
//UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
//String userid=user.getRef();
%>
<html>
	 <script type="text/javascript" src="<%=basePath %>js/notice/notice.js"></script>
	<script type="text/javascript">
            var isupdate=false;
			var p1;
            var xheditorConfig={
                tool : "mfull",
                html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
                upLinkUrl : "{editorRoot}upload.jsp",
                upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
                upImgUrl : "{editorRoot}upload.jsp",
                upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
                upFlashUrl : "{editorRoot}upload.jsp",
                upFlashExt : "swf,SWF"
                //	,upMediaUrl : "{editorRoot}upload.jsp",
                //	upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
                // onUpload:insertUpload //指定回调涵数
            };

			$(function(){
					p1 = new PageControl({
						post_url:'notice?m=ajaxlist', 
						page_id:'page1',
						page_control_name:'p1',
						post_form:document.page1form,
						gender_address_id:'page1address',
						http_operate_handler:noticeListReturn,
						return_type:'json',
						page_no:1, 
						page_size:20,
						rectotal:0,
						pagetotal:1,
						operate_id:"mainTbl"
					});

					pageGo("p1");
					$('#acontent').xheditor(xheditorConfig);
			});

        function btljOperate(bo,o){
             o.style.display=bo?'block':'none';
            if(!isupdate)
                 bo?$("#atrContent").hide():$("#atrContent").show();
            else
                bo?$("#utrContent").hide():$("#utrContent").show();
        }
</script>  
  <body>
  <div class="ej_content">
  <p class="p_20"><a href="javascript:showAddDiv('addDiv')" class="an_small">添加公告</a><a href="javascript:deleteSelected()" class="an_small">删除已选</a></p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >
      <colgroup class="w60"></colgroup>
      <colgroup class="w320"></colgroup>
      <colgroup class="w100"></colgroup>
      <colgroup class="w180"></colgroup>
      <colgroup span="3" class="w100"></colgroup>
    <tbody id="mainTbl">
    </tbody>   
  </table>
      <form id="page1form" name="page1form" method="post">
          <div class="nextpage" align="right" id="page1address"></div>
      </form>
      <input type="hidden" value="" id="ref" name="ref"/>
</div>
    <%@include file="/util/foot.jsp"  %> 
		<!-- 添加层 -->
  <div class="home_tz_float" style="display: none" id="addDiv">
      <div class="public_windows">
          <h3><a href="javascript:closeModel('addDiv','hide');pageGo('p1')"  title="关闭"></a>通知公告</h3>
  <form id="addform" name="addform">
  <table border="0" cellpadding="0" cellspacing="0"  class="public_tab1 public_input">
    <%--<tr>--%>
      <%--<td>类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>--%>
      <%--<td><select id="atype" name="atype"--%>
								<%--onchange="atypeSelect('atdNoticeUsefullife')">--%>
								<%--<option value="-1">--%>
									<%--==请选择==--%>
								<%--</option>--%>
								<%--<c:forEach items="${typeList}" var="itm">--%>
									<%--<option value="${itm.dictionaryvalue }">--%>
									<%--${itm.dictionaryname }--%>
								<%--</option>--%>
								<%--</c:forEach></select>--%>
							<%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
							<%--<input type="checkbox" id="aisTop" name="aisTop" value="0" />--%>
							<%--是否置顶&nbsp;&nbsp;						--%>
     <%--</td>--%>
    <%--</tr>--%>
        <tr>
            <th><span class="ico06"></span>标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
            <td>
                <input type="text" name="atitle"  id="atitle" class="w500" />
            </td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;设&nbsp;&nbsp;&nbsp;&nbsp;置：</th>
            <td><p>
                <input type="checkbox" onclick="p_yxsj.style.display=this.checked?'block':'none'" name="aistime" id="aistime" />
                <label for="aistime">有效时间</label>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="checkbox"  id="atitleLinkck" onclick="btljOperate(this.checked,p_btlj)" name="atitleLinkck"/>
                <label for="atitleLinkck">标题链接</label>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="aisTop" name="aisTop" value="0" />
                置顶</p>
                <p  id="p_yxsj" style="display:none">有效时间：
                    <input type="text" class="w200" id="abeginTime" name="abeginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text" />
                    &mdash;
                    <input type="text" class="w200" id="aendTime" name="aendTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text"/>
                </p>
                <p  id="p_btlj" style="display:none">链接地址：
                    <input name="atitlelink" id="atitlelink" type="text" class="w430" />
                </p></td>
        </tr>

    <tr id="atrContent">
        <th><span class="ico06"></span>内&nbsp;&nbsp;&nbsp;&nbsp;容：</th>
      <td id="atdContent"><textarea style="width:480px;height:200px" id="acontent" name="acontent"/></textarea></td>
    </tr>
    <tr>
        <th><span class="ico06"></span>发送对象：</th>
      <td><ul class="public_list2">
          <c:if test="${!empty roleDicList}">
              <c:forEach items="${roleDicList}" var="itm">
                  <li><input  type="checkbox" value="${itm.dictionaryvalue }" id="aroleId${itm.dictionaryvalue}"
                              name="aroleId" /><label for="aroleId${itm.dictionaryvalue}">&nbsp;${itm.dictionaryname }</label></li>
              </c:forEach>
          </c:if>
      </ul></td>
    </tr>
    <%--<tr id="atr_student_grade">--%>
      <%--<td>年&nbsp;&nbsp;&nbsp;&nbsp;级：</td>--%>
      <%--<td><ul class="public_list2">--%>
        <%--<c:if test="${empty gradeList }">--%>
   							<%--<li>没有发现年级信息!</li>--%>
   						<%--</c:if>--%>
   						<%--<c:if test="${!empty gradeList }">--%>
   							<%--<c:forEach var="g" items="${gradeList }" varStatus="status">--%>
   								<%--<li><input type="checkbox"  value="${g.gradevalue }" name="ack_grade"/>${g.gradename }</li>--%>
   							<%--</c:forEach>--%>
   						<%--</c:if>--%>
      <%--</ul></td>--%>
    <%--</tr>--%>
  </table>
  </form>
  <p class="t_c t_c"><a class="an_public1"  href="javascript:onSubmitdoAdd()">发布</a></p>
</div>
      </div>

<!-- 修改层 -->
		<div class="home_tz_float" style="display: none" id="updDiv">
            <div class="public_windows">
                <h3><a href="javascript:closeModel('updDiv','hide');isupdate=false;pageGo('p1')"  title="关闭"></a>通知公告</h3>
  <form id="updform" name="updform">
      <table border="0" cellpadding="0" cellspacing="0"  class="public_tab1 public_input">
          <%--<tr>--%>
          <%--<td>类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>--%>
          <%--<td><select id="atype" name="atype"--%>
          <%--onchange="atypeSelect('atdNoticeUsefullife')">--%>
          <%--<option value="-1">--%>
          <%--==请选择==--%>
          <%--</option>--%>
          <%--<c:forEach items="${typeList}" var="itm">--%>
          <%--<option value="${itm.dictionaryvalue }">--%>
          <%--${itm.dictionaryname }--%>
          <%--</option>--%>
          <%--</c:forEach></select>--%>
          <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
          <%--<input type="checkbox" id="aisTop" name="aisTop" value="0" />--%>
          <%--是否置顶&nbsp;&nbsp;						--%>
          <%--</td>--%>
          <%--</tr>--%>
          <tr>
              <th><span class="ico06"></span>标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
              <td>
                  <input type="text" name="utitle"  id="utitle" class="w500" />
              </td>
          </tr>
          <tr>
              <th>&nbsp;&nbsp;设&nbsp;&nbsp;&nbsp;&nbsp;置：</th>
              <td><p>
                  <input type="checkbox" name="uistime"  onclick="p_uyxsj.style.display=this.checked?'block':'none'" id="uistime" />
                  <label for="uistime">有效时间</label>&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="checkbox" onclick="btljOperate(this.checked,u_ubtlj)"  id="utitleLinkck" name="utitleLinkck"/>
                  <label for="utitleLinkck">标题链接</label>&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="checkbox" id="uisTop" name="uisTop" value="0" />
                  <label for="uisTop">置顶</label></p>
                  <p id="p_uyxsj" style="display:none">有效时间：
                      <input type="text" class="w200" id="ubeginTime" name="ubeginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text" />
                      &mdash;
                      <input type="text" class="w200" id="uendTime" name="uendTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text"/>
                  </p>
                  <p id="u_ubtlj" style="display:none">链接地址：
                      <input name="utitlelink" id="utitlelink" type="text" class="w430" />
                  </p></td>
          </tr>

          <tr id="utrContent">
              <th><span class="ico06"></span>内&nbsp;&nbsp;&nbsp;&nbsp;容：</th>
              <td id="utdContent"><textarea style="width:480px;height:200px" id="ucontent" name="ucontent"/></textarea>
          </tr>
          <tr>
              <th><span class="ico06"></span>发送对象：</th>
              <td><ul class="public_list2">
                  <c:if test="${!empty roleDicList}">
                      <c:forEach items="${roleDicList}" var="itm">
                          <li><input  type="checkbox" value="${itm.dictionaryvalue }" id="uroleId${itm.dictionaryvalue}"
                                     name="uroleId" /><label for="uroleId${itm.dictionaryvalue}">&nbsp;${itm.dictionaryname }</label></li>
                      </c:forEach>
                  </c:if>
              </ul></td>
          </tr>
          <%--<tr id="atr_student_grade">--%>
          <%--<td>年&nbsp;&nbsp;&nbsp;&nbsp;级：</td>--%>
          <%--<td><ul class="public_list2">--%>
          <%--<c:if test="${empty gradeList }">--%>
          <%--<li>没有发现年级信息!</li>--%>
          <%--</c:if>--%>
          <%--<c:if test="${!empty gradeList }">--%>
          <%--<c:forEach var="g" items="${gradeList }" varStatus="status">--%>
          <%--<li><input type="checkbox"  value="${g.gradevalue }" name="ack_grade"/>${g.gradename }</li>--%>
          <%--</c:forEach>--%>
          <%--</c:if>--%>
          <%--</ul></td>--%>
          <%--</tr>--%>
      </table>
  </form>
  <p class="t_c"><a class="an_public1" onclick="onSubmitdoUpd();isupdate=false;"  href="javascript:;">发布</a></p>
</div>
  </body>
</html>