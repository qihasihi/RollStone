<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
    <meta http-equiv="description" content="This is my page"/>
</head>
<script type="text/javascript">
    var selectedCourseids='';
    var termendtime='${term.semesterenddatestring}';
    $(function(){
        //$("#material_id").val(materialid);
    });
</script>
<body>
    <input id="termid" name="termid" type="hidden" value="${term.ref}"/>
    <input id="gradeid" name="gradeid" type="hidden" value="${grade.gradeid}"/>
    <input id="subjectid" name="gradeid" type="hidden" value="${subject.subjectid}"/>
    <%--<input id="materialid" name="materialid" type="hidden" value="${materialid}"/>--%>
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
                    <font color="red">教务未为您当前学期设置班级，暂不可创建专题!</font>
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
                        <p><span>全部班级:</span><input onchange="changgeAllStartTime(this)"  class="w140" placeholder="设置开始时间"
                                                    onfocus="WdatePicker({maxDate:'${term.semesterenddatestring}',minDate:'%y-%M-{%d+0}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" id="all_classtime"  name="all_classtime" type="text" /></p>
                    </div>
                    <div id="list_ct" style="display:none">
                        <c:forEach var="cl" items="${ cuList}">
                            <p id="p_${cl.classid}"  style="display:none"><span>${cl.classname}:</span>&nbsp;
                                <input  onchange="changeOneStartTime(this,${cl.classid})"  class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'${cl.classid}_classEndTime\',{H:-1})}',minDate:'%y-%M-{%d+0}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${cl.classid}_classtime"  name="classtime" type="text" /></p>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==1}">
                                <p id="p_ts_${ts.CLASS_ID}" style="display:none"><span>${ts.CLASSNAME}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'${ts.CLASS_ID}_ts_classEndTime\',{H:-1})}',minDate:'%y-%M-{%d+0}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_ts_classtime"  name="ts_classtime" type="text" /></p>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="tvc" items="${ tvcList}">
                            <p id="p_v_${tvc.virtualclassid}"  style="display:none"><span>${tvc.virtualclassname}:</span>&nbsp;
                                <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'${tvc.virtualclassid}_v_classEndtime\',{H:-1})}',minDate:'%y-%M-{%d+0}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${tvc.virtualclassid}_v_classtime"  name="v_classtime" type="text" /></p>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==2}">
                                <p id="p_v_${ts.CLASS_ID}"><span>${ts.CLASSNAME}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置开始时间" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'${ts.CLASS_ID}_v_classEndTime\',{H:-1})}',minDate:'%y-%M-{%d+0}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_v_classtime"  name="v_classtime" type="text" /></p>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </td>
        </tr>

        <tr>
            <th>&nbsp;&nbsp;结束时间：</th>
            <td>
                <div class="w430">
                    <p><input id="classEndTimeType0" name="classEndTimeType" type="radio" value="0"/>所有班级一致&nbsp;&nbsp;
                        <input id="classEndTimeType1" name="classEndTimeType" type="radio" value="1" checked="true" />不一致</p>
                    <div id="all_ct_end">
                        <p><span>全部班级:</span><input class="w140" placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'all_classtime\',{H:-1})}',maxDate:'${term.semesterenddatestring}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" id="all_classEndTime"  name="all_classEndTime" type="text" /></p>
                    </div>
                    <div id="list_ct_end" style="display:none">
                        <c:forEach var="cl" items="${ cuList}">
                            <p id="p_end_${cl.classid}"  style="display:none"><span>${cl.classname}:</span>&nbsp;
                                <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'${cl.classid}_classtime\',{H:-1})}',maxDate:'${term.semesterenddatestring}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${cl.classid}_classEndTime"  name="classEndTime" type="text" /></p>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==1}">
                                <p id="p_ts_end_${ts.CLASS_ID}" style="display:none"><span>${ts.CLASSNAME}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'${ts.CLASS_ID}_ts_classtime\',{d:7})}',maxDate:'${term.semesterenddatestring}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_ts_classEndTime"  name="ts_classEndTime" type="text" /></p>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="tvc" items="${ tvcList}">
                            <p id="p_v_end_${tvc.virtualclassid}"  style="display:none"><span>${tvc.virtualclassname}:</span>&nbsp;
                                <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'${tvc.virtualclassid}_v_classtime\',{d:7})}',maxDate:'${term.semesterenddatestring}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${tvc.virtualclassid}_v_classEndtime"  name="v_classEndTime" type="text" /></p>
                        </c:forEach>
                        <c:forEach var="ts" items="${ tsList}">
                            <c:if test="${ts.CLASS_TYPE==2}">
                                <p id="p_v_end_${ts.CLASS_ID}"><span>${ts.CLASSNAME}:</span>&nbsp;
                                    <input class="public_input w220" placeholder="设置结束时间" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'${ts.CLASS_ID}_v_classtime\',{d:7})}',maxDate:'${term.semesterenddatestring}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="${ts.CLASS_ID}_v_classEndTime"  name="v_classEndTime" type="text" /></p>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </td>
        </tr>



        <tr>
            <th><span class="ico06"></span>分享等级：</th>
            <td>
                <input type="radio" name="sharetype" id="sharetype" value="1" />
                校内教师&nbsp;&nbsp;&nbsp;&nbsp;
               <%-- <input type="radio" name="sharetype" id="sharetype" value="2" />
                云端教师&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <input type="radio" name="sharetype" id="sharetype" value="3" checked="true"/>
                不分享</td>
        </tr>

        <tr>
            <th>&nbsp;</th>
            <td>
               <a id="addButton" href="javascript:getTchingMaterial();" class="an_small">提&nbsp;交</a>
            </td>
        </tr>
    </table>
</body>
</html>
<c:if test="${empty tc}">
    <script type="text/javascript">
        $("#classtimeType").attr("checked",true);
        $("#all_ct").show();

        $("#classEndTimeType0").attr("checked",true);
        $("#all_ct_end").show();
    </script>
</c:if>
