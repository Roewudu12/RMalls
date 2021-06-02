package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Images;
import com.roe.rmallservice.mapper.ImagesMapper;
import com.roe.rmallservice.service.IImagesService;
import com.roe.rmallservice.utils.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/21 09:21
 * @Description:
 **/

@Service
@Slf4j
public class ImagesService implements IImagesService{

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public String uploadImage(String originalFilename, InputStream inputStream) {
        String url = FtpUtil.upload(originalFilename, inputStream);
        return url;
    }

    @Override
    public boolean insertImages(List<Images> detailImages) {
        int i = imagesMapper.insertBatchSomeColumn(detailImages);

        log.info("插入了{}个图片到数据库",i);
        if(i>0) return true;
        return false;
    }

    @Override
    public boolean delImagesByGoodId(Long goodId, List<Images> detailImages) {
            QueryWrapper<Images> wrapper = new QueryWrapper<>();
            wrapper.eq("good_id",goodId);
            int i = imagesMapper.delete(wrapper);
            //删除服务器上的图
            FtpUtil.remove(detailImages);
            return i>0;
    }

    @Override
    public boolean delImage(Images image) {
        ArrayList<Images> images = new ArrayList<>();
        images.add(image);
        boolean remove = FtpUtil.remove(images);
        if(image.getImageId()!=null){
            //表示已经上传到数据库需要删除
            imagesMapper.deleteById(image.getImageId());
        }
        return remove;
    }

    @Override
    public void delImagesBatch(ArrayList<Long> ids, ArrayList<Images> images) {
        QueryWrapper<Images> wrapper = new QueryWrapper<>();
        wrapper.in("good_id",ids);
        imagesMapper.delete(wrapper);
        FtpUtil.remove(images);

    }
}
