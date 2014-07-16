
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
<script type="text/javascript"
        src="<%=basePath %>js/teachpaltform/tptask.js"></script>
		<script type="text/javascript">
	var courseid = "${courseid}";
	var termid = "${termid}";
    var pList;
    var ueditorArray=new Array();
    var ueditorObjArray=[];
	$(function() {
        pList = new PageControl( {
            post_url : 'task?m=ajaxStuTaskList',
            page_id : 'page1',
            page_control_name : "pList",
            post_form : document.pListForm,
            gender_address_id : 'pListaddress',
            http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
            http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
            return_type : 'json', //放回的值类型
            page_no : 1, //当前的页数
            page_size : 5, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "initItemList"
        });
        pageGo('pList');

	});


    function doUpdStuNote(ref,taskid) {
        if (typeof  ref == 'undefined'||typeof  taskid == 'undefined')
            return;

        var tmp=null;
        if(ueditorArray.length>0){
            $.each(ueditorArray,function(idx,itm){
                if(itm==taskid){
                    tmp=ueditorObjArray[idx];return;
                }
            });
        }
        var obj=tmp.getContent();
        if (obj.Trim().length < 1) {
            alert('请输入学习心得后提交!');
            return;
        }


        $.ajax({
                    url: "task?doUpdStudyNotes",
                    dataType: 'json',
                    type: "post",
                    cache: false,
                    data: {
                        ref: ref,
                        content: obj
                    },
                    error: function () {
                        alert('系统未响应!');
                    },
                    success: function (rps) {
                        if (rps.type == "error") {
                            alert(rps.msg);
                        } else {
                            pageGo('pList');
                        }
                    }
                });
    }



    /**
     * 加载学习心得编辑框
     * href="javascript:doStuSubmitQues('+itm.tasktype+','+itm.taskid+','+itm.taskvalueid+',\'\','+itm.questiontype+')"
     * @return
     */
    function genderStuNoteUpdText(ref,tasktype,taskid,taskvalueid) {
        if (typeof (tasktype) == 'undefined'||typeof (taskid) == 'undefined'
                ||typeof (taskvalueid) == 'undefined')
            return;
        var spObj=$('#sp_note_'+taskid+'');
        var content=$('#sp_note_'+taskid+' > font').html();
        var queshtm='<tr>';
        queshtm+='<td><strong>学习心得：</strong></td>';
        queshtm+='<td><textarea  id="txt_taskanswer_'+taskid+'">'+content+'</textarea></td>';
        queshtm+='</tr>';
        queshtm+='<tr>';
        queshtm+='<td>&nbsp;</td>';
        queshtm+='<td><a class="an_public3" href="javascript:doUpdStuNote('+ref+','+taskid+')">修&nbsp;改</a></td>';
        queshtm+='</tr>';

        $("#tbl_"+taskid).append(queshtm);
        $(spObj).parent().remove();
        ueditorArray.push(taskid);

        var id=taskid;
        id= new UE.ui.Editor({
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars: [
                //['emotion','attachment','superscript','subscript','fullscreen','insertimage']
                ['emotion','attachment','fullscreen']
            ],
            initialFrameWidth: "600px",
            initialFrameHeight: "90px",
            autoHeightEnabled: false
        });
        textarea:'txt_taskanswer_'+taskid+''; //与textarea的name值保持一致
        id.setDataId(taskid);
        id.render('txt_taskanswer_'+taskid+'');
        ueditorObjArray.push(id);

    }




    function getInvestReturnMethod(rps){
        var html='';
        if(rps.objList!=null&&rps.objList.length>0){
            $.each(rps.objList,function(idx,itm){
                var type="",criteria,questype='',optionhtm='',answerhtm='',answertype='',queshtm='';
                switch (itm.tasktype){
                    case 1:
                        criteria=itm.criteria==1?"查看":"提交心得";
                        type="资源学习&nbsp;&nbsp;&nbsp;&nbsp;";
                        answertype='学习心得';
                        break;
                    case 2:
                        criteria=itm.criteria==1?"查看":"发主帖";
                        type="互动交流&nbsp;&nbsp;&nbsp;&nbsp;";
                        answertype='您的互动';
                        break;
                    case 3:
                        criteria=itm.criteria==1?"提交":"";
                        type="试&nbsp;&nbsp;&nbsp;&nbsp;题";
                        answertype='您的答案';
                        switch (itm.questiontype){
                            case 1:questype="问&nbsp;&nbsp;&nbsp;&nbsp;答：";break;
                            case 2:questype="填&nbsp;&nbsp;&nbsp;&nbsp;空：";break;
                            case 3:questype="单&nbsp;&nbsp;&nbsp;&nbsp;选：";break;
                            case 4:questype="多&nbsp;&nbsp;&nbsp;&nbsp;选：";break;
                        }
                        break;
                    case 4:
                        criteria=itm.criteria==1?"提交试卷":"";
                        type="成卷测试&nbsp;&nbsp;&nbsp;&nbsp;";
                        //answertype='您的互动';
                        break;
                    case 5:
                        criteria=itm.criteria==1?"提交试卷":"";
                        type="自主测试&nbsp;&nbsp;&nbsp;&nbsp;";
                        //answertype='您的互动';
                        break;
                }

                if(itm.questionAnswerList!=null&&itm.questionAnswerList.length>0){
                    if(itm.tasktype==3){
                        var right='',analysis='';
                        var stuanswer='';
                        $.each(itm.questionAnswerList,function(ix,im){stuanswer+=im.answercontent+"&nbsp;"});

                        answerhtm+='<p><strong>您的答案：</strong><span class="width">'+ stuanswer +'</span></p>';
                        if(itm.questiontype==3||itm.questiontype==4){
                            if(itm.rightOptionAnswerList!=null&&itm.rightOptionAnswerList.length>0){
                                right+='<span class="font-red">';
                                $.each(itm.rightOptionAnswerList,function(ix,im){
                                    right+=im.optiontype+"&nbsp;";
                                });
                                right+='</span>';
                            }
                        }else{
                            if(itm.rightAnswerList!=null&&itm.rightAnswerList.length>0){
                                $.each(itm.rightAnswerList,function(ix,im){
                                    right+=im.correctanswer;
                                });
                            }
                        }
                        if(itm.questiontype==1)
                            answerhtm+='<p><strong>学习时间：</strong><span class="width">'+itm.questionAnswerList[0].ctimeString+'</span></p>';
                        else{
                            answerhtm+='<p><strong>正确答案：</strong><span class="width">'+right+'</span></p>';
                        }
                        //answerhtm+='<p><strong>答案解析：</strong><span class="width">'+(typeof(itm.rightAnswerList[0].analysis)=='undefined'?"":replaceAll(replaceAll(itm.rightAnswerList[0].analysis.toLowerCase(),"<p>",""),"</p>",""))+'</span></p>';
                        answerhtm+='<p><strong>答案解析：</strong><span class="width">'+(typeof(itm.rightAnswerList[0].analysis)=='undefined'?"":itm.rightAnswerList[0].analysis)+'</span></p>';
                    }else if(itm.tasktype==1){
                        answerhtm+='<p><strong>学习时间：</strong><span class="width">'+itm.taskPerformanceList[0].ctimeString+'</span></p>';
                        answerhtm+='<p><strong>学习心得：</strong><span class="width"  id="sp_note_'+itm.taskid+'"><font>'+itm.questionAnswerList[0].answercontent+'</font>';
                        if(itm.taskstatus!="1" && itm.taskstatus!="3"){
                            answerhtm+='<a class="ico11" title="编辑" href="javascript:genderStuNoteUpdText('+itm.questionAnswerList[0].ref+','+itm.tasktype+','+itm.taskid+','+itm.taskvalueid+')"></a>';
                        }
                        answerhtm+='</span></p>';
                    }
                }else{
                    if(itm.tasktype==3){
                        if(itm.questiontype!='undefined'&&itm.questiontype==1){
                            queshtm+='<tr>';
                            queshtm+='<td>&nbsp;</td>';
                            queshtm+='<td><textarea  id="txt_taskanswer_'+itm.taskvalueid+'"></textarea></td>';
                            queshtm+='</tr>';
                        }
                        if(itm.taskstatus!="1" && itm.taskstatus!="3"){
                            queshtm+='<tr>';
                            queshtm+='<td>&nbsp;</td>';
                            queshtm+='<td><a class="an_public3" href="javascript:doStuSubmitQues('+itm.tasktype+','+itm.taskid+','+itm.taskvalueid+',\'\','+itm.questiontype+')">保&nbsp;存</a></td>';
                            queshtm+='</tr>';
                        }
                    }else if(itm.tasktype==1&&itm.taskPerformanceList[0]!=null){
                        answerhtm+='<p><strong>学习时间：</strong><span class="width">'+itm.taskPerformanceList[0].ctimeString+'</span></p>';
                    }else if(itm.tasktype==1&&itm.taskstatus!="1" && itm.taskstatus!="3"&&itm.criteria==2){
                        queshtm+='<tr>';
                        queshtm+='<td><strong>学习心得：</strong></td>';
                        queshtm+='<td><textarea  id="txt_taskanswer_'+itm.taskid+'"></textarea></td>';
                        queshtm+='</tr>';
                        queshtm+='<tr>';
                        queshtm+='<td>&nbsp;</td>';
                        queshtm+='<td><a class="an_public3" href="javascript:doStuSubmitQues('+itm.tasktype+','+itm.taskid+','+itm.taskvalueid+',\'\','+itm.questiontype+')">保&nbsp;存</a></td>';
                        queshtm+='</tr>';
                        ueditorArray.push(itm.taskid);
                    }else if(itm.tasktype==2&&itm.taskPerformanceList[0]!=null){
                        answerhtm+='<p><strong>学习时间：</strong><span class="width">'+itm.taskPerformanceList[0].ctimeString+'</span></p>';
                        if(itm.tpTopicThemeInfoList!=null){
                            answerhtm+='<p>';
                            $.each(itm.tpTopicThemeInfoList,function(ix,im){
                                answerhtm+='<a href="tptopictheme?m=toTopicThemeDetail&themeid='+im.themeid+'">'+im.themetitle+'</a>';
                            });
                            answerhtm+='</p>';
                        }
                    }
                }

                /*问题选项*/
                if(itm.questionOptionList!=null&&itm.questionOptionList.length>0){
                    $.each(itm.questionOptionList,function(idx,im){
                        var type=itm.questiontype==3?"radio":"checkbox";
                        optionhtm+='<input type="'+type+'" value="'+im.ref+'" name="ques_option_'+im.questionid+'"/>';
                        optionhtm+=im.optiontype+".&nbsp;";
                        //optionhtm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                        optionhtm+=im.content;
                        optionhtm+='<br>';
                    });
                }

                html+='<div class="jxxt_zhuanti_rw">';
                html+='        <div class="jxxt_zhuanti_rwR">';
                html+='        <div class="title">';
                html+='        <p class="f_right"><span class="ico35" style="cursor:pointer" onclick="loadNoCompleteStu(\''+itm.taskid+'\',\''+itm.usertypeid+'\',\''+itm.taskstatus+'\')"></span><b><a   class="font-red">'+itm.stucount+'/'+itm.totalcount+'</a></b></p>';
                html+='        <p><a class="ico49b" id="a_show_'+itm.taskid+'" href="javascript:void(0);" onclick="showOrhide(this,\''+itm.taskid+'\')"></a><a href="javascript:void(0);" onclick="$(this).prev().click();">任务'+(rps.presult.pageSize*(rps.presult.pageNo-1)+(idx+1))+'：'+type+'</a>';
                if(itm.tasktype==1){
                    if(typeof itm.resourcetype=='undeinfed'|| itm.resourcetype==1)
                        html+='<a  class="font-blue" onclick="toPostURL(\'task?doAddResViewRecord\',{courseid:'+itm.courseid+',taskid:'+itm.taskid+',tasktype:'+itm.tasktype+',groupid:\'\',tpresdetailid:'+itm.taskvalueid+'},false,null)" href="javascript:void(0);" style="color: blue;">'+itm.taskobjname+'</a>';
                    else{
                        if(typeof itm.remotetype!='undefined'){
                            var paramStr=itm.remotetype==1?"hd_res_id":"res_id";
                            html+='<a href="tpres?m=toRemoteResourcesDetail&'+paramStr+'='+itm.taskvalueid+'" class="font-blue">'+itm.taskobjname+'</a>';
                        }
                    }
                }else if(itm.tasktype==2){
                    html+='<a  class="font-blue" onclick="toPostURL(\'task?doAddViewRecord\',{courseid:'+itm.courseid+',taskid:'+itm.taskid+',tasktype:'+itm.tasktype+',groupid:\'\',themeid:'+itm.taskvalueid+'},false,null)" href="javascript:void(0);" style="color: blue;">'+itm.taskobjname+'</a>';
                }else if(itm.tasktype==4&&itm.taskstatus!="1"){
                    html+='<a  class="font-blue" href="paperques?m=testPaper&paperid='+itm.taskvalueid+'&courseid='+itm.courseid+'&taskid='+itm.taskid+'" style="color: blue;">'+itm.taskobjname+'</a>';
                }else if(itm.tasktype==5&&itm.taskstatus!="1"){
                    html+='<a  class="font-blue" href="paper?m=genderZiZhuPaper&taskid='+itm.taskid+'"  style="color: blue;">'+itm.taskobjname+'</a>';
                }
                if(typeof itm.iscomplete!='undefined'&&itm.iscomplete>0)
                    html+='&nbsp;&nbsp;&nbsp;<img src="css/images/an06_131126.png" width="49" height="21" align="absmiddle">';
                html+='</p>';
                html+='</div>';
                html+='<div class="text" id="div_task_'+itm.taskid+'" style="display:none;">';
                html+='        <p class="f_right"><a href="javascript:void(0);" onclick="showModel(\'div_suggest_'+itm.taskid+'\')" class="font-darkblue">提建议</a></p>';
                html+='        <p><strong>完成标准：</strong><span class="font-black">'+criteria+'</span></p>';
                html+='        <p><strong>任务描述：</strong><span class="width">'+(typeof itm.taskremark !='undefined'?itm.taskremark:"")+'</span></p>';
                if(itm.taskstatus=="1")
                    html+='<p class="font-black"><span class="ico33"></span>任务尚未开始，您的作答将不计入任务的统计结果中，请在任务开始之后重新作答！</p>';
                html+='<table border="0" cellspacing="0" cellpadding="0" class="black" id="tbl_'+itm.taskid+'">';
                if(itm.tasktype==3){
                    html+='        <col class="w50"/>';
                    html+='        <col class="w750"/>';
                }else{
                    html+='        <col class="w70"/>';
                    html+='        <col class="w730"/>';
                }
                html+='        <tr>';
                html+='        <td><strong>'+questype+'</strong></td>';
                html+='        <td>';
                if(itm.tasktype==3){
                    var objname=replaceAll(itm.taskobjname.toLowerCase(),'<span name="fillbank"></span>','<input  type="text" name="txt_fb_'+itm.taskvalueid+'" />');
                    //html+=replaceAll(replaceAll(replaceAll(objname.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                    html+=objname;
                    html+='<br>';
                }
                html+=optionhtm;
                html+='</td>';
                html+='</tr>';
                html+=queshtm;
                html+='</table>';
                html+=answerhtm;
                html+='</div>';
                html+='</div>';

                html+='<div class="jxxt_zhuanti_rwL">';
                if(itm.taskstatus=="3"){
                    html+='<p><span class="ico_time"></span>任务已结束</p>';
                }else if(itm.taskstatus=="1"){
                    html+='<p class="blue"><span class="ico_time"></span>任务未开始</p>';
                }else{
                    html+='<p class="green"><span class="ico_time"></span>'+itm.taskstatus+'</p>';
                    if(itm.taskPerformanceList[0]==null)
                        html+='<input type="hidden" name="hd_task_status" value="'+itm.taskid+'"/>';
                }
                html+='</div>';
                html+='<div class="clear"></div>';
                html+='</div>';
                //建议
                html+='<div class="public_windows"  style="display:none;" id="div_suggest_'+itm.taskid+'">';
                html+='        <h3><a href="javascript:closeModel(\'div_suggest_'+itm.taskid+'\')"  title="关闭"></a>提建议</h3>';
                html+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">';
                html+='        <tr>';
                html+='        <td><textarea id="txt_suggest_'+itm.taskid+'"  class="h90 w350"></textarea></td>';
                html+='        </tr>';
                html+='        <tr>';
                html+='        <td><input type="checkbox"  id="ck_anonmous_'+itm.taskid+'" value="1">';
                html+='        <span class="font-black">匿名</span>（提的建议匿名显示）</td>';
                html+='</tr>';
                html+='        <tr>';
                html+='<td class="t_c"><a href="javascript:void(0);" onclick="doSubSuggest('+itm.taskid+')" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeModel(\'div_suggest_'+itm.taskid+'\')" class="an_public1">取&nbsp;消</a></td>';
                html+='</tr>';
                html+='</table>';
                html+='</div>';

            });
        }else{
            html='<p>暂无数据!</p>';
        }
        $("#initItemList").html(html);

        if($("input[name='hd_task_status']").length>0){
            var taskid=$("input[name='hd_task_status']").get(0).value;
            $('a[id="a_show_'+taskid+'"]').click();
        }


        if(ueditorArray.length>0){
            for(var i=0;i<ueditorArray.length;i++){
                var id=ueditorArray[i];
                id= new UE.ui.Editor({
                    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
                    toolbars: [
                        //['emotion','attachment','superscript','subscript','fullscreen','insertimage']
                        ['emotion','attachment','fullscreen']
                    ],
                     initialFrameWidth: "600px",
                     initialFrameHeight: "90px",
                    autoHeightEnabled: false
                });
                textarea:'txt_taskanswer_'+ueditorArray[i]+''; //与textarea的name值保持一致
                id.setDataId(ueditorArray[i]);
                id.render('txt_taskanswer_'+ueditorArray[i]+'');
                ueditorObjArray.push(id);
            }
        }




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

    function preeDoPageSub(pObj){
        if(typeof(pObj)!='object'){
            alert("异常错误，请刷新页面重试!");
            return;
        }
        if(courseid.length<1){
            alert('异常错误，系统未获取到专题标识!');
            return;
        }
        var param={courseid:courseid};
        pObj.setPostParams(param);
    }

    function showOrhide(aobj, taskid) {
        var status=$("#div_task_"+taskid);
        if(status.css("display")=="none"){
            $(status).show();
            $(aobj).removeClass().addClass("ico49a");
        }else{
            $(status).hide();
            $(aobj).removeClass().addClass("ico49b");
        }
    }

	function msgByType(tasktype){
		if(!tasktype)
			return '';
		switch(tasktype){
			case 1: return "请学习资源中的";
			case 2: return "请学习互动交流中的";
			case 3: return "请完成以下作业";
			default: return '';
		}
	}
	function showCourseList(){
		var ulobj=$("#ul_courselist");
		if(ulobj.css("display")=="none")
			ulobj.show();
		else
			ulobj.hide();	  
	}


	
</script>


	</head>
	<body>
    <%@include file="/util/head.jsp" %>
    <%@include file="/util/nav-base.jsp" %>
    <div class="zhuanti">
        <p>${coursename }<a class="ico13" href="javascript:showCourseList();"></a></p>
        <ul  style="display:none;" id="ul_courselist">
            <c:if test="${!empty courseList}">
                <c:forEach items="${courseList}" var="c">
                    <c:if test="${c.courseid!=courseid}">
                        <li>
                            <a href="task?toStuTaskIndex&courseid=${c.courseid }&termid=${termid}">${c.coursename }</a>
                        </li>
                    </c:if>
                </c:forEach>
            </c:if>
        </ul>
    </div>


    <div class="subpage_nav">
        <ul>
            <li class="crumb">
                <a>学习任务</a>
            </li>
            <li>
                <a href="tpres?toStudentIdx&courseid=${courseid }">专题资源</a>
            </li>
            <li>
                <a href="tptopic?m=index&courseid=${courseid }">互动空间</a>
            </li>
        </ul>
    </div>


    <div class="content2">
        <div class="jxxt_zhuanti" id="initItemList">
            <div class="jxxt_zhuanti_rw">
                <div class="jxxt_zhuanti_rwR">
                    <div class="title">
                        <p class="f_right"><span class="ico35"></span><b><a href="1" class="font-red">40/50</a></b></p>
                        <p><a class="ico49b"></a>任务8：试&nbsp;&nbsp;&nbsp;&nbsp;题</p>
                    </div>
                    <div class="text">
                        <p class="f_right"><a href="1" target="_blank" class="font-darkblue">提建议</a></p>
                        <p><strong>完成标准：</strong><span class="font-black">学习心得</span></p>
                        <p><strong>任务描述：</strong><span class="width">高一1班第一组、第二组 ； 高一2班</span></p>
                        <table border="0" cellspacing="0" cellpadding="0" class="black">
                            <col class="w50"/>
                            <col class="w750"/>
                            <tr>
                                <td><strong>单&nbsp;&nbsp;&nbsp;&nbsp;选：</strong></td>
                                <td>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国______的批判<br>
                                    <input type="radio" name="radio" id="radio" value="radio">
                                    A. 民族性<br>
                                    <input type="radio" name="radio" id="radio6" value="radio">
                                    B. 国民性<br>
                                    <input type="radio" name="radio" id="radio7" value="radio">
                                    C. 传统性<br>
                                    <input type="radio" name="radio" id="radio8" value="radio">
                                    D. 文化性</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a href="1" class="an_public3">保&nbsp;存</a></td>
                            </tr>
                        </table>
                        <p><strong>学习时间：</strong><span class="width">2014-03-25 13:40:00</span></p>
                        <p><strong>您的答案：</strong><span class="width"><span class="font-red">B</span></span></p>
                        <p><strong>正确答案：</strong><span class="width">A</span></p>
                        <p><strong>答案解晰：</strong><span class="width">《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国旧中国新世界好莱坞学龄前。</span></p>
                    </div>
                </div>
                <div class="jxxt_zhuanti_rwL"><p><span class="ico_time"></span>任务已结束</p></div>
                <div class="clear"></div>
            </div>

            <div class="jxxt_zhuanti_rw">
                <div class="jxxt_zhuanti_rwR">
                    <div class="title">
                        <p class="f_right"><span class="ico35"></span><b><a href="1" class="font-red">40/50</a></b></p>
                        <p><a class="ico49b"></a>任务7：资源学习&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-blue">请学习学习资源中的 《鸿门宴》话剧.flv</a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="../images/an06_131126.png" width="49" height="21" align="absmiddle"></p>
                    </div>
                    <div class="text">
                        <p class="f_right"><a href="1" target="_blank" class="font-darkblue">提建议</a></p>
                        <p><strong>完成标准：</strong><span class="font-black">学习心得</span></p>
                        <p><strong>学习时间：</strong><span class="width">2014-03-25 13:40:00</span></p>
                        <p><strong>学习心得：</strong><span class="width">《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国旧中国新世界好莱坞学龄前。</span></p>
                    </div>
                </div>
                <div class="jxxt_zhuanti_rwL"><p class="blue"><span class="ico_time"></span>任务未开始</p></div>
                <div class="clear"></div>
            </div>

            <div class="jxxt_zhuanti_rw">
                <div class="jxxt_zhuanti_rwR">
                    <div class="title">
                        <p class="f_right"><span class="ico35"></span><b><a href="1" class="font-red">40/50</a></b></p>
                        <p><a class="ico49a"></a>任务6：资源学习&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-blue">请学习学习资源中的 《鸿门宴》话剧.flv</a></p>
                    </div>
                    <div class="text">
                        <p class="f_right"><a href="1" target="_blank" class="font-darkblue">提建议</a></p>
                        <p><strong>完成标准：</strong><span class="font-black">学习心得</span></p>
                        <p><strong>任务描述：</strong><span class="width">高一1班第一组、第二组 ； 高一2班</span></p>
                        <table border="0" cellspacing="0" cellpadding="0" class="black">
                            <col class="w50"/>
                            <col class="w750"/>
                            <tr>
                                <td><strong>填&nbsp;&nbsp;&nbsp;&nbsp;空：</strong></td>
                                <td>《失踪》、《孔乙己》、《药》等<input name="textfield4" type="text" />有一个共同的主题，<input name="textfield4" type="text" />《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a href="1" class="an_public3">保&nbsp;存</a></td>
                            </tr>
                        </table>
                        <p><strong>学习时间：</strong><span class="width">2014-03-25 13:40:00</span></p>
                        <p><strong>您的答案：</strong><span class="width"><span class="font-red">B</span></span></p>
                        <p><strong>正确答案：</strong><span class="width">A</span></p>
                        <p><strong>答案解晰：</strong><span class="width">《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国旧中国新世界好莱坞学龄前。</span></p>
                    </div>
                </div>
                <div class="jxxt_zhuanti_rwL"><p class="green"><span class="ico_time"></span>300:15:34</p></div>
                <div class="clear"></div>
            </div>
            <div class="jxxt_zhuanti_rw">
                <div class="jxxt_zhuanti_rwR">
                    <div class="title">
                        <p class="f_right"><span class="ico35"></span><b><a href="1" class="font-red">40/50</a></b></p>
                        <p><a class="ico49a"></a>任务5：试&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-blue">请学习学习资源中的 《鸿门宴》话剧.flv</a></p>
                    </div>
                    <div class="text">
                        <p class="f_right"><a href="1" target="_blank" class="font-darkblue">提建议</a></p>
                        <p><strong>完成标准：</strong><span class="font-black">学习心得</span></p>
                        <p><strong>任务描述：</strong><span class="width">高一1班第一组、第二组 ； 高一2班</span></p>
                        <table border="0" cellspacing="0" cellpadding="0" class="black">
                            <col class="w50"/>
                            <col class="w750"/>
                            <tr>
                                <td><strong>问&nbsp;&nbsp;&nbsp;&nbsp;答：</strong></td>
                                <td>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批判条件是什么样的？<br></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><textarea name="textarea2"></textarea></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a href="1" class="an_public3">保&nbsp;存</a></td>
                            </tr>
                        </table>
                        <p><strong>学习时间：</strong><span class="width">2014-03-25 13:40:00</span></p>
                        <p><strong>您的答案：</strong><span class="width">《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国旧中国新世界好莱坞学龄前。</span></p>
                        <p><strong>答案解晰：</strong><span class="width">《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国旧中国新世界好莱坞学龄前。</span></p>
                    </div>
                </div>
                <div class="jxxt_zhuanti_rwL">
                    <p class="red"><span class="ico_time"></span>300:15:34</p>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>

    <div class="public_windows" id="dv_nocomplete" style="display: none;">
        <h3><a href="javascript:void(0);" onclick="closeModel('dv_nocomplete')"  title="关闭"></a>未完成任务人员</h3>
        <div class="jxxt_student_rw_float" id="dv_nocomplete_data">

        </div>
    </div>

    <%@include file="/util/foot.jsp" %>
		<div id="fade" class="black_overlay"
			style="background: black; filter: alpha(opacity =         50); opacity: 0.5; -moz-opacity: 0.5;"></div>
</body>

</html>
