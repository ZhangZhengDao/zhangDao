package cn.zhang.com.dto;

import cn.zhang.com.model.User;
import lombok.Data;

import java.util.List;

@Data
public class friendTextDTO {
    private User user;//数量
    private Integer newestsize;//未读消息数量
    private String textend;//显示最后一条未读消息
}
