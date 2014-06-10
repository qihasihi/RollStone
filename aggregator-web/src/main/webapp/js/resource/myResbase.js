function afterAjaxList(rps){
			var html = "";
			if(rps!=null&&rps.presult.list.length>0){
				$.each(rps.objList,function(idx,itm){
					html+="<tr>";					
					html+="<td align='center'><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ (itm.resname.length>14?(itm.resname.substring(0,15)+"..."):itm.resname) +"</a></td>";
                    html+="<td>"+itm.subjectname+"</td>";
                    html+="<td>"+itm.gradename+"</td>";
                    html+="<td>"+itm.restypename+"</td>";
                    html+="<td>"+itm.filetypename+"</td>";
					html+="<td>"+itm.resscore+"分</td>";
					html+="<td>"+ itm.storenum +"/"+ itm.clicks +"/"+ itm.downloadnum +"</td>"; 
					html+="<td><a href='resource?m=toupdate&resid="+itm.resid+"' class='font-lightblue'>修改</a>&nbsp;"
					+"<a href="+"javascript:changeResState('"+itm.resid+"',3);"+" class='font-lightblue'>删除</a></td>";
					html+="</tr>";
				});
			}else{
				html+="<tr><th colspan='8'>暂无数据！</th></tr>";
			}
			p1.setPageSize(rps.presult.pageSize);
			p1.setPageNo(rps.presult.pageNo);
			p1.setRectotal(rps.presult.recTotal);
			p1.setPagetotal(rps.presult.pageTotal);
			p1.Refresh();
			$("#myResData").html(html);
			$("#reslist").children().hide();
			
			cutoverTab(0);
	}
	
	function afterStoreAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
				html+="<tr id='store_res_"+itm.resid+"'>";					
				html+="<td><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ (itm.resourceInfo.resname.length>14?(itm.resourceInfo.resname.substring(0,13)+"..."):itm.resourceInfo.resname) +"</a></td>";
                html+="<td>"+itm.resourceInfo.subjectname+"</td>";
                html+="<td>"+itm.resourceInfo.gradename+"</td>";
                html+="<td>"+itm.resourceInfo.restypename+"</td>";
                html+="<td>"+itm.resourceInfo.filetypename+"</td>";
				html+="<td>"+itm.resourceInfo.username+"</td>";
                html+="<td>"+ itm.resourceInfo.storenum +"/"+ itm.resourceInfo.clicks +"/"+ itm.resourceInfo.downloadnum +"</td>";
                html+="<td align='center'><a href="+"javascript:operateStore('"+itm.resid+"')"+" class='font-lightblue'>取消收藏</a></td>";
				html+="</tr>";
			});
		}else{
			html+="<tr><th colspan='8'>暂无数据！</th></tr>";
		}
		p2.setPageSize(rps.presult.pageSize);
		p2.setPageNo(rps.presult.pageNo);
		p2.setRectotal(rps.presult.recTotal);
		p2.setPagetotal(rps.presult.pageTotal);
		p2.Refresh();
		$("#storeResData").html(html);
	}

	function afterCheckAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
				html+="<tr>";					
				html+="<td><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ (itm.resname.length>14?(itm.resname.substring(0,13)+"..."):itm.resname) +"</a></td>";  
                html+="<td>"+itm.subjectname+"</td>";
                html+="<td>"+itm.gradename+"</td>";
                html+="<td>"+itm.restypename+"</td>";
                html+="<td>"+itm.filetypename+"</td>";
				html+="<td>"+itm.username+"</td>";
				html+="<td>"+ itm.ctimeString +"</td>";
				html+="<td><a href='javascript:void(0);' onclick="+"getReportList('"+itm.resid+"')"+">"+ itm.reportnum+"</a></td>";
				if(itm.resstatus==1){
					html+="<td id='res_"+itm.resid+"_handle'><a href='javascript:void(0);' onclick="+"changeResState('"+itm.resid+"',0);"+">"; 
					html+="<img title='删除资源' src='images/u107_normal.png'></a></td>";
				}else{
					html+="<td id='res_"+itm.resid+"_handle'>已删除</td>";
				}
				html+="</tr>";
				
			});
		}else{
			html+="<tr><th colspan='9'>暂无数据！</th></tr>";
		}
		p3.setPageSize(rps.presult.pageSize);
		p3.setPageNo(rps.presult.pageNo);
		p3.setRectotal(rps.presult.recTotal);
		p3.setPagetotal(rps.presult.pageTotal);
		p3.Refresh();
		$("#checkData").html(html);
	}
	
	function cutoverTab(n){
		$("#colTab").children().attr("class","");
		$($("#colTab").children()[n]).attr("class","crumb");
		switch (n){
		case 0:$("#reslist").children().hide();
				$("#page1form").show();
				$("#div_myResData").show();
				break;
		
		case 1:$("#reslist").children().hide();
				$("#div_storeResData").show();
				$("#page2form").show();
				break;
				
		case 2:$("#reslist").children().hide();
				$("#page3form").show();
				$("#div_checkResData").show();
				break;
		}
	}
	
	function getStateString(n){
		switch(n){
			case 0:return "未审核";
			case 1:return "审核通过";
			case 2:return "审核未通过";
			case 3:return "已删除";
		}
	}
	
	function getHandleHTML(resid,n){
		switch(n){
			case 0:return "<a href="+"javascript:changeResState('"+resid+"',1);"+" class='font-lightblue'>通过</a>&nbsp;&nbsp;"
				+"<a href="+"javascript:changeResState('"+resid+"',2);"+" class='font-lightblue'>不通过</a>";
			case 1:return "<a href="+"javascript:changeResState('"+resid+"',2);"+" class='font-lightblue'>取消通过审核</a>";
			case 2:return "<a href="+"javascript:changeResState('"+resid+"',1);"+" class='font-lightblue'>通过审核</a>";
			case 3:return "作者已删除";
		}
	}
	
	function changeResState(resid,state){
    if(state==0){
        if(!confirm("确认删除？"))
            return;
    }

    if(resid==null||state==null){
        alert("参数不足，无法提交！");return;
    }
    $.ajax({
        url:'resource?m=changeresstate',
        type:'POST',
        data:{resid:resid,resstatus:state},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            if(rps.type=="success"){
                //$("#res_"+resid+"_handle").html(getHandleHTML(resid,state));
                $("#res_"+resid+"_handle").html("已删除");
            }else{
                alert(rps.msg);
            }
        }
    });
}
	
	function operateStore(resid){
		if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
			alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
		}
		var u='store?m=dodelete'; 
		$.ajax({
			url:u,
			type:'POST',
			data:{resid:resid},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
				}else{
					$("#store_res_"+resid).remove();
				}
			}
		});    		
	}
	
	function getReportList(resid){
		showModel("resource_report");
		$.ajax({
			url:'resourcereport?m=getreportlist',
			type:'POST',
			data:{resid:resid,content:content},
			dataType:'json',
			error:function(){alert("网络异常");$("#addReportBtn").show();},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}
				if(rps!=null&&rps.presult.list.length>0){
					var html="<tr><th>举报人</th><th>原因</th></tr>";
					$.each(rps.objList,function(idx,itm){
						html+="<tr>";					
						html+="<td align='center'>"+itm.realname+"</td>";
						html+="<td>"+itm.content+"</td>";
						html+="</tr>";
					});
					$("#report_list").html(html);
				}
			}
		});    		
	}