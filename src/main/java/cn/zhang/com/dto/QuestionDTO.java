package cn.zhang.com.dto;

import cn.zhang.com.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private User user;
    private Long id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Long comment_count;
    private Long view_count;
    private Long like_count;
    private String tag;
}
