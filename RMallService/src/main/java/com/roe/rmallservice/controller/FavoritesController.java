package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roe.rmallservice.entity.Favorites;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IFavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:28
 * @Description:对用户收藏进行操作
 **/

@RestController
@Slf4j
@RequestMapping("/favorite")
public class FavoritesController {

    @Autowired
    private IFavoriteService favoriteService;

    /**
     * 插入自己喜欢的商品
     * @param favorite
     * @return
     */
    @RequestMapping(value = {"/insert"},method = RequestMethod.POST)
    public String addFavorite(@RequestBody Favorites favorite){
        boolean isOk = favoriteService.addFavorite(favorite);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("插入成功"));
    }

    /**
     * 展示用户的收藏
     * @param userId
     * @return
     */
    @RequestMapping(value = {"/show"},method = RequestMethod.POST)
    public String showFavorite(@RequestParam("userId") Long userId,@RequestParam("page") int page,@RequestParam("pageNum") int pageNum){
       List<Favorites> favorites =  favoriteService.showFavorite(userId,page,pageNum);
        if (favorites==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(favorites));
    }

    /**
     * 查看当前用户是否收藏此商品
     * @param favorite
     * @return
     */
    @RequestMapping(value = {"/is"},method = RequestMethod.POST)
    public String showFavorite(@RequestBody Favorites favorite){
        boolean isOk =  favoriteService.isFavorited(favorite);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok());
    }

    @RequestMapping(value = {"/count"},method = RequestMethod.POST)
    public String count(@RequestParam("userId") Long userId){
        Integer count =  favoriteService.countFavorite(userId);
        if (count==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        return JSON.toJSONString(Result.ok(jsonObject));
    }
}
