package com.roe.rmallservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Category;
import com.roe.rmallservice.entity.Goods;
import com.roe.rmallservice.entity.Images;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.mapper.CategoryMapper;
import com.roe.rmallservice.mapper.GoodsMapper;
import com.roe.rmallservice.mapper.ImagesMapper;
import com.roe.rmallservice.mapper.UsersMapper;
import com.roe.rmallservice.service.ICategoryService;
import com.roe.rmallservice.service.IImagesService;
import com.roe.rmallservice.utils.Constants;
import com.roe.rmallservice.utils.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RmallserviceApplicationTests {
    @Autowired
//    private ICategoryService categoryService;
//      private IImagesService iImagesService;
    private ImagesMapper imagesMapper;
    @Test
    void contextLoads() {
//        Page<Goods> goodsPage = new Page<>(1,6);
//        goodsMapper.selectGoodsByCate(goodsPage,2l);

//        goodsPage.getRecords().forEach(System.out::println);
//        Goods goods = goodsMapper.selectGoodDetailsByGoodId(1l);
//        System.out.println(goods);
//        Page<Users> p = new Page<>(1, 6);
//        Page<Users> usersPage = usersMapper.selectPage(p, null);
        ArrayList<Images> images = new ArrayList<>();
        Images image = new Images();
        image.setImageHttp("sajkhdjkashdjkas");
//        IdentifierGenerator identifierGenerator=new DefaultIdentifierGenerator();
        image.setGoodId(1l);
//        image.setImageId((Long) identifierGenerator.nextId(image));
        Images image2 = new Images();
        image2.setImageHttp("21312413");
//        image2.setImageId((Long) identifierGenerator.nextId(image2));

        image2.setGoodId(2l);
        images.add(image);
        images.add(image2);

        System.out.println(imagesMapper.insertBatchSomeColumn(images));



    }

}
