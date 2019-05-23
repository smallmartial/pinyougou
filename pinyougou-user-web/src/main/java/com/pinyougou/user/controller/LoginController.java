package com.pinyougou.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author smallmartial
 * @Date 2019/5/23
 * @Email smallmarital@qq.com
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    public Map showName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
