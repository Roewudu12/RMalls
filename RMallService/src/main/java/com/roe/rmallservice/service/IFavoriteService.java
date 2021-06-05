package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Favorites;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/4 02:14
 * @Description:
 **/
public interface IFavoriteService {
    boolean addFavorite(Favorites favorite);

    List<Favorites> showFavorite(Long userId, int page, int pageNum);

    boolean isFavorited(Favorites favorite);

    Integer countFavorite(Long userId);
}
