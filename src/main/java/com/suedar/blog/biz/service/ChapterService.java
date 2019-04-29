package com.suedar.blog.biz.service;

import com.suedar.blog.api.domain.Chapter;
import com.suedar.blog.api.query.ChapterPageQuery;
import com.suedar.blog.biz.base.Page;

import java.util.List;

public interface ChapterService {
    int insert(Chapter chapter);

    int deleteById(Integer id);

    int updateById(Chapter chapter);

    Page<List<Chapter>> pageQuery(ChapterPageQuery query);

    List<Chapter> selectByIds(List<Integer> ids);
}
