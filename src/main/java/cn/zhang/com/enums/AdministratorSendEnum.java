package cn.zhang.com.enums;

public enum AdministratorSendEnum {
    AdministratorSend(1,"添加好友成功","我们已经是好友啦，快来聊天吧")
    ;

    private Integer stat;
    private String Describe;
    private String Content;

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    AdministratorSendEnum(Integer stat, String describe, String content) {
        this.stat = stat;
        Describe = describe;
        Content = content;
    }
}
