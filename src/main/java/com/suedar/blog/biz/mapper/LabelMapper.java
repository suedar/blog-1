package com.suedar.blog.biz.mapper;

import com.suedar.blog.api.domain.Label;
import com.suedar.blog.api.query.LabelQuery;

import java.util.List;

public interface LabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Label record);

    int insertSelective(Label record);

    Label selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);

    List<Label> query(LabelQuery query);
}