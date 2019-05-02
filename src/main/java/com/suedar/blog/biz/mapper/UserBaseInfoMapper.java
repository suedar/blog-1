package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.UserBaseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserBaseInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserBaseInfo record);

    int insertSelective(UserBaseInfo record);

    UserBaseInfo selectByPrimaryKey(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(UserBaseInfo record);

    int updateByPrimaryKey(UserBaseInfo record);
}