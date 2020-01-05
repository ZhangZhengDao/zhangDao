package cn.zhang.com.mapper;

import cn.zhang.com.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Usermapper {
    //向数据库添加内容方法
    @Insert("insert into user(name,account,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{account},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
     void insert(User user);

    @Select("select * from user where token=#{token}")//如果形参不是类的话需要加上@Param
    User findByToken(@Param("token") String token);

    @Select("select * from user")
    List<User> select();
}
