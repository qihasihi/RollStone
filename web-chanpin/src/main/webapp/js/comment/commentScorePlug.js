function pRate(divId,starNum,disposable,flag,val,callBack){
	this.starNum=starNum;
	this.value=0;
	this.flag=flag;
	this.disposable=disposable;
	var me=this;
	var sHtml="<ul id='p_rate' style='float:left;'>";
	for(var i=0;i<starNum;i++){
		if(typeof(val)=="undefined"||isNaN(val))
			sHtml+="<li title='"+(i+1)+"分' class='star_out'></li>";
		else if(i<val)
			sHtml+="<li title='"+(i+1)+"分' class='star_hover'></li>";
		else
			sHtml+="<li title='"+(i+1)+"分' class='star_out'></li>";
	}
	sHtml+="</ul>&nbsp;&nbsp;";
	if(typeof(val)=="undefined"||isNaN(val)){
		if(this.flag){
			sHtml+="<span id='starText'>未打分！</span>";
			
		}
		$("#"+divId).html(sHtml);
	}else{
		sHtml+="<span id='starText'>"+val+"分！</span>";
		$("#"+divId).html(sHtml);
		return;
	}
	var B = $("#p_rate");
	var rate = B.children("li");
		rate.hover(function(){
				$(this).attr("class","star_hover").siblings(":lt("+($(this).index())+")").attr("class","star_hover");
		},function(){
			rate.attr("class","star_out");
			B.children("li:lt("+(me.value)+")").attr("class","star_hover");
		});

		rate.click(function(){ 
			if(disposable&&me.value!=0){
				alert("已经打过分了！");
				return; 
			}
			me.value = $(this).index() + 1; 
			$("#scoreResult").html(me.value);
			rate.attr("class","star_out");
			B.children("li:lt("+(me.value)+")").attr("class","star_hover");
			if(me.flag){
				$("#starText").html(me.value+"分");			
			}
			if(callBack){callBack(me.value );}
		});	
	
}

var comment_star;

function creatCommentForm(divId,starNum,disposable,isText,val,callBack) {
		return new pRate(divId,starNum,disposable,isText,val,callBack);
}
