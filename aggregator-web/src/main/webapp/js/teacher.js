//AJAX查询之前执行方法
function beforajaxList(p){
	var param = {};
	
	//教师姓名
	var teachername = $("#sel_teachername").val();
	if(teachername.Trim().length>0){
		param.teachername = teachername;
	}
	//登录帐号
	var username = $("#sel_username").val();
	if(username.Trim().length>0){
		param.username = username;
	}
	//授课科目
	var coursename = $("#sel_coursename").val();
	if(coursename.Trim().length>0){
		param.coursename = coursename;
	}
	//教师性别
	var teachersex = $("input[name='sel_teachersex']").filter(function(){
		return this.checked && this.value.Trim().length>0;
	});
	teachersex = teachersex.val();
	param.teachersex = teachersex;
	//身份证号
	var teachercardid = $("#sel_teachercardid").val();
	if(teachercardid.Trim().length>0){
		param.teachercardid = teachercardid;
	}
	p.setPostParams(param);
}

//执行成功后返回方法 
function afterajaxList(rps){
	var html = '';
	if(rps.type=='error'){
		alert(rsp.msg);
		return;
	}else{
		if(rps.objList.length>0){
			html+='<tr><th>教师姓名</th><th>登录帐号</th><th>授课学科</th>';
			html+='<th>教师性别</th><th>教师电话</th><th>邮箱</th>';
			html+='<th>身份证号</th></tr>';
			$.each(rps.objList,function(idx,itm){
				html+='<tr>';
				if(!(typeof(itm.teachername)=="undefined")){
					html+='<td>'+ itm.teachername +'</td>';
				}else
					html+='<td></td>';
				if(!(typeof(itm.username)=="undefined")){  
					html+='<td>'+ itm.username +'</td>';
				}else
					html+='<td></td>';
				if(!(typeof(itm.coursename)=="undefined")){
					html+='<td>'+ itm.coursename +'</td>';
				}else
					html+='<td></td>';
				if(!!(itm.teachersex)){
					html+='<td>'+ itm.teachersex +'</td>';
				}else
					html+='<td></td>';
				if(!(typeof(itm.teacherphone)=="undefined")){
					html+='<td>'+ itm.teacherphone +'</td>';
				}else
					html+='<td></td>';
				if(!(typeof(itm.teacherpost)=="undefined")){
					html+='<td>'+ itm.teacherpost +'</td>';
				}else
					html+='<td></td>';
				if(!!(itm.teachercardid)){
					html+='<td>'+ itm.teachercardid +'</td>';
				}else
					html+='<td></td>';
				html+='<tr>'
			});
		}else{
			html+="<tr><th>暂无数据!</th></tr>";
		}
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
	}
	$("#datatbl").html(html);
}