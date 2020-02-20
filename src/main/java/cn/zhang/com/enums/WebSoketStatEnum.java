package cn.zhang.com.enums;

import lombok.Data;

/*用户的状态*/

public enum  WebSoketStatEnum {
    WEBSOKETSTAT_TRUE(1,"在线,未打开聊天窗口"),
    WEBSOKETSTAT_FALSE(2,"不在线"),
    WEBSOKETSTAT_MASG(3,"打开了聊天窗口，但不是当前用户"),
    WEBSOKETSTAT_MASGTRUE(4,"两个用户打开了一样的聊天窗口"),
    ;
    private Integer stat;
    private String  describe;

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    WebSoketStatEnum(Integer stat, String describe) {
        this.stat = stat;
        this.describe = describe;
    }
}
