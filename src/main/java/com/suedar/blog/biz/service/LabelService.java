package com.suedar.blog.biz.service;

import com.suedar.blog.api.domain.Label;
import com.suedar.blog.api.query.LabelQuery;

import java.util.List;

public interface LabelService {
    int insert(Label label);

    int deleteById(Integer id);

    int updateById(Label label);

    List<Label> query(LabelQuery query);
}
