package com.suedar.blog.api.vo;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class ChapterVO {
    private MultipartFile file;

    private String chapterContent;

    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private String title;

    private Integer temperature;

    private Integer wordCount;

    private Integer readTime;

    private String content;

    private List<String> labelList;
}
