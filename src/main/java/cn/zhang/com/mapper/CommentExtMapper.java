package cn.zhang.com.mapper;

import cn.zhang.com.model.Comment;
import cn.zhang.com.model.CommentExample;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;


@Mapper
public interface CommentExtMapper {

    @Bean




    @Update("UPDATE cc SET comment_count=comment_count+1 WHERE id=#{id}")
    public void upda(@Param("id") Integer id);

    @Update("UPDATE cc SET like_count=like_count+1 WHERE id=#{id}")
    public void addupdate(@Param("id") Integer id);

    @Update("UPDATE cc SET like_count=like_count-1 WHERE id=#{id}")
    public void jian(@Param("id") Integer id);
    @Select("select * from cc where parent_id=#{CommentId} and TYPE=#{id}")
    @Results(id="base_map",value={
            @Result(id=true, property ="id",column = "id"),
            @Result(property = "parent_id",column = "parentId"),
            @Result(property = "TYPE",column = "type"),
            @Result(property = "content",column = "content"),
            @Result(property = "commentator",column = "commentator"),
            @Result(property = "gmt_create",column = "gmtCreate"),
            @Result(property = "gmt_modified",column = "gmtModified"),
            @Result(property = "like_count",column = "likeCount"),
            @Result(property = "comment_count",column = "commentCount"),
    })
    public List<Comment> findComment(@Param("id") Integer id,@Param("CommentId") Long CommentId);
}