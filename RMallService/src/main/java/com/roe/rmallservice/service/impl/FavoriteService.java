package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Favorites;
import com.roe.rmallservice.mapper.FavoriteMapper;
import com.roe.rmallservice.service.IFavoriteService;
import com.sun.org.apache.bcel.internal.generic.FADD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/4 02:14
 * @Description:
 **/

@Service
@Slf4j
public class FavoriteService implements IFavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public boolean addFavorite(Favorites favorite) {
        int insert = favoriteMapper.insert(favorite);
        return insert>0;
    }

    @Override
    public List<Favorites> showFavorite(Long userId, int page, int pageNum) {
        Page<Favorites> p = new Page<>(page,pageNum);
        return favoriteMapper.getFavoritesByUserId(userId,p).getRecords();
    }

    @Override
    public boolean isFavorited(Favorites favorite) {
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",favorite.getUserId());
        wrapper.eq("good_id",favorite.getGoodId());
        Integer integer = favoriteMapper.selectCount(wrapper);

        return integer>0;
    }

    @Override
    public Integer countFavorite(Long userId) {
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return favoriteMapper.selectCount(wrapper);
    }
}
