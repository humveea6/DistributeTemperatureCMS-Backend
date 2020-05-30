package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomStatusMapper;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.UserRequestService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.softwareengineering.temperaturecms.consts.CMSConst.AC_ON_QUEUE;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
@Service
public class UserRequestServiceImpl implements UserRequestService {

    @Autowired
    private RoomStatusMapper roomStatusMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Boolean ArrangeService(Long roomId, Double currentTemperature) {
        //更新数据库数据
        Integer id = setData(roomId, 1, currentTemperature, 27D, 20D);

        //发送对象消息
        if(id > 0) {
            rabbitTemplate.convertAndSend(AC_ON_QUEUE, id);
            return true;
        }
        else{
            return false;
        }
    }

    private Integer setData(Long roomId,Integer mode,Double currentTem,Double targetTemp,Double fanSpeed){
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setCurrentTemperature(currentTem);
        roomStatus.setRoomId(roomId);
        roomStatus.setMode(mode);
        roomStatus.setTargetTemperature(targetTemp);
        roomStatus.setFansSpeed(fanSpeed);
        int insert = roomStatusMapper.insert(roomStatus);

        if(insert>0) {
            return roomStatus.getId();
        }
        else {
            return 0;
        }
    }
}
