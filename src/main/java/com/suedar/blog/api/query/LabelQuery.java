package com.suedar.blog.api.query;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabelQuery {
    private Integer id;

    private List<Integer> ids;

    private Date created;

    private String label;
}
