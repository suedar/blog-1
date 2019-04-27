package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.ChapterLabelRelation;

public interface ChapterLabelRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChapterLabelRelation record);

    int insertSelective(ChapterLabelRelation record);

    ChapterLabelRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChapterLabelRelation record);

    int updateByPrimaryKey(ChapterLabelRelation record);
}