//////////////////////////////工具
$get={	TargetDOM : null, // 储存当前拖拽的DOM对象引用
	InitHerf : function(o,rmethodName) { // 传递一个DOM对象，给其中的a添加mouseover和mouseout事件
		var v = o.getElementsByTagName('a'), L = v.length, E;
		while (L--) {
			//把A标签对象赋给E,给E添加事件
			(E = v[L]).onmousedown = function() {
				$get.DragBegin(this);
			};
			E.onmouseover = function() {
				$get.TargetDOM && $get.DragOver(this);
			};
			E.onmouseout = function() {
				$get.TargetDOM && $get.DragOut(this);
			};
			E.onmouseup = function(returnmethod) {
				$get.DragEnd(this,rmethodName);
			};
		}
	},
	DragOver : function(o) {
		o.style.backgroundColor = '#888';
		o.style.color = '#FFF';
	},
	DragOut : function(o) {
		o.style.backgroundColor = '';
		o.style.color = '';
	},
	DragBegin : function(o) {
		//A标签
		$get.TargetDOM = o;
	},
	//O为A
	DragEnd : function(o,rmdthod) {
		var TargetDOM = $get.TargetDOM, pTNode = TargetDOM.parentNode, pNode = o.parentNode, v = pTNode
				.getElementsByTagName('a'), L = v.length;
		switch (true) {
		case TargetDOM == o:
			// 这里写点击链接后发生的事件
			break;
		case pNode == pTNode.parentNode.parentNode:
			alert('无法移动，目标文件夹与源文件夹相同!');
			break;
		default:
			while (L--) {
				if (v[L] == o) {
					alert('不能移动到子目录下!');
					o.style.backgroundColor = '';
					o.style.color = '';
					$get.TargetDOM = null;
					return;
				}
			}
			//要拖入的REF
			var parentRef=$(pNode).attr("id");
			//拖拽的REF
			var cRef=$(pTNode).attr("id");
			eval("("+rmdthod+"("+parentRef+","+cRef+"))");			
		}
		$get.TargetDOM = null;
	}
}



////////////////////////////////方法体
/**	
	*查询更新dtree 
	*
	*/
	function searchDeptList(){
		$("#_dtree").html("<p align='center'><img src='img/loading_smail.gif'/>正在操作中…… </p>");
		$.ajax({
		 	 url:"dept?m=ajaxlist", 
			type:"post",			
			dataType:'json', 
			cache: false, 
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
			    if(json.type == "success"){
			    	d = new dTree('d');
			    	d.changeIcon(icon);     
			    	/*d.add("0","-1","部门信息","javascript:dTreeBindClick(-1,'for_root');");
	  					d.add("0","-1","行政部门","javascript:dTreeBindClick(0,'for_root',1)");
  	  					d.add("1","-1","教研组","javascript:dTreeBindClick(-1,'for_root',2)");  
  	  					d.add("2","-1","年级组","javascript:dTreeBindClick(-2,'for_root',3)");    
  	  					d.add("3","-1","自定义部门","javascript:dTreeBindClick(-3,'for_root',4)");*/    
			    	if(json.objList.length>0){            
			    		$.each(json.objList,function(idx,itm){  
			    			var depttype=typeof(itm.typeid)=="undefined"?"":itm.typeid;  
			    			var grade=typeof(itm.grade)=="undefined"?"":itm.grade;
			    			var subjectid=typeof(itm.subjectid)=="undefined"?"":itm.subjectid;
			    			var studyperiods=typeof(itm.studyperiods)=="undefined"?"":itm.studyperiods;			    			
			    			if(itm.parentdeptid=='-1')  
			    				d.add(itm.deptid,itm.parentdeptid,itm.deptname,"javascript:void(0)",'','','','','',"dTreeBindClick(" 
				    					+itm.deptid+",'for_root','"+depttype+"','"+grade+"','"+subjectid+"','"+studyperiods+"');");
			    			else   
			    				d.add(itm.deptid,itm.parentdeptid,itm.deptname,"javascript:to_loadDeptUser("+itm.deptid+",'"+depttype+"')",'','','','','',"javascript:dTreeBindClick("     
				    					+itm.deptid+",'sub_menu','"+depttype+"','"+grade+"','"+subjectid+"','"+studyperiods+"');");    
			    		}) 
			    	} 
			    	$("#_dtree").hide(); 
			    	$("#_dtree").html(d+"");
			    	$("#_dtree").show();		
			    	$get.InitHerf(document.getElementById('_dtree'),"moveReturnFun");
			    	//d.openAll(); 
			    }else{
			        alert(json.msg);
			    }                
			}				
		});  
	}
	
	
	/**
	*绑定Dtree事件
	*/
	function dTreeBindClick(ref,editDiv,typeid,grade,coursename,studyperiods){
		if(typeof(ref)=="undefined"||isNaN(ref)){
			alert('数据异常错误，请刷新重试！');
			return;
		}    
  		$("input[id='hd_ref']").val(ref);
  		$("input[id='hd_typeid']").val(typeid);  
  		    
		   
		try{     
			var o=$("#"+ref+" a").filter(function(){return this.className=='node'}).html().Trim();
			$("#"+editDiv+" > input[id='deptname']").val(o);
			if(ref>4){
				$("#"+editDiv+" > input[id='depttype']").val(typeid);
				$("#"+editDiv+" > input[id='ref']").val(ref);
			}
			$("#"+editDiv+" > input[id='coursename']").val(coursename);    
			$("#"+editDiv+" > input[id='studyperiods']").val(studyperiods); 
			$("#"+editDiv+" > input[id='grade']").val(grade);  
		}catch(e){} 
		var y=mousePostion.y; 
		var x=mousePostion.x;  
		//判断是不是IE8以下的浏览器浏览
		if ($.browser.msie && (parseInt($.browser.version) <= 7)){
			y+=parseFloat(document.documentElement.scrollTop);
			x+=parseFloat(document.documentElement.scrollLeft); 
		}
		
		$("#"+editDiv).css({"left":x+"px","top":y+"px","position:":"relative"});
		
		//处理，方便进行计算 
		showDivId=showDivId.replace(editDiv+"!",""); 
		showDivId+=editDiv+"!";
		$("#"+editDiv).show('fast');   
		
	}
	/**
	*鼠标离开的时候
	*/
	function mouseoverLi(o){
		$(o).css({"background-color":"#e0dcdc","cursor":"pointer"});
		
		$(o).unbind("mouseout");
		$(o).bind("mouseout",function(){
			$(this).css("background-color","");
		})  
	}
	/**
	*进入修改层
	*/
	function to_editDeptname(p_did,t_did){
		var deptname=$("#"+p_did+" input[id='deptname']").val();
		$("#"+t_did+" input[type='text']").val(deptname);
		var typeid=$("#"+p_did+" input[id='depttype']").val();
		var studyperiods=$("#"+p_did+" input[id='studyperiods']").val();
		var coursename=$("#"+p_did+" input[id='coursename']").val();
		var grade=$("#"+p_did+" input[id='grade']").val();
				
		$("#"+t_did+" select[id='depttype']").val(typeid);
		$("#"+t_did+" select[id='coursename']").val(coursename);
		$("#"+t_did+" select[id='studyperiods']").val(studyperiods);
		$("#"+t_did+" select[id='grade']").val(grade); 
		
		showModel(t_did,false);
		$(document).unbind("click");
		
		//行政部门隐藏学科、年级
		var typeidObj=$("#hd_typeid"); 
		var courseObj=$("#"+t_did+" select[id='coursename']");
		var gradeObj=$("#"+t_did+" select[id='grade']");
		$("#tr_type").hide();
		if(typeidObj.val()=="1"){
			courseObj.parent().parent().hide();
			gradeObj.parent().parent().hide();    
		}else if(typeidObj.val()=="2"||typeidObj.val()=="5"){       
			courseObj.parent().parent().show(); 
			gradeObj.parent().parent().show();
			$("#tr_type").show(); 
		}else{ 
			courseObj.parent().parent().show(); 
			gradeObj.parent().parent().show();
		}  
		   
	}
	/**  
	*进入添加层
	*/
	function to_createDeptname(p_did,t_did){
	 	showModel(t_did,false);
		$(document).unbind("click");
		$("#"+t_did+" input[id='c_deptname']").val(""); 
		$("#"+t_did+" input[id='c_deptname']").focus();
		
		//行政部门隐藏学科、年级
		var typeidObj=$("#hd_typeid");
		var courseObj=$("#"+t_did+" select[id='coursename']");
		var gradeObj=$("#"+t_did+" select[id='grade']");
		$("#tr_type").hide();
		if(typeidObj.val()=="1"){
			courseObj.parent().parent().hide();
			gradeObj.parent().parent().hide();    
		}else if(typeidObj.val()=="2"||typeidObj.val()=="5"){       
			courseObj.parent().parent().show(); 
			gradeObj.parent().parent().show();
			$("#tr_type").show();
		}else{ 
			courseObj.parent().parent().show(); 
			gradeObj.parent().parent().show();
		} 
		 
	}	
	
	/** 
	*修改
	*/
	function doUpdateDeptname(deptdivid,nodedivid){
		var ref=$("#hd_ref");
		if(ref.val().Trim().length<1||!isNaN(ref)){
			alert('异常错误，系统尚未获取到您要修改的数据标识，请刷新页面后重试!');
			ref.focus();
			return;
		} 
	
		var deptnameObj=$("#"+deptdivid);
		if(deptnameObj.val().Trim().length<1){
			alert('您尚未输入修改后的部门名称，请输入!\n\n提示：不允许修改成空节点!');
			deptnameObj.focus();
			return;
		}
		
		var typeidObj=$("#hd_typeid");
		if(typeidObj.val().Trim().length<1){
			alert('您尚未选择部门类型，请选择!');
			typeidObj.focus();
			return;  
		}   
		var courseObj=$("#"+nodedivid+" select[id='coursename']");
		var studyperiodsObj=$("#"+nodedivid+" select[id='studyperiods']");
		var gradeObj=$("#"+nodedivid+" select[id='grade']");
		
		if(typeidObj.val()=="2"){
			if(courseObj.val().Trim().length<1){
				alert('您尚未选择科目，请选择!');   
				courseObj.focus();  
				return;  
			} 
//			if(studyperiodsObj.val().Trim().length<1){
//				alert('您尚未选择学段，请选择!\n\n注意：如果是教研组，必须分别选择，科目，学段属性!');
//				studyperiodsObj.focus();
//				return;
//			}
//			if(gradeObj.val().Trim().length<1){
//				alert('您尚未选择年级，请选择!');
//				gradeObj.focus();
//				return;  
//			}  
		}else if(typeidObj.val()=="3"){
			if(gradeObj.val().Trim().length<1){
				alert('您尚未选择年级，请选择!');
				gradeObj.focus();
				return;  
			}    
		}    
		
	
		if(studyperiodsObj.val().Trim().indexOf('初')!=-1){
			if(gradeObj.val().Trim().indexOf("高")!=-1){
				alert('错误，"'+gradeObj.val().Trim()+'"不属于"'+studyperiodsObj.val().Trim()+'"!请重新选择!');
				gradeObj.focus();
				return;
			}
		}else if(studyperiodsObj.val().Trim().indexOf('高')!=-1){
			if(gradeObj.val().Trim().indexOf("初")!=-1){
				alert('错误，"'+gradeObj.val().Trim()+'"不属于"'+studyperiodsObj.val().Trim()+'"!请重新选择!');
				gradeObj.focus();
				return;
			}
		}  
		
		var params={deptid:ref.val().Trim(),deptname:deptnameObj.val().Trim(),typeid:typeidObj.val().Trim()};// 
		if(courseObj.val().Trim().length>0) 
			params.subjectid=courseObj.val().Trim(); 
		if(studyperiodsObj.val().Trim().length>0)
			params.studyperiods=studyperiodsObj.val().Trim();
		if(gradeObj.val().Trim().length>0) 
			params.grade=gradeObj.val().Trim();
		if(!confirm("您确认修改'"+deptnameObj.val().Trim()+"'吗？"))
			return ;
			
		$.ajax({
		 	 url:"dept?m=ajaxmodify",  
			type:"post",
			data:params,
			dataType:'json',
			cache: false,
			error:function(){ 
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
			    if(json.type == "success"){
			    	var dname=deptnameObj.val().Trim();
			    	$("div[id='"+ref.val().Trim()+"'] a").filter(function(){return this.className=='node'}).html(dname);			    	
			    	close_Div("editnode");
			    	bodyClick();
			    }else{
			        alert(json.msg);
			    }                
			}				
		});   
	}
	/*
	*执行添加部门
	*/  
	function createDept(deptnameid,nodedivid) {
		var ref=$("#hd_ref");
		if(ref.val().Trim().length<1||!isNaN(ref)){
			alert('异常错误，系统尚未获取到您要添加数据的上级部门，请刷新页面后重试!');
			ref.focus();
			return;
		}
		var oref=ref.val().Trim();
//		if(oref==-1){
//			oref="0";
//		}
	
		var deptnameObj=$("#"+deptnameid);
		if(deptnameObj.val().Trim().length<1){
			alert('您尚未输入添加的部门名称，请输入!\n\n提示：不允许修改成空节点!');
			deptnameObj.focus();
			return;
		}
		
		//var typeidObj=$("#"+nodedivid+" select[id='depttype']");
		var typeid='';
		var typeidObj=$("#hd_typeid"); 
		if(typeidObj.val().Trim().length<1){
			alert('您尚未选择部门类型，请选择!\n\n注意：如果是教研组，必须分别选择，学科，学段属性!');
			return;
		} 
		typeid=typeidObj.val().Trim();
		if(typeidObj.val()=="2"||typeidObj.val()=="5"){
			var type=$("input[name='rdo_type']").filter(function(){return this.checked==true});
			if(type.length<1){
				alert('您尚未选择部门类型，请选择!\n\n注意：如果是教研组，必须分别选择，学科，学段属性!');
				return;
			} 
			typeid=$(type).get(0).value;
		} 
		 
		var params={parentdeptid:oref,deptname:deptnameObj.val(),typeid:typeid};
		 
		var courseObj=$("#"+nodedivid+" select[id='coursename']"); 
		var gradeObj=$("#"+nodedivid+" select[id='grade']");
		var studyperiodsObj=$("#"+nodedivid+" select[id='studyperiods']"); 
		   
  
		if(typeidObj.val()=="2"){
			if(courseObj.val().Trim().length<1){
				alert('您尚未选择学科，请选择!');
				courseObj.focus();  
				return;  
			} 
//			if(studyperiodsObj.val().Trim().length<1){
//				alert('您尚未选择学段，请选择!\n\n注意：如果是教研组，必须分别选择，学科，学段属性!');
//				studyperiodsObj.focus();
//				return;
//			}
//			if(gradeObj.val().Trim().length<1){
//				alert('您尚未选择年级，请选择!');
//				gradeObj.focus();
//				return;  
//			}    
		}else if(typeidObj.val()=="3"){
			if(gradeObj.val().Trim().length<1){
				alert('您尚未选择年级，请选择!');
				gradeObj.focus();
				return;  
			}
		}
		
		//行政部门隐藏学科、年级
		if(typeidObj.val()=="1"){
			courseObj.hide();
			gradeObj.hide();
		}else{  
			courseObj.show();
			gradeObj.show();
		}
		
		  
		if(studyperiodsObj.val().Trim().indexOf('初')!=-1){
			if(gradeObj.val().Trim().indexOf("高")!=-1){
				alert('错误，"'+gradeObj.val().Trim()+'"不属于"'+studyperiodsObj.val().Trim()+'"!请重新选择!');
				gradeObj.focus();
				return;
			}
		}else if(studyperiodsObj.val().Trim().indexOf('高')!=-1){
			if(gradeObj.val().Trim().indexOf("初")!=-1){
				alert('错误，"'+gradeObj.val().Trim()+'"不属于"'+studyperiodsObj.val().Trim()+'"!请重新选择!');
				gradeObj.focus();
				return;
			}
		}
		if(courseObj.val().Trim().length>0)  
			params.subjectid=courseObj.val().Trim();  
		if(studyperiodsObj.val().Trim().length>0)
			params.studyperiods=studyperiodsObj.val().Trim();
		if(gradeObj.val().Trim().length>0)
			params.grade=gradeObj.val().Trim();
		
		if(!confirm("您确认添加'"+deptnameObj.val().Trim()+"'吗？")) 
			return ;
		
		$.ajax({
			 url:"dept?m=ajaxsave",  
			type:"post", 
			data:params,
			dataType:'json',
			cache: false,  
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
			    if(json.type == "success"){
			    	close_Div("createnode");  
			    	bodyClick();
			    	
			    	//刷新DTree 
			    	searchDeptList();
			    	$("#txt_search").val('');
			    	pageGo('p1');  
			    	
			    }else{
			        alert(json.msg);
			    }                  
			}			
		});		
	} 
	
	/**
	 * 删除
	 * @return
	 */
	function dodelete(){
		var ref=$("#hd_ref");
		if(ref.val().Trim().length<1||!isNaN(ref)){
			alert('异常错误，系统尚未获取到您要删除的数据标识，请刷新页面后重试!');
			ref.focus();
			return;
		}
		if(!confirm("您确定要删除该部门吗?\n\n提示：删除部门的时候，会连同删除该部门下的所有子部门，请谨慎操作!"))
			return;
		$.ajax({
			url:"dept?m=dodelete",  
			type:"post", 
			data:{deptid:ref.val()}, 
			dataType:'json', 
			cache: false,
			error:function(){
				alert('系统未响应，请稍候重试!');
			}, 
			success:function(json){
			    if(json.type == "success"){
			    	alert(json.msg);
			    	$('#sub_menu').hide();
			    	//刷新DTree searchDeptList();
			    	location.href='dept?m=list';
			    }else{
			        alert(json.msg);
			    }                
			}			
		});		 
	}
	/**
	*移动
	*/
	function moveReturnFun(pref,cref){
		if(isNaN(pref)||isNaN(cref)){ 
			alert('异常错误，拖拽错误!');
			return;
		}
		
		$.ajax({
			 url:"dept?m=doUpdateParentId",   
			type:"post",
			data:{parentdeptid:pref, deptid:cref}, 
			dataType:'json',
			cache: false,
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
			    if(json.type == "success"){
			    	//刷新DTree
			    	searchDeptList();
			    }else{
			        alert(json.msg);
			    }                
			}			
		}) 
	}
	
	function closeModel(showId){				
		showAndHidden(showId,'hide');
		showAndHidden('fade','hide');
		try{
			if(yearSelect!=undefined&&yearSelect!="undefined"&&yearSelect==null){
				yearSelect=false;
			}
		}catch(e){}
	}
	  
	/** 
	*关闭弹出层
	*/
	function close_Div(div){
		closeModel(div);
		$(document).bind("click",bodyClick);
	}

	var showDivId="";
	/**
	*设置空白区域点击，取消弹出层
	*/
	function bodyClick(){
		if(showDivId.Trim().length<1){
			return;
		}
		var sdDivIdArray=showDivId.split("!");
		
		for(i=0;i<sdDivIdArray.length;i++){
			if(sdDivIdArray[i].Trim().length>0){
				var sdtmp=$("#"+sdDivIdArray[i].Trim());
				if(sdtmp.length>0&&sdtmp.css("display").Trim()=="block"){
					var width=parseFloat(sdtmp.css("width").replace("px",""));
					var height=parseFloat(sdtmp.css("height").replace("px",""));
					var top=parseFloat(sdtmp.css("top").replace("px",""));
					var left=parseFloat(sdtmp.css("left").replace("px",""));
					if(mousePostion.x>(left+width)||mousePostion.x<left
							||mousePostion.y<top||mousePostion.y>(top+height))
						sdtmp.hide();				
				}
			}		
		}		
	}	
	
	
	
	
	/**
	 * 选中进入接收区域。
	 * @param allowid
	 * @param nowid
	 * @return
	 */
	function toAddInput(firstid,jieshouid){
		var allowSElectObj=document.getElementById(firstid);
		var genderHtml='';
		if(allowSElectObj.options.length>0){
			var selectedUserId='';
			var len=allowSElectObj.options.length;
			for(i=0;i<len;i++){
				var im=allowSElectObj.options[i];
				if(im.selected)
					genderHtml+='<option value="'+im.value+'">'+im.text+'</option>';
			}
			//添加
			if(genderHtml.length>0)
				$("#"+jieshouid).append(genderHtml);
			
			var z=0; 
			while(len>z){			 
				var im=allowSElectObj.options[z];
				if(im.selected)
					allowSElectObj.remove(z);
				else
					z++;
				  len=allowSElectObj.options.length;				
			}
			loadAutoComplete();
			//$("#btn_zhiding").hide();
		} 
	}  
	/**
	 * 添加部门人员
	 * @return
	 */
	function doSubmitUser(){
		var username=$("#txt_search").val();
		if(typeof(username)=="undefined"||username.Trim().length<1||username.indexOf("/")==-1){
			alert('请选择教师后添加!');  
			return;
		}
		var deptid=$("#ref").val();
		if(typeof(deptid)=="undefined"||deptid.Trim().length<1){
			alert('未获取到部门标识!请刷新页面重试!');
			return;
		}
	
		//User_Id
		username=username.split("/")[0];
		if(typeof(username)=='undefined'||username.length<1){
			alert('无效的用户标识!');
			return;
		}
			
		$.ajax({
			url:"dept?m=doSaveDeptUser",
			type:"post",
			data:{deptid:deptid,username:username},
			dataType:'json',
			cache: false,
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
				if(json.type=="error")
					alert(json.msg);
				else{
					$("#txt_search").val('');
					alert(json.msg);  
					pageGo('p1'); 
				}  
			}   
		});
	}
	

	
	/**
	 * 初始化AutoComplate
	 * @return
	 */
	function loadAutoComplete(){
		var ptype=$("#depttype").val();
		if(ptype==null||ptype.Trim().length<1){
			alert('未获取部门类型!请刷新页面重试!');
			return;
		}  
		$("#txt_search").val('');
		$("#txt_search").unautocomplete();
		$.ajax({ 
			 url:"dept?m=autoFillDeptUser",
				type:"post",  
				data:{ptype:ptype},
				dataType:'json', 
				cache: false,
				error:function(){
					alert('系统未响应，请稍候重试!');
				},
				success:function(json){
					if(json.type=="error")
						alert(json.msg);
					else{
						if(json.objList.length>0){
							$("#txt_search").autocomplete(json.objList,{
								minChars: 0,
								width: 210,
								matchContains: true,
								autoFill: false,
								formatItem: function(row, i, max) {
									return row.username+"/"+row.realname;
								}, 
								formatMatch: function(row, i, max) {
                                    return row.username+"/"+row.realname;
								}, 
								formatResult: function(row) {
                                    return row.username+"/"+row.realname;
								},selectedoperate:function(v){
								//	textAutoCompleteed(v);
								}				
							});
						}
					}
				}
		});
		}
	/**
	 * 自动填充完毕后
	 * @param v
	 * @return 
	 */
	function textAutoCompleteed(v){
		var objArray=v.split(" ");
		if(objArray.length>0){
			var id=objArray[0].Trim();
			var options=document.getElementById("allow_user_assert").options;
			$.each(options,function(idx,itm){  
				if(itm.value.split('|')[1].Trim()==id){
					itm.selected=true;
				}
			});  
		}
	}
	
	
	function beforajaxList(p){
		var ref=$("#sub_menu input[id='ref']").val().Trim();
		var dtype=$("#sub_menu input[id='depttype']").val().Trim();
		var param={ref:ref,ptype:dtype};	
		p.setPostParams(param);
	}
	
	//执行分页查询后
	function afterajaxList(rps){
		var ghtml='';  
		if(rps.type=="error"){ 
			alert(rps.msg);
		}else{
			if(rps.objList.length>0){
				$("a").filter(function(){return this.id.indexOf('sd')!=-1}).css("color","black");
				if(rps.objList[0]!=null){ 
					$("div[id='"+rps.objList[0]+"']").children("a").css("color","gray");
					$("#ref").val(rps.objList[0]);
					$("#hd_ref").val(rps.objList[0]);
					
				} 
				if(rps.objList[2]!=null){ 
					$("#depttype").val(rps.objList[2]);
					$("#hd_typeid").val(rps.objList[2]);
				}
				ghtml=['<tr>',
			         	'<th>姓名</th>',
			         	'<th>职位</th>',
			         	'<th>操作</th>',
			         '</tr>'];
				if(rps.objList[1]!=null&&rps.objList[1].length>0){
					$.each(rps.objList[1],function(idx,itm){
						var rstr=typeof(itm.roleinfo.roleid)!='undefined'?itm.rolename+'<input type="hidden" value=\''+itm.roleinfo.roleid+'\'/>':'无';
						ghtml[ghtml.length]=  
							'<tr>'   
						        +'<td>'+itm.realname+'<input type="hidden" value=\''+itm.deptid+'\'/></td>'  
						        +'<td style="cursor: pointer" id="role_'+itm.userref+'">'
						        +rstr     
						        +'</td>'                
						        +'<td><a  class="font-blue"  href="javascript:doDelDeptUser('+itm.deptid+',\''+itm.userref+'\')">删除</a></td>'      
						    +'</tr>';     
 					});  
				}else     
					ghtml[ghtml.length]=['<tr><td colspan="3">此部门暂无人员!</td></tr>'];
				
				//部门职务
				if(rps.objList[3]!=null&&rps.objList[3].length>0){
					var rolehtm='<option value="">无</option>';
					$.each(rps.objList[3],function(idx,itm){
						rolehtm+='<option value="'+itm.roleid+'">'+itm.rolename+'</option>';
					}); 
					$("#sel_role").html(rolehtm);
				}

                $("#maintbl").html(ghtml.join(''));
                $("#maintbl tr:odd").addClass("trbg1");
                loadAutoComplete();
                //增加职务列表事件
                bindTdClick();
			}else{
                ghtml='<tr><td colspan="3"><p style="display:inline;">'+rps.msg+'</p></td></tr>';
                $("#maintbl").html(ghtml);
            }
		} 
	}
	
	function bindTdClick(){
		var td=$("td").filter(function(){return this.id.indexOf('role_')!=-1});
		if(td.length>0){
			$.each(td,function(idx,itm){
				$(itm).bind("click",function(){
					tdClick(idx,itm);
				});  
			});
		}  
	}
	
	function tdClick(idx,obj){
		var rolechange=$("#maintbl select[id='sel_role']");
		if(rolechange.length>0)
			roleChange(undefined,rolechange);
		
		var roleid=$(obj).children('input').val();
		var h="<select id='sel_role'>";
		h+=$("#sel_role").html();
		h+="</select>";
		$(obj).html(h);
		if(roleid!=null&&roleid.length>0)
			$("#maintbl select[id='sel_role']").val(roleid);
		$(obj).unbind('click');
		 
		$("#maintbl select[id='sel_role']").bind("change",function(){
			roleChange(idx,this);  
		});
		  
	}  
	
	function roleChange(idx,obj){    
		var roleid=$(obj).val();     
		var deptid=$(obj).parent('td').siblings().eq(0).children('input').val();
		var userid=$(obj).parent('td').attr('id');
		userid=userid.substring(userid.indexOf("_")+1); 
		//恢复事件  
		$(obj).parent().bind("click",function(){
			tdClick(idx,this);  
		});   
		var h=$(obj).find('option:selected').text();
		if(roleid!=null&&roleid.length>0)
			h+='<input type="hidden" value="'+roleid+'"/>';
		doSetPostName(userid,roleid,deptid);
		$(obj).parent().html(h);   
	}
	
	
	/**
	 * 执行添加
	 * @param udref  userdept ref 要更新的用户表示
	 * @param tuserid 更新的教师
	 * @return
	 */
	function doSetPostName(uid,rid,deptid){
		if(typeof(uid)=="undefined"||uid.length<1){
			alert('异常错误!未获取用户标识!');
			return;
		}
		if(typeof(deptid)=="undefined"||isNaN(deptid)){
			alert('异常错误!未获取部门标识!');
			return;
		} 
		 
		  
		$.ajax({ 
			 url:"dept?m=doUpdPostName",
				type:"post",  
				data:{uid:uid,roleid:rid,deptid:deptid},
				dataType:'json',
				cache: false,
				error:function(){
					alert('系统未响应，请稍候重试!');
				},
				success:function(json){
					if(json.type=="error")
						alert(json.msg);
					else{
						//alert(json.msg);
						$("#txt_search").val('');
						pageGo('p1'); 
					}
				}
		});
	}
	
	function doDelDeptUser(deptid,uid){
		if(typeof(uid)=="undefined"||uid.Trim().length<1){
			alert('未获取到用户标识!请刷新页面重试!');  
			return;
		}
		if(typeof(deptid)=="undefined"||isNaN(deptid)){
			alert('未获取到部门标识!请刷新页面重试!');
			return;  
		}
		if(!confirm('确认删除?')){return;}
		
		$.ajax({
			url:"dept?m=doDelDeptUser",
			type:"post",
			data:{deptid:deptid,uid:uid},
			dataType:'json',
			cache: false,
			error:function(){
				alert('系统未响应，请稍候重试!');
			}, 
			success:function(json){
				if(json.type=="error")
					alert(json.msg);
				else{
					$("#txt_search").val('');
					//alert(json.msg);
					pageGo('p1'); 
				}  
			}   
		});
	}
	
	