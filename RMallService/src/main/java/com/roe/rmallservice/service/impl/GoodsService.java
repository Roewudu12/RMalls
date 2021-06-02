package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Goods;
import com.roe.rmallservice.entity.Images;
import com.roe.rmallservice.mapper.GoodsMapper;
import com.roe.rmallservice.mapper.ImagesMapper;
import com.roe.rmallservice.service.IGoodChoiceService;
import com.roe.rmallservice.service.IGoodsService;
import com.roe.rmallservice.service.IImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Auther: HP
 * @Date: 2021/4/20 19:28
 * @Description:
 **/

@Slf4j
@Service
public class GoodsService implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private IImagesService imagesService;

    @Autowired
    private IGoodChoiceService goodChoiceService;

    @Override
    public List<Goods> showHotGoods(int offset, int num) {
        Page<Goods> page = new Page<>(offset,num);
        goodsMapper.selectGoodsByRecommend(page);
        List<Goods> goods = page.getRecords();
        return goods;
    }


    @Override
    public List<Goods> showGoodsByCate(int offset, int num, Long cateId) {

        Page<Goods> page = new Page<>(offset,num);
        goodsMapper.selectGoodsByCate(page,cateId);
        List<Goods> goods = page.getRecords();

        return goods;
    }

    @Override
    public Goods showGoodById(Long id) {
        Goods good = goodsMapper.selectGoodDetailsByGoodId(id);
        return good;
    }

    @Override
    public boolean delGoodByIds(int[] ids) {
        int i = goodsMapper.deleteBatchIds(Arrays.asList(ids));
        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Goods> showGoodByStatus(int status) {
        return null;
    }

    @Override
    public boolean insertGood(Goods good) {
        int i = goodsMapper.insert(good);
        //获取插入后商品的id放入Image中
        Long goodId = good.getGoodId();
        log.info("插入后商品Id：{}",goodId);
        //有图片再插入
        if(good.getDetailImages().size()!=0){
            List<Images> detailImages = good.getDetailImages();
            detailImages.forEach(image->{
                image.setGoodId(goodId);
            });
            imagesService.insertImages(detailImages);
        }

        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Goods> showGoods(int page, int pageNum) {
        Page<Goods> p = new Page<>(page, pageNum);
        Page<Goods> goodsPage = goodsMapper.selectPage(p, null);
        return goodsPage.getRecords();
    }

    @Override
    public Integer goodsCount() {
        return goodsMapper.selectCount(null);
    }

    @Override
    public List<Goods> selectGoods(int page, int pageNum) {
        Page<Goods> p = new Page<>(page, pageNum);

        return goodsMapper.selectGoods(p).getRecords();
    }

    @Override
    public boolean updateGood(Goods good) {
        int i = goodsMapper.updateById(good);
        //获取新增添的图片
        if(good.getDetailImages().size()!=0){
            List<Images> detailImages = good.getDetailImages();
            List<Images> needInsert = new ArrayList<>();
            detailImages.forEach(image->{
                if(image.getImageId()==null){
                    image.setGoodId(good.getGoodId());
                    needInsert.add(image);
                }

            });
            if(needInsert.size()!=0) {
                imagesService.insertImages(needInsert);
            }
        }

        //获取有更新的以及新增添的商品选项
        if(good.getGoodChoices().size()!=0){
            goodChoiceService.insertOrUpdate(good.getGoodChoices());
        }


        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean delGood(Goods good) {
        int i = goodsMapper.deleteById(good.getGoodId());
        //同时删除与其相关的图片
        if(good.getDetailImages().size()!=0){
            imagesService.delImagesByGoodId(good.getGoodId(),good.getDetailImages());
        }
        return i>0;
    }

    @Override
    public boolean delGoodsBatch(List<Goods> goods) {
        //获取需要删除的商品id以及相关的图片
        ArrayList<Long> ids = new ArrayList<>();
        ArrayList<Images> images = new ArrayList<>();

        for (Goods good:goods) {
            ids.add(good.getGoodId());
            images.addAll(good.getDetailImages());
        }

        //删除所有ids的商品
        int i = goodsMapper.deleteBatchIds(ids);
        if(images.size()!=0){
            imagesService.delImagesBatch(ids,images);
        }
        return i>0;
    }

    @Override
    public List<Goods> showGoodsBySellerId(Long sellerId, int page, int pageNum) {
        Page<Goods> p = new Page<>(page,pageNum);

        return goodsMapper.selectGoodsBySellerId(sellerId,p).getRecords();
    }

    @Override
    public Integer goodsCountByUserId(Long userId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("good_seller_id",userId);

        return goodsMapper.selectCount(wrapper);
    }
}
