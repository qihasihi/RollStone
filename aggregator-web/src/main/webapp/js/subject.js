//AJAX查询之前的方法
function beforajaxList(p){
	var param = {};
	var rolename =$("#sel_subjectname").val();
	if(rolename.Trim().length>0){
		param.subjectname = subjectname;
	}
	p.setPostParams(param);
}

//执行成功后返回方法
function afterajaxList(rps){
	var html = '';
	if(rps.type=="error"){
		alert(rps.msg);
		return;
	}else{
		if(rps.objList.length>0){
			html+="<tr><th>课程名称</th><th>创建时间</th>";			
			html+="<th>修改时间</th><th>操作</th></tr>";
			$.each(rps.objList,function(idx,itm){
				html+="<tr>";
				html+="<td>"+ itm.subjectname +"</td>";  
				html+="<td>"+ itm.ctimeString +"</td>";
				html+="<td>"+ (itm.mtimeString==null?"NaN":itm.mtimeString) +"</td>";
				/**********权限功能操作*************/
				html+="<td>";
				if(isUpdate){
					html+="<a href='javascript:void(0);' onclick='toShowDIV(\"upd\","+ itm.subjectid +")'>修改</a>&nbsp";
				}
				if(isDelete){
					html+="<a href='javascript:void(0);' onclick='doDel("+ itm.subjectid +")'>删除</a>";
				}
				html+="</td>";
				/**********权限功能操作*************/
				html+="</tr>";
			});
		}else{
			html+="<tr><th>暂无数据！</th></tr>";
		}
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
	}
	$("#datatbl").html(html);
}
//重置
function reset(div){
	$("#"+div+" input[type='text']").val("");
}
//显示DIV
function toShowDIV(div,id){
	if(typeof(id)!="undefined" && !isNaN(id)){
		$("#hidden_for_id").val(id);
	}
	if(div=="upd"){
		$("#div_add").hide();
		$("#div_upd").show();
		$.ajax({
			url:'subject?m=toupd&subjectid='+id,
			type:'post',
			dataType:'json',
			error:function(){
				alert("网络异常！");
			},
			success:function(rps){
				if(rps.type=="error"){
					alert(rsp.msg);
					return;
				}
				if(rps.objList.length>0){
					$("#upd_subjectname").val(rps.objList[0].subjectname);
					$("#upd_ctime").html(rps.objList[0].ctimeString);
				}
			}
		});
	}else{
		$("#div_upd").hide();
		$("#div_add").show();
	}
}

//添加
function doAdd(){
	var subjectname = $("#add_subjectname").val();
	if(subjectname.Trim().length<1){
		alert("请您填写角色名称！");
		return;
	}
	$.ajax({
		url:'subject?m=ajaxsave',
		type:'post',
		data:{subjectname:subjectname },
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			$("#div_add").hide();
			reset("div_add");
			pageGo("p1");
			alert(rps.msg);
		}
	});
}

//删除
function doDel(id){
	if(!confirm("确认删除该角色？")){
		return;
	}
	if(typeof(id)=="undefined" || isNaN(id)){
		alert("未获取到角色标识！");
		return;
	}
	$.ajax({
		url:'subject?m=del&subjectid='+id,
		type:'post',		
		dataType:'json',
		error:function(){alert("网络异常！");},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			pageGo("p1");
			alert(rps.msg);
		}
	});
}

//修改
function doUpd(){
	var subjectid = $("#hidden_for_id").val();
	if(typeof(subjectid)=="undefined" || isNaN(subjectid)){
		alert("未获取到角色标识，请刷新后重试！");
		return;
	}
	var subjectname = $("#upd_subjectname").val();
	if(subjectname.Trim().length<1){
		alert("请您填写角色名称");
		return;
	}
	$.ajax({
		url:'subject?m=modify&subjectid='+subjectid,
		data:{subjectname : subjectname},
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常!");},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			$("#div_upd").hide();
			reset("div_upd");
			pageGo("p1");
			alert(rps.msg);
		}
	});
}


