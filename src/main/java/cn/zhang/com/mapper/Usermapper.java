package cn.zhang.com.mapper;

import cn.zhang.com.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

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

    @Select("select * from user where account=#{account} ")
    User findByAccount(@Param("account") String accout);

    @Update("update user set token=#{token},gmt_create=#{gmt_create},gmt_modified=#{gmt_modified} where account=#{account}")
    void updateUser(@Param("token") String token,@Param("gmt_create") Long gmt_create,@Param("gmt_modified") Long gmt_modified,@Param("account") String account);

}
