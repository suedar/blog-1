package com.suedar.blog.biz.service.impl;

import com.suedar.blog.api.domain.RecommendChapter;
import com.suedar.blog.api.query.RecommendChapterPageQuery;
import com.suedar.blog.biz.base.Page;
import com.suedar.blog.biz.mapper.RecommendChapterMapper;
import com.suedar.blog.biz.service.RecommendChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendChapterImpl implements RecommendChapterService {

    private final RecommendChapterMapper mapper;

    @Autowired
    public RecommendChapterImpl(RecommendChapterMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int insert(RecommendChapter recommendChapter) {
        if (recommendChapter == null) {
            return -1;
        }

        return mapper.insertSelective(recommendChapter);
    }

    @Override
    public int deleteById(Integer id) {
        if (id == null) {
            return -1;
        }

        RecommendChapter recommendChapter = new RecommendChapter();
        recommendChapter.setId(id);
        recommendChapter.setIsDeleted(Boolean.TRUE);
        return mapper.updateByPrimaryKeySelective(recommendChapter);
    }

    @Override
    public int updateById(RecommendChapter recommendChapter) {
        if (recommendChapter == null) {
            return -1;
        }

        return mapper.updateByPrimaryKeySelective(recommendChapter);
    }

    @Override
    public Page<List<RecommendChapter>> pageQuery(RecommendChapterPageQuery query) {
        if (query == null) {
            return null;
        }
        Page<List<RecommendChapter>> page = new Page<>(query.getPageNum(), query.getPageSize());
        page.setResult(mapper.pageQuery(query));
        page.setTotalNum(mapper.countPageQuery(query));
        return page;
    }
}
