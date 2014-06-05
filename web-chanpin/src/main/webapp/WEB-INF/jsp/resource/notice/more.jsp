<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp"%>
<html>
<head>
<title>北京四中网校--数字化校园</title>
<script type="text/javascript">
	var p1;
	var noticetype="${param.noticetype}";
	var uid="${sessionScope.CURRENT_USER.ref}";
   $(function(){
		p1 = new PageControl({
			post_url:'notice?m=usernoticelist',
			page_id:'page1',
			page_control_name:'p1',
			post_form:document.page1form,
			gender_address_id:'page1address',
			http_free_operate_handler:validateParam,
			http_operate_handler:noticeListReturn,
			return_type:'json',
			page_no:1, 
			page_size:20,
			rectotal:0,
			pagetotal:1,
			operate_id:"mainUL"
		});
		pageGo("p1");
	});
	function noticeListReturn(rps){
		if(rps.type=="error"){
			alert(rps.msg);
			if (typeof (p1) != "undefined" && typeof (p1) == "object") {
				// 设置空间不可用
				p1.setPagetotal(1);
				p1.setRectotal(0);
				p1.Refresh();
				// 设置显示值
				var shtml = '<tr><td align="center">暂时没有校园活动信息!';
				shtml += '</td></tr>';
				$("#mainTbl").html(shtml);			
				
			}		
		}else{
			var htm='';
			if(rps.objList.length<1)
				htm='暂无!';
			else{
				$.each(rps.objList,function(idx,itm){
					 htm+='<li><span class="font-gray1 f_right p_l_27">'+itm.ctimeChinaString+'</span>';
					 if(typeof(itm.titlelink)=="undefined"||itm.titlelink==null||itm.titlelink.Trim().length<1)
						 htm+='<a href="notice?m=detail&ref='+itm.ref+'" target="_blank">';
					 else
						 htm+='<a href="'+itm.titlelink+'" target="_blank">';
					 htm+=itm.noticetitle+'</a></li>';
				})
			} 
			$("#mainUL").html(htm);
			$("#mainUL").show();
			 
			
			//翻页信息
			if (typeof (p1) != "undefined" && typeof (p1) == "object") {
				p1.setPagetotal(rps.presult.pageTotal);
				p1.setRectotal(rps.presult.recTotal);
				p1.setPageSize(rps.presult.pageSize);
				p1.setPageNo(rps.presult.pageNo);	
				p1.Refresh();
			}
		}	
	}
	/*参数组织*/
	function validateParam(tobj){
		var param=new Object();
		if(typeof(noticetype)!="undefined"&&noticetype.Trim().length>0)
			param.noticetype=noticetype;
		param.cuserid=uid;
		tobj.setPostParams(param);	
	}
			
	
</script>
</head>
<body>
<div class="erji_header"><img src="images/logo.png" width="320" height="96" /></div>
<div class="erji_content">
  <p><a href="user?m=toIndex">首页</a><span class="m_lr_5">&gt;</span>
  <c:if test="${empty param.noticetype||param.noticetype==0}">公告</c:if>
  <c:if test="${!empty param.noticetype&&param.noticetype==1}">网上公示</c:if>
  <c:if test="${!empty param.noticetype&&param.noticetype==2}">校内参考</c:if>
  <c:if test="${!empty param.noticetype&&param.noticetype==3}"><a href="resource?m=toindex">资源系统</a> > 资源公告</c:if>
</p>
  <div class="m_a w870 p_t_20">
    <p class="index_zuzhi_title font16">
    <c:if test="${empty param.noticetype||param.noticetype==0}">公告</c:if>
  <c:if test="${!empty param.noticetype&&param.noticetype==1}">网上公示</c:if>
   <c:if test="${!empty param.noticetype&&param.noticetype==2}">校内参考</c:if>
  <c:if test="${!empty param.noticetype&&param.noticetype==3}">资源公告</c:if></p>
    <ul class="public_list m_t_10" id="mainUL"></ul>
  	<form method="post" name="page1form">
   		 <p  class="t_c p_tb_10" id="page1address"></p>
     </form>
  </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>