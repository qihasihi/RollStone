<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="js/interactive/topic.js"></script>
        <script type="text/javascript" src="js/interactive/theme.js"></script>
		<script type="text/javascript">
			var topicid="${topic.topicid}";
			var state="${topic.status}";
            var selname='',selvalue='',ordercol='',ordertype='';
			var p1,p2;
            var ueditor;
            var newthemeid="${newthemeid}";
            var baseUrl="<%=basePath %>";
            var quotecourseid="${topic.courseid}";
            var quotetopicid="${topic.topicid}";

            var pclsid="${param.clsid}";
            var pclstype="${param.type}";

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
            
            
            var roleStr="${roleStr}";
            $(function(){
				if(roleStr=='STUDENT'&&state==2){  //1:开启  2: 关闭 3: 锁定 4: 隐藏
					alert("该论题已关闭，无法进行查看!");
					window.close();return;
				}
				$(".header_ico1 a").attr("href",$(".header_ico1 a").attr("href"));
				if(topicid==null||topicid.Trim().length<1){
					alert('错误，非法访问!参数异常!');return;
				}
                //加载富文本框
                ueditor= new UE.ui.Editor(edotpr_opt)
                ueditor.render("themecontent");
                ueditor.setDataId(newthemeid);

                //加载分页控件
				p1=new PageControl({
					post_url:'tptopictheme?m=themeListjson&&selectType=-3&status=1',
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称
					post_form:document.page1form,		//form
					gender_address_id:'page1address',		//显示的区域
					http_free_operate_handler:validateParamDetail,		//执行查询前操作的内容
					http_operate_handler:columnsPageReturn_Detail,	//执行成功后返回方法
					return_type:'json',								//放回的值类型
					page_no:1,					//当前的页数
					page_size:15,				//当前页面显示的数量
					rectotal:0,				//一共多少
					pagetotal:1,
					operate_id:"tbl_body_data"
				});
                //执行分页
				pageGo('p1');

				<c:if test="${!empty roleStr&&roleStr=='TEACHER'&&!empty topic.quoteid&&topic.quoteid!=0}">
				  //加载分页控件  如果是老师，则显示所有的引用专题主题
					p2=new PageControl({
						post_url:'tptopictheme?m=themeListjson&cloudstatus=3&selectType=-4',
						page_id:'page2',
						page_control_name:"p2",		//分页变量空间的对象名称
						post_form:document.page2form,		//form
						gender_address_id:'page2address',		//显示的区域
						http_free_operate_handler:validateParamDetail_quote,		//执行查询前操作的内容
						http_operate_handler:theme2PageReturn_Detail,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:15,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_share_data"
					});
					pageGo('p2');
				</c:if>
				<c:if test="${!empty roleStr&&roleStr=='STUDENT'&&!empty topic.quoteid&&topic.quoteid!=0}">
				  //加载分页控件  如果是学生，则显示所有的引用专题主题
					p2=new PageControl({
						post_url:'tptopictheme?m=themeListjson&selectType=-4&status=1',//quote_id IS NOT NULL
						page_id:'page2',
						page_control_name:"p2",		//分页变量空间的对象名称
						post_form:document.page2form,		//form
						gender_address_id:'page2address',		//显示的区域
						http_free_operate_handler:validateParamDetail_quote,		//执行查询前操作的内容
						http_operate_handler:theme2PageReturn_Detail,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:15,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_share_data"
					});
					pageGo('p2');
				</c:if>
			});
            /**
             * 查询的类型
             * @param col  设定的列
             * @param val 设定的值
             * @param divid  选中的值
             */
            function watchChangeType(col,val,divid){
                $("ul[id='pindao_li'] li").removeClass("crumb");
                $("#"+divid).addClass("crumb");
                if(typeof(col)!="undefined"&&col.Trim().length>0)
                    selname=col;selvalue=val;
                pageGo('p1');
            }

            /**
             * 排序
             */
            function orderByColumn(ordercolumn,direct,spid){
                if(typeof(ordercolumn)!="undefined"&&ordercolumn.Trim().length>0){
                    ordercol=ordercolumn;
                    if(typeof(direct)!="undefined"&&direct.Trim().length>0){
                        ordertype=direct
                    }
                    var imgsrc="an21_130423";
                    if(ordertype=="desc"){
                        ordertype="asc";
                        imgsrc="images/an20_130423.png";
                    }else{
                        ordertype="desc";
                        imgsrc="images/an21_130423.png";
                    }

                     var h="<a href=\"javascript:;\" onclick=\"orderByColumn('"+ordercolumn+"\','";;
                     h+=ordertype+"','"+spid+"')\"><img src=\""+imgsrc+"\" width=\"15\" height=\"15\" /></a>";
                    pageGo('p1');
                    $("#"+spid).html(h);
                }
            }
            /*显示创建主题层*/
            function showCreateDiv(dvid){
            	showModel(dvid);	//显示。
            	//生成UEditor

            }
		</script>
	</head>  
  <body>  
 <div class="subpage_head">
     <span class="back">
         <!--<a href="tptopic?m=index&courseid=${topic.courseid}">返回</a>-->
         <a href="javascript:history.go(-1)">返回</a>
     </span>
     <span class="ico55"></span><strong>论题详情</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="font-black">分班级查看：</span>
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
</div>

<div class="jxxt_zhuanti_hdkj_nr">
    <p class="f_right">主帖数：<span class="font-red">${topic.themecount }</span>&nbsp;&nbsp;&nbsp;评论数：<span class="font-red">${topic.restorecount }</span></p>
  <p class="font_strong">${topic.topictitle }</p>
    <p>　　${topic.topiccontent }</p>
</div>

<div class="subpage_nav">
  <ul id="pindao_li">
    <li class="crumb"><a href="javascript:;" onclick="watchChangeType('','','pindao_li_all');">全部</a></li>
    <li id="pindao_li_isbest"><a href="javascript:;" onclick="watchChangeType('isessence','1','pindao_li_isbest')">精华</a></li>
  </ul>
</div>

<div class="content1">
 <p><a href="javascript:;" onclick="showCreateDiv('dv_create')" class="font-darkblue"><span class="ico26"></span>新建主帖</a></p>
  <div class="jxxt_zhuanti_hdkj_tab">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <col class="w350" />
    <col class="w80" />
    <col class="w150" />
    <col class="w80" />
    <col class="w80" />
    <col class="w200" />
    <tr>        
	      <th>主题</th>
	        <th>作者</th>
	        <th>发表时间&nbsp;<span id="sp_ctime"><a href="javascript:;" onclick="orderByColumn('is_top desc,c_time','asc','sp_ctime')"><img src="images/an20_130423.png" width="15" height="15" /></a></span></th>
	        <th>查看&nbsp;<span id="sp_watch"><a href="javascript:;" onclick="orderByColumn('view_count','asc','sp_watch')"><img src="images/an20_130423.png" width="15" height="15" /></a></span></th>
	        <th>评论&nbsp;<span id="sp_huifu"><a href="javascript:;" onclick="orderByColumn('pinglunshu','asc','sp_huifu')"><img src="images/an20_130423.png" width="15" height="15" /></a></span></th>
	        <th>最后发表&nbsp;<span id="sp_lastfb"><a href="javascript:;" onclick="orderByColumn('lastfabiao','asc','sp_lastfb')"><img src="images/an20_130423.png" width="15" height="15" /></a></span></th>
            <!--  <th>操作</th> -->
    </tr>
    <tbody id="tbl_body_data">       
	      </tbody>
  </table>
 </div>
 <form name="page1form" id="page1form" action="" method="post">			
  <div class="nextpage" id="page1address"></div>
</form>

<c:if test="${!empty topic.quoteid&&topic.quoteid!=0}">
    <div id="dv_other_topic">
 <p class="p_t_20 font-black"><span class="ico44"></span>其他学校参考主帖</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
  <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
    <col class="w500" />
    <col class="w350" />
    <col class="w90" />
    </c:if>
    <c:if test="${!empty roleStr&&roleStr=='STUDENT'}">
    <col class="w590" />
    <col class="w350" />
    </c:if>
    <tr>
      <th>主帖</th>
      <th>来源学校</th>
      <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
      	<th>学生可见状态</th>
      </c:if>
    </tr>
    <tbody id="tbl_body_share_data"></tbody>
  </table>
  <form name="page2form" id="page2form" method="post">
  <div class="nextpage" id="page2address"></div>
  </form>
    </div>
 </c:if>
</div>
<div id="dv_create" style="display:none">
	<div class="subpage_head"><span class="ico55"></span><strong>新建主帖</strong></div>
	<div class="content1">
	  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
	    <col class="w100"/>
	    <col class="w700"/>
	    <tr>
	      <th><span class="ico06"></span>标&nbsp;&nbsp;题：</th>
	      <td><input name="themetitle" placeholder="请限制在40个字以内!" maxlength="40" id="themetitle" type="text" class="w350" /></td>
	    </tr>
	    <tr>
	      <th><span class="ico06"></span>内&nbsp;&nbsp;容：</th>
	      <td><textarea name="themecontent" id="themecontent" class="h210 w650" style="height:120px;"></textarea></td>
	    </tr>
	    <tr>
	      <th>&nbsp;</th>
	      <td><a href="javascript:;" onclick="doAddTheme()" class="an_small">确&nbsp;定</a>
	      <a href="javascript:;" onclick="closeModel('dv_create');" class="an_small">取&nbsp;消</a>
	      </td>
	    </tr>
	  </table>
	  <br>
	</div>
</div>



 <%@include file="/util/foot.jsp" %>
  </body>
</html>
