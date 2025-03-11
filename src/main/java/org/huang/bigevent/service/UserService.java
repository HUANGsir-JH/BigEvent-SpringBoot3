package org.huang.bigevent.service;

import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.pojo.User;

import java.util.Map;


public interface UserService {
    void  register(String username, String password);
    boolean isExistUserByUsername(String username);
    
    User findUserByUsername(String username);
    
    String createToken(User user);
    
    void updateUserInfo(User user);
    
    void updateUserAvatar(User user);
    
    void updatePassword(User user);
    
    Result validatePassword(Map<String, Object> params);
}
