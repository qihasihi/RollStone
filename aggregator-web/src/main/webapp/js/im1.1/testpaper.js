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
    //��ʼ��
    init:function(pid,cid){
        currentQuesObj={idx:-1,quesid:0,parentQuesId:0};
        paperid=pid;
        courseid:cid;
    },
//��һ��
     nextNum:function(type){
        var vi=(typeof(i)=="undefined"||i==null)?0:i;
        if(typeof(allquesidObj)=="undefined"||allquesidObj.length<1){
            alert('�쳣���󣬲����쳣!');return;
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
                //��һ��

            }
        }
    },
    /**
     * ��������
     */
    loadQues:function(){
        var ajxObj={
            url:'paperques?m=nextTestQues',
            dataType:'json',
            type:'post',
            data:{quesid:currentQuesObj.quesid,paperid:paperid,courseid:courseid},
            error:function(){alert('�쳣����ԭ��δ֪!');},
            success:function(rps){
                //ʧ��
                if(rps.type=="error"){
                    alert(rps.msg);return;
                }
                var h='';
                //�ɹ���
                if(rps.type=="success"){
                    var quesObj=rps.objList[0];
                    var parentQuesObj=null;
                    //�������
                    if(rps.objList.length>1){ //����������⣬�� 0:���   1����
                        quesObj=rps.objList[1];//
                        parentQuesObj=rps.objList[0];
                    }
                }
            }
        };
        //��������
        $.ajax(ajxObj);

    }
}