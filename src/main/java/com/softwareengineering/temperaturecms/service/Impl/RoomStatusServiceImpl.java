package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomStatusMapper;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.softwareengineering.temperaturecms.consts.CMSConst.AC_ON_QUEUE;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
@Service
public class RoomStatusServiceImpl implements RoomStatusService {

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

    @Override
    public RoomStatus getRoomStatusById(Integer id) {
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);

        return roomStatus;
    }

    private Integer setData(Long roomId, Integer mode, Double currentTem, Double targetTemp, Double fanSpeed){
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
