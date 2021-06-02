package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Goods;
import org.springframework.stereotype.Repository;

/**
 * @Auther: HP
 * @Date: 2021/4/21 13:20
 * @Description:
 **/

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> selectGoodsByRecommend(Page<?> page);

    IPage<Goods> selectGoodsByCate(Page<?> page,Long cateId);

    Goods selectGoodDetailsByGoodId(Long id);

    IPage<Goods> selectGoods(Page<?> page);

    IPage<Goods> selectGoodsBySellerId(Long sellerId,Page<Goods> p);
}
