<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/util/common.jsp" %> 
<%@ page import="com.school.entity.TeacherInfo"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数字化校园</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
<script type="text/javascript">
	var hdtitlelen=0;
	
	
	function doSubAppraTeacher(){
		var userid=$("#userid").val();
		var yearid=$("#yearid").val();
		var targetclassuserref=$("#targetclassuserref").val();
		if(targetclassuserref.Trim().length<1){
			alert('异常错误，系统尚未获取到您要评价的老师的关系标识，请刷新页面后重试!');
			return;
		}	
		var targetuserref=$("#targetuserref").val();
		if(targetuserref.Trim().length<1){
			alert('异常错误，系统尚未获取到您要评价的老师信息，请刷新页面选择后重试!');
			return;
		}
		var classid=$("#classid").val();
		if(classid.Trim().length<1){
			alert('异常错误，系统尚未获取到您所在的班级信息，请刷新页面后重试!');
			return;
		}
		
		var answerRdoArray=$("input[type='radio']").filter(function(){
				return this.id.indexOf('_r_answer_')!=-1
						&&this.checked==true}
		);
		if(answerRdoArray.length!=hdtitlelen){
			alert('您尚未有问题进行回答，请全部回答完毕后，点击确定!');
			return;
		}	
		if(!confirm("您确定对该教职员工进行提交吗？\n\n提示：评价后不可修改，删除!"))
			return;
		//组织数据
		var answerOptions='&t=1';
		$.each(answerRdoArray,function(idx,itm){
			answerOptions+="&answeroption="+itm.value.Trim();
		});
		//进行提交
		$.ajax({
		 	 url:"teacherappraise?m=addappraiselosg"+answerOptions,
			type:"POST",			
			dataType:'json',
			data:{
				  targetclassuserref:targetclassuserref,
				  targetuserref:targetuserref,	
				  classid:classid,
				  userid:userid,
				  yearid:yearid,
				},
			cache: false,
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			success:function(json){
			    if(json.type == "success"){
			    	alert('评教成功,谢谢您的参与！！')
			    	location.href="teacherappraise?m=list_stu&displayTitle=false";
			    }else{
			        alert(json.msg);
			    }                
			}				
		}); 	
	}
	
	function appraiseTeacher(tchRef,tchName,subject){
		$.ajax({
			url:'teacherappraise?m=getteacherclass',
			type:'POST',
			data:{tchid:tchRef},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				var html="";
				if(rps.type=="success"){
					html+="<span><img src='"+rps.objList[0]
					  +"' onerror="+"this.src='images/defaultheadsrc.png'"+" width='135' height='135' /></span>";
					html+="<span>"+tchName+"<br/>";
					html+=rps.objList[1][0].classgrade+rps.objList[1][0].classname;
					
					$("#span_"+tchRef).html("<strong class='font-red'>"+subject+"&nbsp;&nbsp;"+tchName+"</strong><span class='m_lr_5'>&gt;&gt;</span>");
					
					$("#targetclassuserref").val(rps.objList[1][0].ref);
					$("#classid").val(rps.objList[1][0].classid);
					$("#targetuserref").val(tchRef);
					$("#form1")[0].reset();
					$("#def_text").hide();
					$("#appTable").show();
					$("#elt_submit").show();
				}else{
					html+="ERROR！！无法找到教师..";
				}
				$("#tchinfo").html(html);

			}
		});
		
	}
	</script>
</head>

<body>
<%@include file="/util/head.jsp" %>

<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
 <div id="tchinfo" class="pj_xspj_layoutR">
 </div>
 <div class="pj_xspj_layoutL">
   <p class="font14">任课教师序列：</p>
   <p class="p_t_15">
   <c:if test="${teaList==null}">
   		没有找到当前学年的任课老师！
   </c:if>
   <c:if test="${teaList!=null}">
	   <c:forEach items="${teaList}" var="tch">
		   <c:if test="${tch.status }">
		      ${tch.subject }&nbsp;&nbsp;${tch.name }<font color="blue">[已评]</font>
		      <span class="m_lr_5">&gt;&gt;</span>
		   </c:if>
		   
		   <c:if test="${!tch.status&&tch.ref==tchref }">
		   <span id="span_${tch.ref }">
		  	  <a id="${tch.ref }" href="javascript:appraiseTeacher('${tch.ref }','${tch.name }','${tch.subject }');"><span id="a_${tch.ref }">${tch.subject }&nbsp;&nbsp;${tch.name }</span></a>
		  	  <span class="m_lr_5">&gt;&gt;</span>
		  	</span>
		   </c:if>
		   <c:if test="${!tch.status&&tch.ref!=tchref }">
		   <span id="span_${tch.ref }">
		  	  <a id="${tch.ref }" href="teacherappraise?m=list_stu&displayTitle=false&tchref=${tch.ref }"><span id="a_${tch.ref }">${tch.subject }&nbsp;&nbsp;${tch.name }</span></a>
		  	  <span class="m_lr_5">&gt;&gt;</span>
		  	</span>
		   </c:if>
	   </c:forEach>
   </c:if>
   </p>
</div>

 <p class="pj_title">评价内容</p>
    <% int i=1; %>
    <form id="form1" action="" name="">
  	<input type="hidden" id="targetclassuserref" name="targetclassuserref" value=""/>
  	<input type="hidden" id="classid" name="classid" value=""/>
  	<input type="hidden" id="targetuserref" name="targetuserref" value=""/>
  	<p id="def_text" align="center">没有需要进行评教的老师...</p>
    <table id="appTable" border="0" cellpadding="0" cellspacing="0" class=" pj_tab w920" style="display:none;">
      <c:forEach items="${appitemList}" var="item"  varStatus="stauts">
  		<c:if test="${item.istitle==1}">
  		  <script>
		    hdtitlelen++;
		  </script>
	  		<tr class="${status.index%2==1?'trbg2':''}">
	        <td><p><%=i++ %>、${item.name}</p>
	          <div>
	            <ul>
	              <li id="item_${item.ref}_answer_1">
	              	A.<input id="${item.ref}_r_answer_1" type="radio" name="${item.ref}_answer" value="10"/>
	              </li>
	              <li id="item_${item.ref}_answer_2">
	              	B.<input id="${item.ref}_r_answer_2" type="radio" name="${item.ref}_answer" value="7.5"/>
	              </li>
	              <li id="item_${item.ref}_answer_3">
	              	C.<input id="${item.ref}_r_answer_3" type="radio" name="${item.ref}_answer" value="5"/>
	              </li>
	              <li id="item_${item.ref}_answer_4">
	              	D.<input id="${item.ref}_r_answer_4" type="radio" name="${item.ref}_answer" value="2.5"/>
	              </li>
	            </ul>
	        </div>
	        </td>
	        </tr>
	      </c:if>
	   </c:forEach>
    </table>
    <script type="text/javascript">
  		<c:if test="${!empty appitemList}">	
  		<c:forEach items="${appitemList}" var="item">
  		  	<c:if test="${item.itemid>0&&item.istitle==0}">
  		  	  $("#${item.itemid}_r_answer_${item.optionid}").val("${item.itemid}|${item.optionid}|"+(12.5-${item.optionid}*2.5));
	  		  $("#item_${item.itemid}_answer_${item.optionid}").append("${item.name}");
  		  	</c:if>
  		  </c:forEach>
  		</c:if>  
  	</script>
  	</form>
  <p id="elt_submit" class="p_20 m_l_25 " style="display:none;"><a href="javascript:doSubAppraTeacher();" class="an_blue">提&nbsp;交</a></p>
</div>

<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
<div id="pj_welcome" class="public_windows_layout w650 h400" style="display:none;">
  <p class="t_c font14 font_strong p_t_10">温馨小提示</p>
  <p class="p_20">　　同学们，感谢你们参与本次评教工作。学生评价教师的工作是我校每年的一项非常重要的常规工作。评教活动是同学们直接参与学校管理的重要形式，也是沟通师生感情的重要途径。通过学生评教，学校对数据进行科学分析，然后将评教情况在全校教职工大会上给予反馈。对于评教中发现的问题，学校也将客观分析，找出出现问题的原因，及时进行改进。多年来，学校开展评教这项工作，取得了明显的成效。<br />
    　　评教工作也是一项很严肃的工作，请同学们对教师进行客观、公正的评价，特别是不能仅凭借个人的感情恩怨，更不能用个人感情代替一切。不要因为老师与自己关系好就一律选A，对老师做的不够的地方没有任何反映，老师也就不容易发现自己的问题，不利于改进教育教学工作；也不要因为老师批评过自己就一律选D，如果老师对学生的错误行为视而不见，也不利于学生的健康成长，如果老师在管理学生时方法不当或言词过激，我们可以向老师指出，但不能因此就否定老师的全部工作。<br />
    　　实际上，学生评价老师，换一个角度看，也是我们体现自己素质水平的重要形式。所以评教中，我们要求同学们要正确处理好这些关系，要严肃认真、根据老师的实际情况进行客观评价，既有政策水平，又不能偏激。<br />
    　　本次评教是匿名评价，请您客观公正地对教师进行评价。系统要求学生每评完1位教师提交1次，并一定要一次性把所有的给自己授课的教师以及本班班主任评完；评完后退出，即完成整个评教过程。</p>
  <p class="t_c p_b_10"><a class="an_blue" href="javascript:closeModel('pj_welcome');">开始评教</a></p>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
