package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.RecommendChapter;

public interface RecommendChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendChapter record);

    int insertSelective(RecommendChapter record);

    RecommendChapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendChapter record);

    int updateByPrimaryKey(RecommendChapter record);
}