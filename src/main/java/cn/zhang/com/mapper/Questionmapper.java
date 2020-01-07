package cn.zhang.com.mapper;

import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.model.Question;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from question")
    public List<Question> contion();

    @Select("SELECT * FROM question where id=#{id}")
    public Question findID(@Param("id") long id);

    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmt_create},gmt_modified=#{gmt_modified},creator=#{creator},comment_count=#{comment_count},view_count=#{view_count},like_count=#{like_count},tag=#{tag} where id=#{id}")
    public void Update(Question question);
}
