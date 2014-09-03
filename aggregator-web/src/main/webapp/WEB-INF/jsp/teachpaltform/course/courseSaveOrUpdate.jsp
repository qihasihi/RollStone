<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
        <script type="text/javascript" src="js/teachpaltform/course.js"></script>
    </head>
    <script type="text/javascript">
        var materialid="${materialid}";
        var selectedCourseids='';
        $(function(){
            inputThinking();
            $("#material_id").val(materialid);
            /**
            *开始时间后，班级、开始时间都不能再修改
             */
            <c:if test="${!empty tc and tc.isbegin eq 1}">
                $("input[name='classes']").attr("disabled",true);
                $("input[name='tsclasses']").attr("disabled",true);
                $("input[name='vclasses']").attr("disabled",true);

                $("input[name='all_classtime']").attr("disabled",true);
                $("input[name='classtime']").attr("disabled",true);
                $("input[name='ts_classtime']").attr("disabled",true);
                $("input[name='v_classtime']").attr("disabled",true);
            </c:if>

             <c:if test="${!empty tc && !empty tc.courselevel && tc.courselevel ne 3}">
                    $("textarea[id='introduction']").attr("disabled",true);
                    $("input[id='courseName']").attr("disabled",true);
             </c:if>

            <c:if test="${!empty tc && !empty tc.qcourselevel && tc.qcourselevel ne 3}">
                    $("textarea[id='introduction']").attr("disabled",true);
                    $("input[id='courseName']").attr("disabled",true);
            </c:if>

            <c:if test="${!empty tc && !empty tc.quoteid && tc.quoteid ne 0}">
                $("input[name='sharetype']").attr("disabled",true);
            </c:if>
           });
       </script>
       <body>
       <input type="hidden" id="material_id"/>
       <c:if test="${!empty tc}">
        <div class="subpage_head"><span class="ico55"></span><strong>修改专题</strong></div>
    </c:if>

    <c:if test="${empty tc}">
    <div class="subpage_head"><span class="ico55"></span><strong>添加专题</strong></div>
    <div class="subpage_nav">
        <ul>
            <li class="crumb"><a href="javascript:void(0);">新建专题</a></li>
            <li><a href="javascript:toCourseLibrary();">引用专题</a></li>
        </ul>
    </div>
    </c:if>

    <div class="content1">
        <c:if test="${!empty tc}">
            <input type="hidden" id="courseid" name="courseid" value="${tc.courseid}"/>
        </c:if>
        <input id="termid" name="termid" type="hidden" value="${ term.ref}"/>
        <input id="gradeid" name="gradeid" type="hidden" value="${ grade.gradeid}"/>
        <input id="subjectid" name="gradeid" type="hidden" value="${ subject.subjectid}"/>
        <input id="materialid" name="materialid" type="hidden" value="${materialid}"/>
        <input id="courselevel" name="courselevel" type="hidden" value="3"/>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
            <col class="w120"/>
            <col class="w700"/>
            <tr>
                <th><span class="ico06"></span>专题名称：</th>
                <td><input id="courseName" name="courseName" type="text" class="w430" /></td>
            </tr>
            <tr>
                <th>&nbsp;&nbsp;专题简介：</th>
                <td><textarea  id="introduction" name="introduction" class="h90 w600"></textarea></td>
            </tr>
            <tr>
                <th><span class="ico06"></span>班&nbsp;&nbsp;&nbsp;&nbsp;级:</th>
                <td>
                    <c:if test="${fn:length(cuList)==0 && fn:length(tvcList)==0}">
                        <font color="red">教务未为您当前学期设置班级，暂不可创建专题！</font>
                    </c:if>
                    <ul class="public_list2">
                        <c:forEach var="cl" items="${ cuList}">
                            <li><input type="checkbox" id="classes" name="classes" value="${cl.classid}">${cl.classname}</li>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==1}">
                                <li><input type="checkbox" id="classes" name="classes" value="${ts.CLASS_ID}">${ts.CLASSNAME}</li>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="tvc" items="${ tvcList}">
                            <li><input type="checkbox" id="vclasses" name="vclasses" value="${tvc.virtualclassid}">${tvc.virtualclassname}</li>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==2}">
                                <li><input type="checkbox" id="vclasses" name="vclasses" value="${ts.CLASS_ID}">${ts.CLASSNAME}</li>
                            </c:if>
                        </c:forEach>
                </ul>
                </td>
            </tr>
            <tr>
                <th><span class="ico06"></span>开始时间：</th>
                <td>
                    <div class="w430">
                        <p><input id="classtimeType" name="classtimeType" type="radio" value="0"/>所有班级一致&nbsp;&nbsp;
                           <input id="classtimeType" name="classtimeType" type="radio" value="1" checked="true" />不一致</p>
                        <div id="all_ct">
                            <p><span>全部班级:</span><input class="w140" placeholder="设置开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" id="all_classtime"  name="all_classtime" type="text" /></p>
                        </div>
                        <div id="list_ct" style="display:none">
                            <c:forEach var="cl" items="${ cuList}">
                                <p id="p_${cl.classid}"  style="display:none"><span>${cl.classname}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${cl.classid}_classtime"  name="classtime" type="text" /></p>
                            </c:forEach>
                            <c:forEach var="ts" items="${ tsList}">
                                <c:if test="${ts.CLASS_TYPE==1}">
                                    <p id="p_ts_${ts.CLASS_ID}" style="display:none"><span>${ts.CLASSNAME}:</span>&nbsp;
                                        <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_ts_classtime"  name="ts_classtime" type="text" /></p>
                                </c:if>
                            </c:forEach>
                            <c:forEach var="tvc" items="${ tvcList}">
                                <p id="p_v_${tvc.virtualclassid}"  style="display:none"><span>${tvc.virtualclassname}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${tvc.virtualclassid}_v_classtime"  name="v_classtime" type="text" /></p>
                            </c:forEach>
                            <c:forEach var="ts" items="${ tsList}">
                                <c:if test="${ts.CLASS_TYPE==2}">
                                    <p id="p_v_${ts.CLASS_ID}"><span>${ts.CLASSNAME}:</span>&nbsp;
                                        <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_v_classtime"  name="v_classtime" type="text" /></p>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </td>
            </tr>
    <!--
            <tr>
                <th>&nbsp;&nbsp;结束时间：</th>
                <td>
                    <div class="w430">
                        <p><input id="classEndTimeType0" name="classEndTimeType" type="radio" value="0"/>所有班级一致&nbsp;&nbsp;
                            <input id="classEndTimeType1" name="classEndTimeType" type="radio" value="1" checked="true" />不一致</p>
                        <div id="all_ct_end">
                            <p><span>全部班级:</span><input class="w140" placeholder="设置结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" id="all_classEndTime"  name="all_classEndTime" type="text" /></p>
                        </div>
                        <div id="list_ct_end" style="display:none">
                            <c:forEach var="cl" items="${ cuList}">
                                <p id="p_${cl.classid}"  style="display:none"><span>${cl.classname}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${cl.classid}_classEndTime"  name="classEndTime" type="text" /></p>
                            </c:forEach>
                            <c:forEach var="ts" items="${ tsList}">
                                <c:if test="${ts.CLASS_TYPE==1}">
                                    <p id="p_ts_${ts.CLASS_ID}" style="display:none"><span>${ts.CLASSNAME}:</span>&nbsp;
                                        <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_ts_classEndTime"  name="ts_classEndTime" type="text" /></p>
                                </c:if>
                            </c:forEach>
                            <c:forEach var="tvc" items="${ tvcList}">
                                <p id="p_v_${tvc.virtualclassid}"  style="display:none"><span>${tvc.virtualclassname}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${tvc.virtualclassid}_v_classEndtime"  name="v_classEndTime" type="text" /></p>
                            </c:forEach>
                            <c:forEach var="ts" items="${ tsList}">
                                <c:if test="${ts.CLASS_TYPE==2}">
                                    <p id="p_v_${ts.CLASS_ID}"><span>${ts.CLASSNAME}:</span>&nbsp;
                                        <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_v_classEndTime"  name="v_classEndTime" type="text" /></p>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </td>
            </tr>-->
            <tr>
                <th><span class="ico06"></span>分享等级：</th>
                <td>
                    <input type="radio" name="sharetype" id="sharetype" value="1" />
                    校内教师&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="sharetype" id="sharetype" value="2" />
                    云端教师&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="sharetype" id="sharetype" value="3" checked="true"/>
                    不分享</td>
            </tr>
            <tr>
                <th>&nbsp;&nbsp;关联专题：</th>
                <td id="related_p">
                   <div> <input type="text" id="related" class="w430"/>
                    <input type="hidden" id="hcourse_id"/>
                   </div>
                    <ul  class="p_t_10" id="selectedCourse">
                        <c:if test="${!empty trList}">
                            <c:forEach items="${trList}" var="itm">
                                <li id="${itm.relatedcourseid}">${itm.coursename}<a class="ico_delete" title="删除" href="javascript:delLi(${itm.relatedcourseid})"></a></li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </td>
            </tr>

            <tr>
                <th>&nbsp;</th>
                <td>
                    <c:if test="${!empty tc}">
                        <a id="updateButton" href="javascript:updateTeacherCourse();" class="an_small">保&nbsp;存</a>
                    </c:if>

                    <c:if test="${empty tc}">
                        <a id="addButton" href="javascript:getTchingMaterial();" class="an_small">提&nbsp;交</a>
                    </c:if>
                    <a href="javascript:closeAddorUpdateWindow();" class="an_small">取&nbsp;消</a></td>
            </tr>
        </table>
    </div>
    <%@include file="/util/foot.jsp" %>
    </body>
</html>

<div id="teaching_materia_div" class="public_windows" style="display:none;">
    <h3><a href="javascript:closeModel('teaching_materia_div');" title="关闭"></a>选教材版本</h3>
    <div class="jxxt_float_jcbb">
        <p class="font-black">请选择本学科授课的教材版本，选择后搜索资源时会优先推荐该版本。</p>
        <ul id="teaching_materia">
        </ul>

        <p class="t_c ">
            <c:if test="${!empty tc}">
                <a id="addReportBtn" class="an_public1"  href="javascript:void(0);" onclick="updateTeacherCourse();">确定</a>
            </c:if>

            <c:if test="${empty tc}">
                <a id="addReportBtn" class="an_public1"  href="javascript:void(0);" onclick="addTeacherCourse();">确定</a>
            </c:if>
        </p>
    </div>
</div>

<c:if test="${!empty tc}">
    <script type="text/javascript">
        $(function(){
            $("#courseName").val("${tc.coursename}");
            $("#gradeid option[value='${tc.gradeid}']").attr("selected","selected");
            $("#subjectid option[value='${tc.subjectid}']").attr("selected","selected");
            $("input[name='sharetype'][value='${tc.sharetype}']").attr("checked",true);


            $("#all_ct").hide();
            $("#list_ct").show();
            $("#list_ct").children().hide();

            <c:forEach var="cid" items="${ tc.classIdArray}" varStatus="status">
            <c:if test="${tc.classTypeArray[status.index]==1}">
            $("input[name='classes'][value='${cid}']").attr("checked",true);
            $("#p_${cid}").show();
            $("#${cid}_classtime").val("${tc.classTimeArray[status.index]}");
            </c:if>
            <c:if test="${tc.classTypeArray[status.index]==2}">
            $("input[name='vclasses'][value='${cid}']").attr("checked",true);
            $("#p_v_${cid}").show();
            $("#${cid}_v_classtime").val("${tc.classTimeArray[status.index]}");
            </c:if>
            </c:forEach>

            <c:if test="${tc.classtimetype}">
            $("#classtimeType").attr("checked",true);
            $("#list_ct").hide();
            $("#all_ct").show();
            $("#all_classtime").val("${tc.classTimeArray[0]}");
            </c:if>

            $("#introduction").html("${tc.introduction}");

            $("#gradeid").bind("change",function(){
                getClassByOptions();
            });
            $("#subjectid").bind("change",function(){
                getClassByOptions();
            });
        });
    </script>
</c:if>
<c:if test="${empty tc}">
    <script type="text/javascript">
        $("#classtimeType").attr("checked",true);
        $("#all_ct").show();
    </script>
</c:if>
