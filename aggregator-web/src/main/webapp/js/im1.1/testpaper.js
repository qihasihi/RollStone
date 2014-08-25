/**
 * Created by zhengzhou on 14-8-25.
 */
var TestQues;
if (TestQues == undefined) {
    TestQues = function(pid,cid) {
        this.init(pid,cid);
    }
};
TestQues.prototype={
    //初始化
    init:function(pid,cid){
        currentQuesObj={idx:-1,quesid:0,parentQuesId:0};
        paperid=pid;
        courseid:cid;
    },
//下一题
     nextNum:function(type){
        var vi=(typeof(i)=="undefined"||i==null)?0:i;
        if(typeof(allquesidObj)=="undefined"||allquesidObj.length<1){
            alert('异常错误，参数异常!');return;
        }

        if(typeof(allquesidObj)!="undefined"&&allquesidObj.Trim().length>0){
            var arrayObj=allquesidObj.split(",");
            if(arrayObj.length>0){
                if(type==1){
                    if(currentQuesObj.idx==0)
                        currentQuesObj.idx=currentQuesObj.idx;
                    else
                        currentQuesObj.idx=currentQuesObj.idx+1;
                }else{
                    if(currentQuesObj.idx==arrayObj.length-1)
                        currentQuesObj.idx=currentQuesObj.idx;
                    else
                        currentQuesObj.idx=currentQuesObj.idx-1;
                }
                var cuQuesid=arrayObj[currentQuesObj.idx];
                var parentQuesId=0;
                if(cuQuesid.indexOf("|")!=-1){
                    cuQuesid=cuQuesid.split("|")[1];
                    parentQuesId=cuQuesid.split("|")[0];
                }
                currentQuesObj={idx:,quesid:cuQuesid,parentQuesId:parentQuesId};
                //下一体

            }
        }
    },
    /**
     * 加载试题
     */
    loadQues:function(){
        var ajxObj={
            url:'paperques?m=nextTestQues',
            dataType:'json',
            type:'post',
            data:{quesid:currentQuesObj.quesid,paperid:paperid,courseid:courseid},
            error:function(){alert('异常错误，原因：未知!');},
            success:function(rps){
                //失败
                if(rps.type=="error"){
                    alert(rps.msg);return;
                }
                var h='';
                //成功：
                if(rps.type=="success"){
                    var quesObj=rps.objList[0];
                    var parentQuesObj=null;
                    //加载题干
                    if(rps.objList.length>1){ //如果是组试题，则 0:题干   1：题
                        quesObj=rps.objList[1];//
                        parentQuesObj=rps.objList[0];
                    }
                }
            }
        };
        //发送请求
        $.ajax(ajxObj);

    }
}