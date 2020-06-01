package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.vo.InvoiceVo;
import com.softwareengineering.temperaturecms.vo.RoomDetailListVo;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
public interface RoomStatusService {

    //开机
    public Integer ArrangeService(Long roomId,Double currentTemperature);

    //更改目标温度
    public Boolean RequestTemperature(ChangeTargetTemperatureDto changeTargetTemperatureDto);

    //获取单条记录
    public RoomStatus getRoomStatusById(Integer id);

    public Boolean WriteBack(Integer id);

    public Integer roomStatusOff(RoomStatus roomStatus);

    public Double getFee(Integer id);

    //从Redis获取房间实时状况
    public RoomStatus getRoomStatusFromRedis(Integer id);

    public RoomDetailListVo getRoomDetail(Integer id);

    public InvoiceVo getInvoice(Integer id);
}
