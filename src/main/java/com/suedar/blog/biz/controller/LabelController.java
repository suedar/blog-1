package com.suedar.blog.biz.controller;


import com.suedar.blog.api.domain.ChapterLabelRelation;
import com.suedar.blog.api.domain.Label;
import com.suedar.blog.api.dto.ChapterLabelRelationDelDTO;
import com.suedar.blog.api.query.LabelQuery;
import com.suedar.blog.api.vo.LabelVO;
import com.suedar.blog.biz.base.BaseResult;
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
@RequestMapping("/label")
public class LabelController {

    private final ChapterService chapterService;

    private final LabelService labelService;

    private final ChapterLabelRelationService relationService;

    @Autowired
    public LabelController(ChapterService chapterService, LabelService labelService, ChapterLabelRelationService relationService) {
        this.chapterService = chapterService;
        this.labelService = labelService;
        this.relationService = relationService;
    }

    @PostMapping("/insert")
    public BaseResult<Integer> insert(@RequestBody Label label) {
        int res = labelService.insert(label);
        if (res < 0) {
            return BaseResult.errorReturn("新增标签失败");
        }

        return BaseResult.rightReturn(res);
    }

    @GetMapping("/del")
    public BaseResult<Integer> deleteById(@RequestParam("id") Integer id) {
        // 删除标签
        int delLabelRes = labelService.deleteById(id);
        if (delLabelRes < 0) {
            return BaseResult.errorReturn("删除标签失败");
        }

        // 删除文章、标签关系
        ChapterLabelRelationDelDTO delDTO = new ChapterLabelRelationDelDTO();
        delDTO.setLabelId(id);
        relationService.del(delDTO);

        return BaseResult.rightReturn(delLabelRes);
    }

    @PostMapping("/update")
    public BaseResult<Integer> updateById(@RequestBody Label label) {
        int res = labelService.updateById(label);
        if (res < 0) {
            return BaseResult.errorReturn("更新标签失败");
        }

        return BaseResult.rightReturn(res);
    }

    @PostMapping("/query")
    public BaseResult<List<LabelVO>> query(LabelQuery query) {

        // 查询标签
        List<Label> labelList = labelService.query(query);
        if (CollectionUtils.isEmpty(labelList)) {
            return BaseResult.rightReturn(null);
        }

        // 查询标签、文章关系
        List<Integer> labelIds = labelList.stream().map(Label::getId).collect(Collectors.toList());
        List<ChapterLabelRelation> relationList = relationService.selectByLabelIds(labelIds);
        Map<Integer, List<Integer>> labelRelationMap = new HashMap<>();
        relationList.forEach(relation -> {
            if (labelRelationMap.get(relation.getLabelId()) == null) {
                List<Integer> chapIds = new ArrayList<>();
                labelRelationMap.put(relation.getLabelId(), chapIds);
            }
            labelRelationMap.get(relation.getLabelId()).add(relation.getChapId());
        });

        // 拼装文章、标签关系
        List<LabelVO> labelVOList = new ArrayList<>();
        labelList.forEach(label -> {
            LabelVO labelVO = new LabelVO();
            BeanUtils.copyProperties(label, labelVO);
            List<Integer> chapIds = labelRelationMap.get(label.getId());
            if (!CollectionUtils.isEmpty(chapIds)) {
                labelVO.setChapterList(chapterService.selectByIds(chapIds));
            }
            labelVOList.add(labelVO);
        });

        return BaseResult.rightReturn(labelVOList);
    }
}
