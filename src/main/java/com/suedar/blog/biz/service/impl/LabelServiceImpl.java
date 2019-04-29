package com.suedar.blog.biz.service.impl;

import com.suedar.blog.api.domain.Label;
import com.suedar.blog.api.query.LabelQuery;
import com.suedar.blog.biz.mapper.LabelMapper;
import com.suedar.blog.biz.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public int insert(Label label) {
        if (label == null) {
            return -1;
        }

        return labelMapper.insertSelective(label);
    }

    @Override
    public int deleteById(Integer id) {
        if (id == null) {
            return -1;
        }

        Label label = new Label();
        label.setId(id);
        label.setIsDeleted(Boolean.TRUE);
        return labelMapper.updateByPrimaryKeySelective(label);
    }

    @Override
    public int updateById(Label label) {
        if (label == null || label.getId() == null) {
            return -1;
        }

        return labelMapper.updateByPrimaryKeySelective(label);
    }

    @Override
    public List<Label> query(LabelQuery query) {
        return labelMapper.query(query);
    }
}
