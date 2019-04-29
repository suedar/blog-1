package com.suedar.blog.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChapterLabelRelationDelDTO {
    private Integer chapId;

    private Integer labelId;

    private List<Integer> chapIds;

    private List<Integer> labelIds;
}
