package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Goods;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/4/20 19:27
 * @Description:
 **/


public interface IGoodsService {
    /**
     * 获取默认商品
     * @param offset 偏移量
     * @param num 数量
     * @return 商品
     */
    List<Goods> showHotGoods(int offset, int num);

    /**
     * 通过类别获取商品
     * @param offset 偏移量
     * @param num 数量
     * @param cateId 类别
     * @return 商品
     */
    List<Goods> showGoodsByCate(int offset, int num, Long cateId);

    /**
     * 通过id获取商品详细信息
     * @param id 商品id
     * @return 商品
     */
    Goods showGoodById(Long id);

    /**
     * 批量删除商品
     * @param ids id数组
     * @return 布尔值
     */
    boolean delGoodByIds(int[] ids);

    /**
     * 通过商品状态获取商品信息
     * @param status 状态
     * @return 商品列表
     */
    List<Goods> showGoodByStatus(int status);

    /**
     * 插入商品
     * @param good
     * @return
     */
    boolean insertGood(Goods good);

    List<Goods> showGoods(int page, int pageNum);

    Integer goodsCount();

    List<Goods> selectGoods(int page, int pageNum);

    boolean updateGood(Goods good);

    boolean delGood(Goods good);

    boolean delGoodsBatch(List<Goods> goods);

    List<Goods> showGoodsBySellerId(Long sellerId, int page, int pageNum);

    Integer goodsCountByUserId(Long userId);
}
