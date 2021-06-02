package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Images;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/21 09:20
 * @Description:
 **/
public interface IImagesService {

    String uploadImage(String originalFilename, InputStream inputStream);

    boolean insertImages(List<Images> detailImages);


    boolean delImagesByGoodId(Long goodId, List<Images> detailImages);

    boolean delImage(Images image);

    void delImagesBatch(ArrayList<Long> ids,ArrayList<Images> images);
}
