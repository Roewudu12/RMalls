package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Category;
import com.roe.rmallservice.mapper.CategoryMapper;
import com.roe.rmallservice.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/21 17:26
 * @Description:
 **/

@Slf4j
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getTopCate() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.isNull("cate_fid");
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public List<Category> getSubCate(Long fid) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cate_fid",fid);

        return categoryMapper.selectList(wrapper);
    }
}
