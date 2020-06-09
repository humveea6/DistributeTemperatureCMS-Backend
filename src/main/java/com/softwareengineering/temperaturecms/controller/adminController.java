package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.form.AdminSetingForm;
import com.softwareengineering.temperaturecms.form.UserLoginform;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "管理员相关")
public class adminController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/power")
    @ApiOperation("空调开机")
    public ResponseEntity<String> powerOn(){
        //别问，问就是这个方法纯属傻逼多余，他存在的意义就是满足动态结构设计文档

        return WebResultUtil.buildResult(ResponseVo.success("开机成功，进入设置模式"), HttpStatus.OK);
    }

    @PutMapping("/power")
    @ApiOperation("统一参数设置")
    public ResponseEntity<String> setPara(@RequestBody AdminSetingForm adminSetingForm){

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if(adminSetingForm.getMode() != null){
            opsForValue.set(DEFAULT_MODE_REDIS_KEY,adminSetingForm.getMode().toString());
        }
        if(adminSetingForm.getTargetTemperature() != null){
            opsForValue.set(TARGET_TEMPERATURE_REDIS_KEY,adminSetingForm.getTargetTemperature().toString());
        }
        if(adminSetingForm.getFeeRateLow() != null){
            opsForValue.set(LOW_FEE_RATE_REDIS_KEY,adminSetingForm.getFeeRateLow().toString());
        }
        if(adminSetingForm.getFeeRateMid() != null){
            opsForValue.set(MIDDLE_FEE_RATE_REDIS_KEY,adminSetingForm.getFeeRateMid().toString());
        }
        if(adminSetingForm.getFeeRateHigh() != null){
            opsForValue.set(HIGH_FEE_RATE_REDIS_KEY,adminSetingForm.getFeeRateHigh().toString());
        }
        if(adminSetingForm.getFanSpeed() != null){
            opsForValue.set(DEFAULT_FANS_SPEED_REDIS_KEY,adminSetingForm.getFanSpeed().toString());
        }
        if(adminSetingForm.getLowestTemperature() != null){
            opsForValue.set(LOWEST_TEMPERATURE_REDIS_KEY,adminSetingForm.getLowestTemperature().toString());
        }
        if(adminSetingForm.getHighestTemperature() != null){
            opsForValue.set(HIGHEST_TEMPERATURE_REDIS_KEY,adminSetingForm.getHighestTemperature().toString());
        }

        return WebResultUtil.buildResult(ResponseVo.success("设置成功"), HttpStatus.OK);
    }

    @PostMapping("/startup")
    @ApiOperation("进入工作状态")
    public ResponseEntity<String> startUp(){
        //别问，问就是这个方法纯属傻逼多余，他存在的意义就是满足动态结构设计文档

        return WebResultUtil.buildResult(ResponseVo.success("开机成功"), HttpStatus.OK);
    }
}
