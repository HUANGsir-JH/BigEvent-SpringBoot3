package org.huang.bigevent.service.impl;

import org.huang.bigevent.mapper.AvatarMapper;
import org.huang.bigevent.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    private AvatarMapper avatarMapper;
    @Override
    public String findAvatarNameByUserId(Integer userId) {
        return avatarMapper.findAvatarNameByUserId(userId);
    }
    
    @Override
    public void updateAvatarNameByUserId(Integer userId, String avatarName) {
        avatarMapper.updateAvatarNameByUserId(userId, avatarName);
    }
    
    @Override
    public void addAvatar(Integer userId, String avatarName) {
        avatarMapper.addAvatar(userId, avatarName);
    }
    
    
}
