package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopControler")
@Api(tags = "用户店铺相关接口")
@Slf4j
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        String shopStatus = redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(Integer.parseInt(shopStatus));
    }
}
