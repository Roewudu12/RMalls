package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roe.rmallservice.entity.Comments;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.ICommentsService;
import com.roe.rmallservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:31
 * @Description:对评论进行管理
 **/

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    private ICommentsService commentsService;

    /**
     * 发布评价
     * @param comments
     * @return
     */
    @RequestMapping(value = {"/publish"},method = RequestMethod.POST)
    public String publishComments(@RequestBody Comments comments){
        boolean isOk = commentsService.publishComments(comments);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("发布成功"));
    }

    /**
     * 获取商品的评价
     * @param goodId
     * @return
     */
    @RequestMapping(value = {"/show"},method = RequestMethod.POST)
    public String showComments(@RequestParam("goodId") Long goodId,@RequestParam("page") int page,@RequestParam("pageNum") int pageNum){
        List<Comments> comments = commentsService.showComments(goodId,page, pageNum);
        if (comments==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        log.info("传出的评论{}",comments);
        return JSON.toJSONString(Result.ok(comments));
    }

    /**
     * 获取商品评价数量
     * @param goodId
     * @return
     */
    @RequestMapping(value = {"/count"},method = RequestMethod.POST)
    public String count(@RequestParam("goodId") Long goodId){
        Integer count = commentsService.getCount(goodId);
        if(count==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        JSONObject result = new JSONObject();
        result.put("count",count);
        return JSON.toJSONString(Result.ok(result));
    }
}
