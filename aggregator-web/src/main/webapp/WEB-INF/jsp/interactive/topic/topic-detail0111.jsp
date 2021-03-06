<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="js/interactive/topic.js"></script>
        <script type="text/javascript" src="js/interactive/theme.js"></script>
        <script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
        <script type="text/javascript"  src="fancybox/jquery.fancybox-1.3.4.js"></script>
        <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css"/>
        <script type="text/javascript" src="ueditor/lyb/ett-lyb%200.1.js"></script>
		<script type="text/javascript">
            var topicid="${topic.topicid}";
            var state="${topic.status}";
            var ueditor;
            var newthemeid="${newthemeid}";
            var baseUrl="<%=basePath %>";

            var roleStr="${roleStr}";

            var quotecourseid="${topic.courseid}";
            var quotetopicid="${topic.topicid}";

            var pclsid="${param.clsid}";
            var pclstype="${param.type}";
            var culoginId="${sessionScope.CURRENT_USER.userid}";

            var isquote=${(empty param.quoteid1||param.quoteid1==0)?1:2};   //1：不是  2:是
            var edotpr_opt = {
                autoHeightEnabled:false,
                toolbars:[
                    ['fullscreen', 'source',  'undo', 'redo',
                        'bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'removeformat',
                        'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist',
                        'lineheight',
                        'horizontal','spechars',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify',
                        'link', 'unlink',
                        'insertvideo','insertimage', 'emotion', 'attachment',
                        'inserttable', 'deletetable', 'insertparagraphbeforetable',
                        'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells'
                        , 'splittorows', 'splittocols',
                        'fontfamily', 'fontsize'
                    ]
                ]
            };

            var p1;  //得到论题的情况
            $(function(){
                if(roleStr=='STUDENT'&&state==2){  //1:开启  2: 关闭 3: 锁定 4: 隐藏
                    alert("该论题已关闭，无法进行查看!");
                    window.close();return;
                }
                $(".header_ico1 a").attr("href",$(".header_ico1 a").attr("href"));
                if(topicid==null||topicid.Trim().length<1){
                    alert('错误，非法访问!参数异常!');return;
                }



                //加载分页控件
                //查询基本的不是引用的主题
                <c:if test="${empty param.quoteid1||param.quoteid1==0}">
                    p1=new PageControl({
                        post_url:'tptopictheme?m=getTopicZT&&selectType=-3',
                        page_id:'page1',
                        page_control_name:"p1",		//分页变量空间的对象名称
                        post_form:document.page1form,		//form
                        gender_address_id:'page1address',		//显示的区域
                        http_free_operate_handler:validateParamDetail,		//执行查询前操作的内容
                        http_operate_handler:topicPageReturnLoadTheme,	//执行成功后返回方法
                        return_type:'text',								//放回的值类型
                        page_no:1,					//当前的页数
                        page_size:10,				//当前页面显示的数量
                        rectotal:0,				//一共多少
                        pagetotal:1,
                        operate_id:"dv_mainTbl"
                    });
                 </c:if>
                //查询引用的主题
                <c:if test="${!empty param.quoteid1&&param.quoteid1==1}">
                    //教师查询
                    <c:if test="${!empty roleStr&&roleStr=='TEACHER'&&!empty param.quoteid1&&param.quoteid1!=0}">
                        p1=new PageControl({
                            post_url:'tptopictheme?m=getTopicZT&cloudstatus=3&selectType=-4&quoteid1=1',
                            page_id:'page1',
                            page_control_name:"p1",		//分页变量空间的对象名称
                            post_form:document.page1form,		//form
                            gender_address_id:'page1address',		//显示的区域
                            http_free_operate_handler:validateParamDetail_quote,		//执行查询前操作的内容
                            http_operate_handler:topicPageReturnLoadTheme,	//执行成功后返回方法
                            return_type:'text',								//放回的值类型
                            page_no:1,					//当前的页数
                            page_size:10,				//当前页面显示的数量
                            rectotal:0,				//一共多少
                            pagetotal:1,
                            operate_id:"dv_mainTbl"
                        });
                    </c:if>
                    //学生查询
                    <c:if test="${!empty roleStr&&roleStr=='STUDENT'&&!empty param.quoteid1&&param.quoteid1!=0}">
                    //加载分页控件  如果是学生，则显示所有的引用专题主题
                            p1=new PageControl({
                                post_url:'tptopictheme?m=getTopicZT&selectType=-4&quoteid1=1',
                                page_id:'page1',
                                page_control_name:"p1",		//分页变量空间的对象名称
                                post_form:document.page1form,		//form
                                gender_address_id:'page1address',		//显示的区域
                                http_free_operate_handler:validateParamDetail_quote,		//执行查询前操作的内容
                                http_operate_handler:topicPageReturnLoadTheme,	//执行成功后返回方法
                                return_type:'text',								//放回的值类型
                                page_no:1,					//当前的页数
                                page_size:10,				//当前页面显示的数量
                                rectotal:0,				//一共多少
                                pagetotal:1,
                                operate_id:"dv_mainTbl"
                            });
                    </c:if>
                </c:if>

                pageGo('p1');
                    fancyboxObj=$("#a_click").fancybox({
                        'onClosed':function(){
                            $("#dv_content").hide();
                        }
                    });


//                    tpueditor.ready(function(){
//
//                    });



            })

            /*显示创建主题层*/
            function showCreateDiv(dvid){
                $("#dv_content>div").hide();
                $("#dv_content #"+dvid).show();	//显示。
                <c:if test="${empty param.quoteid1||param.quoteid1==0}">
                try{
                    UE.getEditor("themecontent").destroy();
                }catch(e){}
                     $(".edui-default").remove();
                    $("#themecontent").css({'width':'800px','height':'400px'});
                    //加载富文本框
                    var eopt=edotpr_opt;
                    ueditor= new UE.ui.Editor(edotpr_opt)
                    ueditor.render("themecontent");
                    ueditor.setDataId(newthemeid);

                    $("#dv_content").show();
                    $("#a_click").click();
                </c:if>
                //生成UEditor
            }
            function headError(obj){
                obj.src='images/defaultheadsrc_big.jpg';obj.onerror=null;
            }

            function huitie(themeid,freplyid,freplyname){
                var display=$("#dv_doht"+themeid).css("display");
                if(display=='none'){
                    $("#txt_hf"+themeid).val('');
                    if(typeof(freplyid)!="undefined"){
                        $("#dv_doht"+themeid).attr("data-bind",freplyid+"|"+freplyname);
                        $("#hd_rt"+themeid).val(2);
                    }else{
                        $("#dv_doht"+themeid).attr("data-bind","");
                        $("#hd_rt"+themeid).val(1);
                    }
//                    var iframeLen=$("#dv_doht"+themeid+" .edui-editor-iframeholder").length;
//                    if(iframeLen<1){
                        new EttLyb({
                            addressid:'dv_txt'+themeid,
                            controlid:'lyb',
                            width:'745px',
                            height:'70px',
                            isHasImage:false,
                            readlly:function(){
                                //$("#dv_txt"+themeid).append($("#p_fb"+themeid).html());
                                //$("#dv_txt"+themeid).html($("#dv_txt"+themeid+" .edui-editor-toolbarbox").html());
                                $("#dv_ht_"+themeid).show();
                                $("#dv_doht"+themeid).show();
                                $("#p_fb"+themeid).show();
                                var txtareId=$("#dv_doht"+themeid+" textarea").attr("id");
                                UE.getEditor(txtareId).focus();
                                var h=$("#dv_ht_"+themeid).css("height");
                                if(h>500)
                                    location.href="#dv_doht"+themeid;

                            }
                        });
//                    }

                }else{
                    if(typeof(freplyid)!="undefined"){
                        var txtareId=$("#dv_doht"+themeid+" textarea").attr("id");
                        UE.getEditor(txtareId).destroy();
                        $("#txt_hf"+themeid).val('');
                        if(typeof(freplyid)!="undefined"){
                            $("#dv_doht"+themeid).attr("data-bind",freplyid+"|"+freplyname);
                            $("#hd_rt"+themeid).val(2);
                        }else{
                            $("#dv_doht"+themeid).attr("data-bind","");
                            $("#hd_rt"+themeid).val(1);
                        }
//                    var iframeLen=$("#dv_doht"+themeid+" .edui-editor-iframeholder").length;
//                    if(iframeLen<1){
                        new EttLyb({
                            addressid:'dv_txt'+themeid,
                            controlid:'lyb',
                            width:'745px',
                            height:'70px',
                            isHasImage:false,
                            readlly:function(){
                                //$("#dv_txt"+themeid).append($("#p_fb"+themeid).html());
                                //$("#dv_txt"+themeid).html($("#dv_txt"+themeid+" .edui-editor-toolbarbox").html());
                                $("#dv_ht_"+themeid).show();
                                $("#dv_doht"+themeid).show();
                                $("#p_fb"+themeid).show();
                                var txtareId=$("#dv_doht"+themeid+" textarea").attr("id");
                                UE.getEditor(txtareId).focus();
                                var h=$("#dv_ht_"+themeid).css("height");
                                if(parseFloat(h)>500)
                                    location.href="#dv_doht"+themeid;

                            }
                        });
//                    }
                    }else{
                        var length=$("#dv_hl"+themeid+" div[id*='dv_pl']").length;
                        if(length<1)
                            $("#dv_ht_"+themeid).hide();
                        $("#dv_doht"+themeid).hide();
                        var txtareId=$("#dv_doht"+themeid+" textarea").attr("id");
                        UE.getEditor(txtareId).destroy();
                        $("#dv_txt"+themeid).html('');
                    }
                }
            }
            /**
            *
            * @param themeid
            * @param ty 1：展开，2：收起
             */
            function sqzk(themeid,ty){
                if(typeof(themeid)=="undefined"){
                    alert('程序错误!');return;
                }
                if(typeof(ty)=="undefined")
                    ty=1;

                var currentPage=$("#hd_currentpage"+themeid).val();
                if(ty==1){
                    var total=parseInt($("#hd_zttotalnum"+themeid).val());
                    if(total>=currentPage*5)
                        currentPage=parseInt(currentPage)+1;
                    getReplyList(themeid+"",currentPage,5);
                }else if(ty==2){
                    if(currentPage<2){
                        currentPage=1;
                    }else
                        currentPage=parseInt(currentPage)-1;
                    $("#dv_hl"+themeid).html('');
                    getReplyList(themeid+"",1,currentPage*5);
                }

            }
		</script>
	</head>  
  <body>
  <div class="subpage_head">
      <span class="back">
        <c:if test="${empty param.quoteid1||param.quoteid1==0}">
              <c:if test="${!empty param.taskid}">
                  <%if(isStudent){%>
                  <a href="task?toStuTaskIndex&courseid=${topic.courseid}">返回</a>
                  <%}else{%>
                  <a href="task?toTaskList&courseid=${topic.courseid}">返回</a>
                  <%}%>
            </c:if>
            <c:if test="${empty param.taskid}">
                <a href="tptopic?m=index&courseid=${topic.courseid}">返回</a>
            </c:if>
        </c:if>
        <c:if test="${!empty param.quoteid1&&param.quoteid1!=0}">
            <a href="tptopic?m=viewTopic&topicid=${topic.topicid}">返回</a>
        </c:if>
    </span>
      <span class="ico55"></span><strong>
    <c:if test="${empty param.quoteid1||param.quoteid1==0}">
      论题详情
    </c:if>
    <c:if test="${!empty param.quoteid1&&param.quoteid1!=0}">
            参考主帖
    </c:if>
     </strong>
    <c:if test="${empty param.quoteid1||param.quoteid1==0}">
        <c:if test="${!empty roleStr&&roleStr!='STUDENT'}">
            <span class="font-black public_input">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分班级查看：
             <select name="select" onchange="pclsid=this.value.split('.')[0];pclstype=this.value.split('.')[1];pageGo('p1');">
                 <option value="t=1">全部</option>
                 <c:if test="${!empty tccList}">
                     <c:forEach items="${tccList}" var="tc">
                         <c:if test="${param.clsid==tc.CLASS_ID}">
                             <option value="${tc.CLASS_ID}.${tc.CLASSTYPE}" selected>${tc.CLASSES}</option>
                         </c:if>
                         <c:if test="${empty param.clsid||!empty param.clsid&&param.clsid!=tc.CLASS_ID}">
                             <option value="${tc.CLASS_ID}.${tc.CLASSTYPE}">${tc.CLASSES}</option>
                         </c:if>
                     </c:forEach>
                 </c:if>
             </select>
            </span>
        </c:if>
     </c:if>
  </div>

  <div class="content2">
      <c:if test="${empty param.quoteid1||param.quoteid1==0}">
          <div class="jxxt_zhuanti_hdkj_nr">
              <p class="f_right">主帖数：<span id="sp_zt_num">${topic.themecount }</span>&nbsp;&nbsp;&nbsp;回帖数：<span id="sp_pl_num">${topic.restorecount}</span></p>
              <p><strong>${topic.topictitle }</strong></p>
              <p>　　${topic.topiccontent }</p>
              <p>
            <c:if test="${!empty isHasCKZT&&isHasCKZT>0}">
                  <a href="tptopic?m=viewTopic&topicid=${topic.topicid}&quoteid1=1" class="font-darkblue f_right">参考主帖</a>
              </c:if>
                  <a href="javascript:;" onclick="showCreateDiv('dv_create')"  class="font-darkblue"><span class="ico36"></span>新建主帖</a></p>
          </div>
      </c:if>
      <div class="jxxt_zhuanti_hdkj_list" id="dv_mainTbl">
      </div>
      <form name="page1form" id="page1form" method="post">
          <div class="nextpage" id="page1address"></div>
      </form>
  </div>
    <div class="public_float jxxt_zhuanti_hdkj_float" id="dv_content" style="display: none">
<%--新建--%>
        <div id="dv_create" style="display:none">
            <p class="float_title"><strong>新建主帖</strong></p>
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
                <col class="w100"/>
                <col class="w800"/>
                <tr>
                    <th><span class="ico06"></span>标&nbsp;&nbsp;题：</th>
                    <td><input  name="themetitle" placeholder="请限制在40个字以内!" maxlength="40" id="themetitle" type="text" class="w350"  /></td>
                </tr>
                <tr>
                    <th><span class="ico06"></span>内&nbsp;&nbsp;容：</th>
                    <td><textarea name="themecontent" id="themecontent" style="height:400px; width: 800px"></textarea></td>
                </tr>
                <tr>
                    <th>&nbsp;</th>
                    <td><a href="javascript:;" onclick="doAddTheme()"  class="an_public1">提&nbsp;交</a></td>
                </tr>
            </table>
         </div>
        <%--编辑--%>
        <div id="div_update" style="display:none">
            <p class="float_title"><strong>编辑主帖</strong></p>
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
                <col class="w100"/>
                <col class="w800"/>
                <tr>
                    <th><span class="ico06"></span>标&nbsp;&nbsp;题：</th>
                    <td><input   name="update_title" id="update_title"  placeholder="请限制在40个字以内!" maxlength="40" type="text" class="w350"  /></td>
                </tr>
                <tr>
                    <th><span class="ico06"></span>内&nbsp;&nbsp;容：</th>
                    <td><textarea name="update_txt" id="update_txt"  style="height:400px; width: 800px"></textarea>
                        <input type="hidden" name="themeid" id="themeid"/>
                    </td>
                </tr>
                <tr>
                    <th>&nbsp;</th>
                    <td><a  href="javascript:;" onclick="updateThemeContent('div_update')"  class="an_public1">提&nbsp;交</a></td>
                </tr>
            </table>
        </div>

<%--批注--%>
        <div id="div_pizhu"  style="display:none">
            <p class="float_title"><strong>批注详情</strong></p>
            <p class="font-black"><strong>主帖作者：</strong> <span id="to_span"></span> </p>
            <div class="jxxt_zhuanti_hdkj_pizhu">
                <div class="public_input">
                    <textarea id="pizhu_txt" ></textarea>
                </div>
                <input type="hidden" name="pizhu_id" id="pizhu_id"/>
                <p class="p_b_10"><a href="javascript:;" onclick="updateThemePiZhu()" class="an_public3">确定</a></p>
            </div>
      </div>
  </div>
    </div>
    <a id="a_click" href="#dv_content">
    </a>
 <%@include file="/util/foot.jsp" %>
  </body>
</html>
