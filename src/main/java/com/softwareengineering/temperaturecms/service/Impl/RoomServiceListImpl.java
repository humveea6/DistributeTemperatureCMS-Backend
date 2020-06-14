package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomServingListMapper;
import com.softwareengineering.temperaturecms.pojo.RoomServingList;
import com.softwareengineering.temperaturecms.pojo.RoomServingListExample;
import com.softwareengineering.temperaturecms.service.RoomServiceListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-14
 */
public class RoomServiceListImpl implements RoomServiceListService {

    @Autowired
    private RoomServingListMapper roomServingListMapper;

    @Override
    public List<RoomServingList> getRommServiceList(Integer roomId) {
        RoomServingListExample roomServingListExample = new RoomServingListExample();
        roomServingListExample.createCriteria().andRoomIdEqualTo(roomId);
        List<RoomServingList> roomServingLists = roomServingListMapper.selectByExample(roomServingListExample);
        return roomServingLists;
    }
}
