package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author smallmartial
 * @Date 2019/5/23
 * @Email smallmarital@qq.com
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout=6000)
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    /**
     * 购物车列表
     * @param
     * @return
     */

    @RequestMapping("/findCartList")
    public List<Cart> findCartList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String cartListString  = util.CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if(cartListString==null || cartListString.equals("")){
            cartListString="[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
        if(username.equals("anonymousUser")){//如果未登录
            return cartList_cookie;
        }else{
            List<Cart> cartList_redis =cartService.findCartListFromRedis(username);//从redis中提取
            if(cartList_cookie.size()>0){//如果本地存在购物车
                //合并购物车
                cartList_redis=cartService.mergeCartList(cartList_redis, cartList_cookie);
                //清除本地cookie的数据
                util.CookieUtil.deleteCookie(request, response, "cartList");
                //将合并后的数据存入redis
                cartService.saveCartListToRedis(username, cartList_redis);
            }
            return cartList_redis;
        }
    }

//    @RequestMapping("/findCartList")
//    public List<Cart> findCartList(){
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        String cartListString = util.CookieUtil.getCookieValue(request, "cartList","UTF-8");
//        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
//
//        if(username.equals("anonymousUser")){//如果未登录
//            //读取本地购物车//
//            if(cartListString==null || cartListString.equals("")){
//                cartListString="[]";
//            }
//
//            return cartList_cookie;
//        }else{//如果已登录
//
//
//            List<Cart> cartList_redis =cartService.findCartListFromRedis(username);//从redis中提取
//            //cartService.mergeCartList(cartList_cookie,cartList_redis);
//
//            return cartList_redis;
//        }
//
//
//    }

    /**
     * 添加商品到购物车

     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num){
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:9105");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //得到登陆人账号,判断当前是否有人登陆
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        try {
            List<Cart> cartList =findCartList();//获取购物车列表
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            if(username.equals("anonymousUser")){ //如果是未登录，保存到cookie
                util.CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),3600*24 ,"UTF-8");
                System.out.println("向cookie存入数据");
            }else{//如果是已登录，保存到redis
                cartService.saveCartListToRedis(username, cartList);
            }
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }


}
