<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@include file="/util/common.jsp" %>
<%

    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    String username = user.getUsername();
    //String pass=request.getAttribute("pass").toString();
    String pass=user.getPassword();
    request.setAttribute("pageType","index" );
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="<%=basePath %>js/setupwizard.js"></script>
<script src="<%=basePath %>js/common/ajaxfileupload.js"> </script>
<script src="<%=basePath %>js/common/jquery.form.pack.2.24.js"></script>
<script src="<%=basePath %>js/common/jquery.imgareaselect.js"></script>
<link  rel="stylesheet" type="text/css" href="css/imgareaselect-default.css"/>
<title>数字化校园</title>
</head>
<body>

<%@include  file="/util/head.jsp" %>

<div class="home_layout">
<div class="home_layoutR">
    <!-- 通知公告 -->
    <h1><a href="notice?m=tomore&noticetype=<%=UtilTool._NOTICE_TYPE[1]%>" target="_blank" title="more" class="ico_more"></a><span class="ico09"></span>通知公告<a id="a_adNotice" style="display:none" href="notice?m=list" target="_blank" class="ico36" title="添加公告"></a></h1>
    <ul class="one" style="height:130px;">
        <c:if test="${!empty notices}">
            <c:forEach items="${notices}" var="n">
                <li>
                    <c:if test="${!empty n.titlelink}">
                        <a href="${n.titlelink}" target="_blank">${n.noticetitle15String}</a>
                    </c:if>
                    <c:if test="${empty n.titlelink}">
                        <a href="notice?m=detail&ref=${n.ref }" target="_blank">${n.noticetitle15String}</a>
                    </c:if>
                </li>
            </c:forEach>
        </c:if>

        <c:if test="${empty  notices}">
            <li>暂无!</li>
        </c:if>
    </ul>
    <!-- 我的组织，各种 -->
    <h1><span class="ico10"></span>我的组织</h1>
    <%if(!isStudent){ %>
    <c:if test="${!empty deptType}">
        <c:forEach items="${deptType}" var="dt">
            <h2 group="group${dt.deptid }" id="p_dept_type_${dt.deptid }">${dt.deptname} 同事
                <input type="hidden" value="${dt.deptid }"/>
            </h2>
            <ul class="two" group="group${dt.deptid }" id="ul_dept_${dt.deptid }">
            </ul>
            <form name="page${dt.deptid}form">
                <div class="nextpage" id="page${dt.deptid }address" style="display:none"></div>
                <!-- <div class="nextpage"><span><a href="1"><b class="before"></b></a></span>&nbsp;1/4&nbsp;<span><a href="1"><b class="after"></b></a></span></div> -->
            </form>
            <!-- 页码 -->
            <div class="nextpage" group="group${dt.deptid }">
               <span><a href="javascript:;" onclick="p${dt.deptid}.pagePre()" ><b class="before"></b></a>
               </span>&nbsp;<font id="sp_currentstate${dt.deptid}">1/1</font>&nbsp;
                <span><a href="javascript:;" onclick="p${dt.deptid}.pageNext()" ><b class="after"></b></a></span></div>
        </c:forEach>
    </c:if>
    <%}else{%>
    <c:if test="${!empty teachClass}">
        <c:forEach items="${teachClass}" var="teacls">
            <h2 group="group${teacls.classid}" id="p_dept_type_${teacls.classid}">${teacls.classgrade }${teacls.classname } 同学
                <input type="hidden" value="${teacls.classid }"/>
            </h2>
            <ul class="two" group="group_stu${teacls.classid}" id="ul_stu_${teacls.classid}">
            </ul>
            <form name="page${teacls.classid}form">
                <div class="nextpage" id="page${teacls.classid}address" style="display:none"></div>
                <!-- <div class="nextpage"><span><a href="1"><b class="before"></b></a></span>&nbsp;1/4&nbsp;<span><a href="1"><b class="after"></b></a></span></div> -->
            </form>
            <!-- 页码 -->
            <div class="nextpage" group="group${teacls.classid}">
               <span><a href="javascript:;" onclick="p${teacls.classid}.pagePre()" ><b class="before"></b></a>
               </span>&nbsp;<font id="sp_currentstate${teacls.classid}">1/1</font>&nbsp;
                <span><a href="javascript:;" onclick="p${teacls.classid}.pageNext()" ><b class="after"></b></a></span></div>
        </c:forEach>
    </c:if>
    <%}%>

    <!-- 授课班级，所在班级 -->
    <!-- <h2><%if(!isStudent){ %>
   	授课班级
   <%}else{%>所在班级<%} %> </h2>-->
    <!--<ul class="two">
       <c:if test="${!empty teachClass}">
  		<c:forEach items="${teachClass}" var="teacls">
  			<li>${teacls.classgrade }${teacls.classname }</li>
  		</c:forEach>
  	</c:if>
  	 <c:if test="${empty teachClass}">
  	 <li>暂无</li>-->
    </c:if>
    <%/* <li><img src="images/pic02_140226.jpg" width="38" height="38" /><br />王小红</li>
      <li><img src="images/pic02_140226.jpg" width="38" height="38" /><br />王小红</li>
       */%>
    <!--</ul>-->
    <%/*<div class="nextpage"><span><b class="before"></b></span>&nbsp;1/1&nbsp;<span><a href="1"><b class="after"></b></a></span></div>*/ %>
</div>
<!-- 左侧菜单 -->
<%@include file="/util/navigation.jsp"%>

<div class="home_layoutC">
<div class="home_info">
    <div class="home_infoR">
        <p class="f_right"><a href="javascript:showEditUser();" class="ico11" title="编辑"></a></p>
        <!-- 真实姓名，角色 -->
        <p class="one"> <%=u.getRealname()!=null
                &&u.getRealname().trim().length()>0?
                u.getRealname():u.getUsername()%>
            <%if(isStudent){ %>
            同学
            <%}else if(isTeacher){ %>
            老师
            <%} %>
            &nbsp;&nbsp;您好</p>
        <p><strong>您的角色：</strong><span id="sp_role">
        <c:if test="${!empty sessionScope.currentUserRoleList}">
            <c:forEach items="${sessionScope.currentUserRoleList}" var="cuur">
                ${cuur.rolename }&nbsp;&nbsp;
            </c:forEach>
        </c:if>
        </span></p>
        <p>
            <%if(!isStudent){ %>
            <strong>授课班级： </strong>
            <%}else{%><strong>所在班级： </strong><%} %>

          <span id="ul_teacls" style="width:330px">
          <c:if test="${empty teachClass}">
              <span>暂无</span>
          </c:if>
      </span>
        </p>
        <c:if test="${!empty teachClass}">
            <script type="text/javascript">
                <c:forEach items="${teachClass}" var="teacls">
                var searchSpan="sp_${teacls.classid}";
                var h='<font id="sp_${teacls.classid }">${teacls.classgrade }${teacls.classname }&nbsp;&nbsp;</font>';
                if($("#"+searchSpan).length<1)
                    $("#ul_teacls").append(h);
                </c:forEach>
            </script>
        </c:if>
    </div>
    <!-- 头像 -->
    <p class="home_infoL">
        <%if(u.getHeadimage()!=null&&u.getHeadimage().length()>0){ %>
        <img src="<%=u.getHeadimage() %>" width="135" height="135" />
        <%}else{%>
        <img src="images/pic01_121214.png" width="135" height="135" />
        <%} %>
    </p>
</div>

<div class="home_ywxg">
    <h1>与我相关</h1>
    <!--教学任务-->
    <c:if test="${!empty stuTkList}">
        <div class="index_me_content">
            <h2><span class="ico07"></span>任务<strong>${fn:length(stuTkList)}</strong></h2>
            <ul>
                <c:forEach items="${stuTkList}" var="stk">
                    <li>在${stk.COURSE_NAME}专题中，你尚有<span style="color:red">${stk.TKCOUNT}</span>个任务未完成</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <!--<c:if test="${!empty activityList}">
      	<div class="index_me_content">
      	 <h2><span class="ico07"></span>新活动<strong><a href="activity?m=list" target="_blank">${fn:length(activityList)}</a></strong></h2>
          	<ul>
	            <c:forEach items="${activityList}" var="activity">
	            <li><a href="activity?m=todetail&ref=${activity.ref }" target="_blank">${activity.atname }</a></li>
	            </c:forEach>
         	 </ul>
        </div>
      </c:if>-->
    <c:if test="${!empty tzMSGList}">
        <div  class="index_me_content">
            <h2><span class="ico08"></span>通&nbsp;&nbsp;知<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.TONGZHI.getValue() %>&msgname=通知" target="_blank">${tzMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${tzMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <c:if test="${!empty msgNoticeList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>公告消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.NOTICE.getValue() %>&msgname=公告消息" target="_blank">${msgNoticeCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${shenqingMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty shenqingMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>申请消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.SHENQING.getValue() %>&msgname=申请消息" target="_blank">${shenqingMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${shenqingMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty shenheMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>审核消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.SHENHE.getValue() %>&msgname=审核消息" target="_blank">${shenheMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${shenheMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty baomingMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>报名消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.BAOMING.getValue() %>&msgname=报名消息" target="_blank">${baomingMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${baomingMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty luquMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>录取消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.LUQU.getValue() %>&msgname=录取消息" target="_blank">${luquMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${luquMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty fatieMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>发帖消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.FATIE.getValue() %>&msgname=发帖消息" target="_blank">${fatieMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${fatieMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty renmingMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>任命消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.RENMING.getValue() %>&msgname=任命消息" target="_blank">${renmingMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${renmingMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty renwuMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>任务消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.RENWU.getValue() %>&msgname=任务消息" target="_blank">${renwuMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${renwuMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty chengjiMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>成绩消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.CHENGJI.getValue() %>&msgname=成绩消息" target="_blank">${chengjiMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${chengjiMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty huodongMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>活动消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.HUODONG.getValue() %>&msgname=活动消息" target="_blank">${huodongMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${huodongMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty userUpdateMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>信息修改<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.YONGHUXIUGAI.getValue() %>&msgname=信息修改消息" target="_blank">${userUpdateMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${userUpdateMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty tiaobanMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>调班消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.TIAOBAN.getValue() %>&msgname=调班" target="_blank">${tiaobanMSGCount }</a></strong></h2>
            <ul class="public_list">
                <c:forEach items="${tiaobanMSGList}" var="n">
                    <li>${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${!empty xftxMSGList}">
        <div class="index_me_content">
            <h2><span class="ico08"></span>校风消息<strong><a href="myinfouser?m=list&msgid=<%=UtilTool.MYINFO_MSG_TYPE.XFTX.getValue() %>&msgname=校风提醒" target="_blank">${xftxMSGCount }</a></strong></h2>
            <ul class="public_list" id="xf_ul">
                <c:forEach items="${xftxMSGList}" var="n">
                    <li >${n.dynamicString}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>
</div>
<div class="clear"></div>
</div>
<div id="dv_zhong" style="display:none">
    <div class="jcpt_initial" id="div1" style="display:block">
        <div class="next"><a href="javascript:submitUpdatePwd('div1','div2')" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb1">重设密码</li>
            <li>学年设置</li>
            <li>学科设置</li>
            <li>Logo设置</li>
            <li>完成 </li>
        </ul>
        <div class="jcpt_initialC public_input">
            <p><b>第一步：重设密码</b></p>
            <p><strong>用&nbsp;户&nbsp;名：</strong> <%=username %></p>
            <p><strong>重设密码：</strong>
                <input class="w160" onblur="validatePass1(this)" id="newPass" name="newPass" type="password" value=""/>
            </p>
            <div class="tishi" id="passDiv1" style="display: none">密码长度不能小于6个字符</div>
            <p><strong>再次确认：</strong>
                <input id="morePass" name="morePass" onblur="validatePass2(this)" type="password" value="" class="w160"/>
            </p>
            <div class="tishi" id="passDiv2" style="display: none">两次输入的密码不一致</div>
        </div>

    </div>

    <div class="jcpt_initial" style="display:none" id="div2">
        <div class="up"><a href="javascript:next('div2','div1')" title="上一步"></a></div>
        <div class="next"><a href="javascript:submitUpdateTerm('div2','div3')" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb2">重设密码</li>
            <li class="crumb1">学年设置</li>
            <li>学科设置</li>
            <li>Logo设置</li>
            <li>完成 </li>
        </ul>
        <div class="jcpt_initialC public_input">
            <p><b>第二步：学年设置</b></p>
            <p><strong>学&nbsp;&nbsp;&nbsp;&nbsp;年：</strong> ${termList.CLASS_YEAR_NAME }<input type="hidden" id="termyear" value="${termList.CLASS_YEAR_VALUE }"/></p>
            <p><strong>第一学期：</strong>
                <input onblur="validateBtime1()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="beginTime1"  name="beginTime1" type="text" class="w80" value="${termList.BTIME1 }" />
                -<input type="hidden" id="termref1" value="${termList.REF1 }"/>
                <input onblur="validateEtime1()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="endTime1" id="endTime1"  type="text" class="w80" value="${termList.ETIME1 }"/>
            </p>
            <div class="tishi" id="termDiv1" style="display: none">时间不在当前学年</div>
            <p><strong>第二学期：</strong>
                <input onblur="validateBtime2()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="beginTime2"  name="beginTime2" type="text" class="w80"  value="${termList.BTIME2 }"/>
                -<input type="hidden" id="termref2" value="${termList.REF2 }"/>
                <input onblur="validateEtime2()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="endTime2" id="endTime2"  type="text" class="w80" value="${termList.ETIME2 }"/>
            </p>
            <div class="tishi" id="termDiv2" style="display: none">时间不能早于上一学期的时间<br>
            </div>
        </div>
    </div>

    <div class="jcpt_initial" style="display:none" id="div3">
        <div class="up"><a href="javascript:next('div3','div2')" title="上一步"></a></div>
        <div class="next"><a href="javascript:submitSubject('div3','div4')" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb2">重设密码</li>
            <li class="crumb2">学年设置</li>
            <li class="crumb1">学科设置</li>
            <li>Logo设置</li>
            <li>完成 </li>
        </ul>
        <div class="jcpt_initialC public_input">
            <h3>第三步：学科设置</h3>
            <ul id="subject">
                <c:if test="${!empty subList}">
                    <c:forEach var="sub" items="${subList}" varStatus="idx">
                        <c:if test="${sub.subjecttype==1}">
                            <li id="${idx.index+1}"><input name="textfield12" type="text" disabled value="${sub.subjectname }" /></li>
                        </c:if>

                    </c:forEach>
                    <li id="li10"><input id="add10" name="textfield12"  type="text" value="音乐" /><a href="javascript:delSubject('li10')"><span title="删除"></span></a></li>
                    <li id="li11"><input id="add11" name="textfield12"  type="text" value="体育" /><a href="javascript:delSubject('li11')"><span title="删除"></span></a></li>
                    <li id="li12"><input id="add12" name="textfield12"  type="text" value="美术" /><a href="javascript:delSubject('li12')"><span title="删除"></span></a></li>
                    <li id="li13"><input id="add13" name="textfield12"  type="text" value="信息技术" /><a href="javascript:delSubject('li13')"><span title="删除"></span></a></li>
                    <li id="li14"><input id="add14" name="textfield12"  type="text" value="通用技术" /><a href="javascript:delSubject('li14')"><span title="删除"></span></a></li>
                </c:if>
            </ul>
            <div class="t_c"><a href="javascript:addli();"  class="an_public1">新建学科</a></div>
        </div>
    </div>

    <div class="jcpt_initial" style="display:none" id="div4">
        <div class="up"><a href="javascript:next('div4','div3')" title="上一步"></a></div>
        <div class="next"><a href="javascript:next('div4','div5');imgareaselectObj.setOptions({remove:true,hide:true});" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb2">重设密码</li>
            <li class="crumb2">学年设置</li>
            <li class="crumb2">学科设置</li>
            <li class="crumb1">Logo设置</li>
            <li>完成</li>
        </ul>
        <div class="jcpt_initial_logo public_input">
            <p class="font_strong">第四步：设置本校数字校园logo</p>
            <p><input type="file" id="upload" name="upload" class="w320">&nbsp;<a href="javascript:uploadImg();"  class="an_lightblue">上传</a></p>
            <div id="cuthear_div" style="display:none">
                <input type="hidden" name="src" value="" />
                <input type="hidden" name="x1" value="" />
                <input type="hidden" name="y1" value="" />
                <input type="hidden" name="x2" value=""  />
                <input type="hidden" name="y2" value="" />
                <div class="top" width="352" height="127"   id="myPreview" ><img id="myimage" width="352" height="127" ></div>
                <p class="f_right"><a href="javascript:subheadcut();"  class="an_public1">保 存</a><br/>
                    <b>253&times;64</b></p>


                <div class="bottom"   id="preview" style="width: 253px; height: 64px; overflow: hidden;">
                    <img id="previewimg" name="myimage"  width="253" height="64">
                </div>

            </div>
        </div>
    </div>

    <div class="jcpt_initial" style="display:none" id="div5">
        <div class="up"><a href="javascript:next('div5','div4')" title="上一步"></a></div>
        <ul class="nav">
            <li class="crumb2">重设密码</li>
            <li class="crumb2">学年设置</li>
            <li class="crumb2">学科设置</li>
            <li class="crumb2">Logo设置</li>
            <li class="crumb1">完成</li>
        </ul>
        <div class="jcpt_initialC public_input">
            <pre>恭喜您已完成数字化校园平台的初始化设置！<br>开始使用吧！</pre>
            <div class="t_c p_t_30"><a href="javascript:shutdownpop('div5')" class="jcpt_initial_an" title="进入数字化校园平台"></a></div>
        </div>
    </div>
</div>


<div class="home_grxx_float public_input" id="editUserDiv" style="display: none">

    <div class="public_windows">
        <h3><a href="javascript:closeEditUser();"  title="关闭"></a>修改信息</h3>
        <h4>修改头像</h4>
        <div id="headcuthear_div">
            <input type="hidden" name="headsrc" value="" />
            <input type="hidden" name="headx1" value="" />
            <input type="hidden" name="heady1" value="" />
            <input type="hidden" name="headx2" value=""  />
            <input type="hidden" name="heady2" value="" />
            <div class="touxiang">
                <p>
                    <input type="file" name="headupload" id="headupload" class="w240">
                    &nbsp;<a href="javascript:uploadHeadImg();"  class="an_public2">上传</a></p>

                <div class="right">
                    <p><strong>预览效果：</strong>135&times;135</p>
                    <div id="headpreview" style="width: 135px; height:135px; overflow: hidden;">
                        <p class="pic">
                            <%--<img id="headpreviewimg" name="headmyimage" src="">--%>
                            <%if(u.getHeadimage()!=null&&u.getHeadimage().length()>0){ %>
                            <img id="headpreviewimg" name="headmyimage" src="<%=u.getHeadimage() %>" width="135" height="135" />
                            <%}else{%>
                            <img id="headpreviewimg" name="headmyimage" src="images/pic01_121214.png" width="135" height="135" />
                            <%} %>
                        </p>
                    </div>
                    <p><a href="javascript:submitHeadCut();"  class="an_public1">保 存</a><a href="javascript:closeEditUser();"  class="an_public1">取 消</a></p>
                </div>
                <div class="left"  id="headmyPreview"><div id="beijing"><table border="0" style="padding:0 0 0 0;margin: 0 0 0 0;"><tr><td width="300px" height="300px" align="center" valign="middle"><img style="vertical-align: middle" id="headmyimage" src="" ></td></tr></table></div> </div>
            </div>
        </div>
        <h4>修改密码</h4>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1">
            <input type="hidden" value="<%=pass%>" id="oldpass"/>
            <input type="hidden" value="" id="oldname"/>
            <caption>
                <a href="javascript:;" onclick="changeOption(this,1)" class="crumb">校园账号</a>
                <%if(u.getEttuserid()!=null&&u.getDcschoolid()!=null){%>
                    ｜<a href="javascript:;" onclick="changeOption(this,2)" >云账号</a>
                <%}%>
            </caption>
            <tr>
                <th>用&nbsp;户&nbsp;名：</th>
                <td><span id="td_username"><%=username%></span>
                    <span style="" class="font-red" id="userNameMsg"></span>
                </td>
            </tr>
            <tr>
                <th>旧&nbsp;密&nbsp;码：</th>
                <td><input onblur="validateOldPass(this)" name="textfield13" type="password" id="oldHeadPass"/>
                    <a href="javascript:showPwd('oldHeadPass')" class="ico92" title="查看密码"></a><span class="font-gray">6-12字符</span>&nbsp;&nbsp;
                    <span style="" class="font-red" id="oldPassMsg"></span></td>
            </tr>
            <tr>
                <th>新&nbsp;密&nbsp;码：</th>
                <td><input onblur="validateNewPass(this)" name="textfield12" type="password" id="newHeadPass"/>
                    <a href="javascript:showPwd('newHeadPass')" class="ico92" title="查看密码"></a><span class="font-gray">6-12字符</span>&nbsp;&nbsp;
                    <span style="" class="font-red" id="newPassMsg"></span></td>
            </tr>
          <!--  <tr>
                <th>再次确认：</th>
                <td><input onblur="validateMorePass(this)" name="textfield4" type="password" id="moreHeadPass"/>
                    <a href="javascript:showPwd('moreHeadPass')" class="ico92" title="查看密码"></a><span class="font-gray">6-12字符</span>&nbsp;&nbsp;
                    <span style="display: none" class="font-red" id="morePassMsg">两次密码不一致</span></td>
            </tr> -->
            <tr>
                <th>&nbsp;</th>
                <td><a href="javascript:updatePass();"  class="an_public1">修改</a><a href="javascript:closeEditUser();"  class="an_public1">取消</a></td>
            </tr>
        </table>
    </div>
</div>
<input type="hidden" id="pwd_type" value="1"/>
<script type="text/javascript">
    <%if(!isStudent){%>
    <c:if test="${empty teachClass}">
        $("#sp_role").html($("#sp_role").html().replace("任课老师&nbsp;&nbsp;",""));
    </c:if>
    <%}%>

    $(function(){
        $("input[type='password']").focus(function(){
            $(this).get(0).type='password';
        });
    });

    function showPwd(divid){
        var dvObj=$("#"+divid);
        if(typeof  dvObj=='undefined')return;
        $(dvObj).get(0).type="text";
    }

    function changeOption(obj,type){
        $("#pwd_type").val(type);
        $(obj).addClass("crumb");
        $(obj).siblings("a").removeClass("crumb");
        $("#oldHeadPass").val('');
        $("#newHeadPass").val('');

        $("#userNameMsg").html('');
        $("#oldPassMsg").html('');
        $("#newPassMsg").html('');

        if(type==2){
            $("#td_username").html('<input type="text" id="txt_username" onblur="checkEttUserName(this)"/>')
            //获取云帐号
            loadEttUserName();
        }else
            $("#td_username").html('<%=username%>');
    }

    function checkEttUserName(obj,issub){
        var username=obj.value;
        if(username.Trim().length<1){
            $("#userNameMsg").html('请输入用户名!');
        }else{
            $.ajax({
                url:"tpuser?checkEttUserName",
                type:"post",
                data:{userName:username.Trim()},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },
                success:function(json){
                    if(json.type=='error'){
                        if(json.objList[0]!=null&&json.objList[0].toString().length>0){
                            $("#userNameMsg").html(json.objList[0]);
                        }
                    }else{
                        $("#userNameMsg").html('');
                        if(typeof issub!='undefined'&&issub){
                            doUpdPwd();
                        }
                    }

                }
            });
        }
    }


    function doUpdPwd(){
        var param={};
        param.oldUserName=$("#oldname").val().Trim();
        param.newUserName=$("#txt_username").val().Trim();
        param.oldPwd=$("#oldHeadPass").val().Trim();
        param.newPwd=$("#newHeadPass").val().Trim();

        $.ajax({
            url:'tpuser?modifyEttUser',
            dataType:'json',
            type:'POST',
            data:param,
            cache: false,
            error:function(){
                alert('系统未响应!');
            },success:function(rps){
                if(rps.type=='error'){
                    if(rps.objList[0]!=null&&rps.objList[0].toString().length>0){
                       alert(rps.objList[0]);
                    }
                }else{
                    alert("修改成功!");
                }
            }
        });
    }


    function loadEttUserName(){
        $.ajax({
            url:"tpuser?loadEttUserName",
            type:"post",
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },
            success:function(json){
                if(json.objList[0]!=null&&json.objList[0].toString().length>0){
                    $("#txt_username").val(json.objList[0]);
                    $("#oldname").val(json.objList[0]);
                }

                <%if(u.getStuname()!=null&&u.getStuname().length()>0){%>
                    $("#txt_username").attr("disabled",false);
                <%}else {%>
                    $("#txt_username").attr("disabled",true);
                <%}%>
            }
        });
    }


</script>

<!-- 设置用户名-->

<div id="dv_user_name" style="display:none;">
    <div class="jcpt_initial" id="div_username" style="display:block">
        <div class="next"><a href="javascript:setUserName('username','div_username','div_email')" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb1">设置用户名</li>
            <li>设置邮箱</li>
            <li>完成 </li>
        </ul>
        <div class="jcpt_initialC public_input">
            <p><b>第一步：设置用户名</b></p>
            <p><strong>用&nbsp;户&nbsp;名：</strong>
                <input id="username" name="username" onblur="validateUsername('username')" type="text" value="" class="w160"/>
            </p>
            <div class="tishi" id="username_err" style=""></div>
        </div>

    </div>

    <div class="jcpt_initial" style="display:none;" id="div_email">
        <div class="up"><a href="javascript:next('div_email','div_username')" title="上一步"></a></div>
        <div class="next"><a href="javascript:setUserName('email','div_email','div_complete')" title="下一步"></a></div>
        <ul class="nav">
            <li class="crumb2" >设置用户名</li>
            <li class="crumb1">设置邮箱</li>
            <li>完成 </li>
        </ul>
        <div class="jcpt_initialC public_input">
            <p><b>第二步：设置邮箱</b></p>
            <p><strong>邮&nbsp;&nbsp;&nbsp;箱：</strong>
                <input id="email" name="email" onblur="validateUsername('email')" type="text" value="" class="w160"/>
            </p>
            <div class="tishi" id="email_err" style=""></div>
        </div>
    </div>

    <div class="jcpt_initial" style="display:none" id="div_complete">
        <div class="up"><a href="javascript:next('div_complete','div_email')" title="上一步"></a></div>
        <ul class="nav">
            <li class="crumb2">设置用户名</li>
            <li class="crumb2">设置邮箱</li>
            <li class="crumb1">完成</li>
        </ul>
        <div class="jcpt_initialC public_input">
            <pre>恭喜您，已完成初始化设置！</pre>
            <div class="t_c p_t_30"><a href="javascript:shutdownpop('div_complete')" class="jcpt_initial_an" title="进入数字化校园平台"></a></div>
        </div>
    </div>
</div>

<%@include file="/util/foot.jsp"%>
</body>

<script type="text/javascript">
//设置logo设置用的三个变量
var basePath = "<%=basePath %>";
var imgwidth = 0;
var imgheight = 0;
var imgareaselectObj;
$(function(){
    if($("ul li a[href='notice?m=list']").length>0){
        $("ul li a[href='notice?m=list']").parent().remove();
        $("#a_adNotice").show();
    }
    <c:if test="${empty initObj}">
    showSetupWizard("dv_zhong");
    </c:if>
    <c:if test="${!empty initObj and initObj.success==1}">
    showSetupWizard("dv_zhong");
    $("#div1").hide();
    next("div"+${initObj.currentStep},"div"+${initObj.currentStep+1})
    </c:if>

    <c:if test="${!empty ismodify and ismodify==0}">
    //showSetupWizard('dv_user_name')
    </c:if>
    //查询多个部门下的组织
    <%if(!isStudent){ %>    //如果是教师，则加载部门，
    loadDeptByType();
    <%}else{%>              //如果是学生，则加载班级学生
    loadStudentByClsid();
    <%}%>
});
/**
 *查询多个部门下的组织
 */
function loadStudentByClsid(){
    <c:if test="${!empty teachClass}">
    <c:forEach items="${teachClass}" var="tcs">
    var tmpStr="p${tcs.classid} = new PageControl({";
    tmpStr+="post_url:'cls?m=getClsUserByClsId&classid=${tcs.classid}',";
    tmpStr+="page_id:'page${tcs.classid}',";
    tmpStr+="page_control_name:'p${tcs.classid}',";
    tmpStr+="post_form:document.page${tcs.classid}form,";
    tmpStr+="gender_address_id:'page${tcs.classid}address',";
    tmpStr+="http_operate_handler:clsListReturn,";
    tmpStr+="isShowController:false,";
    tmpStr+="return_type:'json',page_no:1,page_size:9,rectotal:0,pagetotal:1,";
    tmpStr+="operate_id:'ul_dept_${tcs.classid}'})";
    eval("("+tmpStr+")");
    pageGo("p${tcs.classid}");
    </c:forEach>
    </c:if>
}

function clsListReturn(rps){
    if(rps.type=='error'){
        if (typeof(eval("(p"+rps.objList[1]+")")) != "undefined" && typeof(eval("(p"+rps.objList[1]+")")) == "object") {
            // 设置空间不可用
            eval("(p"+rps.objList[1]+").setPagetotal(1)");
            eval("(p"+rps.objList[1]+").setRectotal(0)");
            eval("(p"+rps.objList[1]+").Refresh()");
            alert(rps.msg);return;
        }
    }else{
        var htm='<li>暂无</li>';
        if(rps.objList[0]!=null&&rps.objList[0].length>0){
            htm='';
            $.each(rps.objList[0],function(idx,itm){
                var headimage='images/pic02_121214.png';
                if(typeof(itm.headimage)!="undefined"&&itm.headimage.Trim().length>0)
                    headimage=itm.headimage;
                htm+='<li><img width="38" height="38" src="'+headimage+'"  onerror="this.src=\'images/pic02_121214.png\'"/><br/>'+itm.realname+'</li>';
                //   zuzhiname=itm.deptname;
            });
        }else{
            $("h2[group='group_stu"+rps.objList[1]+"']").hide();
            $("ul[group='group_stu"+rps.objList[1]+"']").hide();
            $("div[group='group_stu"+rps.objList[1]+"']").hide();
        }
        $("#ul_stu_"+rps.objList[1]).html(htm);
        // $("#p_dept_type_"+rps.objList[1]+" span").html(zuzhiname);
        //翻页信息
        if (typeof(eval("(p"+rps.objList[1]+")")) != "undefined" && typeof(eval("(p"+rps.objList[1]+")")) == "object") {
            eval("(p"+rps.objList[1]+".setPagetotal("+rps.presult.pageTotal+"))");
            eval("(p"+rps.objList[1]+".setRectotal("+rps.presult.recTotal+"))");
            eval("(p"+rps.objList[1]+".setPageSize("+rps.presult.pageSize+"))");
            eval("(p"+rps.objList[1]+".setPageNo("+rps.presult.pageNo+"))");
            eval("(p"+rps.objList[1]+").Refresh()");
            $('font[id="sp_currentstate'+rps.objList[1]+'"]').html($("form[name='page"+rps.objList[1]+"form'] span[id='bilipage"+rps.objList[1]+"']").html());
            //$('span[id="sp_sumState'+rps.objList[1]+'"]').html($("form[name='page"+rps.objList[1]+"form'] span[id='bilipage"+rps.objList[1]+"']").html());
        }
    }
}


/**
 pop.show();
 }
 /**
 *查询多个部门下的组织
 */
function loadDeptByType(){
    <c:if test="${!empty deptType}">
    <c:forEach items="${deptType}" var="dt1">
    var tmpStr="p${dt1.deptid} = new PageControl({";
    tmpStr+="post_url:'deptuser?m=indexGetListPage&depttypeid=${dt1.deptid}',";
    tmpStr+="page_id:'page${dt1.deptid}',";
    tmpStr+="page_control_name:'p${dt1.deptid}',";
    tmpStr+="post_form:document.page${dt1.deptid}form,";
    tmpStr+="gender_address_id:'page${dt1.deptid}address',";
    tmpStr+="http_operate_handler:deptListReturn,";
    tmpStr+="isShowController:false,";
    tmpStr+="return_type:'json',page_no:1,page_size:9,rectotal:0,pagetotal:1,";
    tmpStr+="operate_id:'ul_dept_${dt1.deptid}'})";
    eval("("+tmpStr+")");
    pageGo("p${dt1.deptid}");
    </c:forEach>
    </c:if>
}
/*返回*/
function deptListReturn(rps){
    if(rps.type=='error'){
        if (typeof(eval("(p"+rps.objList[1]+")")) != "undefined" && typeof(eval("(p"+rps.objList[1]+")")) == "object") {
            // 设置空间不可用
            eval("(p"+rps.objList[1]+").setPagetotal(1)");
            eval("(p"+rps.objList[1]+").setRectotal(0)");
            eval("(p"+rps.objList[1]+").Refresh()");
            alert(rps.msg);return;
        }
    }
    var zuzhiname='';
    var htm='<li>暂无</li>';
    if(rps.objList[0]!=null&&rps.objList[0].length>0){
        htm='';
        $.each(rps.objList[0],function(idx,itm){
            var headimage='images/pic02_121214.png';
            if(typeof(itm.headimage)!="undefined"&&itm.headimage.Trim().length>0)
                headimage=itm.headimage;
            htm+='<li><img width="38" height="38" src="'+headimage+'"  onerror="this.src=\'images/pic02_121214.png\'"/><br/>'+itm.realname+'</li>';
            zuzhiname=itm.deptname;
        });
    }else{
        $("h2[group='group"+rps.objList[1]+"']").hide();
        $("ul[group='group"+rps.objList[1]+"']").hide();
        $("div[group='group"+rps.objList[1]+"']").hide();
    }
    $("#ul_dept_"+rps.objList[1]).html(htm);
    $("#p_dept_type_"+rps.objList[1]+" span").html(zuzhiname);
    //翻页信息
    if (typeof(eval("(p"+rps.objList[1]+")")) != "undefined" && typeof(eval("(p"+rps.objList[1]+")")) == "object") {
        eval("(p"+rps.objList[1]+".setPagetotal("+rps.presult.pageTotal+"))");
        eval("(p"+rps.objList[1]+".setRectotal("+rps.presult.recTotal+"))");
        eval("(p"+rps.objList[1]+".setPageSize("+rps.presult.pageSize+"))");
        eval("(p"+rps.objList[1]+".setPageNo("+rps.presult.pageNo+"))");
        eval("(p"+rps.objList[1]+").Refresh()");
        $('font[id="sp_currentstate'+rps.objList[1]+'"]').html($("form[name='page"+rps.objList[1]+"form'] span[id='bilipage"+rps.objList[1]+"']").html());
        //$('span[id="sp_sumState'+rps.objList[1]+'"]').html($("form[name='page"+rps.objList[1]+"form'] span[id='bilipage"+rps.objList[1]+"']").html());
    }
}
/**
 *公告变换
 */
function noticeShowType(type){
    if(typeof(type)=="undefined"||type==1){
        $("#internetNotice_dv").hide();
        $("#li_notice_1").attr("class","crumb");
        $("#li_notice_2").removeAttr("class");
        $("#notice_dv").show();
    }else{
        $("#notice_dv").hide();
        $("#li_notice_2").attr("class","crumb");
        $("#li_notice_1").removeAttr("class");
        $("#internetNotice_dv").show();
    }
}
/*****************************编辑用户头像和修改密码**********************************/
function showEditUser(){
    showModel('editUserDiv');
}
var headimgwidth = 0;
var headimgheight = 0;
var headimgareaselectObj;
//显示上传后的图片，也就是要切割的图片
function headPreview(img, selection) {
    if (!selection.width || !selection.height)
        return;

    var scaleX = 135 / selection.width;
    var scaleY = 135 / selection.height;

    $('#headpreviewimg').css({
        width : Math.round(scaleX * headimgwidth),
        height : Math.round(scaleY * headimgheight),
        marginLeft : -Math.round(scaleX * selection.x1),
        marginTop : -Math.round(scaleY * selection.y1)
    });
    // alert('<%=basePath%>');
    $('input[name="headsrc"]').val(
            $(img).attr("src").replace(basePath,''));
    $('input[name="headx1"]').val(selection.x1);
    $('input[name="heady1"]').val(selection.y1);
    $('input[name="headx2"]').val(selection.x2);
    $('input[name="heady2"]').val(selection.y2);

}
//提交上传图片
function uploadHeadImg(){
    var t=document.getElementById('headupload');
    if(t.value.Trim().length<1){
        alert('您尚未选择图片，请选择！');
        return;
    }
    var lastname=t.value.Trim().substring(t.value.Trim().lastIndexOf('.')).toLowerCase();;
    if(lastname!='.jpg'&&lastname!='.png'&&lastname!='.gif'&&lastname!='.bmp'){
        alert('您选择的图片不正确，目前只支持jpg,gif,png,bmp图片!');
        return;
    }
    var filePath = $("#headupload").val();
    $.ajaxFileUpload({
        url:"user?m=saveheadsrcfile",
        fileElementId:'headupload',
        dataType: 'json',
        type:'POST',
        success: function (data, status)
        {
            if(typeof(data.error) != 'undefined')
            {
                if(data.error != '')
                {
                    alert(data.error);
                }else
                {
                    alert(data.msg);
                }
            }else{
                //初始化组件
                //showModel('cuthear_div',false);
                darray=data.success.split("|");
                $("#beijing").css({"background-color":"gray","width":303,"height":303});
                $("#headmyimage").attr("src",darray[0]);
                $("#headpreviewimg").attr("src",darray[0]);
                headimgwidth=darray[1];
                headimgheight=darray[2];
                //显示出来
                $("#headcurrent_photo").attr("src",darray[0]);
                $("#headcuthear_div").show('fast');
                headimgareaselectObj=$('#headmyimage').imgAreaSelect({
                    aspectRatio: '1:1',
                    handles: true,
                    instance:true,
                    fadeSpeed: 200,
                    onSelectChange: headPreview
                });

            }
        },
        error: function (data, status, e)
        {
            alert(e);
        }
    });

}
//提交切割图片

function submitHeadCut(){
    var src=$('input[name="headsrc"]').val();
    var x1=$('input[name="headx1"]').val();
    var y1=$('input[name="heady1"]').val();
    var x2=$('input[name="headx2"]').val();
    var y2=$('input[name="heady2"]').val();

    //参数是否正确
    if(src.length<1
            ||x1.Trim().length<1||isNaN(x1.Trim())
            ||x2.Trim().length<1||isNaN(x2.Trim())
            ||y1.Trim().length<1||isNaN(y1.Trim())
            ||y2.Trim().length<1||isNaN(y2.Trim())
            ){
        if(!confirm('请选择截图区域，默认将按全图比例缩放！'))
            return;

    }
    if(src.length<1){
        src=$("#headpreviewimg").attr("src").replace(basePath,'');
    }
    $.ajax({
        url:"user?m=docuthead",
        type:"post",
        dataType:'json',
        cache: false,
        data:{src:src,x1:x1,y1:y1,x2:x2,y2:y2},
        error:function(){
            alert('系统未响应，请稍候重试!');
        },
        success:function(json){
            if(json.type == "error"){
                alert(json.msg);
            }else{
                alert("设置成功");
                // closeModel('editUserDiv');
                $("#headcurrent_photo").attr("src",json.objList[0]);
                imgareaselectObj.setOptions({remove:true,hide:true});
            }
        }
    });
}
function updatePass(){
    var type=$("#pwd_type").val();
    var newPwd=$("#newHeadPass").get(0);
    var oldPwd=$("#oldHeadPass").get(0);
    if(!(validateNewPass(newPwd)&&validateOldPass(oldPwd)))
        return;
    var pass=$("#newHeadPass").val();

    if(type==1){
        $.ajax({
            url:'user?m=changepassword',
            dataType:'json',
            type:'POST',
            data:{new_password:pass
            },
            cache: false,
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                if(rps.type=='success'){
                    $("#oldpass").val($("#newHeadPass").val());
                    alert("修改成功!");
                    //$("#newHeadPass");
                }else
                    alert(rps.msg);
            }
        });
    }else{
        //修改云账号
        checkEttUserName($("#txt_username").get(0),true);
    }
}
function validateOldPass(obj){
    var type=$("#pwd_type").val();
    var pass=obj.value;
    if(type==1){
        var oldpass=$("#oldpass").val();
        if(pass!=oldpass){
            $("#oldPassMsg").html('输入的原密码不对');
            return false;
        }else{
            $("#oldPassMsg").html('');
        }
        if(pass.Trim().length<6||pass.Trim().length>12){
            $("#oldPassMsg").html('密码为6-12个字符');
            return false;
        }else
            $("#oldPassMsg").html('');
    }else{
        if(pass.Trim().length<6||pass.Trim().length>12){
            $("#oldPassMsg").html('密码为6-12个字符');
            return false;
        }else
            $("#oldPassMsg").html('');
    }
    return true;
}
function validateNewPass(obj){
    var pass = obj.value;
    if(pass.Trim().length<1){
        $("#newPassMsg").html("请输入密码");
        return false;
    }else{
        $("#newPassMsg").html('');
    }
    if(pass.Trim().length<6||pass.Trim().length>12){
        $("#newPassMsg").html("请输入6-12个字符的密码长度");
        return false;
    }else{
        $("#newPassMsg").html('');
    }
    return true;
}

function validateMorePass(obj){
    var pass = $("#newHeadPass").val();
    var pass2 = obj.value;
    if(pass2.Trim().length<1){
        $("#morePassMsg").html("请再次输入密码");
        return;
    }else{
        $("#morePassMsg").html('');
    }
    if(pass!=pass2){
        $("#morePassMsg").html("两次输入密码不一致，请重新输入");
        return;
    }else{
        $("#morePassMsg").html('');
    }
}
function closeEditUser(){
    closeModel('editUserDiv');
    //headimgareaselectObj.setOptions({remove:true,hide:true});
    this.reload();
}
</script>
</html>
