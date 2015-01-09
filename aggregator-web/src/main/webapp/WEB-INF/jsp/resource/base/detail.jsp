<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.entity.resource.ResourceInfo" %>
<%@page import="com.school.util.UtilTool"%>
<%@ include file="/util/common-jsp/common-zrxt.jsp" %>
<%String fname=null;//文件名称
    String direcparent="uploadfile/";
    Object resObj=request.getAttribute("resObj");
    if(resObj!=null){
        ResourceInfo rstmp=(ResourceInfo)resObj;
      // fileSystemIpPort=UtilTool.getResourceLocation(rstmp.getResid(),1);
        if(rstmp.getResid()>0)
            direcparent="clouduploadfile";
    }
//%>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/resComment_star.css"/>
    <script type="text/javascript" src="js/resource/res_index.js"></script>
    <script type="text/javascript" src="js/resource/resbase.js"></script>
    <script type="text/javascript" src="js/comment/resCommentScorePlug.js"></script>
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
    <script type="text/javascript" src="flexpaper/swfobject/swfobject.js"></script>
	<script type="text/javascript" src="flexpaper/flexpaper.js"></script>
    <script type="text/javascript"
            src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript"
            src="fancybox/jquery.fancybox-1.3.4.js"></script>

    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css"/>
    <script type="text/javascript">
    	//文件地址:在msg.property中进行编辑
   		var resourcepathHead="<%=fileSystemIpPort%>/uploadfile/";
        <c:if test="${resObj.resid>0}">
            resourcepathHead="<%=fileSystemIpPort%>clouduploadfile/";
        </c:if>
   		var searchType=1;
    	var p1,p2;
        var filetype="${resObj.filetype}";
        var restype="${resObj.restype}";
        var grade='${resObj.grade}';
        var subject='${resObj.subject}';

        var p2config={};
        var cufiletype="${resObj.filetype}"

        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        $("#loading").ajaxStart(function(){
            $(this).show();
        });
        var fancyboxObj;
        $(function(){
            fancyboxObj=$("#a_click").fancybox({
                'onClosed':function(){
                    $("#dv_content").hide();
                }

            });


            $("#loading").ajaxStart(function(){
                var w=$(document).width()/2-50;
                var h=$(document).height()/2-20;
                $(this).css({"left":w+"px","top":h+"px"});
                $(this).show();
            });
            $("#loading").ajaxStop(function(){
                $(this).hide();
            });
            //加载评论
            p1=new PageControl({
                post_url:'commoncomment?m=ajaxlist',
                page_id:'page1',
                page_control_name:"p1",		//分页变量空间的对象名称
                post_form:document.page1form,		//form
                gender_address_id:'page1address',		//显示的区域
                http_free_operate_handler:beforeAjaxList,		//执行查询前操作的内容
                http_operate_handler:afterAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:10,				//当前页面显示的数量
                rectotal:0,				//一共多少
                pagetotal:1,
                operate_id:"commentList"
            });
            pageGo('p1');

            //加载相关资源
            p2=new PageControl({
                post_url:'resource?m=ajaxListByValues',
                page_id:'page2',
                page_control_name:"p2",		//分页变量空间的对象名称
                post_form:document.page2form,		//form
                gender_address_id:'page2address',		//显示的区域
                http_free_operate_handler:beforep2AjaxList,		//执行查询前操作的内容
                http_operate_handler:afterp2AjaxList,	//执行成功后返回方法
                isShowController:false,
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:10,				//当前页面显示的数量
                operate_id:"dv_tuijian_res"
            });
            //进入加载
            fn_changePanel(cufiletype);
        });

        <%--select-course.jsp--%>
        function changeGrade(div){
            var value=$("#"+div).val();
            if(value.Trim().length<1)
                return;
            global_gradeid=value.split("_")[0];
            global_subjectid=value.split("_")[1];

            if(div=='add_subgrade'){
                getTchMaterial(true);
            }else{
                getTchMaterial();
                pageGo("pCourseList");
            }
        }

        /**
         *获取教材
         * 初始教材
         * 加载专题添加页面
         */
        function getTchMaterial(isadd){
            var param={};
            param.termid=termid;
            param.gradeid=global_gradeid;
            param.subjectid=global_subjectid;
            param.atermid=termid;
            $.ajax({
                url:'teachercourse?m=getTchMaterial',
                data:param,
                type:'post',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type=="success"){
                        if(typeof(rps.objList[0])!="undefined"&&rps.objList[0]!=null){
                            $("input[id='material_id']").val(rps.objList[0].materialid);
                            if(isadd){
                                var url='teachercourse?toSaveCourse&gradeid='+global_gradeid+'&subjectid='+global_subjectid;
                                $("#dv_addCourse").load(url,function(){
                                    $("#teaching_materia_div").hide();
                                    $("#dv_course_parent").show();
                                    $("#dv_addCourse").show();
                                });
                            }
                        }else{
                            $("input[id='material_id']").val("");
                            $("#dv_course_parent").hide();
                            toSaveCoursePage();
                        }
                    }
                }
            });
        }




        function loadCoursePanel(){
            var url="teachercourse?toQryTeaCourseList&resid=${resObj.resid}";
            $("#dv_content").load(url,function(){
                $("#dv_content").show();
                $("#a_click").click();
            });
        }
        /**
         * 更改pannel
         *
         */
        function fn_changePanel(type){
            if(typeof(type)!="undefined")
                cufiletype=type;
            if(type==2)     //视频
                p2config.pageSize=4;
            else if(type==4)
                p2config.pageSize=5;
            else
                p2config.pageSize=8;
            $("#ul_xuanxiang li").removeClass("crumb");
            $("#ul_xuanxiang li[value='"+type+"']").addClass("crumb");
            //查询数据
            p2config.pageNo=1;
            pageGo('p2');
        }
        /**
        * 分页前查询(相关)
        * @param p
         */
        function beforep2AjaxList(p){
            var param={};
            //年级
            if(typeof(grade)!="undefined")
                param.gradevalues=grade;
            if(typeof(subject)!="undefined")
                param.subjectvalues=subject;
            if(typeof(restype)!="undefined")
                param.restypevalues=restype;
            if(typeof(cufiletype)!="undefined")
                param.filetypevalues=cufiletype;
            param.isunion=1;
            p.setPostParams(param);

            if(typeof(p2config.pageSize)!="undefined"){
                p2.setPageSize(p2config.pageSize);
                p2config.pageSize=undefined;
            }
            if(typeof(p2config.pageNo)!="undefined"){
                p2.setPageNo(p2config.pageNo);
                p2config.pageNo=undefined;
            }
            p2.Refresh();
        }

        function afterp2AjaxList(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                var h='';
                if(rps.objList!=null&&rps.objList.length>0){
                    if(cufiletype==2){ //视频
                        h+='<ul class="two">'
                    }else if(cufiletype==4){    //图片
                        h+='<ul class="three">'
                    }else{  //文本
                        h+='<ul class="one">';
                    }
                    $.each(rps.objList,function(idx,itm){
                         if(cufiletype==2){ //视频
                            var imgpath=resourcepathHead+itm.path+'/001'+itm.filesuffixname+'.pre.jpg';
                            h+='<li><a href="resource?m=todetail&resid='+itm.resid+'"><img src="'+imgpath+'" width="160" height="90" />'+itm.resname25word+'</a></li>'; //alt="'+itm.resname25word+'" title="'+itm.resname25word+'"
                        }else if(cufiletype==4){    //图片
                            var imgpath='resource?m=getResourceImage&resid='+itm.resid+'&w=135&h=135';//+itm.filesuffixname;
                            h+='<li><a href="resource?m=todetail&resid='+itm.resid+'"  title="'+itm.resname25word+'"><img src="'+imgpath+'" width="135" height="135" /></a></li>';
                        }else{   //动画
                            h+='<li><a href="resource?m=todetail&resid='+itm.resid+'"  title="'+itm.resname25word+'">';

                             itm.filesuffixname=itm.filesuffixname.toLowerCase();
                             if(itm.filesuffixname==".doc"||itm.filesuffixname==".docx")
                                 h+="<b class='ico_doc1'></b>";
                             else if(itm.filesuffixname==".xls"||itm.filesuffixname==".xlsx")
                                 h+="<b class='ico_xls1'></b>";
                             else if(itm.filesuffixname==".ppt"||itm.filesuffixname==".pptx")
                                 h+="<b class='ico_ppt1'></b>";
                             else if(itm.filesuffixname==".swf")
                                 h+="<b class='ico_swf1'></b>";
                             else if(itm.filesuffixname==".pdf")
                                 h+="<b class='ico_pdf1'></b>";
                             else if(itm.filesuffixname==".vsd")
                                 h+="<b class='ico_vsd1'></b>";
                             else if(itm.filesuffixname==".rtf")
                                 h+="<b class='ico_rtf1'></b>";
                             else if(itm.filesuffixname==".txt")
                                 h+="<b class='ico_txt1'></b>";
                             else if(audiosuffix.indexOf(itm.filesuffixname)!=-1)
                                 h+="<b class='ico_mp31'></b>";
                             else if(videosuffix.indexOf(itm.filesuffixname)!=-1)
                                 h+="<b class='ico_mp41'></b>";
                             else if(imagesuffix.indexOf(itm.filesuffixname)!=-1)
                                 h+="<b class='ico_jpg1'></b>";
                             else
                                 h+="<b class='ico_other1'></b>";
                            h+=itm.resname25word+'</a></li>';
                        }
                    });
                    h+='</ul>'
                }
              $("#"+p2.getOperateId()).html(h);
            }
            p2.setPageSize(rps.presult.pageSize);
            p2.setPageNo(rps.presult.pageNo);
            p2.setRectotal(rps.presult.recTotal);
            p2.setPagetotal(rps.presult.pageTotal);
            p2.Refresh();
        }





        function beforeAjaxList(p){
            var param = {};
            param.commenttype = "${commenttype}";
            param.commentobjectid = "${resObj.resid}";
            p.setPostParams(param);
        }

        function afterAjaxList(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><p><strong>"+((rps.presult.pageNo-1)*(rps.presult.pageSize)+idx+1)+"楼</strong></p>";
                    html+='<p>'+itm.commentcontext+'</p>';

                        html+='<p class="font-darkgray f_right" id="p_operate_'+itm.commentid+'">';
                    if(typeof(itm.isopper)=="undefined"||itm.isopper==null||itm.isopper<1)
                        html+='<a href="javascript:doSupportOrOppose(\''+itm.commentid+'\',true);"><span class="ico41" title="赞"></span>';
                    else
                        html+='<a href="javascript:;" style="cursor:default"><span class="ico41" title="已操作"></span>';

                        html+='<b><em id=\''+itm.commentid+'_support\'>'+itm.support+'</em></b></a>';
                    if(typeof(itm.isopper)=="undefined"||itm.isopper==null||itm.isopper<1)
                        html+= '<a  href="javascript:doSupportOrOppose(\''+itm.commentid+'\',false);"><span class="ico72" title="踩">'
                    else
                        html+= '<a  href="javascript:;"  style="cursor:default"><span class="ico72" title="已操作">'
                        html+='</span><b><em id=\''+itm.commentid+'_oppose\'>'+itm.oppose+'</em></b></a>' ;
                        html+='</p>';

                    if(itm.anonymous==1)
                        itm.commentusername="匿名用户";
                    html+='<p class="font-darkgray">'+itm.ctimeString+'&nbsp;&nbsp;&nbsp;&nbsp;来自：'+itm.commentusername+'</p>';
                    html+='</li>';
                });
            }else{
                html+="<li>";
                html+="暂无评论！！";
                html+="</li>";
            }
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(rps.presult.pageNo);
            p1.setRectotal(rps.presult.recTotal);
            p1.setPagetotal(rps.presult.pageTotal);
            p1.Refresh();
            $("#commentList").html(html);
            $("#cpn_0").html(rps.presult.recTotal);
            $("#cpn_1").html(rps.presult.recTotal);
        }

      var allowViewDoc=true;
        function timer(){
          setTimeout(function(){
              allowViewDoc=true;
          },1000);
        }
    /**
    *显示预览
    * @param loc
    * @param path
    * @param imgd
     *
     */
    function showDocView(resid,loc,path){
        if(typeof(resid)=="undefined"||resid==null)
            return;
        if(!allowViewDoc){
            alert("别着急!休息一会再点!");
            return;
        }
        allowViewDoc=false;
        timer();
        $.ajax({
            url:"resource?m=ajx_mkDocFile",
            data:{resid:resid},
            dataType:'json',
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                if(rps.type=="error"){
                    alert(rps.msg);
                    return;
                }
                var p=loc+"/"+path+"/001.swf";
                //验证是否存在

                loadSwfReview(p,'dv_show_doc_view',980,800);
                showModel('dv_doc_prview');
            },
            type:"POST"
        });
    }
    </script>
  </head>  
  <body>


  <a id="a_click" href="#dv_content"></a>
  <div id="dv_content"  style="display: none;"></div>

  <div class="subpage_head"><span class="lm_ico03"></span><strong>资源详情</strong></div>
  <div class="content1 public_input">
      <p class="t_r "><input name="searchValue" id="searchValue" type="text" class="w320"/>
          <input type="hidden" id="searchType" name="searchType" value="1"/>
          &nbsp;<a class="an_search" href="javascript:doSearch();" title="搜索"></a></p>
      <!--图片-->
      <div class="zyxt_zyxq">
      <c:if test="${fileType=='video'||fileType=='mp3'||fileType=='swf'}">
        <h1><span  id="sp_name">${resObj.resname}</span>
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span  id="sp_up_btn_resname"><a href="javascript:;" onclick="updateResColumn('${resObj.resid}','resname','sp_name','sp_up_btn_resname')" title="编辑" class="ico11"></a></span>
                  </c:if>
               <a href="javascript:loadCoursePanel()"><span class="ico51" title="发任务"></span></a>
              </h1>
              <div class="zyxt_zyxqR">
                  <p><span id="sp_grade">${resObj.gradename}</span>&nbsp;&nbsp;
                      <span id="sp_subject">${resObj.subjectname}学科</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span id="sp_up_btn_gradesubject">
                      <a title="编辑" class="ico11" href="javascript:;" onclick="updateSelectColumn('${resObj.resid}','grade|subject','sp_grade|sp_subject','sp_up_btn_gradesubject')"></a>
                  </span>
                      </c:if>
                  </p>
                  <p>资源类型：<span id="sp_restypename">${resObj.restypename}</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span id="sp_up_btn_restype">
                  <a  href="javascript:;" onclick="updateSelectCol('${resObj.resid}','restype','sp_restypename','sp_up_btn_restype','restype')" title="编辑" class="ico11"></a>
                      </span>
                          <c:if test="${!empty resTypeDicList}">
                              <select id="sel_restype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','restype','sel_restype')">
                                  <c:forEach items="${resTypeDicList}" var="ftype">
                                      <c:if test="${ftype.dictionaryvalue==resObj.restype}">
                                          <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                                      </c:if>
                                      <c:if test="${ftype.dictionaryvalue!=resObj.restype}">
                                          <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                                      </c:if>
                                  </c:forEach>
                              </select>
                          </c:if>
                      </c:if>
                  </p>
                  <p>文件类型：<span id="sp_filetypename">${resObj.filetypename}</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span id="sp_up_btn_filetype">
                     <!-- <a href="javascript:;" onclick="updateSelectCol('${resObj.resid}','filetype','sp_filetypename','sp_up_btn_filetype','filetype')" title="编辑" class="ico11"></a>-->
                          </span>
                          <c:if test="${!empty fileTypeList}">
                              <select id="sel_filetype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','filetype','sel_filetype')">
                                  <c:forEach items="${fileTypeList}" var="ftype">
                                      <c:if test="${ftype.dictionaryvalue==resObj.filetype}">
                                          <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                                      </c:if>
                                      <c:if test="${ftype.dictionaryvalue!=resObj.filetype}">
                                          <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                                      </c:if>
                                  </c:forEach>
                              </select>
                          </c:if>
                      </c:if>
                  </p>
                  <p>发布时间：${resObj.ctimeString}</p>
                  <p>发&nbsp;布&nbsp;者：${empty resObj.realname?"北京四中网校":resObj.realname}<a href="resource?m=toteacherreslist&userid=${resObj.userid}" title="more" class="ico_more"></a></p>
                  <p class="info">资源简介
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                          <a title="编辑" class="ico11" href="javascript:;" onclick="updateResColumn('${resObj.resid}','resintroduce','sp_remark','sp_btn_remark',3)"></a>
                      </c:if>
                  </p>
                  <div class="height">
                      <span id="sp_remark">${resObj.resintroduce}</span>
                  </div>
              </div>
              <div class="zyxt_zyxqL">
                  <div id="div_show0">
                  <c:if test="${fileType=='video'}">
                      <!--加载视频-->
                      <c:if test="${!empty resObj.userid&&resObj.userid!=0}">
                          <c:if test="${empty resObj.difftype||resObj.difftype!=1}">
                              <img src="images/video_gszh.jpg" width="578px" height="400px" alt="正在排列,转换"/>
                              <span id="progress_0">排列中</span>
                              <script type="text/javascript">
                                  videoConvertProgress('${resObj.resid}',"001${resObj.filesuffixname}"
                                          ,"001",0,'${resObj.path}/'
                                          ,'<%=fileSystemIpPort%>',resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                                          ,578
                                          ,480
                                  );
                              </script>
                          </c:if>
                      </c:if>
                      <!--微视频-->
                      <c:if test="${!empty resObj.difftype&&resObj.difftype==1}">
                          <script type="text/javascript">
                              loadSWFPlayerLitterView(resourcepathHead+"${resObj.path}/001${resObj.filesuffixname}",'div_show0'
                                      ,resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                                      ,${resObj.resid},578,400,true,undefined);
                          </script>
                      </c:if>
                  </c:if>
                      <c:if test="${fileType=='mp3'}">
                          <script type="text/javascript">
                              loadSWFPlayer(resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}','div_show0','<%=basePath%>images/mp3.jpg',${resObj.resid},578,480);
                          </script>
                      </c:if>
                      <c:if test="${fileType=='swf'}">
                          <script type="text/javascript">
                              swfobjPlayer(resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}','div_show0'
                                      ,578
                                      ,480
                                      ,false,'');
                          </script>
                      </c:if>
                  </div>
                  
              </div>

          </c:if>

          <c:if test="${fileType=='doc'||fileType=='other'}">
     <h1><span  id="sp_name">${resObj.resname}</span>
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span  id="sp_up_btn_resname"><a href="javascript:;" onclick="updateResColumn('${resObj.resid}','resname','sp_name','sp_up_btn_resname')" title="编辑" class="ico11"></a></span>
                  </c:if>
         <c:if test="${fileType=='doc'}">
          <a href="javascript:;" onclick="showDocView(${resObj.resid},resourcepathHead,'${resObj.path}')" title="预览" class="ico76"></a>
         </c:if>
         <a href="javascript:loadCoursePanel()"><span class="ico51" title="发任务"></span></a>
              </h1>
                  <p><span class="w220"><span id="sp_grade">${resObj.gradename}</span>&nbsp;
                      <span id="sp_subject">${resObj.subjectname}学科</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span id="sp_up_btn_gradesubject">
                          <a title="编辑" class="ico11" href="javascript:;" onclick="updateSelectColumn('${resObj.resid}','grade|subject','sp_grade|sp_subject','sp_up_btn_gradesubject')"></a>
                      </span>
                      </c:if>
              </span>
                  资源类型：<span id="sp_restypename">${resObj.restypename}</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span id="sp_up_btn_restype">
                  <a  href="javascript:;" onclick="updateSelectCol('${resObj.resid}','restype','sp_restypename','sp_up_btn_restype','restype')" title="编辑" class="ico11"></a>
                      </span>
                          <c:if test="${!empty resTypeDicList}">
                              <select id="sel_restype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','restype','sel_restype')">
                                  <c:forEach items="${resTypeDicList}" var="ftype">
                                      <c:if test="${ftype.dictionaryvalue==resObj.restype}">
                                          <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                                      </c:if>
                                      <c:if test="${ftype.dictionaryvalue!=resObj.restype}">
                                          <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                                      </c:if>
                                  </c:forEach>
                              </select>
                          </c:if>
                      </c:if>
                  文件类型：<span id="sp_filetypename">${resObj.filetypename}</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span id="sp_up_btn_filetype">
                     <!-- <a href="javascript:;" onclick="updateSelectCol('${resObj.resid}','filetype','sp_filetypename','sp_up_btn_filetype','filetype')" title="编辑" class="ico11"></a>-->
                          </span>
                          <c:if test="${!empty fileTypeList}">
                              <select id="sel_filetype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','filetype','sel_filetype')">
                                  <c:forEach items="${fileTypeList}" var="ftype">
                                      <c:if test="${ftype.dictionaryvalue==resObj.filetype}">
                                          <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                                      </c:if>
                                      <c:if test="${ftype.dictionaryvalue!=resObj.filetype}">
                                          <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                                      </c:if>
                                  </c:forEach>
                              </select>
                          </c:if>
                      </c:if>

                  发布时间：${resObj.ctimeString}&nbsp;&nbsp;&nbsp;&nbsp;
                  发布者：${empty resObj.realname?"北京四中网校":resObj.realname}<a href="resource?m=toteacherreslist&userid=${resObj.userid}" title="more" class="ico_more"></a>
                  <p class="info"><span id="sp_remark">${resObj.resintroduce}</span>
                      <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                          <span><a title="编辑" class="ico11" href="javascript:;" onclick="updateResColumn('${resObj.resid}','resintroduce','sp_remark','sp_btn_remark',3)"></a></span>
                      </c:if>
                  </p>

          </c:if>

          <c:if test="${fileType=='jpeg'}">
       <h1><span  id="sp_name">${resObj.resname}</span>
              <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span  id="sp_up_btn_resname"><a href="javascript:;" onclick="updateResColumn('${resObj.resid}','resname','sp_name','sp_up_btn_resname')" title="编辑" class="ico11"></a></span>
              </c:if>
           <a href="javascript:loadCoursePanel()"><span class="ico51" title="发任务"></span></a>
          </h1>
          <div class="zyxt_zyxqR">
              <p><span id="sp_grade">${resObj.gradename}</span>&nbsp;&nbsp;
                  <span id="sp_subject">${resObj.subjectname}学科</span>
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span id="sp_up_btn_gradesubject">
                      <a title="编辑" class="ico11" href="javascript:;" onclick="updateSelectColumn('${resObj.resid}','grade|subject','sp_grade|sp_subject','sp_up_btn_gradesubject')"></a>
                  </span>
                  </c:if>
              </p>
              <p>资源类型：<span id="sp_restypename">${resObj.restypename}</span>
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <span id="sp_up_btn_restype">
                  <a  href="javascript:;" onclick="updateSelectCol('${resObj.resid}','restype','sp_restypename','sp_up_btn_restype','restype')" title="编辑" class="ico11"></a>
                      </span>
                  <c:if test="${!empty resTypeDicList}">
                      <select id="sel_restype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','restype','sel_restype')">
                          <c:forEach items="${resTypeDicList}" var="ftype">
                              <c:if test="${ftype.dictionaryvalue==resObj.restype}">
                                  <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                              </c:if>
                              <c:if test="${ftype.dictionaryvalue!=resObj.restype}">
                                  <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                              </c:if>
                          </c:forEach>
                      </select>
                  </c:if>
                  </c:if>
                </p>
              <p>文件类型：<span id="sp_filetypename">${resObj.filetypename}</span>
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                      <span id="sp_up_btn_filetype">
                     <!-- <a href="javascript:;" onclick="updateSelectCol('${resObj.resid}','filetype','sp_filetypename','sp_up_btn_filetype','filetype')" title="编辑" class="ico11"></a>-->
                          </span>
                      <c:if test="${!empty fileTypeList}">
                          <select id="sel_filetype" style="display:none" onchange="updateResColumn_Type('${resObj.resid}','filetype','sel_filetype')">
                              <c:forEach items="${fileTypeList}" var="ftype">
                                  <c:if test="${ftype.dictionaryvalue==resObj.filetype}">
                                      <option value="${ftype.dictionaryvalue}" selected>${ftype.dictionaryname}</option>
                                  </c:if>
                                  <c:if test="${ftype.dictionaryvalue!=resObj.filetype}">
                                      <option value="${ftype.dictionaryvalue}">${ftype.dictionaryname}</option>
                                  </c:if>
                              </c:forEach>
                          </select>
                      </c:if>
                  </c:if>
              </p>
              <p>发布时间：${resObj.ctimeString}</p>
              <p>发&nbsp;布&nbsp;者：${empty resObj.realname?"北京四中网校":resObj.realname}<a href="resource?m=toteacherreslist&userid=${resObj.userid}" title="more" class="ico_more"></a></p>
              <p class="info">资源简介
                  <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <a title="编辑" class="ico11" href="javascript:;" onclick="updateResColumn('${resObj.resid}','resintroduce','sp_remark','sp_btn_remark',3)"></a>
                  </c:if>
              </p>
              <div class="height wrapline">
                      <span id="sp_remark">${resObj.resintroduce}</span>
              </div>
          </div>
          <div class="zyxt_zyxqL">
              <img src="resource?m=getResourceImage&resid=${resObj.resid}&w=578&h=480" alt="${resObj.resname}" id="img_show"/>
          </div>
            </c:if>


          <p class="f_right">
              <%/*PZYXT-50
              举报，限制是对本校、其他教师发布、且未分享到云端的资源可进行举报操作，只能举报一次
              ，举报完成之后，显示文字"已举报"。当前对自己的资源也能举报，对其他学校的资源也能举报，对分享到云端的资源也能举报。
              */%>
              <c:if test="${resObj.sharestatus==1&&resObj.userid!=sessionScope.CURRENT_USER.userid}">
                  <c:if test="${isreport==2}"><!--未举报-->
                      <a href="javascript:;" onclick="showModel('resource_report');" class="ico74" title="我要举报"></a>
                  </c:if>
                  <c:if test="${isreport==1}"><!--未举报-->
                      <a href="javascript:;" class="ico74" title="已举报"></a>
                  </c:if>
              </c:if>


              <!--<a href="1" class="ico75" title="我要纠错"></a>-->
              <c:if test="${sessionScope.CURRENT_USER.userid==resObj.userid}">
                  <c:if test="${!empty resObj.resdegree&&resObj.resdegree==3}">
                    <a href="javascript:;" onclick="doDelRes(${resObj.resid})" class="ico04" title="删除"></a>
                  </c:if>
              </c:if>
          </p>
          <p class="ico">
              <c:if test="${ispraise==2}">
		             <span id="praise_status">
		                <a href="javascript:;" onclick="operatePraise('${resObj.resid }')"><span class="ico41" title="赞"></span><span>${resObj.praisenum}</span></a>
                    </span>
              </c:if>
              <c:if test="${ispraise==1}">
              <a style="cursor: default"><span class="ico41" title="已赞"></span>${resObj.praisenum}</a>
              </c:if>
              <c:if test="${isrecomend==2}">
		         <span id="recomend_status">
		         <a href="javascript:;" onclick="operateRecommend('${resObj.resid }')"><span class="ico73" title="推荐"></span><span>${resObj.recomendnum}</span></a>
                 </span>
              </c:if>
              <c:if test="${isrecomend==1}">
                  <a style="cursor: default"><span class="ico73" title="已推荐"></span>${resObj.recomendnum}</a>
              </c:if>
            <span id="dv_store">
		    <c:if test="${isstore==2}">
                <a href="javascript:;" onClick="operateStore(1,'${resObj.resid }','dv_store')"><span class="ico58a" title="收藏"></span><span>${resObj.storenum}</span></a>
            </c:if>
		     <c:if test="${isstore==1}">
                 <a href="javascript:;" onClick="operateStore(2,'${resObj.resid }','dv_store')"><span class="ico58b" title="取消收藏"></span><span>${resObj.storenum}</span></a>
             </c:if>
		    </span>
              <!--微视频-->
              <c:if test="${empty resObj.difftype||resObj.difftype!=1}">
                  <a href="javascript:;" onclick="resourceDownLoadFile('${resObj.resid }','${resObj.filesuffixname}');sp_downnum.innerHTML=parseInt(sp_downnum.innerHTML)+1"><span class="ico59" title="下载"></span><span id="sp_downnum">${resObj.downloadnum}</span></a>
              </c:if>

              <span class="ico46" title="浏览"></span><span>${resObj.clicks}</span></p>

          <div class="subpage_lm">
              <ul id="ul_xuanxiang">
                  <li value="2"><a href="javascript:;" onclick="fn_changePanel(2)">相关视频</a></li>
                  <li value="3"><a href="javascript:;" onclick="fn_changePanel(3)">相关音频</a></li>
                  <li value="5"><a href="javascript:;" onclick="fn_changePanel(5)">相关动画</a></li>
                  <li value="1"><a href="javascript:;" onclick="fn_changePanel(1)">相关文档</a></li>
                  <li value="4"><a href="javascript:;" onclick="fn_changePanel(4)">相关图片</a></li>
              </ul>
          </div>

          <div class="zyxt_zyxq_xgzr font-black">
              <p class="left"><a  href="javascript:;" onclick="pagePre('p2')"><span class="an_left" title="左移"></span></a></p>
              <div id="dv_tuijian_res"></div>
                <form id="page2form" name="page2form" method="post">
                <div id="page2address" style="display:none"></div>
                </form>
              <p class="right"><a href="javascript:;" onclick="pageNext('p2')"><span class="an_right" title="右移"></span></a></p>
          </div>
    </div>

      <div class="zyxt_zyxq_pl public_input">
          <input type="hidden" id="commenttype" value="${commenttype }" />
          <input type="hidden" id="scoretype" value="${scoretype }" />
          <input type="hidden" id="objectid" value="${resObj.resid }" />
          <ul class="one">
              <li><strong>评论</strong>(<span id="sp_commentnum">${resObj.commentnum}</span>)</li>
          </ul>
          <div class="fabiao">
              <textarea name="textarea" id="commentcontext" name="commentcontext" ></textarea>
              <p class="t_r">最多可以输入200个字!&nbsp;&nbsp;&nbsp;&nbsp;
                  <input name="input" type="checkbox"  value="1" id="anonymous" name="anonymous">
                  <span class="font-black">匿名</span>&nbsp;&nbsp;<a href="javascript:;" onclick="addResourceComment();" class="an_small">发&nbsp;表</a></p>
          </div>
          <ul class="two" id="commentList">

          </ul>
          <form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
              <div id="page1address" class="nextpage">
              </div>
          </form>
      </div>

  </div>
  <div style="display: none">
      <select id="sel_gd_hd">
      </select>
      <script type="text/javascript">
          <c:if test="${!empty gradeList}">
          <c:forEach items="${gradeList}" var="gd">
            var h='<option value="${gd.gradeid}">${gd.gradename}</option>';
            if($("#sel_gd_hd option[value='${gd.gradeid}']").length<1){
                  $("#sel_gd_hd").append(h);
            }
          </c:forEach>
          </c:if>
      </script>
      <select id="sel_sb_hd">
      </select>
      <script type="text/javascript">
          <c:if test="${!empty subList}">
          <c:forEach items="${subList}" var="sb">
          var h='<option value="${sb.subjectid}">${sb.subjectname}</option>';
          if($("#sel_sb_hd option[value='${sb.subjectid}']").length<1){
              $("#sel_sb_hd").append(h);
          }
          </c:forEach>
          </c:if>
      </script>
  </div>
  <%@include file="/util/foot.jsp" %>

  <div style="display:none;background: #FFFFFF" id="resource_report">
      <p class="f_right"><a href="javascript:closeModel('resource_report');" title="关闭"><span class="public_windows_close"></span></a></p>
      <br/>
      <h3 align="center">举报资源</h3><br/>
      <table border="0" cellpadding="0" cellspacing="0" >
          <col class="w100" />
          <col class="w400" />
          <tr>
              <td align="right" valign="top">举报原因：</td>
              <td><textarea id="report_content" name="content" type="text" cols="50" rows="5"></textarea></td>
          </tr>
          <tr>
              <td>&nbsp;</td>
                <td><a id="addReportBtn" class="an_small"  href="javascript:;" onclick="resourceReport('${resObj.resid }');">提交</a>
                    <a class="an_small"  href="javascript:;" onclick="closeModel('resource_report');sel_filetype.style.display='none';sel_restype.style.display='none';">取消</a></td>
          </tr>
      </table>
      <br/>
  </div>
    <!--doc预览-->
  <div class="public_windows" style="width:1000px;height:800px;display:none"   id="dv_doc_prview">
      <h3 style="float:right;"><a href="javascript:;" onclick="document.getElementById('fade').style.display='none';dv_doc_prview.style.display='none';"  title="关闭"></a></h3>
      <div class="zyxt_float_fxzy public_input" id="dv_show_doc_view">

      </div>
  </div>
  <!--遮照层-->
  <div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
  <div id="loading" style='display:none;position: absolute;z-index:1005;'><img src="images/loading.gif"/></div>
  </body>
</html>