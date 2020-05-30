package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.pojo.RoomStatus;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
public interface RoomStatusService {

    public Boolean ArrangeService(Long roomId,Double currentTemperature);

    public RoomStatus getRoomStatusById(Integer id);
}
