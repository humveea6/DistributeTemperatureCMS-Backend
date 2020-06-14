package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.pojo.RoomServingList;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-14
 */
public interface RoomServiceListService {

    List<RoomServingList> getRommServiceList(Integer roomId);
}
