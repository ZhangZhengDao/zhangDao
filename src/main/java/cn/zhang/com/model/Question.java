package cn.zhang.com.model;

import lombok.Data;
import sun.rmi.runtime.Log;
@Data
public class Question {
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
