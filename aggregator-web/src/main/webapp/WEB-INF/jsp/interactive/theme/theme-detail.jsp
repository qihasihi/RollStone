<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
    <style type="text/css">
        .pizhu_a_div a{
            color:blue;
            text-decoration: underline;
        }
        .pizhu_a_div a:HOVER{
            color:red;
            text-decoration: underline;
        }
        .pizhu_a_div a:VISITED{
            color:#FF7F50;
            text-decoration: underline;
        }
    </style>
    <script type="text/javascript" src="js/interactive/theme.js"></script>
    <!-- <script type="text/javascript" src="util/xheditor/xheditor-1.2.1.min.js"></script> -->
    <script type="text/javascript">
        var topicid="${theme.topicid}";
        var themeid="${theme.themeid}";
        var authoruserid="${theme.cuserid }";
        
        var loginuserid="${culoginid}";

        var roleType="${roleStr}";

        var p1,p2;
        var state="";
        var restoreid="";
        var ueditor,edotpr_opt ={};
        $(function(){

            if(topicid==null||themeid==null){
                alert('异常错误，非法访问!参数异常!');return;
            }
            //加载富文本框
            edotpr_opt= {
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
                        'fontfamily', 'fontsize']
                ]
            };
            ueditor= new UE.ui.Editor(edotpr_opt);
            ueditor.render("returnVal");
            ueditor.setDataId(restoreid);
            
            tpueditor= new UE.ui.Editor(edotpr_opt);
            tpueditor.render("update_txt");
            tpueditor.setDataId(themeid);
            
          /*  pzueditor= new UE.ui.Editor(edotpr_opt);
           	pzueditor.render("pizhu_txt");
            pzueditor.setDataId(themeid);
			*/
			//加载分页
			//加载分页控件
				p1=new PageControl({
					post_url:'tpthemereply?m=getReplyListJSON&themeid='+themeid,
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称
					post_form:document.page1form,		//form
					gender_address_id:'page1address',		//显示的区域
					http_operate_handler:tpThemeReplyListReturn,	//执行成功后返回方法
					return_type:'json',								//放回的值类型
					page_no:1,					//当前的页数
					page_size:10,				//当前页面显示的数量
					rectotal:0,				//一共多少
					pagetotal:1,
					operate_id:"div_restore"
				});
                //执行分页
				pageGo('p1');
			
        });
       
        function headError(obj){
            obj.src='images/defaultheadsrc_big.jpg';obj.onerror=null;
        }
    </script>
</head>

<body>
<div class="subpage_head">
<span class="back"><a href="javascript:;" onclick="history.go(-1);">返回</a></span>
<span class="ico55"></span><strong>主帖详情</strong>
</div>

<div class="jxxt_zhuanti_hdkj_nr">
    <p><strong><span id="title_span">	
    	<c:if test="${!empty theme.commenttitle }">
            ${theme.commenttitle }
        </c:if>
    	<c:if test="${empty theme.commenttitle }">
            ${theme.themetitle}
        </c:if>
        </span>
        </strong><span class="font-darkgray">${theme.ctimeString }&nbsp;&nbsp;&nbsp;&nbsp;阅读（${theme.viewcount}）</span></p>
    
    <p class="f_right font-black">
        <c:if test="${!empty roleStr&&(roleStr=='TEACHER' || (culoginid==theme.cuserid))}">
            <c:if test="${culoginid==theme.cuserid}">
                <a href="javascript:;" onclick="showUpdateDiv('div_update','${theme.themeid}')"><span class="ico11"></span>编辑</a>&nbsp;&nbsp;
            </c:if>
            <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
             <a href="javascript:;" onclick="showPiZhuDiv('div_pizhu','${theme.themeid}')"><span class="ico42"></span>批注</a>&nbsp;&nbsp;
            </c:if>
            <a href="javascript:;" onclick="doDelTheme(${theme.themeid });"><span class="ico04"></span>删除</a>
        </c:if>

    </p>
    <p class="font-darkgray">
    <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
    
    					 <c:if test="${theme.istop==0}">
                            <span id="span_top"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','1','span_top');"><span class="ico56"></span>置顶</a></span>
                        </c:if>
                        <c:if test="${theme.istop==1}">
                            <span id="span_top"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','0','span_top');"><span class="ico56"></span>取消置顶</a></span>
                        </c:if>
    	&nbsp;｜
    			<c:if  test="${theme.isessence==1}">
                            <span id="span_best"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','0','span_best');"><span class="ico40"></span>取消精华</a></span>
                        </c:if>
                        <c:if  test="${theme.isessence==0}">
                            <span id="span_best"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','1','span_best');"><span class="ico40"></span>加为精华</a></span>
                        </c:if>
    	&nbsp;｜
    </c:if>
    <span><a href="#div_resotre_queck"><span class="ico45"></span>评论(<span  id="sp_pinglunshu">${theme.pinglunshu }</span>)</a></span>&nbsp;｜
    
     <c:if  test="${theme.ispraise==1}">
        <span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}',2);"><span class="ico41"></span>取消赞(${theme.praisecount})</a></span>
     </c:if>
     <c:if  test="${theme.ispraise==0}">
       <span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}',1);"><span class="ico41"></span>赞(${theme.praisecount})</a></span>
     </c:if>
</p>
<div class="jxxt_zhuanti_hdkj_zhutie">
    <div class="info">
      <p><c:if test="${empty theme.headimage}"><img onerror="headError(this);"  src="adf" width="125" height="125" /></c:if>
                    <c:if test="${!empty theme.headimage}"><img  onerror="headError(this);"  src="${theme.cheadimage}" width="125" height="125" /></c:if></p>
      <div class="name">
      <p>姓&nbsp;名：<span id="realname_theme">${theme.crealname }</span></p>
      <p>用户名：<b>${theme.cusername }</b></p>
      <p>身&nbsp;&nbsp;份：${theme.croleType }</p>
      <p>主帖数：${theme.cfatieshu }</p>
      <p>评论数：${theme.cpinglunshu }</p>
      </div>
    </div>
    <div class="text" id="pizhu_${theme.themeid }_1_updatecontent" style="overflow-x:auto">
        <div id="pizhu_${theme.themeid }_1_updatecontent">
           <c:if test="${!empty theme.commentbycontent}">
                            ${theme.commentbycontent }
                        </c:if>
                        <c:if test="${empty theme.commentbycontent}">
                            ${theme.themebycontent }
                        </c:if>
        </div>
        <c:if test="${!empty theme.imattachArray&&theme.sourceid==1}">
            <c:forEach items="${theme.imattachArray}" var="imItm" varStatus="viIdx">
                <c:if test="${imattachtype==1}">
                    <img src="${imItm}" title="${imItm}"/>
                </c:if>
                <c:if test="${imattachtype==2}">
                    <span id="sp_mp3_${viIdx.index}"></span>
                    <script type="text/javascript">
                        jwplayerSetup={
                            'id': 'player1',
                            'width': 65,
                            'height': 50,
                            'file':  '${imItm}',
                            //   'primary': 'flash',
                            'controlbar':'bottom',
                            'controlbar.idlehide':'false',
                            'modes': [
                                {type: 'flash', src: 'js/common/videoPlayer/new/jwplayer.flash.swf', //
                                    config: {
                                        provider: "http",
                                        autostart:"false",
                                        menu:"false"
                                    }
                                },
                                {type: 'html5'}
                            ]
                        };
                        jwplayer("sp_mp3_${viIdx.index}").setup(jwplayerSetup);
                    </script>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
</div>
 <p class="font-darkgray">
  <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
   <c:if test="${theme.istop==0}">
                            <span id="span_top"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','1','span_top');"><span class="ico56"></span>置顶</a></span>
                        </c:if>
                        <c:if test="${theme.istop==1}">
                            <span id="span_top"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','0','span_top');"><span class="ico56"></span>取消置顶</a></span>
                        </c:if>
    	&nbsp;｜
    			<c:if  test="${theme.isessence==1}">
                            <span id="span_best"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','0','span_best');"><span class="ico40"></span>取消精华</a></span>
                        </c:if>
                        <c:if  test="${theme.isessence==0}">
                            <span id="span_best"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','1','span_best');"><span class="ico40"></span>加为精华</a></span>
                        </c:if>
    	&nbsp;｜
    </c:if>
 <span><a href="#div_resotre_queck"><span class="ico45"></span>评论(<span  id="sp_pinglunshu">${theme.pinglunshu }</span>)</a></span>&nbsp;｜
 
 	<c:if  test="${theme.ispraise==1}">
        <span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}','2');"><span class="ico41"></span>取消赞(${theme.praisecount})</a></span>
     </c:if>
     <c:if  test="${theme.ispraise==0}">
       <span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}','1');"><span class="ico41"></span>赞(${theme.praisecount})</a></span>
     </c:if>
 
 </p>
</div>
<div class="content2">
  <p class="jxxt_zhuanti_hdkj_pl">评论详情</p>
  <div id="div_restore">
 
  </div>
  <form name="page1form" id="page1form" method="post">
	  <div class="nextpage m_t_15" id="page1address"></div>  
  </form>
  
 <div class="jxxt_zhuanti_hdkj_pl_fb public_input" id="div_resotre_queck">
   <textarea name="returnVal" id="returnVal" class="" style="height:150px;"></textarea>
    <p class="t_r"><a href="javascript:;" onclick="quckRestore('${theme.themeid }',1)" class="an_small">发表</a></p>
  </div>
</div>
 <%@include file="/util/foot.jsp" %>
<div id="div_update" style="display:none">
<div class="subpage_head"><span class="ico55"></span><strong>编辑主帖</strong></div>
<div class="content1">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
    <col class="w100"/>
    <col class="w700"/>
    <tr>
      <th><span class="ico06"></span>标&nbsp;&nbsp;题：</th>
      <td><input name="update_title" id="update_title" maxlength="1500" type="text" class="w350" /></td>
    </tr>
    <tr>
      <th><span class="ico06"></span>内&nbsp;&nbsp;容：</th>
      <td><textarea id="update_txt" class="h210 w650" style="height:120px;"></textarea>
      <input type="hidden" name="themeid" id="themeid"/>
      </td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td><a href="javascript:;" onclick="updateThemeContent('div_update')" class="an_small">确&nbsp;定</a><a href="javascript:;" onclick="closeModel('div_update')" class="an_small">取消</a></td>
    </tr>
  </table>
</div>
</div>

<div id="div_pizhu"  style="display:none">
<div class="subpage_head"><span class="ico55"></span><strong>批注详情</strong></div>
<div class="content1">
  <p class="font-black"><strong>主帖作者：</strong> <span id="to_span"></span> </p>
  <div class="jxxt_zhuanti_hdkj_pizhu">     
      <div class="public_input">
      <div style="padding-bottom: 15px;">
      <textarea id="pizhu_txt" ></textarea>
      </div>
      <input type="hidden" name="pizhu_id" id="pizhu_id"/>
      </div>
      <p class="p_b_10"><a href="javascript:;" onclick="updateThemePiZhu()" class="an_public3">确定</a><a href="javascript:;" onclick="closePiZhuDiv('div_pizhu')" class="an_public3">取消</a></p>
  </div>
<br/>
</div>
</div>

</body>
</html>