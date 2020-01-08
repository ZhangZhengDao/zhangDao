package cn.zhang.com.dto;

import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private User user;
    private Question question;
}
