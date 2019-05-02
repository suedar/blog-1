package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.ChapterLabelRelation;
import com.suedar.blog.api.dto.ChapterLabelRelationDelDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterLabelRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChapterLabelRelation record);

    int insertSelective(ChapterLabelRelation record);

    ChapterLabelRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChapterLabelRelation record);

    int updateByPrimaryKey(ChapterLabelRelation record);

    List<ChapterLabelRelation> selectByChapterIds(@Param("chapterIds") List<Integer> chapterIds);

    List<ChapterLabelRelation> selectByLabelIds(List<Integer> labelIds);

    int del(ChapterLabelRelationDelDTO delDTO);
}