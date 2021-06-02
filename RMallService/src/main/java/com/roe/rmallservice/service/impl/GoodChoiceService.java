package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.GoodChoice;
import com.roe.rmallservice.mapper.GoodChoiceMapper;
import com.roe.rmallservice.service.IGoodChoiceService;
import com.roe.rmallservice.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/29 22:20
 * @Description:
 **/

@Service
public class GoodChoiceService implements IGoodChoiceService {

    @Autowired
    private GoodChoiceMapper goodChoiceMapper;
    @Override
    public List<GoodChoice> getAllChoices(Long goodId) {
        QueryWrapper<GoodChoice> wrapper = new QueryWrapper<>();
        wrapper.eq("good_id",goodId);
        List<GoodChoice> goodChoices = goodChoiceMapper.selectList(wrapper);
        return goodChoices;
    }

    @Override
    public boolean updateGoodChoices(List<GoodChoice> goodChoices) {
        int i = goodChoiceMapper.updateBatch(goodChoices);
        return i>0;
    }

    @Override
    public boolean insertGoodChoices(List<GoodChoice> goodChoices) {
        int i = goodChoiceMapper.insertBatchSomeColumn(goodChoices);

        return i>0;
    }

    @Override
    public void insertOrUpdate(List<GoodChoice> goodChoices) {
        ArrayList<GoodChoice> insertList = new ArrayList<>();

        for (GoodChoice choice:goodChoices
             ) {
            if(choice.getId()!=null){
                goodChoiceMapper.updateById(choice);
            }else {
                insertList.add(choice);
            }
        }
        if(insertList.size()!=0){
            goodChoiceMapper.insertBatchSomeColumn(insertList);
        }


    }

    @Override
    public boolean delGoodChoice(Long id) {
        int i = goodChoiceMapper.deleteById(id);
        return i>0;
    }
}
