package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.roe.rmallservice.entity.Goods;
import com.roe.rmallservice.entity.Images;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IGoodsService;
import com.roe.rmallservice.service.IImagesService;
import com.roe.rmallservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:10
 * @Description: 主要用于商品推给前端，或查看商品详情时的操作
 **/

@RestController
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;


    /**
     * 主页获取多个默认排序的商品
     * @param page 当前为第几页,加载时由前端传入
     * @return JSON到前端
     */
    @RequestMapping(value = {"/showGood/hot"},method = RequestMethod.POST)
    public String showHotGoods(@RequestParam("page") int page){
        List<Goods> goods = null;

        goods = goodsService.showHotGoods(page,Constants.PAGE_NUM);

        if(goods==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(goods));
    }

    /**
     * 主页获取多个默认排序的商品
     * @param page 当前为第几页,加载时由前端传入
     * @return JSON到前端
     */
    @RequestMapping(value = {"/showGood/goods"},method = RequestMethod.POST)
    public String showGoodsGroupBySeller(@RequestParam("userAuthority") int userAuthority,@RequestParam("userId") Long userId,@RequestParam("page") int page){

        List<Goods> goods = null;
        if(userAuthority==1){
            goods = goodsService.showGoodsBySellerId(userId,page,Constants.PAGE_NUM);
        }else {
            goods = goodsService.selectGoods(page,Constants.PAGE_NUM);
        }

        if(goods==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(goods));
    }

    /**
     * 通过某个类别查看商品
     * @param page 分页
     * @param cateId 类别id
     * @return JSON到前端
     */
    @RequestMapping(value = {"/showGood/byCate"},method = RequestMethod.POST)
        public String showGoodsByCate(@RequestParam("page") int page,@RequestParam("cateId") Long cateId){
        List<Goods> goods = goodsService.showGoodsByCate(page,Constants.PAGE_NUM,cateId);
        if(goods==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(goods));
    }

    /**
     * 通过商品id获取商品详细信息
     * @param id 商品id
     * @return JSON到前端
     */
    @RequestMapping(value = {"/showGood/byId"},method = RequestMethod.POST)
    public String showGoodById(@RequestParam("id") Long id){
        Goods good = goodsService.showGoodById(id);
        if(good==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(good));
    }

    /**
     * 通过商品状态获取商品信息
     * @param status 商品状态
     * @return JSON到前端
     */
    @GetMapping("/showGood/byStatus")
    public String showGoodByStatus(@RequestParam("status") int status){
        List<Goods> goods = goodsService.showGoodByStatus(status);
        if(goods==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(goods));
    }


    /**
     * 批量删除商品
     * @param ids id列表
     * @return JSON到前端
     */
    @GetMapping("/delGood/byIds")
    public String delGoodByIds(@RequestParam("ids")int[] ids){
        boolean isOk = goodsService.delGoodByIds(ids);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok());
    }

    /**
     * 插入商品
     * @param good
     * @return JSON到前端
     */
    @RequestMapping(value = {"/insert"},method = RequestMethod.POST)
    public String insertGood(@RequestBody Goods good){
        log.info("新增商品{}",good);
        goodsService.insertGood(good);

        return JSON.toJSONString(Result.ok());
    }

    /**
     * 获取商品总数
     * @return
     */
    @RequestMapping(value = {"/count"},method = RequestMethod.POST)
    public String goodsCount(@RequestParam("userAuthority") int userAuthority,@RequestParam("userId") Long userId){
        Integer count;
        if(userAuthority==1){
            count = goodsService.goodsCountByUserId(userId);
        }else {
           count = goodsService.goodsCount();
        }

        if(count==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(count));
    }

    /**
     * 更新商品信息
     * @param good
     * @return
     */
    @RequestMapping(value = {"/update"},method = RequestMethod.POST)
    public String updateGood(@RequestBody Goods good){
        log.info("传入待更新的商品{}",good);
       boolean isOk =  goodsService.updateGood(good);
       if(!isOk){
           return JSON.toJSONString(Result.error(ResultCode.ERROR));
       }

       return JSON.toJSONString(Result.ok());
    }

    /**
     * 删除商品
     * @param good
     * @return
     */
    @RequestMapping(value = {"/delete"},method = RequestMethod.POST)
    public String delGood(@RequestBody Goods good){
        log.info("传入待删除的商品{}",good);
        boolean isOK = goodsService.delGood(good);

        if(!isOK){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("删除成功"));
    }

    /**
     * 批量删除商品
     * @param goods
     * @return
     */
    @RequestMapping(value = {"/delete/batch"},method = RequestMethod.POST)
    public String delGoods(@RequestBody List<Goods> goods){
        boolean isOk = goodsService.delGoodsBatch(goods);

        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("删除成功"));
    }
}
