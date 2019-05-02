package com.suedar.blog.biz.service.impl;

import com.suedar.blog.api.domain.Chapter;
import com.suedar.blog.api.query.ChapterPageQuery;
import com.suedar.blog.biz.base.Page;
import com.suedar.blog.biz.mapper.ChapterLabelRelationMapper;
import com.suedar.blog.biz.mapper.ChapterMapper;
import com.suedar.blog.biz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private ChapterLabelRelationMapper relationMapper;

    @Override
    public int insert(Chapter chapter) {
        if (chapter == null) {
            return -1;
        }
        chapterMapper.insertSelective(chapter);
        return chapter.getId();
    }

    @Override
    public int deleteById(Integer id) {
        if (id == null) {
            return -1;
        }

        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setIsDeleted(Boolean.TRUE);
        return chapterMapper.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public int updateById(Chapter chapter) {
        if (chapter == null || chapter.getId() == null) {
            return -1;
        }

        return chapterMapper.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public Page<List<Chapter>> pageQuery(ChapterPageQuery query) {
        if (query == null) {
            return null;
        }

        Page<List<Chapter>> page = new Page<>(query.getPageNum(), query.getPageSize(), query.getIsPage());
        page.setResult(chapterMapper.pageQuery(query));
        page.setTotalNum(chapterMapper.countPageQuery(query));
        return page;
    }

    @Override
    public List<Chapter> selectByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }

        return chapterMapper.selectByPrimaryKeyList(ids);
    }
}
