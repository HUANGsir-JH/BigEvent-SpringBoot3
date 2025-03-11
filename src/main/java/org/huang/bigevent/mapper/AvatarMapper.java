package org.huang.bigevent.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AvatarMapper {
    
    @Insert("INSERT INTO avatar(user_id, avatar_name) VALUES(#{userId}, #{avatarName})")
    void addAvatar(Integer userId, String avatarName);
    
    @Select("SELECT avatar_name FROM avatar WHERE user_id = #{userId}")
    String findAvatarNameByUserId(Integer userId);
    
    @Update("UPDATE avatar SET avatar_name = #{avatarName} WHERE user_id = #{userId}")
    void updateAvatarNameByUserId(Integer userId, String avatarName);
}
