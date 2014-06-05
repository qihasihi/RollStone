function pRate(userId,callBack){
	this.commentuserid=userId;
	this.index=0;
	this.commenttype=0;
	this.commentobjectid=0;
	var me=this;
	$("body").append("<div id='cl'></div>");
	$("body").append("<div id='cs'></div>");
	$("#cs").load("comment/comment.jsp",function() {
		var B = $("#p_rate");
		var rate = B.children("li");
		rate.hover(function(){
				$(this).attr("class","star_hover").siblings(":lt("+($(this).index())+")").attr("class","star_hover");
		},function(){
			rate.attr("class","star_out");
			B.children("li:lt("+(me.index)+")").attr("class","star_hover");
		});
		
		rate.click(function(){
			me.index = $(this).index() + 1;
			$("#scoreResult").html(me.index);
			rate.attr("class","star_out");
			B.children("li:lt("+(me.index)+")").attr("class","star_hover");
			if(callBack){callBack();}
		});
	});
	
	this.showCommentDiv=function(idValue,commentType){
		if(idValue==null||commentType==null){
			alert("缺失参数，无法评论！");
			return;
		}
		this.index=0;
		this.commentobjectid=idValue;
		this.commenttype=commentType;
		showModel("commentDiv",false);
	};

	this.closeCommentDiv=function(){
		this.index=0;
		this.commentobjectid=0;
		this.commenttype=0;
		closeModel("commentDiv");
	};

	this.addComment=function(){
		if(this.commentuserid==null){
			alert("未登录无法评论！");
			return;
		}
		if(this.commentobjectid==0||this.commenttype==0){
			alert("缺失参数，无法评论！");
			return;
		}
		if(this.index<1){
			alert("请打分！");
			return;
		}
		if($("#commentcontext").val()==null||$("#commentcontext").val().length<1){
			alert("请输入评论内容！");
			return;
		}
		var dat={commentuserid:this.commentuserid,commenttype:this.commenttype,commentobjectid:this.commentobjectid,score:this.index,commentcontext:$("#commentcontext").val()} 
		$.ajax({
			url:'comment!addComment.action',
			data:dat,
			type:'post',
			dataType:'json',
			error:function(){
				alert('异常错误,系统未响应！');
			},success:function(rps){
				if(rps.type=="success"){
					alert("评论成功!");
					me.closeCommentDiv();
				}else{
					alert("提交失败!");
				}		
			}
		});
	};

}

var comment_start;

function showCommentListDiv(ctype,cobjectid,title){
	$("#cs").load("comment/commentList.jsp?commenttype="+ctype+"&commentobjectid="+cobjectid,function() {
		showModel("commentList",false);
		$("#commentListTitle").html(title);
	});
}

function getCommentReturnMethod(rps){
	var html=''; 
	if(rps.objList==null||rps.objList.length<1){
		html="<tr><td align='center'>沒有评论！！</td></tr>";
	}
	$.each(rps.objList,function(idx,item){	
			html+='<tr>'; 
			html+='<td>'+  item.commentusername +'</td>';
			html+='<td>'+ item.score +'</td>';    
			html+='<td>'+ item.commentcontext +'</td>';   
			html+='<td>'+ item.creattime +'</td>';   
			html+='</tr>'; 
	});
	$("#tbl_commentList").html(html); 
	if(rps.objList.length>0){
		pList.setPagetotal(rps.presult.pageTotal);
		pList.setRectotal(rps.presult.recTotal);
		pList.setPageSize(rps.presult.pageSize);
		pList.setPageNo(rps.presult.pageNo);
	}else
	{
		pList.setPagetotal(0);
		pList.setRectotal(0);		
		pList.setPageNo(1);
	}
	pList.Refresh();
}


function creatCommentForm(commentuserid,callBack) {
		return new pRate(commentuserid,callBack);
}
