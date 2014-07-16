<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@include file="/util/common.jsp" %> 
<script type="text/javascript">
var p1;
	$(function(){
		//翻页控件
			p1=new PageControl({
				post_url:'testuser?m=jsonlist',
				page_id:'page1',
				page_control_name:"p1",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				//http_free_operate_handler:beforajaxList,
				gender_address_id:'page1address',		//显示的区域						
				http_operate_handler:afterajaxList,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"datatbl"
			});
			pageGo('p1');
	}); 
	
	function afterajaxList(rps){		
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			var s='<tr><th>编号</th><th>用户名</th><th>密保</th><th>答案</th><th>状态</th><th>操作</th></tr>';
			$.each(rps.objList,function(idx,itm){
			s+='<tr>';
			s+='<td>'+itm.uid+'</td>';
			s+='<td>'+itm.uname+'</td>';
			s+='<td>'+(itm.upassques==undefined?'':itm.upassques)+'</td>';
			s+='<td>'+(itm.upassquesanswer==undefined?'':itm.upassquesanswer)+'</td>';
			s+='<td>'+(itm.ustate==0?"启用":"禁止")+'</td>';
			s+='<td><a href="javascript:document.getElementById(\'operateUrl\').value=\'testuser?m=ajaxmodify&uid='+itm.uid+'\';showModel(\'a_div\');">修改</a><a>删除</a></td>';
			s+='</tr>';
			});
			$("#datatbl").html(s);	
			
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
	}
	
	/**
	*添加
	*/
	function doOperateUser(){
	var url=$("#operateUrl").val().Trim();
		if(url.length<1){
			alert('异常错误，访问地址未知!');
			return;
		}
		var unameObj=$("#a_uname");
		if(unameObj.val().Trim().length<1){
			alert('用户名不能为空!');
			unameObj.focus();
			return;
		}
		var upassObj=$("#a_pass");
		if(upassObj.val().Trim().length<1){
			alert('密码不能为空!');
			upassObj.focus();
			return;
		}
		var stateObj=$("input[name='a_rdo_state']").filter(function(){return this.checked;});
		
		var upassquesObj=$("#a_upassques");
			if(upassquesObj.val().Trim().length<1){
			alert('密保问题不能为空!');
			upassquesObj.focus();
			return;
		}
		var upassquesAnswerObj=$("#a_upassquesanswer");
			if(upassquesAnswerObj.val().Trim().length<1){
			alert('密保问题答案不能为空!');
			upassquesAnswerObj.focus();
			return;
		}
		$("#do_addButton").hide();
		$.ajax({
			url: url,
			type:'post',
			dataType:'json',
			data:{uname:unameObj.val().Trim()
				,upass:upassObj.val().Trim()
				,ustate:stateObj.val().Trim()
				,upassques:upassquesObj.val().Trim()
				,upassquesanswer:upassquesAnswerObj.val().Trim()},
			cache: false, 
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				$("#do_addButton").show();
				if(rps.type=="success"){
					closeModel('a_div');
					pageGo('p1');
					alert(rps.msg);
				}else{
					alert(rps.msg);
				}
			}
		});
	}
</script>

<body>
<p></p>
<p></p>
<p><a href="javascript:document.getElementById('operateUrl').value='testuser?m=ajaxadd';showModel('a_div');">添加</a></p>
<p>
	<table id="datatbl" style="width:600px;border: 1px solid gray;">
	
	</table>

</p>
<form action="" name="page1form" method="post">
<div id="page1address"></div>
</form>
<br/><br/><br/>
<div style="margin: 5px;background-color: white" class="white_content" id="a_div"> 
<p>用户名：<input type="text" maxlength="16" name="a_uname" id="a_uname"/></p>
<p>密&nbsp;&nbsp;码：<input type="password" maxlength="16" name="a_pass" id="a_pass" /></p>
<p>状态：<input type="radio" checked="checked" value="0" name="a_rdo_state" id="a_state_0"/><label for="a_state_0">启用</label>
<input type="radio" value="1" name="a_rdo_state" id="a_state_1"/><label for="a_state_1">失效</label>
</p>
<p>密码问题：<input type="text" maxlength="16" name="a_upassques" id="a_upassques"/></p>
<p>问题答案：<input type="text" maxlength="16" name="a_upassquesanswer" id="a_upassquesanswer"/></p>
<input type="hidden" name="operateUrl" id="operateUrl"/>
<p><input type="button" value="确定"  id="do_addButton"  onclick="doOperateUser()"/> <input type="button" onclick="closeModel('a_div');" value="取消"/></p>

</div>


<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
</body>
