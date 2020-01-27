package cn.zhang.com.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private String type;
    private List<String > name;
}
