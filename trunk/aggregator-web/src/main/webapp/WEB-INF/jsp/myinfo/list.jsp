<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp"%>
<html>
<head>
<title>北京四中网校--数字化校园</title>
<script type="text/javascript">
	var p1;
	var msgid="${param.msgid}";
   $(function(){
		p1 = new PageControl({
			post_url:'myinfouser?m=ajaxlist',
			page_id:'page1',
			page_control_name:'p1',
			post_form:document.page1form,
			gender_address_id:'page1address',
			http_free_operate_handler:validateParam,
			http_operate_handler:listReturn,
			return_type:'json',
			page_no:1, 
			page_size:20,
			rectotal:0,
			pagetotal:1,
			operate_id:"mainUL"
		});
		pageGo("p1");
	});
	function listReturn(rps){
		if(rps.type=="error"){
			alert(rps.msg);
			
		}else{
			var htm='';
			if(rps.objList.length<1)
				htm='暂无!';
			else{
				$.each(rps.objList,function(idx,itm){
					 htm+='<li><span class="font-gray1 f_right p_l_27">'+itm.ctimeChinaString+'</span>';
					htm+=itm.dynamicString+'</li>';
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
		if(typeof(msgid)!="undefined"&&msgid.Trim().length>0)
			param.msgid=msgid;
		tobj.setPostParams(param);
	}
			
	
</script>
</head>

<body>
<div class="public_title"><span class="ico09"></span>${msgName }</div>
<div class="ej_content">
    <ul class="one"  id="mainUL">
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">有新校风记录刘长铭校长、黄向伟校长伟伟出席，外交部工委成仪</a>...</li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">式有新校风记录刘长铭校长、黄向伟校风记录刘长铭校长、黄向伟校长出席外交成功的好方法。</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">铭校长、黄向伟校长出席</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">有新校风记录刘长铭校长、黄向伟校长伟伟出席，外交部工委成仪</a>...</li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">式有新校风记录刘长铭校长、黄向伟校风记录刘长铭校长、黄向伟校长出席外交成功的好方法。</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">铭校长、黄向伟校长出席</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">有新校风记录刘长铭校长、黄向伟校长伟伟出席，外交部工委成仪</a>...</li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">式有新校风记录刘长铭校长、黄向伟校风记录刘长铭校长、黄向伟校长出席外交成功的好方法。</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">铭校长、黄向伟校长出席</a></li>
        <li><b>2012年8月13日11:12:43</b><a href="1" target="_blank">部关工委成立仪式有新校风记录刘长</a></li>
    </ul>
    <form method="post" name="page1form">
        <div class="nextpage" id="page1address"></div>
    </form>
</div>




<%@include file="/util/foot.jsp" %>
</body>
</html>