function peerUserListReturn(rps){
	if(rps.type=='error'){
		alert(rps.msg);
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			// 设置空间不可用
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			// 设置显示值
			var shtml = '<tr><td align="center">暂时没有评价主题信息!';
			shtml += '</td></tr>';
			$("#mainTbl").html(shtml);			
			
		}		
	}else{
		var shtml = '';
		shtml+='<tr>';
		shtml+=	'<th>姓名</th><th>参评类别</th><th>操作</th></tr>';
		if(rps.objList.length<1){
			shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
		}else{
			$.each(rps.objList,function(idx,itm){
				if(idx%2==0){
					shtml+='<tr class="trbg2">';
				}else{
					shtml+='<tr>';
				}
				shtml+='<td>'+itm.teachername+'</td>';
				//alert(itm.ptype);
				shtml+='<td><select id="typeid'+itm.ref+'">';
				shtml+='<option value="1"';
				if(itm.ptype==1)
					shtml+=' selected=true';
				shtml+='>教师互评</option><option value="2"';
				if(itm.ptype==2)
					shtml+=' selected=true';
				shtml+='>职工互评</option></select></td>';
				shtml+='<td><a href="javascript:update(\''+itm.ref+'\')"  class="font-lightblue">修改</a></td>';
				
			});
		}
		$("#mainTbl").hide();
		$("#mainTbl").html(shtml);
		$("#mainTbl").show('fast');
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

function getUserListByDept(deptid){
	var peerbaseid = $("#peerid").val().Trim();
	$.ajax({
		url:'peeruser?m=ajaxlist',
		dataType:'json',
		type:'post',
		data:{deptid:deptid,
			  peerbaseid:peerbaseid
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			//alert(rps.msg);
			peerUserListReturn(rps);
		}
	});
}
function update(ref){
	var type = document.getElementById("typeid"+ref);
	var ptype = type.value;
	$.ajax({
		url:'peeruser?m=updateType',
		dataType:'json',
		type:'post',
		data:{ref:ref,ptype:ptype
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			alert(rps.msg);
			//peerUserListReturn(rps);
			//var peerbaseid = $("#peerid").val().Trim();
			//window.location.href="peeruser?m=list&peerbaseid="+peerbaseid;
		}
	});
}