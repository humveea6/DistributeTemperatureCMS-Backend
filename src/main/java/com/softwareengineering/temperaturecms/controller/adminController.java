package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.softwareengineering.temperaturecms.consts.CMSConst.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-01
 */

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/power")
    public ResponseEntity<String> powerOn(){
        //别问，问就是这个方法纯属傻逼多余，他存在的意义就是满足动态结构设计文档

        return WebResultUtil.buildResult(ResponseVo.success("开机成功，进入设置模式"), HttpStatus.OK);
    }

    @PutMapping("/power")
    public ResponseEntity<String> setPara(@RequestParam Integer mode,
                                          @RequestParam Double targetTemperature,
                                          @RequestParam Double feeRate){

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if(mode != null){
            opsForValue.set(DEFAULT_MODE_REDIS_KEY,mode.toString());
        }
        if(targetTemperature != null){
            opsForValue.set(TARGET_TEMPERATURE_REDIS_KEY,targetTemperature.toString());
        }
        if(feeRate != null){
            opsForValue.set(CURRENT_FEE_RATE_REDIS_KEY,feeRate.toString());
        }

        return WebResultUtil.buildResult(ResponseVo.success("设置成功"), HttpStatus.OK);
    }

    @PostMapping("/startup")
    public ResponseEntity<String> startUp(){
        //别问，问就是这个方法纯属傻逼多余，他存在的意义就是满足动态结构设计文档

        return WebResultUtil.buildResult(ResponseVo.success("开机成功"), HttpStatus.OK);
    }
}
