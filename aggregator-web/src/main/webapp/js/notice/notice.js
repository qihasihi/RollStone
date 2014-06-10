function noticeListReturn(rps){
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
		shtml+='<tr><th><input type="checkbox" name="ckAll" id="ckAll"  onclick="checkCKboxToList(this,\'ckNoticeId\')" />全选</th>';
		shtml+=	'<th>公告标题</th><th>发布人</th><th>发布时间</th><th>是否置顶</th><th>点击率</th><th>操作</th></tr>';
		if(rps.objList.length<1){
			shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
		}else{
			$.each(rps.objList,function(idx,itm){
				var type = '';
				if(itm.noticetype==0){
					type="通知公告";
				}else if(itm.noticetype==1){
					type="网上公示";
				}else if(itm.noticetype==2){
					type="校内参考";
				}else{
					type="资源公告";
				}
				var realname = '';
				if(itm.realname==null){
					realname="  ";
				}else{
					realname=itm.realname;
				}
				var istop = '';
				if(itm.istop==0){
					istop="是";
				}else{
					istop="否";
				}
				if(idx%2==0){
					shtml+='<tr class="trbg2">';
				}else{
					shtml+='<tr>';
				}
				
				shtml+='<td><input type="checkbox" value="'+itm.ref+'" onclick="childCheckChange(this,\'ckAll\')" name="ckNoticeId" id="ckNoticeId"/></td>';
				shtml+='<td>'+itm.noticetitle+'</td>';
				shtml+='<td>'+realname+'</td>';
				shtml+='<td>'+itm.ctimestring+'</td>';
				shtml+='<td>'+istop+'</td>';
				shtml+='<td>'+itm.clickcount+'</td>';
				shtml+='<td><a href="javascript:showUpdDiv(\'updDiv\',\''+itm.ref+'\')"  class="ico11" title="编辑"></a>&nbsp;&nbsp;<a href="javascript:deleteNotice(\''+itm.ref+'\')"  class="ico04" title="删除"></a></td>';
				
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

function noticeUserListReturn(rps){
	if(rps.type=='error'){
		alert(rps.msg);
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			// 设置空间不可用
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			// 设置显示值
			var shtml = '<li>暂时没有通知公告信息!</li>';
			$("#mainList").html(shtml);
		}		
	}else{
		var shtml = '';
		if(rps.objList.length<1){
			shtml+='<li>暂时没有通知公告信息!</li>';
		}else{
			$.each(rps.objList,function(idx,itm){
				shtml+='<li>';
                shtml+='<b>'+itm.ctimestring+'</b>';
				if(itm.titlelink!=null&&itm.titlelink!=""){
					shtml+='<a onclick="click(\''+itm.ref+'\')" href="'+itm.titlelink+'" >'+itm.noticetitle+'</a>';
					
				}else{
					shtml+='<a href="notice?m=detail&ref='+itm.ref+'">'+itm.noticetitle+'</a>';
				}
				
			});
		}
		$("#mainList").hide();
		$("#mainList").html(shtml);
		$("#mainList").show('fast');
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

function click(ref){
	$.ajax({
		url:'notice?m=doclick',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{ref:ref
	    },
		cache: false,
		error:function(){
			//alert('异常错误!系统未响应!');
		},success:function(rps){
		}
	});
}

function deleteNotice(ref){
	if(!confirm('公告删除后，系统将不会保留记录，请谨慎操作！\n\n您确认删除吗？'))
		return;
	$.ajax({
		url:'notice?m=dodel',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{ref:ref
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			alert(rps.msg)
			pageGo(p1);
		}
	});
}

//提交  发布信息  验证
function onSubmitdoAdd(){
    var param={};
    var title = $("#atitle").val();
    if(title.Trim().length>0){
        param.noticetitle=title;
    }else{
        alert("请填写公告标题");
        return;
    }
    var istitlelink = $("#atitleLinkck").attr("checked");
    var titlelink = $("#atitlelink").val();
    if(istitlelink){
        if(titlelink.Trim().length>0){
            param.titlelink=titlelink;
        }else{
            alert("请填写标题连接");
            return;

        }
    }
    var istime = $("#aistime").attr("checked");
    var begintime = $("#abeginTime").val();
    var endtime = $("#aendTime").val();
    if(istime){
        if(begintime.Trim().length<0||endtime.Trim().length<0){
            alert("请填写限制时间");
            return;
        }
        param.istime=0;
    }
    if(istime){
        if(!validateTwoDate(document.getElementById("abeginTime").value.Trim()+" 00:00:00",document.getElementById("aendTime").value.Trim()+" 00:00:00")){
           alert("您填的有效开始时间晚于或等于有效结束时间!~请重新填写!");
           return;
        }
        param.begintimeString=begintime;
        param.endtimeString=endtime;
    }
    var content = $("#acontent").val();
    if(content.Trim().length<0&&!istitlelink){
        alert("请填写内容");
        return;
    }
    param.noticecontent=content;
    var roleArray=$("input[name='aroleId']");
    if(roleArray.length==1){
        roleflag=roleArray.checked;
        if(!roleflag){
            alert("您尚选择浏览角色用户!~请选择!");
            return;
        }
    }else if(roleArray.length>1){
        var fl=false;
        for ( var z = 0; z < roleArray.length; z++) {
            if(roleArray[z].checked){
                fl=true;
                break;
            }
        }
        if(fl==false){
           alert("您尚选择浏览角色用户!~请选择!");
            return;
        }
    }


    var istop = $("#aisTop").attr("checked")
    if(istop==true){
        param.istop = 0;
    }
    var roleArray = $("input[name='aroleId']").filter(function(){
        return this.checked==true;
    });
    var roles = "";
    if(roleArray.length>0){
//				for(var i = 0;i<roleArray.length;i++){
//					roles+=roleArray[i].val()+"!";
//				}
        $.each(roleArray,function(idx,itm){
            if(itm!=null&&itm.value.Trim().length>0){
                if(roles.Trim().length>0)
                    roles+="|";
                roles+=itm.value.Trim();
            }
        });
    }
    param.noticerole=roles;
    $.ajax({
        url:'notice?m=doadd',//cls!??.action
        dataType:'json',
        type:'POST',
        data:param,
        cache: false,
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            alert(rps.msg);
            closeModel('addDiv','hide');
            pageGo('p1');
        }
    });
}
//提交  发布信息  验证
function onSubmitdoUpd(){
    var param={};
    param.ref=$("#ref").val();
    var title = $("#utitle").val();
    if(title.Trim().length>0){
        param.noticetitle=title;
    }else{
        alert("请填写公告标题");
        return;
    }
    var istitlelink = $("#utitleLinkck").attr("checked");
    var titlelink = $("#utitlelink").val();
    if(istitlelink){
        if(titlelink.Trim().length>0){
            param.titlelink=titlelink;
        }else{
            alert("请填写标题连接");
            return;

        }
    }
    var istime = $("#uistime").attr("checked");
    var begintime = $("#ubeginTime").val();
    var endtime = $("#uendTime").val();
    if(istime){
        if(begintime.Trim().length<0||endtime.Trim().length<0){
            alert("请填写限制时间");
            return;
        }
        param.istime=0;
        if(!validateTwoDate(document.getElementById("ubeginTime").value.Trim()+" 00:00:00",document.getElementById("uendTime").value.Trim()+" 00:00:00")){
            alert("您填的有效开始时间晚于或等于有效结束时间!~请重新填写!");
            return;
        }
        param.begintimeString=begintime;
        param.endtimeString=endtime;
    }
    var content = $("#ucontent").val();
    if(content.Trim().length<0&&!istitlelink){
        alert("请填写内容");
        return;
    }
    param.noticecontent=content;
    var roleArray=$("input[name='uroleId']");
    if(roleArray.length==1){
        roleflag=roleArray.checked;
        if(!roleflag){
            alert("您尚选择浏览角色用户!~请选择!");
            return;
        }
    }else if(roleArray.length>1){
        var fl=false;
        for ( var z = 0; z < roleArray.length; z++) {
            if(roleArray[z].checked){
                fl=true;
                break;
            }
        }
        if(fl==false){
            alert("您尚选择浏览角色用户!~请选择!");
            return;
        }
    }


    var istop =$("#uisTop").attr("checked");
    if(istop==true){
        param.istop = 0;
    }else{
        param.istop=1;
    }
    var roleArray = $("input[name='uroleId']").filter(function(){
        return this.checked==true;
    });
    var roles = "";
    if(roleArray.length>0){
//				for(var i = 0;i<roleArray.length;i++){
//					roles+=roleArray[i].val()+"!";
//				}
        $.each(roleArray,function(idx,itm){
            if(itm!=null&&itm.value.Trim().length>0){
                if(roles.Trim().length>0)
                    roles+="|";
                roles+=itm.value.Trim();
            }
        });
    }
    param.noticerole=roles;
    $.ajax({
        url:'notice?m=doupd',//cls!??.action
        dataType:'json',
        type:'POST',
        data:param,
        cache: false,
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            alert(rps.msg);
            closeModel('updDiv','hide');
            pageGo('p1');
        }
    });
}
//function atypeSelect(tdid){
//	$("#"+tdid).empty();
//	if($('#atype').val()=="1"){
//		$("#"+tdid).append("<td>有效时间:</td>"+
//	    			"<td><input type=\"text\" class=\"public_input w200\" id=\"abeginTime\" name=\"abeginTime\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})\"  readonly=\"readonly\" style=\"cursor:text\" />"+
//	    			" &mdash;&mdash;"+
//	    			"<input type=\"text\" class=\"public_input w200\" id=\"aendTime\" name=\"aendTime\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})\"  readonly=\"readonly\" style=\"cursor:text\"/></td>"
//	    			);
//	}
//}
//
//function rtypeSelect(tdid){
//	$("#"+tdid).empty();
//	if($('#rtype').val()=="1"){
//		$("#"+tdid).append("<td>有效时间:</td>"+
//	    			"<td><input type=\"text\" class=\"public_input w200\" id=\"rbeginTime\" name=\"rbeginTime\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})\"  readonly=\"readonly\" style=\"cursor:text\" />"+
//	    			" &mdash;&mdash;"+
//	    			"<input type=\"text\" class=\"public_input w200\" id=\"rendTime\" name=\"rendTime\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})\"  readonly=\"readonly\" style=\"cursor:text\"/></td>"
//	    			);
//	}
//}

function aisTitleLinkEditor(ck){
	var flag=ck.checked;
	var htvalue=$("#acontent").val().Trim();
	$("#atdContent").empty();
	var ht="<textarea name=\"acontent\" style=\"height:50px;display:none\" id=\"acontent\" cols=\"70\"></textarea>"
	$("#atdContent").append(ht);		
	if(!flag){
		$("#acontent").val(htvalue);
		$("#acontent").css('display',"block");
		//$("#acontent").css('height',"300px");
		//$("#trSavle").show('fast');
		$('#acontent')
		.xheditor(
				{
					tool : "mfull",
					html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
					upLinkUrl : "{editorRoot}upload.jsp",
					upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
					upImgUrl : "{editorRoot}upload.jsp",
					upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
					upFlashUrl : "{editorRoot}upload.jsp",
					upFlashExt : "swf,SWF"
				//	,upMediaUrl : "{editorRoot}upload.jsp",
				//	upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
				// onUpload:insertUpload //指定回调涵数
				});
	}else{	
		$("#acontent").val("http://");
		
		
		$("#acontent").show('fast');
		
	}
	
}
function risTitleLinkEditor(ck){
	var flag=ck.checked;
	var htvalue=$("#ucontent").val().Trim();
	$("#utdContent").empty();
	var ht="<textarea name=\"ucontent\" style=\"height:50px;display:none\" id=\"ucontent\" cols=\"70\"></textarea>"
	$("#utdContent").append(ht);
	if(!flag){
		$("#ucontent").val(htvalue);
		$("#ucontent").css('display',"block");
		//$("#rcontent").css('height',"300px");
		//$("#trSavle").show('fast');
		$('#ucontent')
		.xheditor(
				{
					tool : "mfull",
					html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
					upLinkUrl : "{editorRoot}upload.jsp",
					upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
					upImgUrl : "{editorRoot}upload.jsp",
					upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
					upFlashUrl : "{editorRoot}upload.jsp",
					upFlashExt : "swf,SWF"
				//	,upMediaUrl : "{editorRoot}upload.jsp",
				//	upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
				// onUpload:insertUpload //指定回调涵数
				});
	}else{	
		$("#rcontent").val("http://");
		
		
		$("#rcontent").show('fast');
		
	}
	
}

function arolechecked(rid,rname){
	if(typeof(rid)=="undefined"||rid==null||isNaN(rid)){
		alert('异常错误!系统未成功获取角色标识!请刷新页面后重试!');
		return;
	}
	//选中的
	var ckrole=$("input[name='aroleId']").filter(function(){
		return this.checked==true;
	});
	var stuid=1;
	var ckSign = $("input[name='aroleId']").filter(function(){
		return (this.checked==true&&this.value==stuid);
	});
	if(ckSign.length>0){
		//说明已经选中(显示年级，班级选项)
		if(rid==stuid){
			$("tr").filter(function(){
				return this.id.Trim()=='atr_student_grade';
			}).show('fast');
		}
	}else{
		//没有选中  (掩藏年级，班级选项)
		var gradeArray = $("input[name='ack_grade']").filter(function(){
			return this.checked==true;
		});
		if(gradeArray.length>0){
			for(var i = 0;i<gradeArray.length;i++){
				gradeArray[i].checked=false;
			}
		}
		$("tr").filter(function(){
			return this.id.Trim().indexOf('atr_student')!=-1;
		}).hide('fast');
		
	}
}
function rrolechecked(rid,rname){
	if(typeof(rid)=="undefined"||rid==null||isNaN(rid)){
		alert('异常错误!系统未成功获取角色标识!请刷新页面后重试!');
		return;
	}
	//选中的
	var ckrole=$("input[name='urroleId']").filter(function(){
		return this.checked==true;
	});
	var stuid=1;
	var ckSign = $("input[name='uroleId']").filter(function(){
		return (this.checked==true&&this.value==stuid);
	});
	if(ckSign.length>0){
		//说明已经选中(显示年级，班级选项)
		if(rid==stuid){
			$("tr").filter(function(){
				return this.id.Trim()=='utr_student_grade';
			}).show('fast');
		}
	}else{
		//没有选中  (掩藏年级，班级选项)
		var gradeArray = $("input[name='uck_grade']").filter(function(){
			return this.checked==true;
		});
		if(gradeArray.length>0){
			for(var i = 0;i<gradeArray.length;i++){
				gradeArray[i].checked=false;
			}
		}
		$("tr").filter(function(){
			return this.id.Trim().indexOf('utr_student')!=-1;
		}).hide('fast');
		
	}
}
function deleteSelected(){
//	var arr = $("input[name=ckNoticeId]").filter(function(){
//		return this.checked = true;
//	});
	var arr = $("input[name=ckNoticeId]:checked");
	var refs = '';
	if(arr.length>0){
		$.each(arr,function(idx,itm){
			if(itm!=null&&itm.value.Trim().length>0){
				if(refs.Trim().length>0)
					refs+="!";
				refs+=itm.value.Trim();
			}
		});	
		$.ajax({
			url:'notice?m=dodel',//cls!??.action
			dataType:'json',
			type:'POST',
			data:{ref:refs
		    },
			cache: false,
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				alert(rps.msg)
				pageGo(p1);
			}
		});
	}else{
		alert("没有任何选中");
		return;
	}
	
}

function showAddDiv(div){
	
	showModel(div,'fade',false);
	clearForm(document.addform);
	$("tr").filter(function(){
		return this.id.Trim().indexOf('atr_student')!=-1;
	}).hide('fast');
}

function showUpdDiv(div,ref){
	clearForm(document.updform);
	$.ajax({
		url:'notice?m=toupd',//cls!??.action
		dataType:'json',
		type:'post',
		data:{ref:ref
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
            isupdate=true;
			$('#ucontent').xheditor(xheditorConfig);
			$.each(rps.objList,function(idx,itm){
				var istitleLink=itm.titlelink+"";
				var beginTime=itm.begintimestring;
				var endTime=itm.endtimestring;
				$("#ref").val(itm.ref);
				$("#utitle").val(itm.noticetitle);
				if(itm.istime==0){
                    p_uyxsj.style.display='block';
                    $("#uistime")[0].checked=true;
					$("#ubeginTime").val(beginTime);
					$("#uendTime").val(endTime);
				}else
                    p_uyxsj.style.display='none';
				if(istitleLink!='undefined'&&istitleLink.length>0){
					$("#utitleLinkck")[0].checked=true;
                    $("#utitlelink").val(itm.titlelink);
                    u_ubtlj.style.display='block';
                    utrContent.style.display='none';
				}else{
                    u_ubtlj.style.display='none';
                    $("#utrContent").show();
                }
				$("#ucontent").val(itm.noticecontent);
                if(itm.istop==0)
                     $("#uisTop")[0].checked=true;
				var roles = itm.noticerole;
				var rolelist=roles.split("|");
					var objRoleId=document.getElementsByName("uroleId");
					if(objRoleId!=null){
						if(objRoleId.length==1||objRoleId.length==undefined){
							for(var i=0;i<rolelist.length;i++){
								objRoleId[j].checked=false;			
								if(rolelist[i]==objRoleId.value.Trim()){
									objRoleId.checked=true;
									if(rolelist[i]==1){
										$("tr").filter(function(){
											return this.id.Trim().indexOf('rtr_student')!=-1;
										}).show('fast');
									}
								}
							}
						}else{
							for(var j=0;j<objRoleId.length;j++){	
								objRoleId[j].checked=false;					
								for(var i=0;i<rolelist.length;i++){
									if(rolelist[i]==objRoleId[j].value.Trim()){
										objRoleId[j].checked=true;
										if(rolelist[i]==1){
											$("tr").filter(function(){
												return this.id.Trim().indexOf('rtr_student')!=-1;
											}).show('fast');
										}
									}						
								}
							 }
						}
					}  	
//				if(itm.noticegrade!=null||itm.noticegrade!=""){
//					var grades = itm.noticegrade;
//					var gradelist = grades.split("|");
//					var grade = document.getElementsByName("rck_grade");
//					if(grade!=null){
//							if(grade.length==1||grade.length==undefined){
//									for(var i=0;i<gradelist.length;i++){
//										grade[j].checked=false;
//										if(gradelist[i]==grade.value.Trim()){
//											grade.checked=true;
//										}
//									}
//							}else{
//								for(var j=0;j<grade.length;j++){
//									grade[j].checked=false;
//									for(var i=0;i<gradelist.length;i++){
//										if(gradelist[i]==grade[j].value.Trim()){
//											grade[j].checked=true;
//										}
//									}
//								 }
//							}
//					}
//				}
					
			});
		}
	});
	
	showModel(div,'fade',false);
	$("tr").filter(function(){
		return this.id.Trim().indexOf('utr_student')!=-1;
	}).hide('fast');
}