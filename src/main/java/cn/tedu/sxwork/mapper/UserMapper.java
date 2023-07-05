package cn.tedu.sxwork.mapper;


import cn.tedu.sxwork.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> selectAllUser();

    //通过用户查询密码,且将密码封装到User对象中
    @Select("select * from user where username=#{name}")
    User selectByUsername(String name);

    @Delete("delete from user where username=#{name}")
    void deleteByUsername(String name);
    //注册
    @Insert("insert into user values(null,#{username},#{password},#{nick},0,#{signature})")
    void insertUser(User user);

    //更新钱包
    @Update("update user set purse=#{i} where id=#{id}")
    void trade(float i,Integer id);

    @Select("select * from user where id=#{id}")
    User selectById(int id);

    @Update("update user set purse=#{i} where id=#{id}")
    void updateUser();
}



