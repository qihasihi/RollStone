
//得到每月的天数
function getDaysOfMonth(year, month){
    if(year && month){
        if(month == 2){
            //2月闰年判断
            if((year % 4 == 0 && year % 100 !=0) || year % 400 == 0){
                return 29;
            }
            return 28;
        }
        var bigMonth = [1,3,5,7,8,10,12];
        var littleMonth = [4,6,9,11];
        for(var m in bigMonth){
            if(bigMonth[m] == month)
                return 31;
        }
        for(var m in littleMonth) {
            if(littleMonth[m] == month)
                return 30;
        }
    }
}

//根据年月生成日历
function showCalendar(year, month){
    if(year && month){
        $("#year").html(year);
        $("#month").html(month);
        var tbody = document.getElementById("calendar");
        //生成前删除之前的行
        /*for(var i = 0,len =$(tbody).children('tr').length;i< len;i++){
         tbody.deleteRow();

         }*/
        $(tbody).children('tr').each(function(){
            $(this).remove();
        })
        var date = new Date(year, month - 1, 1);//month月的第一天
        var day = date.getDay();//星期
        var days = getDaysOfMonth(year, month);//month月的总天数
        var temp = Math.floor((days + day) / 7);
        var rows = (days + day) % 7 == 0 ? temp : (temp + 1);//要循环的行数
        var d = 1;
        for(var i = 1; i <= rows; i++){//循环行
            var tr = document.createElement("tr");
            for(var j = 1; j <= 7; j++){//循环列
                var td = document.createElement("td");
                td.id = "";
                //超过最大天数赋空
                if(d > days){
                    td.innerHTML = "";
                    tr.appendChild(td);
                    continue;
                }
                if(i == 1){
                    //第一行判断month月第一天是星期几，例如星期二前面空两个周日、周一 <td><a href="1">1</a></td>
                    if(j >= day + 1){
                        var html = '<a href="#">';
                        td.innerHTML = html + (d < 10 ? "0" + d : d)+'</a>';
                        td.id ="date_"+ (d < 10 ? "0" + d : d);
                        d++;
                    }else{
                        td.innerHTML = "";
                    }
                } else {
                    var html = '<a href="#">';
                    td.innerHTML = html + (d < 10 ? "0" + d : d)+'</a>';
                    td.id ="date_"+ (d < 10 ? "0" + d : d);
                    d++;
                }
                tr.appendChild(td);
            }
            tbody.appendChild(tr);
        }
    }
    markCalendar(year,month);

    $("#calendar td").click(function(){
        $('td[id^="date"]').removeClass("border");
        $(this).addClass("border");
        var currentDay=$(this).attr('id').substring($(this).attr('id').lastIndexOf("_")+1);
        getTchCourseList(year,month,currentDay);
    })

    var tyear=new Date().getFullYear();
    var tmonth=new Date().getMonth()+1;
    var today=new Date().getDate();
    today=today<10?"0"+today:today;
    tmonth=tmonth<10?"0"+tmonth:tmonth;
    if(tyear==year&&parseInt(tmonth)==parseInt(month))
        $("#date_"+today).addClass('crumb');
    else
        today=1;
    getTchCourseList(year,month,today);
}

/**
 * 查询日历下的列表 （学生，教师，班主任通用）
 * @param year
 * @param month
 * @param day
 */
function getTchCourseList(year,month,day){
    if(typeof year=='undefined'||  month=='undefined'||  day=='undefined')
        return;
    var param={};
    if(typeof(isLession)!="undefined"&&isLession&&!isBanzhuren&&typeof(_clsid)=="undefined"){
        param.gradeid=gradeid;
        param.subjectid=subjectid;
    }

    if(typeof(_clsid)!="undefined"&&!isNaN(_clsid)){
      //  param.gradeid=gradeid;
        param.classid=_clsid;
        if(typeof(subjectid)=="object")
            param.subjectid=subjectid.value;
        else
             param.subjectid=subjectid;
    }
    param.atermid=termid;
    if(typeof(termid)=="object"){
        param.atermid=termid.value;
    }
    param.year=year;
    param.month=month;
    param.day=day;

    $.ajax({
        url:'teachercourse?getCourseCalanedListAjax',
        data:param,
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            var h='';
            var lessLen=$("#ul_lession").length;
            if(lessLen>0)
                h+='<li>我的课表</li>'
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    if(lessLen<1&&typeof(_clsid)!="undefined"){
                        h+='<p><span class="font-black">'+itm.classgrade+itm.classname+'</span>&nbsp;&nbsp;';
                        h+='<a href="task?toStuTaskIndex&courseid='+itm.courseid+'" target="_blank">'+itm.coursename+'</a>';
                        if(itm.courseScoreIsOver==1){
                                h+='<a href="clsperformance?m=toIndex&courseid='+itm.courseid+'&subjectid='+itm.subjectid+'&termid='+param.atermid+'&classid='+itm.classid+'&classtype=1" target="_blank" class="ico33"></a>';
                         }
                        h+='<br>'+itm.datemsg+'</p>';
                    }else{
                        h+='<li>';
                        h+='<a  href="group?m=toGroupManager&termid='+itm.termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'&classid='+itm.classid+'">'+itm.classgrade+itm.classname+'</a>&nbsp;&nbsp;';
                        if(itm.isself>0)
                            h+='<a href="javascript:genderUrl('+itm.courseid+')">'+itm.coursename+'</a>';
                        else
                            h+=itm.coursename;
                        if(typeof(itm.courseScoreIsOver)!="undefined"&&itm.courseScoreIsOver==1)
                                h+='<a href="clsperformance?m=toIndex&courseid='+itm.courseid+'&subjectid='+itm.subjectid+'&termid='+param.atermid+'&classid='+itm.classid+'&classtype=1" target="_blank" class="ico33"></a>';

                        h+='<br>';
                        h+=itm.datemsg;
                        h+='</li>';
                    }

                })
            }else{
                if(lessLen<1&&typeof(_clsid)!="undefined")
                    h+='<p>暂无数据!</p>';
                else
                    h+='<li>暂无数据!</li>';
            }
            if(lessLen<1&&typeof(_clsid)!="undefined")
                $("#ul_kebiao").html(h);
            else
                $("#ul_lession").html(h);
        }
    });
}


function genderUrl(courseid){
    var href="task?toTaskList&courseid="+courseid+"&subjectid="+subjectid+"&gradeid="+gradeid+"&termid="+termid;
    window.open(href);
}


function markCalendar(selYear,selMonth){
    if(!selYear||!selMonth)
        return;
    var p={year:selYear,month:selMonth};
    if(typeof(subjectid)!="undefined"&&typeof(subjectid)!="object"){
        p.subjectid=subjectid;
    }
    if(typeof(gradeid)!="undefined"){
        p.gradeid=gradeid;
    }
    if(typeof(_clsid)!="undefined"&&!isNaN(_clsid)){
        p.classid=_clsid;
    }
    $.ajax({
        url:'teachercourse?markCourseCalendar',
        data:p,
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var day=parseInt(itm.E_DAY)<10?"0"+itm.E_DAY:+itm.E_DAY;
                    if(itm.FLAG==1&&itm.HASCOURSE>0){
                        $("#date_"+day).children('a').append('<span class="ico89b"></span>');
                    }
                    if(itm.FLAG==0&&itm.HASCOURSE>0){
                        $("#date_"+day).children('a').append('<span class="ico89a"></span>');
                    }
                });
            }
        }
    });
}