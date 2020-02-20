package cn.zhang.com.dto;

import cn.zhang.com.enums.WebSoketStatEnum;
import lombok.Data;

/*返回给客户端消息内容封装*/
@Data
public class MessageDTO {
    private String userAccount;
    private String dAccount;
    private String userUrl;
    private String dUrl;
    private String text;
    private String userName;
    private String dName;
    private Integer stat;//发送到消息窗口的状态
}
