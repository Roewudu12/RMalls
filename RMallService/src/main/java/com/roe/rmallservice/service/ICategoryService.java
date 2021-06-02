package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Category;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/21 17:25
 * @Description: 分类服务
 **/
public interface ICategoryService {
    List<Category> getTopCate();

    List<Category> getSubCate(Long fid);
}
