package com.roe.rmallservice.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.roe.rmallservice.method.UpdateBatchMethod;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/22 20:04
 * @Description:支持自定义数据方法注入
 **/

public class EasySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchSomeColumn());
        methodList.add(new UpdateBatchMethod());

        return methodList;
    }
}
