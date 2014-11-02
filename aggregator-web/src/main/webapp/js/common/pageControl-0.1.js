var PageControl;
if (PageControl == undefined) {
	PageControl = function(settings) {
		this.initPage(settings);
	}
};
/**
 * 初始化 PageControl
 * 
 * @param settings
 * @return
 */
PageControl.prototype.initPage = function(settings) {
	try {
		this.customSettings = {};
		this.settings = settings;
		this.initSettings();
		this.loadPageControl(); // 生成HTML
		this.afterBuildInit(); // 生成HTML后，初始化
	} catch (ex) {
	}
};
/**
 * 初始化 PageControl
 * 
 * @param settings
 * @return
 */
PageControl.prototype.Refresh = function() {
	try {		
		this.loadPageControl(); // 生成HTML
		this.afterBuildInit(); // 生成HTML后，初始化
	} catch (ex) {
	}
};
/**
 * 初始化设置
 * 
 * @return
 */
PageControl.prototype.initSettings = function() {
	this.ensureDefault = function(settingName, defaultValue) {
		this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
				: this.settings[settingName];
	};
	this.ensureDefault("post_url", ""); // * 链接地址
	this.ensureDefault("page_id", ""); // ID名称 *
	this.ensureDefault("page_control_name", ""); // 名称，声明变量的名称
	this.ensureDefault("gender_address_id", ""); // 控件生成在页面ID地址 *
	this.ensureDefault("post_params", {}); // 参数
	this.ensureDefault("custom_settings", {});
	this.ensureDefault("post_form", null);
	this.ensureDefault("http_free_operate_handler", null);		//执行查询时，执行的方法
	this.ensureDefault("http_operate_handler", null);// 正在访问中，执行的方法 *
	this.ensureDefault("http_operate_html",
			"<span align='center'><img src='"+this.getRootProject()+"img/loading_smail.gif'/>正在操作中!请稍候……</span>");  // 操作中执行的方法
	this.ensureDefault("return_type", "");// 访问成功后 返回值类型. JSON? *
	this.ensureDefault("page_no", ""); // 页码 *
	this.ensureDefault("page_size", ""); // 显示数 *
	this.ensureDefault("rectotal", ""); // 数据总数量 *
	this.ensureDefault("pagetotal", ""); // 页数总数量 *
	this.ensureDefault("page_order_by", ""); // 排序
	this.ensureDefault("page_sort", ""); //
	this.ensureDefault("new_page_html_mode", false); //新版样式分页栏
	this.ensureDefault("page_abbreviated", false); //分页栏缩略显示
	this.ensureDefault("operate_id", "");//操作对象
	this.customSettings = this.settings.custom_settings;
	delete this.ensureDefault;
};
/**
*
* @param pn
* @return
*/
PageControl.prototype.setPageSort =function(hop){
	if(typeof(hop)=='undefined'||typeof(hop)!='string'||hop.toLowerCase()!="asc"&&hop.toLowerCase()!="desc"){
		return;
	}	
	this.page_sort=hop;
}
/**
*
* @param pn
* @return
*/
PageControl.prototype.getPageSort =function(){	
	return this.page_sort;
}
/**
*
* @param pn
* @return
*/
PageControl.prototype.setHttpOperateHandler =function(hop){
	if(typeof(hop)=='undefined'||typeof(hop)!='function'){
		return;
	}	
	this.settings.http_operate_handler=hop;
}
/**
*
* @param pn
* @return
*/
PageControl.prototype.setPageOrderBy =function(hop){
	if(typeof(hop)=='undefined'||typeof(hop)!='string'){
		return;
	}	
	this.settings.page_order_by=hop;
}

/**
*
* @param pn
* @return
*/
PageControl.prototype.getPageOrderBy =function(){	
	return this.settings.page_order_by;
}
/**
 * 
 * @return
 */
PageControl.prototype.getHttpOperateHandler =function(){	
	return this.settings.http_operate_handler;
}

PageControl.prototype.getRootProject=function(){
	
	  var pathName = window.location.pathname.substring(1); 
	     var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/')); 
	     return window.location.protocol + '//' + window.location.host + '/'+ webName + '/';
} 
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.setHttpFreeOperateHandler =function(fhop){
	if(typeof(fhop)=='undefined'||typeof(fhop)!='function'){
		return;
	}	
	this.settings.http_free_operate_handler=fhop;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.getHttpFreeOperateHandler =function(){
	return this.settings.http_free_operate_handler;
}
/**
 * 
 * @return
 */
PageControl.prototype.getPostForm=function(){	
	return this.settings.post_form;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.setPostForm=function(pf){
	if(typeof(pf)=='undefined'){
		return;
	}	
	this.settings.post_form=pf;
}
/**
 * 
 * @return
 */
PageControl.prototype.getPostParams=function(){
	return this.settings.post_params;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.setPostParams=function(post_params){
	if(typeof(post_params)=='undefined'||typeof(post_params)!="object"){
		return;
	}	
	this.settings.post_params=post_params;
}
/**
 * 
 * @return
 */
PageControl.prototype.getPostParams=function(){
	return this.settings.post_params;
}
/**
 * 
 * @return
 */
PageControl.prototype.getReturnType=function(){
	return this.settings.return_type;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.setReturnType=function(return_type){
	if(typeof(return_type)=='undefined'){
		return;
	}	
	this.settings.return_type=return_type;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.getCustomSettings=function(){
	return this.settings.custom_settings;
}
/**
 * 
 * @param pn
 * @return
 */
PageControl.prototype.setCustomSettings=function(cs){
	if(typeof(pagetotal)=='undefined'||typeof(cs)!='object'){
		return;
	}	
	this.settings.custom_settings=cs;
}
/**
 * 赋予响应显示数量
 * @param pn
 * @return
 */
PageControl.prototype.setPagetotal=function(pagetotal){
	if(typeof(pagetotal)=='undefined'||isNaN(pagetotal)){
		return;
	}	
	this.settings.pagetotal=parseFloat(pagetotal);
}
/**
 * 
 * @return
 */
PageControl.prototype.getPagetotal=function(){
    return this.settings.pagetotal;
}
PageControl.prototype.getOperateId=function(){
    return this.settings.operate_id;
}
/**
 * 响应显示数量
 * @param pn
 * @return
 */
PageControl.prototype.getRectotal=function(){	
	return this.settings.rectotal;
}
/**
 * 赋予响应显示数量
 * @param pn
 * @return
 */
PageControl.prototype.setRectotal=function(rectotal){
	if(typeof(rectotal)=='undefined'||isNaN(rectotal)){
		return;
	}	
	this.settings.rectotal=parseFloat(rectotal);
}
/**
 * 
 * @return
 */
PageControl.prototype.getPageSize=function(){
	return this.settings.page_size;
}
/**
 * 赋予响应显示数量
 * @param pn
 * @return
 */
PageControl.prototype.setPageSize=function(ps){
	if(typeof(ps)=='undefined'||isNaN(ps)){
		return;
	}	
	this.settings.page_size=parseFloat(ps);
}
/**
 * 赋予响应页码
 * @param pn
 * @return
 */
PageControl.prototype.getPageNo=function(){
	return this.settings.page_no;
}
/**
 * 赋予响应页码
 * @param pn
 * @return
 */
PageControl.prototype.setPageNo=function(pn){
	if(typeof(pn)=='undefined'||isNaN(pn)){
		return;
	}	
	this.settings.page_no=parseFloat(pn);
}

/**
 * 赋予响应地址
 * @param url
 * @return
 */
PageControl.prototype.getPostUrl=function(){
	if(this.settings.post_url!=null
			&&this.settings.post_url.indexOf('?')!=-1){
		this.settings.post_url=this.settings.post_url.substring(0,this.settings.post_url.indexOf('?'));
	}
	return this.settings.post_url;
}
/**
 * 赋予响应地址
 * @param url
 * @return
 */
PageControl.prototype.setPostUrl=function(url){
	if(typeof(url)=='undefined'){
		return;
	}
	this.settings.post_url=url;
}
/**
 * 加载HTML分页空间
 * 
 * @return
 */
PageControl.prototype.loadPageControl = function() {
	if (this.settings.gender_address_id == "") {
		throw "翻页控件中没有，传入控件生成至页面地址!";
	}
	// 得到地址
	var tempParent = document.getElementById(this.settings.gender_address_id);
	// 生成HTML
	if(this.settings.new_page_html_mode){
        tempParent.innerHTML = this.getPageHtmlNew();
    }else{
        tempParent.innerHTML = this.getPageHtml();tempParent.className='nextpage';
    }
};


/**
 * 生成分页HTML
 * 
 * @return
 */
PageControl.prototype.getPageHtml = function() {
	var basepath=this.getRootProject();
	var returnVal = '';
	if(!this.settings.page_abbreviated){
		if(this.settings.page_no>1&&this.settings.pagetotal-this.settings.page_no>=0)
			returnVal+='<span><a href="javascript:;" onclick="pagePre(\''+ this.settings.page_control_name+ '\');"><b class="before"></b></a></span>';
		returnVal+='<span';
		if(1==this.settings.page_no)
			returnVal+=' class="crumb"';

		returnVal+='><a href="javascript:;" onclick="'+this.settings.page_control_name+'.pageGo(1)">1</a></span>';
		
		if(this.settings.pagetotal<=6){
			
			for(i=2;i<=this.settings.pagetotal-1;i++){				
				returnVal+='<span'; 
				if(i==this.settings.page_no)
					returnVal+=' class="crumb"';
				returnVal+='><a href="javascript:;" onclick="'+this.settings.page_control_name+'.pageGo('+i+')">'+i+"</a></span>";
			}
		}else{
			var tmpNo=this.settings.page_no;
					
			var i=tmpNo-1;	
			if(i<=1){i=2;tmpNo=tmpNo+1;};
			if(this.settings.pagetotal-tmpNo<=3)
				i=tmpNo=this.settings.pagetotal-6;
			if(i==1)i+=1;
			var endSum=tmpNo+1;
			if(endSum==this.settings.pagetotal-3)
				endSum=endSum-1;
			for(;i<=endSum;i++){
				returnVal+='<span';
				if(i==this.settings.page_no)
					returnVal+=' class="crumb"';
				returnVal+='><a href="javascript:;" onclick="'+this.settings.page_control_name+'.pageGo('+i+')">'+i+"</a></span>";
			}
			returnVal+='&hellip;';
			var tmpSumno=this.settings.pagetotal;
						
			for(i=this.settings.pagetotal-3;i<=this.settings.pagetotal-1;i++){
				returnVal+='<span';
				if(i==this.settings.page_no)
					returnVal+=' class="crumb"';
				returnVal+='><a href="javascript:;" onclick="'+this.settings.page_control_name+'.pageGo('+i+')">'+i+"</a></span>";
			}
		}
		if(1!=this.settings.pagetotal&&this.settings.pagetotal>1){
			returnVal+='<span';
			if(this.settings.pagetotal==this.settings.page_no)
				returnVal+=' class="crumb"';
			returnVal+='><a href="javascript:;" onclick="'+this.settings.page_control_name+'.pageGo('+this.settings.pagetotal+')">'+this.settings.pagetotal+"</a></span>";
		}
		if(this.settings.page_no<this.settings.pagetotal)
			returnVal+='<span><a href="javascript:;" onclick="pageNext(\''+ this.settings.page_control_name+ '\')"><b class="after"></b></a></span>';
		returnVal+='<font id="'+this.settings.page_control_name+'_lastcontrol">&nbsp;&nbsp;共'+this.settings.pagetotal+'页&nbsp;去第';
		returnVal+='<input name="pageResult.pageNo" type="text"  value="'+this.settings.page_no+'" />';
		returnVal+='页&nbsp;&nbsp;<span><a href="javascript:;" onclick="pageGo(\''+ this.settings.page_control_name+ '\');">Go</a></font>';
		returnVal+='<input type="hidden" name="pageResult.orderBy" value="'+ this.settings.page_order_by + '" />'; 
		returnVal+='<input type="hidden" name="pageResult.sort" value="'+ this.settings.page_sort +'" />';
		returnVal+='<input type="hidden" name="pageResult.rectotal" value="'+this.settings.rectotal+'"/>';
		
		returnVal+='<input type="hidden" name="pageResult.pageSize" value="'+this.settings.page_size+'"/>';
		returnVal+='</span>';
		returnVal+='<span class="F_red" id="bili'+ this.settings.page_id + '" style="display:none">' 
					+ this.settings.page_no + '/'+ this.settings.pagetotal + '</span>';
		
		
		
		
		
		
		/*returnVal = '<table style="font-size:12px;font-family:\'宋体\'"><tr><td valign="middle" align="left"><input type="hidden" name="pageResult.pageSize" style="height: 18px; width: 30px" size="3"	value="' + this.settings.page_size + '" />';
		returnVal += '共 <span class="F_red" id="rectotal'+ this.settings.page_id + '">'+this.settings.rectotal + '</span>'
				+ '条&nbsp;每页显示 <span id="spanPageSize'+this.settings.page_id+'" style="width:30px" class="F_red">'+ this.settings.page_size+'</span>条&nbsp;第<span class="F_red" id="bili'+ this.settings.page_id + '">' 
				+ this.settings.page_no + '/'+ this.settings.pagetotal + '</span>' + '页&nbsp;转</td><td valign="middle" align="left" style="width:30px">';
		returnVal += '<input type="text" style="height: 12px; width: 30px" class="InputBorder" maxlength="4" name="pageResult.pageNo" value="'+this.settings.page_no+
					'" size="3" /></td><td valign="middle" align="left" valign="middle">页</td><td align="center" valign="middle">';
		returnVal += '<a href="javascript:pageGo(\''+ this.settings.page_control_name+ '\');" id="pageGo'
					+ this.settings.page_id+ '"><img height="16" alt="GO" src="'+basepath+'images/an18.gif" width="19" align="absmiddle" border="0" /></a>';
		returnVal += '&nbsp;'; 
	
		returnVal += '<a href="javascript:pageFirst(\''+this.settings.page_control_name+'\')" id="apageFirst'+this.settings.page_id+
					'"><img src="'+basepath+'images/page01_a.gif" name="pageFirst" id="pageFirst" title="首页" width="21" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pagePre(\''+ this.settings.page_control_name+ '\');"  id="apagePre'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page02_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageNext(\''+ this.settings.page_control_name+ '\');" id="apageNext'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page03_b.gif" name="pageNext"	id="pageNext" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageLast(\''+ this.settings.page_control_name	+ '\');" id="apageLast'	+ this.settings.page_id+
					'"><img id="pageLast" src="'+basepath+'images/page04_b.gif" name="pageLast" title="末页" width="21" height="16" border="0" /></a>';
		returnVal += '<input type="hidden" name="pageResult.orderBy" value="'+ this.settings.page_order_by + '" />'	+ 
					'<input type="hidden" name="pageResult.sort" value="'+ this.settings.page_sort +'" /></td></tr></table>';
					*/
	}else{
		returnVal = '<table style="font-size:12px;font-family:\'宋体\'"><tr><td valign="middle" align="left"><input type="hidden" name="pageResult.pageSize" style="height: 18px; width: 30px" size="3"	value="' + this.settings.page_size + '" />';
		returnVal += '共 <span class="F_red" id="rectotal'+ this.settings.page_id + '">'+this.settings.rectotal + '</span>'+ '条&nbsp;第<span class="F_red" id="bili'+ this.settings.page_id + '">' 
				+ this.settings.page_no + '/'+ this.settings.pagetotal + '</span>&nbsp;页&nbsp;</td><td valign="middle" align="left" style="width:30px">';
		returnVal += '<input type="hidden" name="pageResult.pageNo" value="'+this.settings.page_no+'" size="3" /></td><td valign="middle" align="left" valign="middle"></td><td align="center" valign="middle">&nbsp;';
	
		returnVal += '<a href="javascript:pagePre(\''+ this.settings.page_control_name+ '\');"  id="apagePre'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page02_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageNext(\''+ this.settings.page_control_name+ '\');" id="apageNext'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page03_b.gif" name="pageNext"	id="pageNext" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<input type="hidden" name="pageResult.orderBy" value="'+ this.settings.page_order_by + '" />'	+ 
					'<input type="hidden" name="pageResult.sort" value="'+ this.settings.page_sort +'" /></td></tr></table>';
	}
	
	return returnVal;
};

/**
 * 生成分页HTML
 * 
 * @return
 */
PageControl.prototype.getPageHtmlNew = function() {
	var basepath=this.getRootProject();
	var returnVal = '';
	
	if(!this.settings.page_abbreviated){
		returnVal = '<div class="t_r m_r_15 p_tb_10">'
			+'<input type="hidden" name="pageResult.pageSize" style="height: 18px; width: 30px" size="3"	value="' + this.settings.page_size + '" />'
			+'<input type="hidden" style="height: 12px; width: 30px" class="InputBorder" maxlength="4" name="pageResult.pageNo" value="'+this.settings.page_no+'" size="3" />';
	
		returnVal += '共&nbsp; <span class="font-red" id="rectotal'+ this.settings.page_id + '">'+this.settings.rectotal + '</span>&nbsp;条记录&nbsp;&nbsp;'
				+ '每页显示 <span id="spanPageSize'+this.settings.page_id+'" class="font-red">'+ this.settings.page_size+'</span>条&nbsp;&nbsp;第&nbsp;<span class="font-red" id="bili'+ this.settings.page_id + '">' 
				+ this.settings.page_no + '/'+ this.settings.pagetotal + '</span>' + '&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;';
	
		returnVal += '<a href="javascript:pageFirst(\''+this.settings.page_control_name+'\')" id="apageFirst'+this.settings.page_id+
					'"><img src="'+basepath+'images/page01_a.gif" name="pageFirst" id="pageFirst" title="首页" width="21" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pagePre(\''+ this.settings.page_control_name+ '\');"  id="apagePre'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page02_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageNext(\''+ this.settings.page_control_name+ '\');" id="apageNext'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page03_b.gif" name="pageNext"	id="pageNext" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageLast(\''+ this.settings.page_control_name	+ '\');" id="apageLast'	+ this.settings.page_id+
					'"><img id="pageLast" src="'+basepath+'images/page04_b.gif" name="pageLast" title="末页" width="21" height="16" border="0" /></a>';
		returnVal += '<input type="hidden" name="pageResult.orderBy" value="'+ this.settings.page_order_by + '" />'	+ 
					'<input type="hidden" name="pageResult.sort" value="'+ this.settings.page_sort +'" />';
	}else{
		returnVal = '<div class="t_r m_r_15 p_tb_10">' 
			+'<input type="hidden" name="pageResult.pageSize" style="height: 18px; width: 30px" size="3"	value="' + this.settings.page_size + '" />'
			+'<input type="hidden" style="height: 12px; width: 30px" class="InputBorder" maxlength="4" name="pageResult.pageNo" value="'+this.settings.page_no+'" size="3" />';
	
		returnVal += '共&nbsp; <span class="font-red" id="rectotal'+ this.settings.page_id + '">'+this.settings.rectotal + '</span>&nbsp;条&nbsp;第&nbsp;<span class="font-red" id="bili'+ this.settings.page_id + '">' 
				+ this.settings.page_no + '/'+ this.settings.pagetotal + '</span>' + '&nbsp;页&nbsp;';
	
		returnVal += '<a href="javascript:pagePre(\''+ this.settings.page_control_name+ '\');"  id="apagePre'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page02_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<a href="javascript:pageNext(\''+ this.settings.page_control_name+ '\');" id="apageNext'+ this.settings.page_id+
					'"><img src="'+basepath+'images/page03_b.gif" name="pageNext"	id="pageNext" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		returnVal += '<input type="hidden" name="pageResult.orderBy" value="'+ this.settings.page_order_by + '" />'	+ 
					'<input type="hidden" name="pageResult.sort" value="'+ this.settings.page_sort +'" />';
	}

	return returnVal;
};
/**
 * 生成HTML后，进行初始化
 * 
 * @return
 */
PageControl.prototype.afterBuildInit = function() {
	if (this.settings.post_form == null) {
		throw "分页控件参数没有指定:post_form 对象!";
	}
	// this.settings.post_form['spanPageSize'].innerHTML(this.settings.post_form["pageResult.pageSize"].value);
	var basepath=this.getRootProject();
	var currentNo = this.settings.post_form["pageResult.pageNo"].value;
	
	if(typeof(this.settings.post_form["pageFirst"])!="undefined")
		this.settings.post_form["pageFirst"].src = basepath+"images/page01_a.gif";
	if(typeof(this.settings.post_form["pagePre"])!="undefined")
		this.settings.post_form["pagePre"].src =  basepath+"images/page02_a.gif";
	if(typeof(this.settings.post_form["pageNext"])!="undefined")
		this.settings.post_form["pageNext"].src =  basepath+"images/page03_a.gif";
	if(typeof(this.settings.post_form["pageLast"])!="undefined")
		this.settings.post_form["pageLast"].src =  basepath+"images/page04_a.gif";
	if($("#apageNext" + this.settings.page_id).length>0){
		$("#apageNext" + this.settings.page_id).attr("href", "javascript:void('')");
		$("#apageNext" + this.settings.page_id).css("cursor", 'Default');
	}
	if($("#apageFirst" + this.settings.page_id).length>0){
		$("#apageFirst" + this.settings.page_id).attr("href", "javascript:void('')");
		$("#apageFirst" + this.settings.page_id).css("cursor", 'Default');
	}
	if($("#apagePre" + this.settings.page_id).length>0){
		$("#apagePre" + this.settings.page_id).attr("href", "javascript:void('')");
		$("#apagePre" + this.settings.page_id).css("cursor", 'Default');
	}
	if($("#apageLast" + this.settings.page_id).length>0){
		$("#apageLast" + this.settings.page_id).attr("href", "javascript:void('')");
		$("#apageLast" + this.settings.page_id).css("cursor", 'Default');
	}
	if (!isNaN(currentNo) && currentNo != null) {		
				if(currentNo==1&&currentNo==parseInt(this.settings.pagetotal)){
					return;
				}
				if(currentNo<=1&&currentNo!=parseFloat(this.settings.pagetotal)){	
					if(typeof(this.settings.post_form["pageLast"])!="undefined")
						this.settings.post_form["pageLast"].src = basepath+ "images/page04_b.gif";
					if(typeof(this.settings.post_form["pageNext"])!="undefined")
						this.settings.post_form["pageNext"].src =  basepath+"images/page03_b.gif";
					if(typeof($("#apageNext" + this.settings.page_id))!="undefined"){
						$("#apageNext" + this.settings.page_id).attr("href",'javascript:pageNext(\'' + this.settings.page_control_name + '\')');
						$("#apageNext" + this.settings.page_id)	.css("cursor", 'pointer');
					}
					
					if(typeof($("#apageLast" + this.settings.page_id))!="undefined"){
						$("#apageLast" + this.settings.page_id)	.attr("href",'javascript:pageLast(\'' + this.settings.page_control_name + '\')');
						$("#apageLast" + this.settings.page_id).css("cursor", 'pointer');
					}
				}else 
				if(currentNo>=parseFloat(this.settings.pagetotal)&&currentNo>1){
					
					if(typeof(this.settings.post_form["pageFirst"])!="undefined")
						this.settings.post_form["pageFirst"].src = basepath+ "images/page01_b.gif";
					if(typeof(this.settings.post_form["pagePre"])!="undefined")
						this.settings.post_form["pagePre"].src =  basepath+"images/page02_b.gif";
					if($("#apageFirst" + this.settings.page_id).length>0){
						$("#apageFirst" + this.settings.page_id).attr("href",'javascript:pageFirst(\'' + this.settings.page_control_name + '\')');
						$("#apageFirst" + this.settings.page_id).css("cursor",'pointer');
					}
					if($("#apagePre" + this.settings.page_id).length>0){
						$("#apagePre" + this.settings.page_id).attr("href",'javascript:pagePre(\'' + this.settings.page_control_name + '\')');
						$("#apagePre" + this.settings.page_id).css("cursor", 'pointer');
					}
				}else if(currentNo>1&&currentNo<parseFloat(this.settings.pagetotal)){
						if(typeof(this.settings.post_form["pageFirst"])!="undefined")
							this.settings.post_form["pageFirst"].src =  basepath+"images/page01_b.gif";
						if(typeof(this.settings.post_form["pagePre"])!="undefined")
							this.settings.post_form["pagePre"].src =  basepath+"images/page02_b.gif";
						
						if($("#apageFirst" + this.settings.page_id).length>0){
							$("#apageFirst" + this.settings.page_id).attr("href",'javascript:pageFirst(\'' + this.settings.page_control_name + '\')');
							$("#apageFirst" + this.settings.page_id).css("cursor",'pointer');
						}
						if($("#apagePre" + this.settings.page_id).length>0){
							$("#apagePre" + this.settings.page_id).attr("href",'javascript:pagePre(\'' + this.settings.page_control_name + '\')');
							$("#apagePre" + this.settings.page_id).css("cursor", 'pointer');
						}
						if(typeof(this.settings.post_form["pageLast"])!="undefined")
							this.settings.post_form["pageLast"].src =  basepath+"images/page04_b.gif";
						if(typeof(this.settings.post_form["pageNext"])!="undefined")
							this.settings.post_form["pageNext"].src =  basepath+"images/page03_b.gif";
						if($("#apageNext" + this.settings.page_id).length>0){
							$("#apageNext" + this.settings.page_id).attr("href",'javascript:pageNext(\'' + this.settings.page_control_name + '\')');
							$("#apageNext" + this.settings.page_id)	.css("cursor", 'pointer');
						}
						if($("#apageLast" + this.settings.page_id).length>0){
							$("#apageLast" + this.settings.page_id)	.attr("href",'javascript:pageLast(\'' + this.settings.page_control_name + '\')');
							$("#apageLast" + this.settings.page_id).css("cursor", 'pointer');
						}
					}
	}
}; 
/**
 * 执行 查询
 */
PageControl.prototype.doSubURL = function(pageidx) {
	// 执行
	var psize = $.trim(this.settings.post_form["pageResult.pageSize"].value);
	var pno = $.trim(this.settings.post_form["pageResult.pageNo"].value);
	if(typeof(pageidx)!="undefined"&&!isNaN(pageidx)&&pageidx<=this.settings.pagetotal)
			pno=pageidx;
	
	var url = "?pageResult.pageSize=" + psize + "&pageResult.pageNo=" + pno
			+ "&returnType=json";
	if(typeof(this.getPageOrderBy())!="undefined"&&this.getPageOrderBy()!=""&&this.getPageOrderBy()!="undefined")
		url+="&pageResult.orderBy="+this.getPageOrderBy();
	if(typeof(this.getPageSort())!="undefined"&&this.getPageSort()!=""&&this.getPageSort()!="undefined")
		url+="&pageResult.sort="+this.getPageSort();
		
	//判断，是否存在?号。
	if(this.settings.post_url.indexOf('?')!=-1){
		url=url.replaceAll("?", "&");
	}
	//操作
    if(typeof(isallowRefresh)=="undefined"||isallowRefresh){
        $("#"+this.settings.operate_id).html(this.settings.http_operate_html);
        if (this.settings.return_type == "") {
    //		$.post(this.settings.post_url + url, this.settings.post_params,
    //				this.settings.http_operate_handler);
            $.ajax({url:this.settings.post_url + url
                ,data:this.settings.post_params,error:function(){
//                    alert('异常错误!系统未响应!');
                },success:this.settings.http_operate_handler,
                type:"POST"
            });
        } else {
            var successMethod= this.settings.http_operate_handler;
            $.ajax({url:this.settings.post_url + url,dataType:this.settings.return_type
                    ,data:this.settings.post_params,error:function(){
//                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    successMethod(rps);
                    if(rps.presult.pageTotal<=1)
                        $('#'+gender_address_id).hide();
                    else
                        $('#'+gender_address_id).show();
                },
                type:"POST"
            });
        }
    }else{
        alert('请勿重复刷新!');
    }
};
/**
 * 验证PageNo是否正确
 * 
 * @return
 */
PageControl.prototype.validatePageNo = function(pageNo) {
	for ( var i = 0; i < pageNo.value.length; i++) {
		if (pageNo.value.charAt(i) < '0' || pageNo.value.charAt(i) > '9') {
			pageNo.value = pageNo.value.substring(0, i);
			if (pageNo.value == "")
				pageNo.value = 1;
		}
	}
	return true;
};
/**
 * 页码判断
 * 
 * @return
 */
PageControl.prototype.pageValidate = function() {
	var pageNoN = this.settings.post_form["pageResult.pageNo"];
	var pageSz = this.settings.post_form["pageResult.pageSize"];
	if (this.validatePageNo(pageNoN) && this.validatePageNo(pageSz)) {
		var pageNo = pageNoN.value;
		if (pageNo < 1)
			pageNo = 1;
		if (pageNo > parseFloat(this.settings.pagetotal))
			pageNo = parseFloat(this.settings.pagetotal);
		this.settings.post_form["pageResult.pageNo"].value = pageNo;
		var pageSize = this.settings.post_form["pageResult.pageSize"].value;
		if (pageSize < 1)
			pageSize = 1;
		pageSz.value = pageSize;
	}

};
/**
 * 事件，跳入页码
 * 
 * @return
 */
PageControl.prototype.pageGo = function(pageidx) {
	
	this.pageValidate(pageidx);  
	this.doSubURL(pageidx);
};

/**
 * 第一页
 * 
 * @return
 */
PageControl.prototype.pageFirst = function() {
	this.settings.post_form["pageResult.pageNo"].value = 1;
	this.doSubURL(); // 必须实现方法pageChangeReturnMethod
};
/**
 * 上一页
 * 
 * @return
 */
PageControl.prototype.pagePre = function() {
	var pageNoN = this.settings.post_form["pageResult.pageNo"];
	if (this.validatePageNo(pageNoN)) {
		var pageNo = pageNoN.value;
		this.settings.post_form["pageResult.pageNo"].value = parseInt(pageNo) - 1;
		this.pageValidate();
		this.doSubURL();
	}
};

/**
 * 下一页
 * 
 * @return
 */
PageControl.prototype.pageNext = function() {
	var pageNoN = this.settings.post_form["pageResult.pageNo"];
	if (this.validatePageNo(pageNoN)) {
		var pageNo = pageNoN.value;
		this.settings.post_form["pageResult.pageNo"].value = parseInt(pageNo) + 1;
		this.pageValidate();
		this.doSubURL();
	}
};
/**
 * 最后一页
 * 
 * @return
 */
PageControl.prototype.pageLast = function() {
	this.settings.post_form["pageResult.pageNo"].value = this.settings.pagetotal;
	this.doSubURL();
};

/**
 * 设置排序字段
 * 
 * @param field
 * @return
 */
PageControl.prototype.orderBy = function(field) {
	this.settings.post_form["pageResult.orderBy"].value = field;
	this.pageFirst();
};



//////////////////////// handler 
/**
 * 跳转页面
 */
function pageGo(obj) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pageGo();
}
/**
 * 跳转页面
 */
function pageGo(obj,pageidx) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pageGo(pageidx);
}
/**
 * 下一页
 * 
 * @param obj
 * @return
 */
function pageNext(obj) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pageNext();
}
/**
 * 最后一页
 * 
 * @param obj
 * @return
 */
function pageLast(obj) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pageLast();
}
/**
 * 前一页
 * 
 * @param obj
 * @return
 */
function pagePre(obj) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pagePre();
}
/**
 * 第一页
 * 
 * @param obj
 * @return
 */
function pageFirst(obj) {
	var temppageControl=eval(obj);
	var freemethod=temppageControl.getHttpFreeOperateHandler();
	if(freemethod!=null)
		freemethod(temppageControl);
	temppageControl.pageFirst();
}
