/**
 * 日期格式化
 * 
 * @param format
 *            格式类型
 * @return
 */
Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, // month
        "d+" : this.getDate(),    // day
        "h+" : this.getHours(),   // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
        "S" : this.getMilliseconds() // millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

/**
 * 今天
 * 
 * @return
 */
Date.prototype.showToDay=function()
{
	var Nowdate=new Date();
	M=Number(Nowdate.getMonth())+1
	return Nowdate.getFullYear()+"-"+M+"-"+Nowdate.getDate();
}

/**
 * 本周第一天
 * 
 * @return
 */
Date.prototype.showWeekFirstDay=function()
{
	var Nowdate=new Date();
	var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);
	return WeekFirstDay;
}
/**
 * 本周最后一天
 * 
 * @return
 */
Date.prototype.showWeekLastDay=function()
{
	var Nowdate=new Date();
	var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);
	var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);
	return WeekLastDay;
}

/**
 * 本月第一天
 * 
 * @return
 */
Date.prototype.showMonthFirstDay=function()
{
	var Nowdate=new Date();
	var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1);
	return MonthFirstDay;
}
/**
 * 本月最后一天
 * 
 * @return
 */
Date.prototype.showMonthLastDay=function()
{
	var Nowdate=new Date();
	var MonthNextFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+1,1);
	var MonthLastDay=new Date(MonthNextFirstDay-86400000);
	return MonthLastDay;
}
/**
 * 上月第一天
 * 
 * @return
 */
Date.prototype.showPreviousFirstDay=function()
{
	var MonthFirstDay=showMonthFirstDay()
	return new Date(MonthFirstDay.getFullYear(),MonthFirstDay.getMonth()-1,1)
}
/**
 * 上月最后一天
 * 
 * @return
 */
Date.prototype.showPreviousLastDay=function()
{
	var MonthFirstDay=showMonthFirstDay();
	return new Date(MonthFirstDay-86400000);
}
/**
 * 上周第一天
 * 
 * @return
 */
Date.prototype.showPreviousFirstWeekDay=function()
{
	var WeekFirstDay=showWeekFirstDay()
	return new Date(WeekFirstDay-86400000*7)
}
/**
 * 上周最后一天
 * 
 * @return
 */
Date.prototype.showPreviousLastWeekDay=function()
{
	var WeekFirstDay=showWeekFirstDay()
	return new Date(WeekFirstDay-86400000)
}
/**
 * 上一天
 * 
 * @return
 */
Date.prototype.showPreviousDay=function()
{
	var MonthFirstDay=new Date();
	return new Date(MonthFirstDay-86400000);
}
/**
 * 下一天
 * 
 * @return
 */
Date.prototype.showNextDay=function()
{
	var MonthFirstDay=new Date();
	return new Date((MonthFirstDay/1000+86400)*1000);
}
/**
 * 下周第一天
 * 
 * @return
 */
Date.prototype.showNextFirstWeekDay=function()
{
	var MonthFirstDay=showWeekLastDay()
	return new Date((MonthFirstDay/1000+86400)*1000)
}
/**
 * 下周最后一天
 * 
 * @return
 */
Date.prototype.showNextLastWeekDay=function()
{
	var MonthFirstDay=showWeekLastDay()
	return new Date((MonthFirstDay/1000+7*86400)*1000)
}
/**
 * 下月第一天
 * 
 * @return
 */
Date.prototype.showNextFirstDay=function()
{
	var MonthFirstDay=showMonthFirstDay()
	return new Date(MonthFirstDay.getFullYear(),MonthFirstDay.getMonth()+1,1)
}
/**
 * 下月最后一天
 * 
 * @return
 */
Date.prototype.showNextLastDay=function()
{
	var MonthFirstDay=showMonthFirstDay()
	return new Date(new Date(MonthFirstDay.getFullYear(),MonthFirstDay.getMonth()+2,1)-86400000)
}
/**
 * 本年的第一天
 * 
 * @return
 */
Date.prototype.showYearFirstDay=function()
{
	var ndate=new Date();
	return new Date(ndate.getFullYear(),0,1);
}
/**
 * 本年的最后一天
 * 
 * @return
 */
Date.prototype.showYearLastDay=function()
{
	var ndate=new Date();
	return new Date(ndate.getFullYear(),11,31); 
}
/**
 * 下周第一天
 * 
 * @return
 */
Date.prototype.showNextFirstWeekDay=function()
{
	var MonthFirstDay=showWeekLastDay()
	return new Date((MonthFirstDay/1000+86400)*1000)
}

// +---------------------------------------------------
// | 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
// +---------------------------------------------------
/**
 * 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
 */
Date.prototype.DateDiff = function(strInterval, dtEnd) {    
var dtStart = this;   
	if (typeof dtEnd == 'string' )// 如果是字符串转换为日期型
	{    
	   dtEnd = StringToDate(dtEnd);   
	}   
	switch (strInterval){    
	   case 's' :return parseInt((dtEnd - dtStart) / 1000);   
	   case 'n' :return parseInt((dtEnd - dtStart) / 60000);   
	   case 'h' :return parseInt((dtEnd - dtStart) / 3600000);   
	   case 'd' :return parseInt((dtEnd - dtStart) / 86400000);   
	   case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));   
	   case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);   
	   case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();   
	}   
}

/**
 * 默认格式输出 'yyyy-MM-dd'
 * 
 * @return
 */
Date.prototype.toString=function(){	
	return this.getFullYear()+"-"+(this.getMonth()+1)+"-"+this.getDate();
}

/* 得到日期年月日等加数字后的日期 */
Date.prototype.dateAdd = function(interval,number)
{
    var d = this;
    var k={'y':'FullYear', 'q':'Month', 'm':'Month', 'w':'Date', 'd':'Date', 'h':'Hours', 'n':'Minutes', 's':'Seconds', 'ms':'MilliSeconds'};
    var n={'q':3, 'w':7};
    eval('d.set'+k[interval]+'(d.get'+k[interval]+'()+'+((n[interval]||1)*number)+')');
    return d;
}


/**
 * 输出 'yyyy-MM-dd HH:Mi:ss'
 * 
 * @return
 */
Date.prototype.toFullString=function(){	
	return this.getFullYear()+"-"+(this.getMonth()+1)
				+"-"+this.getDate()+" "+this.getHours()+":"+
				this.getMinutes()+":"+this.getSeconds();
}
Date.prototype.getDateWeek=function()
{
	todayDate = new Date();
	date = todayDate.getDate();
	month= todayDate.getMonth() +1;
	year= todayDate.getFullYear();
	var dateweek = "";//今天是:

	    dateweek =  dateweek + year+"年" + "年" + month + "月" + date + "日 ";
	/*
	if(navigator.appVersion.indexOf("MSIE") != -1)
	{
	    dateweek = dateweek + year + "年" + month + "月" + date + "日 ";
	}*/
	switch(todayDate.getDay())     
	{ 
	case 0:dateweek += "星期日";break;
	case 1:dateweek += "星期一";break;
	case 2:dateweek += "星期二";break;
	case 3:dateweek += "星期三";break;
	case 4:dateweek += "星期四";break;                  
	case 5:dateweek += "星期五";break;
	case 6:dateweek += "星期六";break;    
	}
	return dateweek;
}
/**
 * 输出。日历   
 * 
 * @return
 */
Date.prototype.DateCandlarShow=function(){
	var d=this;
	function 获取当前时间(cd)
	{	
	        var 日期对象 = typeof(cd)=="undefined"?d:cd;        // 声明一个日期对象
	        var 小时 = 日期对象.getHours();        // 获取当前时
	        var 分钟 = 日期对象.getMinutes();// 获取当前分
	        var 秒=日期对象.getSeconds();
	        秒=秒<10?"0"+秒:秒;
	        var 上下午;        // 上午下午

	        // 判断上下午
	        if (小时 >= 12)
	        {
	                小时 -= 12;
	                上下午 = "下午";
	        }
	        else
	        {
	                上下午 = "上午";
	        }
	        
	        小时 = (小时 == 0)?12:小时;        // 判断是否为０时

	        // 分钟数小于１０则补零
	        if (分钟 < 10)
	        {
	                分钟 = "0" + 分钟;
	        }
	        return (小时 + ':' + 分钟 + ':'+秒+"  " + 上下午);
	}

	function 是否为闰年(年份)
	{
	        if (年份%4==0)        // 年份能被４整除为闰年
	        {
	                return true;
	        }
	        else
	        {
	                return false;
	        }
	}
	// 获取选择月对应的实际天数
	function getDays(月份,年份)
	{
	        // 设定每月的实际天数（１２个月中只有２月会在闰年和非闰年时天数不同）
	        var 天数 = new Array(12);
	        天数[0] = 31;
	        天数[1] = (是否为闰年(年份))?29:28;        // ２月份时闰年为29天，否则为28天
	        天数[2] = 31;
	        天数[3] = 30;
	        天数[4] = 31;
	        天数[5] = 30;
	        天数[6] = 31;
	        天数[7] = 31;
	        天数[8] = 30;
	        天数[9] = 31;
	        天数[10] = 30;
	        天数[11] = 31;
	        return 天数[月份];
	}

	// 获取月名称
	function 获取月份名称(月份)
	{
	        var 月份名称 = new Array(12);
	        月份名称[0] = "1月";
	        月份名称[1] = "2月";
	        月份名称[2] = "3月";
	        月份名称[3] = "4月";
	        月份名称[4] = "5月";
	        月份名称[5] = "6月";
	        月份名称[6] = "7月";
	        月份名称[7] = "8月";
	        月份名称[8] = "9月";
	        月份名称[9] = "10月";
	        月份名称[10] = "11月";
	        月份名称[11] = "12月";
	        return 月份名称[月份];
	}

	// 设置日历参数
	function 设置日历()
	{
	        var 日期对象 =d;
	        var 年份 = 日期对象.getFullYear();        // 获取当前时间年份
	        var 月份 = 日期对象.getMonth(); // 获取当前时间月份
	        var 月份名称 = 获取月份名称(月份);     // 获取月份名称
	        var 当前日 = 日期对象.getDate();        // 获取当前日期中的日

	        var 当月第一天的星期数 = (new Date(年份,月份,1)).getDay();  // 获取本月的第一天对应的星期数

	        var 本月天数 = getDays(月份,年份);        // 获取当前日期天数（也是当月对应的最后一天）

	        // 显示日历
	        显示日历(当月第一天的星期数+1,本月天数,当前日,月份名称,年份);
	}  
	
	function 动态加载时间(sid){
		setInterval(function(){
				var newd=new Date();
			    var 日期对象 =newd;
			    var 年份 = 日期对象.getFullYear();        // 获取当前时间年份
			    var 月份 = 日期对象.getMonth()+1; // 获取当前时间月份
			    var 月份名称 = 获取月份名称(月份);     // 获取月份名称
			    var time=获取当前时间(newd);			   
			    if(time=="0:00:00  上午")
			    	newd.DateCandlarShow();
			    else
			    	 $("#"+sid).html(年份+"年"+月份+"月 "+time);
		},1000);
	} 
	// 显示日历
	function 显示日历(当月第一天的星期数,本月天数,当前日,月份名称,年份)
	{
	        var 日历结构体 = "";
	        日历结构体 += '<TABLE border=1 cellspacing=1 STYLE="WIDTH:100%;border;text-align:center">';
	        var spanName="span_c_"+new Date().getTime();
	        日历结构体 += '<Td colspan=7 height=25 ALIGN="CENTER"><p class="font_strong t_c" id="'+spanName+'">';
	        日历结构体 += 年份+'年'+月份名称+" ";	       
	        var t=获取当前时间();	        
	        日历结构体 += t;
	        日历结构体 += '</p></Td>'; 

	        var 打开单元格 = '<TD width=15 height=30 align="center" valign="center" style="font-weight: normal">'; 
	        var 关闭单元格 = '</TD>'; 

	        var 星期数组 = new Array(7);
	        星期数组[0] = "&nbsp;日";
	        星期数组[1] = "&nbsp;一";
	        星期数组[2] = "&nbsp;二";
	        星期数组[3] = "&nbsp;三";
	        星期数组[4] = "&nbsp;四";
	        星期数组[5] = "&nbsp;五"; 
	        星期数组[6] = "&nbsp;六";

	        日历结构体 += '<TR>';
	        for (var 变量=0;变量 < 7;++变量) 
	        {
	                日历结构体 += 打开单元格 + 星期数组[变量] + 关闭单元格;
	        }
	        日历结构体 += '</TR>';

	        var 日 = 1;                // 显示日
	        var 单元格位置 = 1;        // 单元格定位

	        for (var 行=1;行<=Math.ceil((本月天数+当月第一天的星期数-1)/7);++行)
	        {
	                日历结构体 += '<TR align=center valign=center>';
	                for (var 列=1;列<=7;++列)
	                {
	                        if (日>本月天数)
	                        {
	                                break;
	                        }
	                        if (单元格位置<当月第一天的星期数)
	                        {
	                                日历结构体 += '<TD> </TD>';
	                                单元格位置++;
	                        }
	                        else
	                        {
	                                if (日==当前日)        // 如果日期为当天则用红色加粗显示
	                                {
	                                        日历结构体 += '<TD height=27>';
	                                        日历结构体 += '<FONT color=red><B>';
	                                        日历结构体 += 日;
	                                        日历结构体 += '</B></FONT><BR>';
	                                        日历结构体 += '</TD>';
	                                }
	                                else
	                                {
	                                        日历结构体 += '<TD height=27 style="font-weight: normal">'+日+'</TD>';
	                                }
	                                日++;
	                        }
	                }
	                日历结构体 += '</TR>';
	        }
	        日历结构体 += '</TABLE>';
	        document.write(日历结构体);
	        // 动态加载时间
	        动态加载时间(spanName);
	}
	设置日历();	
	
};