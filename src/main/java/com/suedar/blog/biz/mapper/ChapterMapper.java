package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.Chapter;
import com.suedar.blog.api.query.ChapterPageQuery;

import java.util.List;

public interface ChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    List<Chapter> selectByPrimaryKeyList(List<Integer> ids);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    List<Chapter> pageQuery(ChapterPageQuery query);

    int countPageQuery(ChapterPageQuery query);
}