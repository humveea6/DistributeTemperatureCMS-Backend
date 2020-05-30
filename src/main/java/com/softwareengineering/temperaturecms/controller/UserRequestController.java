package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> RequestOn(@RequestParam Long roomId,
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
}
