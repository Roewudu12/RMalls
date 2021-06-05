package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.roe.rmallservice.entity.Comments;
import org.springframework.stereotype.Repository;

/**
 * @Auther: HP
 * @Date: 2021/5/17 16:33
 * @Description:
 **/
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {
    IPage<Comments> selectCommentsByGoodId(Long goodId, Page<Comments> p);
}
