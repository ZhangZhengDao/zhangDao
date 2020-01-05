package cn.zhang.com.mapper;

import cn.zhang.com.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Questionmapper {
    /*添加数据*/
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) " +
            "values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{comment_count},#{view_count},#{like_count},#{tag})")
    public void create(Question question);
    /*查询所有数据*/
    @Select("SELECT * FROM question limit #{page},#{size} ")
    public List<Question> select(@Param("page") Integer page, @Param("size") Integer size);
}
