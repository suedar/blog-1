package com.suedar.blog.biz.controller;

import com.suedar.blog.api.domain.RecommendChapter;
import com.suedar.blog.api.query.RecommendChapterPageQuery;
import com.suedar.blog.biz.base.BaseResult;
import com.suedar.blog.biz.base.Page;
import com.suedar.blog.biz.service.RecommendChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendChapterController {

    private final RecommendChapterService recommendChapterService;

    @Autowired
    public RecommendChapterController(RecommendChapterService recommendChapterService) {
        this.recommendChapterService = recommendChapterService;
    }

    @PostMapping("/alterRecommand")
    public BaseResult<Integer> insert(RecommendChapter recommendChapter) {
        if (recommendChapter.getId() == null) {
            int res = recommendChapterService.insert(recommendChapter);
            if (res < 0) {
                return BaseResult.errorReturn("新增推荐文章失败");
            }
            return BaseResult.rightReturn(res);
        }

        int res = recommendChapterService.updateById(recommendChapter);
        if (res < 0) {
            return BaseResult.errorReturn("修改推荐文章失败");
        }

        return BaseResult.rightReturn(res);
    }

    @GetMapping("/delRecommand")
    public BaseResult<Integer> deleteById(Integer id) {
        int res = recommendChapterService.deleteById(id);
        if (res < 0) {
            return BaseResult.errorReturn("删除推荐文章失败");
        }

        return BaseResult.rightReturn(res);
    }

    @PostMapping("/recommand")
    public BaseResult<Page<List<RecommendChapter>>> pageQuery(RecommendChapterPageQuery query) {
        Page<List<RecommendChapter>> page = recommendChapterService.pageQuery(query);
        if (page == null) {
            return BaseResult.errorReturn("查询推荐文章失败");
        }

        return BaseResult.rightReturn(page);
    }

}
