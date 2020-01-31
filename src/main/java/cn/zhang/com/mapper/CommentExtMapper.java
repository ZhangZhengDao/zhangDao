package cn.zhang.com.mapper;

import cn.zhang.com.model.Comment;
import cn.zhang.com.model.CommentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
}