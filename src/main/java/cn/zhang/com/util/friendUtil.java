package cn.zhang.com.util;

public interface friendUtil {
    //根据 id判断两个用户是否是好友
    public boolean friendboolean(Integer Userid,Integer friendid);
    //使两个用户成为为好友
    public boolean addfriend(Integer UserID,Integer friendID);
}
