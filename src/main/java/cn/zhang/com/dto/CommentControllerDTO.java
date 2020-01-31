package cn.zhang.com.dto;

import cn.zhang.com.model.Comment;
import cn.zhang.com.model.Dianzan;
import cn.zhang.com.model.User;
import lombok.Data;

@Data
public class CommentControllerDTO {
    private Comment comment;
    private User user;
    private Dianzan dianzan;
}
