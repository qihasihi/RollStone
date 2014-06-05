//AJAX查询之前的方法
function beforajaxList(p){
	var param = {};
	var jobname =$("#srh_jobname").val();
	if(jobname.Trim().length>0){
		param.jobname = jobname;
	}
	p.setPostParams(param);
} 

//执行成功后返回方法
function afterajaxList(rps){
	if(rps.type=="success"){
		var html = '';
		html+='<colgroup span="4" class="w180"></colgroup><tr><th>职务名称</th>';
		html+='<th>创建时间</th><th>修改时间</th><th>操作</th></tr>';
		if(rps.objList.length>0){
			$.each(rps.objList,function(idx,itm){
				html+='<tr';
				if(idx%2==0) 
					html+=' class="trbg2"';
				html+='>';
				html+="<td>"+ itm.jobname +"</td>";  
				html+="<td>"+ itm.ctimeString +"</td>";
				if(typeof(itm.mtimeString)!='undefined')
					html+="<td>"+ itm.mtimeString +"</td>";  
				else{
					html+="<td>----</td>"; 
				}
				html+="<td><a class='font-lightblue' href="+"javascript:selUserjob('"+itm.jobid+"');"+">指定用户</a>" +
						"&nbsp;<a class='font-lightblue' href="+"javascript:showUpdateDiv('"+itm.jobname+"',"+itm.jobid+");"+">修改</a>" +
						"&nbsp;<a class='font-lightblue' href="+"javascript:deleteJob("+itm.jobid+");"+">删除</a></td>";
				html+="</tr>";  
			});    
		}else{
			html+="<tr><td colspan='4'>暂无数据！</th></tr>";
		}
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
		$("#datatbl").html(html);
	}else{
		alert("ERROR!!");
	}
}

//添加
function doAdd(){
	
	var jname=$("#add_jobname");
	if(jname.val().Trim().length<1){
		alert('用户名不能为空!');
		jname.focus();
		return;
	}
	$.ajax({
		url:'job?m=add',
		type:'post',
		data:{jobname:jname.val()},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			closeModel('div_add');
			$("#add_jobname").val("");
			pageGo("p1");
			alert(rps.msg);
		}
	});
}

//删除
function deleteJob(id){
	  $.ajax({
			url: "job?m=del",
			type:'post',
			dataType:'json',
			data:{jobid:id},
			cache: false, 
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				if(rps.type=="success"){
					pageGo("p1");
				}else{
					alert(rps.msg);
				}
			}
		});
}

//修改
function doUpd(){
	var jobid = $("#jobid").val();  
	if(typeof(jobid)=="undefined" || isNaN(jobid)){   
		alert("未获取到职务标识，请刷新后重试！");
		return;
	}
	var jobname = $("#upd_jobname").val();
	if(jobname.Trim().length<1){  
		alert("请您填写角色名称");
		return;
	}
	$.ajax({
		url:'job?m=update',  
		data:{jobname:jobname,jobid:jobid},
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常!");},    
		success:function(rps){   
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			closeModel('div_upd');
			pageGo("p1");     
			alert(rps.msg);  
		}
	});
}
//指定用户
function selRoleUser(roleid){
	if(typeof(roleid)=="undefined" || isNaN(roleid)){
		alert("系统未获取到角色标识!请刷新后重试!"); 
		return;
	}	
	var param = "dialogWidth:500px;dialogHeight:700px;status:no;location:no";
	var returnValue=window.showModalDialog("roleuser?m=toSetRoleUser&roleid="+roleid,param);
	if(returnValue==null||returnValue.Trim().length<1){  
		alert("您未选择用户或未提交!"); 
		return;
	}
	var uidarr = returnValue.split(","); 
	if(uidarr.length<1){ 
		alert("系统未获取到用户标识!请刷新后重试!"); 
		return;
	}
	var uidstr='';
	$.each(uidarr,function(idx,itm){
		if(uidstr.length>0){
			uidstr+=",";
		}else{  
			uidstr+=itm;
		}
	});  
	$.ajax({ 
		url:'roleuser?m=doManageRoleUser', 
		data:{ 
			roleid : roleid,
			useridArray : uidstr 
		}, 
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常!");},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}else
				alert(rps.msg);
		}
	});
}

/**
 * 为选中用户指定页面权限
 */
function setPageRight(rid){
	if(typeof(rid)=="undefined" || isNaN(rid)){
		alert("系统未获取到权限标识，请刷新后重试!"); 
		return;
	}
 	var param ="dialogWidth:500px;dialogHeight:700px;status:no;location:no;";
	var returnValue = window.showModalDialog("pageright?m=dialoglist&roleid="+rid,param);
	
	if(returnValue==null || returnValue.Trim().length<1){
		alert("您未提交或关闭窗口!");  
		return;   
	} 
	$.ajax({
		url:'rightuser?m=setRightByRole',  
		type:'post',
		data:{ 
			rid:rid,
			prightidstr : returnValue,  
		},
		dataType:'json',
		error:function(){alert("网络异常!")}, 
		success:function(rps){
			alert(rps.msg);   
		}
	});
}

function showUpdateDiv(jobname,jobid){
	$("#udtJobname").html(jobname);
	$("#upd_jobname").val(jobname);
	$("#jobid").val(jobid);
	showModel('div_upd');
}

