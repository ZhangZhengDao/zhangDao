package cn.zhang.com.enums;

public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    RELY_DIANZAN(3,"点赞了你回复"),
    RELY_QUXIAODIANZAN(4,"取消点赞了你的回复"),
    RELY_NIMINGYONGHU(-1,"(匿名用户)")
    ;
    private Integer type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationEnum(Integer status, String name) {
        this.type = status;
        this.name = name;
    }
}
