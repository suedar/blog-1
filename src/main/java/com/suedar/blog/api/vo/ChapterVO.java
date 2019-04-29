package com.suedar.blog.api.vo;


import com.suedar.blog.api.domain.Label;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChapterVO {

    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private String title;

    private Integer temperature;

    private Integer wordCount;

    private Integer readTime;

    private String content;

    private List<Label> labelList;
}
