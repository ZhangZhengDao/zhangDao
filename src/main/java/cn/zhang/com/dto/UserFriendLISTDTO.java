package cn.zhang.com.dto;

import cn.zhang.com.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.List;

/*Friend html*/
@Data
public class UserFriendLISTDTO {
    //当前用户关注的人
    private User user;
    //是否也关注了当前用户;
    private Boolean oppositeUser;
    //是否在线
    private Boolean stat;


}
