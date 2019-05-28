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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ChapterController {
    private final ChapterService chapterService;

    private final LabelService labelService;

    private final ChapterLabelRelationService relationService;

//    private static final String UPLOAD_PATH = "/Users/xyang010/Documents/IdeaProject/blog/src/main/resources/upload";
    public static final String UPLOAD_PATH = "C:\\Users\\Administrator\\IdeaProjects\\blog\\src\\main\\resources\\upload";


    @Autowired
    public ChapterController(ChapterService chapterService, LabelService labelService, ChapterLabelRelationService relationService) {
        this.chapterService = chapterService;
        this.labelService = labelService;
        this.relationService = relationService;
    }

    @PostMapping("/alterArticle")
    public BaseResult<Integer> insertOrUpdate(@RequestBody ChapterVO chapterVO) {
        if (chapterVO == null) {
            return BaseResult.errorReturn("保存文章失败");
        }

        Integer chapterId = chapterVO.getId();

        if (chapterVO.getId() == null) {
            // 保存文章
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(chapterVO, chapter);
            chapterId = chapterService.insert(chapter);
            if (chapterId < 0) {
                return BaseResult.errorReturn("保存文章失败");
            }
        } else {
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(chapterVO, chapter);
            int updateChapterRes = chapterService.updateById(chapter);
            if (updateChapterRes < 0) {
                return BaseResult.errorReturn("保存文章失败");
            }
        }

        // 保存文章文件
        try {
            String filePath = UPLOAD_PATH;
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String path = filePath + File.separator + chapterId + ".md";
            String chapterContent = chapterVO.getChapterContent();
            FileOutputStream outputStream = new FileOutputStream(new File(path));
            byte[] data = chapterContent.getBytes();
            outputStream.write(data, 0, data.length);
        } catch (Exception e) {
            return BaseResult.errorReturn("保存文章失败");
        }

        // 保存新标签
        List<String> labelNameList = chapterVO.getLabelList();
        if (CollectionUtils.isEmpty(labelNameList)) {
            return BaseResult.rightReturn(1);
        }

        List<Integer> labelIds = new ArrayList<>();

        for (String labelName : labelNameList) {
            LabelQuery labelQuery = new LabelQuery();
            labelQuery.setLabel(labelName);
            List<Label> labelTempList = labelService.query(labelQuery);
            if (CollectionUtils.isEmpty(labelTempList)) {
                Label label = new Label();
                label.setLabel(labelName);
                int labelId = labelService.insert(label);
                labelIds.add(labelId);
            } else {
                labelIds.add(labelTempList.get(0).getId());
            }
        }

        // 更新标签、文章关系
        List<Integer> addLabelIds = new ArrayList<>();
        List<Integer> delLabelIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(labelIds)) {
            List<Integer> chapterIds = new ArrayList<>();
            chapterIds.add(chapterId);
            List<ChapterLabelRelation> relationList = relationService.selectByChapterIds(chapterIds);
            List<Integer> dbLabelIds = relationList.stream().map(ChapterLabelRelation::getLabelId).collect(Collectors.toList());

            // 待新增标签、文章关系
            labelIds.forEach(labelId -> {
                if (!dbLabelIds.contains(labelId)) {
                    addLabelIds.add(labelId);
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
                for (Integer addLabelId : addLabelIds) {
                    ChapterLabelRelation relation = new ChapterLabelRelation();
                    relation.setLabelId(addLabelId);
                    relation.setChapId(chapterId);
                    addRelationList.add(relation);
                }

                relationService.insert(addRelationList);
            }

            // 删除标签、文章关系
            if (!CollectionUtils.isEmpty(delLabelIds)) {
                ChapterLabelRelationDelDTO delDTO = new ChapterLabelRelationDelDTO();
                delDTO.setLabelIds(delLabelIds);
                delDTO.setChapId(chapterId);
                relationService.del(delDTO);
            }
        }

        return BaseResult.rightReturn(1);
    }

    @GetMapping("/delArticle")
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

    @PostMapping("/article")
    public BaseResult<Page<List<ChapterVO>>> pageQuery(@RequestBody ChapterPageQuery query) {

        // 查询文章
        Page<List<Chapter>> dbPage = chapterService.pageQuery(query);
        if (dbPage == null) {
            return BaseResult.errorReturn("查询文章失败");
        }

        Page<List<ChapterVO>> chapterPage = new Page<>(dbPage.getPageNum(), dbPage.getPageSize(), dbPage.getTotalNum(), dbPage.getIsPage());
        List<Chapter> chapterList = dbPage.getResult();
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
                chapterVO.setLabelList(labelService.query(labelQuery).stream().map(Label::getLabel).collect(Collectors.toList()));
            }
            chapterVOList.add(chapterVO);
        });

        chapterPage.setResult(chapterVOList);
        return BaseResult.rightReturn(chapterPage);
    }

    @GetMapping("/queryArticle")
    public BaseResult<ChapterVO> query(@RequestParam("id") Integer id) {
        if (id == null) {
            return BaseResult.errorReturn("文章id为空");
        }

        List<Integer> chapIdlist = new ArrayList<>();
        chapIdlist.add(id);
        List<Chapter> chapterList = chapterService.selectByIds(chapIdlist);
        if (CollectionUtils.isEmpty(chapIdlist)) {
            return BaseResult.errorReturn("文章不存在");
        }

        // 文章热度 +1
        Chapter dbChapter = chapterList.get(0);
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setTemperature(dbChapter.getTemperature() + 1);
        chapterService.updateById(chapter);

        ChapterVO chapterVO = new ChapterVO();
        BeanUtils.copyProperties(dbChapter, chapterVO);
        chapterVO.setTemperature(chapter.getTemperature());
        List<ChapterLabelRelation> relationList = relationService.selectByChapterIds(chapIdlist);
        if (!CollectionUtils.isEmpty(relationList)) {
            List<Integer> labelIds = relationList.stream().map(ChapterLabelRelation::getLabelId).collect(Collectors.toList());
            LabelQuery labelQuery = new LabelQuery();
            labelQuery.setIds(labelIds);
            List<Label> labelList = labelService.query(labelQuery);
            List<String> labelNameList = labelList.stream().map(Label::getLabel).collect(Collectors.toList());
            chapterVO.setLabelList(labelNameList);
        }

        byte[] dataBytes;

        try(InputStream inputStream = new FileInputStream(UPLOAD_PATH + File.separator + id + ".md")) {
            dataBytes = new byte[inputStream.available()];
            inputStream.read(dataBytes);
        } catch (IOException e) {
            return BaseResult.errorReturn("获取文章内容失败");
        }
        chapterVO.setChapterContent(new String(dataBytes));
        return BaseResult.rightReturn(chapterVO);
    }
}