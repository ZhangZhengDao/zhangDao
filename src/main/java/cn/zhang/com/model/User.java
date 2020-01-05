package cn.zhang.com.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String avatar_url;
    private String account;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
}
