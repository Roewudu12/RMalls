package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.GoodChoice;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/29 22:19
 * @Description:
 **/
public interface IGoodChoiceService {
    List<GoodChoice> getAllChoices(Long goodId);

    boolean updateGoodChoices(List<GoodChoice> goodChoices);

    boolean insertGoodChoices(List<GoodChoice> goodChoices);

    void insertOrUpdate(List<GoodChoice> goodChoices);

    boolean delGoodChoice(Long id);
}
