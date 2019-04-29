package com.suedar.blog.biz.controller;

import com.suedar.blog.api.domain.Chapter;
import com.suedar.blog.api.domain.ChapterLabelRelation;
import com.suedar.blog.api.domain.Label;
import com.suedar.blog.api.dto.ChapterLabelRelationDelDTO;
import com.suedar.blog.api.query.ChapterPageQuery;
import com.suedar.blog.api.query.LabelQuery;
import com.suedar.blog.api.vo.ChapterVO;
import com.suedar.blog.biz.base.BaseResult;
import com.suedar.blog.biz.base.Page;
import com.suedar.blog.biz.service.ChapterLabelRelationService;
import com.suedar.blog.biz.service.ChapterService;
import com.suedar.blog.biz.service.LabelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    private final ChapterService chapterService;

    private final LabelService labelService;

    private final ChapterLabelRelationService relationService;

    @Autowired
    public ChapterController(ChapterService chapterService, LabelService labelService, ChapterLabelRelationService relationService) {
        this.chapterService = chapterService;
        this.labelService = labelService;
        this.relationService = relationService;
    }

    @PostMapping("/insert")
    public BaseResult<Integer> insert(@RequestBody ChapterVO chapterVO) {

        // 保存文章
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterVO, chapter);
        int chapterId = chapterService.insert(chapter);

        // 保存文章、标签关系
        int insertRelationRes = 0;
        List<Label> labelList = chapterVO.getLabelList();
        List<ChapterLabelRelation> relationList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(labelList)) {
            labelList.forEach(label -> {
                ChapterLabelRelation relation = new ChapterLabelRelation();
                relation.setChapId(chapterId);
                relation.setLabelId(label.getId());
                relationList.add(relation);
            });

            insertRelationRes = relationService.insert(relationList);
        }


        if (insertRelationRes < 0) {
            return BaseResult.errorReturn("新增文章失败");
        }

        return BaseResult.rightReturn(insertRelationRes);
    }

    @GetMapping("/del")
    public BaseResult<Integer> deleteById(@RequestParam("id") Integer id) {
        int delChapterRes = chapterService.deleteById(id);
        if (delChapterRes < 0) {
            return BaseResult.errorReturn("删除文章失败");
        }

        ChapterLabelRelationDelDTO delDTO = new ChapterLabelRelationDelDTO();
        delDTO.setChapId(id);
        relationService.del(delDTO);

        return BaseResult.rightReturn(delChapterRes);
    }

    @PostMapping("/update")
    public BaseResult<Integer> updateById(@RequestBody ChapterVO chapterVO) {

        // 更新文章
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterVO, chapter);
        int updateChapterRes = chapterService.updateById(chapter);
        if (updateChapterRes < 0) {
            return BaseResult.errorReturn("修改文章失败");
        }

        // 更新标签、文章关系
        List<Label> labelList = chapterVO.getLabelList();
        List<Integer> addLabelIds = new ArrayList<>();
        List<Integer> delLabelIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(labelList)) {
            List<Integer> labelIds = labelList.stream().map(Label::getId).collect(Collectors.toList());
            List<Integer> chapterIds = new ArrayList<>();
            chapterIds.add(chapter.getId());
            List<ChapterLabelRelation> relationList = relationService.selectByChapterIds(chapterIds);
            List<Integer> dbLabelIds = relationList.stream().map(ChapterLabelRelation::getLabelId).collect(Collectors.toList());

            // 待新增标签、文章关系
            labelList.forEach(label -> {
                if (!dbLabelIds.contains(label.getId())) {
                    addLabelIds.add(label.getId());
                }
            });

            // 待删除标签、文章关系
            dbLabelIds.forEach(dbLabelId -> {
                if (!labelIds.contains(dbLabelId)) {
                    delLabelIds.add(dbLabelId);
                }
            });

            // 新增标签、文章关系
            if (!CollectionUtils.isEmpty(addLabelIds)) {
                List<ChapterLabelRelation> addRelationList = new ArrayList<>(addLabelIds.size());
                addLabelIds.forEach(addLabelId -> {
                    ChapterLabelRelation relation = new ChapterLabelRelation();
                    relation.setLabelId(addLabelId);
                    relation.setChapId(chapter.getId());
                    addRelationList.add(relation);
                });
                relationService.insert(addRelationList);
            }

            // 除标签、文章关系
            if (!CollectionUtils.isEmpty(delLabelIds)) {
                ChapterLabelRelationDelDTO delDTO = new ChapterLabelRelationDelDTO();
                delDTO.setLabelIds(delLabelIds);
                delDTO.setChapId(chapter.getId());
                relationService.del(delDTO);
            }
        }

        return BaseResult.rightReturn(updateChapterRes);
    }

    @PostMapping("/pageQuery")
    public BaseResult<Page<List<ChapterVO>>> pageQuery(ChapterPageQuery query) {

        // 查询文章
        Page<List<Chapter>> dbPage = chapterService.pageQuery(query);
        if (dbPage == null) {
            return BaseResult.errorReturn("查询文章失败");
        }

        Page<List<ChapterVO>> chapterPage = new Page<>(dbPage.getPageNum(), dbPage.getPageSize(), dbPage.getTotalNum());
        List<Chapter> chapterList = dbPage.getData();
        if (CollectionUtils.isEmpty(chapterList)) {
            return BaseResult.rightReturn(chapterPage);
        }

        // 查询文章、标签关系
        List<Integer> chapIds = chapterList.stream().map(Chapter::getId).collect(Collectors.toList());
        List<ChapterLabelRelation> relationList = relationService.selectByChapterIds(chapIds);
        Map<Integer, List<Integer>> chapRelationMap = new HashMap<>();
        relationList.forEach(relation -> {
            if (chapRelationMap.get(relation.getChapId()) == null) {
                List<Integer> labelIds = new ArrayList<>();
                chapRelationMap.put(relation.getChapId(), labelIds);
            }
            chapRelationMap.get(relation.getChapId()).add(relation.getLabelId());
        });

        // 拼装文章、标签关系
        List<ChapterVO> chapterVOList = new ArrayList<>();
        chapterList.forEach(chapter -> {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            List<Integer> labelIds = chapRelationMap.get(chapter.getId());
            if (!CollectionUtils.isEmpty(labelIds)) {
                LabelQuery labelQuery = new LabelQuery();
                labelQuery.setIds(labelIds);
                chapterVO.setLabelList(labelService.query(labelQuery));
            }
            chapterVOList.add(chapterVO);
        });

        chapterPage.setData(chapterVOList);
        return BaseResult.rightReturn(chapterPage);
    }


}
