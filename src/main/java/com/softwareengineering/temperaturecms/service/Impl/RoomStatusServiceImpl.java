package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomStatusMapper;
import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.softwareengineering.temperaturecms.consts.CMSConst.*;

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

    @Override
    public Integer roomStatusOff(RoomStatus roomStatus) {

        roomStatus.setEndTime(System.currentTimeMillis());
        return roomStatusMapper.updateByPrimaryKeySelective(roomStatus);
    }

    @Override
    public Boolean RequestTemperature(ChangeTargetTemperatureDto changeTargetTemperatureDto) {
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(changeTargetTemperatureDto.getId());
        if(roomStatus == null){
            return false;
        }
        rabbitTemplate.convertAndSend(CHANGE_TARGET_TEMPERATURE_QUEUE, JsonUtils.toJson(changeTargetTemperatureDto));

        return true;
    }

    @Override
    public Boolean WriteBack(Integer id){
        rabbitTemplate.convertAndSend(AC_OFF_QUEUE,id);

        return true;
    }

    private Integer setData(Long roomId, Integer mode, Double currentTem, Double targetTemp, Double fanSpeed){
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setCurrentTemperature(currentTem);
        roomStatus.setRoomId(roomId);
        roomStatus.setMode(mode);
        roomStatus.setTargetTemperature(targetTemp);
        roomStatus.setFansSpeed(fanSpeed);
        roomStatus.setStartUp(System.currentTimeMillis());
        int insert = roomStatusMapper.insertSelective(roomStatus);

        if(insert>0) {
            return roomStatus.getId();
        }
        else {
            return 0;
        }
    }
}
