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
		var ghtml='<tr><th>&nbsp;</th><th>信息主题</th><th>信息接收者<img src="images/an23_130423.png" width="9" height="10" /></th>'
			+'<th>发送时间<img src="images/an22_130423.png" width="9" height="10" /></th></tr>';
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr>';
					ghtml+='<td><input onclick="selOneCkObj()" type="checkbox" value="'+itm.smsid+'" name="cksmsid"/></td>';
					ghtml+='<td><p><a href="sms?m=viewSentSMS&smsid='+itm.smsid+'" class="font-blue">'+itm.smstitle+'</a></p></td>';
					ghtml+='<td>'+itm.receiverlist+'</td>';
					ghtml+='<td>'+itm.mtime+'</td>';
					/***********功能能权限**************/  
					ghtml+='</tr>';
				});		
			}else{
				ghtml+='<tr><th colspan="4">暂无数据 !</th></tr>';
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
		var ghtml='<tr><th>&nbsp;</th><th>状态<img src="images/an23_130423.png" width="9" height="10" /></th><th>信息主题</th>'
        +'<th>信息发送者<img src="images/an23_130423.png" width="9" height="10" /></th><th>接收时间<img src="images/an22_130423.png" width="9" height="10" /></th></tr>';
      
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr><td><input type="checkbox" value="'+itm.ref+'" name="cksmsid"/></td>';
					if(itm.status==0)
						ghtml+='<td><img src="images/an01_131009.gif" width="14" height="10" /></td>';
					else
						ghtml+='<td><img src="images/an02_131009.gif" width="14" height="10" /></td>';
					ghtml+='<td><p><a href="sms?m=viewReceiverSMS&ref='+itm.ref+'" class="font-blue">';
					ghtml+=itm.smstitle.length>20?(itm.smstitle.substring(0,20)+"..."):itm.smstitle+'</a></p></td>';
					ghtml+='<td>'+itm.sendername+'</td>';
					ghtml+='<td>'+itm.ctime+'</td>';
					/***********功能能权限**************/
					ghtml+='</tr>';
				});		
			}else{
				ghtml+='<tr><th colspan="5">暂无数据 !</th></tr>';
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
				smsidstr  : smsidstring
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
		if(smsreceiver.indexOf("；")>=0){
			alert("接收人之间请用“半角”分号隔开（英文输入分号）！");
			return ;			
		}
		if(smsreceiver.Trim().length<1){
			alert("请您填写收件人！");
			return;
		}
		if(smstitle.Trim().length<1){
			alert("请您填写主题！");
			return;
		}
		if(smscontent.Trim().length<1){
			alert("请您填写内容！");
			return;
		}
		if(typeof(smsid)==undefined||isNaN(smsid)||smsid.length<1){
			smsid=0;
		}
		$.ajax({
			url:'sms?m=ajaxsave',
			type:'POST',
			data:{ receiverlist:smsreceiver,
			       smstitle:smstitle,
			       smscontent:smscontent,
			       smsid:smsid
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
				smsidstr  : smsid				
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
			       smsid:smsid
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
	function doDelReceiveSMS(smsid){
		var smsids = new Array();
		if(smsid==null)
			$("input[type='checkbox'][name='cksmsid']:checked").each(function() {smsids.push($(this).val());});
		else
			smsids.push(smsid);
		
		if(!confirm('确认删除?')){return;} 
		$.ajax({
			url:'sms?m=deleteReceiveSMS',
			type:'post', 
			data:{
				smsids  : smsids.join('|')				
			},
			dataType:'json',
			error:function(){alert("网络异常!")},  
			success:function(rps){
				if(rps.type=='success'){
					if(smsid!=null)
						window.location.href="sms?m=inbox";
					pageGo("p1");
				}else{
					alert("删除失败！！");
				}
			}
		});
	}
	
	//删除
	function doDelSentSMS(smsid){
		var smsids = new Array();
		if(smsid==null)
			$("input[type='checkbox'][name='cksmsid']:checked").each(function() {smsids.push($(this).val());});
		else
			smsids.push(smsid);
		if(!confirm('确认删除?')){return;} 
		$.ajax({
			url:'sms?m=deleteSentSMS',
			type:'post', 
			data:{
				smsids  : smsids.join('|')
			},
			dataType:'json',
			error:function(){alert("网络异常!")},  
			success:function(rps){
				if(rps.type=='success'){
					if(smsid!=null)
						window.location.href="sms?m=sent";
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
	
	function checkReceiverList(receList){
		if(receList.indexOf("；")){
			return false;
		}
	}