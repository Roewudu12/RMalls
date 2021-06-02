package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/10 22:41
 * @Description:
 **/

public interface IUserService {
    Users userInfoById(Long uid);

    Users userInfoByName(String username);

    Users userInfoUpdate(Users user);

    List<Users> showUsers(int page, int pageNum);

    Integer countUser();

    Boolean insertUser(Users user);

    Boolean deleteUser(Long uid);

    List<Users> searchUserLike(int page, int pageNum,String input);

    Boolean deleteUsers(List<Long> ids);
}
