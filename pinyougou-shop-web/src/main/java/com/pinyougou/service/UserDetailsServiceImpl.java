package com.pinyougou.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.shop.controller.SellerController;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author smallmartial
 * @Date 2019/5/10
 * @Email smallmarital@qq.com
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public UserDetailsServiceImpl(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.println("经过了UserDetialServiceImpl");

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        //构建角色列表
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
       // return new User(username,"123456",grantedAuths);
        //得到商家
        TbSeller seller = sellerService.findOne(username);
        if (seller !=null){
            if (seller.getStatus().equals("1")){
                return new User(username,seller.getPassword(),grantedAuths);
            }else {
                return null;
            }
        }else {
            return null;

        }

    }
}

