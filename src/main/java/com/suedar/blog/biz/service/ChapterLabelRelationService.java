package com.suedar.blog.biz.service;

import com.suedar.blog.api.domain.ChapterLabelRelation;
import com.suedar.blog.api.dto.ChapterLabelRelationDelDTO;

import java.util.List;

public interface ChapterLabelRelationService {

    List<ChapterLabelRelation> selectByChapterIds(List<Integer> chapterIds);

    List<ChapterLabelRelation> selectByLabelIds(List<Integer> labelIds);

    int insert(List<ChapterLabelRelation> chapterLabelRelations);

    int del(ChapterLabelRelationDelDTO delDTO);
}
