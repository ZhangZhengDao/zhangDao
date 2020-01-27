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
    @Update("UPDATE COMMENT SET comment_count=comment_count+1 WHERE id=#{id}")
   public void upda(@Param("id") Integer id);

}