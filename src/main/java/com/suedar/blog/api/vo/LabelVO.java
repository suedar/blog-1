package com.suedar.blog.api.vo;

import com.suedar.blog.api.domain.Chapter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabelVO {
    private Integer id;

    private Date created;

    private String label;

    private List<Chapter> chapterList;
}
