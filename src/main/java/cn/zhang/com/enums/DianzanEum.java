package cn.zhang.com.enums;

public enum  DianzanEum {
    REPLY_QUESTION(1,"点赞"),
    REPLY_COMMENT(2,"取消点赞")
    ;
    private Integer type;
    private String name;

    DianzanEum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
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
}
