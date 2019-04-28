package com.suedar.blog.api.query;

import com.suedar.blog.biz.base.Page;
import lombok.Data;

@Data
public class RecommendChapterPageQuery extends Page {
    private String text;
}
