package org.huang.bigevent.controller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.pojo.User;
import org.huang.bigevent.service.AvatarService;
import org.huang.bigevent.service.UserService;
import org.huang.bigevent.utils.AliyunOssPicturesUtil;
import org.huang.bigevent.utils.Md5Util;
import org.huang.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AvatarService avatarService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @PostMapping("/registry")
    public Result register(@Pattern(regexp = "^.{5,16}$") String username,@Pattern(regexp = "^.{5,16}$") String password) {
        // 检查用户名是否已经被注册
        boolean isExist = userService.isExistUserByUsername(username);
        if(isExist){
            return Result.builder().code(1).message("用户名已经被注册").build();
        }
        // 注册用户
        userService.register(username, password);
        return Result.builder().code(0).message("注册成功").build();
    }
    
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^.{5,16}$") String username,@Pattern(regexp = "^.{5,16}$") String password) {
        // 检查用户名是否存在
        var user = userService.findUserByUsername(username);
        if(user == null){
            return Result.builder().code(1).message("用户不存在，请先进行注册").build();
        }
        // 检查密码是否正确
        boolean isCorrect = user.getPassword().equals(Md5Util.getMD5String(password));
        if(!isCorrect){
            return Result.builder().code(1).message("账号或密码错误，请重试").build();
        }
        // 登录成功
        // 生成token
        String token = userService.createToken(user);
        // 将token保存到redis中
        String key = "user:token:" + user.getUsername();
        stringRedisTemplate.opsForValue().set(key,token,12, TimeUnit.HOURS);
        return Result.builder().code(0).message("登录成功").data(token).build();
    }
    
    @GetMapping("/userinfo")
    public Result userinfo(){
        // 从ThreadLocal中获取用户信息
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        // 根据用户名查询用户信息
        String username= (String) userinfo.get("username");
        var user = userService.findUserByUsername(username);
        user.setPassword("此地无银三百两~");
        return Result.builder().code(0).message("获取用户信息成功").data(user).build();
    }
    
    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated User user){
        // 从ThreadLocal中获取用户信息
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        // 根据用户名查询用户信息
        String username= (String) userinfo.get("username");
        var userInDB = userService.findUserByUsername(username);
        // 更新用户信息
        userInDB.setNickname(user.getNickname());
        userInDB.setEmail(user.getEmail());
        // 保存到数据库
        userService.updateUserInfo(userInDB);
        return Result.builder().code(0).message("更新用户信息成功").build();
    }
    
    @PatchMapping("/updateAvatar") // @PatchMapping是用来更新部分字段的
    public Result updateAvatar(@RequestParam @URL String avatarUrl){ // @URL是用来校验URL格式的
        // 从ThreadLocal中获取用户信息
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        // 根据用户名查询用户信息
        String username= (String) userinfo.get("username");
        Integer userId = (Integer) userinfo.get("id");
        var user = userService.findUserByUsername(username);
        // 查询用户头像
        String avatarName = avatarService.findAvatarNameByUserId(userId);
        // 如果用户头像不存在，则添加用户头像
        if(avatarName == null){
            avatarService.addAvatar(userId, avatarUrl);
        }else{
            // 如果用户头像存在，则更新用户头像
            avatarService.updateAvatarNameByUserId(userId, avatarUrl);
            // 删除原来的头像
            AliyunOssPicturesUtil.delete(avatarName);
        }
        // 更新用户头像
        user.setUserPic(avatarUrl);
        // 保存到数据库
        userService.updateUserAvatar(user);
        return Result.builder().code(0).message("更新用户头像成功").build();
    }
    
    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String,Object> params, @RequestHeader("Authorization") String token){
        // 校验密码
        var result = userService.validatePassword(params);
        if(result.getCode() != 0){
            return result;
        }
        // 从ThreadLocal中获取用户信息
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        // 根据用户名查询用户信息
        var user= userService.findUserByUsername((String) userinfo.get("username"));
        String newPassword = (String) params.get("newPassword");
        // 更新密码
        user.setPassword(Md5Util.getMD5String(newPassword));
        // 保存到数据库
        userService.updatePassword(user);
        // 删除redis中的token
        stringRedisTemplate.delete("user:token:" + user.getUsername());
        return Result.builder().code(0).message("更新密码成功").build();
    }
}
