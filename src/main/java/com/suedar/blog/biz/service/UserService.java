package com.suedar.blog.biz.service;


import com.suedar.blog.api.domain.UserBaseInfo;

public interface UserService {

    UserBaseInfo selectByUserId(Integer userId);

    int updateByUserId(UserBaseInfo userBaseInfo);
}
