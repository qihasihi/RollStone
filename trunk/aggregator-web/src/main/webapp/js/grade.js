
//执行分页查询后
function afterajaxList(rps){
	var ghtml='';
	if(rps.type=="error"){
		alert(rps.msg);
	}else{			
		if(rps.objList.length>0){
			ghtml+='<tr><th>年级名称</th><th>实际的值</th>';
			ghtml+='<th>正年级组长</th><th>副年级组长</th><th>操作</th></tr>'; 				
			$.each(rps.objList,function(idx,itm){
				ghtml+='<tr';  
				if(idx%2==0)   
					ghtml+=' class="trbg2"';  
				ghtml+='>';
				ghtml+='<td>'+itm.gradename+'</td>';
				ghtml+='<td>'+itm.gradevalue+'</td>';
				ghtml+='<td>'+itm.zhengleader+'</td>';
				ghtml+='<td>'+itm.fuleader+'</td>';  
				/***********功能能权限**************/
				ghtml+='<td>';   
				if(isUpdate){
					ghtml+='<a  class="font-lightblue" href="javascript:void(0);" onclick="toUpd('+ itm.gradeid +')">修改</a>&nbsp;';     
				}if(isDelete){
					ghtml+='<a  class="font-lightblue" href="javascript:;" onclick="doDelGrade('+itm.gradeid+')">删除</a>&nbsp;'; 
				}
				ghtml+='<a  class="font-lightblue" href="javascript:;" onclick="setGradeLeader('+itm.gradeid+','+leader_roleid+')">指定正组长</a>&nbsp;';
				ghtml+='<a  class="font-lightblue" href="javascript:;" onclick="setGradeLeader('+itm.gradeid+','+leader_fu_roleid+')">指定副组长</a>';
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
	$('#datatbl').html(ghtml); 
}
  

//删除
function doDelGrade(gradeid){
	if(typeof(gradeid)=="undefined"||isNaN(gradeid)){
		alert('异常错误，没有获取到删除所需标识!');
		return;  
	}
	if(!confirm('确认删除?')){return;}      
	$ajax('grade?m=del&gradeid='+gradeid,undefined,'POST','json',function(rps){
		if(rps.type=="success")
			pageGo('p1');	
		alert(rps.msg);	
	},function(){
		alert('网络异常！');
	})
}
//修改之前
function toUpd(gradeid){
	$("#hidden_for_id").val(gradeid);
	$ajax('grade?m=toupd',{gradeid:gradeid},'POST','json',function(rps){ 
		if(rps.type=="error"){
			alert(rps.msg);
			return;
		}
		var html = '';
		if(rps.objList.length>0){
			$("#upd_gradename").val(rps.objList[0].gradename);
			$("#upd_gradevalue").val(rps.objList[0].gradevalue);
			showModel('div_upd');
		}
	},function(){
		alert("网络异常！")
	});
}
//修改
function doUpd(){
	var gradeid = $("#hidden_for_id").val();
	if(gradeid.length<1){
		alert("系统未获取到年份标识，请刷新页面重试！");
		return;
	}
	var gradename=$("#upd_gradename");
	if(gradename.val().Trim().length<1){
		alert("请输入年级名称!");
		gradename.focus();
		return;
	}
	var gradevalue=$("#upd_gradevalue");
	if(gradevalue.val().Trim().length<1){
		alert("请输入年级实际值!");
		gradevalue.focus();
		return;
	}
	 
	if(!confirm("数据验证成功，确认修改？")){
		return;
	}
	$.ajax({
		url:'grade?m=modify',
		data:{
			gradeid : gradeid,
			gradename : gradename.val(),
			gradevalue : gradevalue.val()
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
	var gradename=$("#add_gradename");
	if(gradename.val().Trim().length<1){
		alert("请输入年级名称!");
		gradename.focus();
		return;
	}
	var gradevalue=$("#add_gradevalue");
	if(gradevalue.val().Trim().length<1){
		alert("请输入年级实际值!");
		gradevalue.focus();
		return;
	}
	if(!confirm("数据验证成功，确认添加？")){
		return;
	}
	$ajax('grade?m=ajaxsave',
		{
			gradename : gradename.val(),
			gradevalue : gradevalue.val()
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