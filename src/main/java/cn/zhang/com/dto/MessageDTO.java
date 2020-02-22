package cn.zhang.com.dto;

import cn.zhang.com.enums.WebSoketStatEnum;
import lombok.Data;

/*返回给客户端消息内容封装*/

public class MessageDTO {
    private String userAccount;
    private String dAccount;
    private String userUrl;
    private String dUrl;
    private String text;
    private String userName;
    private String dName;
    private Integer stat;//发送到消息窗口的状态
    private String data;

    public MessageDTO() {
    }

    public MessageDTO(String userAccount, String dAccount, String userUrl, String dUrl, String text, String userName, String dName, Integer stat, String data) {
        this.userAccount = userAccount;
        this.dAccount = dAccount;
        this.userUrl = userUrl;
        this.dUrl = dUrl;
        this.text = text;
        this.userName = userName;
        this.dName = dName;
        this.stat = stat;
        this.data = data;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getdAccount() {
        return dAccount;
    }

    public void setdAccount(String dAccount) {
        this.dAccount = dAccount;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getdUrl() {
        return dUrl;
    }

    public void setdUrl(String dUrl) {
        this.dUrl = dUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /*
    * 将时间重新编译
    * */
    public String getData() {
        String d= this.data.replace(":", "-");
        return d;
    }

    public void setData(String data) {
        this.data = data.replace(":", "-");
    }
}
