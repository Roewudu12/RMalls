package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.GoodChoice;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IGoodChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/29 22:29
 * @Description:
 **/

@RestController
@RequestMapping("/choice")
@Slf4j
public class GoodChoiceController {

    @Autowired
    private IGoodChoiceService goodChoiceService;

    /**
     * 获取商品对应的选择项
     * @param goodId
     * @return
     */
    @RequestMapping(value = {"/byGoodId"},method = RequestMethod.POST)
    public String GoodChoicesGet(@RequestParam("goodId") Long goodId){
        List<GoodChoice> goodChoices = goodChoiceService.getAllChoices(goodId);
        if(goodChoices == null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok(goodChoices));
    }

    /**
     * 批量更新
     * @param goodChoices
     * @return
     */
    @RequestMapping(value = {"/update"},method = RequestMethod.POST)
    public String GoodChoicesUpdate(@RequestBody List<GoodChoice> goodChoices){
        boolean isOk = goodChoiceService.updateGoodChoices(goodChoices);

        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("更新成功"));
    }

    /**
     * 批量插入
     * @param goodChoices
     * @return
     */
    @RequestMapping(value = {"/insert"},method = RequestMethod.POST)
    public String GoodChoicesInsert(@RequestBody List<GoodChoice> goodChoices){
        boolean isOk = goodChoiceService.insertGoodChoices(goodChoices);

        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("插入成功"));
    }

    /**
     * 根据id删除选项
     * @param id
     * @return
     */
    @RequestMapping(value = {"/delete"},method = RequestMethod.POST)
    public String GoodChoiceDeleteById(@RequestParam("id") Long id){
        boolean isOk = goodChoiceService.delGoodChoice(id);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("删除成功"));
    }
}
