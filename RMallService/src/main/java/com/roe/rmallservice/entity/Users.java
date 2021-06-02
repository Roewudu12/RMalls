package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @Auther: HP
 * @Date: 2021/3/12 14:25
 * @Description: 用户实体类
 **/


@Data
public class Users implements UserDetails{
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long userId;
    private String username;
    private String userPhone;
    private String userIntroduction;
    private String password;
    private Integer userAuthority;
    private Integer shopStar;
    private String image;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
