package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Comments;
import com.roe.rmallservice.mapper.CommentsMapper;
import com.roe.rmallservice.service.ICommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/2 14:29
 * @Description:
 **/

@Slf4j
@Service
public class CommentsService implements ICommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    @Override
    public boolean publishComments(Comments comments) {
        int insert = commentsMapper.insert(comments);
        return insert>0;
    }

    @Override
    public List<Comments> showComments(Long goodId, int page, int pageNum) {
        Page<Comments> p = new Page<>(page, pageNum);

        return commentsMapper.selectCommentsByGoodId(goodId,p).getRecords();
    }

    @Override
    public Integer getCount(Long goodId) {
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("good_id",goodId);
        return commentsMapper.selectCount(wrapper);
    }
}
