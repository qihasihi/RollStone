function toAdd(){
	window.location.href='activity?m=toadd';
}
function toCheck(){
	window.location.href='activity?m=adminlist';
}
function activityListReturn(rps){
	if(rps.type=='error'){
		alert(rps.msg);
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			// 设置空间不可用
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			// 设置显示值
			var shtml = '<tr><td align="center">暂时没有校园活动信息!';
			shtml += '</td></tr>';
			$("#mainTbl").html(shtml);
		}		
	}else{
		var shtml = '';
		var nattriCount=0;
		shtml+='<tr><th>活动名称';
		if(rps.presult.orderBy=='at_name'){
			if(rps.presult.sort=='desc')
				shtml+='<a href="javascript:void(\'atname\',\'asc\')"><img src="images/page08_a.gif" border=0/></a>';
			else
				shtml+='<a href="javascript:void(\'atname\',\'desc\')><img src="images/page09_a.gif" border=0/></a>';
		}else
			shtml+='<a  href="javascript:void(\'atname\',\'desc\')><img src="images/page09_a.gif" border=0/></a>';
		shtml+='</th><th>场地';
		shtml+='</th><th>开始时间';
		shtml+='</th><th>结束时间';
		shtml+='</th><th>参与人数/受邀人数</th>';
		shtml+='<th>发起者';
		shtml+='</th><th>我的态度';
		shtml+='</th>';		
		shtml+='<th width="200">操作</th>';
		if(rps.objList.length<1){
			shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
		}else{
			$.each(rps.objList,function(idx,itm){	
				if(idx%2==0){
					shtml+='<tr class="trbg2">';
				}else{
					shtml+='<tr>';
				}
				
				var atname=itm.atname;
				if(atname.Trim().length>10)
					atname=atname.substring(0,10)+"...";
				var atstats="";
				
				if(!validateTwoDate(itm.begintimestring+":00",new Date().toFullString())){
					if(userid.Trim()==itm.cuserid){
						atstats+='<span style="font-color:red"><b>[';
						switch(parseInt(itm.state)){
							case 0:
								atstats+='已公布';
								break;
							case 1:
								atstats+='不公布';
								break;
							case 2:
								atstats+='审核中';
								break;
						}
						atstats+=']</b></span>';
					}
				}else if(validateTwoDate(itm.begintimestring+":00", new Date().toFullString())&&!validateTwoDate(itm.endtimestring+":00", new Date().toFullString())){
					atstats+='<b><span style="font-color:red">[正在进行中]</span></b>';
				}else{
					atstats+='<b><span style="font-color:red">[已结束]</span></b>';
				}
				var addressName="";	
				if(itm.siteinfo!=null){
					var arrobj=itm.siteinfo;
					$.each(arrobj,function(x,m){
						var tmp = m.sitename;
						if(tmp.length>10){
							tmp=tmp.substring(0,10)+"...";
						}
						if(userid.Trim()==itm.cuserid){//如果这个活动是我创建的。		
							addressName+='<a target="_blank" href="activitysite?m=todetail&ref='+m.ref+'" class="font-lightblue">'+tmp+'</a><br/>';
						}else{
							addressName+='<a href="javascript:loadConflictSite('+m.ref+',\'siteDetail\')" class="font-lightblue">'+tmp+'</a></br>';
						}
					});
				}
				//活动地址
				shtml+='<td><p ><b><a target="_blank" href="activity?m=todetail&ref='+itm.ref+'">'+atname+'</a></b>'+atstats+'</p></td>';
				shtml+='<td>'+addressName+'</td>';	//活动地址
				shtml+='<td>'+itm.begintimestring+'</td>';		//开始时间
				shtml+='<td>'+itm.endtimestring+'</td>';		//结束时间
				shtml+='<td>'+itm.num1+"/"+itm.num2+'</td>';	 	//确认参加者/邀请者
				shtml+='<td>'+itm.username+'</td>';		//发起者
				
				if(userid.Trim()==itm.cuserid){
					shtml+='<td>参加</td>';
				}else{
					if(itm.issign==1){
						if(typeof(itm.attitude)=="undefined"||itm.attitude==null||itm.attitude=="null"){
							shtml+='<td></td>';
						}else{
							shtml+='<td align="center">';
							//说明不是报名机制
						
								switch(parseInt(itm.attitude)){
									case 0:
										shtml+='参加';
										break;
									case 1:
										shtml+='可能参加';
										break;
									case 2:
										shtml+='不参加';
										break;
								}						
							shtml+='</td>';
						}						
					}else{//报名机制  0
						if(itm.isin==0)
							shtml+='<td>未报名</td>';
						else
							shtml+='<td>已报名</td>';
					}
				}
				shtml+='<td align="center">';
				
				
				if(!validateTwoDate(itm.begintimestring+":00",new Date().toFullString())){ 	
					if(typeof(itm.attitude)=="undefined"||itm.attitude==null||itm.attitude=="null"){
						if(userid.Trim()==itm.cuserid){				
			// <td><a href="1" class="font-lightblue">修改</a><span class="Tr Mt10">&nbsp;&nbsp;</span><a href="1" class="font-lightblue">取消</a></td>
							shtml+='<a target="_blank" href="activity?m=toupd&ref='+itm.ref+'" class="font-lightblue">修改</a>&nbsp;&nbsp;';
							shtml+='<a href="javascript:doDel(\''+itm.ref+'\')"  class="font-lightblue">删除</a>';			
						}else{
							if(itm.issign==1){
								shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',0)" class="font-lightblue">参加</a>&nbsp;&nbsp;';
								shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',1)" class="font-lightblue">可能参加</a>&nbsp;&nbsp;';
								shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',2)" class="font-lightblue">不参加</a>';
							}else{
								if(itm.isin==0){
									shtml+='<a href="javascript:do_sign(\''+itm.ref+'\')" class="font-lightblue">报名</a>';
								}else{
									shtml+='<a href="javascript:do_reset_sign(\''+itm.ref+'\')" class="font-lightblue">取消报名</a>';
								}
							}
							//没有表态  进行特殊提示
							nattriCount++; 
						}
					}else{
						if(itm.issign==1){
							switch(parseInt(itm.attitude)){
								case 0://'参加'
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',1)" class="font-lightblue">可能参加</a>&nbsp;&nbsp;';
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',2)" class="font-lightblue">不参加</a>';
									break;
								case 1://'可能参加'
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',0)" class="font-lightblue">参加</a>&nbsp;&nbsp;';
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',2)" class="font-lightblue">不参加</a>';
									break;
								case 2://'不参加'
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',0)" class="font-lightblue">参加</a>&nbsp;&nbsp;';
									shtml+='<a href="javascript:updateAttitude(\''+itm.ref+'\',1)" class="font-lightblue">可能参加</a>';
									break;
							}
						}else{
							if(itm.isin==0){
								shtml+='<a href="javascript:do_sign('+itm.ref+')" class="font-lightblue">报名</a>';
							}else{
								shtml+='<a href="javascript:do_reset_sign('+itm.ref+')" class="font-lightblue">取消报名</a>';
							}
						}
					}
				}
				shtml+='</td></tr>'; 
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

/**
 * 加载冲突情况
 * @param sref
 * @param sdiv
 * @return
 */
function loadConflictSite(sref,sdiv){
	if(typeof(sref)=="undefined"||isNaN(sref)){
		alert('异常错误!系统尚未获取到您要加载的场地标识!请刷新页面后重试!');
		return;
	}
	$.ajax({
		url:"activity?m=cksite",
		dataType:'json',
		type:"post",
		cache: false,
		data:{siteId:sref,returnType:'json'},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){			
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var shtml="<colgroup span='3' class='w270'></colgroup>";
				if(rps.objList.length<1){
					shtml+='<tr><th>该场地暂无活动信息!不存在冲突情况!</th></tr>';
				}else{
					//shtml+='<colgroup class="W310"></colgroup>';
					//shtml+='<colgroup class="W370"></colgroup>';
					//shtml+='<colgroup  class="W310"></colgroup>';
					shtml+='<tr><th>活动名称</th><th>活动时间</th>';
					shtml+='<th>冲突活动名称</th></tr>';
					$.each(rps.objList,function(ix,itm){
						if(typeof(itm.oldname)!="undefined"&&
								typeof(itm.oldref)!="undefined"&&
								itm.oldname!=null&&itm.oldname!="null"
									&&itm.oldref!=null&&itm.oldref!="null"){
							shtml+='<tr style="color:red;">';
						}else 
							if(ix%2==0){
								shtml+='<tr class="trbg2">';
							}else{
								shtml+='<tr>';
							}
						var atname=itm.atname;
						if(atname.Trim().length>10)
							atname=atname.substring(0,10)+'...';
						shtml+='<td><a target="_blank" href="">'+atname+'</a></td>';
						shtml+='<td>'+itm.begintimestring+'~'+itm.endtimestring+'</td>';
						var clshtml="";						
						if(typeof(itm.oldname)!="undefined"&&itm.oldname!=null&&typeof(itm.oldref)!="undefined"&&itm.oldref!=null){
							var tmpName = itm.oldname
							if(tmpName.Trim().length>10)
								atname=tmpName.substring(0,10)+'...';
							clshtml='<span><a style="color:red" target="_blank" href="">'+tmpName+'</a></span></a>'
												
						}else
							clshtml='暂无';
						shtml+='<td>'+clshtml+'</td>';
						shtml+='</tr>';
					});					
				}
				$("#conflictSiteTbl").html(shtml);
				if($("#"+sdiv).css("display")!="block"){
					showModel(sdiv,'fade',false);
				}
			}
		}
	});
	
}

/**
 * 删除
 * @param ref
 * @return
 */
function doDel(ref){
	if(typeof(ref)=="undefined"){
		alert('异常错误!您要删除的活动标识错误!请刷新页面后重试!');
		return;
	}
	
	if(!confirm('活动删除后，系统将不会保留记录，请谨慎操作！\n\n您确认删除吗？'))
		return;
	$.ajax({
		url:'activity?m=dodel',
		dataType:'json',
		type:"post",
		cache: false,
		data:{ref:ref,returnType:'json'},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="success"){
				pageGo('p1');
				alert(rps.msg);
			}else{
				alert(rps.msg);
			}
		}
	});
} 

/**
 *  报名
 * @param ac_id 活动ID
 * @return
 */
function do_sign(ac_id){
	if(typeof(ac_id)!="undefined"){
		$.ajax({
			url:'activityuser?m=ajaxsave',
			dataType:'json',
			type:"post",
			cache: false,
			data:{activityid:ac_id,returnType:'json'},
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
				}else{
					pageGo('p1');
					alert(rps.msg);					
				}
			}			
		});		
	}	
}
/**
 * 取消报名
 * @param ac_id
 * @return
 */
function do_reset_sign(ac_id){
	if(typeof(ac_id)!="undefined"){
		$.ajax({
			url:'activityuser?m=ajaxdel',
			dataType:'json',
			type:"post",
			cache: false,
			data:{activityid:ac_id,returnType:'json'},
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
				}else{
					pageGo('p1');
					alert(rps.msg);					
				}
			}			
		});		
	}	
	
}
/**
 * 修改我的态度
 * @param ref
 * @param attRef
 * @return
 */
function updateAttitude(ref,attRef){
	if(typeof(ref)=="undefined"){
		alert('异常错误!系统尚未获取到您要操作的数据标识!请刷新页面后重试!');
		return;
	}
	if(typeof(attRef)=="undefined"||isNaN(attRef)){
		alert('异常错误!系统尚未获取到您要操作的态度标识!请刷新页面后重试!');
		return;
	}
	//var u="activityuser!doUpdate.action?returnType=json";
	$.ajax({
		url:'activityuser?m=ajaxupd',
		dataType:'json',
		type:"post",
		cache: false,
		data:{attitude:attRef,activityid:ref},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//刷新页面列表
				alert(rps.msg);
				pageGo('p1');
			}
		}
	});
}

//=================================================================管理员
function validateAdminParam(tobj){
	if(typeof(tobj)!="object"){
		alert('异常错误!请刷新页面后重试!');
		return;
	}
	var param=new Object();
	
	var atname=$("#at_name").val().Trim();
	if(atname.length>0)
		param.atName=atname;
	var state=$("#shState").val().Trim();
	if(state.length>0)
		param.state=state;
	tobj.setPostParams(param);
}
function activityAdminReturn(rps){
	if(rps.type=="error"){
		alert(rps.msg);
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			// 设置空间不可用
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			// 设置显示值
			var shtml = '<tr><td align="center">暂时没有校园活动信息!';
			shtml += '</td></tr>';
			$("#mainTbl").html(shtml);
		}		
	}else{
		
		var shtml = "";var attriCount=0;		
		
		shtml+='<tr><th>活动名称';
		if(rps.presult.orderBy=='at_name'){
			if(rps.presult.sort=='desc')
				shtml+='<a href="javascript:void(\'at_name\',\'asc\')"><img src="images/page08_a.gif" border=0/></a>';
			else
				shtml+='<a href="javascript:void(\'at_name\',\'desc\')><img src="images/page09_a.gif" border=0/></a>';
		}else
			shtml+='<a  href="javascript:void(\'at_name\',\'desc\')><img src="images/page09_a.gif" border=0/></a>';
		shtml+='</th><th>场地';
		
		shtml+='</th><th>开始时间';
		shtml+='</th><th>结束时间';
		shtml+='</th><th>邀请人数</th>';
		shtml+='<th>发起者';
		shtml+='</th><th>操作</th>';
		if(rps.objList.length<1){
			shtml+="<tr><th colspan='7' style='height:65px' align='center'>暂无信息!</th></tr>";
		}else{					
			$.each(rps.objList,function(idx,itm){
				var isbegin=false;
				if(idx%2==0){
					shtml+='<tr class="trbg2">';
				}else{
					shtml+='<tr>';
				}
					var aname=itm.atname.Trim();
						if(aname.length>10)
							aname=aname.substring(0,10)+"...";
					var st="";
					if(validateTwoDate(itm.begintimestring+":00", new Date().toFullString())&&	!validateTwoDate(itm.endtimestring+":00", new Date().toFullString())){
						st="<span class='font-red'>[正在进行中]</span>";
						isbegin=true;  
					}else if(validateTwoDate(itm.endtimestring+":00", new Date().toFullString())){
						st="<span class='font-red'>[已结束]</span>";
						isbegin=true;
					}else 
						st="<span class='font-red'>[未开始]</span>";
						shtml+=' <td ><p><b><a target="_blank" href="activity?m=todetail&ref='+itm.ref+'" class="font-lightblue">'+aname+'</a></b>'+st+'</p></td>';
						//shtml+='<td><p>'+aname+st+'</p></td>';
					//检测是否有冲突
					
						var tico='<img src="images/an34.gif" width="16" alt="活动时间存在冲突" height="15" class="Ml6" />';
						var addressName="";			//活动地址
						var arrobj=itm.siteinfo;
						$.each(arrobj,function(x,m){
							var tmp=m.sitename;
							if(tmp.length>10)
								tmp=tmp.substring(0,10)+"...";
							if(userid.Trim()==itm.cuserid){//如果这个活动是我创建的。		
								addressName+='<a target="_blank" href="activitysite?m=todetail&ref='+m.ref+'" class="font-lightblue">'+tmp+'</a><br/>';
							}else{
								addressName+='<a href="javascript:loadConflictSite('+m.ref+',\'siteDetail\')" class="font-lightblue">'+tmp+'</a>';
							}//addressName+=tmp;
							//if(parseInt(m.split(',')[2])>0)
								//addressName+=tico;							
							addressName+='<br/>';
						});
						
						shtml+=' <td align="center"><p>'+addressName+'</p></td>';
					
						shtml+='<td align="center">'+itm.begintimestring+'</td>';
						shtml+='<td align="center">'+itm.endtimestring+'</td>';
						shtml+='<td align="center">'+itm.num2+'</td>';
						shtml+='<td align="center">'+itm.username+'</td>';
						shtml+='<td align="center">';
					
							if(parseInt(itm.state)==0){   //已公布
								shtml+='<p>已公布</span><span class="Plr10">|</span>';
								if(!validateTwoDate(itm.begintimestring+":00", new Date().toFullString())){
									if(isbegin==false)
										shtml+='<a href="javascript:changeState(\''+itm.ref+'\',1)" class="font-lightblue">不公布</a>';
									else
										shtml+='不公布';
								}else{
									shtml+='&nbsp;&nbsp;&nbsp;';
								}
								shtml+='</p></td>';
							}else if(parseInt(itm.state)==1){
								shtml+='<p>不公布</span><span class="Plr10">|</span>';
								if(!validateTwoDate(itm.begintimestring+":00", new Date().toFullString())){
									if(isbegin==false)
										shtml+='<a href="javascript:changeState(\''+itm.ref+'\',0)" class="font-lightblue">公布</a>';
									else
										shtml+='公布';
								}else{
									shtml+='&nbsp;&nbsp;&nbsp;';
								}
								shtml+='</p>';
							}else if(parseInt(itm.state)==2){
								shtml+='<p>未审核</span><span>|</span>';
								if(!validateTwoDate(itm.begintimestring+":00", new Date().toFullString())){
									if(isbegin==false){
										attriCount++;
										shtml+='<a href="javascript:changeState(\''+itm.ref+'\',0)" class="font-lightblue">公布</a>&nbsp;';
										shtml+='<a href="javascript:changeState(\''+itm.ref+'\',1)" class="font-lightblue">不公布</a>&nbsp;';
									}else{
										shtml+='公布&nbsp;不公布';
									}
								}else{
									shtml+='&nbsp;&nbsp;&nbsp;';
								}
								shtml+='</p>';
							}
						
						shtml+='</td>';
						shtml+='</tr>';
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
		
		if(attriCount>0){
			$("#pt_new").html('<p class="Pb10">校园活动管理员，您有<font color="red">'+attriCount+'</font>个活动需要进行审核 !</p>');
			$("#pt_new").show('fast');
		}else{
			$("#pt_new").hide('fast');
		}
	}
}
/**
 * 条件查询
 * @param ref
 * @param state
 * @return
 */
function getListByParam(){
	var param = {};
	var name = $("#at_name").val();
	if(name.length>0){
		param.atname=name;
	}
	var state = $("#shState").val();
	if(state.length>0){
		param.state = state;
	}
	$.ajax({
		url:'activity?m=ajaxadminlist',
		dataType:'json',
		type:"post",
		cache: false,
		data:param,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			
				activityAdminReturn(rps);
			
		}
	});
}
/**
 * 修改状态
 * @param ref
 * @param state
 * @return
 */
function changeState(ref,state){
	if(typeof(ref)=="undefined"){
		alert('异常错误!尚未获取到活动标识!请刷新页面后重试!');
		return;
	}
	if(typeof(state)=="undefined"||isNaN(state)){
		alert('异常错误!尚未获取到状态标识!请刷新页面后重试!');
		return;
	}
	//开始执行
	$.ajax({
		url:'activity?m=changestate',
		dataType:'json',
		type:"post",
		cache: false,
		data:{ref:ref,state:state,returnType:'json'},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				alert(rps.msg)
				//刷新页面列表
				pageGo('p1');
			}
		}
	});
}
