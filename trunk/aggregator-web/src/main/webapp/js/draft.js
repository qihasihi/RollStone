	//AJAX查询之前的方法
	function beforajaxList(p){
		var param = {};
		var keyword =$("#sel_keyword").val();
		if(keyword.Trim().length>0){
			param.smstitle = keyword;
			param.receiver = keyword;
		}
		p.setPostParams(param);
	}

	//执行分页查询后
	function afterajaxList(rps){
		var ghtml='<tr><th>&nbsp;</th><th>信息主题</th><th>信息接收者<img src="images/an23_130423.png" alt="" width="9" height="10" /></th>'
			+'<th>保存时间<img src="images/an22_130423.png" alt="" width="9" height="10" /></th><th>操作</th></tr>';
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr>';
					ghtml+='<td><input onclick="selOneCkObj()" type="checkbox" value="'+itm.smsid+'" name="cksmsid"/></td>'; 
					ghtml+='<td><p><a href="javascript:toUpd('+ itm.smsid +');" class="font-blue">'+itm.smstitle+'</a></p></td>';	 
					ghtml+='<td>'+itm.receiverlist+'</td>';
					ghtml+='<td>'+itm.ctime+'</td>';
					ghtml+='<td>';
					ghtml+='<a href="javascript:toUpd('+ itm.smsid +');" target="_blank" class="font-lightblue">修改</a>&nbsp;&nbsp;';
					ghtml+='<a href="javascript:;" onclick="doDelDraft('+itm.smsid+')" class="font-lightblue">删除</a>';
					ghtml+='</td>';
					ghtml+='</tr>';
				});		
			}else{
				ghtml+='<tr><th colspan="5">没有草稿记录 !</th></tr>';
			}
			
				p1.setPageSize(rps.presult.pageSize);
				p1.setPageNo(rps.presult.pageNo);
				p1.setRectotal(rps.presult.recTotal);
				p1.setPagetotal(rps.presult.pageTotal);
				p1.Refresh();
		}
		$('#maintbl').html(ghtml);
	}
	//修改
	function toUpd(smsid){
		var url="sms?m=write"+"&"+"smsid="+smsid; 
		location.href=url;
	}
	//删除
	function doDelDraft(smsid){
		
		if(!confirm('确认删除?')){return;} 
		$.ajax({
			url:'sms?m=deleteDraftSMS',
			type:'post', 
			data:{
				smsid  : smsid, 
				
			},
			dataType:'json',
			error:function(){alert("网络异常!")},  
			success:function(rps){
				if(rps.type=='success'){
					pageGo("p1");
				}else{
					alert("删除失败！！");
				}
			}
		});
	}
	
	function selAllCkObj(obj){ 
		$("input[name='cksmsid']").attr("checked",obj.checked);
	}   
	
	function selOneCkObj(){   
		var ck = $("input[name='cksmsid']");
		var cked = $("input[name='cksmsid']").filter(function(){ return this.checked==true});
		$("input[id='ck_all']").attr("checked",ck.length==cked.length);
	}   
	function selComplete(){
		//获取权限标识
		var smsidstr =$("input[name='cksmsid']").filter(function(){
			return this.checked==true});
		
		var smsidstring='',returnValue='';
		var smsidarray = [];
		if(smsidstr.length>0){
			$.each(smsidstr,function(idx,itm){
				if(smsidstring.length>0)
					smsidstring+=",";
				smsidstring += itm.value.Trim(); 
			});
		}
		if(smsidstr.length<0){
			msg='未选中数据';
		}else{ 
			msg='删除'; 
		
		}   
		if(!confirm(msg)){return;}    
			      
		$.ajax({
			url:'sms?m=delBySmsid',
			type:'get', 
			data:{
				smsidstr  : smsidstring, 
				
			},
			dataType:'json',
			error:function(){alert("网络异常!")},  
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
				}else{
					alert(rps.msg);
					pageGo("p1");
				}
			}
		});
	
	} 