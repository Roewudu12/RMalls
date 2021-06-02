package com.roe.rmallservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.mapper.UsersMapper;
import com.roe.rmallservice.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/11 15:09
 * @Description: 用户服务层
 **/

@Service
@Slf4j
public class UsersService implements IUserService {
    @Autowired
    private UsersMapper usersMapper;


    @Override
    public Users userInfoById(Long uid) {
        System.out.println(uid);
        Users users = usersMapper.selectById(uid);
        log.debug("获取到用户信息：{}",users);
        return users;
    }

    @Override
    public Users userInfoByName(String username) {
        //根据用户名查询数据库
        QueryWrapper<Users> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        Users user = usersMapper.selectOne(queryWrapper);

        return user;
    }

    @Override
    public Users userInfoUpdate(Users user) {
        //更新数据库
        log.info("更新的是{}",user);
        int update = usersMapper.updateById(user);
        log.debug("更新了{}",update);
        //读取用户信息
        return userInfoById(user.getUserId());
    }

    @Override
    public List<Users> showUsers(int page, int pageNum) {
        Page<Users> p = new Page<>(page, pageNum);
        Page<Users> usersPage = usersMapper.selectPage(p, null);
        log.info("获取当前页信息{}",usersPage);
        return usersPage.getRecords();
    }

    @Override
    public Integer countUser() {
        return usersMapper.selectCount(null);
    }

    @Override
    public Boolean insertUser(Users user) {
        int insert = usersMapper.insert(user);
        if (insert>0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUser(Long uid) {
        log.info("删除用户id为{}",uid);
        int i = usersMapper.deleteById(uid);
        log.info("删除{}",i);
        if(i>0) return true;
        return false;
    }

    @Override
    public List<Users> searchUserLike(int page, int pageNum,String input) {
        Page<Users> p = new Page<>(page, pageNum);
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("username",input);
        return usersMapper.selectPage(p, wrapper).getRecords();
    }

    @Override
    public Boolean deleteUsers(List<Long> ids) {
        int i = usersMapper.deleteBatchIds(ids);
        if(i==ids.size()){
            return true;
        }
        return false;
    }

}
