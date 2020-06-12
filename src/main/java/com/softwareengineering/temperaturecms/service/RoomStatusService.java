package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.StateEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.vo.DefaultSettingVo;
import com.softwareengineering.temperaturecms.vo.InvoiceVo;
import com.softwareengineering.temperaturecms.vo.RoomDetailListVo;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
public interface RoomStatusService {

    //开机
    public DefaultSettingVo ArrangeService(Long roomId, Double currentTemperature);

    //更改目标温度
    public Boolean RequestTemperature(ChangeTargetTemperatureDto changeTargetTemperatureDto);

    public Boolean RequestFanSpeed(ChangeTargetTemperatureDto changeTargetTemperatureDto);

    //获取单条记录
    public RoomStatus getRoomStatusById(Integer id);

    public Boolean WriteBack(Integer id);

    public Integer roomStatusOff(RoomStatus roomStatus);

    public Double getFee(Integer id);

    //从Redis获取房间实时状况
    public RoomStatus getRoomStatusFromRedis(Integer id);

    public Boolean updateRoomStatusInRedis(Integer id,RoomStatus roomStatus);

    public RoomDetailListVo getRoomDetail(Integer id);

    public InvoiceVo getInvoice(Integer id);

    public Long countByRoomId(Long roomId);

    public List<RoomStatus> getRoomStatusList(Long roomId);

    public void pauseService(Integer id);

    public Boolean continueService(Integer id);

    //修改房间空调工作状态
    public Boolean changeRoomServingState(Integer id, StateEnum stateEnum);

    //获取空调工作状态
    public StateEnum getRoomServingState(Integer id);

    public Double getRoomServiceTime(Integer id);
}
