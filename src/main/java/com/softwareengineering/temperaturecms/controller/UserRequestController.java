package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
@RestController
@Slf4j
@RequestMapping("/room")
public class UserRequestController {

    @Autowired
    private RoomStatusService roomStatusService;

    @PostMapping("/service")
    public ResponseEntity<String> requestOn(@RequestParam Long roomId,
                                            @RequestParam Double currentTemperature){

        Integer requestStatus = roomStatusService.ArrangeService(roomId, currentTemperature);


        if(requestStatus > 0){
            Map<String,Integer> res = new HashMap<>();
            res.put("id",requestStatus);
            return WebResultUtil.buildResult(ResponseVo.success(res), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_ON_FAIL),HttpStatus.OK);
        }
    }

    @GetMapping("/service")
    public ResponseEntity<String> updateStatus(Integer id){
        RoomStatus roomStatusFromRedis = roomStatusService.getRoomStatusFromRedis(id);

        return WebResultUtil.buildResult(ResponseVo.success(roomStatusFromRedis),HttpStatus.OK);
    }

    @PostMapping("/temperature")
    public ResponseEntity<String> changeTargetTemperature(@RequestParam Integer id,
                                                          @RequestParam Double targetTemperature){

        ChangeTargetTemperatureDto changeTargetTemperatureDto = new ChangeTargetTemperatureDto(id,targetTemperature);
        Boolean requestStatus = roomStatusService.RequestTemperature(changeTargetTemperatureDto);


        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.CHANGE_TARGET_TEMPERATURE_FAIL),HttpStatus.OK);
        }
    }

    @PutMapping("/service")
    public ResponseEntity<String> requestOff(@RequestParam Integer id){

        Boolean requestStatus = roomStatusService.WriteBack(id);

        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_OFF_FAIL),HttpStatus.OK);
        }
    }

    @GetMapping("/fee")
    public ResponseEntity<String> getFee(Integer id){
        Double fee = roomStatusService.getFee(id);

        if(fee.equals(0D)){
            return WebResultUtil.buildResult(
                    ResponseVo.error(ResponseEnum.GET_FEE_FAIL,"获取费用失败，空调尚未关机！"),HttpStatus.OK);
        }

        Map<String,Object> res = new HashMap<>();
        res.put("id",id);
        res.put("fee",fee);

        return WebResultUtil.buildResult(ResponseVo.success(res),HttpStatus.OK);
    }
}
