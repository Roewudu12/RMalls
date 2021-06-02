package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IUserService;
import com.roe.rmallservice.utils.Constants;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: HP
 * @Date: 2021/5/11 15:08
 * @Description: 用户操作控制层
 **/

@RestController
@Slf4j
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private IUserService userService;

    /**
     * 通过用户id获取用户信息
     * @param uid 用户id
     * @return userJSON对象到前端
     */
    @RequestMapping(value = {"/userInfo/byId"},method = RequestMethod.POST)
    public String userInfoById(@RequestParam("userId") Long uid){
       Users user =  userService.userInfoById(uid);
       if(user==null){
           return JSON.toJSONString(Result.error(ResultCode.ERROR));
       }
       return JSON.toJSONString(Result.ok(user));
    }

    /**
     * 通过用户名称获取用户信息
     * @param username 用户名称
     * @return userJSON对象到前端
     */
    @RequestMapping(value = {"/userInfo/byName"},method = RequestMethod.POST)
    public String userInfoByName(@RequestParam("username") String username){
        Users user =  userService.userInfoByName(username);
        if(user==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(user));
    }


    /**
     * 更新用户信息
     * @param map 表单信息
     * @return
     */
    @RequestMapping(value = {"/userInfo/update"},method = RequestMethod.POST)
    public String userInfoUpdate(@RequestBody Map map){
        log.debug("userInfoUpdate",map);
        Users user = new Users();
        user.setUserId(Long.valueOf(map.get("userId").toString()));
        user.setUserAuthority((Integer) map.get("userAuthority"));
        user.setUsername((String)map.get("username"));
        user.setPassword(new BCryptPasswordEncoder().encode((String)map.get("password")));
        user.setUserPhone((String)map.get("userPhone"));
        user.setUserIntroduction((String)map.get("userIntroduction"));
        Users users = userService.userInfoUpdate(user);

        if(users==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(users));
    }

    /**
     * 查询分页查询用户
     * @param page
     * @return
     */
    @RequestMapping(value = {"/showUsers"},method = RequestMethod.POST)
    public String showUsers(@RequestParam("page") int page){
        List<Users> users = userService.showUsers(page, Constants.PAGE_NUM);
        if(users==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(users));
    }

    /**
     * 查询用户总数量
     * @return
     */
    @RequestMapping(value = {"/count"},method = RequestMethod.POST)
    public String countUser(){
        Integer count = userService.countUser();

        if(count==null){
            //查询失败
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(count));
    }

    /**
     * 新增用户
     * @param map 表单获取
     * @return
     */
    @RequestMapping(value = {"/insert"},method = RequestMethod.POST)
    public  String insertUser(@RequestBody Map map){
        Users user = new Users();
        user.setUsername((String)map.get("username"));
        user.setUserPhone((String)map.get("userPhone"));
        user.setUserAuthority((Integer)map.get("userAuthority"));
        user.setPassword(new BCryptPasswordEncoder().encode(Constants.PASSWORD));

        if (!userService.insertUser(user)) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("新增成功"));
    }

    /**
     * 根据uid删除用户
     * @param uid
     * @return
     */
    @RequestMapping(value = {"/delete"},method = RequestMethod.POST)
    public String deleteUser(@RequestParam("userId") Long uid){

        if (!userService.deleteUser(uid)) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("删除成功"));
    }

    /**
     * 模糊查询名字
     * @param input
     * @return
     */
    @RequestMapping(value = {"/search/like"},method = RequestMethod.POST)
    public String searchUserLike(@RequestParam("input") String input,@RequestParam("page") int page){
        List<Users> users = userService.searchUserLike(page,Constants.PAGE_NUM,input);
        if (users==null || users.size()==0) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(users));
    }

    /**
     * 批量删除user
     * @param ids
     * @return
     */
    @RequestMapping(value = {"/delete/users"},method = RequestMethod.POST)
    public String deleteUsers(@RequestBody List<Long> ids){
        if (!userService.deleteUsers(ids)) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("删除成功"));
    }
}
