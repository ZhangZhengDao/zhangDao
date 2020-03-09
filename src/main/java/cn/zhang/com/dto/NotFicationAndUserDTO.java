package cn.zhang.com.dto;

import cn.zhang.com.model.Comment;
import cn.zhang.com.model.NotiFication;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import lombok.Data;

@Data
public class NotFicationAndUserDTO {
    private User user;
    private Question question;
    private NotiFication notiFication;
    private Comment comment;
}
