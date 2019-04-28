package com.suedar.blog.biz.service;

import com.suedar.blog.api.domain.RecommendChapter;
import com.suedar.blog.api.query.RecommendChapterPageQuery;
import com.suedar.blog.biz.base.Page;

import java.util.List;

public interface RecommendChapterService {

    int insert(RecommendChapter recommendChapter);

    int deleteById(Integer id);

    int updateById(RecommendChapter recommendChapter);

    Page<List<RecommendChapter>> pageQuery(RecommendChapterPageQuery query);

}
