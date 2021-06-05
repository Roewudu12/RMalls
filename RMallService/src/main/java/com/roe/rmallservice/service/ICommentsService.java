package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Comments;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/2 14:29
 * @Description:
 **/
public interface ICommentsService {
    boolean publishComments(Comments comments);

    List<Comments> showComments(Long goodId, int page, int pageNum);

    Integer getCount(Long goodId);
}
