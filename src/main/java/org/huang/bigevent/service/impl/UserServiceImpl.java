package org.huang.bigevent.service.impl;

import org.huang.bigevent.mapper.UserMapper;
import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.pojo.User;
import org.huang.bigevent.service.UserService;
import org.huang.bigevent.utils.JwtUtil;
import org.huang.bigevent.utils.Md5Util;
import org.huang.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 保存到数据库，进行注册
        var user = User.builder().username(username).password(md5String)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        userMapper.insertUser(user);
    }
    
    @Override
    public boolean isExistUserByUsername(String username) {
        var user = userMapper.selectUserByUsername(username);
        return user != null;
    }
    
    @Override
    public User findUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }
    
    @Override
    public String createToken(User user) {
        // 如果登陆时，已经有token，那么删除原来的token
        String key= "user:token:" + user.getUsername();
        if(stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.delete(key);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        return JwtUtil.genToken(claims);
    }
    
    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserInfo(user);
    }
    
    @Override
    public void updateUserAvatar(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserAvatar(user);
    }
    
    @Override
    public void updatePassword(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updatePassword(user);
    }
    
    @Override
    public Result validatePassword(Map<String, Object> params) {
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        var user = userMapper.selectUserByUsername((String) userinfo.get("username"));
        String oldPassword = (String) params.get("oldPassword");
        String newPassword = (String) params.get("newPassword");
        String repeatPassword = (String) params.get("repeatPassword");
        if(oldPassword.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()){
            return Result.builder().code(1).message("密码不能为空").build();
        } else if(!newPassword.equals(repeatPassword)){
            return Result.builder().code(1).message("两次输入的密码不一致").build();
        } else if(!Md5Util.checkPassword(oldPassword, user.getPassword())){
            return Result.builder().code(1).message("原密码错误").build();
        }
        return Result.builder().code(0).message("密码校验通过").build();
    }
}
