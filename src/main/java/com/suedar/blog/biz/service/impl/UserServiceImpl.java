package com.suedar.blog.biz.service.impl;

import com.suedar.blog.api.domain.UserBaseInfo;
import com.suedar.blog.biz.mapper.UserBaseInfoMapper;
import com.suedar.blog.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    public UserServiceImpl(UserBaseInfoMapper userBaseInfoMapper) {
        this.userBaseInfoMapper = userBaseInfoMapper;
    }

    @Override
    public UserBaseInfo selectByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return userBaseInfoMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByUserId(UserBaseInfo userBaseInfo) {
        if (userBaseInfo == null || userBaseInfo.getUserId() == null) {
            return -1;
        }

        return userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    @Override
    public UserBaseInfo selectByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        return userBaseInfoMapper.selectByUserName(userName);
    }
}
