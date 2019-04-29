package com.suedar.blog.api.query;


import com.suedar.blog.biz.base.Page;
import lombok.Data;

import java.util.Date;

@Data
public class ChapterPageQuery extends Page {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private String title;

    private Integer temperature;

    private Integer wordCount;

    private Integer readTime;
}
