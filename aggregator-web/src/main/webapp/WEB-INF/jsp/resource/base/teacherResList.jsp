<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<head>
    <script type="text/javascript" src="js/resource/new_resbase.js"></script>
    <script type="text/javascript" src="js/resource/res_index.js"></script>
<script type="text/javascript">
	var p1,p2; 
	var defaultSearchValue="${srhValue}";
	var defaultUserid="${userid}";
    var audiosuffix="${audiosuffix}";
    var videosuffix="${videosuffix}";
    var imagesuffix="${imagesuffix}";
	var tchuserid="${userid}";
    var tuid="${param.userid}";
    $(function(){
		
		if(defaultSearchValue!=null
				&&defaultSearchValue.length>0)
			$("#searchValue").val(defaultSearchValue);

        if(defaultUserid.length>0||tuid=="0"){
            $("#sp_school").hide();
        }

		p1=new PageControl({
			post_url:'resource?m=ajaxUser',
			page_id:'page1',
			page_control_name:"p1",		//分页变量空间的对象名称
			post_form:document.page1form,		//form
			http_free_operate_handler:beforeAjaxList,
			gender_address_id:'page1address',		//显示的区域
			http_operate_handler:afterAjaxList,	//执行成功后返回方法
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:10,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"teacherList"
		});

		p2=new PageControl({
			post_url:'resource?m=ajaxList',
			page_id:'page2',
			page_control_name:"p2",		//分页变量空间的对象名称
			post_form:document.page2form,		//form
			http_free_operate_handler:beforeTchResAjaxList,
			gender_address_id:'page2address',		//显示的区域
			http_operate_handler:afterTchResAjaxList,	//执行成功后返回方法
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:20,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"teacherReses"
		});
        if(defaultUserid.Trim().length>0||defaultSearchValue.Trim().length>0)
		    pageGo('p1');
	});
    /**
    *执行前判断
     */
    function doP1Search(){
        var val=$("#searchValue").val();
        if(val.Trim().length>0){
            pageGo('p1');
        }
    }

	function   beforeAjaxList(p){
		var param = {};
		
		if($("#searchValue").val().length>0){
			param.username = $("#searchValue").val();
            //defaultSearchValue=param.realname;
		}
		if(defaultUserid.length>0){
			param.userid=defaultUserid;
			defaultUserid="";
		}
		p.setPostParams(param);
	}
	
	function afterAjaxList(rps){
        $("#schoolname").show();
        page2address.style.display='block';
        $("#sp_teacher").html('');
		var html='<h1 id="h_teacherList">相关教师</h1><ul id="teacherList"></ul>';
        $("#sp_teacher").append(html);
		if(rps!=null&&rps.presult.list.length>0){
            var isflag=true;
            $.each(rps.objList[0],function(idx,itm){
                html="<li id='tch_li_"+itm.userid+"'><span>"+itm.resnum+"</span><input type='hidden' value='"+(itm.schoolname==null?"无":itm.schoolname)+"'/>";
                if(itm.resdegree==3){
                    html+="<b class='ico64' title='校内'></b>";
                }else{
                    html+='<b class="ico63" title="云端"></b>';
                }
                html+="<a href="+"javascript:getTchReses("+itm.userid+",'"+(itm.schoolname==null?"无":itm.schoolname)+"')"+">"+ itm.username +"</a>";
                html+="</li>";
                if($("li[id='tch_li_"+itm.userid+"']").length<1){
                    $("#teacherList").append(html);

                }else{
                    if($("li[id='tch_li_"+itm.userid+"'] input[type='hidden']").val()=="无"){
                        $("li[id='tch_li_"+itm.userid+"']").remove();
                        $("#teacherList").append(html);

                    }
                }
            })
            $("#sp_school").html('');
            if(rps.objList.length>1){
                $.each(rps.objList[1],function(idx,itm){
                    var h='';
                    if(idx==0)
                        h+='<h1>相关学校</h1>';
                    h+='<a  href="javascript:;" onclick="openUl(\'school_stu_'+itm.schoolname+'\')"><h2>'+itm.schoolname+'</h2></a>';
                    h+='<ul id="school_stu_'+itm.schoolname+'" style="display:none"></ul>';
                    if($("ul[id='school_stu_"+itm.schoolname+"']").length<1){
                        $("#sp_school").append(h)
                    }
                })
            }
            if(rps.objList.length>1){
                if(rps.objList[1].length>0){
                $.each(rps.objList[2],function(idx,itm){
                    html="<li id='s_li_"+itm.userid+"'><span>"+itm.resnum+"</span><input type='hidden' value='"+(itm.schoolname==null?"无":itm.schoolname)+"'/>";
                    if(itm.resdegree==3){
                        html+="<b class='ico64' title='校内'></b>";
                    }else{
                        html+='<b class="ico63" title="云端"></b>';
                    }
                    html+="<a href="+"javascript:getTchReses("+itm.userid+",'"+(itm.schoolname==null?"无":itm.schoolname)+"')"+">"+ itm.username +"</a>";
                    html+="</li>";
                    var len=$("ul[id='school_stu_"+itm.schoolname+"'] li[id='s_li_"+itm.userid+"']").length;
                    if(len<1)
                        $("ul[id='school_stu_"+itm.schoolname+"']").append(html);
                });

                }else{
                    var reshtml="";
                    reshtml+="<tr><td colspan='2'>暂无数据！</td></tr> ";
                    $("#teacherReses").html(reshtml);
                    $("#schoolname").parent().hide();
                    page2address.style.display='none';
                }
            }
            if( $("#teacherList li").length<1){
                $("#sp_teacher").html('');
                $("#sp_school ul:first").show();
            }
            $("#sp_school ul").each(function(idx,itm){
                if($(this).children("li").length<1)
                $(this).remove();
            })
            //得到分校的相关信息
            if($("#dv_search_user ul li:first a").length>0)
                eval("("+$("#dv_search_user ul li:first a").attr("href").replace("javascript:","")+")");
          //  else
                //eval("("+$("#teacherList li a").filter(function(){return this.innerText.Trim()==defaultSearchValue}).first().attr("href").replace("javascript:","")+")")
		}else{
			html+="<li>没有找到！</li>";
			var reshtml="";
            reshtml+="<tr><td colspan='2'>暂无数据！</td></tr> ";
			$("#teacherReses").html(reshtml);
		//	refreshTchResult(rps);
		}
	}
	function openUl(id){
        var dis=$("#"+id).css("display");
        if(dis=="none")
            $("#"+id).css("display","block");
        else
            $("#"+id).css("display","none");

    }
	function beforeTchResAjaxList(p){
		var param = {};
        if(tchuserid!=null&&(tchuserid+"").length>0)
		    param.userid = tchuserid;
        param.sharestatus=-3;
		p.setPostParams(param);
	}
	
	function afterTchResAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){

			$.each(rps.objList,function(idx,itm){
                html+="<tr>";
                html+="<td>";
                itm.filesuffixname=itm.filesuffixname.toLowerCase();
                if(itm.filesuffixname==".doc"||itm.filesuffixname==".docx")
                    html+="<b class='ico_doc1'></b>";
                else if(itm.filesuffixname==".xls"||itm.filesuffixname==".xlsx")
                    html+="<b class='ico_xls1'></b>";
                else if(itm.filesuffixname==".ppt"||itm.filesuffixname==".pptx")
                    html+="<b class='ico_ppt1'></b>";
                else if(itm.filesuffixname==".swf")
                    html+="<b class='ico_swf1'></b>";
                else if(itm.filesuffixname==".pdf")
                    html+="<b class='ico_pdf1'></b>";
                else if(itm.filesuffixname==".vsd")
                    html+="<b class='ico_vsd1'></b>";
                else if(itm.filesuffixname==".rtf")
                    html+="<b class='ico_rtf1'></b>";
                else if(itm.filesuffixname==".txt")
                    html+="<b class='ico_txt1'></b>";
                else if(audiosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_mp31'></b>";
                else if(videosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_mp41'></b>";
                else if(imagesuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_jpg1'></b>";
                else
                    html+="<b class='ico_other1'></b>";
                html+="</td>";
                html+="<td><p><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                html+="<a href='javascript:;' onmouseover=\"getResCourse('"+itm.resid+"')\" onmouseout=\"dv_res_course.style.display='none';ul_course.innerHTML='';\"  class='font-darkblue'>知识点</a>&nbsp;&nbsp;&nbsp;";
                if(itm.resdegree!=3){
                    html+="<span class='ico70' title='北京四中网校标准资源库'></span></p>";
                }else
                    html+="<span>"+itm.username+"</span>";


                html+="<p><div class='wrapline'>"+(itm.resintroduce==null?"无":itm.resintroduce)+"</div></p>";
                html+="<p class='text_info'>"+itm.subjectname+","+itm.gradename+","+itm.restypename+","+itm.filetypename
                        +"<span class='ico41' title='赞'></span><b>"+itm.praisenum+"</b>"
                        +"<span class='ico73' title='推荐'></span><b>"+itm.recomendnum+"</b>"
                        +"<span class='ico58a' title='收藏'></span><b>"+itm.storenum+"</b>"
                        +"<span class='ico46' title='浏览'></span><b>"+itm.clicks+"</b>"
                        +"<span class='ico59' title='下载'></span><b>"+itm.downloadnum+"</b></p></td>";
                html+="</tr> ";
			});
		}else{
            html+="<tr><td colspan='2'>暂无数据！</td></tr> ";
		}
		$("#tchResTotal").html(rps.presult.recTotal);
		p2.setPageSize(rps.presult.pageSize);
		p2.setPageNo(rps.presult.pageNo);
		p2.setRectotal(rps.presult.recTotal);
		p2.setPagetotal(rps.presult.pageTotal);
		p2.Refresh();
		$("#teacherReses").html(html);
	}

	function refreshTchResult(rps){
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
		var html="";
		html+='共 <span class="F_red">'+p1.getRectotal()+'</span>'+'条&nbsp;';
		if(p1.getPageNo()==1)
			html+= '<img src="'+p1.getRootProject()+'images/page02_a.gif" name="pagePre1" id="pagePre1" title="上一页" width="14" height="16" border="0" />&nbsp;';
		else
			html+= '<a href="javascript:pagePre(\'p1\');">'
				+'<img src="'+p1.getRootProject()+'images/page02_b.gif" name="pagePre1" id="pagePre1" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		if(p1.getPageNo()==p1.getPagetotal())
			html+= '<img src="'+p1.getRootProject()+'images/page03_a.gif" name="pagePre1" id="pagePre1" title="上一页" width="14" height="16" border="0" />&nbsp;';
		else
			html+= '<a href="javascript:pageNext(\'p1\');">'
				+'<img src="'+p1.getRootProject()+'images/page03_b.gif" name="pageNext1" id="pageNext1" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		$("#tchResult").html(html);
	}
	
	function getTchReses(userid,schoolname){
		getTchInfo(userid);
		$("#tch_li_"+userid).siblings().attr("class","");
		$("#tch_li_"+userid).attr("class","crumb");
        $("#schoolname").html(schoolname);
        $("#schoolname").parent().show();
		tchuserid=userid;
		pageGo('p2');  		
	}

	function getTchInfo(userid){
		$.ajax({
			url:'teacher?m=ajaxlistByTnOrUn',
			type:'post',
			data:{tuserid:userid },
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"&&rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						var tch=rps.objList[0];
						$("#tchRealName").html(tch.teachername);
						$("#tchUserName").html(tch.userinfo.username);
						$("#tchName").html(tch.teachername);
						$("#tchSubject").html(tch.subjects!=null?tch.subjects:"无");
					});
				}
			}
		});
	}

    function getResCourse(resid){
        var dvstyle=dv_res_course.style;
        dvstyle.left=(mousePostion.x+5)+'px';
        dvstyle.top=(mousePostion.y+5)+'px';
        dvstyle.display='block';
        // dv_res_course.style.display='none';
        if(typeof(resid)=="undefined"||resid==null||(resid+"").length<1){
            alert("异常错误，原因：参数异常!");return;
        }
        if($("#ul_course li").length<1)
            $.ajax({
                url:'resource?m=ajaxResCourse',
                type:'post',
                data:{resid:resid },
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    var h='';
                    $("#ul_course").html('');
                    if(rps.type=="success"){
                        if(rps.objList.length<1){
                            h+="<li>暂无知识点!</li>";
                            $("#ul_course").append(h);
                        }else{
                            $.each(rps.objList,function(idx,itm){
                                h+='<li value="'+itm.COURSE_ID+'">'+itm.COURSE_NAME+'</li>';
                                if(idx%30==0&&idx>0){
                                    $("#ul_course").append(h);
                                    h='';
                                }
                            });
                            if(h.length>0)
                                $("#ul_course").append(h);
                        }
                    }else{
                        h+="<li>异常错误!原因：未知!</li>";
                    }
                }
            });
    }

</script>
</head>
<body>
<div class="subpage_head"><span class="ico57"></span><strong>资源搜索</strong></div>

<div class="subpage_nav">
    <ul>
        <li class="crumb"><a href="resource?m=toteacherreslist">按教师搜索</a></li>
        <li><a href="resource?m=resList">按资源搜索</a></li>
        <li><a href="resource?m=courseList">按知识点搜索</a></li>
    </ul>
</div>

<div class="zyxt_search_layout">
    <div class="zyxt_searchT public_input">
        <input id="searchValue" name="searchValue"  placeholder="输入信息按教师名称或分校分称搜索!" type="text" class="w320"/>&nbsp;<a href="javascript:;" onclick="doP1Search()" class="an_search" title="搜索"></a>
    </div>
    <input type="hidden" value="2" name="searchType" id="searchType"/>

    <div class="zyxt_searchR">
        <p>学校：<span id="schoolname">--</span></p>
        <h6></h6>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
            <col class="w30"/>
            <col class="w750"/>
            <tbody id="teacherReses"></tbody>
        </table>
        <form id="page2form" name="page2form" method="post">
            <p align="center" id="page2address"  class="nextpage"></p>
        </form>
    </div>

    <div class="zyxt_searchL" id="dv_search_user">
       <span id="sp_teacher">
           </span>

        <span id="sp_school"></span>
    </div>
    <form id="page1form" name="page1form" method="post">
        <p align="center" id="page1address" style="display:none;"></p>
    </form>
    <div class="clear"></div>
</div>

<%@include file="/util/foot.jsp" %>


<!--知识点-->
<div class="public_windows font-black"  id="dv_res_course" name="dv_res_course" style="display:none;position: absolute">
    <h3>知识点</h3>
    <ul id="ul_course">

    </ul>
</div>
</body>
</html>
