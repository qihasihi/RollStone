
//执行分页查询后
function afterajaxList(rps){
	var ghtml='';
	if(rps.type=="error"){
		alert(rps.msg);
	}else{			
		if(rps.objList.length>0){
			ghtml+='<tr><th>学年名称</th><th>实际的值</th>';
			ghtml+='<th>开始时间</th><th>结束时间</th><th>创建时间</th><th>操作</th></tr>'; 				
			$.each(rps.objList,function(idx,itm){
				ghtml+='<tr';
				if(idx%2==0) 
					ghtml+=' class="trbg2"';
				ghtml+='>';
				ghtml+='<td>'+itm.classyearname+'</td>';
				ghtml+='<td>'+itm.classyearvalue+'</td>';  
				if(typeof(itm.btimeString)!='undefined')
					ghtml+='<td>'+itm.btimeString+'</td>';
				else
					ghtml+='<td></td>';
				if(typeof(itm.etimeString)!='undefined')
					ghtml+='<td>'+itm.etimeString+'</td>';
				else
					ghtml+='<td></td>';
				ghtml+='<td>'+itm.ctimeString+'</td>';
				/***********功能能权限**************/
				ghtml+='<td>';
				if(isUpdate){
					ghtml+='<a class="font-lightblue" href="javascript:void(0);" onclick="toUpd('+ itm.classyearid +')">修改</a>&nbsp;';     
				}if(isDelete){
					ghtml+='<a class="font-lightblue" href="javascript:;" onclick="doDelClass('+itm.classyearid+')">删除</a>';
				}
				ghtml+='</td>';
				/***********功能能权限**************/  
				ghtml+='</tr>';
			});		
		}else{
			ghtml+='<tr><th>暂无数据 !</th></tr>';
		}
		
			p1.setPageSize(rps.presult.pageSize);
			p1.setPageNo(rps.presult.pageNo);
			p1.setRectotal(rps.presult.recTotal);
			p1.setPagetotal(rps.presult.pageTotal);
			p1.Refresh();
	}
	$('#maintbl').html(ghtml);
}
  

//删除
function doDelClass(clsid){
	if(typeof(clsid)=="undefined"||isNaN(clsid)){
		alert('异常错误，没有获取到删除所需标识!');
		return;
	}
	if(!confirm('确认删除?')){return;}      
	$ajax('classyear?m=del&classyearid='+clsid,undefined,'POST','json',function(rps){
		if(rps.type=="success")
			pageGo('p1');	
		alert(rps.msg);	
	},function(){
		alert('网络异常！');
	})
}
//修改之前
function toUpd(classyearid){
	$("#hidden_for_upd").val(classyearid);
	$ajax('classyear?m=toupd',{classyearid:classyearid},'POST','json',function(rps){ 
		if(rps.type=="error"){
			alert(rps.msg);
			return;
		}
		var html = '';
		if(rps.objList.length>0){
			$("#upd_classyear_name").val(rps.objList[0].classyearname);
			$("#upd_classyear_value").val(rps.objList[0].classyearvalue);
			$("#upd_btime").val(rps.objList[0].btimeString);
			$("#upd_etime").val(rps.objList[0].etimeString);
			showModel('div_upd');
		}
	},function(){
		alert("网络异常！")
	});
}
//修改
function doUpd(){
	var classyearid = $("#hidden_for_upd").val();
	if(classyearid.length<1){
		alert("系统未获取到年份标识，请刷新页面重试！");
		return;
	}
	var classyearname=$("#upd_classyear_name");
	if(classyearname.val().Trim().length<1){
		alert("请输入年份名称!");
		classyearname.focus();
		return;
	}
	var classyearvalue=$("#upd_classyear_value");
	if(classyearvalue.val().Trim().length<1){
		alert("请输入年份值!");
		classyearvalue.focus();
		return;
	}
	var btime=$("#upd_btime");
	var etime=$("#upd_etime");
	if(btime.val().Trim().length<1){
		alert("请输入开始时间!");
		btime.focus();
		return;
	}
	if(etime.val().Trim().length<1){
		alert("请输入结束时间!");
		etime.focus();
		return
	}
	var returnval=rpc.PageUtilTool.BeginDiffEndDate(btime.val(),etime.val()); 
	if(returnval=='='||returnval==">"){
		alert("开始时间不能大于或等于结束时间!");
		btime.focus();
		return;
	}
	if(!confirm("数据验证成功，确认修改？")){
		return;
	}
	$.ajax({
		url:'classyear?m=modify',
		data:{
			classyearid : classyearid,
			classyearname : classyearname.val(),
			classyearvalue : classyearvalue.val(), 
			btime :btime.val(),
			etime :etime.val()
		},
		type:'post',
		dataType:'json',
		error:function(){
			alert('网络异常！');
		},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			pageGo('p1');
			closeModel('div_upd');
			alert(rps.msg);
		}
	});
	
}

//添加
function doAdd(){
	var classyearname=$("#add_classyear_name");
	if(classyearname.val().Trim().length<1){
		alert("请输入年份名称!");
		classyearname.focus();
		return;
	}
	var classyearvalue=$("#add_classyear_value");
	if(classyearvalue.val().Trim().length<1){
		alert("请输入年份值!");
		classyearvalue.focus();
		return;
	}
	var btime=$("#btime");
	var etime=$("#etime");
	if(btime.val().Trim().length<1){
		alert("请输入开始时间!");
		btime.focus();
		return;
	}
	if(etime.val().Trim().length<1){
		alert("请输入结束时间!");
		etime.focus();
		return
	}
	var returnval=rpc.PageUtilTool.BeginDiffEndDate(btime.val(),etime.val()); 
	if(returnval=='='||returnval==">"){
		alert("开始时间不能大于或等于结束时间!");
		btime.focus();
		return;
	}
	if(!confirm("数据验证成功，确认添加？")){
		return;
	}
	$ajax('classyear?m=ajaxsave',
		{
			classyearname : classyearname.val(),
			classyearvalue : classyearvalue.val(), 
			btime :btime.val(),
			etime :etime.val()
		},
		'post',
		'json',
		function(rps){
			if(rps.type=="error"){ 
				alert(rps.msg);
				return;
			}
			pageGo('p1');
			closeModel('div_add');
			alert(rps.msg);
		},
		function(){
			alert('网络异常！');
		}
	);
	 
}

function reset(div){
	$("div[id="+div+"] input").val('');
	$("div[id="+div+"] select").val('');
}