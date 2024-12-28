package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺管理相关接口")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    private static final String MSG_SHOP_STATUS_BUSY = "店铺已打烊";
    private static final String MSG_SHOP_STATUS_OPEN = "店铺已营业";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        stringRedisTemplate.opsForValue().set("SHOP_STATUS", status.toString());
        return Result.success();
    }

    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        String shopStatus = stringRedisTemplate.opsForValue().get("SHOP_STATUS");
        Integer status = Integer.parseInt(shopStatus);
        return Result.success(status);
    }
}
