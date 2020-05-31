package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Boolean requestStatus = roomStatusService.ArrangeService(roomId, currentTemperature);

        //todo:updateStatus 更新监视器显示状态

        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_ON_FAIL),HttpStatus.OK);
        }
    }

    @PostMapping("/temperature")
    public ResponseEntity<String> changeTargetTemperature(@RequestParam Integer id,
                                                          @RequestParam Double targetTemperature){

        ChangeTargetTemperatureDto changeTargetTemperatureDto = new ChangeTargetTemperatureDto(id,targetTemperature);
        Boolean requestStatus = roomStatusService.RequestTemperature(changeTargetTemperatureDto);

        //todo:updateStatus 更新监视器显示状态

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
        //todo:updateStatus 更新监视器显示状态

        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_OFF_FAIL),HttpStatus.OK);
        }
    }
}
