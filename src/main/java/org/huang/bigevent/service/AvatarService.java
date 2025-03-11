package org.huang.bigevent.service;

public interface AvatarService {
    String findAvatarNameByUserId(Integer userId);
    
    void updateAvatarNameByUserId(Integer userId, String avatarName);
    
    void addAvatar(Integer userId, String avatarName);
}
