package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Images;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:27
 * @Description:对图片进行上传，加载等操作
 **/

@RestController
@Slf4j
@RequestMapping("/image")
public class ImagesController {

    @Autowired
    private IImagesService iImagesService;

    /**
     * 获取前端传来的图片上传到服务器
     * @param file 图片
     * @return 返回服务器随机生成的图片路径
     * @throws IOException
     */
    @RequestMapping(value = {"/upload"},method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        String url = iImagesService.uploadImage(file.getOriginalFilename(),file.getInputStream());

        if(url == ""){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        Images image = new Images();
        image.setImageHttp(url);
        return JSON.toJSONString(Result.ok(image));
    }

    @RequestMapping(value = {"/delete"},method = RequestMethod.POST)
    public String deleteImage(@RequestBody Images image){
        log.info("待删除的图片{}",image);
        boolean isOk = iImagesService.delImage(image);

        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok());
    }
}
