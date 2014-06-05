	//AJAX查询之前的方法
	function beforajaxGetSentList(p){
		var param = {};
		var keyword =$("#sel_keyword").val();
		if(keyword.Trim().length>0){
			param.smstitle = keyword;
			param.sender = keyword;
		}
		p.setPostParams(param);
	}

	//执行分页查询后
	function afterajaxGetSentList(rps){
		var ghtml='';
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				ghtml+='<tr><th><input type="checkbox" id="ck_all" onclick="selAllCkObj(this)" />全选/不选 </th>'; 
				ghtml+='<th>状态</th><th>信息主题</th>';
				ghtml+='<th>接收者</th><th>发送时间</th></tr>';				
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr>';
					ghtml+='<td><input onclick="selOneCkObj()" type="checkbox" value="'+itm.smsid+'" name="cksmsid"/></td>'; 
					if(itm.status==0)
						ghtml+='<td>未查看</td>';
					else
						ghtml+='<td>已查看</td>';
					ghtml+='<td><a href="sms?m=viewReceiverSMS&ref='+itm.ref+'">'+itm.smstitle+'</a></td>';	 
					ghtml+='<td>'+itm.receiverlist+'</td>';
					ghtml+='<td>'+itm.mtime+'</td>';
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
	
	//AJAX查询之前的方法
	function beforajaxGetReceiveList(){
		var param = {};
		var keyword =$("#sel_keyword").val();
		if(keyword.Trim().length>0)
			param.smstitle = keyword;
		else
			param.smstitle = "";
		p1.setPostParams(param);
	}

	//执行分页查询后
	function afterajaxGetReceiveList(rps){
		var ghtml='';
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				ghtml+='<tr><th><input type="checkbox" id="ck_all" onclick="selAllCkObj(this)" />全选/不选 </th>'; 
				ghtml+='<th>状态</th><th>信息主题</th>';
				ghtml+='<th>信息发送者</th><th>接收时间</th></tr>';				
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr>';
					ghtml+='<td><input onclick="selOneCkObj()" type="checkbox" value="'+itm.ref+'" name="cksmsid"/></td>'; 
					if(itm.status==0)
						ghtml+='<td>未查看</td>';
					else
						ghtml+='<td>已查看</td>';
					ghtml+='<td><a href="sms?m=viewReceiverSMS&ref='+itm.ref+'">'+itm.smstitle+'</a></td>';	 
					ghtml+='<td>'+itm.sendername+'</td>';
					ghtml+='<td>'+itm.ctime+'</td>';
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
	//添加
	function toSend(){
		var smsid=$("#sms_id").val();
		var smsreceiver = $("#sel_reciever").val();
		var smstitle = $("#sel_smstitle").val();
		var smscontent = $("#sel_smscontent").val();
		if(smsreceiver.Trim().length<1){
			alert("请您填写收件人！");
			return;
		}else if(smstitle.Trim().length<1){
			alert("请您填写主题！");
			return;
		}else if(smscontent.Trim().length<1){
			alert("请您填写内容！");
			return;
		}
		if(typeof(smsid)=="undefined"||isNaN(smsid)){
			smsid=0;
		}
		$.ajax({
			url:'sms?m=ajaxsave',
			type:'POST',
			data:{ receiverlist:smsreceiver,
			       smstitle:smstitle,
			       smscontent:smscontent,
			       smsid:smsid,
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}
				if(rps.type=="error1"){
					alert(rps.msg);
					return;
				}
				if(rps.type=="error2"){
					alert(rps.msg);
					return;
				}
			
				alert(rps.msg);
				location.href='sms?m=inbox';
			
			}
		});
	}
	//重置
	function reset(div){
		$("#"+div).val("");
	}
	
	function  submit(id){
		var url="sms?m=add";
		if(typeof(id)!="undefined"&&!isNaN(id)&&id.Trim().length>0){
			url="sms?m=upd";
		}
		if(!confirm('确认操作?')){return;} 
		$.ajax({
			url:url,
			type:'post', 
			data:{
				smsidstr  : smsid, 
				
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
	
	
	
	//存草稿
	function toDraft(smsid){
		var smsreceiver = $("#sel_reciever").val();
		var smstitle = $("#sel_smstitle").val();
		var smscontent = $("#sel_smscontent").val();
		var smsstatus=0;
		var url="sms?m=ajaxsavetodraft";
		if(typeof(smsid)!="undefined"&&!isNaN(smsid)&&smsid.Trim().length>0){
			url="sms?m=ajaxupdtodraft";
		}else{
			smsid=0;
		}
	//	if(!confirm('确认操作?')){return;} 
		if(smsreceiver.Trim().length<1){
			alert("请您填写收件人！");
			return;
		}else if(smstitle.Trim().length<1){
			alert("请您填写主题！");
			return;
		}else if(smscontent.Trim().length<1){
			alert("请您填写内容！");
			return;
		}
	
		$.ajax({
			url:url,
			type:'POST',
			data:{ receiverlist:smsreceiver,
			       smstitle:smstitle,
			       smscontent:smscontent,
			       smsstatus:smsstatus,
			       smsid:smsid,
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}	
				alert(rps.msg);
				location.href='sms?m=inbox';
			
			}
		});
	}
	//获取从草稿箱跳转到写信息的短信id
	function getSmsId() 
	{ 
	var url=location.href; 
	var tmp1=url.split("?")[1]; 
	var tmp2=tmp1.split("&")[1]; 
	var tmp3=tmp2.split("=")[1]; 
	var smsid=tmp3; 
	
	$.ajax({
		url:'sms?m=writelist',
		type:'post', 
		data:{
			smsid  : smsid
		},
		dataType:'json',
		error:function(){alert("网络异常!")},  
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
			}else{
				if(rps.objList.length>0){
					alert(rps.objList[0].smsid);
					$("#sel_reciever").val(rps.objList[0].receiver);
					$("#sel_smstitle").val(rps.objList[0].smstitle);
					$("#sel_smscontent").val(rps.objList[0].smscontent);	
				}
			}
			
			
		}
	});
	} 
	
	//删除
	function doDelReceiveSMS(){
		var smsids = new Array();
		$("input[type='checkbox'][name='cksmsid']:checked").each(function() {smsids.push($(this).val());});
		if(!confirm('确认删除?')){return;} 
		$.ajax({
			url:'sms?m=deleteSentSMS',
			type:'post', 
			data:{
				smsids  : smsids.join('|'), 
				
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
	
	//删除
	function doDelSentSMS(){
		var smsids = new Array();
		$("input[type='checkbox'][name='cksmsid']:checked").each(function() {smsids.push($(this).val());});
		if(!confirm('确认删除?')){return;} 
		$.ajax({
			url:'sms?m=deleteSentSMS',
			type:'post', 
			data:{
				smsids  : smsids.join('|'), 
				
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
	
	
	function toReply(smsid){
		var url="sms?m=write&type=reply"+"&"+"smsid="+smsid; 
		location.href=url;
	}
	
	function toForward(smsid){
		var url="sms?m=write&type=forward"+"&"+"smsid="+smsid; 
		location.href=url;
	}