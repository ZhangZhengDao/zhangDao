package cn.zhang.com.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
