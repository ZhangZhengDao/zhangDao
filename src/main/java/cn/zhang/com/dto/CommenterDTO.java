package cn.zhang.com.dto;

import cn.zhang.com.model.User;
import lombok.Data;

@Data
public class CommenterDTO {
    private Long id;
    private String content;
    private User user;
}
