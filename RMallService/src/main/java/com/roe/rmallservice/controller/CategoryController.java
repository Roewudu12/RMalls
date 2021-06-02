package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Category;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/21 17:21
 * @Description: 商品类别控制类
 **/

@RestController
@Slf4j
@RequestMapping("/cate")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取顶级分类
     * @return
     */
    @RequestMapping(value = {"/top"},method = RequestMethod.POST)
    public String getTopCate(){
        List<Category> categories = categoryService.getTopCate();
        if (categories==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(categories));
    }

    /**
     * 获取子分类
     * @param fid 父分类Id
     * @return
     */
    @RequestMapping(value = {"/sub"},method = RequestMethod.POST)
    public String getSubCate(@RequestParam("cateId") Long fid){
        List<Category> categories = categoryService.getSubCate(fid);
        if (categories==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(categories));
    }
}
