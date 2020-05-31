package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
public interface RoomStatusService {

    //开机
    public Boolean ArrangeService(Long roomId,Double currentTemperature);

    //更改目标温度
    public Boolean RequestTemperature(ChangeTargetTemperatureDto changeTargetTemperatureDto);

    //获取单条记录
    public RoomStatus getRoomStatusById(Integer id);

    public Boolean WriteBack(Integer id);

    public Integer roomStatusOff(RoomStatus roomStatus);
}
