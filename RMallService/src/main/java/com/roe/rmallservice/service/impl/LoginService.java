package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.mapper.UsersMapper;
import com.roe.rmallservice.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/12 14:34
 * @Description:
 **/

@Service("userDetailsService")
@Slf4j
public class LoginService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库
        QueryWrapper<Users> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        Users users = usersMapper.selectOne(queryWrapper);
        log.info("当前登录用户:{}",users);
        if(users==null){
            log.error("当前用户不存在:{}",username);
            throw new UsernameNotFoundException("用户名不存在");
        }
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
//        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        return users;
    }
}
