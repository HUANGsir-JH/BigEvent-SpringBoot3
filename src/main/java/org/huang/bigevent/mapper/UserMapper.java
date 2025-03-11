package org.huang.bigevent.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.huang.bigevent.pojo.User;

@Mapper
public interface UserMapper {
    
    @Select("select * from user where username=#{username}")
    User selectUserByUsername(String username);
    
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},#{createTime},#{updateTime})")
    void insertUser(User user);
    
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} " +
            "where id=#{id}")
    void updateUserInfo(User user);
    
    @Update("update user set user_pic=#{userPic},update_time=#{updateTime} where id=#{id}")
    void updateUserAvatar(User user);
    
    @Update("update user set password=#{password},update_time=#{updateTime} where id=#{id}")
    void updatePassword(User user);
}
