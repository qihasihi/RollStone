package com.school.entity.personalspace;

import com.school.entity.UserInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qihaishi on 14-6-4.
 */
public class PsUserFriend implements  Serializable {
    private Long ref;
    private UserInfo userinfo;
    private UserInfo friendinfo;
    private Integer isaccept;
    private PsFriendGroupInfo friendgroup;
    private Date ctime;
    private Date mtime;

    private Object friendname;

    public Object getFriendname() {
        return friendname;
    }

    public void setFriendname(Object friendname) {
        this.friendname = friendname;
    }

    public PsFriendGroupInfo getFriendgroup() {
        if(friendgroup==null)
            friendgroup=new PsFriendGroupInfo();
        return friendgroup;
    }
    public void setFriendgroup(PsFriendGroupInfo friendgroup) {
        this.friendgroup = friendgroup;
    }

    public Integer getUserid(){
        return this.getUserinfo().getUserid();
    }
    public Integer getFriendid(){
        return this.getFriendinfo().getUserid();
    }
    public void  setUserid(Integer userid){
        this.getUserinfo().setUserid(userid);
    }
    public void  setFriendid(Integer friendid){
        this.getFriendinfo().setUserid(friendid);
    }

    public UserInfo getUserinfo() {
        if(userinfo==null)
            userinfo=new UserInfo();
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public UserInfo getFriendinfo() {
        if(friendinfo==null)
            friendinfo=new UserInfo();
        return friendinfo;
    }

    public void setFriendinfo(UserInfo friendinfo) {
        this.friendinfo = friendinfo;
    }

    public Long getGroupid() {
        return this.getFriendgroup().getGroupid();
    }

    public void setGroupid(Long groupid) {
        this.getFriendgroup().setGroupid(groupid);
    }

    public Integer getIsaccept() {
        return isaccept;
    }

    public void setIsaccept(Integer isaccept) {
        this.isaccept = isaccept;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

}
