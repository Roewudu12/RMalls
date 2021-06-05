package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Favorites;
import org.springframework.stereotype.Repository;

/**
 * @Auther: HP
 * @Date: 2021/5/17 16:32
 * @Description:
 **/
@Repository
public interface FavoriteMapper extends BaseMapper<Favorites> {
    IPage<Favorites> getFavoritesByUserId(Long userId, Page<Favorites> p);
}
