package com.suedar.blog.biz.service.impl;


import com.suedar.blog.api.domain.ChapterLabelRelation;
import com.suedar.blog.api.dto.ChapterLabelRelationDelDTO;
import com.suedar.blog.biz.mapper.ChapterLabelRelationMapper;
import com.suedar.blog.biz.service.ChapterLabelRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ChapterLabelRelationServiceImpl implements ChapterLabelRelationService {

    @Autowired
    private ChapterLabelRelationMapper relationMapper;

    @Override
    public List<ChapterLabelRelation> selectByChapterIds(List<Integer> chapterIds) {
        if (CollectionUtils.isEmpty(chapterIds)) {
            return null;
        }

        return relationMapper.selectByChapterIds(chapterIds);
    }

    @Override
    public List<ChapterLabelRelation> selectByLabelIds(List<Integer> labelIds) {
        if (CollectionUtils.isEmpty(labelIds)) {
            return null;
        }

        return relationMapper.selectByLabelIds(labelIds);
    }

    @Override
    public int insert(List<ChapterLabelRelation> chapterLabelRelations) {
        if (CollectionUtils.isEmpty(chapterLabelRelations)) {
            return -1;
        }

        int count = 0;
        for (ChapterLabelRelation relation : chapterLabelRelations) {
            count += relationMapper.insertSelective(relation);
        }

        return count;
    }

    @Override
    public int del(ChapterLabelRelationDelDTO delDTO) {
        if (delDTO == null) {
            return -1;
        }

        return relationMapper.del(delDTO);
    }
}
