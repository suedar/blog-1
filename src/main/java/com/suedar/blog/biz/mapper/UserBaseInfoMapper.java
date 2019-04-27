package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.UserBaseInfo;

public interface UserBaseInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserBaseInfo record);

    int insertSelective(UserBaseInfo record);

    UserBaseInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserBaseInfo record);

    int updateByPrimaryKey(UserBaseInfo record);
}